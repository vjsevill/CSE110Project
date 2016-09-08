package com.rip.roomies.server;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.SocketStrings;

import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * Created by haotuusa on 5/30/16.
 */
public class Server {

	private static final Logger log = Logger.getLogger(Server.class.getName());

	// Connection to the database
	private static Socket socket;

	/**
	 * Helper class that initiates the connection to the database, setting the
	 * conn object to a non-null value if successful.
	 *
	 * @throws Exception if the database cannot be connected to
	 */
	private synchronized static void connect() throws URISyntaxException {

		if (socket == null || !socket.connected()) {

			log.info(InfoStrings.SERVER_CONNECT);
			socket = IO.socket(SocketStrings.SERVER_URL);
			socket.connect();
		}
	}

	public static Socket getConnection() throws URISyntaxException {
		if (socket == null || !socket.connected()) {
			connect();
		}
		return socket;
	}

	protected static void activateNotification(
			String notificationName, Emitter.Listener listener) throws URISyntaxException {
		if (socket == null || !socket.connected()) {
			connect();
		}
		socket.on(notificationName, listener);
	}
}
