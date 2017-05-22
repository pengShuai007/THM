package com.kyee.cloud.operator.dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.base.ext.C_SYSTEM_MENU_EXT;
import model.base.ext.SYS_DEPT_POST_FRAME_EXT;
import model.base.ext.SYS_MENU_EXT;
import model.base.ext.SYS_USER_EXT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.DataBase.Helper.AbstractDataBase;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;
import APP.Comm.View.GridRequestParameters;
import APP.Model.BaseEntity;
import APP.Model.C_SYSTEM_HOSPITAL;

import com.mysql.jdbc.CallableStatement;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年2月20日 下午8:59:56
 * 描述:管理员登陆
 * </pre>
 */
public class OperatorLoginDalC {
	/**
	 * 
	 * <pre>
	 * 作者:刘健
	 * 日期:2014年2月20日 下午9:45:55
	 * 描述:查询管理员登陆名及密码
	 * </pre>
	 */
	public List<BaseEntity> queryAdmin(IDataBase baseDb, Map<String, String> map)
			throws BaseBllException {
		// String sql =
		// "SELECT * FROM SYS_USER WHERE OPERATOR_NAME=:OPERATORNAME AND PASS_WORD=:PASSWORD ";
		String sql = "SELECT * FROM SYS_USER WHERE USER_CODE =:USER_CODE AND PASS_WORD=:PASSWORD ";
		List<AppDbParameter> appDbParameters = new ArrayList<AppDbParameter>();
		appDbParameters.add(new AppDbParameter("USER_CODE", map
				.get("operatorName")));
		appDbParameters
				.add(new AppDbParameter("PASSWORD", map.get("passWord")));
		List<BaseEntity> rest = baseDb.QueryEntity(SYS_USER_EXT.class, sql,
				appDbParameters).getEntityList();
		return rest;

	}

	public List<BaseEntity> queryAll(IDataBase baseDb) throws BaseBllException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT MENU_ID,MENU_NAME,C_S_MENU_ID,MENU_URL,M.MENU_CODE ");
		sql.append(" FROM SYS_MENU M WHERE M.C_S_MENU_ID IS NOT NULL ORDER BY MENU_ORDER ");
		return baseDb.QueryEntity(SYS_MENU_EXT.class, sql.toString(), null)
				.getEntityList();
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年4月22日下午7:11:46
	 * 描述：根据用户名查询所属的角色及权限
	 * </pre>
	 */
	public List<BaseEntity> queryByName(IDataBase baseDb, String loginName)
			throws BaseBllException {
		StringBuffer sql = new StringBuffer();
		// sql.append("SELECT MENU_ID,MENU_NAME,C_S_MENU_ID,MENU_URL,M.MENU_CODE     ");
		// sql.append("FROM SYS_MENU M INNER JOIN SYS_ROLE_AUTHORITY A ON (M.MENU_CODE=A.MENU_CODE)");
		// sql.append("INNER JOIN SYS_ROLE R ON(R.ROLE_ID=A.ROLE_ID)");
		// sql.append("INNER JOIN SYS_USER O ON(O.ROLE_ID=R.ROLE_ID)");
		// sql.append("WHERE O.USER_CODE =:USER_CODE AND M.C_S_MENU_ID IS NOT NULL");
		sql.append("SELECT DISTINCT MENU_ID,MENU_NAME,C_S_MENU_ID,MENU_URL,M.MENU_CODE     ");
		sql.append("FROM SYS_MENU M INNER JOIN SYS_POST_PERM A ON (M.MENU_CODE = A.MENU_CODE)");
		sql.append("INNER JOIN SYS_DEPT_POST_INFO O ON (O.POST_ID = A.POST_ID) ");
		sql.append("INNER JOIN SYS_DEPT_POST_DETAIL R ON (R.INFO_ID = O.INFO_ID)");
		sql.append("INNER JOIN SYS_USER S ON (S.USER_ID = R.USER_ID) ");
		sql.append("WHERE S.USER_CODE = :USER_CODE AND M.C_S_MENU_ID IS NOT NULL ORDER BY MENU_ORDER ");
		List<AppDbParameter> para = new ArrayList<AppDbParameter>();
		para.add(new AppDbParameter("USER_CODE", loginName));
		return baseDb.QueryEntity(SYS_MENU_EXT.class, sql.toString(), para)
				.getEntityList();
	}

