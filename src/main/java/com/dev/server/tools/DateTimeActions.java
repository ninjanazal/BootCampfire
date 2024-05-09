package com.dev.server.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class provides utility methods for working with date and time objects.
 */
public class DateTimeActions {

	/**
	 * Constant representing the session expiration time in hours (24 hours by
	 * default).
	 * You can modify this value to adjust session expiration duration.
	 */
	private static final Integer SESSIONEXPIRATIONHOURS_INTEGER = 24;
	/**
	 * A thread-safe, immutable DateTimeFormatter object used for consistent date
	 * and time formatting.
	 * This pattern ("yyyy-MM-dd HH:mm") represents year-month-day followed by hour
	 * and minute (24-hour format).
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	/**
	 * This method formats a `LocalDateTime` object into a String representation
	 * according to the
	 * defined `DATE_TIME_FORMATTER`. It's likely used to convert date and time
	 * objects to a format
	 * suitable for storage or display.
	 *
	 * @param dateTime The `LocalDateTime` object to be formatted.
	 * @return A String representing the formatted date and time.
	 */
	public static String FormatDateToString(LocalDateTime dateTime) {
		return dateTime.format(DATE_TIME_FORMATTER);
	}

	/**
	 * This method parses a String representation of date and time into a
	 * `LocalDateTime` object.
	 * It uses the defined `DATE_TIME_FORMATTER` to ensure consistency in parsing
	 * the expected format.
	 * This is likely used to convert stored or received date and time strings into
	 * usable objects.
	 *
	 * @param dataString The String containing the date and time in the expected
	 *                   format ("yyyy-MM-dd HH:mm").
	 * @return A `LocalDateTime` object representing the parsed date and time.
	 */
	public static LocalDateTime ParseStringToDate(String dataString) {
		return LocalDateTime.parse(dataString, DATE_TIME_FORMATTER);
	}

	/**
	 * This method generates a `LocalDateTime` object representing the expiration
	 * date based on the current time
	 * and the defined `SESSIONEXPIRATIONHOURS_INTEGER`. It adds the specified
	 * number of hours to the current
	 * time to calculate the expiration date/time. This method is likely used for
	 * managing session lifespans
	 * or other time-based functionalities in your application.
	 *
	 * @return A `LocalDateTime` object representing the expiration date/time
	 *         calculated from the current time
	 *         and the session expiration duration in hours.
	 */
	public static LocalDateTime GenerateExpirationDateFromNow() {
		return LocalDateTime.now().plusHours(SESSIONEXPIRATIONHOURS_INTEGER);
	}

}
