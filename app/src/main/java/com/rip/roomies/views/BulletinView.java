package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.activities.home.Home;
import com.rip.roomies.events.bulletins.ModifyBulletinListener;
import com.rip.roomies.events.bulletins.RemoveBulletinListener;
import com.rip.roomies.models.Bulletin;
import com.rip.roomies.util.InfoStrings;

import java.util.logging.Logger;

/**
 * Created by VinnysMacOS on 5/21/16.
 */
public class BulletinView extends LinearLayout {

	private static final Logger log = Logger.getLogger(BulletinView.class.getName());

	private Bulletin bull;
	private BulletinContainer container;
	private Home activity;
	/*Tag to differentiate which screen are we coming from 1=MODIFY BULLETIN*/
	private final int RESULT_CODE_MODIFY_BULLETIN = 1;

	/**
	 * @see android.view.View( Context )
	 */
	public BulletinView(Context context) {
		super(context);
	}

	public BulletinView(Context context, BulletinContainer container, Home activity) {
		super(context);
		this.container = container;
		this.activity = activity;
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public BulletinView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public BulletinView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Get the Bulletin object that this class represents
	 *
	 * @return The Bulletin object in question
	 */
	public Bulletin getBulletin() {
		return bull;
	}

	/**
	 * Set the bulletin of this object whose information this view will display
	 *
	 * @param bull The bulletin object to display
	 */
	public void setBulletin(Bulletin bull) {
		this.bull = bull;
		setupLayout();
	}

	/**
	 * Sets up the layout for this UserView.
	 */
	private void setupLayout() {
		log.info(String.format(InfoStrings.VIEW_SETUP, BulletinView.class.getSimpleName()));

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.setMargins(20, 5, 0, 5);
		setLayoutParams(lp);
		setOrientation(LinearLayout.HORIZONTAL);

        /* Vertical Container for Buttons*/
		LinearLayout rightLayout = new LinearLayout(getContext());
		LayoutParams rlp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rightLayout.setLayoutParams(rlp);
		rightLayout.setOrientation(LinearLayout.HORIZONTAL);
		rightLayout.setGravity(Gravity.END);

		TextView content = new TextView(getContext());
		Button removeButton = new Button(getContext());
		Button editButton = new Button(getContext());

		/* Configure text */
		content.setTextSize(20);
		content.setTextColor(getResources().getColor(R.color.black));
		LayoutParams lpc = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lpc.weight = 0.7f;
		content.setLayoutParams(lpc);

		/* Configure buttons */
		removeButton.setBackground(getResources().getDrawable(R.drawable.xcircle));
		editButton.setBackground(getResources().getDrawable(R.drawable.pencilcircle));
		LayoutParams lpm = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lpm.width = (int)(30 * getContext().getResources().getDisplayMetrics().density); //30dp
		lpm.height = (int)(30 * getContext().getResources().getDisplayMetrics().density); //30dp
		lpm.setMargins(15, 10, 0, 10);
		removeButton.setLayoutParams(lpm);
		editButton.setLayoutParams(lpm);
		removeButton.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
		editButton.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);

		/* Set button listeners */
		removeButton.setOnClickListener(new RemoveBulletinListener(this, rightLayout, bull, removeButton,
				editButton, content, container));

		editButton.setOnClickListener(new ModifyBulletinListener(activity, bull, content));

		/* Getting information from bulletin */
		content.setText('"' + bull.getContent() + '"');

		/* Add fields to view */
		rightLayout.addView(editButton);
		rightLayout.addView(removeButton);
		addView(content);
		addView(rightLayout);
	}

}
