package com.rip.roomies.events.groups;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.home.Home;
import com.rip.roomies.controllers.GroupController;
import com.rip.roomies.functions.JoinGroupFunction;
import com.rip.roomies.models.Group;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.Validation;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents the listener for when the "Create Group" button is pressed.
 */
public class JoinGroupListener implements View.OnClickListener, JoinGroupFunction {
	private static final Logger log = Logger.getLogger(JoinGroupListener.class.getName());

	private GenericActivity context;
	private EditText name;

	public JoinGroupListener(GenericActivity context, EditText name) {
		this.context = context;
		this.name = name;
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

		log.info(InfoStrings.JOINGROUP_EVENT);

		GroupController.getController().joinGroup(this, name.getText().toString());
	}

	@Override
	public void joinGroupFail() {
		Toast.makeText(context, DisplayStrings.JOIN_GROUP_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void joinGroupSuccess(Group group) {
		log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY_DELAYED,
				Home.class.getName(), DisplayStrings.TOAST_LONG_LENGTH));

		context.toHome();
	}
}
