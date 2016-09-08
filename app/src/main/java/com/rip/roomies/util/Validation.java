package com.rip.roomies.util;

import android.widget.EditText;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This is a class that can validate any user input based on what the input is.
 */
public class Validation {
	private static final Logger log = Logger.getLogger(Validation.class.getName());

	public enum ParamType {Identifier, Password, Email, SmallImage, Other};

	private static final int MIN_IDENT_LENGTH = 4;
	private static final int MIN_PASS_LENGTH = 8;
	private static final int MAX_SMALL_IMAGE_SIZE = 8000;

	/**
	 * Validates a text field based on its type and whagt it contains.
	 * @param param The EditText field to validate
	 * @param type The type of the data held
	 * @param name The name of the field
	 * @return An error message if one occured, an empty string otherwise
	 */
	public static String validate(EditText param, ParamType type, String name) {
		String input = param.getText().toString();

		// If empty, then all types will fail
		if (input.isEmpty()) {
			return String.format(Locale.US, DisplayStrings.MISSING_FIELD, name);
		}

		// Choose between one of the cases and validate it independently
		switch (type) {
		case Identifier:
			return validateIdentifier(input, name);
		case Password:
			return validatePassword(input, name);
		case Email:
			return validateEmail(input, name);
		}

		// If we are here, there are no errors
		return "";
	}

	/**
	 * Validate just an identifier type
	 * @param param The EditText of the field
	 * @param name The name of the field
	 * @return An error if one occured, an empty string otherwise
	 */
	private static String validateIdentifier(String param, String name) {
		// The identifier should be at least minimum length
		if (param.length() < MIN_IDENT_LENGTH) {
			return String.format(Locale.US, DisplayStrings.TOO_SHORT, name, MIN_IDENT_LENGTH);
		}

		// The identifier should be alphanumeric (with spaces allowed)
		for (int i = 0; i < param.length(); ++i) {
			char c = param.charAt(i);
			if (!Character.isLetter(c) && !Character.isDigit(c) && c != '_') {
				return String.format(Locale.US, DisplayStrings.NOT_ALPHANUM, name);
			}
		}

		// The first character of the identifier must be a letter
		if (!Character.isLetter(param.charAt(0))) {
			return String.format(Locale.US, DisplayStrings.FIRST_NOT_ALPHA, name);
		}

		return "";
	}

	/**
	 * Validate just a password type
	 * @param param The EditText of the field
	 * @param name The name of the field
	 * @return An error if one occured, an empty string otherwise
	 */
	private static String validatePassword(String param, String name) {
		// The identifier should be at least minimum length
		if (param.length() < MIN_PASS_LENGTH) {
			return String.format(Locale.US, DisplayStrings.TOO_SHORT, name, MIN_PASS_LENGTH);
		}

		return "";
	}

	/**
	 * Validate just an email type
	 * @param param The EditText of the field
	 * @param name The name of the field
	 * @return An error if one occured, an empty string otherwise
	 */
	private static String validateEmail(String param, String name) {
		EmailValidator ev = EmailValidator.getInstance();

		// Check if tbe address is valid. If not, return error message
		if (!ev.isValid(param)) {
			return String.format(Locale.US, DisplayStrings.INVALID, name);
		}

		return "";
	}

	public static String validateImage(byte[] image, ParamType type, String name) {
		if (type == ParamType.SmallImage && image != null && image.length > MAX_SMALL_IMAGE_SIZE) {
			log.info("Size: " + image.length);
			return String.format(Locale.US, DisplayStrings.TOO_LARGE, name,
					MAX_SMALL_IMAGE_SIZE + " bytes");
		}

		return "";
	}
}
