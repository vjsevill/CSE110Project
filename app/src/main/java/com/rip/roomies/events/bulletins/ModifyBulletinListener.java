package com.rip.roomies.events.bulletins;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.activities.bills.Bills;
import com.rip.roomies.activities.home.Home;
import com.rip.roomies.models.Bill;
import com.rip.roomies.models.Bulletin;

/**
 * Created by VinnysMacOS on 5/20/16.
 */
public class ModifyBulletinListener implements View.OnClickListener {

    private Home activity;
    private Bulletin editBull;
    private TextView content;

    public ModifyBulletinListener(Home activity, Bulletin editBull, TextView content) {
        this.activity = activity;
        this.editBull = editBull;
        this.content = content;
    }

    @Override
    public void onClick(View v) {
        activity.toEditBillScreen(content, editBull);
    }

}
