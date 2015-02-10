/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/** Helper class for formatting strings */
public class BBBCalendarUtil {

	private static final String TAG = BBBCalendarUtil.class.getName();
	
	public static final long TIME_PERIOD_1_DAY = 1000 * 60 * 60 * 24;
	
	/** SimpleDateFormat object that will format/parse times of the form 'h:mm a' e.g '2:00 PM' */
	public static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("h:mm a", Locale.getDefault());

	/** SimpleDateFormat for formatting dates of the form yyyy */
	public static final SimpleDateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy", Locale.getDefault());

	/** SimpleDateFormat for formatting dates of the form yyyy-MM-dd */
	public static final SimpleDateFormat FORMAT_YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

	/** SimpleDateFormat for formatting dates of the form yyyy-MM-dd'T'HH:mm:ss*/
	public static final SimpleDateFormat FORMAT_TIME_STAMP = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
	
	/**
	 * Parses a string representing a date using the supplied date string. Useful for when you may not know the exact format of a date string and you want to attempt to use several possible formats.
	 */
	public static synchronized Calendar attemptParse(String date, SimpleDateFormat... formatters) {
		Date temp = null;
	
		for(int i=0; i<formatters.length; i++) {
		
			try {
				temp = formatters[i].parse(date);
			} catch (ParseException e) {
				continue;
			}
			
            long time = temp.getTime();
            time += TimeZone.getDefault().getDSTSavings();
			
			if(temp != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(time);
			
				return calendar;
			}	
		}
		
		return null;
	}

	/**
	 * Formats the date to the default locale
	 * 
	 * @param date
	 * @return the formatted date
	 */
	public static String formatDate(long date) {		
		DateFormat f = DateFormat.getDateInstance();
		Date d = new Date();
		d.setTime(date);
		
		return f.format(d);
	}
		
	/**
	 * Parses a string representing a date using the supplied date string and SimpleDateFormat object
	 * 
	 * @param date The string representing the date you want to parse
	 * @param formatter The SimpleDateFormat you want to use to parse the date
	 * @return The Calendar object which is the parsed date or null if the string was of the wrong format
	 */
	public static synchronized Calendar parseDate(String date, SimpleDateFormat formatter) {
		
		try {
			Date temp = formatter.parse(date);
			
			Calendar calendar = Calendar.getInstance();
			
            long time = temp.getTime();
            time += TimeZone.getDefault().getDSTSavings();
			
			calendar.setTimeInMillis(time);
			
			return calendar;
		} catch(ParseException e) {
			LogUtils.w(TAG, e.getMessage(), e);
		}
	
		return null;
	}
	
	/**
	 * Converts a time into a formatted string
	 * 
	 * @param time the time you want to convert
	 * @param formatter the SimpleDateFormat you want to use for the conversion
	 * @return the formatted date
	 */
	public static synchronized String formatTime(long time, SimpleDateFormat formatter) {
		Date date = new Date();
		date.setTime(time);
		return formatter.format(date);
	}
	
	/**
	 * Checks if a date falls within a certain given time period from now 
	 * 
	 * @param date the date you want to check
	 * @param timePeriod a length of time
	 * @return true if date falls within time period else false
	 */
	public static boolean isTimeWithinTimePeriodFromNow(long date, long timePeriod) {
		long currentTime = System.currentTimeMillis();
		
		if(Math.abs(currentTime - date) < timePeriod) {
			return true;
		}
		
		return false;
	}
}