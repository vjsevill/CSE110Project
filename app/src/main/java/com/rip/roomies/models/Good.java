package com.rip.roomies.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.rip.roomies.sql.SQLCreate;
import com.rip.roomies.sql.SQLGet;
import com.rip.roomies.sql.SQLModify;
import com.rip.roomies.sql.SQLRemove;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.views.GoodView;
import com.rip.roomies.views.TaskView;

import java.util.logging.Logger;

/**
 * Created by johndoney on 5/29/16.
 */
public class Good extends Task<GoodLog, Good> {
    private static final Logger log = Logger.getLogger(Good.class.getName());

    public static final Parcelable.Creator<Good> CREATOR
            = new Parcelable.Creator<Good>() {
        public Good createFromParcel(Parcel in) {
            return new Good(in);
        }

        public Good[] newArray(int size) {
            return new Good[size];
        }
    };

    //------- CONSTRUCTORS -------//


    public Good(Parcel in) {
        super(in);
    }

    /** @inheritDoc **/
    public Good(int id) {
        super(id);
    }

    /** @inheritDoc **/
    public Good(String name, String description, int groupId, User[] users) {
        super(name, description, groupId, users);
    }

    /** @inheritDoc **/
    public Good(int id, String name, String description, User[] users) {
        super(id, name, description, users);
    }

    /** @inheritDoc **/
    public Good(int id, String name, String description, int groupId, User assignee, User[] users) {
        super(id, name, description, groupId, assignee, users);
    }

    //------- DATABASE METHODS -------//

    /** @inheritDoc **/
    @Override
    public Good create() {
        log.info(InfoStrings.CREATE_GOOD_MODEL);
        return SQLCreate.createGood(this);
    }

    /** @inheritDoc **/
    @Override
    public Good modify() {
        log.info(InfoStrings.MODIFY_GOOD_MODEL);
        return SQLModify.modifyGood(this);
    }

    /** @inheritDoc **/
    @Override
    public Good remove() {
        log.info(InfoStrings.REMOVE_GOOD_MODEL);
        return SQLRemove.removeGood(this);
    }

    public Good complete(double price) {
        log.info(InfoStrings.COMPLETE_GOOD_MODEL);
        return SQLModify.completeGood(this, price);
    }


    public Good complete() {
        return complete(0);
    }

    /** @inheritDoc **/
    @Override
    public Good getRotation() {
        log.info(InfoStrings.GET_ROTATION_MODEL);
        return SQLGet.getGoodUsers(this);
    }

    /** @inheritDoc **/
    @Override
    public TaskView getView(Context context) {
        GoodView goodView = new GoodView(context);
        goodView.setGood(this);
        return goodView;
    }

    //------- OBJECT METHODS -------//

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        else if (o instanceof Good) {
            return (getId() == ((Good) o).getId());
        }
        else {
            return false;
        }
    }
}