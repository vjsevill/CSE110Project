package com.rip.roomies.sql;

import com.rip.roomies.util.InfoStrings;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * This is a SQLQuery helper class which will handle connecting to and executing
 * statements from the database.
 */
public class SQLQuery {
	private static final Logger log = Logger.getLogger(SQLQuery.class.getName());

	// Connection to the database
	private static Connection conn = null;

	//roomies_app.....password=#room1es4lyfe
	// The connection string to connect to database
	private static final String CONN_STRING = "jdbc:jtds:sqlserver://rationallyimpairedprogrammers.database.windows.net:1433/cse110_dev;user=roomies_app;password=#room1es4lyfe;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;instance=SQLEXPRESS;socketKeepAlive=true;";

	/**
	 * Helper class that initiates the connection to the database, setting the
	 * conn object to a non-null value if successful.
	 *
	 * @throws Exception if the database cannot be connected to
	 */
	private synchronized static void connect() throws Exception {
		if (conn == null || conn.isClosed()) {
			log.info(InfoStrings.DATABASE_CONNECT);

			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			conn = DriverManager.getConnection(CONN_STRING);

			log.info(InfoStrings.DATABASE_CONNECTED);
		}
	}

	/**
	 * Queries the server by attempting to execute the string passed into the method.
	 * If there is any error in either connecting or executing the query, the exception
	 * will be thrown to the caller.
	 *
	 * @param query The sql string to attempt to execute
	 * @return The result set of the query
	 * @throws Exception if the database cannot be connected to or statement fails
	 */
	protected static ResultSet execute(String query) throws Exception {

		if (conn == null || conn.isClosed()) {
			connect();
		}

		if (query == null) {
			return null;
		}

		log.info(String.format(InfoStrings.DATABASE_QUERY, query));
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		return stmt.executeQuery(query);
	}

	/**
	 * Prepares a SQL statement.
	 * @param query The query to prepare
	 * @return The prepared statement
	 * @throws Exception If the sql connection fails
	 */
	protected static PreparedStatement getPreparedStatement(String query) throws Exception {
		if (conn == null || conn.isClosed()) {
			connect();
		}

		if (query == null) {
			return null;
		}

		return conn.prepareStatement(query);
	}

	/**
	 * Sanitizes the input from the query by replacing all single quotes with two of them.
	 * @param param The parameter to sanitize
	 * @return The sanitized parameter
	 */
	public static String sanitize(String param) {
		return param.replaceAll("'", "''");
	}
}