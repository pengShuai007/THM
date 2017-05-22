package com.util.callEtlTools;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.Util.HLogger;


public class KtrParams 
{
	//调用KTR方式
	public static final String TIMER_CALL_KTR_TYPE = "0"; //传统定时调用ETL方式
	public static final String JAVA_CALL_KTR_TYPE = "2";//Java程序调用ETL方式
	private static String rootPath;

	
	/**
	 * <pre>
	 * 创建人：王攀科
	 * 日期：2014年12月4日 下午9:29:27
	 * 描述：根据配置文件加载KTR路径
	 * <pre>
	 */
	public static void loadKtrPath(String path)
	{
		rootPath = path.replace("\\", "/") + "Resources/APPETL/";
		HLogger.info(" \n\n\nrootPath = " +rootPath + "\n\n\n\n");
//		OP_APPOINT_KTR_PATH = rootPath + ETL_PATH + "appoint/opAppiont.ktr";
//		CHECK_REGISTER_KTR_PATH = rootPath + ETL_PATH;
//		REGIST_USER_KTR_PATH = rootPath + ETL_PATH;
//		QUERY_DOCTOR_SCHEDULE_INFO_KTR_PATH = rootPath + ETL_PATH + "schedule/ScheduleInfo.ktr";
//		QUERY_REGISTER_SCHEDULE_INFO_KTR_PATH = rootPath + ETL_PATH + "schedule/ScheduleInfo.ktr";
//		QUERY_APPOINT_SCHEDULE_INFO_KTR_PATH = rootPath + ETL_PATH + "schedule/ScheduleInfo.ktr";
//		System.out.println("*\n*、n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\npath = " + rootPath + "\n*\n*\n*\n*\n*\n*\n*\n*\n*\n*\n");
//		System.out.println(" OP_APPOINT_KTR_PATH : " + OP_APPOINT_KTR_PATH);
	}

	public static String getRootPath() {
		return rootPath;
	}

	/**
     * <pre>
     * 创建人：王攀科
     * 日期：2014年12月8日 上午11:59:25
     * 任务号：
     * 描述：查询KTR的链接方式 :	0 定时器触发ETL,ETL调用贺和程序无关；
     * 						1   程序直接调用ETL
     * 						2   程序采用南京模式调用ETL
     * <pre>
     */
    public static String queryConnKtrType(IDataBase appDb, String interfaceName) throws BaseBllException
    {
    	String result = "0";
    	StringBuffer sqlStr = new StringBuffer(" SELECT INTERFACE_TYPE_CODE FROM T_FRONT_INTERFACES_INFO WHERE INTERFACE_METHOD = '");
    	sqlStr.append(interfaceName).append("' ");
    	
    	if(appDb.Query(sqlStr.toString(), null).getDataTable().getRows().size() > 0)
    	{
    		result = appDb.Query(sqlStr.toString(), null).getDataTable().getRows().get(0).getStringColumn("INTERFACE_TYPE_CODE");
    	}
    	
    	return result;
    }
	
}
