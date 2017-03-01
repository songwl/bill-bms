package com.yipeng.bill.bms.core.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by lancey on 15/1/14.
 */
public abstract class DateUtils {

	private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

	public static String formatDate(Date date, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}

	public static String formatDate(Date date) {
		return formatDate(date, SIMPLE_DATE_FORMAT);
	}

	public static Date parseDate(String strDate, String format) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			return sf.parse(strDate);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Date parseDate(String strDate) {
		return parseDate(strDate, SIMPLE_DATE_FORMAT);
	}

	public static class DayTime implements Serializable {
		private boolean symbol;//true:正数，false:负数
		private int day;
		private int hour;
		private int minute;

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getHour() {
			return hour;
		}

		public void setHour(int hour) {
			this.hour = hour;
		}

		public int getMinute() {
			return minute;
		}

		public void setMinute(int minute) {
			this.minute = minute;
		}

		public boolean isSymbol() {
			return symbol;
		}

		public void setSymbol(boolean symbol) {
			this.symbol = symbol;
		}

		@Override
		public String toString() {
			return "DayTime{" + "symbol=" + symbol + ", day=" + day + ", hour=" + hour + ",minute=" + minute + '}';
		}
	}

	public static Date getMonthFirstDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return getDayStart(c.getTime());
	}

	public static Date getDayStart(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getDayEnd(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 转换分钟到时间
	 * @param minute
	 * @return
	 */
	public static DayTime conver2Day(int minute) {
		DayTime dt = new DayTime();
		if (minute < 0) {
			dt.setDay(0);
			int t = Math.abs(minute);
			dt.setHour(t / 60);
			dt.setMinute(t % 60);
		} else {
			int day = minute / (24 * 60);
			dt.setDay(day);
			int tmp = minute % (24 * 60);
			int hour = tmp / 60;
			dt.setHour((24 - hour) % 24);
			if (dt.getHour() > 0) {
				dt.setDay(dt.getDay() + 1);
			}
			dt.setMinute((60 - tmp % 60) % 60);
			if (dt.getMinute() > 0) {
				dt.setHour(dt.getHour() - 1);
			}
		}
		return dt;

	}

	/**
	 * 转换成分
	 * @param time
	 * @return
	 */
	public static int conver2Minute(DayTime time) {
		int total = 0;
		total += time.getMinute();
		total += time.getHour() * 60;

		if (time.getDay() == 0) {
			total = 0 - total;
		} else {
			total = time.getDay() * 60 * 24 - total;
		}
		return total;
	}

	/**
	 * 
	 * @param startDate
	 * @param includeStart
	 * @param endDate
	 * @param includeEnd
	 * @return
	 */
	public static List<Date> getDatesExtension(Date startDate, boolean includeStart, Date endDate, boolean includeEnd) {
		List<Date> list = new ArrayList<Date>();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		// 如果开始日期小于结束日期
		if (startCalendar.after(endCalendar)) {
			return null;
		}

		if (startCalendar.before(endCalendar)) {
			// 是否包含开始日期
			if (includeStart) {
				list.add(startDate);
			}

			startCalendar.add(Calendar.DATE, 1);
			// 计算2个日期之间的日期
			while (startCalendar.before(endCalendar)) {
				list.add(startCalendar.getTime());
				startCalendar.add(Calendar.DATE, 1);
			}

			// 是否包含结束日期
			if (includeEnd) {
				list.add(endDate);
			}
		}

		return list;
	}

	public static void main(String[] args) {
		DayTime dt = conver2Day((590));
		System.out.println(dt);

		String str = ToStringBuilder.reflectionToString(dt);
		System.out.println(str);

		DayTime dt2 = new DayTime();
		dt2.setDay(1);
		dt2.setHour(14);
		dt2.setMinute(10);
		System.out.println(conver2Minute(dt2));
	}
}
