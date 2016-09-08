package com.rip.roomies.functions;

import com.rip.roomies.models.Group;

/**
 * An interface designed for objects that need to be able to add users to a group.
 */
public interface InviteUsersFunction {
	void inviteUsersFail();
	void inviteUsersSuccess(Group group);
}
