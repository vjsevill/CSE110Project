package com.rip.roomies.sql;

import com.rip.roomies.models.Bill;
import com.rip.roomies.models.Bulletin;
import com.rip.roomies.models.Duty;
import com.rip.roomies.models.Good;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.Exceptions;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.SQLStrings;
import com.rip.roomies.util.WarningStrings;

import java.sql.ResultSet;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * A class that contains static methods for executing database commands relating to creation.
 */
public class SQLCreate {
	private static final Logger log = Logger.getLogger(SQLCreate.class.getName());
	private static final int MAX_USERS_STRING_LENGTH = 1000;

	/**
	 * Use SQLQuery class to create an connection and insert a duty into the duty table on the
	 * database. Additionally, the user rotation order is inserted into the users_duty table.
	 * If successful the Duty object will be returned, otherwise return null
	 *
	 * @param duty The duty that should be created on the database
	 * @return Duty - the Duty object with all the group info just been created
	 */
	public static Duty createDuty(Duty duty) {
		ResultSet rset;
		String usersString = "";

		try {
			// Turn users array into a delineated string
			for (User user : duty.getUsers()) {
				usersString += user.getId();
				usersString += SQLStrings.LIST_DELIMITER;
			}

			// Can only take max length of 1000, so truncate
			if (usersString.length() > MAX_USERS_STRING_LENGTH) {
				log.warning(String.format(Locale.US, WarningStrings.ADD_USERS_TO_GROUP_TRUNCATE,
						MAX_USERS_STRING_LENGTH));
				usersString = usersString.substring(0, MAX_USERS_STRING_LENGTH);
				usersString = usersString.substring(0, usersString.lastIndexOf(SQLStrings.LIST_DELIMITER) + 1);
			}

			//debug statement
			log.info(InfoStrings.CREATEDUTY_SQL);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.CREATE_DUTY,
					SQLQuery.sanitize(duty.getName()), SQLQuery.sanitize(duty.getDescription()),
					duty.getGroupId(), SQLQuery.sanitize(usersString)));

			// error happened when contacting sql server
			if(rset == null || !rset.next()) {
				// debug statement
				log.info(InfoStrings.CREATEDUTY_FAILED);
				return null;
			}
			// if there is a rset
			else {
				//explain what each column corresponds to
				int resultId = rset.getInt("DutyID");
				String resultName = rset.getString("Name");
				String resultDescription = rset.getString("Description");
				int resultGroup = rset.getInt("DutyGroupID");

				User u = new User(
						rset.getInt("ID"),
						rset.getString("FirstName"),
						rset.getString("LastName"),
						rset.getString("Username"),
						rset.getString("Email"),
						null,
						rset.getBytes("ProfileIcon")
				);

				// debug statement
				log.info(String.format(Locale.US, InfoStrings.CREATEDUTY_SUCCESSFUL,
						resultId, resultName, resultDescription, resultGroup));

				return new Duty(resultId, resultName, resultDescription, resultGroup,
						u, duty.getUsers());
			}
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Use SQLQuery class to create an connection and insert a group into the group table on the
	 * database, if is successful the User object will be returned, otherwise return null
	 *
	 * @param group The group that should be created on the database
	 * @return Group - the Group object with all the group info just been created
	 */
	public static Group createGroup(Group group, User user) {
		ResultSet rset;

		try {
			//debug statement
			log.info(InfoStrings.CREATEGROUP_SQL);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.CREATE_GROUP,
					SQLQuery.sanitize(group.getName()), SQLQuery.sanitize(group.getDescription()),
					user.getId()));

			// group already exist
			if (rset == null || !rset.next()) {
				//debug statement
				log.info(InfoStrings.CREATEGROUP_FAILED);
				return null;
			}
			//if there's a rset
			else {
				//first column is id, second is lastname, third is firstname
				//so pass the column number accordingly to get the info about user
				int resultID = rset.getInt("ID");
				String resultName = rset.getString("Name");
				String resultDescription = rset.getString("Description");

				//debug statement
				log.info(String.format(Locale.US, InfoStrings.CREATEGROUP_SUCCESSFULL,
						resultID, resultName, resultDescription));

				return new Group(resultID, resultName, resultDescription);

			}
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Use SQLQuery class to create an connection and insert a user into the user table on the
	 * database, if is successful the User object will be returned, otherwise return null
	 *
	 * @param user The user to be created on the database
	 * @return User - the User object with all the user info just been created
	 */
	public static User createUser(User user) {
		ResultSet rset;

		try {
			log.info(InfoStrings.CREATEUSER_SQL);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.CREATE_USER,
					SQLQuery.sanitize(SQLQuery.sanitize(user.getFirstName())),
					SQLQuery.sanitize(user.getLastName()),
					SQLQuery.sanitize(user.getUsername()), SQLQuery.sanitize(user.getEmail()),
					SQLQuery.sanitize(user.getPassword())));

