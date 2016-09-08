package com.rip.roomies.activities.groups;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.events.groups.JoinGroupListener;

public class JoinGroup extends GenericActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_group);

		EditText name = (EditText) findViewById(R.id.group_name);

		Button submit = (Button) findViewById(R.id.join_group_btn);

		submit.setOnClickListener(new JoinGroupListener(this, name));
	}
}
