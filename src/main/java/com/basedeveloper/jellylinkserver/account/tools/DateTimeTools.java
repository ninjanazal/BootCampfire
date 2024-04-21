package com.basedeveloper.jellylinkserver.account.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeTools {
	private static final Integer SESSIONEXPIRATIONHOURS_INTEGER = 24;
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static String FormatDateToString(LocalDateTime dateTime) {
		return dateTime.format(DATE_TIME_FORMATTER);
	}

	public static LocalDateTime ParseStringToDate(String dataString) {
		return LocalDateTime.parse(dataString, DATE_TIME_FORMATTER);
	}

	public static LocalDateTime GenerateExpirationDateFromNow() {
		return LocalDateTime.now().plusHours(SESSIONEXPIRATIONHOURS_INTEGER);
	}
}
