package com.rip.roomies.activities.login;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.application.SaveSharedPreference;
import com.rip.roomies.controllers.LoginController;
import com.rip.roomies.functions.LoginFunction;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.Images;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

public class SplashScreen extends GenericActivity implements LoginFunction{
	private static final Logger log = Logger.getLogger(SplashScreen.class.getName());
	private static final double IMAGE_WIDTH_RATIO = 3.0 / 10;
	private static final double IMAGE_HEIGHT_RATIO = 2.0 / 25;
	private Activity activity;
	private LoginFunction func;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash_screen);

		activity = this;
		func = this;
		LoginController.getController().connect();

		log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY_DELAYED,
				Login.class.getName(), DisplayStrings.TOAST_LONG_LENGTH));

		// Delayed switch to login screen
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				String username = SaveSharedPreference.getUsername(activity);
				String password = SaveSharedPreference.getPassword(activity);
				if(username.length() != 0 && password.length() != 0) {
					LoginController.getController().login(func, username, password);
				}
				else {
					toLogin();
				}
			}
		}, DisplayStrings.TOAST_LONG_LENGTH);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		ImageView logo = (ImageView) findViewById(R.id.appname);
		logo.setImageBitmap(Images.getScaledDownBitmap(getResources(), R.mipmap.logowhite,
				(int) (size.x * IMAGE_WIDTH_RATIO), (int) (size.y * IMAGE_HEIGHT_RATIO)));


	}
	@Override
	public void loginFail() {
		Toast.makeText(this, DisplayStrings.LOGIN_FAIL, Toast.LENGTH_LONG).show();
		toLogin();
	}

	@Override
	public void loginSuccess(User user) {
		if (Group.getActiveGroup() == null) {
			toGroupChoice();
		}
		else {
			toHome();
		}
	}

}