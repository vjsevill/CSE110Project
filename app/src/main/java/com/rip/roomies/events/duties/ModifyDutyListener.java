package com.rip.roomies.events.duties;





import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.DutyController;
import com.rip.roomies.functions.ModifyDutyFunction;
import com.rip.roomies.models.Duty;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.Validation;
import com.rip.roomies.views.UserContainer;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents the listener for when a duty is modified.
 */
public class ModifyDutyListener implements View.OnClickListener, ModifyDutyFunction {
	private static final Logger log = Logger.getLogger(CreateDutyListener.class.getName());

	private GenericActivity activity;
	private EditText name;
	private EditText desc;
	private UserContainer users;
	private Duty duty;

	/**
	 * Modify Duty Listener Constructor
	 *
	 * @param context  Activity that is using the listener
	 * @param name The name field
	 * @param desc The description field
	 * @param users The list of users
	 */
	public ModifyDutyListener(GenericActivity context, EditText name,
	                          EditText desc, UserContainer users, Duty duty) {
		this.activity = context;
		this.name = name;
		this.desc = desc;
		this.users = users;
		this.duty = duty;
	}

	/**
	 * modifyDuty.onClickListener
	 *
	 * @param v the View object passed in by ViewDuty activity
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

		log.info(InfoStrings.MODIFY_DUTY_EVENT);

		/* Modify Duty Activity*/
		DutyController.getController().modifyDuty(this, duty.getId(),
				name.getText().toString(), desc.getText().toString(),
				users.getUsers());
	}

	@Override
	public void modifyDutyFail() {
		Toast.makeText(activity, DisplayStrings.MODIFY_DUTY_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void modifyDutySuccess(Duty duty) {
		Intent i = activity.getIntent();
		i.putExtra("Duty", duty);
		i.putExtra("toRemove", false);
		activity.setResult(Activity.RESULT_OK, i);
		activity.finish();
	}
}
