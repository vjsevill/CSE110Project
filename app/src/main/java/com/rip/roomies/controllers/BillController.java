package com.rip.roomies.controllers;

import android.os.AsyncTask;

import com.rip.roomies.activities.bills.Bills;
import com.rip.roomies.models.Bill;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.views.BillContainer;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by VinnysMacOS on 5/22/16.
 */
public class BillController {
    private static final Logger log = Logger.getLogger(BillController.class.getName());

    private static BillController controller;
    private BillContainer bills;
    private Bills activity;
    String flip_amount;
    int oweeId;
    int ownerId;

    /**
     * Gets the singleton bill controller.
     * @return The bill controller
     */
    public static BillController getController() {
        if (controller == null) {
            controller = new BillController();
        }

        return controller;
    }

    public void createBill(final String name, final String description, final String amount,
                           final BillContainer youowe_bills_container,
                           final BillContainer oweyou_bills_container,
                           final int oweeID) {



        if(amount.startsWith("-")) {
            this.bills = youowe_bills_container;
            flip_amount = amount.replace("-", "");
            oweeId = User.getActiveUser().getId();
            ownerId = oweeID;
        }
        else {
            this.bills = oweyou_bills_container;
            flip_amount = amount;
            oweeId = oweeID;
            ownerId = User.getActiveUser().getId();
        }
        // Create and run a new thread
        new AsyncTask<Void, Void, Bill>() {
            @Override
            protected Bill doInBackground(Void... v) {
                log.info(String.format(Locale.US, InfoStrings.CREATEBILL_CONTROLLER,
                        name, description, Float.parseFloat(flip_amount)));

                // Create request user and get response from createUser()
                Bill request = new Bill(ownerId, name, description, Float.parseFloat(flip_amount), oweeId);
                Bill response = request.createBill();
                return response;
            }

            @Override
            protected void onPostExecute(Bill result) {
                //if the bill returned wasnt null, add it to the container
                if (result != null) {
                    //add the bill returned from the DB to the BillContainer. Has uniq bill id, owner id, name, desc, amount...

                    if(amount.startsWith("-")){
                        youowe_bills_container.addBill(result);
                        activity.addToYouOweBalance(result.getAmount());
                    }
                    else {
                        oweyou_bills_container.addBill(result);
                        activity.addToOweYouBalance(result.getAmount());
                    }

                }
            }
        }.execute();
    }

    public void removeBill(final int billRowID,
                           final BillContainer container) {
        // Create and run a new thread
        new AsyncTask<Void, Void, Bill>() {
            @Override
            protected Bill doInBackground(Void... v) {
               // log.info(String.format(Locale.US, InfoStrings.CREATEBILL_CONTROLLER,
                //        name, description, Float.parseFloat(amount)));

                // Create request user and get response from createUser()
                Bill request = new Bill();
                return request.removeBill(billRowID);
            }

            @Override
            protected void onPostExecute(Bill result) {
                //if the bill returned wasnt null,remove it from the billcontainer
                if (result != null) {
                    //remove the bill from the BillContainer.
                    container.removeBill(result);
                    if (result.getAmount() < 0)
                        activity.addToYouOweBalance(-1*result.getAmount());
                    else
                        activity.addToOweYouBalance(-1*result.getAmount());
                }
            }
        }.execute();

    }

    public void modifyBill(final Bill theBillToEdit) {
        // Create and run a new thread
        new AsyncTask<Void, Void, Bill>() {
            @Override
            protected Bill doInBackground(Void... v) {
                // log.info(String.format(Locale.US, InfoStrings.CREATEBILL_CONTROLLER,
                //        name, description, Float.parseFloat(amount)));

                // Create request user and get response from createUser()
                Bill request = new Bill();
                request.modifyBill(theBillToEdit);

                //We dont need to return anything...
                return null;
            }

        }.execute();

    }

    public void populateBills(final BillContainer youowe_bills_container,
                         final BillContainer oweyou_bills_container) {
        // Create and run a new thread
        new AsyncTask<Void, Void, Bill[]>() {
            @Override
            protected Bill[] doInBackground(Void... v) {
                log.info(String.format(Locale.US, InfoStrings.GET_BILLS_CONTROLLER,
                        User.getActiveUser().getId()));

                return User.getActiveUser().getBills();
            }

            @Override
            protected void onPostExecute(Bill[] result) {
                //if the array returned wasn't null, add each bill to the appropriate container
                if (result != null) {
                    for (Bill bill : result) {
                        if (bill.getAmount() < 0) {
                            bill.setAmount(bill.getAmount());
                            youowe_bills_container.addBill(bill);
                            activity.addToYouOweBalance(bill.getAmount());
                        }
                        else {
                            oweyou_bills_container.addBill(bill);
                            activity.addToOweYouBalance(bill.getAmount());
                        }
                    }
                }
            }

        }.execute();

    }

    public void setActivity(Bills activity) {
        this.activity = activity;
    }

}
