package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rip.roomies.models.Duty;
import com.rip.roomies.models.Good;
import com.rip.roomies.models.Task;
import com.rip.roomies.util.InfoStrings;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Is a container for a list of task views.
 */
public class TaskContainer extends ScrollView {
	private static final Logger log = Logger.getLogger(TaskContainer.class.getName());

	private ArrayList<Task> tasks = new ArrayList<>();
	private LinearLayout taskLayout;

	/**
	 * @see android.view.View( Context )
	 */
	public TaskContainer(Context context) {
		super(context);
		taskLayout = new LinearLayout(context);
		taskLayout.setOrientation(LinearLayout.VERTICAL);
		addView(taskLayout);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public TaskContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		taskLayout = new LinearLayout(context, attrs);
		taskLayout.setOrientation(LinearLayout.VERTICAL);
		addView(taskLayout);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public TaskContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		taskLayout = new LinearLayout(context, attrs, defStyle);
		taskLayout.setOrientation(LinearLayout.VERTICAL);
		addView(taskLayout);
	}

	/**
	 * Adds a new task to the TaskContainer at the end of the list.
	 *
	 * @param newTask The new Task to add
	 */
	public void addTask(Task newTask) {
		log.info(String.format(InfoStrings.CONTAINER_ADD,
				TaskView.class.getSimpleName(), TaskContainer.class.getSimpleName()));

		tasks.add(newTask);

		TaskView taskView = newTask.getView(getContext());
		taskView.setTask(newTask);
		taskLayout.addView(taskView);
	}

	/**
	 * Modify an already existing task inside the TaskContainer by replacing
	 * it with a task that has the same id
	 * @param toMod The replacement data
	 */
	public void modifyTask(Task toMod) {
		log.info(String.format(Locale.US, InfoStrings.CONTAINER_MODIFY,
				TaskView.class.getSimpleName(), TaskContainer.class.getSimpleName()));

		for (int i = 0; i < tasks.size(); ++i) {
			if (tasks.get(i).equals(toMod)) {
				tasks.set(i, toMod);

				taskLayout.removeViewAt(i);
				TaskView tView = toMod.getView(getContext());
				tView.setTask(toMod);
				taskLayout.addView(tView, i);

				return;
			}
		}
	}

	public void removeTask(Task toRem) {
		log.info(String.format(Locale.US, InfoStrings.CONTAINER_REMOVE,
				TaskView.class.getSimpleName(), TaskContainer.class.getSimpleName()));

		for (int i = 0; i < tasks.size(); ++i) {
			if (tasks.get(i).equals(toRem)) {
				tasks.remove(i);
				taskLayout.removeViewAt(i);
				return;
			}
		}
	}

	public void completeTask(Task toCom) {
		log.info(String.format(Locale.US, InfoStrings.CONTAINER_MODIFY,
				TaskView.class.getSimpleName(), TaskContainer.class.getSimpleName()));

		for (int i = 0; i < tasks.size(); ++i) {
			if (tasks.get(i).equals(toCom)) {
				if (tasks.get(i).getAssignee().getId() != toCom.getAssignee().getId()) {
					tasks.remove(i);
					taskLayout.removeViewAt(i);
				}
				return;
			}
		}
	}

	/**
	 * Get the tasks held by this TaskContainer
	 * @return An array of tasks
	 */
	public Task[] getTasks() {
		Task[] temp = new Task[tasks.size()];
		return tasks.toArray(temp);
	}
}
