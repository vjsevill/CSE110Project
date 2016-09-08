package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rip.roomies.activities.bills.Bills;
import com.rip.roomies.models.Bill;
import com.rip.roomies.util.InfoStrings;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by VinnysMacOS on 5/21/16.
 */
public class BillContainer extends ScrollView {
    private static final Logger log = Logger.getLogger(BillContainer.class.getName());

    private ArrayList<Bill> bills = new ArrayList<>();
    private LinearLayout billLayout;


    /**
     * @see android.view.View(Context)
     */
    public BillContainer(Context context) {
        super(context);

        billLayout = new LinearLayout(context);
        addView(billLayout);
    }

    /**
     * @see android.view.View(Context, AttributeSet)
     */
    public BillContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        billLayout = new LinearLayout(context, attrs);
        addView(billLayout);
    }

    /**
     * @see android.view.View(Context, AttributeSet, int)
     */
    public BillContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        billLayout = new LinearLayout(context, attrs, defStyle);
        addView(billLayout);
    }

    /**
     * Adds a new bill to the BillContainer at the end of the list.
     *
     * @param newBill The new Bill to add
     * @param oweeID
     */
    public void addBill(Bill newBill) {
        log.info(String.format(InfoStrings.CONTAINER_ADD,
                BillView.class.getName(),  BillContainer.class.getName()));

        //add our newly created bill to our dynamic arraylist of bills.
        bills.add(newBill);

        BillView billView = new BillView(getContext(), (Bills)getContext(), this);
        billView.setBill(newBill);
        billLayout.addView(billView);
    }

    public void removeBill(Bill billToRemove) {
        //log statement

        //remove the bill from the dynamic list of bills
        for (int i = 0; i < bills.size(); i++) {
            if (bills.get(i).getRowID() == billToRemove.getRowID()) {
                    bills.remove(i);
            }
        }
    }


    /**
     * Get the bills held by this BillContainer
     * @return An array of bills
     */
    public Bill[] getBills() {
        Bill[] temp = new Bill[bills.size()];
        return bills.toArray(temp);
    }
}
