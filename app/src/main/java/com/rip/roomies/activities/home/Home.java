package com.rip.roomies.activities.home;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;

import android.view.View;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.bills.Bills;
import com.rip.roomies.activities.bulletin.AddBulletin;
import com.rip.roomies.activities.bulletin.ModifyBulletin;
import com.rip.roomies.activities.duties.ListAllDuties;
import com.rip.roomies.activities.goods.ListAllGoods;
import com.rip.roomies.activities.profile.Profile;
import com.rip.roomies.activities.tasks.ListMyTasks;

import com.rip.roomies.application.SaveSharedPreference;
import com.rip.roomies.controllers.HomeController;
import com.rip.roomies.models.Bill;
import com.rip.roomies.models.Bulletin;

import com.rip.roomies.controllers.LoginController;

import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.server.ServerRequest;
import com.rip.roomies.util.Images;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.views.BulletinContainer;

import java.net.URISyntaxException;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Home extends GenericActivity {
	private static final Logger log = Logger.getLogger(Home.class.getName());
	private static final double IMAGE_WIDTH_RATIO = 3.0 / 10;
	private static final double IMAGE_HEIGHT_RATIO = 2.0 / 25;

	private User user;
	private CharSequence first_name;
	private BulletinContainer container;
	private Bulletin editBull;
	private TextView aBullCont;
	private boolean popupEnabled = false;
	private TextView username;
	private ImageView profileBadge;

	private static final int RESULT_CODE_MODIFY_BULLETIN = 1;
	private static final int RESULT_CODE_ADD_BULLETIN = 2;
	private static final int UPDATE_PROFILE = 3;
	final Activity self = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		//right when load in home screen update the token
		try {
			ServerRequest.refreshToken(User.getActiveUser().getId(), FirebaseInstanceId.getInstance().getToken());
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}

		TextView dutiesScreen = (TextView) findViewById(R.id.home_overallduties);
		TextView goodsScreen = (TextView) findViewById(R.id.home_shareditem);
		TextView billScreen = (TextView) findViewById(R.id.home_IOU);
		username = (TextView) findViewById(R.id.home_username);

		user = User.getActiveUser();
		first_name = user.getFirstName();
		username.setText(" " + first_name + "!");
		username.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, Profile.class));
			}
		});

		profileBadge = (ImageView) findViewById(R.id.home_profilepicture);
		profileBadge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivityForResult(new Intent(self, Profile.class), UPDATE_PROFILE);
			}
		});

		Button bulletinAddButton = (Button) findViewById(R.id.bulletin_addbtn);
		container = (BulletinContainer) findViewById(R.id.bulletin_container);

		billScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, Bills.class));
			}
		});

		dutiesScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, ListAllDuties.class));
			}
		});

		setBalance(billScreen);
		TextView noBulletinsMsg = (TextView) findViewById(R.id.no_bulletins_msg);
		HomeController.populateBulletins(container, noBulletinsMsg);

		goodsScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, ListAllGoods.class));
			}
		});

		TextView toMyDuties = (TextView) findViewById(R.id.to_view_my_duties);
		toMyDuties.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(self, ListMyTasks.class));
			}
		});

		bulletinAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(self, AddBulletin.class);
				startActivityForResult(i, RESULT_CODE_ADD_BULLETIN);
			}
		});

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		ImageView logo = (ImageView) findViewById(R.id.home_appname);
		logo.setImageBitmap(Images.getScaledDownBitmap(getResources(), R.mipmap.logowhite,
				(int) (size.x * IMAGE_WIDTH_RATIO), (int) (size.y * IMAGE_HEIGHT_RATIO)));


		User thisUser = User.getActiveUser();

		if (thisUser == null || thisUser.getProfilePic() == null) {
			profileBadge.setImageBitmap(Images.getScaledDownBitmap(getResources(),
					R.mipmap.default_user_image, (int) (size.x * IMAGE_WIDTH_RATIO),
					(int) (size.y * IMAGE_HEIGHT_RATIO)));
		}
		else {
			profileBadge.setImageBitmap(BitmapFactory.decodeByteArray(thisUser.getProfilePic(),
					0, thisUser.getProfilePic().length));
		}

		//make server listening to all the notification
		try {
			ServerRequest.subscribToRoom(Group.getActiveGroup().getId());
			ServerRequest.subscribToMyTopic(User.getActiveUser().getId());
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
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

		Button btnYes = (Button)popupView.findViewById(R.id.yes_btn);
		Button btnNo = (Button)popupView.findViewById(R.id.no_btn);

		btnYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(SaveSharedPreference.getUsername(v.getContext()).length() != 0 || SaveSharedPreference.getPassword(v.getContext()).length() != 0) {
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
						Home.class.getSimpleName()));
				popupWindow.dismiss();
				popupEnabled = false;

			}
		});
		popupWindow.showAtLocation(container, Gravity.CENTER,0,0);
	}

	private void setBalance(final TextView billScreen) {
		// Create and run a new thread
		new AsyncTask<Void, Void, CharSequence>() {
			@Override
			protected CharSequence doInBackground(Void... v) {
				return Bill.getNegativeBalance();
			}
			@Override
			protected void onPostExecute(CharSequence result) {
				if (result.charAt(1) == '0')
					billScreen.setTextColor(getResources().getColor(R.color.green));
				else
					billScreen.setTextColor(getResources().getColor(R.color.pink));

				billScreen.setText(result);
			}
		}.execute();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,resultCode,data);

		if(requestCode == RESULT_CODE_MODIFY_BULLETIN && resultCode == RESULT_OK) {
			String updContent = data.getStringExtra("Key_New_Content");

			editBull.setContent(updContent);
			aBullCont.setText('"'+ updContent+'"');

			HomeController.getController().modifyBulletin(editBull);

		}
		else if(requestCode == RESULT_CODE_ADD_BULLETIN && resultCode == RESULT_OK) {
			String content = data.getStringExtra("Key_New_Content");

			HomeController.getController().createBulletin(content, container);
		}
		else if (requestCode == UPDATE_PROFILE && resultCode == RESULT_OK) {
			Bitmap bmp = data.getExtras().getParcelable("ProfileIcon");
			if (bmp != null) {
				profileBadge.setImageBitmap(bmp);
			}
			username.setText(" " + data.getExtras().getString("FirstName") + "!");
		}
	}

	public void toEditBillScreen(TextView content, Bulletin editBull) {
		aBullCont = content;
		this.editBull = editBull;

		log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
				ModifyBulletin.class.getSimpleName()));

		Intent i = new Intent(getApplicationContext(), ModifyBulletin.class);
		i.putExtra("Orig_Key_Content", editBull.getContent());
		startActivityForResult(i, RESULT_CODE_MODIFY_BULLETIN);
	}
}
