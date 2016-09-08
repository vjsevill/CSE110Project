package com.rip.roomies.models;

import java.sql.Date;

/**
 * This object represents a log of a completed duty.
 */
public class DutyLog extends TaskLog {
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
	public DutyLog(int id, String name, String description, int groupId,
	                  Date completion, int taskId, User assignee) {
		super(id, name, description, groupId, completion, taskId, assignee);
	}
}
