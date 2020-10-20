/**
 * 
 */
package com.srm.coreframework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Common  Date util class
 * 
 *
 */
public class DateUtil {

	public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final String format_dd_MM_yyyy = "dd-MM-yyyy";
	private static final String format_dd_MMM_yy = "dd-MMM-yy";
	private static final DateTimeFormatter formatter_Session = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

	/* Academic coding start */
	private static final String DATE_FORMAT = "dd-MMM-yyyy";
	private static final String TIME_FORMAT = "HH:mm:ss";
	private static final String DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

	/**
	 * convert localDate object into date string
	 * 
	 * @param localDate - localDate object
	 * @return - converted date string
	 */
	public static String localDateToString(LocalDate localDate) {
		return localDate.format(DATE_FORMATTER);
	}

	/**
	 * convert date string into LocalDate object
	 * 
	 * @param inputDate - input date string
	 * @return - LocalDate object
	 */
	public static LocalDate stringToLocalDate(String inputDate) {
		return LocalDate.parse(inputDate, DATE_FORMATTER);
	}

	/**
	 * convert local date time object into date string
	 * 
	 * @param localDate - local date time object
	 * @return - converted date time string
	 */
	public static String localDateTimeToString(LocalDateTime localDateTime) {
		return localDateTime.format(DATE_TIME_FORMATTER);
	}

	/**
	 * convert date time string into LocalDateTime object
	 * 
	 * @param inputDate - input date time string
	 * @return - LocalDateTime object
	 */
	public static LocalDateTime stringToLocalDateTime(String inputDateTime) {
		return LocalDateTime.parse(inputDateTime, DATE_TIME_FORMATTER);
	}

	/**
	 * convert local time into local time string
	 * 
	 * @param localTime - local time object
	 * @return - local time string
	 */
	public static String localTimeToString(LocalTime localTime) {
		return localTime.format(TIME_FORMATTER);
	}

	/**
	 * convert local time string into local time object
	 * 
	 * @param localTime - local time string
	 * @return - local time object
	 */
	public static LocalTime stringToLocalTime(String localTime) {
		return LocalTime.parse(localTime, TIME_FORMATTER);
	}

	/* Academic coding end */

	/**
	 * 
	 * @return
	 */
	public static LocalDateTime getCurrentLocalTime() {
		// Get current date
		LocalDateTime now = LocalDateTime.now();
		String formatDateTime = now.format(formatter);
		LocalDateTime dateTime = LocalDateTime.parse(formatDateTime, formatter);
		return dateTime;
	}

	
	public static Date getCalenderDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	/**
	 * 
	 * @param localDateTime
	 * @return
	 */
	public static String convertLocalTimeToStr(LocalDateTime localDateTime) {
		String str = localDateTime.format(formatter);
		return str;
	}

	/**
	 * 
	 * @param inputDate
	 * @return
	 */
	public static LocalDateTime convertStringToLocalTime(String inputDate) {
		LocalDateTime dateTime = LocalDateTime.parse(inputDate, formatter);
		return dateTime;
	}

	/**
	 * Get current string date
	 * 
	 * @return
	 */
	public static String getDateToString(Date dateParam) {
		DateFormat dateFormat = new SimpleDateFormat(format_dd_MM_yyyy);
		String dateReturn = dateFormat.format(dateParam);
		return dateReturn;
	}

	/**
	 * Get current string date
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getStringToDate(String dateParam) throws ParseException {
		Date dateReturn = new SimpleDateFormat(format_dd_MM_yyyy).parse(dateParam);
		return dateReturn;
	}

	public static Date getDateMMMFormat(String dateParam) throws ParseException {
		Date dateReturn = new SimpleDateFormat(format_dd_MMM_yy).parse(dateParam);
		return dateReturn;
	}

	public static String getStringMMFormat(Date dateParam) {
		DateFormat dateFormat = new SimpleDateFormat(format_dd_MMM_yy);
		String dateReturn = dateFormat.format(dateParam);
		return dateReturn;
	}

	public static String convertLocalTimeToStrSession(LocalDateTime localDateTime) {
		String str = localDateTime.format(formatter_Session);
		return str;
	}

	public static String getCurrentDateTimeStr() {
		LocalDateTime dateTime1 = DateUtil.getCurrentLocalTime();
		return DateUtil.convertLocalTimeToStr(dateTime1);
	}

	public String getMonth(Long month) {
		Calendar cal = Calendar.getInstance();
		cal.set(2001, (month.intValue()) - 1, 1);
		return (new SimpleDateFormat("MMM").format(cal.getTime())).toUpperCase();
	}
	
	/**
	 * Method used to change the date to string format.
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String dateToString(Date date, String format) {
		if (date == null)
			return null;
		String strDate = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			strDate = dateFormat.format(date);
			return strDate;
		} catch (Exception e) {
			e.printStackTrace();
			return strDate;
		}
	}
	
	private static final int MILLISECOND = 1;

	private static final int SECOND = 1000 * MILLISECOND;
	
	private static final int MINUTE = 60 * SECOND;
	
	/**
	 * Method used to get Minutes difference between maxDate and minDate.
	 * 
	 * @param maxDate
	 *            Date
	 * @param minDate
	 *            Date
	 * @return int
	 */
	public static int getMinutesDifference(Date maxDate, Date minDate) {
		long milliSecond = maxDate.getTime() - minDate.getTime();
		int minute = (int) (milliSecond / MINUTE);
		if (milliSecond % MINUTE != 0) {
			minute = minute + 1;
		}
		return minute;
	}

}
