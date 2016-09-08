package com.rip.roomies.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.rip.roomies.sql.SQLCreate;
import com.rip.roomies.sql.SQLGet;
import com.rip.roomies.sql.SQLModify;
import com.rip.roomies.sql.SQLRemove;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.views.DutyView;
import com.rip.roomies.views.TaskView;

import java.util.logging.Logger;

/**
 * This class defines a duty that requires an action to complete.
 */
public class Duty extends Task<DutyLog, Duty> {
	private static final Logger log = Logger.getLogger(Duty.class.getName());

	public static final Parcelable.Creator<Duty> CREATOR
			= new Parcelable.Creator<Duty>() {
		public Duty createFromParcel(Parcel in) {
			return new Duty(in);
		}

		public Duty[] newArray(int size) {
			return new Duty[size];
		}
	};

	//------- CONSTRUCTORS -------//


	public Duty(Parcel in) {
		super(in);
	}

	/** @inheritDoc **/
	public Duty(int id) {
		super(id);
	}

	/** @inheritDoc **/
	public Duty(String name, String description, int groupId, User[] users) {
		super(name, description, groupId, users);
	}

	/** @inheritDoc **/
	public Duty(int id, String name, String description, User[] users) {
		super(id, name, description, users);
	}

	/** @inheritDoc **/
	public Duty(int id, String name, String description, int groupId, User assignee, User[] users) {
		super(id, name, description, groupId, assignee, users);
	}

	//------- DATABASE METHODS -------//

	/** @inheritDoc **/
	@Override
	public Duty create() {
		log.info(InfoStrings.CREATE_DUTY_MODEL);
		return SQLCreate.createDuty(this);
	}

	/** @inheritDoc **/
	@Override
	public Duty modify() {
		log.info(InfoStrings.MODIFY_DUTY_MODEL);
		return SQLModify.modifyDuty(this);
	}

	/** @inheritDoc **/
	@Override
	public Duty remove() {
		log.info(InfoStrings.REMOVE_DUTY_MODEL);
		return SQLRemove.removeDuty(this);
	}

	/** @inheritDoc **/
	@Override
	public Duty complete() {
		log.info(InfoStrings.COMPLETE_DUTY_MODEL);
		return SQLModify.completeDuty(this);
	}

	/** @inheritDoc **/
	@Override
	public Duty getRotation() {
		log.info(InfoStrings.GET_ROTATION_MODEL);
		return SQLGet.getDutyUsers(this);
	}

	/** @inheritDoc **/
	@Override
	public TaskView getView(Context context) {
		DutyView dutyView = new DutyView(context);
		dutyView.setDuty(this);
		return dutyView;
	}

	//------- OBJECT METHODS -------//

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		else if (o instanceof Duty) {
			return (getId() == ((Duty) o).getId());
		}
		else {
			return false;
		}
	}

}
