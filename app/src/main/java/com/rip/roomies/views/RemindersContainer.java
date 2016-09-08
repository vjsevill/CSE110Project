package com.rip.roomies.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rip.roomies.models.User;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class represents a container for multiple UserView objects that can
 * be displayed in a dynamic group.
 */
public class RemindersContainer extends ScrollView {
	private static final Logger log = Logger.getLogger(UserContainer.class.getName());

	private ArrayList<User> users = new ArrayList<>();
	private LinearLayout userLayout;

	/**
	 * @see android.view.View(Context)
	 */
	public RemindersContainer(Context context) {
		super(context);
		userLayout = new LinearLayout(context);
		userLayout.setOrientation(LinearLayout.VERTICAL);
		addView(userLayout);
	}
}
