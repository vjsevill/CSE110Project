package com.rip.roomies.functions;

import com.rip.roomies.models.Group;

/**
 * An interface designed for objects that need to be able to create groups.
 */
public interface CreateGroupFunction {
	void createGroupFail();
	void createGroupSuccess(Group group);
}
