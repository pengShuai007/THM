package APP.Comm.Util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.base.ext.M_SYSTEM_EXCEPTION_RECORDS_EXT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AbstractDataBase;
import APP.Comm.DataBase.Helper.DBFactory;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.DotNet.HttpContext;
import APP.Model.Base.SYS_EXCEPTION_PARA;

public class HDBLoggerUtil {

	public enum LoggerLevel{
		DEBUG,INFO,WARN,ERROR,FATAL
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出info信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 上午11:59:22
	* @param o 需要输出信息
	* </pre>
	 */
	private static void info(Object o)
	{
		trace("-1","-1","-1","","","","","","","0","","","",o,LoggerLevel.INFO,false);
		//Info("-1","-1","-1","","","","","","",o);
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出info信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 上午11:16:12
	* @param _context HttpContext
	* @param o 需要输出的信息
	* </pre>
	 */
	private static void info(HttpContext _context, Object o) {
		//医院ID
		String hospitalId = getHospitalID(_context.getRequest());
		// 获取当前用户
		String currentUser = getUserId(_context);
		// 获取用户来源
		String userResource = getUserSource(_context);
		// 获取访问者的IP
		String ip = HandlerUtil.getIpAddr(_context.getRequest());

		String serverIp = HandlerUtil.getServerIp(_context.getRequest());// 获取当前服务器的IP
		String apkVersion = _context.getRequest().getParameter("opVersion");// 获取当前op版本,获取apk传过来的版本号
		// String
		// version=SystemParams.getParamaValue("WEB_BUILDER_VERSION_APP");//获取当前op版本
		String tVersion = _context.getRequest().getParameter("t_version");// 获取端版本号
		// op参数
		String op = _context.getRequest().getParameter("op");
		if (op == null
				&& _context.getRequest().getParameter("reqReserved") != null) {
			op = _context.getRequest().getParameter("reqReserved");
		}
		// URL
		String url = _context.getRequest().getRequestURL().toString();
		
		String requestParameter = getRequestMapString(_context);
		/*Info(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op, o);*/
		trace(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op, "0",requestParameter,"","",o, LoggerLevel.INFO,false);
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出info信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 上午11:12:51
	* @param currentUser 当前用户
	* @param userResource 用户来源
	* @param ip 访问者IP
	* @param serverIp 当前服务器IP
	* @param apkVersion apk版本
	* @param tVersion 端版本
	* @param url 访问url
	* @param op 访问op
	* @param o 需要输出信息
	* </pre>
	 */
	private static void info(String hospitalID,String currentUser, String userResource, String ip,
			String serverIp, String apkVersion, String tVersion, String url,
			String op, Object o) {
		trace(hospitalID,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op, "0","","","",o, LoggerLevel.INFO,false);
		/*Info(hospitalID,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op, o);*/
	}

	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出warn信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 下午1:28:17
	* @param o 需要输出信息
	* </pre>
	 */
	private static void warn(Object o) {
		//trace("-1","-1", "-1", "", "", "", "", "", "", o, LoggerLevel.WARN);
		//Warn("-1","-1","-1","","","","","","",o);
		outString("-1","-1","-1","","","","","","","0","","","","",o+"",false, 2);
	}

	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出warn信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 下午1:28:46
	* @param _context HttpContext
	* @param o 需要输出信息
	* </pre>
	 */
	private static void warn(HttpContext _context, Object o) {
		//医院ID
		String hospitalId = getHospitalID(_context.getRequest());
		// 获取当前用户
		String currentUser = getUserId(_context);
		// 获取用户来源
		String userResource = getUserSource(_context);
		// 获取访问者的IP
		String ip = HandlerUtil.getIpAddr(_context.getRequest());

		String serverIp = HandlerUtil.getServerIp(_context.getRequest());// 获取当前服务器的IP
		String apkVersion = _context.getRequest().getParameter("opVersion");// 获取当前op版本,获取apk传过来的版本号
		// String
		// version=SystemParams.getParamaValue("WEB_BUILDER_VERSION_APP");//获取当前op版本
		String tVersion = _context.getRequest().getParameter("t_version");// 获取端版本号
		// op参数
		String op = _context.getRequest().getParameter("op");
		if (op == null
				&& _context.getRequest().getParameter("reqReserved") != null) {
			op = _context.getRequest().getParameter("reqReserved");
		}
		// URL
		String url = _context.getRequest().getRequestURL().toString();
		
		String requestParameter = getRequestMapString(_context);
		/*trace(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op, o, LoggerLevel.WARN);*/
		/*Warn(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op, o);*/
		outString(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op,"0",requestParameter,"","","",o+"",false, 2);
	}

	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出warn信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 下午12:03:13
	* @param currentUser 当前用户
	* @param userResource 用户来源
	* @param ip 访问者IP
	* @param serverIp 当前服务器IP
	* @param apkVersion apk版本
	* @param tVersion 端版本
	* @param url 访问url
	* @param op 访问op
	* @param requestParameter 访问op
	* @param errorSql 访问op
	* @param isCloudorTerminal 
	* @param o 需要输出信息
	* </pre>
	 */
	private static void warn(String hospitalId,String currentUser, String userResource, String ip,
			String serverIp, String apkVersion, String tVersion, String url,
			String op,String requestParameter,String errorSql,String isCloudorTerminal, Object o) {
		/*trace(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op, o, LoggerLevel.WARN);*/
		/*Warn(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op, o);*/
		outString(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op,"0",requestParameter,errorSql,isCloudorTerminal,"",o+"",false, 2);
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出error信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 下午1:34:16
	* @param o 需要输出信息
	* </pre>
	 */
	public static void error(Object o)
	{
		//trace("-1","-1", "-1", "", "", "", "", "", "", o, LoggerLevel.ERROR);
		SYS_EXCEPTION_PARA entity = new SYS_EXCEPTION_PARA();
		entity.sethospitalId("-1");
		entity.setcurrentUser("-1");
		entity.setuserResource("-1");
		entity.seterrorSource("0");
		entity.setin(o);
		//Error("-1","-1", "-1", "", "", "", "", "", "","0","","","","", o);
		Error(entity);
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出error信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 下午1:34:51
	* @param _context HttpContext
	* @param o 需要输出信息
	* </pre>
	 */
	public static void error(HttpContext _context,Object o)
	{
		//医院ID
		String hospitalId = getHospitalID(_context.getRequest());
		// 获取当前用户
		String currentUser = getUserId(_context);
		// 获取用户来源
		String userResource = getUserSource(_context);
		// 获取访问者的IP
		String ip = HandlerUtil.getIpAddr(_context.getRequest());

		String serverIp = HandlerUtil.getServerIp(_context.getRequest());// 获取当前服务器的IP
		String apkVersion = _context.getRequest().getParameter("opVersion");// 获取当前op版本,获取apk传过来的版本号
		// String
		// version=SystemParams.getParamaValue("WEB_BUILDER_VERSION_APP");//获取当前op版本
		String tVersion = _context.getRequest().getParameter("t_version");// 获取端版本号
		// op参数
		String op = _context.getRequest().getParameter("op");
		if (op == null
				&& _context.getRequest().getParameter("reqReserved") != null) {
			op = _context.getRequest().getParameter("reqReserved");
		}
		// URL
		String url = _context.getRequest().getRequestURL().toString();
		
		String requsetParameter = getRequestMapString(_context);
		//trace(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
		//		url, op, o, LoggerLevel.ERROR);
		SYS_EXCEPTION_PARA entity = new SYS_EXCEPTION_PARA();
		/*String hospitalId,String currentUser, String userResource, String ip,
		String serverIp, String apkVersion, String tVersion, String url,
		String op, String errorSource,String requsetParameter,String errorSql,
		String errorMsgForMonitor,String iscloudorTerminal,Object in*/
		entity.sethospitalId(hospitalId);
		entity.setcurrentUser(currentUser);
		entity.setuserResource(userResource);
		entity.setip(ip);
		entity.setserverIp(serverIp);
		entity.setapkVersion(apkVersion);
		entity.settVersion(tVersion);
		entity.seturl(url);
		entity.setop(op);
		entity.setrequsetParameter(requsetParameter);
		entity.setin(o);
		entity.seterrorSource("0");
		/*Error(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
						url, op, "0",requsetParameter,"","","",o);*/
		Error(entity);
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2870
	* 描述:系统异常
	* 作者:冯泽
	* 日期:2015年8月3日 下午1:50:02
	* @param _context
	* @param o
	* </pre>
	 */
	public static void cloudSystemError(HttpContext _context,Object o)
	{
		error(_context,"1","","","0",o,false);
	}
	
	public static void terminalSystemError(HttpContext _context,Object o)
	{
		error(_context,"1","","","1",o,false);
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2870
	* 描述:业务异常
	* 作者:冯泽
	* 日期:2015年8月3日 下午1:50:18
	* @param _context
	* @param o
	* </pre>
	 */
	public static void cloudBusinessError(HttpContext _context,Object o)
	{
		error(_context,"2","","","0",o,false);
	}
	
	public static void terminalBusinessError(HttpContext _context,Object o)
	{
		error(_context,"2","","","1",o,false);
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2870
	* 描述:系统异常
	* 作者:冯泽
	* 日期:2015年8月3日 下午1:39:52 
	* @param _context HttpContext
	* @param errorSql 异常SQL
	* @param o 需要输出的信息
	* </pre>
	 */
	public static void cloudSystemError(HttpContext _context,String errorSql,String errorMsgForMonitor,Object o,boolean isNetException)
	{
		error(_context,"1",errorSql,errorMsgForMonitor,"0",o,isNetException);
	}
	
	public static void terminalSystemError(HttpContext _context,String errorSql,String errorMsgForMonitor,Object o,boolean isNetException)
	{
		error(_context,"1",errorSql,errorMsgForMonitor,"1",o,isNetException);
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2870
	* 描述:业务异常
	* 作者:冯泽
	* 日期:2015年8月3日 下午1:41:04
	* @param _context HttpContext
	* @param errorSql 异常SQL
	* @param o 需要输出的信息
	* </pre>
	 */
	public static void cloudBusinessError(HttpContext _context,String errorSql,String errorMsgForMonitor,Object o,boolean isNetException)
	{
		error(_context,"2",errorSql,errorMsgForMonitor,"0",o,isNetException);
	}
	
	public static void terminalBusinessError(HttpContext _context,String errorSql,String errorMsgForMonitor,Object o,boolean isNetException)
	{
		error(_context,"2",errorSql,errorMsgForMonitor,"1",o,isNetException);
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2870
	* 描述:
	* 作者:冯泽
	* 日期:2015年8月3日 下午3:15:34
	* @param _context HttpContext
	* @param errorSource 异常来源 1：系统异常，2：非系统异常
	* @param errorSql 错误SQL
	* @param cloud 云或者端异常，0：云异常，1：端异常
	* @param o
	* </pre>
	 */
	private static void error(HttpContext _context, String errorSource,
			String errorSql, String errorMsgForMonitor,
			String iscloudorTerminal, Object o,boolean isNetException) {
		//医院ID
		String hospitalId = getHospitalID(_context.getRequest());
		// 获取当前用户
		String currentUser = getUserId(_context);
		// 获取用户来源
		String userResource = getUserSource(_context);
		// 获取访问者的IP
		String ip = HandlerUtil.getIpAddr(_context.getRequest());

		String serverIp = HandlerUtil.getServerIp(_context.getRequest());// 获取当前服务器的IP
		String apkVersion = _context.getRequest().getParameter("opVersion");// 获取当前op版本,获取apk传过来的版本号
		// String
		// version=SystemParams.getParamaValue("WEB_BUILDER_VERSION_APP");//获取当前op版本
		String tVersion = _context.getRequest().getParameter("t_version");// 获取端版本号
		// op参数
		String op = _context.getRequest().getParameter("op");
		if (op == null
				&& _context.getRequest().getParameter("reqReserved") != null) {
			op = _context.getRequest().getParameter("reqReserved");
		}
		// URL
		String url = _context.getRequest().getRequestURL().toString();
		
		String requsetParameter = getRequestMapString(_context);
		//trace(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
		//		url, op, o, LoggerLevel.ERROR);
		SYS_EXCEPTION_PARA entity = new SYS_EXCEPTION_PARA();
		/*String hospitalId,String currentUser, String userResource, String ip,
		String serverIp, String apkVersion, String tVersion, String url,
		String op, String errorSource,String requsetParameter,String errorSql,
		String errorMsgForMonitor,String iscloudorTerminal,Object in*/
		entity.sethospitalId(hospitalId);
		entity.setcurrentUser(currentUser);
		entity.setuserResource(userResource);
		entity.setip(ip);
		entity.setserverIp(serverIp);
		entity.setapkVersion(apkVersion);
		entity.settVersion(tVersion);
		entity.seturl(url);
		entity.setop(op);
		entity.setrequsetParameter(requsetParameter);
		entity.setin(o);
		entity.seterrorSource(errorSource);
		entity.seterrorSql(errorSql);
		entity.seterrorMsgForMonitor(errorMsgForMonitor);
		entity.setiscloudorTerminal(iscloudorTerminal);
		entity.setIsNetException(isNetException);
		/*Error(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
						url, op, errorSource,requsetParameter,errorSql,errorMsgForMonitor,iscloudorTerminal,o);*/
		Error(entity);
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出error信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 下午1:36:07
	* @param currentUser 当前用户
	* @param userResource 用户来源
	* @param ip 访问者IP
	* @param serverIp 当前服务器IP
	* @param apkVersion apk版本
	* @param tVersion 端版本
	* @param url 访问url
	* @param op 访问op
	* @param requsetParameter 请求参数
	* @param errorSql 错误SQL
	* @param iscloudorTerminal 0:云，1：端
	* @param o 需要输出信息
	* </pre>
	 */
	public static void error(SYS_EXCEPTION_PARA entity){
	/*public static void error(String hospitalId,String currentUser, String userResource,
				String ip,String serverIp, String apkVersion, String tVersion, String url,
				String op,String requsetParameter,String errorSql,String errorMsgForMonitor,
				String iscloudorTerminal,Object o){*/
	
	
		
		
		/*Error(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op, "0",requsetParameter,errorSql,errorMsgForMonitor,iscloudorTerminal,o);*/
		Error(entity);
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出debug信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 下午1:37:14
	* @param o 需要输出信息
	* </pre>
	 */
	private static void debug(Object o)
	{
		//trace("-1","-1", "-1", "", "", "", "", "", "", o, LoggerLevel.DEBUG);
		SYS_EXCEPTION_PARA entity = new SYS_EXCEPTION_PARA ();
		entity.sethospitalId("-1");
		entity.setcurrentUser("-1");
		entity.setuserResource("-1");
		entity.setin(o);
		//Debug("-1","-1", "-1", "", "", "", "", "", "", "", "","","",o);
		Debug(entity);
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出debug信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 下午1:37:34
	* @param _context HttpContext
	* @param o 需要输出信息
	* </pre>
	 */
	private static void debug(HttpContext _context,Object o)
	{
		//医院ID
		String hospitalId = getHospitalID(_context.getRequest());
		// 获取当前用户
		String currentUser = getUserId(_context);
		// 获取用户来源
		String userResource = getUserSource(_context);
		// 获取访问者的IP
		String ip = HandlerUtil.getIpAddr(_context.getRequest());

		String serverIp = HandlerUtil.getServerIp(_context.getRequest());// 获取当前服务器的IP
		String apkVersion = _context.getRequest().getParameter("opVersion");// 获取当前op版本,获取apk传过来的版本号
		// String
		// version=SystemParams.getParamaValue("WEB_BUILDER_VERSION_APP");//获取当前op版本
		String tVersion = _context.getRequest().getParameter("t_version");// 获取端版本号
		// op参数
		String op = _context.getRequest().getParameter("op");
		if (op == null
				&& _context.getRequest().getParameter("reqReserved") != null) {
			op = _context.getRequest().getParameter("reqReserved");
		}
		// URL
		String url = _context.getRequest().getRequestURL().toString();
		
		String requestParameter = getRequestMapString(_context);
		/*trace(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op, o, LoggerLevel.DEBUG);*/
		SYS_EXCEPTION_PARA entity = new SYS_EXCEPTION_PARA();
		entity.sethospitalId(hospitalId);
		entity.setcurrentUser(currentUser);
		entity.setuserResource(userResource);
		entity.setip(ip);
		entity.setserverIp(serverIp);
		entity.setapkVersion(apkVersion);
		entity.settVersion(tVersion);
		entity.seturl(url);
		entity.setop(op);
		entity.setrequsetParameter(requestParameter);
		entity.setin(o);
		/*Debug(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
				url, op,requestParameter,"","","", o);*/
		Debug(entity);
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2674
	* 描述:输出debug信息到数据库
	* 作者:冯泽
	* 日期:2015年7月14日 下午1:39:12
	* @param currentUser 当前用户
	* @param userResource 用户来源
	* @param ip 访问者IP
	* @param serverIp 当前服务器IP
	* @param apkVersion apk版本
	* @param tVersion 端版本
	* @param url 访问url
	* @param op 访问op
	* @param o 需要输出信息
	* </pre>
	 */
	/*private static void debug(String hospitalId,String currentUser, String userResource,
			String ip,String serverIp, String apkVersion, String tVersion, String url,
			String op,String requestParameter,String errorSql,String errorMsgForMonitor,
			String iscloudorTerminal, Object o){*/
	private static void debug(SYS_EXCEPTION_PARA entity){
		Debug(entity);
	}
	
	
	private static void trace(String hospitalId,String currentUser,String userResource,
			String ip, String serverIp,String apkVersion,String tVersion,String url,
			String op,String errorSource,String requestParameter,String errorSql,
			String iscloudorTerminal,Object o,LoggerLevel level,boolean isNetException) {
		Throwable ex = new Throwable();
		if (level == LoggerLevel.DEBUG) {
			debugStackTrace(hospitalId,currentUser,userResource,ip,serverIp,
					apkVersion,tVersion,url,op,o,ex);
		}else if (level == LoggerLevel.INFO) {
			infoStackTrace(hospitalId,currentUser,userResource,ip,serverIp,
					apkVersion,tVersion,url,op,o,ex);
		}else if (level == LoggerLevel.WARN) {
			warnStackTrace(hospitalId,currentUser,userResource,ip,serverIp,
					apkVersion,tVersion,url,op,o,ex);
		}else if (level == LoggerLevel.ERROR) {
			errorStackTrace(hospitalId,currentUser,userResource,ip,serverIp,
					apkVersion,tVersion,url,op,errorSource,requestParameter,
					errorSql,iscloudorTerminal,o,ex,isNetException);
		}
	}
	
	/*private static void Error(String hospitalId,String currentUser, String userResource, String ip,
			String serverIp, String apkVersion, String tVersion, String url,
			String op, String errorSource,String requsetParameter,String errorSql,
			String errorMsgForMonitor,String iscloudorTerminal,Object in)*/
	private static void Error(SYS_EXCEPTION_PARA entity)
		{
		Object in = entity.getin();
		String errorSql = entity.geterrorSql();
		String errorMsgForMonitor = entity.geterrorMsgForMonitor();
		String errorSource = entity.geterrorSource();
		String tVersion = entity.gettVersion();
		String iscloudorTerminal = entity.getiscloudorTerminal();
		String requsetParameter = entity.getrequsetParameter();
		String str = "";
		String t_ip = "";
		String hospitalId = entity.gethospitalId();
		String currentUser = entity.getcurrentUser();
		String userResource = entity.getuserResource();
		String ip = entity.getip();
		String serverIp = entity.getserverIp();
		String apkVersion = entity.getapkVersion();
		String url = entity.geturl();
		String op = entity.getop();
		boolean isNetException = entity.getIsNetException();
		// InvocationTargetException
		if (in instanceof Throwable) {
//			errorStackTrace(in);
			if (in instanceof InvocationTargetException) {
				entity.setin(((InvocationTargetException) in).getTargetException());
				/*error(hospitalId,currentUser,userResource,ip,
						serverIp,apkVersion,tVersion,url,
						op,requsetParameter,errorSql,errorMsgForMonitor,iscloudorTerminal,((InvocationTargetException) in).getTargetException());*/
				error(entity);
			} else {
				if(DotNetToJavaStringHelper.isNullOrEmpty(errorMsgForMonitor))
				{
					//Edit start fengze 替换html标签 KYEEAPPTEST-2873 云后台异常管理维护里面抛出异常信息 2015-8-11 19:50:28
					str = replaceHtmllable(in + ":") + "<br>";
					//Edit end fengze 替换html标签 KYEEAPPTEST-2873 云后台异常管理维护里面抛出异常信息 2015-8-11 19:50:28
					if("errorSql".equals(errorSql))
					{
						errorSql = "";
					}
				}else{
					Map<String, Object> errorMsgMap = null;
                    try {
                        errorMsgMap = JsonUtil.jsonToMap(errorMsgForMonitor);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // edit start KYEEAPPC-4391 修改系统组代码中fidbugs找到的错误 wangzhang 2015-12-08
                    if(errorMsgMap != null){
						errorSql = errorMsgMap.get("errorSql")+"";
						errorSql = revertString(errorSql);
						if("errorSql".equals(errorSql))
						{
							errorSql = "";
						}
						errorSource = errorMsgMap.get("errorLevel")+"";
						if("system".equals(errorSource))
						{
							errorSource = "1";//系统异常
						}else if("business".equals(errorSource))
						{
							errorSource = "2";//非系统异常
						}else{
							errorSource = "0";//未设置
						}
						tVersion = errorMsgMap.get("serverVersion")+"";
						iscloudorTerminal = "1";
						if("serverVersion".equals(tVersion))
						{
							tVersion = "";
						}
						String requestParameters = errorMsgMap.get("requestParameters")+"";
						requestParameters = revertString(requestParameters);
						if(!"requestParameters".equals(requestParameters))
						{
							requsetParameter = "云参数：" +requsetParameter + "端参数： " +requestParameters;
						}
						t_ip = errorMsgMap.get("serverIP")+"";
						if("serverIP".equals(t_ip))
						{
							t_ip = "";
						}
						String errorStackTrace = errorMsgMap.get("errorStackTrace")+"";
						str ="端堆栈信息：<br>"+ revertString(errorStackTrace) + "<br>";
						//Edit start fengze 替换html标签 KYEEAPPTEST-2873 云后台异常管理维护里面抛出异常信息 2015-8-11 19:50:28
						str =str+"云堆栈信息：<br>"+replaceHtmllable(in + ":") + "<br>";
						//Edit end fengze 替换html标签 KYEEAPPTEST-2873 云后台异常管理维护里面抛出异常信息 2015-8-11 19:50:28
                    }
                    // edit end KYEEAPPC-4391 修改系统组代码中fidbugs找到的错误 wangzhang 2015-12-08
                }
				if (in instanceof BaseBllException) {					
					str = str + errorStackTrace(hospitalId,currentUser,userResource,ip,
							serverIp,apkVersion,tVersion,url,
							op,BaseBllException.getFinalException((BaseBllException)in));
				}else{
					str = str + errorStackTrace(hospitalId,currentUser,userResource,ip,
							serverIp,apkVersion,tVersion,url,
							op,in);
				}
			}
			outString(hospitalId,currentUser, userResource, ip, serverIp, apkVersion,
					tVersion, url, op,errorSource,requsetParameter,errorSql,iscloudorTerminal,t_ip,str,isNetException, 4);
		}else{
			trace(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
					url,op,errorSource,requsetParameter,errorSql,iscloudorTerminal,in,LoggerLevel.ERROR,isNetException);
		}
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2870
	* 描述:获取错误信息堆栈
	* 作者:冯泽
	* 日期:2015年8月3日 下午8:10:31
	* @param in
	* @return
	* </pre>
	 */
	public static String ErrorStackTrace(Object in)
		{
		String str = "";
		if (in instanceof Throwable) {
			if (in instanceof InvocationTargetException) {
				str = ErrorStackTrace(((InvocationTargetException) in).getTargetException());
			} else {
				//Edit start fengze 替换html标签 KYEEAPPTEST-2873 云后台异常管理维护里面抛出异常信息 2015-8-11 19:50:28
				str = replaceHtmllable(in + ":") + "<br>";
				//Edit end fengze 替换html标签 KYEEAPPTEST-2873 云后台异常管理维护里面抛出异常信息 2015-8-11 19:50:28
				if (in instanceof BaseBllException) {
					str = str + errorStackTrace("","","","",
							"","","","","",BaseBllException.getFinalException((BaseBllException)in));
				}else{
					str = str + errorStackTrace("","","","",
							"","","","",
							"",in);
				}
			}
		}else{
			Throwable ex = new Throwable();
			str = errorStackTrace(in,ex);
		}
		return str;
	}
	
	/*private static void Info(String hospitalId,String currentUser, String userResource,
			String ip,String serverIp, String apkVersion, String tVersion, String url,
			String op,String requsetParameter,String errorSql,String errorMsgForMonitor,
			String iscloudorTerminal, Object in)SYS_EXCEPTION_PARA entity*/
	private static void Info(SYS_EXCEPTION_PARA entity)
		{
		Object in = entity.getin();
		String errorSql = entity.geterrorSql();
		String errorMsgForMonitor = entity.geterrorMsgForMonitor();
		String errorSource = entity.geterrorSource();
		String tVersion = entity.gettVersion();
		String iscloudorTerminal = entity.getiscloudorTerminal();
		String requsetParameter = entity.getrequsetParameter();
		String str = "";
		String t_ip = "";
		String hospitalId = entity.gethospitalId();
		String currentUser = entity.getcurrentUser();
		String userResource = entity.getuserResource();
		String ip = entity.getip();
		String serverIp = entity.getserverIp();
		String apkVersion = entity.getapkVersion();
		String url = entity.geturl();
		String op = entity.getop();
		// InvocationTargetException
		if (in instanceof Throwable) {
//			errorStackTrace(in);
			if (in instanceof InvocationTargetException) {
				entity.setin(((InvocationTargetException) in).getTargetException());
				error(entity);
				/*error(hospitalId,currentUser,userResource,ip,
						serverIp,apkVersion,tVersion,url,
						op,requsetParameter,errorSql,errorMsgForMonitor,iscloudorTerminal,((InvocationTargetException) in).getTargetException());*/
			} else {
				str = in + ":" + "<br>";
				if (in instanceof BaseBllException) {
					str = str + ((BaseBllException)in).getCustomMessage();
//					t1 = ((BaseBllException) in).getE();
					str = str + errorStackTrace(hospitalId,currentUser,userResource,ip,
							serverIp,apkVersion,tVersion,url,
							op,BaseBllException.getFinalException((BaseBllException)in));
				}else{
					str = str + errorStackTrace(hospitalId,currentUser,userResource,ip,
							serverIp,apkVersion,tVersion,url,
							op,in);
				}
			}
			outString(hospitalId,currentUser, userResource, ip, serverIp, apkVersion,
					tVersion, url, op,"0",requsetParameter,errorSql,iscloudorTerminal,"", str,false, 1);
		}else{
			trace(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
					url,op,"0",requsetParameter,errorSql,iscloudorTerminal,in,LoggerLevel.INFO,false);
		}
	}
	
	/*private static void Warn(String hospitalId,String currentUser, String userResource,
			String ip,String serverIp, String apkVersion, String tVersion, String url,
			String op,String requsetParameter,String errorSql,String errorMsgForMonitor,
			String iscloudorTerminal,Object in){*/
	private static void Warn(SYS_EXCEPTION_PARA entity){
		Object in = entity.getin();
		String errorSql = entity.geterrorSql();
		String tVersion = entity.gettVersion();
		String iscloudorTerminal = entity.getiscloudorTerminal();
		String requsetParameter = entity.getrequsetParameter();
		String str = "";
		String hospitalId = entity.gethospitalId();
		String currentUser = entity.getcurrentUser();
		String userResource = entity.getuserResource();
		String ip = entity.getip();
		String serverIp = entity.getserverIp();
		String apkVersion = entity.getapkVersion();
		String url = entity.geturl();
		String op = entity.getop();
		// InvocationTargetException
		if (in instanceof Throwable) {
//			errorStackTrace(in);
			if (in instanceof InvocationTargetException) {
				entity.setin(((InvocationTargetException) in).getTargetException());
				error(entity);
				/*error(hospitalId,currentUser,userResource,ip,
						serverIp,apkVersion,tVersion,url,
						op,requsetParameter,errorSql,errorMsgForMonitor,iscloudorTerminal,((InvocationTargetException) in).getTargetException());*/
			} else {
				str = in + "" + "<br>";
				if (in instanceof BaseBllException) {
//					t1 = ((BaseBllException) in).getE();
					str = str + errorStackTrace(hospitalId,currentUser,userResource,ip,
							serverIp,apkVersion,tVersion,url,
							op,BaseBllException.getFinalException((BaseBllException)in));
				}else{
					str = str + errorStackTrace(hospitalId,currentUser,userResource,ip,
							serverIp,apkVersion,tVersion,url,
							op,in);
				}
			}
			outString(hospitalId,currentUser, userResource, ip, serverIp, apkVersion,
					tVersion, url, op,"0",requsetParameter,errorSql,iscloudorTerminal,"", str,false, 2);
		}else{
			trace(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
					url,op,"0",requsetParameter,errorSql,iscloudorTerminal,in,LoggerLevel.WARN,false);
		}
	}
	
	/*private static void Debug(String hospitalId,String currentUser, String userResource,
			String ip,String serverIp, String apkVersion, String tVersion, String url,
			String op,String requsetParameter,String errorSql,String errorMsgForMonitor,
			String iscloudorTerminal, Object in){*/
	private static void Debug(SYS_EXCEPTION_PARA entity){
		Object in = entity.getin();
		String errorSql = entity.geterrorSql();
		String tVersion = entity.gettVersion();
		String iscloudorTerminal = entity.getiscloudorTerminal();
		String requsetParameter = entity.getrequsetParameter();
		String str = "";
		String hospitalId = entity.gethospitalId();
		String currentUser = entity.getcurrentUser();
		String userResource = entity.getuserResource();
		String ip = entity.getip();
		String serverIp = entity.getserverIp();
		String apkVersion = entity.getapkVersion();
		String url = entity.geturl();
		String op = entity.getop();
		//String errorMsgForMonitor = entity.geterrorMsgForMonitor();
		// InvocationTargetException
		if (in instanceof Throwable) {
//			errorStackTrace(in);
			if (in instanceof InvocationTargetException) {
				entity.setin(((InvocationTargetException) in).getTargetException());
				error(entity);
				/*error(hospitalId,currentUser,userResource,ip,
						serverIp,apkVersion,tVersion,url,
						op,requsetParameter,errorSql,errorMsgForMonitor,iscloudorTerminal,((InvocationTargetException) in).getTargetException());*/
			} else {
				str = in + "" + "<br>";
				if (in instanceof BaseBllException) {
//					t1 = ((BaseBllException) in).getE();
					str = str + errorStackTrace(hospitalId,currentUser,userResource,ip,
							serverIp,apkVersion,tVersion,url,
							op,BaseBllException.getFinalException((BaseBllException)in));
				}else{
					str = str + errorStackTrace(hospitalId,currentUser,userResource,ip,
							serverIp,apkVersion,tVersion,url,
							op,in);
				}
			}
			outString(hospitalId,currentUser, userResource, ip, serverIp, apkVersion,
					tVersion, url, op,"0",requsetParameter,errorSql,iscloudorTerminal,"", str, false,3);
		}else{
			trace(hospitalId,currentUser, userResource, ip, serverIp, apkVersion, tVersion,
					url,op,"0",requsetParameter,errorSql,iscloudorTerminal,in,LoggerLevel.DEBUG,false);
		}
	}
	
	
	public static void infoStackTrace(String hospitalId,String currentUser, String userResource,
			String ip, String serverIp, String apkVersion, String tVersion,
			String url, String op, Object o, Object in) {
		String str = new String();
		str = o + "" + "<br>";
		StringBuffer ident = new StringBuffer();
		StackTraceElement[] ste = ((Throwable) in).getStackTrace();
		for (int i = 0; i < ste.length; i++) {
			String temp = ste[i].toString();
			if (checkLogFlag(temp)) {
				ident.append("&nbsp;");
				str = str + ident+temp+"<br>";
			}
		}
		outString(hospitalId,currentUser, userResource, ip, serverIp, apkVersion,
				tVersion, url, op, "0","","","","",str,false, 1);
	}
	
	private static void warnStackTrace(String hospitalId,String currentUser, String userResource,
			String ip, String serverIp, String apkVersion, String tVersion,
			String url, String op, Object o, Object in) {
		StackTraceElement[] ste = ((Throwable) in).getStackTrace();
		int i0 = 0;
		String str = new String();
		StringBuffer ident = new StringBuffer();
		str = o + "" + "<br>";
		for (int i = 0; i < ste.length; i++) {
			String temp = ste[i].toString();
			if (checkLogFlag(temp)) {
				for (int ii = 0; ii <= i0; ii++) {
					ident.append("&nbsp;");
				}
				str = str + ident + temp + "<br>";
				i0++;
			}
		}
		outString(hospitalId,currentUser, userResource, ip, serverIp, apkVersion,
				tVersion, url, op,"0","","","","", str,false,2);
	}
	
	private static void debugStackTrace(String hospitalId,String currentUser, String userResource,
			String ip, String serverIp, String apkVersion, String tVersion,
			String url, String op, Object o, Object in) {
		StackTraceElement[] ste = ((Throwable) in).getStackTrace();
		String str = new String();
		StringBuffer ident=new StringBuffer();
		str = o + "" + "<br>";
		int i0=0;
		for (int i = 0; i < ste.length; i++) {
			String temp=ste[i].toString();
			if(checkLogFlag(temp)){
				for(int ii=0;ii<=i0;ii++){
					ident.append("&nbsp;");
				}
				str = str + ident + temp + "<br>";
				i0++;
			}
		}
		outString(hospitalId,currentUser, userResource, ip, serverIp, apkVersion,
				tVersion, url, op,"0","","","","",str, false,3);
	}
	
	private static String errorStackTrace(Object o,Object in){
		StackTraceElement[] ste = ((Throwable) in).getStackTrace();
		int i0=0;
		String str = new String();
		str = replaceHtmllable(o + ":") + "<br>";
		for (int i = 0; i < ste.length; i++) {
			String temp=ste[i].toString();
			if(checkLogFlag(temp)){
				StringBuffer ident=new StringBuffer();
				for(int ii=0;ii<=i0;ii++){
					ident.append("&nbsp;");
				}
				str = str + ident + temp + "<br>";
				i0++;
			}
		}
		return str;
	}
	
	private static void errorStackTrace(String hospitalId,String currentUser, String userResource,
			String ip, String serverIp, String apkVersion, String tVersion,
			String url, String op, String errorSource,String requsetParameter,
			String errorSql,String isCloudOrTerminal,Object o, Object in,boolean isNetException) {
		StackTraceElement[] ste = ((Throwable) in).getStackTrace();
		int i0=0;
		String str = new String();
		//Edit start fengze 替换html标签 KYEEAPPTEST-2873 云后台异常管理维护里面抛出异常信息 2015-8-11 19:50:28
		str = replaceHtmllable(o + "") + "<br>";
		//Edit end fengze 替换html标签 KYEEAPPTEST-2873 云后台异常管理维护里面抛出异常信息 2015-8-11 19:50:28
		for (int i = 0; i < ste.length; i++) {
			String temp=ste[i].toString();
			if(checkLogFlag(temp)){
				StringBuffer ident=new StringBuffer();
				for(int ii=0;ii<=i0;ii++){
					ident.append("&nbsp;");
				}
				str = str + ident + temp + "<br>";
				i0++;
			}
		}
		outString(hospitalId,currentUser, userResource, ip, serverIp, apkVersion,
				tVersion, url, op,errorSource,requsetParameter,errorSql,isCloudOrTerminal,"", str,isNetException, 4);
	}
	
	private static String errorStackTrace(String hospitalId,String currentUser, String userResource,
			String ip, String serverIp, String apkVersion, String tVersion,
			String url, String op,Object in) {
		StackTraceElement[] ste = ((Throwable) in).getStackTrace();
		int i0=0;
		String str = new String();
		str = "";
		for (int i = 0; i < ste.length; i++) {
//			error(ste[i]);
			String temp=ste[i].toString();
			if(checkLogFlag(temp)){
				StringBuffer ident=new StringBuffer();
				for(int ii=0;ii<=i0;ii++){
					ident.append("&nbsp;");
				}
				str = str + ident + temp + "<br>";
				i0++;
			}
		}
		return str;
	}
	/**
	 *任务：
	 *描述：过滤和应用本身无关的异常信息
	 *人员：施建龙
	 *时间：2015年2月1日下午3:18:17
	 **/
	private static boolean checkLogFlag(String message){
		String tempUpper=message.toUpperCase();
		if((tempUpper.indexOf("APP.")>=0 || tempUpper.indexOf("KYEE")>=0 
				|| tempUpper.indexOf("BAIDU.YUN")>=0 
				|| tempUpper.indexOf("COM.FRAMEWORK")>=0
				|| tempUpper.indexOf("COM.UTIL")>=0
				|| tempUpper.indexOf("SABER.WEBSERVER")>=0
				|| tempUpper.indexOf("QUYIYUAN")>=0)
				&& tempUpper.indexOf("HDBLOGGERUTIL")<0){
					return true;
				}else{
					return false;
				}
	}
	
	private static String getUserSource(HttpContext _context) {
		String userSource = _context.getParameter("userSource");
		if (DotNetToJavaStringHelper.isNullOrEmpty(userSource)) {
			userSource=_context.getParameter("operateUserSource");
			if (DotNetToJavaStringHelper.isNullOrEmpty(userSource)) {
				userSource = _context.getParameter("USER_SOURCE");
				if (DotNetToJavaStringHelper.isNullOrEmpty(userSource)) {
					userSource = _context.getParameter("APPOINT_SOURCE");
					if (DotNetToJavaStringHelper.isNullOrEmpty(userSource)) {
						userSource = "0";
					}
				}
			}
		}
		return userSource;
	}
	
	private static String getUserId(HttpContext _context)
	{
		String userId=_context.getParameter("operateCurrent_UserId");
		if(DotNetToJavaStringHelper.isNullOrEmpty(userId))
		{
		  userId=_context.getParameter("USER_ID");
		  if (DotNetToJavaStringHelper.isNullOrEmpty(userId))
	         {
			    userId = _context.getParameter("userId");	            
	            if (DotNetToJavaStringHelper.isNullOrEmpty(userId))
	            {
	            	userId = "-1";
	            }
	         }
		}
		 return userId;
	}
	private  static String getHospitalID(HttpServletRequest request) {
		String hospitalID = request.getParameter("hospitalID");
		if (DotNetToJavaStringHelper.isNullOrEmpty(hospitalID)) {
			hospitalID = request.getParameter("HOSPITAL_ID");
			if (DotNetToJavaStringHelper.isNullOrEmpty(hospitalID)) {
				hospitalID = request.getParameter("hospitalId");
				if (DotNetToJavaStringHelper.isNullOrEmpty(hospitalID)) {
					hospitalID = "-1";
				}
			}
		}
		return hospitalID;
	}
	
	public static void outString(String hospitalId,String currentUser, String userResource,
			String ip, String serverIp, String apkVersion, String tVersion,
			String url, String op,String errorSource,String requsetParameter,
			String errorSql,String isCloudOrTerminal,String t_ip,String out,boolean isNetException,int type){
		String switchState = getSwitch();
	    String SystemExceptionSwitch = getSystemExceptionSwitch(); //是否记录非系统级异常
	    String netExceptionSwitch = "";
	    if("1".equals(switchState))
    	{
    		if("1".equals(SystemExceptionSwitch))
    		{
    			//Edit start fengze KYEEAPPC-5717 添加网络异常标识 2016年3月31日 20:48:08
    			if(isNetException){
    				netExceptionSwitch = getNetExceptionSwitch();
    				if("1".equals(netExceptionSwitch)){
    					OutStringExt(userResource, 
        						errorSource,errorSql,isCloudOrTerminal,tVersion,out, type,hospitalId);
    				}
    			}else{
    				OutStringExt(userResource, 
    						errorSource,errorSql,isCloudOrTerminal,tVersion,out, type,hospitalId);
    			}
    			//Edit end fengze KYEEAPPC-5717 添加网络异常标识 2016年3月31日 20:48:08
    		}else{
    			//记录非系统异常开关关闭
    			if("2".equals(errorSource))
    			{
    				//非系统级异常，开关关闭，不记录
    			}else{
    				//系统级异常与未设置异常来源
    				//Edit start fengze KYEEAPPC-5717 添加网络异常标识 2016年3月31日 20:48:08
    				if(isNetException){
    					netExceptionSwitch = getNetExceptionSwitch();
        				if("1".equals(netExceptionSwitch)){
        					OutStringExt(userResource, 
            						errorSource,errorSql,isCloudOrTerminal,tVersion,out, type,hospitalId);
        				}
    				}else{
    					OutStringExt(userResource,
        						errorSource,errorSql,isCloudOrTerminal,tVersion,out, type,hospitalId);	
    				}
    				//Edit end fengze KYEEAPPC-5717 添加网络异常标识 2016年3月31日 20:48:08
    			}
    		}
    	}
	}
	
	
	private static String getSwitch()
	{
        /*IDataBase appDb = null;
        AbstractDataBase appDB = null;
        String result = "";
        try {
            appDb = DBFactory.CreateAppDB();
            appDB = (AbstractDataBase) appDb;
            appDB.Open();
            appDB.NeedTransaction(false);
            String sqlStr = " SELECT PARA_VALUE "
            		      + " FROM SYS_PARAMETER "
            		      + " WHERE SYS_PARA_ID = 6518 ";
            result = appDb.QueryObject(sqlStr, null)+"";
            //return result;
        } catch (Exception e) {
            // /#region 回滚事务
            try {
                if(appDB != null){
                    appDB.failure();
                }
            } catch (Exception e1) {
                HLogger.error(e1);
            }
        } finally {
            try {	
                if (appDB != null) {
                    appDB.Close();
                }
            } catch (Exception e) {
                HLogger.error(e);
            }
        }
		return result; */ 
		String result = "1";
//		try{
//			result = PubSystemParams.getParams("HDBLOGGER_SWITCH", null);
//		}catch(Exception e){
//			HLogger.error(e);
//		}
		return result; 
	}
	
	private static String getSystemExceptionSwitch()
	{
        /*IDataBase appDb = null;
        AbstractDataBase appDB = null;
        String result = "";
        try {
            appDb = DBFactory.CreateAppDB();
            appDB = (AbstractDataBase) appDb;
            appDB.Open();
            appDB.NeedTransaction(false);
            String sqlStr = " SELECT PARA_VALUE "
            		      + " FROM SYS_PARAMETER "
            		      + " WHERE SYS_PARA_ID = 6519 ";
            result = appDb.QueryObject(sqlStr, null)+"";
            //return result;
        } catch (Exception e) {
            // /#region 回滚事务
            try {
                if(appDB != null){
                    appDB.failure();
                }
            } catch (Exception e1) {
                HLogger.error(e1);
            }
        } finally {
            try {	
                if (appDB != null) {
                    appDB.Close();
                }
            } catch (Exception e) {
                HLogger.error(e);
            }
        }*/
		String result = "1";
//		try{
//			result = PubSystemParams.getParams("HDBLOGGER_SWITCH_IS_SYSTEM", null);
//		}catch(Exception e){
//			HLogger.error(e);
//		}
		return result;    
	}
	
	private static String getNetExceptionSwitch(){
		String result = "1";
//		try{
//			result = PubSystemParams.getParams("NET_EXCEPTION_SWITCH", null);
//		}catch(Exception e){
//			HLogger.error(e);
//		}
		return result;
	}
	
	private static void OutString(String hospitalId,String currentUser, String userResource,
			String ip, String serverIp, String apkVersion, String tVersion,
			String url, String op,String errorSource,String requsetParameter,
			String errorSql,String isCloudOrTerminal,String cloudVersion,String t_ip,
			String out,int type){
	        IDataBase baseDb = null;
	        AbstractDataBase baseDB = null;
	        try {
	        	baseDb = DBFactory.CreateBaseDB();
	        	baseDB = (AbstractDataBase) baseDb;
	        	baseDB.OpenBase();
	        	baseDB.NeedTransaction(false);
	            String sqlStr = " insert into M_SYSTEM_EXCEPTION_RECORDS (USER_ID,VISIT_OP,"
	            		+ "IP,SERVER_IP,USER_RESOURCE,"
	            		+ "ERROR_MESSAGE,EXCEPTION_TYPE,CREATE_TIME,EXCEPTION_SOURCE,ERROR_SQL,"
	            		+ "IF_SQL_EXCEPTION,REQUEST_PARAMETERS) "
	            		+ "values(:USER_ID,:VISIT_OP,"
	            		+ ":IP,:SERVER_IP,:USER_RESOURCE,"
	            		+ ":ERROR_MESSAGE,:EXCEPTION_TYPE,:CREATE_TIME,:EXCEPTION_SOURCE,:ERROR_SQL,"
	            		+ ":IF_SQL_EXCEPTION,:REQUEST_PARAMETERS) ";
	            List<AppDbParameter> paramList = new ArrayList<AppDbParameter>();
	            Date createTime = baseDb.ServerDate();
	            paramList.add(new AppDbParameter("USER_ID",currentUser));
//	            paramList.add(new AppDbParameter("HOSPITAL_ID",hospitalId));
	            paramList.add(new AppDbParameter("VISIT_OP",url+"/"+op));
	            paramList.add(new AppDbParameter("IP",ip));
	            paramList.add(new AppDbParameter("SERVER_IP",serverIp));
//	            paramList.add(new AppDbParameter("VERSION",apkVersion));
//	            paramList.add(new AppDbParameter("T_VERSION",tVersion));
	            paramList.add(new AppDbParameter("USER_RESOURCE",userResource));
	            paramList.add(new AppDbParameter("EXCEPTION_TYPE",type));
	            paramList.add(new AppDbParameter("ERROR_MESSAGE",out));
	            paramList.add(new AppDbParameter("CREATE_TIME",createTime));
//	            paramList.add(new AppDbParameter("CLOUD_VERSION",cloudVersion));
	            if(DotNetToJavaStringHelper.isNullOrEmpty(errorSource))
	            {
	            	  paramList.add(new AppDbParameter("EXCEPTION_SOURCE","0"));
	            }else{
	            	 paramList.add(new AppDbParameter("EXCEPTION_SOURCE",errorSource));
	            }
	            if(DotNetToJavaStringHelper.isNullOrEmpty(errorSql))
	            {
	            	  paramList.add(new AppDbParameter("ERROR_SQL",""));
	            	  paramList.add(new AppDbParameter("IF_SQL_EXCEPTION","0"));
	            }else{
	            	 paramList.add(new AppDbParameter("ERROR_SQL",errorSql));
	            	 paramList.add(new AppDbParameter("IF_SQL_EXCEPTION","1"));
	            }
//	            if(DotNetToJavaStringHelper.isNullOrEmpty(isCloudOrTerminal))
//	            {
//	            	paramList.add(new AppDbParameter("CLOUD_OR_TERMINAL","3"));
//	            }else{
//	            	paramList.add(new AppDbParameter("CLOUD_OR_TERMINAL",isCloudOrTerminal));
//	            }
	            paramList.add(new AppDbParameter("REQUEST_PARAMETERS",requsetParameter));
	            
	          //Edit start fengze KYEEAPPTEST-2980 修改异常监控逻辑，任务状态修改为“解决”以后，如果问题再次出现，在主表（c_system_exception_records）中记录一条数据 2015-9-1 10:58:38
	            String sqlSelect = " SELECT ID FROM M_SYSTEM_EXCEPTION_RECORDS WHERE ERROR_MESSAGE=:ERROR_MESSAGE ";
	          //Edit end fengze KYEEAPPTEST-2980 修改异常监控逻辑，任务状态修改为“解决”以后，如果问题再次出现，在主表（c_system_exception_records）中记录一条数据 2015-9-1 10:58:38
	            List<AppDbParameter> paramListSelect = new ArrayList<AppDbParameter>();
	            paramListSelect.add(new AppDbParameter("ERROR_MESSAGE",out));
	            String resultSelect = baseDb.QueryObject(sqlSelect, paramListSelect)+"";
	            if(DotNetToJavaStringHelper.isNullOrEmpty(resultSelect))
	            {
	            	baseDb.Save(sqlStr, paramList);	
	            	String resultSelectT = baseDb.QueryObject(sqlSelect, paramListSelect)+"";
	            	String sqlStrRecordDetails = " INSERT INTO C_SYSTEM_EXCEPTION_RECORDS_DETAILS (SERVER_IP,"
		            			+ "CREATE_TIME,RECORD_ID,USER_ID,HOSPITAL_ID,IP,T_IP,VERSION,T_VERSION,"
		            			+ "CLOUD_VERSION,USER_RESOURCE,REQUEST_PARAMETERS,VISIT_OP) VALUES(:SERVER_IP,:CREATE_TIME,"
		            			+ ":RECORD_ID,:USER_ID,:HOSPITAL_ID,:IP,:T_IP,:VERSION,:T_VERSION,"
		            			+ ":CLOUD_VERSION,:USER_RESOURCE,:REQUEST_PARAMETERS,:VISIT_OP) ";
			            List<AppDbParameter> recordDetailsParamList = new ArrayList<AppDbParameter>();
			            recordDetailsParamList.add(new AppDbParameter("SERVER_IP",serverIp));
			            recordDetailsParamList.add(new AppDbParameter("CREATE_TIME",createTime));
			            recordDetailsParamList.add(new AppDbParameter("RECORD_ID",resultSelectT));
			            recordDetailsParamList.add(new AppDbParameter("USER_ID",currentUser));
			            recordDetailsParamList.add(new AppDbParameter("HOSPITAL_ID",hospitalId));
			            recordDetailsParamList.add(new AppDbParameter("IP",ip));
			            recordDetailsParamList.add(new AppDbParameter("T_IP",t_ip));
			            recordDetailsParamList.add(new AppDbParameter("VERSION",apkVersion));
			            recordDetailsParamList.add(new AppDbParameter("T_VERSION",tVersion));
			            recordDetailsParamList.add(new AppDbParameter("CLOUD_VERSION",cloudVersion));
			            recordDetailsParamList.add(new AppDbParameter("USER_RESOURCE",userResource));
			            recordDetailsParamList.add(new AppDbParameter("REQUEST_PARAMETERS",requsetParameter));
			            recordDetailsParamList.add(new AppDbParameter("VISIT_OP",url+"/"+op));
			            baseDb.Save(sqlStrRecordDetails,recordDetailsParamList);
	            }else{
	            	String sqlStrRecordDetails = " INSERT INTO C_SYSTEM_EXCEPTION_RECORDS_DETAILS (SERVER_IP,"
	            			+ "CREATE_TIME,RECORD_ID,USER_ID,HOSPITAL_ID,IP,T_IP,VERSION,T_VERSION,"
	            			+ "CLOUD_VERSION,USER_RESOURCE,REQUEST_PARAMETERS) VALUES(:SERVER_IP,:CREATE_TIME,"
	            			+ ":RECORD_ID,:USER_ID,:HOSPITAL_ID,:IP,:T_IP,:VERSION,:T_VERSION,"
	            			+ ":CLOUD_VERSION,:USER_RESOURCE,:REQUEST_PARAMETERS) ";
		            List<AppDbParameter> recordDetailsParamList = new ArrayList<AppDbParameter>();
		            recordDetailsParamList.add(new AppDbParameter("SERVER_IP",serverIp));
		            recordDetailsParamList.add(new AppDbParameter("CREATE_TIME",createTime));
		            recordDetailsParamList.add(new AppDbParameter("RECORD_ID",resultSelect));
		            recordDetailsParamList.add(new AppDbParameter("USER_ID",currentUser));
		            recordDetailsParamList.add(new AppDbParameter("HOSPITAL_ID",hospitalId));
		            recordDetailsParamList.add(new AppDbParameter("IP",ip));
		            recordDetailsParamList.add(new AppDbParameter("T_IP",t_ip));
		            recordDetailsParamList.add(new AppDbParameter("VERSION",apkVersion));
		            recordDetailsParamList.add(new AppDbParameter("T_VERSION",tVersion));
		            recordDetailsParamList.add(new AppDbParameter("CLOUD_VERSION",cloudVersion));
		            recordDetailsParamList.add(new AppDbParameter("USER_RESOURCE",userResource));
		            recordDetailsParamList.add(new AppDbParameter("REQUEST_PARAMETERS",requsetParameter));
		            baseDb.Save(sqlStrRecordDetails,recordDetailsParamList);
	            }
	        } catch (Exception e) {
	            // /#region 回滚事务
	            try {
	                if (baseDB != null) {
	                    baseDB.failure();
	                }
	            } catch (Exception e1) {
	                HLogger.error(e1);
	            }
	        } finally {
	            try {	
	                if (baseDB != null) {
	                	baseDB.Close();
	                }
	            } catch (Exception e) {
	                HLogger.error(e);
	            }
	        }     
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-3050
	* 描述:存储数据到数据库
	* 作者:冯泽
	* 日期:2015年8月21日 下午2:42:39
	* @param userResource 用户来源
	* @param errorSource 错误来源
	* @param errorSql 错误SQL
	* @param isCloudOrTerminal 端或者云
	* @param out 堆栈信息
	* @param type 异常类型
	* </pre>
	 */
	private static void OutStringExt( String userResource,
			String errorSource,
			String errorSql,String isCloudOrTerminal,String tVersion,
			String out,int type,String hospitalId)
	{
		String currentCloudTVersion = "";
		if("1".equals(isCloudOrTerminal)){
			//如果是端异常，则查询数据库获取目前云医院表中存储的端版本号
			currentCloudTVersion = getCloudTVersion(hospitalId);
		}
		M_SYSTEM_EXCEPTION_RECORDS_EXT entity = new M_SYSTEM_EXCEPTION_RECORDS_EXT();
		entity.setERROR_MESSAGE(out);//堆栈信息
		entity.setEXCEPTION_TYPE(type);//异常类型
		entity.setUSER_RESOURCE(userResource);
		entity.setCREATE_TIME(new Timestamp(System.currentTimeMillis()));//创建时间
//		entity.setT_VERSION(tVersion);
//		entity.setCURRENT_CLOUD_T_VERSION(currentCloudTVersion);
		if(DotNetToJavaStringHelper.isNullOrEmpty(errorSource))
        {
			//异常来源，0:未设置
			entity.setEXCEPTION_SOURCE("0");
        }else{
        	//异常来源，1:系统异常 2:非系统异常
        	entity.setEXCEPTION_SOURCE(errorSource);
        }
		if(DotNetToJavaStringHelper.isNullOrEmpty(errorSql))
        {
			entity.setERROR_SQL("");
			//非SQL错误
			entity.setIF_SQL_EXCEPTION("0");
        }else{
        	entity.setERROR_SQL(errorSql);
        	//SQL错误
        	entity.setIF_SQL_EXCEPTION("1");
        }
		 if(DotNetToJavaStringHelper.isNullOrEmpty(isCloudOrTerminal))
         {
			//未设置
//         	entity.setCLOUD_OR_TERMINAL("3");
         }else{
        	 //1:端异常,0:云异常
//        	entity.setCLOUD_OR_TERMINAL(isCloudOrTerminal);
         }
	}
	
	private static String getCloudTVersion(String hospitalid){
//edit start KYEEAPPC-4033 wangzhang 整改系统去系统参数的地方，都从只读库区 2015年11月17日 13:29:48
//		 IDataBase appDb = null;
//	     AbstractDataBase appDB = null;
	     String result = "";
	     if(!DotNetToJavaStringHelper.isNullOrEmpty(hospitalid) && !"-1".equals(hospitalid))
	     {
	        try {
//	            appDb = DBFactory.CreateAppDB();
//	            appDB = (AbstractDataBase) appDb;
//	            appDB.Open();
//	            appDB.NeedTransaction(false);
//	            String sqlStr = " SELECT T_VERSION FROM C_SYSTEM_HOSPITAL WHERE HOSPITAL_ID =:HOSPITAL_ID ";
//	            List<AppDbParameter> para = new ArrayList<AppDbParameter>(); 
//	    		para.add(new AppDbParameter("HOSPITAL_ID", hospitalid));
//	    		result = (String) appDb.QueryObject(sqlStr, para)+"";
//	        	result = GetHosTerminerVersion.getTerminerVersion(hospitalid);
	            //return result;
	        } catch (Exception e) {
	            // /#region 回滚事务
//	            try {
//	                if(appDB != null){
//	                    appDB.failure();
//	                }
//	            } catch (Exception e1) {
//	                HLogger.error(e1);
//	            }
	            HLogger.error(e);
	        } 
//	        finally {
//	            try {	
//	                if (appDB != null) {
//	                    appDB.Close();
//	                }
//	            } catch (Exception e) {
//	                HLogger.error(e);
//	            }
//	        }
//edit end   KYEEAPPC-4033 wangzhang 整改系统去系统参数的地方，都从只读库区 2015年11月17日 13:29:48

	     }
			return result; 
	}
	
	public static String getRequestMapString(HttpContext _context){
		// 参数Map
	    Map properties = _context.getRequest().getParameterMap();
	    // 返回值Map
	    Map mapTemp = new HashMap();
	    Iterator entries = properties.entrySet().iterator();
	    Map.Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        mapTemp.put(name, value);
	    }
	    return JsonUtil.getJsonGson().toJson(mapTemp);
	}
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2870
	* 描述:替换特殊字符
	* 作者:冯泽
	* 日期:2015年8月4日 上午10:49:39
	* @param value
	* @return
	* </pre>
	 */
	public static String covertString(String value)
	{
		/*String result = "";
		result = value.replaceAll("'", "Monitor_Single_Quotes");
		result = result.replaceAll(":", "Monitor_Colon");
		result = result.replaceAll(" ", "Monitor_Blank_Space");
		result = result.replaceAll(";", "Monitor_Semicolon");
		result = result.replaceAll("\n", "Monitor_Enter");
		result = result.replaceAll("\r", "Monitor_Newline");
		result = result.replaceAll("\"", "Monitor_Double_Quotation_Marks");
		result = result.replaceAll("[{]", "Monitor_Left_Brace");
		result = result.replaceAll("[}]", "Monitor_Right_Brace");
		result = result.replaceAll(",", "Monitor_Comma");
		result = result.replaceAll("/", "Monitor_forward_slash");
		result = result.replaceAll("\\[", "Monitor_Left_Brackets");
		result = result.replaceAll("\\]", "Monitor_Right_Brackets");
		return result;*/
		return value;
	}
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPC-2870
	* 描述:还原特殊字符
	* 作者:冯泽
	* 日期:2015年8月4日 上午10:49:58
	* @param value
	* @return
	* </pre>
	 */
	public static String revertString(String value)
	{
		String result = "";
		result = value.replaceAll("Monitor_Single_Quotes", "'");
		result = result.replaceAll("Monitor_Colon", ":");
		result = result.replaceAll("Monitor_Blank_Space", " ");
		result = result.replaceAll("Monitor_Semicolon", ";");
		result = result.replaceAll("Monitor_Enter", "\n");
		result = result.replaceAll("Monitor_Newline", "\r");
		result = result.replaceAll("Monitor_Double_Quotation_Marks", "\"");
		result = result.replaceAll("Monitor_Left_Brace", "{");
		result = result.replaceAll("Monitor_Right_Brace", "}");
		result = result.replaceAll("Monitor_Comma", ",");
		result = result.replaceAll("Monitor_forward_slash", "/");
		result = result.replaceAll("Monitor_Comma", ",");
		result = result.replaceAll("Monitor_Left_Brackets ", "\\[");
		result = result.replaceAll("Monitor_Right_Brackets ", "\\]");
		return result;
	}
	
	public static String getErrorInfoFromException(Throwable e) {  
        try {  
            StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            return "\r\n" + sw.toString() + "\r\n";  
        } catch (Exception e2) {
            return "bad get Error Info From Exception!";  
        }  
    }
	
	/**
	 * 
	* <pre>
	* 任务:KYEEAPPTEST-2873
	* 描述:云后台异常管理维护里面抛出异常信息
	* 作者:冯泽
	* 日期:2015年8月11日 下午7:42:10
	* @param str
	* @return
	* </pre>
	 */
	public static String replaceHtmllable(String str)
	{
		String subStr = str.replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", "");
		return subStr;
	}
	/*private static void outString(String out,int type)
	{
		System.out.print(out);
	}
	*/
	public static void main(String args[])
	{
		String str = new String();
		str = "hel[l]o" + "\r\n";
		System.out.println(str);
		System.out.println(covertString(str));
		System.out.println(revertString(str));
		//outString(str,1);
	}
}
