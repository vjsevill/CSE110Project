package com.rip.roomies.activities.goods;

import android.os.Bundle;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.GoodController;
import com.rip.roomies.functions.ListGoodLogsFunction;
import com.rip.roomies.models.GoodLog;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.views.GoodLogContainer;

import java.util.logging.Logger;

/**
 * Created by johndoney on 5/30/16.
 */
public class ListGoodLogs extends GenericActivity implements ListGoodLogsFunction {
	private static final Logger log = Logger.getLogger(ListAllGoods.class.getName());
	GoodLogContainer glc;

	@Override
	public void onCreate(Bundle savedInstance) {

		super.onCreate(savedInstance);
		setContentView(R.layout.activity_good_log);

		/* Linking xml objects to java */
		glc = (GoodLogContainer) findViewById(R.id.good_list);

		GoodController.getController().GoodLog(this);
	}

	@Override
	public void ListGoodLogsFail() {
		Toast.makeText(this, DisplayStrings.LOG_GOOD_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void ListGoodLogsSuccess(GoodLog[] goods) {
		for(GoodLog g : goods) {
			glc.addGoodLog(g);
		}
	}
}