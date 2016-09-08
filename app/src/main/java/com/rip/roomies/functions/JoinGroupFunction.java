package com.rip.roomies.functions;

import com.rip.roomies.models.Group;

/**
 * An interface designed for objects that need to be able to join groups.
 */
public interface JoinGroupFunction {
	void joinGroupFail();
	void joinGroupSuccess(Group group);
}
