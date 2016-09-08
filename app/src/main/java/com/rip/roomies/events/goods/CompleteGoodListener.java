package com.rip.roomies.events.goods;

import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.GoodController;
import com.rip.roomies.functions.CompleteGoodFunction;
import com.rip.roomies.models.Good;
import com.rip.roomies.models.User;
import com.rip.roomies.server.ServerRequest;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;

import java.net.URISyntaxException;
import java.util.Locale;
import java.util.logging.Logger;


/**
 * Created by michaelzhong on 5/31/16.
 */
public class CompleteGoodListener implements View.OnClickListener {
	//	private ListAllGoods context;
	private Good good;
	private GenericActivity activity;
	private PopupWindow popupWindow;
	//	private int popUpLayoutID;
//	private GoodView goodview;
	private static final Logger log = Logger.getLogger(CompleteGoodListener.class.getName());
	//private double amount; //for bill $ amount
	public static final int COMPLETE_GOOD = 4;
	private EditText userInput;
	private CompleteGoodFunction funct;

	/**
	 * CONSTRUCTOR
	 *
	 * @param context Activity calling the listener class
	 * @param good    The good to be modified
	 */
/*	public CompleteGoodListener(ListAllGoods context, GoodView goodview, Good good) {
		this.context = context;
		this.good = good;
		this.goodview = goodview;
		this.popUpLayoutID = R.layout.activity_confirm_complete_good;
	}
*/

	public CompleteGoodListener(GenericActivity context, CompleteGoodFunction funct,
	                            Good good, PopupWindow popUpWindow, EditText userInput) {
		this.good = good;
		this.funct = funct;
		this.activity = context;
		this.popupWindow = popUpWindow;
		this.userInput = userInput;
	}
	/*
	public CompleteGoodListener(GenericActivity context, Good good, PopupWindow popUpWindow) {
		this.good = good;
		this.activity = context;
		this.popupWindow = popUpWindow;
	}
	*/


	/**
	 * completeDuty.onClickListener
	 *
	 * @param v the View object passed in by ViewDuty activity
	 */
	@Override
	public void onClick(View v) {
/*
		log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
				ViewGood.class.getSimpleName()));


		int popUpLayoutID = R.layout.activity_confirm_complete_good;

		//Creating inflater
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		//Inflating popup window layout
		View popupView = layoutInflater.inflate(popUpLayoutID, null);

		//creating pop up window and setting width and height to match parent
		final PopupWindow popupWindow = new PopupWindow(popupView,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setFocusable(true);
		popupWindow.update();

		final EditText userInput = (EditText) popupView.findViewById(R.id.amount);
		Button btnYes = (Button) popupView.findViewById(R.id.yes_btn);
		Button btnNo = (Button) popupView.findViewById(R.id.no_btn);

		final CompleteGoodFunction self = this;
		btnYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				amount = Double.valueOf(userInput.getText().toString());
				GoodController.getController().completeGood(self, good.getId(), amount);
				popupWindow.dismiss();
			}
		});

		btnNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
						ListAllGoods.class.getSimpleName()));
				popupWindow.dismiss();
			}
		});
		popupWindow.showAtLocation(btnYes, Gravity.CENTER, 0, 0); */



		//String Buffer for Error Message*/
		StringBuilder errMessage = new StringBuilder();

		// Check if duty is null*/
		if (good == null) {
			errMessage.append(String.format(Locale.US, DisplayStrings.MISSING_FIELD, "Good"));
		}
		// Check if error occurred*/
		if (errMessage.length() != 0) {
			String errMsg = errMessage.substring(0, errMessage.length() - 1);
			Toast.makeText(activity, errMsg, Toast.LENGTH_SHORT).show();
			return;
		}

		log.info(InfoStrings.COMPLETE_GOOD_EVENT);
		// Complete Duty Activity*/\
		double amount = 0;
		String input = userInput.getText().toString();
		if(!input.isEmpty()) {
			amount = Double.parseDouble(userInput.getText().toString());
			popupWindow.dismiss();
		}
		else{
			Toast.makeText(activity, "Please enter the amount", Toast.LENGTH_SHORT).show();
			return;
		}
		GoodController.getController().completeGood(funct, good.getId(), amount);
//		GoodController.getController().completeGood(this, good.getId());

		try {
			ServerRequest.completeCommonGood(good.getId(),User.getActiveUser().getFirstName(), good.getName());
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}


	}
}

