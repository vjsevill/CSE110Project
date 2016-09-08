package com.rip.roomies.sql;

import com.rip.roomies.models.Bill;
import com.rip.roomies.models.Bulletin;
import com.rip.roomies.models.Duty;
import com.rip.roomies.models.DutyLog;
import com.rip.roomies.models.Good;
import com.rip.roomies.models.GoodLog;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.Task;
import com.rip.roomies.models.User;
import com.rip.roomies.util.Exceptions;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.SQLStrings;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * A class that contains static methods for executing database commands relating to getting.
 */
public class SQLGet {
	private static final Logger log = Logger.getLogger(SQLGet.class.getName());

	private static final int TYPE_DUTY = 1;
	private static final int TYPE_GOOD = 2;

	/**
	 * Finds a specific group the user belongs to, along with all members
	 * @param group The group used to query the database for users
	 * @return A properly filled group, with all the users in that group
	 */
	public static Group getGroupUsers(Group group) {
		ResultSet rs;

		try {
			// Log finding user
			log.info(InfoStrings.GET_GROUP_USERS_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_GROUP_USERS,
					group.getId()));

			// If no rows, then finding failed
			if (rs == null) {
				log.info(InfoStrings.GET_GROUP_USERS_FAILED);
				return null;
			}
			else {
				ArrayList<User> users = new ArrayList<>();

				while (rs.next()){
					int id = rs.getInt("ID");
					String first = rs.getString("FirstName");
					String last = rs.getString("LastName");
					String username = rs.getString("Username");
					String email = rs.getString("Email");
					byte[] profilePic = rs.getBytes("ProfileIcon");
					users.add(new User(id, first, last, username, email, null, profilePic));
				}

				User[] temp = new User[users.size()];

				// Return a new user object
				temp = users.toArray(temp);

				//debug statement
				log.info(InfoStrings.GET_GROUP_USERS_SUCCESSFUL);

				// Return a new user object
				return new Group(group.getId(), group.getName(), group.getDescription(), temp);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}

	}

