package com.rip.roomies.activities;

import android.app.Activity;
import android.content.Intent;

import com.rip.roomies.activities.duties.ListAllDuties;
import com.rip.roomies.activities.groups.GroupChoice;
import com.rip.roomies.activities.home.Home;
import com.rip.roomies.activities.login.Login;
import com.rip.roomies.activities.login.SplashScreen;
import com.rip.roomies.application.MyApplication;
import com.rip.roomies.util.InfoStrings;

import java.util.logging.Logger;

/**
 * This class generically represents all Activities that will exist in the Roomies application.
 * Its primary purpose is to define a set of transition methods for moving between activities.
 * These methods can be overriden if some extra functionality is needed.
 */
public abstract class GenericActivity extends Activity {
	private static final Logger log = Logger.getLogger(GenericActivity.class.getName());

	/**
	 * Transitions to the main login page.
	 */
	public void toLogin() {
		log.info(String.format(InfoStrings.SWITCH_ACTIVITY, Login.class.getName()));


		startActivity(new Intent(this, Login.class));
	}

	/**
	 * Transitions to the home screen.
	 */
	public void toHome() {
		log.info(String.format(InfoStrings.SWITCH_ACTIVITY, Home.class.getName()));

		startActivity(new Intent(this, Home.class));
	}

	/**
	 * Transitions to the view all activities screen.
	 */
	public void toAllDuties() {
		log.info(String.format(InfoStrings.SWITCH_ACTIVITY, ListAllDuties.class.getName()));

		startActivity(new Intent(this, ListAllDuties.class));
	}

	public void toGroupChoice(){
		startActivity(new Intent(this, GroupChoice.class));
	}

	public void toSplashScreen(){
		startActivity(new Intent(this, SplashScreen.class));
	}


	@Override
	protected void onResume() {
		super.onResume();
		MyApplication.activityResumed();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MyApplication.activityPaused();
	}
}
