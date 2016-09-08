package com.rip.roomies.events.goods;

/**
 * Created by johndoney on 5/30/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.GoodController;
import com.rip.roomies.functions.ModifyGoodFunction;
import com.rip.roomies.models.Good;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.Validation;
import com.rip.roomies.views.UserContainer;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents the listener for when a good is modified.
 */
public class ModifyGoodListener implements View.OnClickListener, ModifyGoodFunction {
    private static final Logger log = Logger.getLogger(CreateGoodListener.class.getName());

    private GenericActivity activity;
    private EditText name;
    private EditText desc;
    private UserContainer users;
    private Good good;

    /**
     * Modify Good Listener Constructor
     *
     * @param context  Activity that is using the listener
     * @param name The name field
     * @param desc The description field
     * @param users The list of users
     */
    public ModifyGoodListener(GenericActivity context, EditText name,
                              EditText desc, UserContainer users, Good good) {
        this.activity = context;
        this.name = name;
        this.desc = desc;
        this.users = users;
        this.good = good;
    }

    /**
     * modifyGood.onClickListener
     *
     * @param v the View object passed in by ViewGood activity
     */
    @Override
    public void onClick(View v) {
		/*String Buffer for Error Message*/
        StringBuilder errMessage = new StringBuilder();

		/* Check if user entered name*/
        errMessage.append(Validation.validate(name, Validation.ParamType.Other, "Name"));

		/*Check if user entered users on good*/
        if (users.getUsers().length == 0) {
            errMessage.append(String.format(Locale.US, DisplayStrings.MISSING_FIELD, "Users"));
        }
		/* Check if error occurred*/
        if (errMessage.length() != 0) {
            String errMsg = errMessage.substring(0, errMessage.length() - 1);
            Toast.makeText(activity, errMsg, Toast.LENGTH_SHORT).show();
            return;
        }

        log.info(InfoStrings.MODIFY_GOOD_EVENT);

		/* Modify Good Activity*/
        GoodController.getController().modifyGoods(this, good.getId(),
                name.getText().toString(), desc.getText().toString(),
                users.getUsers());
    }

    @Override
    public void modifyGoodFail() {
        Toast.makeText(activity, DisplayStrings.MODIFY_GOOD_FAIL, Toast.LENGTH_LONG).show();
    }

    @Override
    public void modifyGoodSuccess(Good good) {
        Intent i = activity.getIntent();
        i.putExtra("Good", good);
        i.putExtra("toRemove", false);
        activity.setResult(Activity.RESULT_OK, i);
        activity.finish();
    }
}
