package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.util.logging.Logger;

/**
 * This class represents a drop-down container for user objects so that a user among
 * a group of users can be selected.
 */
public class UserSpinner extends Spinner {
	private static final Logger log = Logger.getLogger(UserSpinner.class.getName());

	private ArrayAdapter<User> users;

	/**
	 * @see android.view.View(Context)
	 */
	public UserSpinner(Context context) {
		super(context);
		users = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
		setAdapter(users);
	}

	/**
	 * @see android.view.View(Context, AttributeSet)
	 */
	public UserSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		users = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
		setAdapter(users);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public UserSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		users = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
		setAdapter(users);
	}

	/**
	 * Adds a new user to the UserContainer at the end of the list.
	 *
	 * @param newUser The new User to add
	 */
	public void addUser(User newUser) {
		log.info(String.format(InfoStrings.CONTAINER_ADD,
				UserView.class.getSimpleName(), UserSpinner.class.getSimpleName()));
		users.add(newUser);
		users.notifyDataSetChanged();
	}

	/**
	 * Get the users held by this UserContainer
	 * @return An array of users
	 */
	public User[] getUsers() {
		User[] temp = new User[users.getCount()];
		for (int i = 0; i < users.getCount(); ++i) {
			temp[i] = users.getItem(i);
		}
		return temp;
	}

	/**
	 * Gets the user selected in this spinner.
	 * @return The selected user
	 */
	public User getSelected() {
		return users.getItem(getSelectedItemPosition());
	}

	public void select(String user) {
		for (int i = 0; i < users.getCount(); ++i) {
			User u = users.getItem(i);
			if (user.equals(u.toString())) {
				setSelection(i, false);
				return;
			}
		}
	}
}
