package com.rip.roomies.events.login;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.login.Login;
import com.rip.roomies.controllers.LoginController;
import com.rip.roomies.functions.FindUserFunction;
import com.rip.roomies.models.User;
import com.rip.roomies.server.ServerRequest;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.Validation;

import java.net.URISyntaxException;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents the listener for when the "Retrieve Password" button is pressed.
 */
public class PassRetrieveListener implements View.OnClickListener, FindUserFunction {
	private static final Logger log = Logger.getLogger(PassRetrieveListener.class.getName());

	private GenericActivity context;
	private EditText email;

	public PassRetrieveListener(GenericActivity context, EditText email) {
		this.context = context;
		this.email = email;
	}

	@Override
	public void onClick(View v) {

		String errMsg = "";

		errMsg += Validation.validate(email, Validation.ParamType.Email, "Email");

		if (!errMsg.isEmpty()) {
			errMsg = errMsg.substring(0, errMsg.length() - 1);
			Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show();
			return;
		}

		log.info(InfoStrings.PASSRETRIEVE_EVENT);

		LoginController.getController().passRetrieve(this, email.getText().toString());

	}

	@Override
	public void findUserFail() {
		Toast.makeText(context, DisplayStrings.PASS_RETRIEVE_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void findUserSuccess(User user) {

		log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY_DELAYED,
				Login.class.getName(), DisplayStrings.TOAST_SHORT_LENGTH));
		log.info("email: " + user.getEmail());

		//socket here
		//after actually completed back from controller, call the and remind everyone
//		Socket mSocket;
		try {
//			//connection to the node.js server
//			mSocket = IO.socket(SocketStrings.SERVER_URL);
//			mSocket.connect();
//			//emit the password retreive action
//			mSocket.emit(SocketStrings.PASSWORD_RETRIEVE, user.getId(), user.getEmail());
			ServerRequest.passwordRetrieve(user.getId(), user.getEmail());
			Toast.makeText(context, DisplayStrings.PASS_RETRIEVE_SUCCESS, Toast.LENGTH_SHORT).show();

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					context.toLogin();
				}
			}, DisplayStrings.TOAST_SHORT_LENGTH);

		}catch (URISyntaxException e) {
			Toast.makeText(context, DisplayStrings.PASS_RETRIEVE_FAIL, Toast.LENGTH_SHORT).show();
			throw new RuntimeException(e);
		}
	}
}
