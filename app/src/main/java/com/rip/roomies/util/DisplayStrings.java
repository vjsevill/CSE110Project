package com.rip.roomies.util;

/**
 * A class containing a list of static strings meant to be displayed to the user.
 */
public class DisplayStrings {
	public static final long TOAST_SHORT_LENGTH = 2000;
	public static final long TOAST_LONG_LENGTH = 3500;

	public static final String MISSING_FIELD = "Required field missing: %s\n";
	public static final String TOO_SHORT = "%s is too short. Must be at least %d characters.\n";
	public static final String TOO_LARGE = "%s is too large. Max size: %s.\n";
	public static final String NOT_ALPHANUM = "%s must be alphanumeric or underscore.\n";
	public static final String FIRST_NOT_ALPHA = "First character of %s must be a letter.\n";
	public static final String INVALID = "%s entered is invalid.\n";
	public static final String FIELD_MISMATCH = "Fields %s and %s do not match.\n";

	public static final String CREATE_GROUP_FAIL = "Could not create a new group.\n" +
			"Check the logs for error.";
	public static final String CREATE_GROUP_SUCCESS = "Group \"%s\" successfully created.\n" +
			"Transferring to home page...";

	public static final String JOIN_GROUP_FAIL = "Could not join group.\n" +
			"Check the logs for error.";
	public static final String JOIN_GROUP_SUCCESS = "Group \"%s\" successfully joined.\n" +
			"Transferring to home page...";

	public static final String CREATE_USER_FAIL = "Could not create a new user.\n" +
			"Check the logs for error.";
	public static final String CREATE_USER_SUCCESS = "User \"%s\" successfully created.\n" +
			"Transferring to login page...";

	public static final String LOG_DUTY_FAIL = "Log Duty failed...\n";
	public static final String LOG_DUTY_SUCCESS = "Log Duty Successful...\n";

	public static final String LOG_GOOD_FAIL = "Log Good failed...\n";

	public static final String LOGIN_FAIL = "Login failed. Make sure your credentials are correct.";

	public static final String PASS_RETRIEVE_FAIL = "Could not retrieve password.\n" +
			"Check the logs for error.";
	public static final String PASS_RETRIEVE_SUCCESS = "Password reset successful.\n" +
			"Please check your email for your temporary password.";

	public static final String FIND_USER_FAIL = "Could not get that user.\n" +
			"Either username is incorrect or that user is already in a group.";

	public static final String INVITE_USERS_FAIL = "Could not invite user(s) to group.\n";

	public static final String CREATE_DUTY_SUCCESS = "Duty creation successful.";
	public static final String CREATE_DUTY_FAIL = "Could not create duty.\n" +
			"Please make sure all fields were filled correctly";
	public static final String MODIFY_DUTY_SUCCESS = "Duty modification successful.";
	public static final String MODIFY_DUTY_FAIL = "Could not modify duty.\n" +
			"Check the logs for error.";
	public static final String REMOVE_DUTY_SUCCESS = "Duty removal successful.";
	public static final String REMOVE_DUTY_FAIL = "Could not remove duty.\n" +
			"Check the logs for error.";
	public static final String COMPLETE_DUTY_SUCCESS = "Duty completion successful.";
	public static final String COMPLETE_DUTY_FAIL = "Could not complete duty.\n" +
			"Check the logs for error.";

	public static final String CREATE_GOOD_SUCCESS = "Good creation successful.";
	public static final String CREATE_GOOD_FAIL = "Could not create good.\n" +
			"Please make sure that all fields were filled correctly";
	public static final String MODIFY_GOOD_SUCCESS = "Good modification successful.";
	public static final String MODIFY_GOOD_FAIL = "Could not modify good.\n" +
			"Check the logs for error.";
	public static final String REMOVE_GOOD_SUCCESS = "Good removal successful.";
	public static final String REMOVE_GOOD_FAIL = "Could not remove good.\n" +
			"Check the logs for error.";

	public static final String COMPLETE_GOOD_SUCCESS = "Good completion successful.";
	public static final String COMPLETE_GOOD_FAIL = "Could not complete good.\n" +
			"Check the logs for error.";

	public static final String LIST_ALL_DUTIES_FAIL = "Could not list all duties.\n" +
			"Check the logs for error.";

	public static final String LIST_MY_TASKS_FAIL = "Could not list your tasks.\n" +
			"Check the logs for error.";

	public static final String LIST_ALL_GOODS_FAIL = "Could not list all goods.\n" +
			"Check the logs for error.";
}
