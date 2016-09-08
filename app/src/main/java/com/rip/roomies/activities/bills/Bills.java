package com.rip.roomies.activities.bills;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.BillController;
import com.rip.roomies.events.bills.AddBillListener;
import com.rip.roomies.models.Bill;
import com.rip.roomies.views.BillContainer;

import java.text.DecimalFormat;

public class Bills extends GenericActivity {

    private final int EDIT_BILL_RESULT_CODE= 1;
    private final int ADD_BILL_RESULT_CODE = 2;
    private final int REQUEST_CODE = 1;


    private BillContainer youowe_bills_container;
    private BillContainer oweyou_bills_container;
    private TextView you_owe_balance;
    private TextView owe_you_balance;
    private TextView total_balance;
    private Button addBill;
    private Bill theBillToEdit;


    private TextView aBillsName, aBillsDescription, aBillsAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);

        //set the Listeners which will set the controllers
        /* Linking xml objects to java */
        addBill = (Button) findViewById(R.id.add_bill_btn);
        youowe_bills_container = (BillContainer) findViewById(R.id.bills_youowe_container);
        oweyou_bills_container = (BillContainer) findViewById(R.id.bills_oweyou_container);
        you_owe_balance = (TextView) findViewById(R.id.bill_youowe_balance);
        owe_you_balance = (TextView) findViewById(R.id.bill_oweyou_balance);
        total_balance = (TextView) findViewById(R.id.bill_total);

        addBill.setOnClickListener(new AddBillListener(this, youowe_bills_container,oweyou_bills_container));

        //populate list of bills
        BillController controller = BillController.getController();
        controller.setActivity(this);
        controller.populateBills(youowe_bills_container, oweyou_bills_container);
    }


    /**
     * This is called from one of the edit bill buttons listeners for a
     * particular bill. Will start the ModifyBill activity and will
     * send it the original fields for the bill, so the user can edit them.
     *
     * @param name The name of the person the user owes or has lent money to.
     * @param description The description for the bill.
     * @param amount The amount for the bill.
     *
     */
    public void toEditBillScreen(TextView name, TextView description, TextView amount, Bill theBillToEdit) {
        //grab the TextViews for this particular bill.
        aBillsAmount = amount;
        aBillsDescription = description;
        aBillsName = name;
        this.theBillToEdit = theBillToEdit;

        //Get ready for transferring to the Modify Bill activity.
        Intent intent = new Intent(getApplicationContext(), ModifyBill.class);

        //send the ModifyBill activity key-value pairs so that
        //when the activity starts, its EditText fields for name, desc,
        //and amount are filled correctly.
        intent.putExtra("Orig_Key_Name", name.getText());
        intent.putExtra("Orig_Key_Description", description.getText());
        DecimalFormat format = new DecimalFormat("#.##");
        format.setMinimumFractionDigits(2);
        intent.putExtra("Orig_Key_Amount", format.format(theBillToEdit.getAmount()));
        intent.putExtra("Key_Bill_Row_ID", String.valueOf(theBillToEdit.getRowID()));

        //Start the ModifyBill activity, when its finished, onActivityResult
        //will be called.
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void toAddBillScreen() {
        startActivityForResult(new Intent(getApplicationContext(), AddBill.class), REQUEST_CODE);
    }



    /**
     * This is called from two places.
     * 1)From one of the edit bill buttons listeners for a
     * particular bill. Will start the ModifyBill activity and will
     * send it the original fields for the bill. So the user can edit them.
     * 2) From the Add a new Bill page when the user wants to submit the new bill and
     * go back to the main Bills page(ie this activity).
     *
     * @param data The updated Strings for the name, description, and amount.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == EDIT_BILL_RESULT_CODE) {

            //Grab the updated Strings for the bills name, description, and
            //amount fields
            String updName = data.getStringExtra("Upd_Key_Name");
            String updDescription = data.getStringExtra("Upd_Key_Description");
            String updAmount = data.getStringExtra("Upd_Key_Amount");

            //remove previous amount from balance
            if (theBillToEdit.getAmount() < 0)
                addToYouOweBalance(-1*theBillToEdit.getAmount());
            else
                addToOweYouBalance(-1*theBillToEdit.getAmount());

            //reset the bills name,description,amount to whatever the user edited.
            theBillToEdit.setName(updName);
            theBillToEdit.setDescription(updDescription);
            DecimalFormat cash = new DecimalFormat("$#.##");
            cash.setMinimumFractionDigits(2);
            boolean negative = false;
            if (updAmount.charAt(0) == '$')
                updAmount = updAmount.substring(1);
            else if (updAmount.charAt(0) == '-' && updAmount.charAt(1) == '$') {
                negative = true;
                updAmount = updAmount.substring(2);
            }

            if (negative)
                theBillToEdit.setAmount(-1 * Float.parseFloat(updAmount));
            else
                theBillToEdit.setAmount(Float.parseFloat(updAmount));

            //reset the text for the 3 TextViews of the bill the user
            //selected in the main IOU's page.
            aBillsName.setText(updName);
            aBillsDescription.setText(updDescription);
            aBillsAmount.setText(cash.format(Float.parseFloat(updAmount)));

            //update the DB bill entry
            BillController.getController().modifyBill(theBillToEdit);


            //add new amount to balance
            if (theBillToEdit.getAmount() < 0)
                addToYouOweBalance(theBillToEdit.getAmount());
            else
                addToOweYouBalance(theBillToEdit.getAmount());

        }
        else if(resultCode == ADD_BILL_RESULT_CODE) {
            String newName = data.getStringExtra("Key_New_Name");
            String newDescription = data.getStringExtra("Key_New_Description");
            String newAmount = data.getStringExtra("Key_New_Amount");
            int oweeID = Integer.parseInt(data.getStringExtra("Key_New_oweeID"));

            //the args are all OK to be inserted into the database, ie amount is a parsable float
            BillController.getController().createBill(newName, newDescription, newAmount, youowe_bills_container,
                    oweyou_bills_container, oweeID);
        }
    }

    @Override
    public void onBackPressed() {
        this.toHome();
    }

    public void addToYouOweBalance(float change) {
        addToBalance(you_owe_balance, -1*change);
        addToBalance(total_balance, change);
    }

    public void addToOweYouBalance(float change) {
        addToBalance(owe_you_balance, change);
        addToBalance(total_balance, change);
    }

    private void addToBalance(final TextView balance, final float change) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String balanceString = balance.getText().toString();
                if (balanceString.charAt(0) == '-')
                    balanceString = '-' + balanceString.substring(2);
                else
                    balanceString = balanceString.substring(1);
                float bal = Float.parseFloat(balanceString);
                bal += change;

                DecimalFormat cash = new DecimalFormat("$#.##");
                cash.setMinimumFractionDigits(2);

                balance.setText(cash.format(bal));
            }
        });
    }
}
