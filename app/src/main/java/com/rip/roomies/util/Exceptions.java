package com.rip.roomies.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A utility class for any exception related functionality.
 */
public class Exceptions {
	/**
	 * Turns a stack trace into a string which can be logged.
	 * @param e The exception whose stack trace to print
	 * @return A string of the stack trace
	 */
	public static String stacktraceToString(Exception e)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		e.printStackTrace(ps);
		ps.close();
		return baos.toString();
	}
}
