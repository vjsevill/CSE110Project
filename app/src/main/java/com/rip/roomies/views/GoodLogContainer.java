package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rip.roomies.models.GoodLog;
import com.rip.roomies.util.InfoStrings;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This container is designed to hold goodlogs in a scoll view.
 */
public class GoodLogContainer extends ScrollView {
	private static final Logger log = Logger.getLogger(GoodLogContainer.class.getName());

	private ArrayList<GoodLog> goodLogs = new ArrayList<>();
	private LinearLayout goodLogLayout;

	/**
	 * @see android.view.View( Context )
	 */
	public GoodLogContainer(Context context) {
		super(context);
		goodLogLayout = new LinearLayout(context);
		goodLogLayout.setOrientation(LinearLayout.VERTICAL);
		addView(goodLogLayout);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public GoodLogContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		goodLogLayout = new LinearLayout(context, attrs);
		goodLogLayout.setOrientation(LinearLayout.VERTICAL);
		addView(goodLogLayout);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public GoodLogContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		goodLogLayout = new LinearLayout(context, attrs, defStyle);
		goodLogLayout.setOrientation(LinearLayout.VERTICAL);
		addView(goodLogLayout);
	}

	/**
	 * Adds a new good to the GoodContainer at the end of the list.
	 *
	 * @param newGoodLog The new Good to add
	 */
	public void addGoodLog(GoodLog newGoodLog) {
		log.info(String.format(InfoStrings.CONTAINER_ADD,
				GoodLogView.class.getSimpleName(), GoodLogContainer.class.getSimpleName()));

		goodLogs.add(newGoodLog);

		GoodLogView goodLogView = new GoodLogView(getContext());
		goodLogView.setGoodLog(newGoodLog);
		goodLogLayout.addView(goodLogView);
	}
}