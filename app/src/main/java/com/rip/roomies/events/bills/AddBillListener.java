package com.rip.roomies.events.bills;

import android.view.View;

import com.rip.roomies.activities.bills.Bills;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.views.BillContainer;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by VinnysMacOS on 5/21/16.
 */
public class AddBillListener implements View.OnClickListener {
    private static final Logger log = Logger.getLogger(AddBillListener.class.getName());

    private Bills activity;
    private BillContainer container;
    private BillContainer container2;

    public AddBillListener(Bills activity, BillContainer container, BillContainer container2) {
        this.activity = activity;
        this.container = container;
        this.container2 = container2;
    }

    @Override
    public void onClick(View v) {

        log.info(String.format(Locale.US, InfoStrings.INVITE_USERS));

        //grab the 3 edit texts from xml->java

        activity.toAddBillScreen();


    }



}
