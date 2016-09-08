package com.rip.roomies.events.duties;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.duties.ViewDuty;
import com.rip.roomies.functions.CompleteDutyFunction;
import com.rip.roomies.models.Duty;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by michaelzhong on 5/30/16.
 */
public class PopUpDutyListener implements View.OnClickListener{
	private static final Logger log = Logger.getLogger(PopUpDutyListener.class.getName());
	private GenericActivity context;
	private int layoutID;
	private Duty duty;
	private Button callerbtn;
	private CompleteDutyFunction funct;

	public PopUpDutyListener(GenericActivity context, CompleteDutyFunction funct,
	                         Button caller, int popUpID, Duty duty ){
		this.context = context;
		this.funct = funct;
		this.layoutID = popUpID;
		this.duty = duty;
		this.callerbtn = caller;

	}

	/* Displaying popup window*/
	@Override
	public void onClick(View v) {
		LayoutInflater layoutInflater
				= (LayoutInflater) context.getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(layoutID, null);
		final PopupWindow popupWindow = new PopupWindow(
				popupView,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		Button btnYes = (Button)popupView.findViewById(R.id.yes_btn);
		Button btnNo = (Button)popupView.findViewById(R.id.no_btn);

		btnYes.setOnClickListener(new CompleteDutyListener(context, funct, duty, popupWindow));

		btnNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
						ViewDuty.class.getSimpleName()));
				popupWindow.dismiss();

			}
		});
		popupWindow.showAtLocation(callerbtn, Gravity.CENTER,0,0);
	}
}
