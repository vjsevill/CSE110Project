package com.rip.roomies.util;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class provides static methods for converting data types.
 */
public class Conversions {
	private static final Logger log = Logger.getLogger(Conversions.class.getName());
	private static final char[] hexChars = "0123456789ABCDEF".toCharArray();

	public static String byteArrayToHexString(byte[] data) {
		if (data == null) {
			return "NULL";
		}

		char[] hexString = new char[data.length * 2 + 2];
		hexString[0] = '0';
		hexString[1] = 'x';
		int j = 2;

		for (int i = 0; i < data.length; ++i) {
			hexString[j++] = hexChars[(data[i] & 0xF0) >> 4];
			hexString[j++] = hexChars[data[i] & 0x0F];
		}

		return new String(hexString);
	}
}
