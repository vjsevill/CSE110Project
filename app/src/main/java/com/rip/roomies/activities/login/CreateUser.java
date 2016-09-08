package com.rip.roomies.activities.login;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.events.login.CreateUserListener;
import com.rip.roomies.util.Images;

import java.util.logging.Logger;

public class CreateUser extends GenericActivity {
	private static final Logger log = Logger.getLogger(CreateUser.class.getName());
	private static final double IMAGE_WIDTH_RATIO = 3.0 / 10;
	private static final double IMAGE_HEIGHT_RATIO = 2.0 / 25;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Button createUser;
		EditText firstName;
		EditText lastName;
		EditText username;
		EditText email;
		EditText password;
		EditText confirmPassword;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);

		/* Linking xml objects to java */
		firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		username = (EditText) findViewById(R.id.username);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		confirmPassword = (EditText) findViewById(R.id.confirmPass);
		createUser = (Button) findViewById(R.id.btnSubmit);

		createUser.setOnClickListener(new CreateUserListener(this, firstName, lastName,
				username, email, password, confirmPassword));

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		ImageView logo = (ImageView) findViewById(R.id.appname2);
		logo.setImageBitmap(Images.getScaledDownBitmap(getResources(), R.mipmap.logo2,
				(int) (size.x * IMAGE_WIDTH_RATIO), (int) (size.y * IMAGE_HEIGHT_RATIO)));
	}
}
