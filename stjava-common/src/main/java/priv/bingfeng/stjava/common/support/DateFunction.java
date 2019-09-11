package priv.bingfeng.stjava.common.support;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFunction {
	
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}
	
	/** date转string */
	public static String formatDateToString(Date date) {
		return formatDateToString(date, null);
	}
	public static String formatDateToString(Date date, String format) {
		if (!StringUtils.hasText(format)) format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		return sdf.format(date);
	}
	
	/** string转date */
	public static Date parseStringToDate(String time) {
		return parseStringToDate(time, null);
	}
	public static Date parseStringToDate(String time, String format) {
		if (!StringUtils.hasText(format)) {
			if (time.length() == "yyyy-MM-dd".length()) {
				format = "yyyy-MM-dd";
			} else if (time.length() == "yyyyMMdd".length()) {
				format = "yyyyMMdd";
			} else {
				format = "yyyy-MM-dd HH:mm:ss";
			}
		}
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/** 月移位 */
	public static Date moveMonth(Date date, int move) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, move);
		
		return c.getTime();
	}
	public static Date getDateBeforeMonth(Date date, int move) {
		return moveMonth(date, -move);
	}
	public static Date getDateAfterMonth(Date date, int move) {
		return moveMonth(date, move);
	}

	/** 天移位 */
	public static Date moveDay(Date date, int move) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, move);
		
		return c.getTime();
	}
	public static Date getDateBeforeDay(Date date, int move) {
		return moveDay(date, -move);
	}
	public static Date getDateAfterDay(Date date, int move) {
		return moveDay(date, move);
	}
	
	/** 小时移位 */
	public static Date moveHour(Date date, int move) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, move);
		
		return calendar.getTime();
	}
	public static Date getDateBeforeHour(Date date, int move) {
		return moveHour(date, -move);
	}
	public static Date getDateAfterHour(Date date, int move) {
		return moveHour(date, move);
	}
	
	/** 分钟移位 */
	public static Date moveMin(Date date, int move) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, move);
		
		return calendar.getTime();
	}
	public static Date getDateBeforeMin(Date date, int move) {
		return moveMin(date, -move);
	}
	public static Date getDateAfterMin(Date date, int move) {
		return moveMin(date, move);
	}
	
	/** 两date间隔天数 */
	public static int getBetweenDays(Date d1, Date d2) {
		return Math.abs((int) ((d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24)));
	}
	
	/** 两date间隔小时 */
	public static int getBetweenHours(Date d1, Date d2) {
		return Math.abs((int) ((d1.getTime() - d2.getTime()) / (1000 * 60 * 60)));
	}
	
	/** 两date间隔分钟 */
	public static int getBetweenMinutes(Date d1, Date d2) {
		return Math.abs((int) ((d1.getTime() - d2.getTime()) / (1000 * 60)));
	}

	/** 获取本周周一 */
	public static Date getWeekFirstDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		if (c.get(Calendar.DAY_OF_WEEK) == 1) c.add(Calendar.DAY_OF_MONTH, -1);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND,0);
		date = c.getTime();
		return date;
	}
	
	/** 获取date为星期几 */
	public static int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public static int getDateYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR); 
	}
	
	public static Date getYmd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/** 时间截取 */
	public static Date cutDate(Date date, String cutType) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		if (cutType.contains("s")) calendar.set(Calendar.SECOND, 0);
		if (cutType.contains("m")) calendar.set(Calendar.MINUTE, 0);
		if (cutType.contains("h")) calendar.set(Calendar.HOUR, 0);
		if (cutType.contains("H")) calendar.set(Calendar.HOUR_OF_DAY, 0);
		if (cutType.contains("d")) calendar.set(Calendar.DAY_OF_MONTH, 1);
		if (cutType.contains("M")) calendar.set(Calendar.MONTH, 0);
		
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
}
