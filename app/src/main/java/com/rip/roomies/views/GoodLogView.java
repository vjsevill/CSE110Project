package com.rip.roomies.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.models.GoodLog;
import com.rip.roomies.util.InfoStrings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * Create a view that represents a good log.
 */
public class GoodLogView extends LinearLayout {

	private static final Logger log = Logger.getLogger(GoodLogView.class.getName());

	private GoodLog goodLog;

	/**
	 * @see android.view.View( Context )
	 */
	public GoodLogView(Context context) {
		super(context);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public GoodLogView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public GoodLogView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Get the Good object that this class represents
	 *
	 * @return The Good object in question
	 */
	public GoodLog getGoodLog() {
		return goodLog;
	}

	/**
	 * Set the good of this object whose information this view will display
	 *
	 * @param goodLog The good object to display
	 */
	public void setGoodLog(GoodLog goodLog) {
		this.goodLog = goodLog;
		setupLayout();
	}

	/**
	 * Sets up the layout for this GoodView.
	 */
	private void setupLayout() {
		log.info(String.format(InfoStrings.VIEW_SETUP, GoodLogView.class.getSimpleName()));

		setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.VERTICAL);

		TextView name = new TextView(getContext());
		TextView description = new TextView(getContext());
		TextView assignee = new TextView(getContext());
		TextView completeDate = new TextView(getContext());
		LinearLayout innerLayout = new LinearLayout(getContext());


		name.setTextColor(getResources().getColor(R.color.colorPrimary));
		name.setTypeface(null, Typeface.BOLD);
		name.setTextSize(20);

		description.setTextColor(Color.BLACK);
		description.setTextSize(15);

		assignee.setTextColor(Color.BLACK);
		assignee.setTextSize(15);

		DateFormat compDate = new SimpleDateFormat("MM/dd/yyyy  |  hh:mm a");
		String text = compDate.format(goodLog.getCompletion());
		completeDate.setTextColor(getResources().getColor(R.color.black_overlay));


		innerLayout.setOrientation(LinearLayout.VERTICAL);
		innerLayout.setPadding(50, 50, 50, 50);

		name.setText(goodLog.getName());
		description.setText(goodLog.getDescription());

		String fullName = goodLog.getAssignee().getFirstName() + " " + goodLog.getAssignee().getLastName();

		assignee.setText(fullName);
		completeDate.setText(text);

		LinearLayout hline = new LinearLayout(getContext());
		hline.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
		hline.setBackgroundColor(Color.BLACK);

		LinearLayout outerLayout = new LinearLayout(getContext());
		outerLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		outerLayout.setOrientation(LinearLayout.HORIZONTAL);


		innerLayout.addView(name);
		innerLayout.addView(assignee);
		innerLayout.addView(description);
		innerLayout.addView(completeDate);

		addView(innerLayout);
		addView(hline);
	}
}