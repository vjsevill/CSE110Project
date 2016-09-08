package com.rip.roomies.activities.duties;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.events.duties.RemindDutyListener;
import com.rip.roomies.models.Duty;
import com.rip.roomies.models.User;
import com.rip.roomies.views.UserContainer;

import java.util.logging.Logger;

/**
 * This activity is intended for when a user modifies a duty.
 */
public class ViewDuty extends GenericActivity {
	private static final Logger log = Logger.getLogger(ModifyDuty.class.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_duty);

		//it's named action duty because it might be complete duty button or remind button
		Button actionDuty;
		TextView dutyName;
		TextView desc;
		UserContainer users;

		/* Linking xml objects to java */
		dutyName = (TextView) findViewById(R.id.duty_name);
		desc = (TextView) findViewById(R.id.description);
		users = (UserContainer) findViewById(R.id.users_container);
		actionDuty = (Button) findViewById(R.id.comp_duty_btn);

		dutyName.setTextColor(getResources().getColor(R.color.colorPrimary));
		dutyName.setTypeface(null, Typeface.BOLD);

		desc.setTextColor(Color.BLACK);

		// Populate the information
		Duty duty = getIntent().getExtras().getParcelable("Duty");

		if (duty != null) {
			dutyName.setText(duty.getName());
			desc.setText(duty.getDescription());

			for (User u : duty.getUsers()) {
				users.addUser(u);
			}
		}

		//This is to know who is currently in charge of the duty
		User currentAssignee = duty.getAssignee();
		/*  Then set the button name to either complete duty or remind
			the person depending on the situation*/

		//the case when "I" am the current assignee
		if(currentAssignee.getId() == User.getActiveUser().getId()) {
			//change the button name to complete duty
			actionDuty.setText("Complete " + duty.getName());
			//triggering event of completing the duty, go change database, rotation...etc

			/* Retrieving the popup Layout to use */
			int popUpID = R.layout.activity_confirm_duty_comp;

			/* Start popup windown upon click*/
			//actionDuty.setOnClickListener(new PopUpDutyListener(this, actionDuty, popUpID, duty));

			//actionDuty.setOnClickListener(new CompleteDutyListener(this, duty));

		}
		//the case when another person is the assignee
		else {
			//change the button name to reminding the person
			actionDuty.setText("Remind " + currentAssignee.getFirstName());
			//trigger the event of reminding the person
			actionDuty.setOnClickListener(new RemindDutyListener(actionDuty, this, currentAssignee.getId(), duty));
		}
	}

}
