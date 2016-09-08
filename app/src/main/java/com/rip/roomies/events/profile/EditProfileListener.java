package com.rip.roomies.events.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.ProfileController;
import com.rip.roomies.functions.UpdateProfileFunction;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.Exceptions;
import com.rip.roomies.util.Images;
import com.rip.roomies.util.Validation;

import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;

/**
 * Created by VinnysMacOS on 5/29/16.
 */
public class EditProfileListener implements View.OnClickListener, UpdateProfileFunction {
    private static final Logger log = Logger.getLogger(EditProfileListener.class.getName());

    private GenericActivity activity;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etGroupDescription;
	private ImageView profilePic;
	private int width;
	private int height;
	private boolean updated = false;

    public EditProfileListener(GenericActivity activity, EditText etFirstName, EditText etLastName,
                               EditText etEmail, EditText etGroupDescription, ImageView profilePic,
                               int width, int height) {
        this.activity = activity;
        this.etFirstName = etFirstName;
        this.etLastName = etLastName;
        this.etEmail = etEmail;
        this.etGroupDescription = etGroupDescription;
		this.profilePic = profilePic;
	    this.width = width;
	    this.height = height;
    }

    @Override
    public void onClick(View v) {
	    byte[] image = null;
	    if (profilePic != null && updated) {
		    Bitmap bmp = ((BitmapDrawable) profilePic.getDrawable()).getBitmap();
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
		    image = stream.toByteArray();
		    try {
			    stream.close();
		    }
		    catch (Exception e) {
			    log.severe(Exceptions.stacktraceToString(e));
		    }
	    }

	    String errMsg = "";

	    errMsg += Validation.validate(etFirstName, Validation.ParamType.Other, "First Name");
	    errMsg += Validation.validate(etLastName, Validation.ParamType.Other, "Last Name");
	    errMsg += Validation.validate(etEmail, Validation.ParamType.Email, "Email");
	    errMsg += Validation.validateImage(image, Validation.ParamType.SmallImage, "Profile Icon");

	    if (!errMsg.isEmpty()) {
		    errMsg = errMsg.substring(0, errMsg.length() - 1);
		    Toast.makeText(activity, errMsg, Toast.LENGTH_SHORT).show();
		    return;
	    }

			    //populate the DB with the changes
        ProfileController.getController().updateProfile(this, etFirstName.getText().toString(),
		        etLastName.getText().toString(), etEmail.getText().toString(),
		        etGroupDescription.getText().toString(),
		        image);

    }

    @Override
    public void updateProfileFailure() {
        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show();

    }

    @Override
    public void updateProfileSuccess(User user) {
        //trim the whitespace off all the Strings
        String firstname = etFirstName.getText().toString().trim();
        String lastname = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

	    byte[] image = null;
	    if (profilePic != null && updated) {
		    Bitmap bmp = ((BitmapDrawable) profilePic.getDrawable()).getBitmap();
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
		    image = stream.toByteArray();
		    try {
			    stream.close();
		    }
		    catch (Exception e) {
			    log.severe(Exceptions.stacktraceToString(e));
		    }
	    }

        //update the active user
        User.setActiveUser(new User(User.getActiveUser().getId(), firstname,
                lastname, User.getActiveUser().getUsername(), email, "", image));

        //update active group
        Group.setActiveGroupDescription(etGroupDescription.getText().toString());

	    byte[] updatedImg = user.getProfilePic();
	    Bitmap bmp = null;
	    if (updatedImg != null) {
		    bmp = Images.getScaledDownBitmap(updatedImg, width, height);
	    }
	    Intent i = activity.getIntent();
	    i.putExtra("ProfileIcon", bmp);
	    i.putExtra("FirstName", user.getFirstName());
        activity.setResult(Activity.RESULT_OK, i);
		activity.finish();
	    activity.toHome();
    }

	public void setUpdated(boolean isUpdated) {
		updated = isUpdated;
	}
}
