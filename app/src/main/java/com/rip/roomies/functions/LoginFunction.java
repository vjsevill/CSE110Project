package com.rip.roomies.functions;

import com.rip.roomies.models.User;

/**
 * An interface designed for objects that need to be able to login.
 */
public interface LoginFunction {
	void loginFail();
	void loginSuccess(User user);
}
