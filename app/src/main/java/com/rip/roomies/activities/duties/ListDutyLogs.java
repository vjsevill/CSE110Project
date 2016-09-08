package com.rip.roomies.activities.duties;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.DutyController;
import com.rip.roomies.functions.ListDutyLogsFunction;
import com.rip.roomies.models.DutyLog;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.views.DutyLogContainer;

import java.util.logging.Logger;

/**
 * Created by johndoney on 5/29/16.
 */
public class ListDutyLogs extends GenericActivity implements ListDutyLogsFunction {
	private static final Logger log = Logger.getLogger(ListAllDuties.class.getName());
	DutyLogContainer dlc;

	@Override
	public void onCreate(Bundle savedInstance) {

		super.onCreate(savedInstance);
		setContentView(R.layout.activity_duty_log);

		/* Linking xml objects to java */
		dlc = (DutyLogContainer) findViewById(R.id.duty_list);

		DutyController.getController().DutyLog(this);
	}

	@Override
	public void ListDutyLogsFail() {
		Toast.makeText(this, DisplayStrings.LOG_DUTY_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void ListDutyLogsSuccess(DutyLog[] duties) {
		for(DutyLog d : duties) {
			dlc.addDutyLog(d);
		}
	}
}
