package com.rip.roomies.activities.groups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.application.SaveSharedPreference;
import com.rip.roomies.controllers.LoginController;
import com.rip.roomies.models.User;
import com.rip.roomies.server.ServerRequest;
import com.rip.roomies.util.InfoStrings;

import java.net.URISyntaxException;
import java.util.Locale;
import java.util.logging.Logger;

public class GroupChoice extends GenericActivity {
	private static final Logger log = Logger.getLogger(GroupChoice.class.getName());

	private boolean popupEnabled = false;
	private User user;
	private Button joinBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_join_group);
		Button createBtn;

		final Context self = this;
		joinBtn = (Button) findViewById(R.id.btnJoinGroup);
		createBtn = (Button) findViewById(R.id.btnCreateGroup);

		joinBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(self, JoinGroup.class));
			}
		});

		createBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(self, CreateGroup.class));
			}
		});

		user = User.getActiveUser();
	}


	@Override
	public void onBackPressed() {
		if (popupEnabled) {
			return;
		}

		popupEnabled = true;

		LayoutInflater layoutInflater
				= (LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(R.layout.confirm_logoff, null);
		final PopupWindow popupWindow = new PopupWindow(
				popupView,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		Button btnYes = (Button) popupView.findViewById(R.id.yes_btn);
		Button btnNo = (Button) popupView.findViewById(R.id.no_btn);

		btnYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (SaveSharedPreference.getUsername(v.getContext()).length() != 0 || SaveSharedPreference.getPassword(v.getContext()).length() != 0) {
					log.info("username: " + SaveSharedPreference.getUsername(v.getContext()) + " 1 from logout" + "\n");
					log.info("password: " + SaveSharedPreference.getPassword(v.getContext()) + "1 from logout" + "\n");
					SaveSharedPreference.clearUsername(v.getContext());
					SaveSharedPreference.clearPassword(v.getContext());
					log.info("username: " + SaveSharedPreference.getUsername(v.getContext()) + "2 from logout" + "\n");
					log.info("password: " + SaveSharedPreference.getPassword(v.getContext()) + "2 from logout" + "\n");

				}
				popupWindow.dismiss();
				popupEnabled = false;

				try {
					ServerRequest.refreshToken(user.getId(), "");
				}
				catch (URISyntaxException e) {
					e.printStackTrace();
				}

				LoginController.getController().logoff();
				toLogin();
			}
		});

		btnNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
						GroupChoice.class.getSimpleName()));
				popupWindow.dismiss();
				popupEnabled = false;

			}
		});
		popupWindow.showAtLocation(joinBtn, Gravity.CENTER,0,0);
	}
}
