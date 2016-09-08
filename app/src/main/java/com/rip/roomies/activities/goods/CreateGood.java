package com.rip.roomies.activities.goods;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.events.duties.AddRotationListener;
import com.rip.roomies.events.duties.CreateDutyListener;
import com.rip.roomies.events.duties.RemoveRotationListener;
import com.rip.roomies.events.goods.CreateGoodListener;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.views.UserContainer;
import com.rip.roomies.views.UserSpinner;

import java.util.logging.Logger;

/**
 * Created by johndoney on 5/30/16.
 */
public class CreateGood extends GenericActivity {
	private static final Logger log = Logger.getLogger(CreateGood.class.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Button addGood;
		Button addUser;
		Button removeUser;
		EditText goodName;
		EditText desc;
		UserContainer users;
		UserSpinner allUsers;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_shared_goods);

		/* Linking xml objects to java */
		goodName = (EditText) findViewById(R.id.good_name);
		desc = (EditText) findViewById(R.id.description);
		allUsers = (UserSpinner) findViewById(R.id.group_users_spinner);
		addUser = (Button) findViewById(R.id.add_user_btn);
		users = (UserContainer) findViewById(R.id.users_container);
		addGood = (Button) findViewById(R.id.add_good_btn);
		removeUser = (Button) findViewById(R.id.rem_user_btn);

		for(User u : Group.getActiveGroup().getMembers()) {
			allUsers.addUser(u);
		}

		addGood.setOnClickListener(new CreateGoodListener(this, goodName, desc, users));
		addUser.setOnClickListener(new AddRotationListener(this, users, allUsers));
		removeUser.setOnClickListener(new RemoveRotationListener(this, users));

	}

}
