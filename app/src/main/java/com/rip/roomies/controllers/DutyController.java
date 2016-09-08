package com.rip.roomies.controllers;

import android.os.AsyncTask;

import com.rip.roomies.functions.CompleteDutyFunction;
import com.rip.roomies.functions.CreateDutyFunction;
import com.rip.roomies.functions.ListAllDutiesFunction;
import com.rip.roomies.functions.ListDutyLogsFunction;
import com.rip.roomies.functions.ListMyTasksFunction;
import com.rip.roomies.functions.ModifyDutyFunction;
import com.rip.roomies.functions.RemoveDutyFunction;
import com.rip.roomies.models.Duty;
import com.rip.roomies.models.DutyLog;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.Task;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * A class that is designed to handle operations revolving around duties.
 */
public class DutyController {
	private static final Logger log = Logger.getLogger(DutyController.class.getName());
	private static DutyController controller;

	public static DutyController getController() {
		if (controller == null) {
			controller = new DutyController();
		}

		return controller;
	}

	/**
	 * Attempts to create a duty with the specified parameters.
	 * @param funct The funct to post results to
	 * @param name The name of the duty in the database
	 * @param description The description of the duty
	 * @param groupId The ID of the group this duty is associated with
	 * @param users The list of Users on the rotation for this duty
	 */
	public void createDuty(final CreateDutyFunction funct, final String name, final String
			description, final int groupId, final User[] users) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Duty>() {
			@Override
			public Duty doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.CREATE_DUTY_CONTROLLER, name,
						description, groupId, users.length));

				// Create request user and get response from login()
				Duty request = new Duty(name, description, groupId, users);
				return request.create();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Duty response) {
				if (response == null) {
					funct.createDutyFail();
				}
				else {
					funct.createDutySuccess(response);
				}
			}
		}.execute();
	}

	/**
	 * Attempts to display all duties for this group.
	 * @param funct The funct to post results to
	 */
	public void listAllDuties(final ListAllDutiesFunction funct) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Duty[]>() {
			@Override
			public Duty[] doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.GET_GROUP_DUTIES_CONTROLLER,
						Group.getActiveGroup().getId()));

				// Create request user and get response from login()
				return Group.getActiveGroup().getDuties();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Duty[] response) {
				if (response == null) {
					funct.listAllDutiesFail();
				}
				else {
					funct.listAllDutiesSuccess(response);
				}
			}
		}.execute();
	}

	public void DutyLog(final ListDutyLogsFunction funct)
	{
		new AsyncTask<Void, Void, DutyLog[]>() {
			@Override
			public DutyLog[] doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.GET_GROUP_DUTIES_CONTROLLER,
						Group.getActiveGroup().getId()));

				// Create request user and get response from login()
				return Group.getActiveGroup().getDutyLogs();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(DutyLog[] response) {
				if (response == null) {
					funct.ListDutyLogsFail();
				}
				else {
					funct.ListDutyLogsSuccess(response);
				}
			}
		}.execute();
	}

	/**
	 * Attempts to display all tasks for this user within the group context.
	 * @param funct The funct to post results to
	 */
	public void listMyTasks(final ListMyTasksFunction funct) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Task[]>() {
			@Override
			public Task[] doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.GET_USER_TASKS_CONTROLLER,
						User.getActiveUser().getId(), Group.getActiveGroup().getId()));

				// Create request user and get response from login()
				return User.getActiveUser().getTasks(Group.getActiveGroup());
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Task[] response) {
				if (response == null) {
					funct.listMyTasksFail();
				}
				else {
					funct.listMyTasksSuccess(response);
				}
			}
		}.execute();
	}

	/**
	 * Attempts to modify the duty with the specified parameters.
	 * @param funct The funct to post results to
	 * @param id The unique id of this duty in the database
	 * @param name The name of the duty in the database
	 * @param description The description of the duty
	 * @param users The list of Users on the rotation for this duty
	 */
	public void modifyDuty(final ModifyDutyFunction funct, final int id, final String name, final
		String description, final User[] users) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Duty>() {
			@Override
			public Duty doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.MODIFY_DUTY_CONTROLLER, id, name,
						description));

				// Create request user and get response from login()
				Duty request = new Duty(id, name, description, users);
				return request.modify();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Duty response) {
				if (response == null) {
					funct.modifyDutyFail();
				}
				else {
					funct.modifyDutySuccess(response);
				}
			}
		}.execute();
	}

	/**
	 * Attempts to remove the duty with the specified id.
	 * @param funct The funct to post results to
	 * @param id The unique id of this duty in the database
	 */
	public void removeDuty(final RemoveDutyFunction funct, final int id) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Duty>() {
			@Override
			public Duty doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.REMOVE_DUTY_CONTROLLER, id));

				// Create request user and get response from login()
				Duty request = new Duty(id);
				return request.remove();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Duty response) {
				if (response == null) {
					funct.removeDutyFail();
				}
				else {
					funct.removeDutySuccess(response);
				}
			}
		}.execute();
	}

	/**
	 * Attempts to mark the duty with the specified id as completed.
	 * @param funct The funct to post results to
	 * @param id The unique id of this duty in the database
	 */
	public void completeDuty(final CompleteDutyFunction funct, final int id) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Duty>() {
			@Override
			public Duty doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.COMPLETE_DUTY_CONTROLLER, id));

				// Create request user and get response from login()
				Duty request = new Duty(id);
				return request.complete();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Duty response) {
				if (response == null) {
					funct.completeDutyFail();
				}
				else {
					funct.completeDutySuccess(response);
				}
			}
		}.execute();
	}
}
