package com.rip.roomies.models;

import java.sql.Date;

/**
 * Created by johndoney on 5/29/16.
 */
public class GoodLog extends TaskLog {
	private float price;
	/**
	 * Creates a complete task log object.
	 *
	 * @param id          The id of the task log
	 * @param name        The name of the task
	 * @param description The description of the task
	 * @param groupId     The id of the group this belongs to
	 * @param completion  The date this task was completed
	 * @param taskId      The id of the task
	 * @param assignee  The id of the user who completed the task
	 */
	public GoodLog(int id, String name, String description, int groupId,
	               Date completion, int taskId, User assignee, float price) {
		super(id, name, description, groupId, completion, taskId, assignee);
		this.price = price;
	}

	public float getPrice() { return price; }
}
