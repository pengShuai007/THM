package com.kyee.cloud.operator.bll;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import model.base.ext.C_SYSTEM_MENU_EXT;
import model.base.ext.SYS_DEPT_POST_FRAME_EXT;
import model.base.ext.SYS_MENU_EXT;
import model.base.ext.SYS_USER_EXT;
import net.hydromatic.linq4j.Linq4j;
import net.hydromatic.linq4j.function.Predicate1;
import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;
import APP.Comm.Util.MD5Pass;
import APP.Comm.Util.RSAUtil;
import APP.Comm.View.BaseTreeNode;
import APP.Model.BaseEntity;

//import com.kyee.auth.util.CommonAuthUtil;
//import com.kyee.auth.util.UserInfoVo;
//import com.kyee.cloud.appoint.bll.AppointRegistParm;
import com.kyee.cloud.operator.dal.OperatorLoginDalC;
import com.kyee.common.auth.CommonAuthUtil;

public class OperatorLoginBllC extends BaseBLL {

	private OperatorLoginDalC loginDal = new OperatorLoginDalC();

	/**
	 * <pre>
	 * 作者:刘健
	 * 日期:2014年2月20日 下午9:20:58 
	 * 描述:查询管理员登陆名及密码
	 * </pre>
	 */
	public SYS_USER_EXT queryAdmin() throws BaseBllException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("operatorName", getParameterValue("operatorName"));
		map.put("passWord",
				MD5Pass.EncryptString(getParameterValue("passWord")));

