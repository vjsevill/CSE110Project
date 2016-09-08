package com.rip.roomies.util;

/**
 * A class containing a list of static warning strings for logging.
 */
public class WarningStrings {
	public static final String ADD_USERS_TO_GROUP_TRUNCATE = "Warning: string \"usersString\" " +
			"will be truncated when passed in to procedure AddUsersToGroup.\n" +
			"usersString cannot be more than %d characters long.";
}
