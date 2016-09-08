package com.rip.roomies.events.duties;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.DutyController;
import com.rip.roomies.functions.RemoveDutyFunction;
import com.rip.roomies.models.Duty;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents the listener for when a duty is deleted.
 */
public class RemoveDutyListener implements View.OnClickListener, RemoveDutyFunction {
	private static final Logger log = Logger.getLogger(RemoveDutyListener.class.getName());

	private Duty duty;
	private GenericActivity activity;

	/**
	 * Remove Duty Listener Constructor
	 *
	 * @param context  Activity that is using the listener
	 * @param duty  The existing duty object in a view
	 */
	public RemoveDutyListener(GenericActivity context, Duty duty) {
		this.duty = duty;
		this.activity = context;
	}

	/**
	 * removeDuty.onClickListener
	 *
	 * @param v the View object passed in by ViewDuty activity
	 */
	@Override
	public void onClick(View v) {
		/*String Buffer for Error Message*/
		StringBuilder errMessage = new StringBuilder();

		/* Check if duty is null*/
		if (duty == null) {
			errMessage.append(String.format(Locale.US, DisplayStrings.MISSING_FIELD, "Duty"));
		}
		/* Check if error occurred*/
		if (errMessage.length() != 0) {
			String errMsg = errMessage.substring(0, errMessage.length() - 1);
			Toast.makeText(activity, errMsg, Toast.LENGTH_SHORT).show();
			return;
		}

		log.info(InfoStrings.REMOVE_DUTY_EVENT);

		/* Remove Duty Activity*/
		DutyController.getController().removeDuty(this, duty.getId());
	}

	@Override
	public void removeDutyFail() {
		Toast.makeText(activity, DisplayStrings.REMOVE_DUTY_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void removeDutySuccess(Duty duty) {
		Intent i = activity.getIntent();
		i.putExtra("Duty", duty);
		i.putExtra("toRemove", true);
		activity.setResult(Activity.RESULT_OK, i);
		activity.finish();
	}
}
