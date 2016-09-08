package com.rip.roomies.events.duties;

import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.DutyController;
import com.rip.roomies.functions.CompleteDutyFunction;
import com.rip.roomies.models.Duty;
import com.rip.roomies.models.User;
import com.rip.roomies.server.ServerRequest;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;

import java.net.URISyntaxException;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents the listener for when a duty is marked as completed.
 */
public class CompleteDutyListener implements View.OnClickListener {
	private static final Logger log = Logger.getLogger(CompleteDutyListener.class.getName());

	private Duty duty;
	private GenericActivity activity;
	private PopupWindow popupWindow;
	private CompleteDutyFunction funct;

	/**
	 * Complete Duty Listener Constructor
	 *
	 * @param context  Activity that is using the listener
	 * @param duty  The existing duty object in a view
	 */
	public CompleteDutyListener(GenericActivity context, CompleteDutyFunction funct,
	                            Duty duty, PopupWindow popUpWindow) {
		this.duty = duty;
		this.funct = funct;
		this.activity = context;
		this.popupWindow = popUpWindow;
	}

	/**
	 * completeDuty.onClickListener
	 *
	 * @param v the View object passed in by ViewDuty activity
	 */
	@Override
	public void onClick(View v) {

		popupWindow.dismiss();
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

		log.info(InfoStrings.COMPLETE_DUTY_EVENT);
		/* Complete Duty Activity*/
		DutyController.getController().completeDuty(funct, duty.getId());

		try {
			ServerRequest.completeDuty(duty.getId(), User.getActiveUser().getFirstName(), duty.getName());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
