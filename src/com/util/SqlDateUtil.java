package com.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("deprecation")
public class SqlDateUtil {

	/**
	 * <pre>
	 * 作者:张勇
	 * 描述:获取本周周一的日期
	 * 日期:2014年4月4日17:57:40
	 * </pre>
	 */
	public static String getMondayDateOfThisWeek(){
		Date monday =  new Date();
		Date now = new Date();
		int day = now.getDay();
		day = day == 0? 7 : day;
		long timeStamp = now.getTime() - day * 24 * 3600000;
		monday.setTime(timeStamp + (1 * 24 * 3600000));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String mondayStr = sdf.format(monday);
		return mondayStr;
	}
	
	/**
	 * <pre>
	 * 作者:张勇
	 * 描述:获取本周周日的日期
	 * 日期:2014年4月4日17:57:40
	 * </pre>
	 */
	public static String getSundayDateOfThisWeek(){
		Date sunday = new Date();
		Date now = new Date();
		int day = now.getDay();
		day = day == 0? 7 : day;
		long timeStamp = now.getTime() - day * 24 * 3600000;
		sunday.setTime(timeStamp + (7 * 24 * 3600000));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String sundayStr = sdf.format(sunday);
		return sundayStr;
	}

	/**
	 * <pre>
	 * 作者:常延锋
	 * 描述:获取系统当前时间，返回Timestamp（yyyy-MM-dd HH:MM:SS）
	 * 日期:2014年5月29日9:57:40
	 * </pre>
	 */
	public static Timestamp getSystemTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return Timestamp.valueOf(sdf.format(System.currentTimeMillis()));
	}
	
    /**
     * 	
     * 描述: 获取格式化时间<br/>
     * 创建人: 黄能洪 <br/>
     *
     * @param time  时间（String）
     * @param format  格式(yyyy-MM-dd HH:mm:ss)
     * @return    String
     * @throws ParseException 
     * @since Ver 1.1
     */
	public static String DateFormat(String time,String format) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		Date date=sdf.parse(time);
		return sdf.format(date);
	}
}
