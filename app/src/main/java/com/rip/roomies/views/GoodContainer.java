package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rip.roomies.models.Good;
import com.rip.roomies.util.InfoStrings;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class represents a container for multiple GoodView objects that can
 * be displayed in a dynamic group.
 */
public class GoodContainer extends ScrollView {
	private static final Logger log = Logger.getLogger(GoodContainer.class.getName());

	private ArrayList<Good> goods = new ArrayList<>();
	private LinearLayout goodLayout;

	/**
	 * @see android.view.View(Context)
	 */
	public GoodContainer(Context context) {
		super(context);
		goodLayout = new LinearLayout(context);
		goodLayout.setOrientation(LinearLayout.VERTICAL);
		addView(goodLayout);
	}

	/**
	 * @see android.view.View(Context, AttributeSet)
	 */
	public GoodContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		goodLayout = new LinearLayout(context, attrs);
		goodLayout.setOrientation(LinearLayout.VERTICAL);
		addView(goodLayout);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public GoodContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		goodLayout = new LinearLayout(context, attrs, defStyle);
		goodLayout.setOrientation(LinearLayout.VERTICAL);
		addView(goodLayout);
	}

	/**
	 * Adds a new good to the GoodContainer at the end of the list.
	 *
	 * @param newGood The new Good to add
	 */
	public void addGood(Good newGood) {
		log.info(String.format(InfoStrings.CONTAINER_ADD,
				GoodView.class.getSimpleName(), GoodContainer.class.getSimpleName()));

		goods.add(newGood);

		GoodView goodView = new GoodView(getContext());
		goodView.setGood(newGood);
		goodLayout.addView(goodView);
	}

	/**
	 * Modify an already existing good inside the GoodContainer by replacing
	 * it with a good that has the same id
	 * @param toMod The replacement data
	 */
	public void modifyGood(Good toMod) {
		log.info(String.format(Locale.US, InfoStrings.CONTAINER_MODIFY,
				GoodView.class.getSimpleName(), GoodContainer.class.getSimpleName()));

		for (int i = 0; i < goods.size(); ++i) {
			if (goods.get(i).getId() == toMod.getId()) {
				goods.set(i, toMod);

				goodLayout.removeViewAt(i);
				GoodView dView = new GoodView(getContext());
				dView.setGood(toMod);
				goodLayout.addView(dView, i);

				return;
			}
		}
	}

	public void removeGood(Good toRem) {
		log.info(String.format(Locale.US, InfoStrings.CONTAINER_REMOVE,
				GoodView.class.getSimpleName(), GoodContainer.class.getSimpleName()));

		for (int i = 0; i < goods.size(); ++i) {
			if (goods.get(i).getId() == toRem.getId()) {
				goods.remove(i);
				goodLayout.removeViewAt(i);
				return;
			}
		}
	}

	/**
	 * Get the goods held by this GoodContainer
	 * @return An array of goods
	 */
	public Good[] getGoods() {
		Good[] temp = new Good[goods.size()];
		return goods.toArray(temp);
	}
}
