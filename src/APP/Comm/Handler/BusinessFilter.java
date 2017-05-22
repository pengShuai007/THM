package APP.Comm.Handler;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.BLL.FatalBllException;
import APP.Comm.Config.SystemParams;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HDBLoggerUtil;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.HandlerUtil;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.JsonUtil.JsonAlertType;
import APP.Comm.Util.TimeStampRadomUtil;

/**
 * 说明： 通过此Filter,进行预处理
 * 在前端和BusinessHandler之间建立关联
 * 作者：施建龙 时间：2014年3月25日-下午1:25:07
 */
public class BusinessFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HLogger hLogger = null;
		HttpServletRequest _request = (HttpServletRequest) request;
		HttpServletResponse _response = (HttpServletResponse) response;
		ServletContext servletContext = null;
		
		//edit start KYEEAPP-1120 houhy 2014年12月11日 23:24:57
		_request.setCharacterEncoding("UTF-8");
		_response.setCharacterEncoding("UTF-8");//_request.getParameterNames()
		String uri = null;
        String op = null;
        String loc=null;
		
		HttpContext _context = new HttpContext(_request, _response);
		//edit end KYEEAPP-1120 houhy 2014年12月11日 23:24:57
		
		//Add start KYEEAPPMAINTENANCE-1162  用户操作记录 2016年12月12日 09:45:01
		String terminal_monitor_process_id = "";
		String terminal_monitor_flag = "N"; //是否开启端监控
		
		try {
			//edit start KYEEAPPMAINTENANCE-1162  用户操作记录 2016年12月12日 09:45:01
			uri = _request.getRequestURI().replace("//", "/");
			op = _request.getParameter("op");
			loc = _context.getParameter("loc");
			
			HLogger.info("BusinessFilter doFilter Start!");
			terminal_monitor_process_id = checkUserProcessMonitor(
					loc, terminal_monitor_flag, terminal_monitor_process_id,
					_context);
			HLogger.info("请求URI:" + uri + ",Method=" + _request.getMethod()
	        + ",op=" + op);
			if (uri.indexOf("/login.jsp") > -1 || uri.indexOf("main.jsp") > -1
					|| uri.endsWith(".jspx")) {
				_response
						.setHeader("Access-Control-Allow-Methods", "POST, GET");
				_response.setHeader("Access-Control-Allow-Origin", "*");
				_response.setHeader("Access-Control-Allow-Headers",
						"Content-Type, Authorization, Accept,X-Requested-With");
				servletContext = request.getServletContext();
				
				_context.setServletContext(servletContext);
				response.setContentType("text/html;charset=UTF-8");
				/**
				 *任务：
				 *描述：store组件会自动发送options方法请求，针对options方法，app框架不做处理
				 *人员：施建龙
				 *时间：2014年11月20日上午11:19:43
				 **/
				if (!_request.getMethod().equals("OPTIONS")) {
					initBusinessHandler(_context).ProcessRequest();
					HLogger.info("BusinessFilter doFilter end!");
				} else {
					HLogger.info("BusinessFilter doFilter end!");
					return;
				}
			} else {
				chain.doFilter(request, response);
				HLogger.info("BusinessFilter doFilter end!");
				return;
			}
		} catch (Exception ex) {
			terminal_monitor_process_id = analysisToSolveException(hLogger,loc,
					terminal_monitor_process_id,
					_context, ex.getCause());
			String msg="";
			if(ex.getCause() instanceof BaseBllException){
				Throwable excetion = BaseBllException.getFinalException(ex.getCause());
				HLogger.error(excetion);
				if (excetion != null && excetion.getMessage() != null) {
					msg = excetion.getMessage().replaceAll("'", "").replace("\n", "");
				}
				_context.write(JsonUtil.exceptionJson(msg));
			}else{
				// 系统级别异常处理
				Throwable excetion = ex.getCause();
				HLogger.error(excetion);
				if (excetion != null && excetion.getMessage() != null) {
					msg = excetion.getMessage().replace("\r\n", "")
							.replaceAll("'", "");
				}
				msg = msg.replace("\n", "");
				msg = msg.replace("\r", "");
				_context.write(JsonUtil.exceptionJson("很抱歉，您本次提交的业务没有正常完成，" + "请参考以下详细信息解决。如需进一步帮助，请联系趣医技术支持人员！<br/>详细信息："
	                    + msg));
			}
		}
	}

	/**
	 * 说明： 作者：施建龙 时间：2014年3月25日-下午1:33:45
	 */
	private BusinessHandler initBusinessHandler(HttpContext _context)
			throws BaseBllException {
		BusinessHandler businessHandler = new BusinessHandler();
		_context.setHttpHandler(businessHandler);
		businessHandler.initContext(_context);
		return businessHandler;
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	/**
	 * <pre>
	 * 任务： KYEEAPPMAINTENANCE-1162
	 * 描述： 检查用户访问记录,设置用户或操作是否被监控
	 * 作者：huangpeng 
	 * 时间：2016年12月12日上午10:30:47
	 * @param _context
	 * returnType：void
	 * </pre>
	 */
	private String checkUserProcessMonitor(String loc, 
			String terminal_monitor_flag,
			String terminal_monitor_process_id, HttpContext _context) {
		return terminal_monitor_process_id;
	}
	/**
	 * 
	* <pre>
	* 任务:
	* 描述:分析并解决异常
	* 作者:冯泽
	* 日期:2016年3月1日 下午3:19:08
	* @param HLogger
	* @param loc
	* @param terminal_monitor_process_id
	* @param _context
	* @param ex
	* @return
	* </pre>
	 */
	private String analysisToSolveException(HLogger hLogger, String loc,
			String terminal_monitor_process_id,
			HttpContext _context, Throwable ex) {
		String errorSql = "errorSql";
		String errorStackTrace = "";
		String errorLevel = "";
		String errorMsgForMonitor;
		String serverVersion;
		String requestParameters;
		String serverIP;
		//String terminal_need_monitor = _context.getRequest().getParameter("terminal_need_monitor");
		//Edit start fengze KYEEAPPC-5717 添加网络异常标识 2016年3月31日 20:48:08
		boolean isNetException = false;
		//Edit end fengze KYEEAPPC-5717 添加网络异常标识 2016年3月31日 20:48:08
		if("t".equals(loc))
		 {
			 terminal_monitor_process_id = TimeStampRadomUtil.GetARadomValue();
		 }
		// edit start KYEEAPP-1120 统一在框架的最外层处理异常 houhy 2014年12月11日 23:20:29
		String msg = "";
		if (ex instanceof BaseBllException) {
			
			isNetException = ((BaseBllException) ex).isNetException();
		    // HLogger.error(ex);
			errorSql = errorSql + ((BaseBllException) ex).getErrorSql();
			//云获取端堆栈信息
			errorMsgForMonitor = ((BaseBllException) ex).getErrorMsgForMonitor() +"";
			//String userSource = getUserSource(_context);
			String isWebS = _context.getParameter("isWebS");
		    Throwable excetion = BaseBllException.getFinalException(ex);
		    if (excetion instanceof BaseBllException) {
		        /**
		         * 任务：*描述：如果为FatalBllException,则输出ERROR，系统级异常，输出ERROR，
		         * 其他输出WARN 原则：后续需要抛出异常的代码块，都不进行日志输出 人员：施建龙
		         * *时间：2015年5月27日上午11:04:12
		         **/
				if (excetion instanceof FatalBllException) {
					if (!"t".equals(loc)) {
						if ("1".equals(isWebS)) {
							// 修改userSource判断条件 2015-8-21 09:06:39
							errorStackTrace = HDBLoggerUtil
									.ErrorStackTrace(excetion);
							errorLevel = "system";
						}
						// 云异常，记录一查异常信息到数据库
						HDBLoggerUtil.cloudSystemError(_context, errorSql,
								errorMsgForMonitor, excetion, isNetException);
					} else {
						// 端异常、获取堆栈、SQL信息，返回给云
						errorStackTrace = HDBLoggerUtil
								.ErrorStackTrace(excetion);
						errorLevel = "system";
					}
					// 针对“系统级异常”，直接存储至云数据库中，并提供后台的“异常管理维护”功能 2015-7-17 15:20:00
					HLogger.error(excetion);
				} else {
					HLogger.warnStackTrace(excetion);
					// Edit add start fengze KYEEAPPC-2867 添加对webservice层的监控
					// 2015-7-26 12:01:46
					if (!"t".equals(loc)) {
						// Edit start fengze KYEEAPPTEST-2913 修改userSource判断条件
						// 2015-8-21 09:06:39
						if ("1".equals(isWebS)) {
							errorStackTrace = HDBLoggerUtil
									.ErrorStackTrace(excetion);
							errorLevel = "business";
						}
						// Edit end fengze KYEEAPPTEST-2913 修改userSource判断条件
						// 2015-8-21 09:06:39
						// 云异常，记录一查异常信息到数据库
						HDBLoggerUtil.cloudBusinessError(_context, errorSql,
								errorMsgForMonitor, excetion, isNetException);
					} else {
						// 端异常、获取堆栈、SQL信息，返回给云
						errorStackTrace = HDBLoggerUtil
								.ErrorStackTrace(excetion);
						errorLevel = "business";
					}
				}
			} else {
				// Edit add start fengze KYEEAPPC-2674
				// 针对“系统级异常”，直接存储至云数据库中，并提供后台的“异常管理维护”功能 2015-7-17 15:20:00
				if (!"t".equals(loc)) {
					// Edit start fengze KYEEAPPTEST-2913 修改userSource判断条件
					// 2015-8-21 09:06:39
					if ("1".equals(isWebS)) {
						errorStackTrace = HDBLoggerUtil
								.ErrorStackTrace(excetion);
					}
					// 云异常，记录一查异常信息到数据库
					HDBLoggerUtil.cloudSystemError(_context, errorSql,
							errorMsgForMonitor, excetion, isNetException);
				} else {
					// 端异常、获取堆栈、SQL信息，返回给云
					errorStackTrace = HDBLoggerUtil.ErrorStackTrace(excetion);
				}
				// 针对“系统级异常”，直接存储至云数据库中，并提供后台的“异常管理维护”功能 2015-7-17 15:20:00
				errorLevel = "system";
				HLogger.error(excetion);
			}
		    /**
			 * 任务：*描述：ex.getMessage()修改为调用ex.getMessage() 人员：施建龙
			 * *时间：2015年5月17日下午3:55:45
			 **/
			if (ex != null && ((BaseBllException) ex).getViewMessage() != null) {
				msg = ((BaseBllException) ex).getViewMessage()
						.replaceAll("'", "").replace("\n", "");
			}

			// edit start 添加错误码异常输出 houhy 2014年12月19日 17:42:26
			String errorCode = ((BaseBllException) ex).getErrorCode();

			JsonAlertType alertType = ((BaseBllException) ex).getAlertType();
			if (!"t".equals(loc)) {
				// Edit start fengze KYEEAPPTEST-2913 修改userSource判断条件 2015-8-21
				// 09:06:39
				// 过滤平台信息
				if ("1".equals(isWebS)) {
					// Edit end fengze KYEEAPPTEST-2913 修改userSource判断条件
					// 2015-8-21 09:06:39
					// 将异常信息返回给webservice
					serverVersion = "";// VersionSystemParams.getParamaValue("WEB_BUILDER_VERSION_APP");
					requestParameters = HDBLoggerUtil
							.getRequestMapString(_context);
					serverIP = HandlerUtil.getServerIp(_context.getRequest());
					errorSql = HDBLoggerUtil.covertString(errorSql);
					errorStackTrace = HDBLoggerUtil
							.covertString(errorStackTrace);
					requestParameters = HDBLoggerUtil
							.covertString(requestParameters);
					if (DotNetToJavaStringHelper.isNullOrEmpty(errorSql)) {
						errorSql = "errorSql";
					}
					if (DotNetToJavaStringHelper.isNullOrEmpty(errorStackTrace)) {
						errorStackTrace = "errorStackTrace";
					}
					if (DotNetToJavaStringHelper.isNullOrEmpty(serverVersion)) {
						serverVersion = "serverVersion";
					}
					if (DotNetToJavaStringHelper
							.isNullOrEmpty(requestParameters)) {
						requestParameters = "requestParameters";
					}
					if (DotNetToJavaStringHelper.isNullOrEmpty(serverIP)) {
						serverIP = "serverIP";
					}
					// 云，返回异常信息给webservice
					// _context.write(JsonUtil.exceptionJson(msg,errorCode,errorSql,errorLevel,errorStackTrace,serverVersion,requestParameters,serverIP,alertType));
					// edit end 添加错误码异常输出 houhy 2014年12月19日 17:42:26
				} else {
					// 云，返回异常给APK或者其他
					if ("0000500".equals(errorCode)) {
						/**
						 * 任务：*描述：临时处理措施，隐藏APK前端的“系统内部错误” 人员：施建龙
						 * *时间：2015年6月12日上午10:08:34
						 **/
						// Edit start fengze KYEEAPPTEST-2873 添加开关控制AlertType
						// 2015-8-11 19:20:26
						String DEFAULT_ERROR_CODE_ALERTYPE = SystemParams
								.getParamaValue("DEFAULT_ERROR_CODE_ALERTYPE");
						if (DotNetToJavaStringHelper
								.isNullOrEmpty(DEFAULT_ERROR_CODE_ALERTYPE)
								|| "Y".equals(DEFAULT_ERROR_CODE_ALERTYPE)) {
							alertType = JsonAlertType.NO;
						}
						if ("system".equals(errorLevel)) {
							msg = "系统繁忙！";
						}
						// Edit end fengze KYEEAPPTEST-2873 添加开关控制AlertType
						// 2015-8-11 19:20:26
						// _context.write(JsonUtil.exceptionJson(msg,alertType));
					}
					// edit start KYEEAPPTEST-1389 对错误码000300的异常处理 houhy
					// 2015年3月6日 10:59:18
					else if ("0000300".equals(errorCode)) {
						// _context.write(JsonUtil.exceptionJson(true,msg,errorCode,alertType));
						// edit end KYEEAPPTEST-1389 对错误码000300的异常处理 houhy
						// 2015年3月6日 10:59:18
					} else {
						// _context.write(JsonUtil.exceptionJson(msg,errorCode,alertType));
						// edit end 添加错误码异常输出 houhy 2014年12月19日 17:42:26
					}
				}
			} else {
				// 端，返回异常信息给云
				// serverVersion =
				// VersionSystemParams.getParamaValue("WEB_BUILDER_VERSION_APP");
				requestParameters = HDBLoggerUtil.getRequestMapString(_context);
				serverIP = HandlerUtil.getServerIp(_context.getRequest());
				errorSql = HDBLoggerUtil.covertString(errorSql);
				errorStackTrace = HDBLoggerUtil.covertString(errorStackTrace);
				requestParameters = HDBLoggerUtil
						.covertString(requestParameters);
				if (DotNetToJavaStringHelper.isNullOrEmpty(errorSql)) {
					errorSql = "errorSql";
				}
				if (DotNetToJavaStringHelper.isNullOrEmpty(errorStackTrace)) {
					errorStackTrace = "errorStackTrace";
				}
				if (DotNetToJavaStringHelper.isNullOrEmpty(requestParameters)) {
					requestParameters = "requestParameters";
				}
				if (DotNetToJavaStringHelper.isNullOrEmpty(serverIP)) {
					serverIP = "serverIP";
				}
				// edit end 添加错误码异常输出 houhy 2014年12月19日 17:42:26
			}
		} else {
			// 系统级别异常处理
			HLogger.error(ex);
		}
		// edit end KYEEAPP-1120 统一在框架的最外层处理异常 houhy 2014年12月11日 23:20:29
		return terminal_monitor_process_id;
	}

}
