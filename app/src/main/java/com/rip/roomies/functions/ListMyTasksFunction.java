package com.rip.roomies.functions;

import com.rip.roomies.models.Task;

/**
 * Created by Kanurame on 5/19/2016.
 */
public interface ListMyTasksFunction {
	void listMyTasksFail();
	void listMyTasksSuccess(Task[] tasks);
}
