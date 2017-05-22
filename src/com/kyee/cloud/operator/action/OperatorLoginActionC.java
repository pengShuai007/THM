package com.kyee.cloud.operator.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import model.base.ext.SYS_USER_EXT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.MD5Pass;
import APP.Comm.Util.OutJsonType;
import APP.Comm.View.BaseTreeNode;

import com.kyee.cloud.operator.bll.OperatorLoginBllC;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年2月20日 下午8:58:31
 * 描述:管理员登录
 * </pre>
 */
public class OperatorLoginActionC {

	private OperatorLoginBllC loginBll = new OperatorLoginBllC();

	/**
	 * 
	 * <pre>
	 * 作者:刘健
	 * 日期:2014年2月20日 下午9:07:50
	 * 描述:管理员登录验证
	 * </pre>
	 */
	public void login(HttpContext context) throws BaseBllException {
		if ("admin".equals(context.getRequest().getParameter("operatorName")
				.toLowerCase())) {
			String password = MD5Pass.EncryptString(context.getRequest()
					.getParameter("passWord"));
			boolean isSuccess = checkAdmin(context, password);
			if (isSuccess) {
				SYS_USER_EXT sysadmin = new SYS_USER_EXT();
				sysadmin.setUSER_CODE("admin");
				sysadmin.setUSER_NAME("admin");
				sysadmin.setPASS_WORD(password);
				HttpSession session = context.getRequest().getSession(true);
				context.getRequest()
						.getSession(true)
						.setAttribute(
								"fr_username",
								context.getRequest()
										.getParameter("operatorName")
										.toUpperCase());
				session.setAttribute("appuser", sysadmin);
			}
			context.write((boolean) isSuccess ? JsonUtil
					.successMessageJsonString("登录成功！",
							JsonUtil.objectToJsonString(isSuccess)) : JsonUtil
					.errorMessageJsonString("登录失败！"));
		} else {
			context.getHttpHandler().SetParamsToBLL(loginBll, context);
			loginBll.getBaseDB().NeedOpen(true);
			loginBll.getBaseDB().NeedTransaction(false);
			Object result = context.getHttpHandler().BLLContainer.DoProcess(
					loginBll, "queryAdmin");
			// 将登录用户名名存放到session中
			HttpSession session = context.getRequest().getSession(true);
			session.setAttribute("appuser", result);
			context.getRequest()
					.getSession(true)
					.setAttribute(
							"fr_username",
							context.getRequest().getParameter("operatorName")
									.toUpperCase());
			System.out.println(context.getRequest()
					.getParameter("operatorName").toUpperCase());
			System.out.println(context.getSessionAttribute("fr_username"));
			context.write(result != null ? JsonUtil.successMessageJsonString(
					"登录成功！", JsonUtil.objectToJsonString(result)) : JsonUtil
					.errorMessageJsonString("登录失败！"));
		}
	}

