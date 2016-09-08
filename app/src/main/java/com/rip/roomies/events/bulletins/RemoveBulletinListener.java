package com.rip.roomies.events.bulletins;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.controllers.BillController;
import com.rip.roomies.controllers.HomeController;
import com.rip.roomies.models.Bill;
import com.rip.roomies.models.Bulletin;
import com.rip.roomies.views.BillView;
import com.rip.roomies.views.BulletinContainer;
import com.rip.roomies.views.BulletinView;

/**
 * Created by VinnysMacOS on 5/20/16.
 */
public class RemoveBulletinListener implements View.OnClickListener {

    private LinearLayout context;
    private LinearLayout rightLayout;
    private Button removeBulletin;
    private Button editBulletin;
    private TextView content;
    private Bulletin selectedBulletin;
    private BulletinContainer container;

    public RemoveBulletinListener(LinearLayout context, LinearLayout rightLayout,
                                  Bulletin selectedBulletin, Button removeBulletin,
                                  Button editBulletin, TextView content,
                                  BulletinContainer container) {
        this.context = context;
        this.rightLayout = rightLayout;
        this.removeBulletin = removeBulletin;
        this.editBulletin = editBulletin;
        this.selectedBulletin = selectedBulletin;
        this.content = content;
        this.container = container;
    }

    @Override
    public void onClick(View v) {
        //first remove the selectedBill from the DB
        HomeController.getController().removeBulletin(selectedBulletin.getRowID(), container);

        //now remove its contents from the view.
        rightLayout.removeView(removeBulletin);
        rightLayout.removeView(editBulletin);
        context.removeView(content);
        context.removeView(rightLayout);
    }
}
