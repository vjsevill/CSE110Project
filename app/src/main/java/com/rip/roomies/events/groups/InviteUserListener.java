package com.rip.roomies.events.groups;

import android.view.View;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.GroupController;
import com.rip.roomies.functions.InviteUsersFunction;
import com.rip.roomies.models.Group;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.views.UserContainer;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents the listener for when the "Send Invites" button is pressed.
 */
public class InviteUserListener implements View.OnClickListener, InviteUsersFunction {
	private static final Logger log = Logger.getLogger(InviteUserListener.class.getName());

	private GenericActivity context;
	private UserContainer container;

	public InviteUserListener(GenericActivity context, UserContainer container) {
		this.context = context;
		this.container = container;
	}

	@Override
	public void onClick(View v) {

		log.info(String.format(Locale.US, InfoStrings.INVITE_USERS));

		if(container.getUsers().length > 0) {
			GroupController.getController().addUsersToGroup(this, container.getUsers());
		}
		else {
			Toast.makeText(context, String.format(Locale.US, DisplayStrings.MISSING_FIELD, "Users"),
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void inviteUsersFail() {
		Toast.makeText(context, DisplayStrings.INVITE_USERS_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void inviteUsersSuccess(Group group) { context.finish(); }
}
