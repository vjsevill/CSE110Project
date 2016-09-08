package com.rip.roomies.activities.duties;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.events.duties.AddRotationListener;
import com.rip.roomies.events.duties.CreateDutyListener;
import com.rip.roomies.events.duties.RemoveRotationListener;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.views.UserContainer;
import com.rip.roomies.views.UserSpinner;

import java.util.logging.Logger;

/**
 * Created by Kanurame on 5/19/2016.
 */
public class CreateDuty extends GenericActivity {
	private static final Logger log = Logger.getLogger(CreateDuty.class.getName());
	User blank = new User(-1, "(Select User)", "", "", "", "", null);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Button addDuty;
		Button addUser;
		Button removeUser;
		EditText dutyName;
		EditText desc;
		UserContainer users;
		UserSpinner allUsers;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_duty);

		/* Linking xml objects to java */
		dutyName = (EditText) findViewById(R.id.duty_name);
		desc = (EditText) findViewById(R.id.description);
		allUsers = (UserSpinner) findViewById(R.id.group_users_spinner);
		addUser = (Button) findViewById(R.id.add_user_btn);
		users = (UserContainer) findViewById(R.id.users_container);
		addDuty = (Button) findViewById(R.id.add_duty_btn);
		removeUser = (Button) findViewById(R.id.rem_user_btn);

		allUsers.addUser(blank);
		for(User u : Group.getActiveGroup().getMembers()) {
			allUsers.addUser(u);
		}

		addDuty.setOnClickListener(new CreateDutyListener(this, dutyName, desc, users));
		addUser.setOnClickListener(new AddRotationListener(this, users, allUsers));
		removeUser.setOnClickListener(new RemoveRotationListener(this, users));

	}

}
