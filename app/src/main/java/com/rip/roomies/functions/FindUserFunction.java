package com.rip.roomies.functions;

import com.rip.roomies.models.User;

/**
 * An interface designed for objects that need to be able to find users.
 */
public interface FindUserFunction {
	void findUserFail();
	void findUserSuccess(User user);
}
