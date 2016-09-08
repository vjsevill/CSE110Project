package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.rip.roomies.models.Task;

import java.util.logging.Logger;

/**
 * This class represents a task in a way that is clean and easy to view.
 */
public abstract class TaskView extends LinearLayout {
	private static final Logger log = Logger.getLogger(TaskView.class.getName());

	public static final int EDIT_GOOD = 1;
	public static final int VIEW_GOOD = 2;
	public static final int ADD_GOOD = 3;
	public static final int COMPLETE_GOOD = 4;

	public static final int EDIT_DUTY = 5;
	public static final int VIEW_DUTY = 6;
	public static final int ADD_DUTY = 7;


	private Task task;

	/**
	 * @see android.view.View( Context )
	 */
	public TaskView(Context context) {
		super(context);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public TaskView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public TaskView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Get the Task object that this class represents
	 *
	 * @return The Task object in question
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Set the task of this object whose information this view will display
	 *
	 * @param task The task object to display
	 */
	public void setTask(Task task) {
		this.task = task;
		setupLayout();
	}

	/**
	 * Sets the layout of this view.
	 */
	protected abstract void setupLayout();
}
