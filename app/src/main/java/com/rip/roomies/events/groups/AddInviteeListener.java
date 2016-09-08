package com.rip.roomies.events.groups;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.UserController;
import com.rip.roomies.functions.FindUserFunction;
import com.rip.roomies.models.User;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.Validation;
import com.rip.roomies.views.UserContainer;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents the listener for when the "+" button is pressed on the create room page.
 */
public class AddInviteeListener implements View.OnClickListener, FindUserFunction {
	private static final Logger log = Logger.getLogger(AddInviteeListener.class.getName());

	private GenericActivity context;
	private UserContainer container;
	private EditText username;

	public AddInviteeListener(GenericActivity context, UserContainer container, EditText username) {
		this.context = context;
		this.container = container;
		this.username = username;
	}

	@Override
	public void onClick(View v) {
		String errMsg = "";

		errMsg += Validation.validate(username, Validation.ParamType.Identifier, "Invitee Username");

		if (!errMsg.isEmpty()) {
			errMsg = errMsg.substring(0, errMsg.length() - 1);
			Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show();
			return;
		}

		log.info(InfoStrings.FIND_USER_EVENT);

		UserController.getController().findUser(this, 0, username.getText().toString(), null);
	}

	@Override
	public void findUserFail() {
		Toast.makeText(context, DisplayStrings.FIND_USER_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void findUserSuccess(User user) {
		if (user.getGroupId() != 0) {
			findUserFail();
			return;
		}

		container.addUser(user);
	}
}
