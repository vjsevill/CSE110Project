package com.rip.roomies.controllers;

import android.os.AsyncTask;

import com.rip.roomies.functions.CreateUserFunction;
import com.rip.roomies.functions.FindUserFunction;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.net.URL;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * The controller to handle user related events.
 */
public class UserController {
	private static final Logger log = Logger.getLogger(UserController.class.getName());

	private static UserController controller;

	/**
	 * Gets the singleton user controller.
	 * @return The user controller
	 */
	public static UserController getController() {
		if (controller == null) {
			controller = new UserController();
		}

		return controller;
	}

	/**
	 * Creates a user with the parameters specified and posts results to funct.
	 * @param funct The funct to post the results to
	 * @param firstName The first name of the user to create
	 * @param lastName The last name of the user to create
	 * @param username The username of the user to create
	 * @param email The email of the user to create
	 * @param passwd The password of the user to submit
	 */
	public void createUser(final CreateUserFunction funct, final String firstName, final String lastName,
	                       final String username, final String email, final String passwd) {
		// Create and run a new thread
		new AsyncTask<Void, Void, User>() {
			@Override
			protected User doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.CREATEUSER_CONTROLLER,
						lastName, firstName, username, email));

				// Create request user and get response from createUser()
				User request = new User(firstName, lastName, username, email, passwd);
				User response = request.createUser();
				return response;
			}

			@Override
			protected void onPostExecute(User result) {
				// If fail, call fail callback. Otherwise, call success callback
				if (result == null) {
					funct.createUserFail();
				}
				else {
					funct.createUserSuccess(result);
				}
			}
		}.execute();
	}

	/**
	 * Finds a user using one or more of the three unique parameters given.
	 * @param funct The funct to post results to
	 * @param id The unique ID of the user to find
	 * @param username The unique username of the user to find
	 * @param email The unique email of the user to find
	 */
	public void findUser(final FindUserFunction funct, final int id, final String username, final String email) {
		// Create and run a new thread
		new AsyncTask<Void, Void, User>() {
			@Override
			protected User doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.FIND_USER_CONTROLLER,
						id, username, email));

				// Create request user and get response from createUser()
				User request = new User(id, username, email);
				User response = request.findUser();

				return response;
			}

			@Override
			protected void onPostExecute(User response) {
				// If fail, call fail callback. Otherwise, call success callback
				if (response == null) {
					funct.findUserFail();
				}
				else {
					funct.findUserSuccess(response);
				}
			}
		}.execute();
	}
}
