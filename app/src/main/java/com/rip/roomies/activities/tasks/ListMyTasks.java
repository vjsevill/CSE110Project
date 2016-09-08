package com.rip.roomies.activities.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.DutyController;
import com.rip.roomies.functions.CompleteDutyFunction;
import com.rip.roomies.functions.CompleteGoodFunction;
import com.rip.roomies.functions.ListMyTasksFunction;
import com.rip.roomies.models.Duty;
import com.rip.roomies.models.Good;
import com.rip.roomies.models.Task;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.views.DutyContainer;
import com.rip.roomies.views.DutyView;
import com.rip.roomies.views.GoodView;
import com.rip.roomies.views.TaskContainer;
import com.rip.roomies.views.TaskView;

/**
 * The activity of when the user wishes to view his or her duties.
 */
public class ListMyTasks extends GenericActivity implements ListMyTasksFunction,
		CompleteDutyFunction, CompleteGoodFunction {
	TaskContainer tc;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_list_my_tasks);

		tc = (TaskContainer) findViewById(R.id.task_list);

		DutyController.getController().listMyTasks(this);
	}

	@Override
	public void listMyTasksFail() {
		Toast.makeText(this, DisplayStrings.LIST_MY_TASKS_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void listMyTasksSuccess(Task[] tasks) {
		if (tasks == null || tasks.length == 0) {
			TextView msg = (TextView) findViewById(R.id.no_tasks_msg);
			msg.setVisibility(View.VISIBLE);
		}
		else {
			for (Task t : tasks) {
				tc.addTask(t);
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TaskView.EDIT_DUTY && resultCode == RESULT_OK) {
			Duty duty = data.getExtras().getParcelable("Duty");
			boolean toRemove = data.getExtras().getBoolean("toRemove");

			if (toRemove) {
				tc.removeTask(duty);

				if (tc.getTasks() == null || tc.getTasks().length == 0) {
					TextView msg = (TextView) findViewById(R.id.no_tasks_msg);
					msg.setVisibility(View.VISIBLE);
				}
			}
			else {
				tc.modifyTask(duty);
			}
		}
		else if (requestCode == TaskView.VIEW_DUTY && resultCode == RESULT_OK) {
			Duty duty = data.getExtras().getParcelable("Duty");
			tc.removeTask(duty);

			TextView msg = (TextView) findViewById(R.id.no_tasks_msg);
			msg.setVisibility(View.GONE);
		}
		else if (requestCode == TaskView.EDIT_GOOD && resultCode == RESULT_OK) {
			Good good = data.getExtras().getParcelable("Good");
			boolean toRemove = data.getExtras().getBoolean("toRemove");

			if (toRemove) {
				tc.removeTask(good);

				if (tc.getTasks() == null || tc.getTasks().length == 0) {
					TextView msg = (TextView) findViewById(R.id.no_goods_msg);
					msg.setVisibility(View.VISIBLE);
				}
			}
			else {
				tc.modifyTask(good);
			}
		}
	}

	@Override
	public void completeGoodFail() {
		Toast.makeText(this, DisplayStrings.COMPLETE_GOOD_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void completeGoodSuccess(Good good) {
		tc.completeTask(good);
	}

	@Override
	public void completeDutyFail() {
		Toast.makeText(this, DisplayStrings.COMPLETE_DUTY_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void completeDutySuccess(Duty d) {
		tc.completeTask(d);
	}
}