	/**
	 * 
	 * <pre>
	 * 作者:秦晓东
	 * 日期:2014年2月27日 下午3:53:45
	 * 描述:获取系统菜单
	 * </pre>
	 */
	public void queryMenus(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(false);
		// 从session中拿到当前登录用户
		SYS_USER_EXT appuser = (SYS_USER_EXT) context
				.getSessionAttribute("appuser");
		// edit huangnenghong start 2014/12/31 KYEEAPPTEST-943
		// 添加对当前用户的判断，判断是否session过期
		if (appuser == null) {
			context.write(JsonUtil.errorMessageJsonString("timeOut"));
		} else {
			loginBll.getAttrParams().put("LoginName", appuser.getUSER_CODE());
			@SuppressWarnings("unchecked")
			List<BaseTreeNode> result = (List<BaseTreeNode>) context
					.getHttpHandler().BLLContainer.DoProcess(loginBll,
					"queryMenus");
			context.write(result != null ? JsonUtil.listBeanToJsonString(
					result.size(), true, "查询成功", result, OutJsonType.Tree)
					: JsonUtil.errorMessageJsonString("查询失败！"));
		}
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年4月23日下午1:41:42
	 * 描述：退出系统，销毁session，返回登陆
	 * </pre>
	 */
	public void loginOut(HttpContext context) throws BaseBllException {
		context.getSession().invalidate();
		context.write("1");
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年4月23日下午7:57:26
	 * 描述：修改后台登录密码
	 * </pre>
	 */
	public void updatepwd(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				loginBll, "updatepwd");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("修改信息成功!")
				: JsonUtil.errorMessageJsonString("修改信息失败！"));
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年4月24日下午1:24:51
	 * 描述：初始化查询用户及其角色
	 * </pre>
	 */
	public void queryoperator(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(false);
		SYS_USER_EXT appuser = (SYS_USER_EXT) context
				.getSessionAttribute("appuser");
		loginBll.getAttrParams().put("USER_CODE", appuser.getUSER_CODE());
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(loginBll, "queryoperator");
		context.write(result != null ? JsonUtil.dataTableToJsonString(
				Integer.parseInt(result.size() + ""), true, "初始化查询成功",
				result.DataTable, OutJsonType.Grid) : JsonUtil
				.errorMessageJsonString("初始化查询失败"));

	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午8:36:03
	 * 描述：查询用户
	 * </pre>
	 */
	public void queryOperatorByname(HttpContext context)
			throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(false);
		DataTable result = (DataTable) context.getHttpHandler().BLLContainer
				.DoProcess(loginBll, "queryOperatorByname");
		context.write(result != null ? JsonUtil.dataTableToJsonString(result
				.getRows().size(), true, "查询成功", result, OutJsonType.Grid)
				: JsonUtil.errorMessageJsonString("查询失败"));
	}

	/**
	 * 任务号：KYEEAPPMAINTENANCE-1047 描述：新增用户是校验这个用户user_code是否存在 作者：李添
	 * 时间：2016年10月26日19:23:05
	 * 
	 * @param context
	 * @throws BaseBllException
	 */
	public void checkUserCode(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(false);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				loginBll, "checkUserCode");
		context.write(result != -1 ? JsonUtil.successMessageJsonString("查询成功",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("查询失败"));
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午4:43:38
	 * 描述：新增用户
	 * </pre>
	 */
	public void saveoperator(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				loginBll, "addoperator");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("新增成功",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("新增失败！"));
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午5:23:13
	 * 描述：修改用户
	 * </pre>
	 */

	public void changeOperator(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				loginBll, "changeOperator");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("修改成功",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("修改失败！"));
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午5:24:53
	 * 描述：删除用户
	 * </pre>
	 */
	public void deleteOperator(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				loginBll, "deleteOperator");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("删除成功",
				JsonUtil.objectToJsonString(result)) : JsonUtil
				.errorMessageJsonString("删除失败！"));
	}

	/**
	 * 任务号：KYEEAPPMAINTENANCE-45 描述：获取当前用户名 作者：liuxingping
	 * 时间：2015年1月16日下午6:57:48
	 * 
	 * @param context
	 * @throws BaseBllException
	 */
	public void getCurrentUser(HttpContext context) throws BaseBllException {
		HLogger.info("OperatorLoginActionC Function getCurrentUser start");

		SYS_USER_EXT appuser = (SYS_USER_EXT) context
				.getSessionAttribute("appuser");
		HLogger.info("OperatorLoginActionC Function getCurrentUser end");
		context.write(appuser != null ? JsonUtil.listBeanToJsonString(1, true,
				"查询成功!", appuser, OutJsonType.Grid) : JsonUtil
				.errorMessageJsonString("timeout"));
	}

	/**
	 * 任务号： 描述： 作者：liuxingping 时间：2015年1月19日上午11:12:34
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public boolean checkAdmin(HttpContext context, String password)
			throws BaseBllException {
		HLogger.info("OperatorLoginActionC Function checkAdmin start");
		String Ocspath = context.getServletContext().getRealPath("/");
		Ocspath = Ocspath.endsWith(File.separator) ? Ocspath : Ocspath
				+ File.separator;
		String path = Ocspath + "WEB-INF" + File.separator + "classes"
				+ File.separator + "application.properties";
		if (new File(path).exists()) {
			try {
				String passWord = GetValueByKey(path, "admin");
				if (password.equals(passWord)) {
					HLogger.info("OperatorLoginActionC Function checkAdmin end");
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		HLogger.info("OperatorLoginActionC Function getCurrentUser end");
		return false;
	}

	public String GetValueByKey(String path, String key) throws Exception {
		HLogger.info("OperatorLoginActionC Function GetValueByKey start");
		Properties propertis = new Properties();
		InputStream input = new BufferedInputStream(new FileInputStream(
				new File(path)));
		propertis.load(input);
		String result = propertis.getProperty(key);
		input.close();
		HLogger.info("OperatorLoginActionC Function GetValueByKey end");
		return result;
	}

	/**
	 * <pre>
	 * 任务:KYEEAPPMAINTENANCE-50
	 * 描述:第二次握手校验用户登录
	 * 作者:罗京
	 * 日期:2015年2月2日 下午8:41:02
	 * @param context
	 * @throws BaseBllException
	 * </pre>
	 */
	public void checkUserLogin(HttpContext context) throws BaseBllException {
		HLogger.info("OperatorLoginActionC Function checkUserLogin Start!");
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(true);
		SYS_USER_EXT result = (SYS_USER_EXT) context.getHttpHandler().BLLContainer
				.DoProcess(loginBll, "checkUserLogin");
		// 将登录用户名名存放到session中（包括fireport报表）
		HttpSession session = context.getRequest().getSession(true);
		session.setAttribute("appuser", result);
		if (result != null && !result.getUSER_CODE().equals("")) {
			session.setAttribute("fr_username", result.getUSER_CODE());
		}
		HLogger.info("OperatorLoginActionC Function checkUserLogin End!");
		context.write(result != null ? JsonUtil.successMessageJsonString(
				"登录成功", JsonUtil.objectToJsonString(result.getUSER_CODE()))
				: JsonUtil.errorMessageJsonString("请检查用户名密码！"));
	}

