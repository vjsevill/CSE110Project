package com.rip.roomies.sql;

import com.rip.roomies.models.Bill;
import com.rip.roomies.models.Bulletin;
import com.rip.roomies.models.Duty;
import com.rip.roomies.models.Good;
import com.rip.roomies.models.User;
import com.rip.roomies.util.Conversions;
import com.rip.roomies.util.Exceptions;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.SQLStrings;
import com.rip.roomies.util.WarningStrings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * This is a SQL class for modifying models.
 */
public class SQLModify {
	private static final Logger log = Logger.getLogger(SQLModify.class.getName());
	private static final int MAX_USERS_STRING_LENGTH = 1000;

	public static Duty completeDuty(Duty duty) {
		ResultSet rset;

		try {
			//debug statement
			log.info(InfoStrings.COMPLETEDUTY_SQL);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.COMPLETE_DUTY,
					duty.getId()));

			// error happened when contacting sql server
			if (rset == null || !rset.next()) {
				// debug statement
				log.info(InfoStrings.COMPLETEDUTY_FAILED);
				return null;
			}
			// if there is a rset
			else {
				//explain what each column corresponds to
				int resultId = rset.getInt("DutyID");
				String resultName = rset.getString("Name");
				String resultDescription = rset.getString("Description");
				int dutyGroupId = rset.getInt("DutyGroupID");

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
				log.info(String.format(Locale.US, InfoStrings.COMPLETEDUTY_SUCCESSFUL,
						resultId, resultName, resultDescription, dutyGroupId));

				return new Duty(resultId, resultName, resultDescription, dutyGroupId,
						u, duty.getRotation().getUsers());
			}
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	public static Duty modifyDuty(Duty duty) {
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
			log.info(InfoStrings.MODIFYDUTY_SQL);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.MODIFY_DUTY,
					duty.getId(), SQLQuery.sanitize(duty.getName()),
					SQLQuery.sanitize(duty.getDescription()), SQLQuery.sanitize(usersString)));

			// error happened when contacting sql server
			if (rset == null || !rset.next()) {
				// debug statement
				log.info(InfoStrings.MODIFYDUTY_FAILED);
				return null;
			}
			// if there is a rset
			else {
				//explain what each column corresponds to
				int resultId = rset.getInt("DutyID");
				String resultName = rset.getString("Name");
				String resultDescription = rset.getString("Description");
				int dutyGroupId = rset.getInt("DutyGroupID");

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
				log.info(String.format(Locale.US, InfoStrings.MODIFYDUTY_SUCCESSFUL,
						resultId, resultName, resultDescription, dutyGroupId));

				return new Duty(resultId, resultName, resultDescription, dutyGroupId,
						u, duty.getUsers());
			}
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}


	public static User updateProfile (int groupID, int userID, String firstName, String lastName,
	                                     String email, String groupDescription, byte[] profilePic) {
		ResultSet rset;

		try {

			//debug statement
			//log.info(InfoStrings.MODIFYDUTY_SQL);
			String profilePicString = Conversions.byteArrayToHexString(profilePic);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.UPDATE_PROFILE,
					userID, groupID, firstName, lastName, email, groupDescription));

			// error happened when contacting sql server
			if(rset == null || !rset.next()) {
				// debug statement
				//log.info(InfoStrings.MODIFYDUTY_FAILED);
				return null;
			}
			// if there is a rset
			else {
				byte[] image = profilePic;

				// debug statement
				//log.info(String.format(Locale.US, InfoStrings.MODIFYDUTY_SUCCESSFUL,
				//		resultId, resultName, resultDescription, dutyGroupId));
				PreparedStatement pstmt = SQLQuery.getPreparedStatement(SQLStrings.SET_PROFILE_ICON);
				if (pstmt != null) {
					pstmt.setInt(1, userID);
					pstmt.setBytes(2, profilePic);
					ResultSet rs = pstmt.executeQuery();

					if (rs != null && rs.next()) {
						image = rs.getBytes("ProfileIcon");
					}
				}

				return new User(rset.getInt("ID"),
						rset.getString("FirstName"),
						rset.getString("LastName"),
						rset.getString("Username"),
						rset.getString("Email"),
						null,
						image);
			}
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}

	}


	public static Integer changePassword(int userID, String newPassword) {
		ResultSet rset;

		try {

			//debug statement
			//log.info(InfoStrings.MODIFYDUTY_SQL);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.MODIFY_PASSWORD,
					userID, newPassword));

			// error happened when contacting sql server
			if(rset == null || !rset.next()) {
				// debug statement
				//log.info(InfoStrings.MODIFYDUTY_FAILED);
				return null;
			}
			// if there is a rset
			else {

				// debug statement
				//log.info(String.format(Locale.US, InfoStrings.MODIFYDUTY_SUCCESSFUL,
				//		resultId, resultName, resultDescription, dutyGroupId));

				return 1;
			}
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}

	}

	public static void modifyBill(Bill billToModify) {

		try {
			SQLQuery.execute(String.format(Locale.US, SQLStrings.MODIFY_BILL_SQL, billToModify.getRowID(),
					billToModify.getName(), billToModify.getDescription(),
					Math.abs(billToModify.getAmount())));
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return;
		}
	}

	public static void modifyBulletin(Bulletin bullToModify) {

		try {
			SQLQuery.execute(String.format(Locale.US, SQLStrings.MODIFY_BULLETIN, bullToModify.getRowID(),
					bullToModify.getContent()));
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return;
		}
	}

		public static Good completeGood (Good good,double price){
			ResultSet rset;

			try {
				//debug statement
				log.info(InfoStrings.COMPLETEGOOD_SQL);

				// get the result table from query execution through sql
				rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.COMPLETE_GOOD,
						good.getId(), price));

				// error happened when contacting sql server
				if (rset == null || !rset.next()) {
					// debug statement
					log.info(InfoStrings.COMPLETEGOOD_FAILED);
					return null;
				}
				// if there is a rset
				else {
					//explain what each column corresponds to
					int resultId = rset.getInt("GoodID");
					String resultName = rset.getString("Name");
					String resultDescription = rset.getString("Description");
					int dutyGroupId = rset.getInt("GroupID");

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
					log.info(String.format(Locale.US, InfoStrings.COMPLETEGOOD_SUCCESSFUL,
							resultId, resultName, resultDescription, dutyGroupId));

					return new Good(resultId, resultName, resultDescription, dutyGroupId,
							u, good.getRotation().getUsers());
				}
			}
			catch (Exception e) {
				log.severe(Exceptions.stacktraceToString(e));
				return null;
			}
		}

	public static Good modifyGood(Good good) {
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
			log.info(InfoStrings.MODIFYGOOD_SQL);

			// get the result table from query execution through sql
			rset = SQLQuery.execute(String.format(Locale.US, SQLStrings.MODIFY_GOOD,
					good.getId(), SQLQuery.sanitize(good.getName()),
					SQLQuery.sanitize(good.getDescription()), SQLQuery.sanitize(usersString)));

			// error happened when contacting sql server
			if (rset == null || !rset.next()) {
				// debug statement
				log.info(InfoStrings.MODIFYGOOD_FAILED);
				return null;
			}
			// if there is a rset
			else {
				//explain what each column corresponds to
				int resultId = rset.getInt("GoodID");
				String resultName = rset.getString("Name");
				String resultDescription = rset.getString("Description");
				int dutyGroupId = rset.getInt("GroupID");

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
				log.info(String.format(Locale.US, InfoStrings.MODIFYGOOD_SUCCESSFUL,
						resultId, resultName, resultDescription, dutyGroupId));

				return new Good(resultId, resultName, resultDescription, dutyGroupId,
						u, good.getUsers());
			}
		}
		catch (Exception e) {
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}
}