package com.rip.roomies.activities.goods;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.events.duties.AddRotationListener;
import com.rip.roomies.events.goods.ModifyGoodListener;
import com.rip.roomies.events.goods.RemoveGoodListener;
import com.rip.roomies.events.duties.RemoveRotationListener;
import com.rip.roomies.models.Good;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.views.UserContainer;
import com.rip.roomies.views.UserSpinner;

import java.util.logging.Logger;

/**
 * This activity handles when a user wants to modify a good.
 */
public class ModifyGood extends GenericActivity {
	private static final Logger log = Logger.getLogger(ModifyGood.class.getName());

	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.activity_modify_good);

		Button modifyGood;
		Button addUser;
		Button removeUser;
		Button removeGood;
		EditText goodName;
		EditText desc;
		UserContainer users;
		UserSpinner allUsers;

		/* Linking xml objects to java */
		goodName = (EditText) findViewById(R.id.good_name);
		desc = (EditText) findViewById(R.id.description);
		allUsers = (UserSpinner) findViewById(R.id.group_users_spinner);
		addUser = (Button) findViewById(R.id.add_user_btn);
		removeUser = (Button) findViewById(R.id.rem_user_btn);
		users = (UserContainer) findViewById(R.id.users_container);
		modifyGood = (Button) findViewById(R.id.mod_good_btn);
		removeGood = (Button) findViewById(R.id.rem_good_btn);

		for(User u : Group.getActiveGroup().getMembers()) {
			allUsers.addUser(u);
		}

		// Populate the information
		Good good = getIntent().getExtras().getParcelable("Good");

		if (good != null) {
			goodName.setText(good.getName());
			desc.setText(good.getDescription());

			for (User u : good.getUsers()) {
				users.addUser(u);
			}
		}

		modifyGood.setOnClickListener(new ModifyGoodListener(this, goodName, desc, users, good));
		addUser.setOnClickListener(new AddRotationListener(this, users, allUsers));
		removeUser.setOnClickListener(new RemoveRotationListener(this, users));
		removeGood.setOnClickListener(new RemoveGoodListener(this, good));
	}
}
