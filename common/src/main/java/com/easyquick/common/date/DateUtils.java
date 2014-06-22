/**
 * 与日期时间相关类的包
 */
package com.easyquick.common.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

/**
 * @author 王辉
 * 日期工具类
 */
public class DateUtils {
	
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYYMMDDHHmmss = "yyyyMMddHHmmss";
	
	/**
	 * 获取日期中的年份
	 * @param date 日期
	 * @return Integer 日期中的年份
	 * @throws Exception
	 */
	public static Integer getYear(Date date) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	    return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取日期中的月份
	 * @param date 日期
	 * @return Integer 日期中的月份
	 * @throws Exception
	 */
	public static Integer getMonth(Date date) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	    return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取日期中的日
	 * @param Date 日期
	 * @return Integer 日期中的日
	 * @throws Exception
	 */
	public static Integer getDay(Date date) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	    return cal.get(Calendar.DATE);
	}
	
	/**
	 * 获取日期整数(到日的)
	 * @param date 日期
	 * @return Integer 到日的日期整数(没有时间 列如20080412)
	 * @throws Exception
	 */
	public static Integer getDateWithDay(Date date) throws Exception{
		return Integer.valueOf(getFormatDate(date, "yyyyMMdd"));
	}
	
	/**
	 * 获取时间整数(到秒)
	 * @param date 日期
	 * @return Integer 到秒的时间整数(列如120101)
	 * @throws Exception
	 */
	public static Integer getTimeWithSecond(Date date) throws Exception{
		return Integer.valueOf(getFormatDate(date, "HHmmss"));
	}
	
	/**
	 * 获取时间整数(到毫秒)
	 * @param date 日期
	 * @return Long 到毫秒的时间整数(列如120101001)
	 * @throws Exception
	 */
	public static Long getTimeWithMillisecond(Date date) throws Exception{
		return Long.valueOf(getFormatDate(date, "HHmmssSSS"));
	}
	
	/**
	 * 获取日期时间整数(到秒)
	 * @param date 日期
	 * @return Long 到秒的日期时间整数(列如20080412090101)
	 * @throws Exception
	 */
	public static Long getDateWithSecond(Date date) throws Exception{
		return Long.valueOf(getFormatDate(date, "yyyyMMddHHmmss"));
	}
	
	/**
	 * 获取日期时间整数(到毫秒)
	 * @param date 日期
	 * @return Long 到毫秒的日期时间整数(列如20080412090101001)
	 * @throws Exception
	 */
	public static Long getDateWithMillisecond(Date date) throws Exception{
		return Long.valueOf(getFormatDate(date, "yyyyMMddHHmmssSSS"));
	}
	
	/**
	 * 获取格式化后的日期字符串
	 * @param date 日期
	 * @param format 格式化字符串(类如:"yyyy-MM-dd hh:mm:ss")
	 * @return String 格式化后的日期字符串
	 * @throws Exception
	 */
	public static String getFormatDate(Date date, String format) throws Exception{
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 获取当前日期
	 * @return Date 日期
	 * @throws Exception
	 */
	public static Date getDate()throws Exception{
		return new Date();
	}
	
	/**
	 * 转换字符串为日期类型
	 * @param dateString 日期字符串(必须是:"yyyyMMdd"格式)
	 * @return Date 日期
	 * @throws Exception
	 */
	public static Date convertYYYYMMDDStringToDate(String dateString)throws Exception{
		return convertStringToDate(dateString, YYYYMMDD);
	}
	
	/**
	 * 转换字符串为日期类型
	 * @param dateString 日期字符串(必须是:"yyyyMMddHHmmss"格式)
	 * @return Date 日期
	 * @throws Exception
	 */
	public static Date convertYYYYMMDDHHmmssStringToDate(String dateString)throws Exception{
		return convertStringToDate(dateString, YYYYMMDDHHmmss);
	}
	
	/**
	 * 转换字符串为日期类型
	 * @param dateString 日期字符串(类如:"20080412")
	 * @param format 格式化字符串(类如:"yyyy-MM-dd hh:mm:ss")
	 * @return Date 日期
	 * @throws Exception
	 */
	public static Date convertStringToDate(String dateString, String format)throws Exception{
		return new SimpleDateFormat(format).parse(dateString);
	}
	

	/**
	 * 获取两个日期间间隔日期
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return Vector<Date> 间隔的日期集合
	 * @throws Exception
	 */
	public static Collection<Date> getBetweenDate(
			Date startDate, 
			Date endDate) throws Exception{
		Calendar startCalendar = getDateCalendar(startDate);
		Calendar endCalendar = getDateCalendar(endDate);
		Collection<Date> daysVector = new Vector<>();
		
		startCalendar.add(Calendar.DAY_OF_MONTH, 1);
		while(!startCalendar.after(endCalendar)){
			daysVector.add(startCalendar.getTime());
			startCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return daysVector;
	}
	
	/**
	 * 获取两个日期间隔天数
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return Integer 间隔的天数
	 * @throws Exception
	 */
	public static Integer getBetweenDateDays(
			Date startDate, 
			Date endDate) throws Exception{
		Calendar startCalendar = getDateCalendar(startDate);
		Calendar endCalendar = getDateCalendar(endDate);
		int result = 0;
		
		startCalendar.add(Calendar.DAY_OF_MONTH, 1);
		while(!startCalendar.after(endCalendar)){
			++result;
			startCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return result;
	}
	
	/**
	 * 获取两个日期间隔月份
	 * @param startMonth 开始月份
	 * @param endMonth 结束月份
	 * @return Collection<Integer> 间隔的月份集合
	 * @throws Exception
	 */
	public static Collection<Integer> getBetweenMonth(
			Date startMonth, 
			Date endMonth) throws Exception{
		
		Calendar startCalendar = getDateCalendar(startMonth);
		Calendar endCalendar = getDateCalendar(endMonth);
		Collection<Integer> months = new Vector<>();
				
		startCalendar.add(Calendar.MONTH, 1);
		while ( !startCalendar.after(endCalendar)) {
			int year = startCalendar.get(Calendar.YEAR);
			int month = startCalendar.get(Calendar.MONTH) + 1;
			
			months.add(year*100 + month);
			startCalendar.add(Calendar.MONTH, 1);
		}
		
		return months;
	}
	
	/**
	 * 获取两个日期间隔月份
	 * @param startMonth 开始月份(例如：201401)
	 * @param endMonth 结束月份(例如：201402)
	 * @return Collection<Integer> 间隔的月份集合
	 * @throws Exception
	 */
	public static Collection<Integer> getBetweenMonth(
			Integer startMonth, 
			Integer endMonth) throws Exception{
		
		Calendar startCalendar = getMonthCalendar(startMonth);
		Calendar endCalendar = getMonthCalendar(endMonth);
		Collection<Integer> months = new Vector<>();
				
		startCalendar.add(Calendar.MONTH, 1);
		while ( !startCalendar.after(endCalendar)) {
			int year = startCalendar.get(Calendar.YEAR);
			int month = startCalendar.get(Calendar.MONTH) + 1;
			
			months.add(year*100 + month);
			startCalendar.add(Calendar.MONTH, 1);
		}
		
		return months;
	}
	
	/**
	 * 获取两个月份间隔月份数
	 * @param startMonth 开始月份
	 * @param endMonth 结束月份
	 * @return Integer 间隔的月份数
	 * @throws Exception
	 */
	public static Integer getBetweenMonths(
			Date startMonth, 
			Date endMonth) throws Exception{
		
		Calendar startCalendar = getDateCalendar(startMonth);
		Calendar endCalendar = getDateCalendar(endMonth);
		Integer result = 0;
				
		startCalendar.add(Calendar.MONTH, 1);
		while ( !startCalendar.after(endCalendar)) {
			++result;
			startCalendar.add(Calendar.MONTH, 1);
		}
		
		return result;
	}
	
	/**
	 * 获取两个月份间隔月份数
	 * @param startMonth 开始月份(例如：201401)
	 * @param endMonth 结束月份(例如：201402)
	 * @return Integer 间隔的月份数
	 * @throws Exception
	 */
	public static Integer getBetweenMonths(
			Integer startMonth, 
			Integer endMonth) throws Exception{
		
		Calendar startCalendar = getMonthCalendar(startMonth);
		Calendar endCalendar = getMonthCalendar(endMonth);
		Integer result = 0;
				
		startCalendar.add(Calendar.MONTH, 1);
		while ( !startCalendar.after(endCalendar)) {
			++result;
			startCalendar.add(Calendar.MONTH, 1);
		}
		
		return result;
	}
	
	/**
	 * 获取月末日期
	 * @param month 月份(例如：201401)
	 * @return Date 日期实例
	 * @throws Exception
	 */
	public static Date getMonthLastDay(Date month) throws Exception{
		
		Calendar calendar = getDateCalendar(month);
		
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return calendar.getTime();
	}
	
	
	/**
	 * 获取月末日期
	 * @param month 月份
	 * @return Date 日期实例
	 * @throws Exception
	 */
	public static Date getMonthLastDay(Integer month) throws Exception{
		
		Calendar calendar = getMonthCalendar(month);
		
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return calendar.getTime();
	}
	
	/**
	 * 获取月份的Calendar实例
	 * @param month 月份
	 * @return Calendar
	 * @throws Exception
	 */
	public static Calendar getMonthCalendar(Integer month) throws Exception{
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(month/100, month-((month/100)*100)-1, 1);
		
		return calendar;
	}
	
	/**
	 * 获取日期的Calendar实例
	 * @param date 日期
	 * @return Calendar
	 * @throws Exception
	 */
	public static Calendar getDateCalendar(Date date) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
				
		return calendar;
	}
}
