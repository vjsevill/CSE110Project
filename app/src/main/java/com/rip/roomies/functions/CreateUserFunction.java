package com.rip.roomies.functions;

import com.rip.roomies.models.User;

/**
 * An interface designed for objects that need to be able to create users.
 */
public interface CreateUserFunction {
	void createUserFail();
	void createUserSuccess(User user);
}
