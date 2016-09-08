package com.rip.roomies.events.duties;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.DutyController;
import com.rip.roomies.functions.CreateDutyFunction;
import com.rip.roomies.models.Duty;
import com.rip.roomies.models.Group;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.Validation;
import com.rip.roomies.views.UserContainer;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents the listener for when a duty is created.
 */
public class CreateDutyListener implements View.OnClickListener, CreateDutyFunction {
	private static final Logger log = Logger.getLogger(CreateDutyListener.class.getName());

	private EditText name;
	private EditText description;
	private UserContainer users;
	private GenericActivity activity;

	/**
	 * Create Duty Listener Constructor
	 *
	 * @param context  Activity that is using the listener
	 * @param name  The name of this duty as entered by user
	 * @param description   The description given to the duty by the user
	 * @param users The list of users on the duty
	 */
	public CreateDutyListener(GenericActivity context, EditText name, EditText description,
	                          UserContainer users) {
		this.name = name;
		this.description = description;
		this.users = users;
		this.activity = context;
	}

	/**
	 * createDuty.onClickListener
	 *
	 * @param v the View object passed in by ListAllDuties activity
	 */
	@Override
	public void onClick(View v) {
		/*String Buffer for Error Message*/
		StringBuilder errMessage = new StringBuilder();

		/* Check if user entered name*/
		errMessage.append(Validation.validate(name, Validation.ParamType.Other, "Name"));

		/*Check if user entered users on duty*/
		if (users.getUsers().length == 0) {
			errMessage.append(String.format(Locale.US, DisplayStrings.MISSING_FIELD, "Users"));
		}
		/* Check if error occurred*/
		if (errMessage.length() != 0) {
			String errMsg = errMessage.substring(0, errMessage.length() - 1);
			Toast.makeText(activity, errMsg, Toast.LENGTH_SHORT).show();
			return;
		}


		log.info(InfoStrings.CREATE_DUTY_EVENT);

		/* Create Duty Activity*/
		DutyController.getController().createDuty(this, name.getText().toString(),
				description.getText().toString(), Group.getActiveGroup().getId(), users.getUsers());
	}

	@Override
	public void createDutyFail() {
		Toast.makeText(activity, DisplayStrings.CREATE_DUTY_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void createDutySuccess(Duty duty) {
		Intent i = activity.getIntent();
		i.putExtra("Duty", duty);
		activity.setResult(Activity.RESULT_OK, i);
		activity.finish();
	}
}
