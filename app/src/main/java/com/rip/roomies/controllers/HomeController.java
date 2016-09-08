package com.rip.roomies.controllers;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.activities.bills.Bills;
import com.rip.roomies.activities.home.Home;
import com.rip.roomies.models.Bill;
import com.rip.roomies.models.Bulletin;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.views.BillContainer;
import com.rip.roomies.views.BulletinContainer;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by VinnysMacOS on 5/22/16.
 */
public class HomeController {
    private static final Logger log = Logger.getLogger(HomeController.class.getName());

    private static HomeController controller;
	private static TextView noBulletinsMsg;

    /**
     * Gets the singleton home controller.
     * @return The home controller
     */
    public static HomeController getController() {
        if (controller == null) {
            controller = new HomeController();
        }

        return controller;
    }

    public void createBulletin(final String content, final BulletinContainer container) {
        // Create and run a new thread
        new AsyncTask<Void, Void, Bulletin>() {
            @Override
            protected Bulletin doInBackground(Void... v) {
                log.info(String.format(Locale.US, InfoStrings.CREATE_BULLETIN_CONTROLLER,
                        content));

                Bulletin request = new Bulletin(content);
                Bulletin response = request.createBulletin();
                return response;
            }

            @Override
            protected void onPostExecute(Bulletin result) {
                //if the bulletin returned wasn't null, add it to the container
                if (result != null) {
                    container.addBulletin(result);
	                noBulletinsMsg.setVisibility(View.GONE);
                }
            }
        }.execute();
    }

    public void removeBulletin(final int rowID,
                           final BulletinContainer container) {
        // Create and run a new thread
        new AsyncTask<Void, Void, Bulletin>() {
            @Override
            protected Bulletin doInBackground(Void... v) {
                log.info(String.format(Locale.US, InfoStrings.REMOVE_BULLETIN_CONTROLLER, rowID));

                Bulletin request = new Bulletin();
                return request.removeBulletin(rowID);
            }

            @Override
            protected void onPostExecute(Bulletin result) {
                //if the bulletin returned wasn't null,remove it from the bulletincontainer
                if (result != null) {
                    //remove the bulletin from the BulletinContainer.
                    container.removeBulletin(result);
	                if (container.getBulletins().length == 0)
		                noBulletinsMsg.setVisibility(View.VISIBLE);
                }
            }
        }.execute();

    }

    public void modifyBulletin(final Bulletin theBullToEdit) {
        // Create and run a new thread
        new AsyncTask<Void, Void, Bulletin>() {
            @Override
            protected Bulletin doInBackground(Void... v) {
                log.info(String.format(Locale.US, InfoStrings.MODIFY_BULLETIN_CONTROLLER,
                        theBullToEdit.getContent()));

                Bulletin request = new Bulletin();
                request.modifyBulletin(theBullToEdit);

                return null;
            }

        }.execute();

    }

    public static void populateBulletins(final BulletinContainer container, final TextView msg) {
        // Create and run a new thread
        new AsyncTask<Void, Void, Bulletin[]>() {
            @Override
            protected Bulletin[] doInBackground(Void... v) {
                log.info(String.format(Locale.US, InfoStrings.GET_BULLETINS_CONTROLLER,
                        Group.getActiveGroup().getId()));
				noBulletinsMsg = msg;
                return Group.getActiveGroup().getBulletins();
            }

            @Override
            protected void onPostExecute(Bulletin[] result) {
                if (result != null && result.length != 0) {
	                noBulletinsMsg.setVisibility(View.GONE);
                    for (Bulletin bull : result) {
                            container.addBulletin(bull);
                    }
                }
                else {
                    noBulletinsMsg.setVisibility(View.VISIBLE);
                }
            }

        }.execute();

    }

}
