package com.rip.roomies.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.activities.bills.Bills;
import com.rip.roomies.events.bills.ModifyBillListener;
import com.rip.roomies.events.bills.PopUpBillListener;
import com.rip.roomies.events.bills.RemindBillListener;
import com.rip.roomies.models.Bill;
import com.rip.roomies.util.InfoStrings;

import java.text.DecimalFormat;
import java.util.logging.Logger;

/**
 * Created by VinnysMacOS on 5/21/16.
 */
public class BillView extends LinearLayout {

	private static final Logger log = Logger.getLogger(BillView.class.getName());

	Bills activity;

	private Bill bill;
	private BillContainer container;

	/**
	 * @see android.view.View( Context )
	 */
	public BillView(Context context) {
		super(context);
	}

	public BillView(Context context, Bills activity, BillContainer container) {
		super(context);
		this.activity = activity;
		this.container = container;
	}


	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public BillView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public BillView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Get the User object that this class represents
	 *
	 * @return The User object in question
	 */
	public Bill getBill() {
		return bill;
	}

	/**
	 * Set the bill of this object whose information this view will display
	 *
	 * @param bill The bill object to display
	 */
	public void setBill(Bill bill) {
		this.bill = bill;
		setupLayout();
	}

	/**
	 * Sets up the layout for this UserView.
	 */
	private void setupLayout() {
		log.info(String.format(InfoStrings.VIEW_SETUP, BillView.class.getSimpleName()));

		setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.VERTICAL);

        /* Horizontal Container for Buttons*/
		LinearLayout innerLayout = new LinearLayout(getContext());
		innerLayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		LinearLayout underline = new LinearLayout(getContext());
		underline.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, 1));

		underline.setBackgroundColor(getResources().getColor(R.color.black));

		innerLayout.setOrientation(LinearLayout.HORIZONTAL);

		TextView name = new TextView(getContext());
		TextView amount = new TextView(getContext());
		TextView description = new TextView(getContext());
		Button removeBill = new Button(getContext());
		Button editBill = new Button(getContext());
		Button remindBill = new Button(getContext());
		Button paidBill = new Button(getContext());

		/* Setting text Size */
		name.setTextSize(20);
		amount.setTextSize(20);
		description.setTextSize(20);

        /* set layout_gravity for Buttons. Prepping for Linear Layout*/


        /* Setting text Color */
		name.setTextColor(getResources().getColor(R.color.black));
		amount.setTextColor(getResources().getColor(R.color.black));
		description.setTextColor(getResources().getColor(R.color.black));

		removeBill.setText("Remove");
		editBill.setText("Edit");
		remindBill.setText("Remind");
		paidBill.setText("Paid");

		removeBill.setTextSize(20);
		editBill.setTextSize(20);
		remindBill.setTextSize(20);
		paidBill.setTextSize(20);

		/* Setting Font
		removeBill.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
		editBill.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
		remindBill.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
		paidBill.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));*/

		removeBill.setTextColor(getResources().getColor(R.color.pink));
		editBill.setTextColor(getResources().getColor(R.color.colorPrimary));
		remindBill.setTextColor(getResources().getColor(R.color.colorPrimary));
		paidBill.setTextColor(getResources().getColor(R.color.pink));


		removeBill.setPadding(30, 30, 30, 30);
		editBill.setPadding(30, 30, 30, 30);
		remindBill.setPadding(30, 30, 30, 30);

        /* Changing Gray buttons to Blue bordered ones */
		removeBill.setBackground(getResources().getDrawable(R.drawable.rec_border_pink));
		editBill.setBackground(getResources().getDrawable(R.drawable.rec_border));
		remindBill.setBackground(getResources().getDrawable(R.drawable.rec_border));
		paidBill.setBackground(getResources().getDrawable(R.drawable.rec_border_pink));

		//set the removeBill/editbill listeners

		int removeLayoutID = R.layout.activity_confirm_bill_remove;
		int paidLayoutID = R.layout.activity_confirm_bill_paid;

		removeBill.setOnClickListener(new PopUpBillListener(activity, removeBill, removeLayoutID, this, bill, removeBill,
				editBill, name,
				amount, description, innerLayout, underline));

		paidBill.setOnClickListener(new PopUpBillListener(activity, removeBill, paidLayoutID, this, bill, removeBill,
				editBill, name,
				amount, description, innerLayout, underline));

		editBill.setOnClickListener(new ModifyBillListener(activity, bill,
				this, name,
				amount, description));

		remindBill.setOnClickListener(new RemindBillListener(remindBill,this, bill));

		if(bill.reminded()) {
			remindBill.setBackground(getResources().getDrawable(R.drawable.rec_border_gray));
			remindBill.setTextColor(getResources().getColor(R.color.black_overlay));
			remindBill.setEnabled(false);
		}
		else {
			remindBill.setBackground(getResources().getDrawable(R.drawable.rec_border_pink));
			remindBill.setTextColor(getResources().getColor(R.color.pink));
			remindBill.setEnabled(true);
		}



		/* Getting User's Information from bill*/
		name.setText(bill.getName());
		description.setText(bill.getDescription());
		DecimalFormat cash = new DecimalFormat("$#.##");
		cash.setMinimumFractionDigits(2);
		amount.setText(cash.format(Math.abs(bill.getAmount())));

		/* Accenting the name of each bill. Better to differentiate*/
		name.setTypeface(Typeface.DEFAULT_BOLD);
		description.setTextColor(getResources().getColor(R.color.black_overlay));



		/* Removing negative sign in amount in YouOwe */
		if (bill.getAmount()< 0)
			amount.setText(cash.format(bill.getAmount()*-1));
		else
			amount.setText(cash.format(bill.getAmount()));



		/* Setting up Layout Parameters for the buttons and bill info */

		LinearLayout.LayoutParams editBill_lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				0.33f);

		LinearLayout.LayoutParams removeBill_lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				0.33f);

		LinearLayout.LayoutParams remindBill_lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				0.33f);

		LinearLayout.LayoutParams paidBill_lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				0.33f);

		LinearLayout.LayoutParams billInfo_lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		billInfo_lp.setMargins(40, 0, 0, 0);

		/* Adding space between Buttons */
		editBill_lp.setMargins(20,15,10,20);
		paidBill_lp.setMargins(10,15,20,20);
		removeBill_lp.setMargins(10,15,20,20);
		remindBill_lp.setMargins(10,15,10,20);

		/* Setting Buttons in LinearView Horizontal for owe you */
		innerLayout.addView(editBill, editBill_lp);

		/* Removing negative sign in owe you before display */
		if(bill.getAmount()>0){
			innerLayout.addView(remindBill, remindBill_lp);
			innerLayout.addView(removeBill, removeBill_lp);
		}

		else
			innerLayout.addView(paidBill, paidBill_lp);


		innerLayout.setGravity(Gravity.CENTER);
		addView(name, billInfo_lp);
		addView(amount, billInfo_lp);
		addView(description, billInfo_lp);
		addView(innerLayout);
		addView(underline);
		setPadding(0,0,0,5);
	}

    /**
     * Get the BillContainer holding this BillView
     *
     * @return The BillContainer object in question
     */
    public BillContainer getContainer() {
        return container;
    }

}
