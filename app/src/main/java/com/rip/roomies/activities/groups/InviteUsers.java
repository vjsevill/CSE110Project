package com.rip.roomies.activities.groups;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.events.groups.AddInviteeListener;
import com.rip.roomies.events.groups.InviteUserListener;
import com.rip.roomies.events.groups.RemoveInviteeListener;
import com.rip.roomies.views.UserContainer;

import java.util.logging.Logger;

/**
 * Invite users activity
 */
public class InviteUsers extends GenericActivity {
	private static final Logger log = Logger.getLogger(InviteUsers.class.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Button addUser;
		Button removeUser;
		Button inviteUsers;
		EditText inviteeName;
		UserContainer users;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_users);

		/* Linking xml objects to java */
		addUser = (Button) findViewById(R.id.add_user_btn);
		removeUser = (Button) findViewById(R.id.rem_user_btn);
		inviteUsers = (Button) findViewById(R.id.invite_users_btn);
		inviteeName = (EditText) findViewById(R.id.add_user_field);
		users = (UserContainer) findViewById(R.id.invitees_container);

		addUser.setOnClickListener(new AddInviteeListener(this, users, inviteeName));
		removeUser.setOnClickListener(new RemoveInviteeListener(this, users));

		inviteUsers.setOnClickListener(new InviteUserListener(this, users));
	}

	@Override
	public void onBackPressed() {
		this.toHome();
	}
}