	/**
	 * 任务号： 描述： 作者：liuxingping 时间；2015年3月23日下午5:03:22
	 * 
	 * @param context
	 * @throws BaseBllException
	 */
	public void QueryPost(HttpContext context) throws BaseBllException {
		HLogger.info("OperatorLoginActionC Function QueryPost Start!");
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true); // 打开appdb数据库
		loginBll.getBaseDB().NeedTransaction(false); // 不打开事务
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(loginBll, "QueryPost");
		HLogger.info("OperatorLoginActionC Function QueryPost End!");
		context.write(result != null ? JsonUtil.dataTableToJsonString(
				result.DataTable.getRows().size(), true, "初始化成功",
				result.DataTable, OutJsonType.Combox) : JsonUtil
				.errorMessageJsonString("初始化失败"));
	}

	@SuppressWarnings("unchecked")
	public void QueryOperation(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(false);
		List<BaseTreeNode> result = (List<BaseTreeNode>) context
				.getHttpHandler().BLLContainer.DoProcess(loginBll,
				"QueryOperation");
		context.write(result != null ? JsonUtil.listBeanToJsonString(
				result.size(), true, "查询成功", result, OutJsonType.Tree)
				: JsonUtil.errorMessageJsonString("查询失败！"));
	}

	@SuppressWarnings("unchecked")
	public void QueryUserPost(HttpContext context) throws BaseBllException {
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(false);
		List<BaseTreeNode> result = (List<BaseTreeNode>) context
				.getHttpHandler().BLLContainer.DoProcess(loginBll,
				"QueryUserPost");
		context.write(result != null ? JsonUtil.listBeanToJsonString(
				result.size(), true, "查询成功", result, OutJsonType.Tree)
				: JsonUtil.errorMessageJsonString("查询失败！"));
	}

	public void ResetPassWord(HttpContext context) throws BaseBllException {
		HLogger.info("OperatorLoginActionC Function ResetPassWord Start");
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(true);
		int result = (int) context.getHttpHandler().BLLContainer.DoProcess(
				loginBll, "ResetPassWord");
		HLogger.info("OperatorLoginActionC Function ResetPassWord End");
		context.write(result > 0 ? JsonUtil.successMessageJsonString("重置成功")
				: JsonUtil.errorMessageJsonString("重置失败！"));
	}

	public void queryMenusByWorkNumber(HttpContext context)
			throws BaseBllException {

		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(false);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> result = (List<Map<String, Object>>) context
				.getHttpHandler().BLLContainer.DoProcess(loginBll,
				"queryMenusByWorkNumber");
		String jsonString = "{\"menuList\":"
				+ JsonUtil.objectToJsonString(result) + "}";
		context.write(jsonString);
	}

	/**
	 * <pre>
	 * CopyRight(c) 2015
	 * 创建人：杨博申
	 * 日期：2015年7月14日15:00:27
	 * 描述：获取登录用户信息
	 * </pre>
	 */
	public void queryUser(HttpContext context) throws BaseBllException {
		HLogger.info("SystemLogMonitorActionC Function sysLogMonitor begin!");
		HLogger.info("说明:初始化短信配置信息");
		context.getHttpHandler().SetParamsToBLL(loginBll, context);
		loginBll.getBaseDB().NeedOpen(true);
		loginBll.getBaseDB().NeedTransaction(false);
		AppDataTable result = (AppDataTable) context.getHttpHandler().BLLContainer
				.DoProcess(loginBll, "queryUser");
		context.write(result != null ? JsonUtil.dataTableToJsonString(
				Integer.parseInt(result.size() + ""), true, "初始化查询成功",
				result.DataTable, OutJsonType.Grid) : JsonUtil
				.errorMessageJsonString("初始化查询失败"));
		HLogger.info("SystemLogMonitorActionC Function sysLogMonitor end!");
	}
}