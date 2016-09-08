package com.rip.roomies.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.goods.ModifyGood;
import com.rip.roomies.events.goods.PopUpGoodListener;
import com.rip.roomies.events.goods.RemindGoodListener;
import com.rip.roomies.functions.CompleteGoodFunction;
import com.rip.roomies.models.Good;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class is a displayable view that represents a Good object. It will display
 * any necessary information as well as style once implemented.
 */
public class GoodView extends TaskView {
	private static final Logger log = Logger.getLogger(GoodView.class.getName());

	private Good good;

	/**
	 * @see android.view.View(Context)
	 */
	public GoodView(Context context) {
		super(context);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public GoodView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public GoodView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Get the Good object that this class represents
	 *
	 * @return The Good object in question
	 */
	public Good getGood() {
		return good;
	}

	/**
	 * Set the good of this object whose information this view will display
	 *
	 * @param good The good object to display
	 */
	public void setGood(Good good) {
		this.good = good;
		setupLayout();
	}

	/**
	 * Sets up the layout for this GoodView.
	 */
	protected void setupLayout(){
		log.info(String.format(InfoStrings.VIEW_SETUP, GoodView.class.getSimpleName()));

		removeAllViews();

		LinearLayout.LayoutParams w = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		setLayoutParams(w);
		setOrientation(LinearLayout.VERTICAL);

		TextView name = new TextView(getContext());
		TextView description = new TextView(getContext());
		TextView assignee = new TextView(getContext());
		Button actBtn = new Button(getContext());
		Button editBtn = new Button(getContext());

		LinearLayout innerLayout = new LinearLayout(getContext());
		innerLayout.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1.0f));

		name.setTextColor(getResources().getColor(R.color.colorPrimary));
		name.setTypeface(null, Typeface.BOLD);
		assignee.setTextColor(Color.BLACK);
		description.setTextColor(getResources().getColor(R.color.black_overlay));

		innerLayout.setOrientation(LinearLayout.VERTICAL);
		innerLayout.setPadding(25, 25, 25, 25);

		name.setText(good.getName());
		description.setText(good.getDescription());
		String fullName = good.getAssignee().getFirstName() + " " + good.getAssignee().getLastName();
		assignee.setText(fullName);

		description.setPadding(30,10,0,10);
		assignee.setPadding(30,10,0,10);
		innerLayout.addView(name);
		innerLayout.addView(assignee);

		if(description.getText().length()==0 ||
				description.getText().toString().trim().length()==0){
			description.setText("(No Description)");
		}

		innerLayout.addView(description);

		editBtn.setText("Edit");
		editBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
		editBtn.setBackground(getResources().getDrawable(R.drawable.rec_border));
		actBtn.setPadding(10, 20, 10, 20);
		LinearLayout.LayoutParams p = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		p.gravity = Gravity.CENTER;
		p.setMargins(10, 50, 10, 50);
		editBtn.setLayoutParams(p);
		editBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
						ModifyGood.class.getSimpleName()));

				Intent i = new Intent(getContext(), ModifyGood.class);
				i.putExtra("Good", good);
				((Activity) getContext()).startActivityForResult(i, EDIT_GOOD);
			}
		});


		LinearLayout.LayoutParams v = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		v.gravity = Gravity.CENTER_VERTICAL;
		v.setMargins(10, 50, 10, 50);
		actBtn.setLayoutParams(v);

		User currentAssignee = good.getAssignee();
		if (currentAssignee.getId() == User.getActiveUser().getId()) {
			actBtn.setText("Complete");
			actBtn.setTextColor(getResources().getColor(R.color.dark_green));
			actBtn.setBackground(getResources().getDrawable(R.drawable.rec_border_green));
			actBtn.setPadding(10, 20, 10, 20);
			int popUpID = R.layout.activity_confirm_complete_good;
			actBtn.setOnClickListener(new PopUpGoodListener(
					(GenericActivity) getContext(), (CompleteGoodFunction) getContext(),
					actBtn, popUpID, good));
		}
		else{
			actBtn.setText("Remind");

			actBtn.setTextColor(getResources().getColor(R.color.pink));
			actBtn.setBackground(getResources().getDrawable(R.drawable.rec_border_pink));
			actBtn.setPadding(10, 20, 10, 20);
			actBtn.setOnClickListener(new RemindGoodListener( actBtn ,
					(GenericActivity) getContext(), currentAssignee.getId(), good));

			if(good.reminded()) {
				actBtn.setBackground(getResources().getDrawable(R.drawable.rec_border_gray));
				actBtn.setTextColor(getResources().getColor(R.color.black_overlay));
				actBtn.setEnabled(false);
			}
			else {
				actBtn.setBackground(getResources().getDrawable(R.drawable.rec_border_pink));
				actBtn.setTextColor(getResources().getColor(R.color.pink));
				actBtn.setEnabled(true);

			}
		}



		LinearLayout hline = new LinearLayout(getContext());
		LayoutParams hlinep = new LayoutParams(200,1);
		hlinep.gravity = Gravity.CENTER_HORIZONTAL;
		hline.setLayoutParams(hlinep);
		hline.setBackgroundColor(Color.BLACK);

		LinearLayout outerLayout = new LinearLayout(getContext());
		outerLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		outerLayout.setOrientation(LinearLayout.HORIZONTAL);

		outerLayout.addView(innerLayout);
		outerLayout.addView(editBtn);
		outerLayout.addView(actBtn);

		addView(outerLayout);
		addView(hline);
	}
}