package com.rip.roomies.events.goods;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.GoodController;
import com.rip.roomies.functions.RemoveGoodFunction;
import com.rip.roomies.models.Good;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by johndoney on 5/30/16.
 */
public class RemoveGoodListener implements View.OnClickListener, RemoveGoodFunction {
	private static final Logger log = Logger.getLogger(RemoveGoodListener.class.getName());

	private Good good;
	private GenericActivity activity;

	/**
	 * Remove Good Listener Constructor
	 *
	 * @param context  Activity that is using the listener
	 * @param good The existing good object in a view
	 */
	public RemoveGoodListener(GenericActivity context, Good good) {
		this.good = good;
		this.activity = context;
	}

	/**
	 * removeDuty.onClickListener
	 *
	 * @param v the View object passed in by ViewDuty activity
	 */
	@Override
	public void onClick(View v) {
		/*String Buffer for Error Message*/
		StringBuilder errMessage = new StringBuilder();

		/* Check if duty is null*/
		if (good == null) {
			errMessage.append(String.format(Locale.US, DisplayStrings.MISSING_FIELD, "Good"));
		}
		/* Check if error occurred*/
		if (errMessage.length() != 0) {
			String errMsg = errMessage.substring(0, errMessage.length() - 1);
			Toast.makeText(activity, errMsg, Toast.LENGTH_SHORT).show();
			return;
		}

		log.info(InfoStrings.REMOVE_GOOD_EVENT);

		/* Remove Duty Activity*/
		GoodController.getController().removeGood(this, good.getId());
	}

	@Override
	public void removeGoodFail() {
		Toast.makeText(activity, DisplayStrings.REMOVE_GOOD_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void removeGoodSuccess(Good good) {
		Intent i = activity.getIntent();
		i.putExtra("Good", good);
		i.putExtra("toRemove", true);
		activity.setResult(Activity.RESULT_OK, i);
		activity.finish();
	}
}
