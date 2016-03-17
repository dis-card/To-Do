/*
 *      Copyright (C) 2016  Vikash Kumar
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package darkstars.in.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * It is a utility class to handle date, time and their formats.
 * Created by Vikash Kumar on 3/12/2016.
 */
public class DateUtil {

    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm";
    private static final Calendar DUMMY_CAL = Calendar.getInstance();

    /**
     * Formats the input date.
     * @param date, input date.
     * @param format, format of the resultant date.
     * @return formatted date as string.
     */
    public static String formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * Formats the time associated with input date.
     * @param date, input date.
     * @param format, format of the resultant time.
     * @return formatted time as string.
     */
    public static String formatTime(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * Formats date using default date format.
     * @param date, input date.
     * @return formatted (default format) date as string.
     */
    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * Formats time using default time format.
     * @param date, input date.
     * @return formatted (default format) time as string.
     */
    public static String formatTime(Date date) {
        return formatDate(date, DEFAULT_TIME_FORMAT);
    }

    /**
     * @return, current date in system's default time zone.
     */
    public static Date getCurrentDate() {
        return getCurrentDate(TimeZone.getDefault());
    }

    /**
     * Retrieves current date in a given time zone.
     * @param timeZone, input time zone.
     * @return, current time from given time zone.
     */
    public static Date getCurrentDate(TimeZone timeZone) {
        Calendar cal = Calendar.getInstance(timeZone);
        return cal.getTime();
    }

    /**
     * @return, current date as a string using system's default time zone and default date format.
     */
    public static String getCurrentDateString() {
        return formatDate(getCurrentDate(), DEFAULT_DATE_FORMAT);
    }

    /**
     * @return, current time as a string using system's default time zone and default time format.
     */
    public static String getCurrentTimeString() {
        return formatDate(getCurrentDate(), DEFAULT_TIME_FORMAT);
    }

    /**
     * @param date, input date
     * @param month, input month
     * @param year, input year
     * @return string representation of the date specified in input.
     */
    public static String getDateString(int date, int month, int year) {
        DUMMY_CAL.set(year, month, date);
        return formatDate(DUMMY_CAL.getTime());
    }

    /**
     * @param hour, input hour
     * @param min, input min
     * @return default string representation of the time specified in input.
     */
    public static String getTimeString(int hour, int min) {
        DUMMY_CAL.set(Calendar.HOUR, hour);
        DUMMY_CAL.set(Calendar.MINUTE, min);
        DUMMY_CAL.set(Calendar.SECOND, 0);
        DUMMY_CAL.set(Calendar.MILLISECOND, 0);
        return formatTime(DUMMY_CAL.getTime());
    }

    /**
     * Retrieves current date in string format from the given time zone.
     * @param timeZone,
     * @return, string representation of current date in specified time zone.
     */
    public static String getCurrenDateString(TimeZone timeZone) {
        return formatDate(getCurrentDate(timeZone),DEFAULT_DATE_FORMAT);
    }

    /**
     * Retrieves current date in specified string format from the given time zone.
     * @param timeZone
     * @param format
     * @return string representation of current date in specified time zone adn format.
     */
    public static String getCurrentDateString(TimeZone timeZone, String format) {
        return formatDate(getCurrentDate(), format);
    }
}
