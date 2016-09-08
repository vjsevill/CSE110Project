package com.rip.roomies.events.bills;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.activities.bills.Bills;
import com.rip.roomies.models.Bill;

/**
 * Created by VinnysMacOS on 5/20/16.
 */
public class ModifyBillListener implements View.OnClickListener {

    private Bills activity;
    private Bill theBillToEdit;
    private LinearLayout context;
    private TextView name, description, amount;

    public ModifyBillListener(Bills activity, Bill theBillToEdit, LinearLayout context, TextView name, TextView amount, TextView description) {
        this.activity = activity;
        this.theBillToEdit = theBillToEdit;
        this.context = context;
        this.name = name; this.description = description; this.amount = amount;
    }

    @Override
    public void onClick(View v) {
        //only if this is the creditor can the bill be modified

        activity.toEditBillScreen(name, description, amount, theBillToEdit);
    }

}
