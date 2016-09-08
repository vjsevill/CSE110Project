package com.rip.roomies.activities.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.events.profile.ChangePasswordListener;

public class ChangePassword extends GenericActivity {

    private Button btSubmitPassword;
    private EditText etPreviousPassword;
    private EditText etNewPassword;
    private EditText cfNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btSubmitPassword = (Button) findViewById(R.id.settings_submitpassword);
        etPreviousPassword = (EditText) findViewById(R.id.settings_previouspassword);
        etNewPassword = (EditText) findViewById(R.id.settings_newpassword);
        cfNewPassword = (EditText) findViewById(R.id.settings_cfnewpassword);

        btSubmitPassword.setOnClickListener(new ChangePasswordListener(this, etPreviousPassword,
                etNewPassword, cfNewPassword));

    }
}
