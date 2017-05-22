package com.kyee.cloud.systemParameter.dal;

import java.util.ArrayList;
import java.util.List;

import model.base.ext.SYS_PARAMETER_EXT;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.AppEntitys;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.HLogger;

/**
 * <pre>
 * 任务：KYEEAPP-1006
 * 描述：系统参数
 * 作者：何萌
 * 时间：2014年11月13日13:58:47
 * </pre>
 */
public class SysParameterDal {
	/**
	 * <pre>
	 * 任务：KYEEAPP-1006
	 * 描述：获取radio值
	 * 作者：何萌
	 * 时间：2014年11月13日13:58:47
	 * </pre>
	 */
	public final AppDataTable GetRadioTableValue(
			SYS_PARAMETER_EXT sysParameterExt, IDataBase baseDB)
			throws BaseBllException {
		HLogger.info("SysParameterDal  Function GetRadioTableValue begin!");
		HLogger.info("说明：获取radio值");
		String sql = "  SELECT " + sysParameterExt.getVALUE_COLUMN()
				+ " AS VALUEFIELD," + sysParameterExt.getDISPLAY_COLUMN()
				+ " AS TEXTFIELD FROM " + sysParameterExt.getSOURCE_TABLE()
				+ " WHERE 1=1 " + sysParameterExt.getFILTER();
		AppDataTable result = baseDB.Query(sql, null);
		HLogger.info("SysParameterDal  Function GetRadioTableValue end!");
		return result;
	}

	/**
	 * <pre>
	 * 任务：KYEEAPP-1006
	 * 描述：按系统排版系统参数
	 * 作者：何萌
	 * 修改者：罗代华
	 * 修改原因：增加超级用户
	 * 时间：2014年11月13日13:58:47
	 * </pre>
	 */
	public final AppEntitys GetAllSysParamOrderBySys(IDataBase baseDb,
			boolean flag) throws BaseBllException {
		HLogger.info("SysParameterDal  Function GetAllSysParamOrderBySys begin!");
		HLogger.info("获取所有系统参数");
		String sql = "   SELECT       " + "\r\n"
				+ "                        SYS_PARA_ID," + "\r\n";
		sql += "                        PARA_TYPE," + "\r\n"
				+ "                        PARA_CODE," + "\r\n"
				+ "                        PARA_NAME," + "\r\n"
				+ "                        PARA_VALUE," + "\r\n"
				+ "                        COMMENTS," + "\r\n"
				+ "                        SUB_SYSTEM," + "\r\n"
				+ "                        PARA_DESC," + "\r\n"
				+ "                        IS_GLOBAL," + "\r\n"
				+ "                        IS_INIT_PARA," + "\r\n"
				+ "                        BUSINESS_CODE," + "\r\n"
				+ "                        IS_DISPLAY," + "\r\n"
				+ "                        IF_HAVE_SOURCE," + "\r\n"
				+ "                        SOURCE_TABLE," + "\r\n"
				+ "                        VALUE_COLUMN," + "\r\n"
				+ "                        DISPLAY_COLUMN," + "\r\n"
				+ "                        IF_MULTI_CHECK," + "\r\n"
				+ "                        FILTER," + "\r\n"
				+ "                        CONTROL_TYPE," + "\r\n"
				+ "                        PARA_VALUES," + "\r\n"
				+ "                        DISPLAY_ORDER," + "\r\n"
				+ "                        CONTENT_HREF_LINK," + "\r\n"
				+ "                        COLSPAN  " + "\r\n"
				+ "                      FROM       " + "\r\n"
				+ "                        SYS_PARAMETER " + "\r\n"
				+ "                      WHERE     " + "\r\n"
				+ "                         1=1 " + "\r\n";
		if (!flag) {
			sql += "                   AND  IS_DISPLAY= 1";
		}
		sql += "     ORDER BY       DISPLAY_ORDER ";

		AppEntitys result = baseDb.QueryEntity(SYS_PARAMETER_EXT.class, sql,
				null);
		HLogger.info("SysParameterDal  Function GetAllSysParamOrderBySys end!");
		return result;
	}

	/**
	 * <pre>
	 * 任务：KYEEAPP-1006
	 * 描述：按系统排版系统参数
	 * 作者：何萌
	 * 时间：2014年11月13日13:58:47
	 * 修改：hemeng
	 * 修改内容：KYEEAPP-1017 区分是云还是端发送的请求 
	 * 修改时间：2014年11月20日16:10:51
	 * </pre>
	 */
	public AppEntitys queryBusinessCode(IDataBase baseDb, String currentUser)
			throws BaseBllException {
		HLogger.info("SysParameterDal  Function queryBusinessCode start!");
		HLogger.info("说明:查询子系统下的BUSINESS_CODE");
		// edit start KYEEAPPMAINTENANCE-23 罗京
		// 数据库中已经去掉了PARA_SOURCE字段，并且不再根据树菜单查询 2015年1月8日11:21:52
		String sql = "SELECT DISTINCT(BUSINESS_CODE)" + "\r\n"
				+ "FROM  SYS_PARAMETER " + "\r\n";
		if (!"admin".equals(currentUser)) {
			sql += "WHERE IS_DISPLAY = 1 " + "\r\n";
		}
		// edit end KYEEAPPMAINTENANCE-23 罗京 数据库中已经去掉了PARA_SOURCE字段，并且不再根据树菜单查询
		// 2015年1月8日11:21:52
		AppEntitys result = baseDb.QueryEntity(SYS_PARAMETER_EXT.class, sql,
				null);
		HLogger.info("SysParameterDal  Function queryBusinessCode end!");
		return result;
	}

	// /////////////////////////下面这个方法是李添加的///////////////////////////////////////////////////////
	public final SYS_PARAMETER_EXT getParamsById(IDataBase appDB, String paraId)
			throws BaseBllException {
		HLogger.info("SysParameterDal  Function getParamsById begin!");
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM SYS_PARAMETER ");
		sql.append(" WHERE SYS_PARA_ID = :SYS_PARA_ID ");
		List<AppDbParameter> parameters = new ArrayList<AppDbParameter>();
		parameters.add(new AppDbParameter("SYS_PARA_ID", paraId));
		SYS_PARAMETER_EXT result = (SYS_PARAMETER_EXT) appDB
				.QueryEntity(SYS_PARAMETER_EXT.class, sql + "", parameters)
				.getEntityList().get(0);
		HLogger.info("SysParameterDal  Function getParamsById end!");
		return result;
	}
}