			// group already exist
			if (rset == null || !rset.next()) {
				//debug statement
				log.info(InfoStrings.CREATEUSER_FAILED);
				return null;
			}
			//if there's a rset
			else {

				//second column is lastname, third is firstname
				//so pass the column number accordingly to get the info about user
				int resultID = rset.getInt("ID");
				String resultLastName = rset.getString("LastName");
				String resultFirstName = rset.getString("FirstName");
				String resultUsername = rset.getString("Username");
				String resultEmail = rset.getString("Email");
				byte[] profilePic = rset.getBytes("ProfileIcon");

				//debug statement
				log.info(String.format(Locale.US, InfoStrings.CREATEUSER_SUCCESSFULL, resultID,
						resultLastName, resultFirstName, resultUsername, resultEmail));

				return new User(resultID, resultFirstName,
						resultLastName, resultUsername, resultEmail, null, profilePic);

			}
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	public static Bill createBill(Bill bill) {
		ResultSet rset;

		try {
			log.info(InfoStrings.CREATEBILL_SQL);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.CREATE_BILL,
					bill.getOwnerID(), bill.getName(), bill.getDescription(),
					bill.getAmount(), bill.getOweeID()));


			rset.next();

			//second column is name, third is description, 4th is amount
			//so pass the column number accordingly to get the info about the bill
			int resultID = rset.getInt("ID");
			int resultOwnerID = rset.getInt("OwnerID");
//			String resultName = rset.getString("name");
			String resultDescription = rset.getString("Description");
			float resultAmount = rset.getFloat("Amount");
			int resultOweeID = rset.getInt("OweeID");
			String resultName;
			if(User.getActiveUser().getId() == resultOweeID) {
				resultAmount = -resultAmount;
				resultName = Group.getActiveGroup().getMember(resultOwnerID).getName();
			}
			else{
//				resultName = User.getActiveUser().getName();
				resultName = Group.getActiveGroup().getMember(resultOweeID).getName();
			}

			//debug statement
			log.info(String.format(Locale.US, InfoStrings.CREATEBILL_SUCCESSFULL, resultID,
					resultName, resultDescription, resultAmount));

			return new Bill(resultOwnerID, resultID, resultName, resultDescription, resultAmount, resultOweeID);
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));

			return null;
		}
	}

	public static Bulletin createBulletin(Bulletin bull) {
		ResultSet rset;

		try {
			log.info(InfoStrings.CREATE_BULLETIN_SQL);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.CREATE_BULLETIN,
					Group.getActiveGroup().getId(), SQLQuery.sanitize(bull.getContent())));

			rset.next();

			int resultID = rset.getInt("ID");
			int resultGroupID = rset.getInt("GroupID");
			String resultContent = rset.getString("Content");


			//debug statement
			log.info(String.format(Locale.US, InfoStrings.CREATE_BULLETIN_SUCCESSFUL, resultID,
					resultGroupID, resultContent));

			return new Bulletin(resultID, resultGroupID, resultContent);
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));

			return null;
		}
	}


	/**
	 * Use SQLQuery class to create an connection and insert a good into the goods table on the
	 * database. Additionally, the user rotation order is inserted into the users_duty table.
	 * If successful the Good object will be returned, otherwise return null
	 *
	 * @param good The Good that should be created on the database
	 * @return Good - the Good object with all the group info just been created
	 */
	public static Good createGood(Good good) {
		ResultSet rset;
		String usersString = "";

		try {
			// Turn users array into a delineated string
			for (User user : good.getUsers()) {
				usersString += user.getId();
				usersString += SQLStrings.LIST_DELIMITER;
			}

			// Can only take max length of 1000, so truncate
			if (usersString.length() > MAX_USERS_STRING_LENGTH) {
				log.warning(String.format(Locale.US, WarningStrings.ADD_USERS_TO_GROUP_TRUNCATE,
						MAX_USERS_STRING_LENGTH));
				usersString = usersString.substring(0, MAX_USERS_STRING_LENGTH);
				usersString = usersString.substring(0, usersString.lastIndexOf(SQLStrings.LIST_DELIMITER) + 1);
			}

			//debug statement
			log.info(InfoStrings.CREATEGOOD_SQL);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.CREATE_GOOD,
					SQLQuery.sanitize(good.getName()), SQLQuery.sanitize(good.getDescription()),
					good.getGroupId(), SQLQuery.sanitize(usersString)));

			// error happened when contacting sql server
			if(rset == null || !rset.next()) {
				// debug statement
				log.info(InfoStrings.CREATEGOOD_FAILED);
				return null;
			}
			// if there is a rset
			else {
				//explain what each column corresponds to
				int resultId = rset.getInt("GoodID");
				String resultName = rset.getString("Name");
				String resultDescription = rset.getString("Description");
				int resultGroup = rset.getInt("GroupID");

				User u = new User(
						rset.getInt("ID"),
						rset.getString("FirstName"),
						rset.getString("LastName"),
						rset.getString("Username"),
						rset.getString("Email"),
						null,
						rset.getBytes("ProfileIcon")
				);

				// debug statement
				log.info(String.format(Locale.US, InfoStrings.CREATEGOOD_SUCCESSFUL,
						resultId, resultName, resultDescription, resultGroup));

				return new Good(resultId, resultName, resultDescription, resultGroup,
						u, good.getUsers());
			}
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}
}