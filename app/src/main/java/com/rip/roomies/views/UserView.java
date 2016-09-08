package com.rip.roomies.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.util.logging.Logger;

/**
 * This class is a displayable view that represents a User object. It will display
 * any necessary information as well as style once implemented.
 */
public class UserView extends LinearLayout {
	private static final Logger log = Logger.getLogger(UserView.class.getName());

	private User user;

	/**
	 * @see android.view.View(Context)
	 */
	public UserView(Context context) {
		super(context);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public UserView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public UserView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Get the User object that this class represents
	 *
	 * @return The User object in question
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the user of this object whose information this view will display
	 *
	 * @param user The user object to display
	 */
	public void setUser(User user) {
		this.user = user;
		setupLayout();
	}

	/**
	 * Sets up the layout for this UserView.
	 */
	private void setupLayout() {
		log.info(String.format(InfoStrings.VIEW_SETUP, UserView.class.getSimpleName()));

		setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.VERTICAL);

		TextView name = new TextView(getContext());
		TextView username = new TextView(getContext());
		TextView email = new TextView(getContext());

		String fullName = user.getFirstName() + " " + user.getLastName();
		name.setText(fullName);
		username.setText(user.getUsername());
		email.setText(user.getEmail());

		name.setTextColor(getResources().getColor(R.color.colorPrimary));
		name.setTextSize(20);

		LinearLayout btmBorder = new LinearLayout(getContext());
		LayoutParams btmBorderp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
		btmBorderp.gravity = Gravity.CENTER_HORIZONTAL;
		btmBorder.setLayoutParams(btmBorderp);
		btmBorder.setBackgroundColor(getResources().getColor(R.color.black));

		name.setTextColor(Color.BLACK);
		name.setPadding(50, 50, 50, 50);
		addView(name);
		addView(btmBorder);
	}
}
