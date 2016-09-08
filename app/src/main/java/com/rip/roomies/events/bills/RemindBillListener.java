package com.rip.roomies.events.bills;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.models.Bill;
import com.rip.roomies.models.User;
import com.rip.roomies.server.ServerRequest;

import java.net.URISyntaxException;

/**
 * Created by michaelzhong on 5/29/16.
 */


public class RemindBillListener implements View.OnClickListener{
	private Bill bill;
	private Button button;
	private LinearLayout context;

	public RemindBillListener(Button button, LinearLayout context, Bill bill) {
		this.bill=bill;
		this.button = button;
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		/* INSER REMINDER FUNCTION CALL HERE (HAO) */
		try {
			ServerRequest.remindBill(bill.getRowID(), bill.getOweeID(), User.getActiveUser().getFirstName(),
					bill.getAmount(), bill.getDescription());
			button.setBackground(context.getResources().getDrawable(R.drawable.rec_border_gray));
			button.setTextColor(context.getResources().getColor(R.color.black_overlay));
			Toast.makeText(context.getContext(), "Reminder Sent, please wait for another 6" +
					" hours to send another one", Toast.LENGTH_SHORT).show();
			button.setEnabled(false);

		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