		// List<BaseEntity> list = loginDal.queryAdmin(getAppDB(), map);
		List<BaseEntity> list = loginDal.queryAdmin(getBaseDB(), map);
		if (list != null && list.size() > 0) {
			return (SYS_USER_EXT) list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * <pre>
	 * 作者:秦晓东
	 * 日期:2014年2月27日 下午3:59:02
	 * 描述:查询系统菜单
	 * </pre>
	 */
	public List<BaseTreeNode> queryMenus() throws BaseBllException {
		String loginName = super.getAttrParameterValue("LoginName");
		List<BaseEntity> list = null;
		if ("admin".equals(loginName)) {
			list = loginDal.queryAll(super.getBaseDB());
		} else {
			list = loginDal.queryByName(super.getBaseDB(), loginName);
		}

		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		if (list != null) {
			List<SYS_MENU_EXT> entities = Linq4j
					.asEnumerable(
							Arrays.asList(list.toArray(new SYS_MENU_EXT[] {})))
					.where(new Predicate1<SYS_MENU_EXT>() {
						public boolean apply(SYS_MENU_EXT entity) {
							return entity.getC_S_MENU_ID() == 0;
						}
					}).toList();
			for (SYS_MENU_EXT sys_menu : entities) {
				BaseTreeNode node = new BaseTreeNode();
				node.setid(sys_menu.getMENU_ID() + "");
				node.settext(sys_menu.getMENU_NAME());
				List<BaseTreeNode> ns = converChildren(list,
						sys_menu.getMENU_ID());
				if (ns.size() > 0) {
					node.setchildren(ns.toArray(new BaseTreeNode[] {}));
					node.setstate("open");
				} else {
					node.setstate("close");
				}
				node.setattributes(sys_menu.getMENU_URL());
				nodes.add(node);
			}
		}
		return nodes;
	}

	/**
	 * 
	 * <pre>
	 * 作者:秦晓东
	 * 日期:2014年2月27日 下午4:36:10
	 * 描述:组装子节点
	 * </pre>
	 */
	private List<BaseTreeNode> converChildren(List<BaseEntity> entities,
			final int parentId) throws BaseBllException {
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		List<BaseEntity> ens = Linq4j.asEnumerable(entities)
				.where(new Predicate1<BaseEntity>() {
					public boolean apply(BaseEntity entity) {
						return ((SYS_MENU_EXT) entity).getC_S_MENU_ID() == parentId;
					}
				}).toList();
		for (BaseEntity be : ens) {
			BaseTreeNode node = new BaseTreeNode();
			SYS_MENU_EXT cs = (SYS_MENU_EXT) be;
			node.setid(cs.getMENU_ID() + "");
			node.settext(cs.getMENU_NAME());
			List<BaseTreeNode> ns = converChildren(entities, cs.getMENU_ID());
			if (ns.size() > 0) {
				node.setchildren(ns.toArray(new BaseTreeNode[] {}));
				node.setstate("closed");
			} else {
				node.setstate("close");
			}
			node.setattributes(cs.getMENU_URL());
			nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年4月24日下午1:27:29
	 * 描述：初始化查询用户及角色
	 * </pre>
	 */
	public AppDataTable queryoperator() throws BaseBllException {
		return loginDal.queryoperator(super.getBaseDB(),
				super.getGridRequestParameters());
	}

	/**
	 * 任务号：KYEEAPPMAINTENANCE-1047 描述：添加用户前校验是否这个user_code存在 作者：李添
	 * 时间：2016年10月26日19:15:23
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public int checkUserCode() throws BaseBllException {
		String uerCode = getParameterValue("USER_CODE");
		AppDataTable countUser = loginDal.checkUserCode(super.getBaseDB(),
				uerCode);
		int result = countUser.getDataTable().getRows().get(0)
				.getIntColumn("userCount");
		return result;
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午4:13:55
	 * 描述：新增用户
	 * </pre>
	 */
	public int addoperator() throws BaseBllException {
		String operator = getParameterValue("postdata");
		if (DotNetToJavaStringHelper.isNullOrEmpty(operator)) {
			HLogger.error("传入空的operator");
			return 0;
		}
		SYS_USER_EXT oper = (SYS_USER_EXT) JsonUtil.jsonStringToObject(
				operator, SYS_USER_EXT.class);
		oper.setPASS_WORD(MD5Pass.EncryptString(oper.getPASS_WORD()));
		if ("admin".equals(oper.getUSER_CODE().toLowerCase())) {
			HLogger.error("登陆名重复");
			return 0;
		}
		return loginDal.saveoperator(super.getBaseDB(), oper);
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午5:25:48
	 * 描述：修改用户
	 * </pre>
	 */
	public int changeOperator() throws BaseBllException {
		String operator = getParameterValue("postdata");
		if (DotNetToJavaStringHelper.isNullOrEmpty(operator)) {
			HLogger.error("传入空的operator");
			return 0;
		}
		SYS_USER_EXT oper = (SYS_USER_EXT) JsonUtil.jsonStringToObject(
				operator, SYS_USER_EXT.class);
		if ("admin".equals(oper.getUSER_CODE().toLowerCase())) {
			HLogger.error("登陆名重复");
			return 0;
		}
		return loginDal.changeOperator(super.getBaseDB(), oper);
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午5:26:46
	 * 描述：删除用户
	 * </pre>
	 */
	public int deleteOperator() throws BaseBllException {
		String operId = getParameterValue("USER_ID");
		if (DotNetToJavaStringHelper.isNullOrEmpty(operId)) {
			HLogger.error("传入空的operId");
			return 0;
		}
		return loginDal.deleteOperator(super.getBaseDB(),
				Integer.parseInt(operId));
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午8:31:15
	 * 描述：查询用户
	 * </pre>
	 */
	public DataTable queryOperatorByname() throws BaseBllException {
		// Edit Start liuxingping
		// String operName = getParameterValue("OPERATOR_NAME");
		String userCode = getParameterValue("USER_CODE");
		String userName = getParameterValue("USER_NAME");
		// String userRole = getParameterValue("USER_NAME");
		String postId = getParameterValue("POST_ID");
		return loginDal.queryOperatorByname(super.getBaseDB(), postId,
				userCode, userName, super.getGridRequestParameters());
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月28日下午7:22:16
	 * 描述：修改后台登录密码
	 * </pre>
	 */
	public int updatepwd() throws BaseBllException {
		String postdata = getParameterValue("postdata");
		int result = 0;
		if (DotNetToJavaStringHelper.isNullOrEmpty(postdata)) {
			HLogger.error("传入空的postdata");
			return 0;
		}
		// Edit start KYEEAPPMAINTENANCE-45 liuxingping
		SYS_USER_EXT user = (SYS_USER_EXT) JsonUtil.jsonStringToObject(
				postdata, SYS_USER_EXT.class);
		SYS_USER_EXT appUser = (SYS_USER_EXT) context
				.getSessionAttribute("appuser");
		String userCode = appUser.getUSER_CODE();
		if (!user.getPASS_WORD().equals("")
				&& !user.getOLDPASS_WORD().equals("")) {
			user.setPASS_WORD(MD5Pass.EncryptString(user.getPASS_WORD()));
			user.setOLDPASS_WORD(MD5Pass.EncryptString(user.getOLDPASS_WORD()));
		}
		if ("admin".equals(user.getUSER_CODE())) {
			boolean isSuccess = updateAdminPass(user);
			if (isSuccess) {
				return 1;
			} else {
				return 0;
			}
		} else {
			result = loginDal.updatepwd(super.getBaseDB(), user, userCode);
		}
		return result;
		// Edit end KYEEAPPMAINTENANCE-45 liuxingping
	}

	public boolean updateAdminPass(SYS_USER_EXT user) throws BaseBllException {
		HLogger.info("OperatorLoginActionC Function checkAdmin start");
		String Coppath = context.getServletContext().getRealPath("/");
		Coppath = Coppath.endsWith(File.separator) ? Coppath : Coppath
				+ File.separator;
		String path = Coppath + "WEB-INF" + File.separator + "classes"
				+ File.separator + "application.properties";
		if (new File(path).exists()) {
			Properties prop = new Properties();
			try {
				InputStream fis = new BufferedInputStream(new FileInputStream(
						new File(path)));
				;
				prop.load(fis);
				fis.close();
				String result = prop.getProperty("admin");
				if (result.equals(user.getOLDPASS_WORD())) {
					OutputStream fos = new FileOutputStream(path);
					prop.setProperty("admin", user.getPASS_WORD());
					prop.store(fos, "Update '" + "admin" + "' value");
					fos.close();
				} else {
					return false;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		HLogger.info("OperatorLoginActionC Function getCurrentUser end");
		return true;
	}

	/**
	 * 描述：校验用户登录 作者：李智博
	 */
	public SYS_USER_EXT checkUserLogin() throws BaseBllException, Exception {
		HLogger.info("OperatorLoginBllC Function checkUserLogin Start!");
		SYS_USER_EXT result = null;

		// 用户名和密码解析
		String userStr = RSAUtil.decrypt(getParameterValue("Data"));
		// 根据_$$@@_分割字符串
		String[] userinfoArray = userStr.split("\\_\\$\\$\\@\\@\\_");
		if (userinfoArray.length != 2) {
			// 用户名密码解析错误的情况
			return result;
		}
		// 获取用户名，密码（MD5加密）
		String uname = userinfoArray[0];
		// 对密码进行MD5加密
		String upass = MD5Pass.EncryptString(userinfoArray[1]);

		if (uname.equalsIgnoreCase("admin")) {
			// admin用户为内置用户（即在配置文件中），不在数据库保存
			HttpContext ctx = super.getContext();
			if (checkAdmin(ctx, upass)) {
				result = new SYS_USER_EXT();
				result.setUSER_CODE("admin");
				result.setUSER_NAME("管理员");
				result.setPASS_WORD(upass);
			}
		} else {
			SYS_USER_EXT user = loginDal
					.queryByOperatorName(getBaseDB(), uname);
			if (user == null) {// 拼音不存在
				HLogger.error("#########TOT##########:拼音用户名不存在" + uname);
				return null;
			} else {
				String ucode = uname;
				result = loginDal.checkUserLogin(getBaseDB(), ucode, upass);// 查询用户名、密码是否正确
			}
		}
		HLogger.info("OperatorLoginBllC Function checkUserLogin End!");
		return result;
	}

	/**
	 * <pre>
	 * 任务:KYEEAPPMAINTENANCE-50
	 * 描述:检查admin
	 * 作者:罗京
	 * 日期:2015年2月10日 上午10:00:39
	 * @param context
	 * @param password
	 * @return
	 * @throws BaseBllException
	 * </pre>
	 */
	public boolean checkAdmin(HttpContext context, String password)
			throws BaseBllException {
		HLogger.info("OperatorLoginBllC Function checkAdmin start");
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
		HLogger.info("OperatorLoginBllC Function getCurrentUser end");
		return false;
	}

	/**
	 * <pre>
	 * 任务:KYEEAPPMAINTENANCE-50
	 * 描述:获取admin密码
	 * 作者:罗京
	 * 日期:2015年2月10日 上午10:01:35
	 * @param path
	 * @param key
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	public String GetValueByKey(String path, String key) throws Exception {
		HLogger.info("OperatorLoginBllC Function GetValueByKey start");
		Properties propertis = new Properties();
		InputStream input = new BufferedInputStream(new FileInputStream(
				new File(path)));
		propertis.load(input);
		String result = propertis.getProperty(key);
		input.close();
		HLogger.info("OperatorLoginBllC Function GetValueByKey end");
		return result;
	}

	/**
	 * 任务号： 描述： 作者：liuxingping 时间；2015年3月23日下午5:17:18
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public List<BaseTreeNode> QueryOperation() throws BaseBllException {
		HLogger.info("OperatorLoginBllC Function QueryOperation Start!");
		String userCode = super.getParameterValue("UserCode");
		List<BaseEntity> list = loginDal.queryByName(super.getBaseDB(),
				userCode);
		List<BaseTreeNode> resultnodes = new ArrayList<BaseTreeNode>();
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		BaseTreeNode root = new BaseTreeNode();
		root.setid("-1");
		root.settext("系统菜单(可操作菜单)");
		if (list != null) {
			List<SYS_MENU_EXT> entities = Linq4j
					.asEnumerable(
							Arrays.asList(list.toArray(new SYS_MENU_EXT[] {})))
					.where(new Predicate1<SYS_MENU_EXT>() {
						public boolean apply(SYS_MENU_EXT entity) {
							return entity.getC_S_MENU_ID() == 0;
						}
					}).toList();
			for (SYS_MENU_EXT sys_menu : entities) {
				BaseTreeNode node = new BaseTreeNode();
				node.setid(sys_menu.getMENU_ID() + "");
				node.settext(sys_menu.getMENU_NAME());
				List<BaseTreeNode> ns = converChildren(list,
						sys_menu.getMENU_ID());
				if (ns.size() > 0) {
					node.setchildren(ns.toArray(new BaseTreeNode[] {}));
					node.setstate("closed");
				} else {
					node.setstate("close");
				}
				node.setattributes(sys_menu.getMENU_URL());
				nodes.add(node);
			}
			root.setchildren(nodes.toArray(new BaseTreeNode[] {}));
			root.setstate("open");
		}
		resultnodes.add(root);
		HLogger.info("OperatorLoginBllC Function QueryOperation End!");
		return resultnodes;
	}

	public List<BaseTreeNode> QueryUserPost() throws BaseBllException {
		HLogger.info("OperatorLoginBllC Fuction QueryUserPost Start!");
		String userCode = super.getParameterValue("UserCode");
		List<BaseEntity> list = loginDal.GetUserPost(super.getBaseDB());
		List<BaseTreeNode> resultnodes = new ArrayList<BaseTreeNode>(); // 构造的菜单树
		if (list != null) {
			for (BaseEntity be : list) {
				SYS_DEPT_POST_FRAME_EXT sysDeptInfo = (SYS_DEPT_POST_FRAME_EXT) be;
				BaseTreeNode node = new BaseTreeNode();
				node.setid(sysDeptInfo.getDEPT_POST_ID() + "");
				node.settext(sysDeptInfo.getDEPT_POST_NAME());
				List<BaseTreeNode> nds = converPostChildren(super.getBaseDB(),
						sysDeptInfo.getDEPT_CODE(), userCode);
				if (nds.size() > 0) {
					node.setchildren(nds.toArray(new BaseTreeNode[] {}));
					node.setstate("open");
				} else {
					node.setstate("close");
				}
				node.setattributes(sysDeptInfo.getDEPT_CODE());
				resultnodes.add(node);
			}
		}
		HLogger.info("DeptPostManagerBll Fuction QueryUserPost End!");
		return resultnodes;
	}

	private List<BaseTreeNode> converPostChildren(IDataBase baseDB,
			String deptCode, String userCode) throws BaseBllException {
		List<BaseEntity> ens = loginDal.GetUserPost(super.getBaseDB(),
				deptCode, userCode);
		List<BaseTreeNode> nodes = new ArrayList<BaseTreeNode>();
		for (BaseEntity be : ens) {
			BaseTreeNode node = new BaseTreeNode();
			SYS_DEPT_POST_FRAME_EXT sysDeptInfo = (SYS_DEPT_POST_FRAME_EXT) be;
			node.setid(sysDeptInfo.getDEPT_POST_ID() + "");
			node.settext(sysDeptInfo.getDEPT_POST_NAME());
			List<BaseTreeNode> ns = converPostChildren(baseDB,
					sysDeptInfo.getDEPT_CODE(), userCode);
			if (ns.size() > 0) {
				node.setchildren(ns.toArray(new BaseTreeNode[] {}));
				node.setstate("open");
			} else {
				node.setstate("close");
			}
			node.setattributes(sysDeptInfo.getDEPT_CODE());
			nodes.add(node);
		}
		return nodes;
	}

	public AppDataTable QueryPost() throws BaseBllException {
		HLogger.info("OperatorLoginBllC Fuction QueryPost Start!");
		AppDataTable result = loginDal.QueryPost(super.getBaseDB());
		HLogger.info("OperatorLoginBllC Fuction QueryPost End!");
		return result;
	}

	public int ResetPassWord() throws BaseBllException {
		HLogger.info("OperatorLoginBllC Function ResetPassWord start");
		String defualtPass = MD5Pass.EncryptString("a1b2c3//");
		String userCode = super.getParameterValue("userCode");
		int result = loginDal.ResetPassWord(super.getBaseDB(), userCode,
				defualtPass);
		// 发送邮件给重置密码的人和管理员
		DataTable email_result = loginDal.QueryUserEmail(super.getBaseDB(),
				userCode);
		// EmailMsgNotifyImpl msg = new EmailMsgNotifyImpl();
		String adminEmail = "lizhibo@quyiyuan.com";
		String selfEmail = (String) email_result.getRow().get(0)
				.getColumn("EMail");
		String subject = "COP-用户管理-用户重置密码";
		String content = "重置密码人的userCode：" + userCode + "\n新密码：" + "a1b2c3//";
		// msg.sendMail(adminEmail, null, null,subject, content, null,null);
		// msg.sendMail(selfEmail, null, null,subject, content, null,null);
		HLogger.info("OperatorLoginBllC Function ResetPassWord end");
		return result;
	}

	public List<Map<String, Object>> queryMenusByWorkNumber()
			throws BaseBllException {

		String workNumber = context.getRequest().getParameter("workNumber");
		String token = context.getRequest().getParameter("token");

		if (CommonAuthUtil.getUserInfo(workNumber, token) != null) {

			List<BaseEntity> list = loginDal.queryMenusByWorkNumber(
					getBaseDB(), workNumber);
			if (list != null) {
				List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
				Iterator<BaseEntity> it = list.iterator();
				while (it.hasNext()) {
					C_SYSTEM_MENU_EXT tmpMenu = (C_SYSTEM_MENU_EXT) it.next();
					Map<String, Object> menuMap = new HashMap<String, Object>();
					menuMap.put("menuId", tmpMenu.getMENU_ID());
					menuMap.put("menuText", tmpMenu.getMENU_NAME());
					menuMap.put("menuUrl", tmpMenu.getMENU_URL());
					menuList.add(menuMap);
				}

				if (menuList != null) {
					return menuList;
				}
			} else {
				return null;
			}
		}
		return null;
	}
	
	 /**
	 * 
	 * <pre>
	 * CopyRight(c) 2015
	 * 创建人：杨博申
	 * 日期：2015年7月14日15:04:37
	 * 描述：获取登录用户信息
	 * </pre>
	 */
	public AppDataTable queryUser() throws BaseBllException {
		SYS_USER_EXT appUser = (SYS_USER_EXT)context.getSessionAttribute("appuser");
		String USER_CODE = appUser.getUSER_CODE();
		return loginDal.queryUser(super.getBaseDB(),USER_CODE);
	}

}