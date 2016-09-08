package com.rip.roomies.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.models.DutyLog;
import com.rip.roomies.util.InfoStrings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * Created by Tony Phan on 5/29/2016.
 */
public class DutyLogView extends LinearLayout {

	private static final Logger log = Logger.getLogger(DutyLogView.class.getName());

	private DutyLog dutyLog;

	/**
	 * @see android.view.View( Context )
	 */
	public DutyLogView(Context context) {
		super(context);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public DutyLogView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public DutyLogView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Get the Duty object that this class represents
	 *
	 * @return The Duty object in question
	 */
	public DutyLog getDutyLog() {
		return dutyLog;
	}

	/**
	 * Set the duty of this object whose information this view will display
	 *
	 * @param dutyLog The duty object to display
	 */
	public void setDutyLog(DutyLog dutyLog) {
		this.dutyLog = dutyLog;
		setupLayout();
	}

	/**
	 * Sets up the layout for this DutyView.
	 */
	private void setupLayout() {
		log.info(String.format(InfoStrings.VIEW_SETUP, DutyLogView.class.getSimpleName()));

		setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.VERTICAL);

		TextView name = new TextView(getContext());
		TextView description = new TextView(getContext());
		TextView assignee = new TextView(getContext());
		TextView completeDate = new TextView(getContext());
		LinearLayout innerLayout = new LinearLayout(getContext());



		name.setText(dutyLog.getName());
		name.setTextColor(getResources().getColor(R.color.colorPrimary));
		name.setTypeface(null, Typeface.BOLD);
		name.setTextSize(20);

		description.setText(dutyLog.getDescription());
		description.setTextColor(Color.BLACK);
		description.setTextSize(15);

		String fullName = dutyLog.getAssignee().getFirstName() + " " + dutyLog.getAssignee().getLastName();
		assignee.setText(fullName);
		assignee.setTextColor(Color.BLACK);
		assignee.setTextSize(15);

		DateFormat compDate = new SimpleDateFormat("MM/dd/yyyy  |  hh:mm a");
		String text = compDate.format(dutyLog.getCompletion());
		completeDate.setText(text);
		completeDate.setTextColor(getResources().getColor(R.color.black_overlay));



		innerLayout.setOrientation(LinearLayout.VERTICAL);
		innerLayout.setPadding(50, 50, 50, 50);



		innerLayout.addView(name);
		innerLayout.addView(assignee);
		innerLayout.addView(description);
		innerLayout.addView(completeDate);



		LinearLayout hline = new LinearLayout(getContext());
		hline.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
		hline.setBackgroundColor(Color.BLACK);

		LinearLayout outerLayout = new LinearLayout(getContext());
		outerLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		outerLayout.setOrientation(LinearLayout.HORIZONTAL);


		outerLayout.addView(innerLayout);

		addView(outerLayout);
		addView(hline);
	}
}