	/**
	 * Finds a user from the database using unique keys only
	 * @param user The object to use to search for a user on the database
	 * @return The full user object, or null if the user could not be found
	 */
	public static Group[] getGroups(User user) {
		ResultSet rs;

		try {
			// Log finding user
			log.info(InfoStrings.GET_GROUPS_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_GROUPS,
					user.getId()));

			// If no rows, then finding failed
			if (rs == null || !rs.next()) {
				log.info(InfoStrings.GET_GROUPS_FAILED);
				return null;
			}
			else {
				ArrayList<Group> groups = new ArrayList<>();

				do {
					int id = rs.getInt("ID");
					String name = rs.getString("Name");
					String description = rs.getString("Description");
					groups.add(new Group(id, name, description));
				} while(rs.next());

				//debug statement
				log.info(InfoStrings.GET_GROUPS_SUCCESSFUL);

				Group[] temp = new Group[groups.size()];

				// Return a new user object
				return groups.toArray(temp);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Finds a duty from the database using unique keys only
	 * @param group The group to use to search for a duty on the database
	 * @return The full set of group duties, or null if none could not be found
	 */
	public static Duty[] getGroupDuties(Group group) {
		ResultSet rs;

		try {
			// Log finding user
			log.info(InfoStrings.GET_GROUP_DUTIES_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_GROUP_DUTIES,
					group.getId()));

			// If no rows, then finding failed
			if (rs == null) {
				log.info(InfoStrings.GET_GROUP_DUTIES_FAILED);
				return null;
			}
			else {
				ArrayList<Duty> duties = new ArrayList<>();

				while(rs.next()) {
					int resultId = rs.getInt("DutyID");
					String resultName = rs.getString("Name");
					String resultDescription = rs.getString("Description");
					int resultGroup = rs.getInt("DutyGroupID");
					Timestamp time = rs.getTimestamp("TimeReminded");
					User u = new User(
							rs.getInt("ID"),
							rs.getString("FirstName"),
							rs.getString("LastName"),
							rs.getString("Username"),
							rs.getString("Email"),
							null,
							rs.getBytes("ProfileIcon")
					);

					Duty temp = new Duty(resultId, resultName, resultDescription, resultGroup, u, null);
					temp.setTime(time);
					temp = temp.getRotation();

					duties.add(temp);

				}

				//debug statement
				log.info(InfoStrings.GET_GROUP_DUTIES_SUCCESSFUL);

				Duty[] temp = new Duty[duties.size()];

				// Return a new user object
				return duties.toArray(temp);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Gets the list of tasks that belong to the user in the current group context
	 * @param group The group the user belongs to
	 * @param user The user to search for tasks on the database
	 * @return The full set of user's tasks, or null if none could not be found
	 */
	public static Task[] getUserTasks(Group group, User user) {
		ResultSet rs;

		try {
			// Log finding user
			log.info(InfoStrings.GET_USER_TASKS_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_USER_TASKS,
					group.getId(), user.getId()));

			// If no rows, then finding failed
			if (rs == null) {
				log.info(InfoStrings.GET_USER_TASKS_FAILED);
				return null;
			}
			else {
				ArrayList<Task> tasks = new ArrayList<>();

				while(rs.next()){
					int resultType = rs.getInt("Type");
					int resultId = rs.getInt("ID");
					String resultName = rs.getString("Name");
					String resultDescription = rs.getString("Description");
					int resultGroup = rs.getInt("GroupID");

					SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_USER_TASKS,
							group.getId(), user.getId()));

					Task temp;

					if (resultType == TYPE_DUTY) {


						temp = new Duty(resultId, resultName, resultDescription, resultGroup, user, null);
					}
					else if (resultType == TYPE_GOOD){
//						ResultSet rset = SQLQuery.execute( "EXEC GetGoodTimeDiff @id = " + resultId);
//						rset.next();
						temp = new Good(resultId, resultName, resultDescription, resultGroup, user, null);
					}
					else {
						return null;
					}
					temp = temp.getRotation();

					tasks.add(temp);
				}

				//debug statement
				log.info(InfoStrings.GET_USER_TASKS_SUCCESSFUL);

				Task[] temp = new Task[tasks.size()];

				// Return a new user object
				return tasks.toArray(temp);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Finds a duty from the database using unique keys only
	 * @param duty The group the user belongs to
	 * @return The full set of user's duties, or null if none could not be found
	 */
	public static Duty getDutyUsers(Duty duty) {
		ResultSet rs;

		try {
			// Log finding user
			log.info(InfoStrings.GET_DUTY_USERS_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_DUTY_USERS,
					duty.getId()));

			// If no rows, then finding failed
			if (rs == null || !rs.next()) {
				log.info(InfoStrings.GET_DUTY_USERS_FAILED);
				return null;
			}
			else {
				ArrayList<User> users = new ArrayList<>();

				do {
					int id = rs.getInt("ID");
					String first = rs.getString("FirstName");
					String last = rs.getString("LastName");
					String username = rs.getString("Username");
					String email = rs.getString("Email");
					byte[] profilePic = rs.getBytes("ProfileIcon");

					users.add(new User(id, first, last, username, email, null, profilePic));
				} while(rs.next());

				//debug statement
				log.info(InfoStrings.GET_DUTY_USERS_SUCCESSFUL);

				User[] temp = new User[users.size()];
				temp = users.toArray(temp);

				// Return a new user object
				Duty duty2 = new Duty(duty.getId(), duty.getName(), duty.getDescription(),
						duty.getGroupId(), duty.getAssignee(), temp);
				duty2.setTime(duty.getTime());
				return duty2;
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Finds all duty logs from database pertaining to a certain group
	 * @param group The group to acquire duty logs for
	 * @return The full set of duty logs belonging to the group
	 */
	public static DutyLog[] getGroupDutyLogs(Group group) {
		ResultSet rs;
		ResultSet getUser;

		try {
			// Log finding user
			log.info(InfoStrings.GET_GROUP_DUTY_LOGS_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_GROUP_DUTY_LOGS,
					group.getId()));

			// If no rows, then finding failed
			if (rs == null) {
				log.info(InfoStrings.GET_GROUP_DUTY_LOGS_FAILED);
				return null;
			}
			else {
				ArrayList<DutyLog> logs = new ArrayList<>();

				while(rs.next()){
					int resultId = rs.getInt("ID");
					String resultName = rs.getString("Name");
					String resultDescription = rs.getString("Description");
					int resultGroup = rs.getInt("DutyGroupID");
					Date completeDate = rs.getDate("CompletionDate");
					int dutyId = rs.getInt("DutyID");
					int assigneeId = rs.getInt("AssigneeID");

					getUser = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_USER_BY_ID,
							assigneeId));

					if(getUser == null || !getUser.next()) {
						log.info(InfoStrings.GET_GROUP_DUTY_LOGS_FAILED);
						return null;
					}

					int userId = getUser.getInt("ID");
					String first = getUser.getString("FirstName");
					String last = getUser.getString("LastName");
					String username = getUser.getString("Username");
					String email = getUser.getString("Email");
					byte[] profilePic = getUser.getBytes("ProfileIcon");

					User assignee = new User(userId, first, last, username, email, null, profilePic);

					DutyLog temp = new DutyLog(resultId, resultName, resultDescription, resultGroup,
							completeDate, dutyId, assignee);

					logs.add(temp);

				}

				//debug statement
				log.info(InfoStrings.GET_GROUP_DUTY_LOGS_SUCCESSFUL);

				DutyLog[] temp = new DutyLog[logs.size()];

				// Return a new user object
				return logs.toArray(temp);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Finds all bills associated with a user
	 * @param user The user to acquire bills for
	 * @return The full set of bills associated with this user
	 */
	public static Bill[] getUserBills(User user) {
		ResultSet rs;
		try {
			log.info(InfoStrings.GET_BILLS_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_BILLS, user.getId()));

			// If no rows, then finding failed
			if (rs == null) {
				log.info(InfoStrings.GET_BILLS_FAILED);
				return null;
			}
			else {
				ArrayList<Bill> bills = new ArrayList<>();

				while (rs.next()) {
					int resultId = rs.getInt("ID");
					int resultOwnerId = rs.getInt("OwnerID");
//					String resultName = rs.getString("Name");
					String resultDescription = rs.getString("Description");
					float resultAmount = rs.getFloat("Amount");
					int resultOweeID = rs.getInt("OweeID");
					Timestamp time = rs.getTimestamp("TimeReminded");

					String resultName;
					if(User.getActiveUser().getId() == resultOweeID) {
						resultAmount = -resultAmount;
						resultName = Group.getActiveGroup().getMember(resultOwnerId).getName();
					}
					else{
//						resultName = User.getActiveUser().getName();
						resultName = Group.getActiveGroup().getMember(resultOweeID).getName();
					}


					Bill temp = new Bill(resultOwnerId, resultId, resultName, resultDescription,
							resultAmount, resultOweeID);
					temp.setTime(time);
					bills.add(temp);
				}

				//debug statement
				log.info(String.format(Locale.US, InfoStrings.GET_BILLS_SUCCESSFUL,
						User.getActiveUser().getId()));

				Bill[] temp = new Bill[bills.size()];

				// Return a new user object
				return bills.toArray(temp);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Finds all bulletins associated with a group
	 * @param group The group to acquire bulletins for
	 * @return The full set of bulletins associated with this user
	 */
	public static Bulletin[] getUserBulletins(Group group) {
		ResultSet rs;
		try {
			log.info(InfoStrings.GET_BULLETINS_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_BULLETINS, group.getId()));

			// If no rows, then finding failed
			if (rs == null) {
				log.info(InfoStrings.GET_BULLETINS_FAILED);
				return null;
			}
			else {
				ArrayList<Bulletin> bulls = new ArrayList<>();

				while (rs.next()) {
					int resultId = rs.getInt("ID");
					int resultGroupId = rs.getInt("GroupID");
					String resultContent = rs.getString("Content");

					Bulletin temp = new Bulletin(resultId, resultGroupId, resultContent);

					bulls.add(temp);
				}

				//debug statement
				log.info(String.format(Locale.US, InfoStrings.GET_BULLETINS_SUCCESSFUL,
						Group.getActiveGroup().getId()));

				Bulletin[] temp = new Bulletin[bulls.size()];

				// Return a new user object
				return bulls.toArray(temp);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Finds a good from the database using unique keys only
	 * @param group The group to use to search for a good on the database
	 * @return The full set of group goods, or null if none could not be found
	 */
	public static Good[] getGroupGoods(Group group) {
		ResultSet rs;

		try {
			// Log finding user
			log.info(InfoStrings.GET_GROUP_GOODS_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_GROUP_GOODS,
					group.getId()));

			// If no rows, then finding failed
			if (rs == null) {
				log.info(InfoStrings.GET_GROUP_GOODS_FAILED);
				return null;
			}
			else {
				ArrayList<Good> goods = new ArrayList<>();

				while(rs.next()) {
					int resultId = rs.getInt("GoodID");
					String resultName = rs.getString("Name");
					String resultDescription = rs.getString("Description");
					int resultGroup = rs.getInt("GroupID");
					Timestamp time = rs.getTimestamp("TimeReminded");
					User u = new User(
							rs.getInt("ID"),
							rs.getString("FirstName"),
							rs.getString("LastName"),
							rs.getString("Username"),
							rs.getString("Email"),
							null,
							rs.getBytes("ProfileIcon")
					);

					Good temp = new Good(resultId, resultName, resultDescription, resultGroup, u, null);
					temp = temp.getRotation();
					temp.setTime(time);
					goods.add(temp);

				}

				//debug statement
				log.info(InfoStrings.GET_GROUP_GOODS_SUCCESSFUL);

				Good[] temp = new Good[goods.size()];

				// Return a new user object
				return goods.toArray(temp);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Finds a good from the database using unique keys only
	 * @param good The good the user belongs to
	 * @return The full set of users to a good, or null if none could not be found
	 */
	public static Good getGoodUsers(Good good) {
		ResultSet rs;

		try {
			// Log finding user
			log.info(InfoStrings.GET_GOOD_USERS_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_GOOD_USERS,
					good.getId()));

			// If no rows, then finding failed
			if (rs == null || !rs.next()) {
				log.info(InfoStrings.GET_GOOD_USERS_FAILED);
				return null;
			}
			else {
				ArrayList<User> users = new ArrayList<>();

				do {
					int id = rs.getInt("ID");
					String first = rs.getString("FirstName");
					String last = rs.getString("LastName");
					String username = rs.getString("Username");
					String email = rs.getString("Email");
					byte[] profilePic = rs.getBytes("ProfileIcon");

					users.add(new User(id, first, last, username, email, null, profilePic));
				} while(rs.next());

				//debug statement
				log.info(InfoStrings.GET_GOOD_USERS_SUCCESSFUL);

				User[] temp = new User[users.size()];
				temp = users.toArray(temp);

				// Return a new user object
				Good temp_good = new Good(good.getId(), good.getName(), good.getDescription(),
						good.getGroupId(), good.getAssignee(), temp);
				temp_good.setTime(good.getTime());
				return temp_good;
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}

	/**
	 * Finds all good logs from database pertaining to a certain group
	 * @param group The group to acquire good logs for
	 * @return The full set of good logs belonging to the group
	 */
	public static GoodLog[] getGroupGoodLogs(Group group) {
		ResultSet rs;
		ResultSet getUser;

		try {
			// Log finding user
			log.info(InfoStrings.GET_GROUP_GOOD_LOGS_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_GROUP_GOOD_LOGS,
					group.getId()));

			// If no rows, then finding failed
			if (rs == null) {
				log.info(InfoStrings.GET_GROUP_GOOD_LOGS_FAILED);
				return null;
			}
			else {
				ArrayList<GoodLog> logs = new ArrayList<>();

				while(rs.next()){
					int resultId = rs.getInt("ID");
					String resultName = rs.getString("Name");
					String resultDescription = rs.getString("Description");
					float price = rs.getFloat("Price");
					int resultGroup = rs.getInt("GroupID");
					Date completeDate = rs.getDate("PurchaseDate");
					int dutyId = rs.getInt("CommonGoodID");
					int assigneeId = rs.getInt("PurchaserID");

					getUser = SQLQuery.execute(String.format(Locale.US, SQLStrings.GET_USER_BY_ID,
							assigneeId));

					if(getUser == null || !getUser.next()) {
						log.info(InfoStrings.GET_GROUP_GOOD_LOGS_FAILED);
						return null;
					}

					int userId = getUser.getInt("ID");
					String first = getUser.getString("FirstName");
					String last = getUser.getString("LastName");
					String username = getUser.getString("Username");
					String email = getUser.getString("Email");
					byte[] profilePic = getUser.getBytes("ProfileIcon");

					User assignee = new User(userId, first, last, username, email, null, profilePic);

					GoodLog temp = new GoodLog(resultId, resultName, resultDescription, resultGroup,
							completeDate, dutyId, assignee, price);

					logs.add(temp);

				}

				//debug statement
				log.info(InfoStrings.GET_GROUP_GOOD_LOGS_SUCCESSFUL);

				GoodLog[] temp = new GoodLog[logs.size()];

				// Return a new user object
				return logs.toArray(temp);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}
}