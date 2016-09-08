package com.rip.roomies.activities.goods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.models.Good;
import com.rip.roomies.models.User;
import com.rip.roomies.views.UserContainer;

/**
 * This activity handles when a user wants to view a good.
 */
public class ViewGood extends GenericActivity {
	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.activity_view_good);

		//it's named action good because it might be complete good button or remind button
		Button actionGood;
		Button viewLogs;
		TextView goodName;
		TextView desc;
		UserContainer users;

		/* Linking xml objects to java */
		goodName = (TextView) findViewById(R.id.good_name);
		desc = (TextView) findViewById(R.id.description);
		users = (UserContainer) findViewById(R.id.users_container);
		actionGood = (Button) findViewById(R.id.comp_good_btn);
		viewLogs = (Button) findViewById(R.id.logs_btn);

		// Populate the information
		Good good = getIntent().getExtras().getParcelable("Good");

		if (good != null) {
			goodName.setText(good.getName());
			desc.setText(good.getDescription());

			for (User u : good.getUsers()) {
				users.addUser(u);
			}
		}

		//This is to know who is currently in charge of the good
		User currentAssignee = good.getAssignee();
		/*  Then set the button name to either complete good or remind
			the person depending on the situation*/

		//the case when "I" am the current assignee
		if(currentAssignee.getId() == User.getActiveUser().getId()) {
			//change the button name to complete good
			actionGood.setText("Complete " + good.getName());
			//triggering event of completing the good, go change database, rotation...etc
			//todo implement

			//actionGood.setOnClickListener(new CompleteGoodListener(this, good));
		}
		//the case when another person is the assignee
		else {
			//change the button name to reminding the person
			actionGood.setText("Remind " + currentAssignee.getFirstName());
			//trigger the event of reminding the person
			//todo imlpement
			//actionGood.setOnClickListener(new RemindGoodListener(this, currentAssignee.getId(), good));
		}


		final Activity self = this;

		viewLogs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, ListGoodLogs.class));
			}
		});

	}
}
