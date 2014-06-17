/**
 * 与日期时间有关的类的包
 */
package com.easyquick.common.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 王辉
 * 描述：日期工具类
 */
public class DateUtils {
	
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
		return convertStringToDate(dateString, "yyyyMMdd");
	}
	
	/**
	 * 转换字符串为日期类型
	 * @param dateString 日期字符串(必须是:"yyyyMMddHHmmss"格式)
	 * @return Date 日期
	 * @throws Exception
	 */
	public static Date convertYYYYMMDDHHmmssStringToDate(String dateString)throws Exception{
		return convertStringToDate(dateString, "yyyyMMddHHmmss");
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
	
}
