package com.rip.roomies.controllers;

import android.os.AsyncTask;

import com.rip.roomies.functions.CompleteGoodFunction;
import com.rip.roomies.functions.CreateGoodFunction;
import com.rip.roomies.functions.ListAllGoodsFunction;
import com.rip.roomies.functions.ListGoodLogsFunction;
import com.rip.roomies.functions.ListMyGoodsFunction;
import com.rip.roomies.functions.ModifyGoodFunction;
import com.rip.roomies.functions.RemoveGoodFunction;
import com.rip.roomies.models.Good;
import com.rip.roomies.models.GoodLog;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by johndoney on 5/29/16.
 */
public class GoodController {
	private static final Logger log = Logger.getLogger(GoodController.class.getName());
	private static GoodController controller;

	public static GoodController getController() {
		if (controller == null) {
			controller = new GoodController();
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
	public void createGood(final CreateGoodFunction funct, final String name, final String
			description, final int groupId, final User[] users) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Good>() {
			@Override
			public Good doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.CREATE_GOOD_CONTROLLER, name,
						description, groupId, users.length));

				// Create request user and get response from login()
				Good request = new Good(name, description, groupId, users);
				return request.create();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Good response) {
				if (response == null) {
					funct.createGoodFail();
				}
				else {
					funct.createGoodSuccess(response);
				}
			}
		}.execute();
	}

	/**
	 * Attempts to display all duties for this group.
	 * @param funct The funct to post results to
	 */
	public void listAllGoods(final ListAllGoodsFunction funct) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Good[]>() {
			@Override
			public Good[] doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.GET_GROUP_GOODS_CONTROLLER,
						Group.getActiveGroup().getId()));

				// Create request user and get response from login()
				return Group.getActiveGroup().getGoods();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Good[] response) {
				if (response == null) {
					funct.listAllGoodsFail();
				}
				else {
					funct.listAllGoodsSuccess(response);
				}
			}
		}.execute();
	}

	public void GoodLog(final ListGoodLogsFunction funct)
	{
		new AsyncTask<Void, Void, GoodLog[]>() {
			@Override
			public GoodLog[] doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.GET_GROUP_GOOD_LOGS_CONTROLLER,
						Group.getActiveGroup().getId()));

				// Create request user and get response from login()
				return Group.getActiveGroup().getGoodLogs();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(GoodLog[] response) {
				if (response == null) {
					funct.ListGoodLogsFail();
				}
				else {
					funct.ListGoodLogsSuccess(response);
				}
			}
		}.execute();
	}

	/**
	 * Attempts to display all duties for this group.
	 * @param funct The funct to post results to
	 */
	public void listMyGoods(final ListMyGoodsFunction funct) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Good[]>() {
			@Override
			public Good[] doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.GET_USER_GOODS_CONTROLLER,
						User.getActiveUser().getId(), Group.getActiveGroup().getId()));

				// Create request user and get response from login()
				return User.getActiveUser().getGoods(Group.getActiveGroup());
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Good[] response) {
				if (response == null) {
					funct.listMyGoodsFail();
				}
				else {
					funct.listMyGoodsSuccess(response);
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
	public void modifyGoods(final ModifyGoodFunction funct, final int id, final String name, final
	String description, final User[] users) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Good>() {
			@Override
			public Good doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.MODIFY_GOOD_CONTROLLER, id, name,
						description));

				// Create request user and get response from login()
				Good request = new Good(id, name, description, users);
				return request.modify();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Good response) {
				if (response == null) {
					funct.modifyGoodFail();
				}
				else {
					funct.modifyGoodSuccess(response);
				}
			}
		}.execute();
	}

	/**
	 * Attempts to remove the duty with the specified id.
	 * @param funct The funct to post results to
	 * @param id The unique id of this duty in the database
	 */
	public void removeGood(final RemoveGoodFunction funct, final int id) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Good>() {
			@Override
			public Good doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.REMOVE_GOOD_CONTROLLER, id));

				// Create request user and get response from login()
				Good request = new Good(id);
				return request.remove();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Good response) {
				if (response == null) {
					funct.removeGoodFail();
				}
				else {
					funct.removeGoodSuccess(response);
				}
			}
		}.execute();
	}

	/**
	 * Attempts to mark the duty with the specified id as completed.
	 * @param funct The funct to post results to
	 * @param id The unique id of this duty in the database
	 */
	public void completeGood(final CompleteGoodFunction funct, final int id, final double amount) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Good>() {
			@Override
			public Good doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.COMPLETE_GOOD_CONTROLLER, id));

				// Create request user and get response from login()
				Good request = new Good(id);
				return request.complete(amount);
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Good response) {
				if (response == null) {
					funct.completeGoodFail();
				}
				else {
					funct.completeGoodSuccess(response);
				}
			}
		}.execute();
	}

	/*
	public void completeGood(final CompleteGoodFunction funct, final int id) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Good>() {
			@Override
			public Good doInBackground(Void... v) {
				log.info(String.format(Locale.US, InfoStrings.COMPLETE_GOOD_CONTROLLER, id));

				// Create request user and get response from login()
				Good request = new Good(id);
				return request.complete();
			}

			// If fail, call fail callback. Otherwise, call success callback
			@Override
			public void onPostExecute(Good response) {
				if (response == null) {
					funct.completeGoodFail();
				}
				else {
					funct.completeGoodSuccess(response);
				}
			}
		}.execute();
	}*/
}
