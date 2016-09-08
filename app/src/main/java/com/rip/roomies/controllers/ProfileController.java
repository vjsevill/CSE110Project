package com.rip.roomies.controllers;

import android.os.AsyncTask;

import com.rip.roomies.functions.ChangePassFunction;
import com.rip.roomies.functions.UpdateProfileFunction;
import com.rip.roomies.models.User;

import java.util.logging.Logger;

/**
 * Created by VinnysMacOS on 5/29/16.
 */
public class ProfileController {
    private static final Logger log = Logger.getLogger(ProfileController.class.getName());
    private static ProfileController controller;


    public static ProfileController getController() {
        if (controller == null) {
            controller = new ProfileController();
        }
        return controller;
    }


    public void changePassword(final ChangePassFunction funct, final String newPassword, final String oldPassword,
                               final String cfnewPassword) {
        // Create and run a new thread
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                // Debug user entered fields
               // log.info(String.format(Locale.US, InfoStrings.CREATEGROUP_CONTROLLER,
                //        name, description));



                if (new User(User.getActiveUser().getUsername(), oldPassword).login() == null
                        || (!newPassword.equals(cfnewPassword))) {
                    //previous password doesnt match that in database
                    return null;
                }

                return User.getActiveUser().changePassword(newPassword);
            }

            @Override
            protected void onPostExecute(Integer response) {
                // If this call fails, whole thing fails
                if (response == null) {
                    funct.changePassFailure();
                }

                // Otherwise, print success
                else {
                    funct.changePassSuccess();
                }
            }
        }.execute();
    }


    public void updateProfile(final UpdateProfileFunction funct, final String firstName,
                              final String lastName, final String email,
                              final String groupDescription, final byte[] profilePic) {
        // Create and run a new thread
        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... params) {
                // Debug user entered fields
                // log.info(String.format(Locale.US, InfoStrings.CREATEGROUP_CONTROLLER,
                //        name, description));

                // Create request group and get response from createGroup()
                byte[] image = User.getActiveUser().getProfilePic();
                if (profilePic != null) {
                    image = profilePic;
                }

                return User.getActiveUser().updateProfile(firstName, lastName, email,
		                groupDescription, image);
            }

            @Override
            protected void onPostExecute(User response) {
                if (response == null) {
                    funct.updateProfileFailure();
                }
                else {
                    funct.updateProfileSuccess(response);
                }
            }
        }.execute();
    }




}
