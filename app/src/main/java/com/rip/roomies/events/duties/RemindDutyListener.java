package com.rip.roomies.events.duties;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.models.Duty;
import com.rip.roomies.server.ServerRequest;
import com.rip.roomies.util.InfoStrings;

import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * Created by haotuusa on 5/26/16.
 */
public class RemindDutyListener implements View.OnClickListener {
	private static final Logger log = Logger.getLogger(RemindDutyListener.class.getName());

	private int receiverID;
	private GenericActivity activity;
	private Duty duty;
	private Button button;
	/**
	 * Complete Duty Listener Constructor
	 *
	 * @param context  Activity that is using the listener
	 * @param receiverID  The ID of the person you want to remind
	 */
	public RemindDutyListener(Button button, GenericActivity context, int receiverID, Duty duty) {
		this.receiverID = receiverID;
		this.activity = context;
		this.duty = duty;
		this.button = button;
	}

	/**
	 * completeDuty.onClickListener
	 *
	 * @param v the View object passed in by ViewDuty activity
	 */
	@Override
	public void onClick(View v) {
		try {
			ServerRequest.remindDuty(duty.getId(), receiverID, duty.getName());
			button.setEnabled(false);
			Toast.makeText(activity.getApplicationContext(), "Reminder Sent, please wait for another 6" +
					" hours to send another one", Toast.LENGTH_SHORT).show();
			button.setBackground(activity.getResources().getDrawable(R.drawable.rec_border_gray));
			button.setTextColor(activity.getResources().getColor(R.color.black_overlay));
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		log.info(InfoStrings.REMIND_DUTY_EVENT);
	}

}
