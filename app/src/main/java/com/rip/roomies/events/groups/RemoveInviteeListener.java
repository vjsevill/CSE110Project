package com.rip.roomies.events.groups;

import android.view.View;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.views.UserContainer;

import java.util.logging.Logger;

/**
 * Created by johndoney on 5/26/16.
 */
public class RemoveInviteeListener implements View.OnClickListener {
	private static final Logger log = Logger.getLogger(RemoveInviteeListener.class.getName());

	private GenericActivity context;
	private UserContainer container;

	public RemoveInviteeListener(GenericActivity context, UserContainer container) {
		this.context = context;
		this.container = container;
	}

	@Override
	public void onClick(View v) {
		container.removeUser();
	}
}
