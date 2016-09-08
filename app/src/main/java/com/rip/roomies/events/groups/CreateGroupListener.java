package com.rip.roomies.events.groups;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.groups.InviteUsers;
import com.rip.roomies.controllers.GroupController;
import com.rip.roomies.functions.CreateGroupFunction;
import com.rip.roomies.models.Group;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.Validation;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents the listener for when the "Create Group" button is pressed.
 */
public class CreateGroupListener implements View.OnClickListener, CreateGroupFunction {
	private static final Logger log = Logger.getLogger(CreateGroupListener.class.getName());

	private GenericActivity context;
	private EditText name;
	private EditText description;

	public CreateGroupListener(GenericActivity context, EditText name, EditText description) {
		this.context = context;
		this.name = name;
		this.description = description;
	}

	@Override
	public void onClick(View v) {
		String errMsg = "";

		errMsg += Validation.validate(name, Validation.ParamType.Other, "Name");

		if (!errMsg.isEmpty()) {
			errMsg = errMsg.substring(0, errMsg.length() - 1);
			Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show();
			return;
		}

		log.info(InfoStrings.CREATEGROUP_EVENT);

		GroupController.getController().createGroup(this, name.getText().toString(),
				description.getText().toString());
	}

	@Override
	public void createGroupFail() {
		Toast.makeText(context, DisplayStrings.CREATE_GROUP_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void createGroupSuccess(Group group) {
		log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY_DELAYED,
				InviteUsers.class.getName(), DisplayStrings.TOAST_LONG_LENGTH));

		context.toHome();
	}
}
