package com.rip.roomies.activities.bulletin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;

public class AddBulletin extends GenericActivity {

	private EditText content;
	private Button submit;
	/*Tag to differentiate which screen are we coming from 2=ADD BULLETIN*/
	private final int RESULT_CODE_ADD_BULLETIN = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_bulletin);

		//link xml objects to java
		content = (EditText) findViewById(R.id.addBulletinContent);
		submit = (Button) findViewById(R.id.addBulletin_send);

		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String updatedAmount;
				//if parseARgs returns false, means user entered in something wrong.
				if (!parseArgs(content.getText().toString())) {
					return;
				}
				//pass the 3 fields back to activities.home.Home
				Intent intent = new Intent();
				intent.putExtra("Key_New_Content", content.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}

		});
	}

	/**
	 * @param content
	 * @return true if parseArgs failed, ie the user didnt enter in something.
	 */
	public boolean parseArgs(String content) {
		if (content.equals("") || content.trim().length()==0) {
			Toast.makeText(getApplicationContext(), "Make sure all fields are filled.",
					Toast.LENGTH_LONG).show();
			return false;
		}

		if (content.length() > 250) {
			Toast.makeText(getApplicationContext(), "Please keep your message below 250 characters.",
					Toast.LENGTH_LONG).show();
			return false;
		}

		return true;
	}
}
