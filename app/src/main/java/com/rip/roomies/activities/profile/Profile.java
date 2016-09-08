package com.rip.roomies.activities.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.groups.InviteUsers;
import com.rip.roomies.events.profile.EditProfileListener;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.Exceptions;
import com.rip.roomies.util.Images;

import java.util.logging.Logger;

public class Profile extends GenericActivity implements View.OnClickListener {
	private static final Logger log = Logger.getLogger(Profile.class.getName());
	private static final double IMAGE_WIDTH_RATIO = 1.0 / 3;
    private static final double IMAGE_HEIGHT_RATIO = 1.0 / 5;
	private static final int SELECT_IMAGE = 1;

    private TextView tvTapToEdit;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etGroupDescription;
    private EditText etGroupName;
    private Button btChangePassword;
    private Button btSaveChanges;
	private View btm_divider;
    //private Button btLeaveGroup;
    private User thisUser;
    private Group thisUsersGroup;
    private ImageView userProfile;

	private int imageWidth;
	private int imageHeight;

	private EditProfileListener epListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //assures the keyboard only pops up when an edit text is clicked
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //xml->java
        tvTapToEdit = (TextView) findViewById(R.id.settings_tap_hint);
        etFirstName = (EditText) findViewById(R.id.settings_firstname);
        etLastName = (EditText) findViewById(R.id.settings_lastname);
        etEmail = (EditText) findViewById(R.id.settings_email);
        etGroupDescription = (EditText) findViewById(R.id.settings_description);
        etGroupName = (EditText) findViewById(R.id.settings_gname);
        btChangePassword = (Button) findViewById(R.id.settings_changepassword);
        btSaveChanges = (Button) findViewById(R.id.settings_submitbtn);
	    btm_divider = (View) findViewById(R.id.settings_bottom_divider);
        //btLeaveGroup = (Button) findViewById(R.id.settings_leavebtn);
        userProfile = (ImageView) findViewById(R.id.settings_user_profile);


        //lock the editable fields
        etFirstName.setEnabled(false); etLastName.setEnabled(false);
        etEmail.setEnabled(false); etGroupDescription.setEnabled(false);
        etGroupName.setEnabled(false);

        //Load the information for this user
        thisUser = User.getActiveUser();
        thisUsersGroup = Group.getActiveGroup();

        if (thisUser != null && thisUsersGroup != null) {
            etFirstName.setText(thisUser.getFirstName());
            etLastName.setText(thisUser.getLastName());
            etEmail.setText(thisUser.getEmail());
            etGroupDescription.setText(thisUsersGroup.getDescription());
            etGroupName.setText(thisUsersGroup.getName());
        }

	    Display display = getWindowManager().getDefaultDisplay();
	    Point size = new Point();
	    display.getSize(size);

	    imageWidth = (int) (size.x * IMAGE_WIDTH_RATIO);
	    imageHeight = (int) (size.y * IMAGE_HEIGHT_RATIO);

	    if (thisUser == null || thisUser.getProfilePic() == null) {
		    userProfile.setImageBitmap(Images.getScaledDownBitmap(getResources(),
				    R.mipmap.default_user_image, imageWidth, imageHeight));
	    }
	    else {
		    userProfile.setImageBitmap(BitmapFactory.decodeByteArray(thisUser.getProfilePic(),
				    0, thisUser.getProfilePic().length));
	    }

        etFirstName.setBackgroundColor(Color.WHITE);
        //etFirstName.setBackgroundColor(Color.WHITE);
        etLastName.setBackgroundColor(Color.WHITE);
        etEmail.setBackgroundColor(Color.WHITE);
        etGroupDescription.setBackgroundColor(Color.WHITE);
        etGroupName.setBackgroundColor(Color.WHITE);


        //set the listeners for the leavegroup button/submit changes button
	    epListener = new EditProfileListener(this, etFirstName, etLastName,
			    etEmail, etGroupDescription, userProfile, imageWidth,
			    imageHeight);
        btSaveChanges.setOnClickListener(epListener);
        //btLeaveGroup.setOnClickListener(new LeaveGroupListener(this));
        btChangePassword.setOnClickListener(this);


        tvTapToEdit.setOnClickListener(this);

	    final Activity self = this;
	    userProfile.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    Intent intent = new Intent(Intent.ACTION_PICK);
			    intent.setType("image/*");
			    self.startActivityForResult(intent, SELECT_IMAGE);
		    }
	    });

	    Button inviteUsers = (Button) findViewById(R.id.settings_inviteusers_btn);
		inviteUsers.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    self.startActivity(new Intent(self, InviteUsers.class));
		    }
	    });

    }


    /**
     * IS called when the taptoedit textview is pressed on this view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.settings_tap_hint:
                //change background color to light gray
                etFirstName.setBackgroundColor(Color.LTGRAY);
                etLastName.setBackgroundColor(Color.LTGRAY);
                etEmail.setBackgroundColor(Color.LTGRAY);
                etGroupDescription.setBackgroundColor(Color.LTGRAY);

                etFirstName.setEnabled(true);
                etLastName.setEnabled(true);
                etEmail.setEnabled(true);
                etGroupDescription.setEnabled(true);
                break;
            case R.id.settings_changepassword:
                startActivity(new Intent(this, ChangePassword.class));
                break;
        }

    }



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
			try {
				userProfile.setImageBitmap(Images.getScaledDownBitmap(getContentResolver(),
						data.getData(), imageWidth, imageHeight));
				epListener.setUpdated(true);
			}
			catch (Exception e) {
				log.severe(Exceptions.stacktraceToString(e));
			}
		}
	}

}
