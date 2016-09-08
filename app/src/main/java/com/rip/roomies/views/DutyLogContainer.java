package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rip.roomies.models.DutyLog;
import com.rip.roomies.util.InfoStrings;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by johndoney on 5/29/16.
 */
public class DutyLogContainer extends ScrollView {
	private static final Logger log = Logger.getLogger(DutyLogContainer.class.getName());

	private ArrayList<DutyLog> dutyLogs = new ArrayList<>();
	private LinearLayout dutyLogLayout;

	/**
	 * @see android.view.View( Context )
	 */
	public DutyLogContainer(Context context) {
		super(context);
		dutyLogLayout = new LinearLayout(context);
		dutyLogLayout.setOrientation(LinearLayout.VERTICAL);
		addView(dutyLogLayout);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public DutyLogContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		dutyLogLayout = new LinearLayout(context, attrs);
		dutyLogLayout.setOrientation(LinearLayout.VERTICAL);
		addView(dutyLogLayout);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public DutyLogContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		dutyLogLayout = new LinearLayout(context, attrs, defStyle);
		dutyLogLayout.setOrientation(LinearLayout.VERTICAL);
		addView(dutyLogLayout);
	}

	/**
	 * Adds a new duty to the DutyContainer at the end of the list.
	 *
	 * @param newDutyLog The new Duty to add
	 */
	public void addDutyLog(DutyLog newDutyLog) {
		log.info(String.format(InfoStrings.CONTAINER_ADD,
				DutyLogView.class.getSimpleName(), DutyLogContainer.class.getSimpleName()));

		dutyLogs.add(newDutyLog);

		DutyLogView dutyLogView = new DutyLogView(getContext());
		dutyLogView.setDutyLog(newDutyLog);
		dutyLogLayout.addView(dutyLogView);
	}
}
