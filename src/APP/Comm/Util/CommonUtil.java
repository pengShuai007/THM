package APP.Comm.Util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataRow;
import APP.Comm.DataBase.Helper.AppDataTable;
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public class CommonUtil {

	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	protected static SimpleDateFormat sdfLong = new SimpleDateFormat(
			"yyyy-MM-dd HHmmss");

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-19����9:12:46
	 */
	public static String getRequestMapParaValue(
			Map<String, String[]> parameter, String name) {
		String[] value = parameter.get(name);
		if (value == null) {
			return "";
		} else {
			if (value.length == 0) {
				return "";
			} else {
				return value[0];
			}
		}
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-19����9:48:39
	 */
	public static java.sql.Date parse2SqlDate(String data)
			throws BaseBllException {
		java.sql.Date date;
		try {
			date = new java.sql.Date(sdf.parse(data).getTime());
		} catch (ParseException e) {

			// e.printStackTrace();
			throw new BaseBllException(e);
		}
		return date;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-11-8下午5:02:36
	 */
	public static java.sql.Date parse2SqlDate(String data, String pattern)
			throws BaseBllException {
		java.sql.Date date;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			date = new java.sql.Date(sf.parse(data).getTime());
		} catch (ParseException e) {

			// e.printStackTrace();
			throw new BaseBllException(e);
		}
		return date;
	}

	/**
	 * 转换成长日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatLongDate(Date date) {
		if (date == null)
			return "";
		String data = sdfLong.format(date);
		return data;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-19����9:48:45
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return "";
		String data = sdf.format(date);
		return data;
	}

	public static String formatDate(Date date, String pattern) {
		if (date == null)
			return "";
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		String data = sf.format(date);
		return data;
	}

	/**
	 * comments: 传入yyyy-MM-dd格式的日期型String，返回依据pattern格式化的string sjl modify
	 * 2014年1月24日下午2:54:50
	 */
	public static String formatDate(String date, String pattern)
			throws BaseBllException {
		// SimpleDateFormat sf = new SimpleDateFormat(pattern);
		// String data = sf.format(date);
		return formatDate(parse2SqlDate(date), pattern);
	}

	/**
	 * 
	 * 20142014年1月14日 qxd
	 * 
	 * @param date
	 * @param interval
	 * @return
	 * @throws BaseBllException
	 */
	public static Date addMinute(Date date, int interval)
			throws BaseBllException {
		java.sql.Date dt = null;
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		try {
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, interval);
			dt = new java.sql.Date(calendar.getTime().getTime());
		} catch (Exception e) {
			throw new BaseBllException(e);
		}
		return dt;

	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-11-2下午7:37:18
	 */
	public static Date addMonth(Date date, int interval)
			throws BaseBllException {
		java.sql.Date dt = null;
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// for (String id : TimeZone.getAvailableIDs()) {
		//
		// System.out.println(id);
		// }
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// dt = sdf.parse(date);
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, interval);
			// dt = sdf.format(calendar.getTime());
			// dt=sdf.parse(sdf.format(calendar.getTime()));
			dt = new java.sql.Date(calendar.getTime().getTime());
		} catch (Exception e) {

			// e.printStackTrace();
			throw new BaseBllException(e);
		}
		return dt;
	}

	public static Date addDay(Date date, int interval) throws BaseBllException {
		java.sql.Date dt = null;
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// dt = sdf.parse(date);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, interval);
			// dt = sdf.format(calendar.getTime());
			// dt=calendar.getTime();
			dt = new java.sql.Date(calendar.getTime().getTime());
		} catch (Exception e) {

			// e.printStackTrace();
			throw new BaseBllException(e);
		}
		return dt;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2014年1月3日下午5:04:49
	 */

	public static String formatDateTime(Date date) {
		if (date == null)
			return "";
		return sdf.format(date);
	}

	public static String formatDate(Object date) {
		if (date == null)
			return "";
		return formatDate((Date) date);
	}

	public static String formatDateTime(Object date) {
		if (date == null)
			return "";
		return formatDateTime((Date) date);
	}

	public static AppDataTable mergeHrpDataTable(AppDataTable appDataTable)
			throws BaseBllException {
		List<DataRow> dataRows = appDataTable.DataTable.getRows();
		List<String> columnList = appDataTable.DataTable.Columns;
		for (int i = 1; i < columnList.size(); i++) {
			for (DataRow dataRow : dataRows) {
				Object object = dataRow.getColumn(columnList.get(i));
				if (object instanceof String) {
					String tmpString = (String) object;
					tmpString = tmpString.replace("\"", "\\\"");
					dataRow.setColumn(columnList.get(i), tmpString);
				}
			}
		}
		return appDataTable;
	}

	/**
	 * 
	 * 2014年1月14日 qxd
	 * 
	 * @param year
	 * @return
	 */
	public static boolean judge(int year) {
		boolean yearleap = (year % 400 == 0) || (year % 4 == 0)
				&& (year % 100 != 0);// 采用布尔数据计算判断是否能整除
		return yearleap;
	}

	/**
	 * 
	 * 2014年1月14日 qxd
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int calculate(int year, int month) {
		boolean yearleap = judge(year);
		int day;
		if (yearleap && month == 2) {
			day = 29;
		} else if (!yearleap && month == 2) {
			day = 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			day = 30;
		} else {
			day = 31;
		}
		return day;
	}

	/**
	 * 获取日期中的年
	 * 
	 * @author 秦晓东
	 * @param date
	 * @return
	 */
	public static int getYearOfDate(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取日期中的月
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonthOfDate(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取日期中月中的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfDateMonth(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取日期中小时数
	 * 
	 * @param date
	 * @return
	 */
	public static int getHourOfDateDay(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取分钟数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinuteOfDateDay(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 获取秒数
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecondsOfDateDay(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 解析字符串为时间戳
	 * 
	 * @param date
	 *            字符串类型："2014-1-21 19:32:20",常用在UPDATE_TIME、CREATE_TIME等
	 * @return
	 */
	public static Timestamp parse2Timestamp(String date) {
		return Timestamp.valueOf(date);
	}

	/**
	 * @param args
	 * @throws BaseBllException
	 */
	public static void main(String[] args) throws BaseBllException {
		Date date = new java.sql.Date(new Date().getTime());
		System.out.println(formatDate(date, "yyyy-MM-dd HH:mm:ss"));
		//
		// // date = addMonth(date, 1);
		// System.out.println("年：" + getYearOfDate(date));
		// System.out.println("月：" + getMonthOfDate(date));
		// System.out.println("日：" + getDayOfDateMonth(date));
		// System.out.println("时：" + getHourOfDateDay(date));
		// System.out.println("分：" + getMinuteOfDateDay(date));
		// System.out.println("秒：" + getSecondsOfDateDay(date));
	}
}
