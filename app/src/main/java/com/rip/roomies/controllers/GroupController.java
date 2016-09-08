package com.rip.roomies.controllers;

import android.os.AsyncTask;

import com.rip.roomies.functions.InviteUsersFunction;
import com.rip.roomies.functions.CreateGroupFunction;
import com.rip.roomies.functions.JoinGroupFunction;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * The controller to handle group related events.
 */
public class GroupController {
	private static final Logger log = Logger.getLogger(GroupController.class.getName());

	private static GroupController controller;

	/**
	 * Gets the static controller singleton object for this controller.
	 * @return The singleton controller
	 */
	public static GroupController getController() {
		if (controller == null) {
			controller = new GroupController();
		}

		return controller;
	}

	/**
	 * Creates a new group with the parameters specified, and adds the current user
	 * along with all the invitees afterwards.
	 * @param funct The funct to post the results to
	 * @param name The name of the group to create
	 * @param description The description of the group to create
	 */
	public void createGroup(final CreateGroupFunction funct, final String name,
	                        final String description) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Group>() {
			@Override
			protected Group doInBackground(Void... params) {
				// Debug user entered fields
				log.info(String.format(Locale.US, InfoStrings.CREATEGROUP_CONTROLLER,
						name, description));

				// Create request group and get response from createGroup()
				Group request = new Group(name, description);
				Group response = request.createGroup(User.getActiveUser());

				if (response != null) {
					Group.setActiveGroup(response);
					response = Group.getActiveGroup();
				}

				return response;
			}

			@Override
			protected void onPostExecute(Group response) {
				// If this call fails, whole thing fails
				if (response == null) {
					funct.createGroupFail();
				}

				// Otherwise, print success
				else {
					funct.createGroupSuccess(response);
				}
			}
		}.execute();
	}

	public void addUsersToGroup(final InviteUsersFunction funct, final User[] users) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Group>() {
			@Override
			public Group doInBackground(Void... v) {
				// Debug users to add
				String usersString = "[";
				for (int i = 0; i < users.length; ++i) {
					usersString += users[i].getFirstName() + " " + users[i].getLastName();

					if (i != users.length - 1) {
						usersString += ", ";
					}
				}
				usersString += "]";

				Group group = Group.getActiveGroup();

				log.info(String.format(Locale.US, InfoStrings.ADD_USERS_TO_GROUP_CONTROLLER,
						group.getId(), group.getName(), usersString));

				// Add users to the group specified
				Group response = group.addUsers(users);

				if (response != null) {
					Group.setActiveGroup(response);
					response = Group.getActiveGroup();
				}

				return response;
			}

			@Override
			public void onPostExecute(Group group) {
				if (group == null) {
					funct.inviteUsersFail();
				}
				else {
					funct.inviteUsersSuccess(group);
				}
			}
		}.execute();
	}

	public void joinGroup(final JoinGroupFunction funct, final String name) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Group>() {
			@Override
			public Group doInBackground(Void... v) {
				Group group = new Group(name, "");
				// Look for the group name that was inputted, if non-existent, fail.
				Group databaseGroup = group.findGroup();
				if (databaseGroup == null)
					return null;

				// If the user is logged in, add them to the database
				User activeUser = User.getActiveUser();
				if (activeUser != null)

					databaseGroup = databaseGroup.addUsers(activeUser);

				if (databaseGroup != null) {
					Group.setActiveGroup(databaseGroup);
				}

				return databaseGroup;
			}

			@Override
			public void onPostExecute(Group group) {
				if (group == null)
					funct.joinGroupFail();
				else {
					funct.joinGroupSuccess(group);
				}
			}
		}.execute();
	}
}
