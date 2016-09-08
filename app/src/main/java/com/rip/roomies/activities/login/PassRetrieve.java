package com.rip.roomies.activities.login;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.events.login.PassRetrieveListener;
import com.rip.roomies.util.Images;

import java.util.logging.Logger;

public class PassRetrieve extends GenericActivity {
	private static final Logger log = Logger.getLogger(PassRetrieve.class.getName());
	private static final double IMAGE_WIDTH_RATIO = 3.0 / 10;
	private static final double IMAGE_HEIGHT_RATIO = 2.0 / 25;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pass_retrieve);

		EditText email = (EditText) findViewById(R.id.pass_retrieve_email);
		Button submit = (Button) findViewById(R.id.pass_retrieve_submit);

		submit.setOnClickListener(new PassRetrieveListener(this, email));

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		ImageView logo = (ImageView) findViewById(R.id.appname);
		logo.setImageBitmap(Images.getScaledDownBitmap(getResources(), R.mipmap.logo2,
				(int) (size.x * IMAGE_WIDTH_RATIO), (int) (size.y * IMAGE_HEIGHT_RATIO)));
	}
}
