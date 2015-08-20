package com.ron.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author 
 *
 */
public final class DateUtil {
	
	private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static synchronized String dateToString(Date date) {
		return sdf.format(date);
	}

	/**
	 * 返回天key
	 * @return 
	 */
	public static Date getDayTime(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	} 
	
}


