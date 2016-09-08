package com.rip.roomies.util;

/**
 * Created by haotuusa on 5/27/16.
 */
public class SocketStrings {

	//connection url
//	public static final String SERVER_URL = "https://cse110-fcmtest.herokuapp.com";
	public static final String SERVER_URL = "https://cse110-server.herokuapp.com";
//	public static final String SERVER_URL = "http://192.168.0.21:3000";
//	public static final String SERVER_URL = "http://128.54.213.209:3000";
	//Strings for all the event android side listen from and emit to server, name has to be exact
	public static final String COMPLETE_DUTY = "complete duty";
	public static final String NOTIFICATION_DUTY = "notification duty";
	public static final String COMPLETE_CSG = "complete common good";
	public static final String NOTIFICATION_CSG= "notification common good";
	public static final String PASSWORD_RETRIEVE = "password retrieve";
	public static final String NOTIFICATION_BILL= "notification bill";

	//Strings for all the socket subscribtion android send to sever to join room
	public static final String NOTIFICATION_LISTEN =  "listen to notification";
	public static final String COMPLETION_LISTEN = "listen to completion";

	public static final String REFRESH_TOKEN = "refresh token";
}
