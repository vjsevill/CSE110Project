package com.rip.roomies.events.duties;

import android.view.View;
import android.widget.Spinner;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.models.User;
import com.rip.roomies.views.UserContainer;

import java.util.logging.Logger;

/**
 * Created by johndoney on 5/23/16.
 */
public class AddRotationListener implements View.OnClickListener {
	private static final Logger log = Logger.getLogger(AddRotationListener.class.getName());

	private GenericActivity context;
	private UserContainer container;
	private Spinner spinner;

	public AddRotationListener(GenericActivity context, UserContainer container, Spinner spinner) {
		this.context = context;
		this.container = container;
		this.spinner = spinner;
	}

	@Override
	public void onClick(View v) {
		container.addUser((User) spinner.getSelectedItem());
	}
}
