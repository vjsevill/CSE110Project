package com.rip.roomies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.rip.roomies.sql.SQLCreate;
import com.rip.roomies.sql.SQLFind;
import com.rip.roomies.sql.SQLGet;
import com.rip.roomies.sql.SQLLogin;
import com.rip.roomies.sql.SQLModify;
import com.rip.roomies.util.InfoStrings;

import java.util.logging.Logger;

/**
 * This class represents a potential user of the Roomies system.
 */
public class User implements Parcelable {
	private int id = 0;
	private String firstName = "";
	private String lastName = "";
	private String username = "";
	private String email = "";
	private String password = "";
	private byte[] profilePic = null;
	private int groupId;

	private static User activeUser;
	private static final Logger log = Logger.getLogger(User.class.getName());

	public static final Parcelable.Creator<User> CREATOR
			= new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};

	//------- CONSTRUCTORS -------//

	/**
	 * Constructor used when logging in. Constructs the current active user using only the
	 * information provided at the Login prompt.
	 *
	 * @param username The User's login username.
	 * @param passwd   The User's password, as entered on the login screen.
	 */
	public User(String username, String passwd) {
		this.username = username;
		this.password = passwd;
	}

	/**
	 * Constructor used when retrieving a User's information from the database.
	 *
	 * @param id       The unique ID identifying this User in the database.
	 * @param username The User's login username.
	 * @param email    The email address used to contact this User.
	 */
	public User(int id, String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}

	/**
	 * Constructor used when creating a User. Populates all fields except id.
	 *
	 * @param firstName The User's first name.
	 * @param lastName  The User's last name.
	 * @param username  The User's login username.
	 * @param email     The email address used to contact this User.
	 * @param passwd    The User's password used to login.
	 */
	public User(String firstName, String lastName, String username, String email,
	            String passwd) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = passwd;
	}

	/**
	 * Constructor used when creating a User. Populates all fields, including id.
	 *
	 * @param firstName The User's first name.
	 * @param lastName  The User's last name.
	 * @param username  The User's login username.
	 * @param email     The email address used to contact this User.
	 * @param passwd    The User's password used to login.
	 * @param profilePic The bit array used to upload User's picture
	 */
	public User(int id, String firstName, String lastName, String username, String email,
	            String passwd, byte[] profilePic) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = passwd;
		this.profilePic = profilePic;
	}

	/**
	 * Constructor used when creating a User. Populates all fields, including id.
	 *
	 * @param firstName The User's first name.
	 * @param lastName  The User's last name.
	 * @param username  The User's login username.
	 * @param email     The email address used to contact this User.
	 * @param passwd    The User's password used to login.
	 * @param profilePic The bit array used to upload User's picture
	 */
	public User(int id, String firstName, String lastName, String username, String email,
	            String passwd, byte[] profilePic, int groupId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = passwd;
		this.profilePic = profilePic;
		this.groupId = groupId;
	}

	/**
	 * Constructor used when creating a User. Populates all fields, including id.
	 *
	 * @param id        The User's id
	 * @param firstName The User's first name.
	 * @param lastName  The User's last name.
	 * @param username  The User's login username.
	 * @param email     The email address used to contact this User.
	 * @param passwd    The User's password used to login.
	 */
	public User(int id, String firstName, String lastName, String username, String email,
	            String passwd){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = passwd;
	}

	//------- DATABASE METHODS -------//

	public static boolean connect() {
		return SQLLogin.connect();
	}

	/**
	 * Connects to the database, logging in with information provided at the login prompt.
	 *
	 * @return The newly logged-in user.
	 */
	public User login() {
		log.info(InfoStrings.LOGIN_MODEL);
		User loggedIn = SQLLogin.login(this);

		if (loggedIn != null) {
			activeUser = loggedIn;
		}

		password = "";
		return loggedIn;
	}



	public User updateProfile(String firstName, String lastName, String email,
	                             String groupDescription, byte[] profilePic) {

		return SQLModify.updateProfile(Group.getActiveGroup().getId(), User.getActiveUser().getId(),
				firstName, lastName, email, groupDescription, profilePic);
	}




	/**
	 * Logs off the user by removing the static activeUser field.
	 */
	public static void logoff() {
		log.info(InfoStrings.LOGOFF);
		activeUser = null;
	}

	/**
	 * Connects to the database, creating a new User with the information provided.
	 *
	 * @return The newly created User.
	 */
	public User createUser() {
		log.info(InfoStrings.CREATEUSER_MODEL);
		return SQLCreate.createUser(this);
	}

	/**
	 * Connects to the database, and attempts to find a user with one of the unique fields
	 * of this class.
	 * @return If found, the whole user will be returned. Otherwise, null is returned
	 */
	public User findUser() {
		log.info(InfoStrings.FIND_USER_MODEL);
		return SQLFind.findUser(this);
	}

	/**
	 * Connects to the database, and finds all of the Groups this User is in.
	 * @return Returns an array of Groups that this user is in.
	 */
	public Group[] getGroups() {
		return SQLGet.getGroups(this);
	}


	public Integer changePassword(String newPassword) {
		return SQLModify.changePassword(User.getActiveUser().getId(), newPassword);
	}


	/**
	 * Gets the duties that belong to a user in current group context.
	 * @param group The group object of the context
	 * @return The array of duties
	 */
	public Task[] getTasks(Group group) {
		log.info(InfoStrings.GET_USER_TASKS_MODEL);
		return SQLGet.getUserTasks(group, this);
	}

	/**
	 * Gets the goods that belong to a user in current group context.
	 * @param group The group object of the context
	 * @return The array of goods
	 */
	public Good[] getGoods(Group group) {
		log.info(InfoStrings.GET_USER_GOODS_MODEL);
		return null; //TODO remove this function call
	}

	/**
	 * Gets the bills that belong to this user.
	 * @return The array of bills
	 */
	public Bill[] getBills() {
		log.info(InfoStrings.GET_BILLS_MODEL);
		return SQLGet.getUserBills(this);
	}

	//------- OBJECT METHODS -------//

	public static User getActiveUser() {
		return activeUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public byte[] getProfilePic() {
		return profilePic;
	}

	public int getId() {
		return id;
	}

	public static void setActiveUser(User user) {
		activeUser = user;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getGroupId() {
		return groupId;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	//------- PARCELABLE METHODS -------//

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeString(username);
		dest.writeString(email);
		dest.writeString(password);

		dest.writeInt((profilePic != null) ? profilePic.length : 0 );
		dest.writeByteArray((profilePic!=null) ? profilePic : new byte[0]);
	}


	/**
	 * Creates a user object from a parcel.
	 * @param in The parcel to read
	 */
	public User(Parcel in) {
		id = in.readInt();
		firstName = in.readString();
		lastName = in.readString();
		username = in.readString();
		email = in.readString();
		password = in.readString();

		int length = in.readInt();
		byte[] temp = new byte[length];
		in.readByteArray(temp);
		profilePic = temp;
	}

	public String getName() {
		return firstName + " " + lastName;
	}
}
