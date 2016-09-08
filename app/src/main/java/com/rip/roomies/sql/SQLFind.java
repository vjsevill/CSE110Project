package com.rip.roomies.sql;

import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.Exceptions;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.SQLStrings;

import java.sql.ResultSet;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * A class that contains static methods for executing database commands relating to finding.
 */
public class SQLFind {
	private static final Logger log = Logger.getLogger(SQLFind.class.getName());


	/**
	 * Finds a user from the database using unique keys only
	 * @param user The object to use to search for a user on the database
	 * @return The full user object, or null if the user could not be found
	 */
	public static User findUser(User user) {
		ResultSet rs;

		try {
			// Log finding user
			log.info(InfoStrings.FIND_USER_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.FIND_USER,
					user.getId(), user.getUsername(),
					user.getEmail()));

			// If no rows, then finding failed
			if (rs == null || !rs.next()) {
				log.info(InfoStrings.FIND_USER_FAILED);
				return null;
			}
			else {
				// Get results of SQL statement. Columns are ID, last name, first name
				// username, and email.
				int resultID = rs.getInt("ID");
				String resultLastName = rs.getString("LastName");
				String resultFirstName = rs.getString("FirstName");
				String resultUsername = rs.getString("Username");
				String resultEmail = rs.getString("Email");
				byte[] profilePic = rs.getBytes("ProfileIcon");
				int groupId = rs.getInt("GroupID");

				// This checks if multiple rows were returned. If so, then finding failed
				if (rs.next()) {
					log.info(InfoStrings.FIND_USER_FAILED);
					return null;
				}

				//debug statement
				log.info(String.format(Locale.US, InfoStrings.FIND_USER_SUCESSFUL,
						resultID, resultLastName, resultFirstName, resultUsername, resultEmail));

				// Return a new user object
				return new User(resultID, resultFirstName, resultLastName, resultUsername,
						resultEmail, null, profilePic, groupId);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Finds a group from the database using unique keys only
	 * @param group The object to use to search for a user on the database
	 * @return The full user object, or null if the user could not be found
	 */
	public static Group findGroup(Group group) {
		ResultSet rs;

		try {
			// Log finding group
			log.info(InfoStrings.FIND_GROUP_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.FIND_GROUP,
					group.getId(), SQLQuery.sanitize(group.getName())));

			// If no rows, then finding failed
			if (rs == null || !rs.next()) {
				log.info(InfoStrings.FIND_GROUP_FAILED);
				return null;
			}
			else {
				// Get results of SQL statement. Columns are ID, name, and description
				int resultID = rs.getInt("ID");
				String resultName = rs.getString("Name");
				String resultDescription = rs.getString("Description");

				// This checks if multiple rows were returned. If so, then finding failed
				if (rs.next()) {
					log.info(InfoStrings.FIND_GROUP_FAILED);
					return null;
				}

				//debug statement
				log.info(String.format(Locale.US, InfoStrings.FIND_GROUP_SUCESSFUL,
						resultID, resultName, resultDescription));

				// Return a new group object
				return new Group(resultID, resultName, resultDescription);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}
}