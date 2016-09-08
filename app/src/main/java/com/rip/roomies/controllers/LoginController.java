package com.rip.roomies.controllers;

import android.os.AsyncTask;

import com.rip.roomies.functions.FindUserFunction;
import com.rip.roomies.functions.LoginFunction;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * The controller to handle login related events.
 */
public class LoginController {
	private static final Logger log = Logger.getLogger(LoginController.class.getName());

	private static LoginController controller;

	/**
	 * Gets the singleton login controller.
	 * @return The login controller
	 */
	public static LoginController getController() {
		if (controller == null) {
			controller = new LoginController();
		}

		return controller;
	}

	public void connect() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			public Void doInBackground(Void... v) {
				User.connect();
				//return
				return null;
			}
		}.execute();
	}

	/**
	 * Attempts to login the user with the specified credentials.
	 * @param funct The funct to post results to
	 * @param username The username to check
	 * @param passwd The password to check
	 */
	public void login(final LoginFunction funct, final String username, final String passwd) {
		// Create and run a new thread
		new AsyncTask<Void, Void, User>() {
			@Override
			public User doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.LOGIN_CONTROLLER, username));

				// Create request user and get response from login()
				User request = new User(username, passwd);
				User response = request.login();

				if (response != null) {
					Group[] groups = response.getGroups();
					if (groups != null && groups.length > 0) {
						Group.setActiveGroup(groups[0]);
					}
				}

				//need proper return type
				return response;
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(User response) {
				if (response == null) {
					funct.loginFail();
				}
				else {
					funct.loginSuccess(response);
				}
			}
		}.execute();
	}

	/**
	 * Logs off the user by calling User's static logoff().
	 */
	public void logoff() {
		User.logoff();
		Group.logoff();
	}

	/**
	 * Attempts to retrieve the password of a registered user using the specified email.
	 * @param funct The funct to post results to
	 * @param email The email to attempt to password recover
	 */
	public void passRetrieve(final FindUserFunction funct, final String email) {
		// Create and run a new thread
		new AsyncTask<Void, Void, User>() {
			@Override
			public User doInBackground(Void... v) {
				// Create request user
				User request = new User(0, null, email);

				log.info(String.format(Locale.US, InfoStrings.PASSRETRIEVE_CONTROLLER, email));

				User response = request.findUser();
				//need proper return type
				return response;
			}
			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(User response) {
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
