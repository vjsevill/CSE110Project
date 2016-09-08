package com.rip.roomies.events.bills;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.bills.Bills;
import com.rip.roomies.models.Bill;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.views.BillView;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by michaelzhong on 5/31/16.
 */
public class PopUpBillListener implements View.OnClickListener{
	private static final Logger log = Logger.getLogger(PopUpBillListener.class.getName());
	private GenericActivity context;
	private int layoutID;
	private Button callerBtn, removeBill, editBill;
	private Bill bill;
	private TextView name, amount, description;
	private LinearLayout innerLayout, underline;
	private BillView billView;



	public PopUpBillListener(GenericActivity context, Button caller,
	                         int popUpID, BillView billView,
	                         Bill bill, Button removeBill, Button editBill,
	                         TextView name, TextView amount, TextView description,
	                         LinearLayout innerLayout, LinearLayout underline){
		this.context=context;
		this.layoutID=popUpID;
		this.bill=bill;
		this.callerBtn=caller;
		this.removeBill=removeBill;
		this.editBill=editBill;
		this.name=name;
		this.amount=amount;
		this.description=description;
		this.innerLayout=innerLayout;
		this.underline = underline;
		this.billView=billView;

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

		btnYes.setOnClickListener(new RemoveBillListener(billView, bill, removeBill, editBill,
				name, amount, description, innerLayout, underline, popupWindow));

		btnNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
						Bills.class.getSimpleName()));
				popupWindow.dismiss();

			}
		});
		popupWindow.showAtLocation(callerBtn, Gravity.CENTER,0,0);
	}
}