package com.rip.roomies.events.goods;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.goods.ListAllGoods;
import com.rip.roomies.activities.goods.ViewGood;
import com.rip.roomies.functions.CompleteGoodFunction;
import com.rip.roomies.models.Good;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by Han on 6/2/2016.
 */
public class PopUpGoodListener implements View.OnClickListener{
    private static final Logger log = Logger.getLogger(PopUpGoodListener.class.getName());
    private GenericActivity context;
    private CompleteGoodFunction funct;
    private int layoutID;
    private Good good;
    private Button callerbtn;
    //private double amount; //for bill $ amount

    public PopUpGoodListener(GenericActivity context, CompleteGoodFunction funct, Button caller,int popUpID, Good good ){
        this.context = context;
	    this.funct = funct;
        this.layoutID = popUpID;
        this.good = good;
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

        final EditText userInput = (EditText) popupView.findViewById(R.id.amount);
        Button btnYes = (Button) popupView.findViewById(R.id.yes_btn);
        Button btnNo = (Button) popupView.findViewById(R.id.no_btn);

        popupWindow.setFocusable(true);
        popupWindow.update();

       // amount = Double.valueOf(userInput.getText().toString());
        //amount = Double.parseDouble(userInput.getText().toString());
//        btnYes.setOnClickListener(new CompleteGoodListener(context, good, popupWindow, amount));
        btnYes.setOnClickListener(new CompleteGoodListener(context, funct, good, popupWindow, userInput));

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
                        ViewGood.class.getSimpleName()));
                popupWindow.dismiss();

            }
        });
        popupWindow.showAtLocation(callerbtn, Gravity.CENTER, 0, 0);
    }
}