	/**
	 * 
	 * <pre>
	 * 作者:秦晓东
	 * 日期:2014年2月27日 下午4:00:07
	 * 描述:查询菜单
	 * </pre>
	 */
	public List<BaseEntity> queryMenus(IDataBase appDb) throws BaseBllException {
		String sql = " SELECT * FROM C_SYSTEM_MENU WHERE C_S_MENU_ID IS NOT NULL ORDER BY MENU_ORDER ";

		return appDb.QueryEntity(C_SYSTEM_MENU_EXT.class, sql, null)
				.getEntityList();
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年4月24日下午1:30:43
	 * 描述：查询用户及其角色
	 * </pre>
	 */
	public AppDataTable queryoperator(IDataBase baseDb,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		StringBuffer sql = new StringBuffer();
		// sql.append("SELECT O.OPERATOR_ID,O.HOSPITAL_ID,HOSPITAL_NAME,O.OPERATOR_NAME,O.ROLE_ID,ROLE_NAME FROM SYS_USER O INNER JOIN C_SYSTEM_ROLE R ON (O.ROLE_ID=R.ROLE_ID) INNER JOIN C_SYSTEM_HOSPITAL H ON (O.HOSPITAL_ID=H.HOSPITAL_ID ) ");
		sql.append("SELECT USER_CODE, USER_ID,USER_NAME,PHONE_NUM,EMAIL,JOB_ID,IS_PROTECTED FROM SYS_USER ");
		return baseDb.QueryByPage(sql.toString(), "USER_CODE", null,
				gridRequestParameters);
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年4月24日下午1:30:43
	 * 描述：查询用户及其角色
	 * </pre>
	 */
	public DataTable queryoperatorFor(IDataBase appDb) throws BaseBllException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT O.OPERATOR_ID,O.HOSPITAL_ID,HOSPITAL_NAME,O.OPERATOR_NAME,O.ROLE_ID,ROLE_NAME FROM SYS_USER O INNER JOIN C_SYSTEM_ROLE R ON (O.ROLE_ID=R.ROLE_ID) left JOIN C_SYSTEM_HOSPITAL H ON (O.HOSPITAL_ID=H.HOSPITAL_ID ) where O.OPERATOR_NAME<>'approot'");

		return appDb.Query(sql.toString(), null).getDataTable();
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年5月7日下午2:35:28
	 * 描述：查询医院下拉列表
	 * </pre>
	 */
	public List<BaseEntity> queryhoscombox(IDataBase appDb)
			throws BaseBllException {
		StringBuffer sql = new StringBuffer();
		// Edit begin 党智康 2014年11月28日 16:47:18
		// 增加试上线医院
		// 任务：KYEEAPPTEST-806
		sql.append(" SELECT HOSPITAL_ID,HOSPITAL_NAME FROM C_SYSTEM_HOSPITAL WHERE STATUS IN('1', '2')");
		// Edit end
		return appDb.QueryEntity(C_SYSTEM_HOSPITAL.class, sql.toString(), null)
				.getEntityList();
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年6月25日下午4:35:28
	 * 描述：查询医院下拉列表
	 * </pre>
	 */
	public List<BaseEntity> queryhoscomboxByHid(IDataBase appDb, int hospitalId)
			throws BaseBllException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOSPITAL_ID,HOSPITAL_NAME FROM C_SYSTEM_HOSPITAL WHERE STATUS=1 AND HOSPITAL_ID=:HOSPITAL_ID");
		List<AppDbParameter> para = new ArrayList<AppDbParameter>();
		para.add(new AppDbParameter("HOSPITAL_ID", hospitalId));
		return appDb.QueryEntity(C_SYSTEM_HOSPITAL.class, sql.toString(), para)
				.getEntityList();
	}

	/**
	 * 任务号：KYEEAPPMAINTENANCE-1047 描述：校验用户是否存在 作者：李添 时间：2016年10月26日19:09:48
	 * 
	 * @param baseDb
	 * @param userCode
	 * @return
	 * @throws BaseBllException
	 */
	public AppDataTable checkUserCode(IDataBase baseDb, String userCode)
			throws BaseBllException {
		String sql = "SELECT COUNT(*) userCount FROM sys_user WHERE USER_CODE = :USER_CODE ";
		List<AppDbParameter> para = new ArrayList<AppDbParameter>();
		para.add(new AppDbParameter("USER_CODE", userCode));
		return baseDb.Query(sql, para);
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午4:20:39
	 * 描述：新增用户检查
	 * </pre>
	 */
	public int saveoperator(IDataBase baseDb, SYS_USER_EXT oper)
			throws BaseBllException {
		StringBuffer sql = new StringBuffer();
		String userCode = oper.getUSER_CODE();
		sql.append("SELECT * FROM SYS_USER WHERE USER_CODE = :USER_CODE ");
		List<AppDbParameter> para = new ArrayList<AppDbParameter>();
		para.add(new AppDbParameter("USER_CODE", userCode));
		List<BaseEntity> checkoper = baseDb.QueryEntity(SYS_USER_EXT.class,
				sql.toString(), para).getEntityList();
		if (checkoper != null && checkoper.size() > 0) {
			return 0;
		}
		HLogger.info("##########*_*##########" + oper.getJOB_ID());
		return baseDb.Save(oper);
		// Edit end KYEEAPPMAINTENANCE-45 liuxingping
	}

	public int querybyjobid(IDataBase baseDb, String JOB_ID)
			throws BaseBllException {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(*) counts FROM SYS_USER WHERE JOB_ID = :JOB_ID ");
		List<AppDbParameter> para = new ArrayList<AppDbParameter>();
		para.add(new AppDbParameter("JOB_ID", JOB_ID));
		int sum = baseDb.Query(sql.toString(), para).getDataTable().getRows()
				.get(0).getIntColumn("counts");
		HLogger.info("##########*_*##########" + JOB_ID);
		return sum;

	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午5:27:41
	 * 描述：修改用户
	 * </pre>
	 */
	public int changeOperator(IDataBase baseDb, SYS_USER_EXT oper)
			throws BaseBllException {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE SYS_USER SET USER_CODE =:USERCODE, USER_NAME=:USERNAME,ROLE_ID=:ROLEID,PHONE_NUM=:PHONE_NUM,EMAIL=:EMAIL,JOB_ID=:JOBID,IS_PROTECTED=:IS_PROTECTED WHERE USER_ID=:USERID");
		List<AppDbParameter> parp = new ArrayList<AppDbParameter>();
		parp.add(new AppDbParameter("USERCODE", oper.getUSER_CODE()));
		parp.add(new AppDbParameter("USERNAME", oper.getUSER_NAME()));
		parp.add(new AppDbParameter("ROLEID", oper.getROLE_ID()));
		parp.add(new AppDbParameter("USERID", oper.getUSER_ID()));
		parp.add(new AppDbParameter("PHONE_NUM", oper.getPHONE_NUM()));
		parp.add(new AppDbParameter("EMAIL", oper.getEMAIL()));
		parp.add(new AppDbParameter("JOBID", oper.getJOB_ID()));
		parp.add(new AppDbParameter("IS_PROTECTED", oper.getIS_PROTECTED()));
		return baseDb.Update(sql.toString(), parp);
	}

	/**
	 * s
	 * 
	 * <pre>
	 *  CopyRight(c) 2014-2015
	 *  创建人：兰文涛
	 *  日期：2014年4月24日下午5:28:14
	 *  描述：删除用户
	 *  edit 2016年3月8日下午5:28:14
	 * 荣昌:已存储过程的形式调用，进行删除
	 * </pre>
	 */
	public int deleteOperator(IDataBase baseDb, int operId)
			throws BaseBllException {

		AbstractDataBase baseDB = (AbstractDataBase) baseDb;
		Connection baseDbConnection = null;
		baseDB.OpenBase();
		baseDbConnection = baseDB.getConnection();
		int result = 1;
		CallableStatement callstatement;
		try {
			HLogger.info("OperatorLoginDalC Function deleteOperator Start!");
			callstatement = (CallableStatement) baseDbConnection
					.prepareCall("call P_DELETE_SYS_USER(?)");

			callstatement.setInt(1, operId);

			callstatement.execute();
			HLogger.info("OperatorLoginDalC Function test end!");
			if (baseDbConnection != null) {
				HLogger.info("OperatorLoginDalC Function deleteOperator test!");
				baseDbConnection.commit();
			}
		} catch (SQLException e) {
			HLogger.info("OperatorLoginDalC Function exet test" + e.toString());
			result = 0;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// conn，数据库连接
		HLogger.info("OperatorLoginDalC Function end");
		return result;
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月24日下午8:25:28
	 * 描述：根据用户名查询
	 * </pre>
	 */
	public DataTable queryOperatorByname(IDataBase baseDb, String postId,
			String userCode, String userName,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		StringBuffer sql = new StringBuffer();
		// sql.append("SELECT O.OPERATOR_ID,O.HOSPITAL_ID,HOSPITAL_NAME,O.OPERATOR_NAME,O.ROLE_ID,ROLE_NAME FROM SYS_USER O INNER JOIN C_SYSTEM_ROLE R ON (O.ROLE_ID=R.ROLE_ID) INNER JOIN C_SYSTEM_HOSPITAL H ON (O.HOSPITAL_ID=H.HOSPITAL_ID ) WHERE O.OPERATOR_NAME =:NAME ");
		sql.append(" SELECT DISTINCT AA.USER_ID, AA.USER_CODE, AA.USER_NAME,AA.PHONE_NUM,AA.EMAIL,AA.JOB_ID,AA.IS_PROTECTED  FROM (SELECT SU.*,SDP.POST_ID FROM SYS_USER SU ");
		sql.append(" LEFT JOIN SYS_DEPT_POST_DETAIL SD ON SU.USER_ID = SD.USER_ID ");
		sql.append(" LEFT JOIN SYS_DEPT_POST_INFO SDP ON SD.INFO_ID = SDP.INFO_ID) AA WHERE 1=1 ");
		List<AppDbParameter> para = new ArrayList<AppDbParameter>();
		if (!DotNetToJavaStringHelper.isNullOrEmpty(userName)) {
			sql.append(" AND AA.USER_NAME LIKE :NAME");
			para.add(new AppDbParameter("NAME", "%" + userName + "%"));
		}
		if (!DotNetToJavaStringHelper.isNullOrEmpty(userCode)) {
			sql.append(" AND AA.USER_CODE LIKE :CODE");
			para.add(new AppDbParameter("CODE", "%" + userCode + "%"));
		}
		// Edit start KYEEAPPMAINTENANCE-128 haofahui 增加角色过滤 2015年3月20日 13:32:47
		if (!DotNetToJavaStringHelper.isNullOrEmpty(postId)) {
			sql.append(" AND AA.POST_ID = :POST_ID");
			para.add(new AppDbParameter("POST_ID", postId));
		}
		// Edit end KYEEAPPMAINTENANCE-128 haofahui 增加角色过滤 2015年3月20日 13:32:47
		return baseDb.QueryByPage(sql.toString(), "USER_ID", para,
				gridRequestParameters).getDataTable();
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：兰文涛
	 * 日期：2014年4月28日下午7:40:32
	 * 描述：修改后台登录密码
	 * </pre>
	 */
	public int updatepwd(IDataBase baseDb, SYS_USER_EXT oper, String userCode)
			throws BaseBllException {
		// Edit start 杨博申 2015年7月6日14:46:24
		List<AppDbParameter> appDbParameters = new ArrayList<AppDbParameter>();
		// Edit start 黄鹏 2016年6月6日10:53:24
		String sqlString = "UPDATE SYS_USER SET PHONE_NUM=:PHONE_NUM,EMAIL=:EMAIL";
		// Edit end 黄鹏 2016年6月6日10:53:35
		if (!oper.getPASS_WORD().equals("")
				&& !oper.getOLDPASS_WORD().equals("")) {
			sqlString += ",PASS_WORD =:PASSWORD";
			appDbParameters.add(new AppDbParameter("PASSWORD", oper
					.getPASS_WORD()));
		}
		sqlString += " WHERE USER_CODE =:USER_CODE ";
		if (!oper.getPASS_WORD().equals("")
				&& !oper.getOLDPASS_WORD().equals("")) {
			sqlString += "AND PASS_WORD=:OLDPASSWORD";
			appDbParameters.add(new AppDbParameter("OLDPASSWORD", oper
					.getOLDPASS_WORD()));
		}
		appDbParameters.add(new AppDbParameter("USER_CODE", userCode));
		appDbParameters
				.add(new AppDbParameter("PHONE_NUM", oper.getPHONE_NUM()));
		appDbParameters.add(new AppDbParameter("EMAIL", oper.getEMAIL()));
		int a = baseDb.Update(sqlString, appDbParameters);
		// Edit end 杨博申 2015年7月6日14:46:07
		return a;
	}

	/**
	 * <pre>
	 * 任务:KYEEAPPMAINTENANCE-50
	 * 描述:第一次握手校验
	 * 作者:罗京
	 * 日期:2015年2月2日 下午4:54:18
	 * @param appDb
	 * @param user
	 * @param key
	 * @return
	 * @throws BaseBllException
	 * </pre>
	 */
	public String checkUserName(IDataBase baseDb, String uname, String key)
			throws BaseBllException {
		HLogger.info("OperatorLoginDalC Function checkUserName Start!");
		String result = null;// 返回的结果
		List<BaseEntity> opreater = queryByName(baseDb, uname);
		// 用户名不存在
		if (opreater.size() == 0 || opreater == null) {
			return result;
		}
		// 用户名存在,保存key值
		String sqlstr = "UPDATE SYS_USER SET AUTH_KEY=:KEY WHERE USER_CODE=:OPERATORNAME";
		List<AppDbParameter> appDbParam = new ArrayList<AppDbParameter>();
		appDbParam.add(new AppDbParameter("KEY", key));
		appDbParam.add(new AppDbParameter("OPERATORNAME", uname));
		baseDb.Update(sqlstr, appDbParam);
		// 返回key值和服务器时间
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestring = df.format(date);
		result = key + "_" + datestring;
		HLogger.info("OperatorLoginDalC Function checkUserName End!");
		return result;
	}

	/**
	 * <pre>
	 * 任务:KYEEAPPMAINTENANCE-50
	 * 描述:通过用户名查询用户信息
	 * 作者:罗京
	 * 日期:2015年2月9日 下午2:43:36
	 * @param appDb
	 * @param uname
	 * @param key
	 * @return
	 * @throws BaseBllException
	 * </pre>
	 */
	public SYS_USER_EXT queryByOperatorName(IDataBase baseDb, String uname)
			throws BaseBllException {
		HLogger.info("OperatorLoginDalC Function queryByOperatorName Start!");
		String sql = "SELECT * FROM SYS_USER WHERE USER_CODE=:OPERATORNAME";
		List<AppDbParameter> appDbParameters = new ArrayList<AppDbParameter>();
		appDbParameters.add(new AppDbParameter("OPERATORNAME", uname));
		List<BaseEntity> opreater = baseDb.QueryEntity(SYS_USER_EXT.class, sql,
				appDbParameters).getEntityList();
		SYS_USER_EXT operator = (SYS_USER_EXT) opreater.get(0);
		HLogger.info("OperatorLoginDalC Function queryByOperatorName End!");
		return operator;
	}

	/**
	 * <pre>
	 * 任务:KYEEAPPMAINTENANCE-50
	 * 描述:第二次握手校验用户登录
	 * 作者:罗京
	 * 日期:2015年2月3日 上午10:40:41
	 * @param appDb
	 * @param uname
	 * @param upass
	 * @return
	 * @throws BaseBllException
	 * </pre>
	 */
	public SYS_USER_EXT checkUserLogin(IDataBase baseDb, String uname,
			String upass) throws BaseBllException {
		HLogger.info("OperatorLoginDalC Function checkUserLogin Start!");
		SYS_USER_EXT result = null;
		String sql = "SELECT * FROM SYS_USER WHERE USER_CODE=:OPERATORNAME AND PASS_WORD = :PASSWORD";
		List<AppDbParameter> appDbParameters = new ArrayList<AppDbParameter>();
		appDbParameters.add(new AppDbParameter("OPERATORNAME", uname));
		appDbParameters.add(new AppDbParameter("PASSWORD", upass));
		List<BaseEntity> opreater = baseDb.QueryEntity(SYS_USER_EXT.class, sql,
				appDbParameters).getEntityList();
		if (opreater.size() == 0 || opreater == null)
			return result;
		result = (SYS_USER_EXT) opreater.get(0);
		HLogger.info("OperatorLoginDalC Function checkUserLogin End!");
		return result;
	}

	/**
	 * <pre>
	 * 任务:KYEEAPPMAINTENANCE-50
	 * 描述:删除登录历史记录
	 * 作者:罗京
	 * 日期:2015年2月9日 下午4:54:42
	 * @param appDb
	 * @param str
	 * @throws BaseBllException
	 * </pre>
	 */
	public void deleteLoginInfo(IDataBase baseDb, String str)
			throws BaseBllException {
		HLogger.info("OperatorLoginDalC Function deleteLoginInfo Start!");
		String sql = "DELETE FROM SECURITY_LOGININFO WHERE INFO_ID IN";
		sql += str;
		baseDb.Delete(sql, null);
		HLogger.info("OperatorLoginDalC Function deleteLoginInfo End!");

	}

	public List<BaseEntity> GetUserPost(IDataBase baseDB)
			throws BaseBllException {
		HLogger.info("OperatorLoginDalC Function GetUserPost Start!");
		String sqlStr = " SELECT T.DEPT_ID DEPT_POST_ID, "
				+ " T.DEPT_NAME DEPT_POST_NAME, " + " '0' DEPT_OR_POST, "
				+ " T.DEPT_CODE, " + " T.DEPT_LIST_CODE "
				+ " FROM SYS_DEPT_INFO T" + " WHERE T.DEPT_LIST_CODE = '00' ";
		List<BaseEntity> result = baseDB.QueryEntity(
				SYS_DEPT_POST_FRAME_EXT.class, sqlStr, null).getEntityList();
		HLogger.info("OperatorLoginDalC Function GetUserPost End!");
		return result;
	}

	public List<BaseEntity> GetUserPost(IDataBase baseDB, String deptCode,
			String userCode) throws BaseBllException {
		HLogger.info("OperatorLoginDalC Function GetSysDeptTree Start!");
		List<AppDbParameter> paras = new ArrayList<AppDbParameter>();
		String sqlStr = "(SELECT AA.* FROM (SELECT CONCAT(T.DEPT_ID,'P',S.POST_ID) DEPT_POST_ID,"
				+ " D.INFO_ID, "
				+ " S.POST_NAME DEPT_POST_NAME, "
				+ " CONCAT(T.DEPT_CODE,'*',S.POST_CODE) DEPT_CODE, "
				+ " S.POST_CODE DEPT_LIST_CODE "
				+ " FROM SYS_DEPT_INFO T,SYS_DEPT_POST_INFO D, SYS_POST_DICT S "
				+ " WHERE D.POST_ID = S.POST_ID AND T.DEPT_ID = D.DEPT_ID AND T.DEPT_CODE = :DEPT_CODE ) AA, SYS_DEPT_POST_DETAIL SD, SYS_USER SU "
				+ " WHERE AA.INFO_ID = SD.INFO_ID AND SD.USER_ID = SU.USER_ID AND SU.USER_CODE = :USER_CODE ) "
				+ " UNION "
				+ " (SELECT T.DEPT_ID DEPT_POST_ID, "
				+ " '-1' INFO_ID, "
				+ " T.DEPT_NAME DEPT_POST_NAME, "
				+ " T.DEPT_CODE, "
				+ " T.DEPT_LIST_CODE "
				+ " FROM SYS_DEPT_INFO T, (SELECT DISTINCT SDI.DEPT_CODE FROM SYS_DEPT_POST_DETAIL SDP, "
				+ " SYS_USER SU, SYS_DEPT_POST_INFO SDPI, SYS_DEPT_INFO SDI WHERE SDP.USER_ID = SU.USER_ID "
				+ " AND SU.USER_CODE = :USER_CODE AND SDP.INFO_ID = SDPI.INFO_ID AND SDPI.DEPT_ID = SDI.DEPT_ID) SS "
				+ " WHERE 1=1 "
				+ " AND T.DEPT_CODE LIKE :DEPT_CODED "
				+ " AND T.DEPT_CODE NOT LIKE :DEPT_CODES"
				+ " AND SS.DEPT_CODE LIKE CONCAT(T.DEPT_CODE,'%') "
				+ " ORDER BY DEPT_CODE )";
		paras.add(new AppDbParameter("DEPT_CODE", deptCode));
		paras.add(new AppDbParameter("USER_CODE", userCode));
		paras.add(new AppDbParameter("DEPT_CODED", deptCode + "-%"));
		paras.add(new AppDbParameter("DEPT_CODES", deptCode + "-%-%"));
		List<BaseEntity> result = baseDB.QueryEntity(
				SYS_DEPT_POST_FRAME_EXT.class, sqlStr, paras).getEntityList();
		HLogger.info("OperatorLoginDalC Function GetSysDeptTree End!");
		return result;
	}

	public AppDataTable QueryPost(IDataBase baseDB) throws BaseBllException {
		HLogger.info("OperatorLoginDalC Fuction QueryPost Start!");
		String sqlStr = " SELECT POST_ID, POST_NAME FROM SYS_POST_DICT ";
		AppDataTable result = baseDB.Query(sqlStr, null);
		HLogger.info("OperatorLoginDalC Fuction QueryPost End!");
		return result;
	}

	public int ResetPassWord(IDataBase baseDB, String userCode,
			String defualtPass) throws BaseBllException {
		HLogger.info("OperatorLoginDalC Function ResetPassWord Start");
		List<AppDbParameter> para = new ArrayList<AppDbParameter>();
		String sqlStr = " UPDATE SYS_USER SET PASS_WORD = :PASS_WORD WHERE USER_CODE = :USER_CODE ";
		para.add(new AppDbParameter("PASS_WORD", defualtPass));
		para.add(new AppDbParameter("USER_CODE", userCode));
		int result = baseDB.Update(sqlStr, para);
		HLogger.info("OperatorLoginDalC Function ResetPassWord End");
		return result;
	}

	/**
	 * 任务：KYEEAPPMAINTENANCE-760 作者:黄鹏 日期:2016年5月9日 下午3:44:02 描述:查询用户的Email
	 * 
	 * @param baseDB
	 * @param userCode
	 * @return
	 * @throws BaseBllException
	 */
	public DataTable QueryUserEmail(IDataBase baseDB, String userCode)
			throws BaseBllException {
		HLogger.info("OperatorLoginDalC Fuction QueryUserEmail Start!");
		List<AppDbParameter> para = new ArrayList<AppDbParameter>();
		String sqlStr = " SELECT EMAIL FROM SYS_USER WHERE USER_CODE = :USER_CODE ";
		para.add(new AppDbParameter("USER_CODE", userCode));
		DataTable result = baseDB.Query(sqlStr, para).getDataTable();
		HLogger.info("OperatorLoginDalC Fuction QueryUserEmail End!");
		return result;
	}

	public List<BaseEntity> queryMenusByWorkNumber(IDataBase appDb,
			String workNumber) throws BaseBllException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT MENU_ID,MENU_NAME,C_S_MENU_ID,MENU_URL,M.MENU_CODE     ");
		sql.append("FROM SYS_MENU M INNER JOIN SYS_POST_PERM A ON (M.MENU_CODE = A.MENU_CODE)");
		sql.append("INNER JOIN SYS_DEPT_POST_INFO O ON (O.POST_ID = A.POST_ID) ");
		sql.append("INNER JOIN SYS_DEPT_POST_DETAIL R ON (R.INFO_ID = O.INFO_ID)");
		sql.append("INNER JOIN SYS_USER S ON (S.USER_ID = R.USER_ID) ");
		sql.append("WHERE S.JOB_ID = :WORKNUMBER AND M.C_S_MENU_ID IS NOT NULL ORDER BY MENU_ORDER ");
		List<AppDbParameter> para = new ArrayList<AppDbParameter>();
		para.add(new AppDbParameter("WORKNUMBER", workNumber));
		return appDb.QueryEntity(C_SYSTEM_MENU_EXT.class, sql.toString(), para)
				.getEntityList();
	}
	
	 /**
   	* <pre>
   	* 描述:获取登录用户信息
   	* 作者:杨博申
   	* 日期:2015年7月14日15:59:54
   	* </pre>
   	*/
	public AppDataTable queryUser(IDataBase baseDb, String USER_CODE) throws BaseBllException{
		List<AppDbParameter> Parameters = new ArrayList<AppDbParameter>();
		Parameters.add(new AppDbParameter("USER_CODE", USER_CODE));
		//Edit start 黄鹏 2016年6月6日10:46:24
		String sql = " SELECT PASS_WORD,PHONE_NUM,EMAIL,JOB_ID FROM SYS_USER WHERE USER_CODE = :USER_CODE";
		//Edit end 黄鹏 2016年6月6日10:46:26
		return baseDb.Query(sql, Parameters);
	}

}