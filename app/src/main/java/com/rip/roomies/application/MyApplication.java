package com.rip.roomies.application;

import android.app.Application;

/**
 * Created by haotuusa on 6/1/16.
 */
public class MyApplication extends Application {


	public static boolean isActivityVisible() {
		return activityVisible;
	}

	public static void activityResumed() {
		activityVisible = true;
	}

	public static void activityPaused() {
		activityVisible = false;
	}

	private static boolean activityVisible;
}
