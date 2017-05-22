package APP.Comm.DataBase.Helper;

import java.util.Arrays;

import APP.Comm.DataBase.DotNet.OracleParameter;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.DataBase.Parameter.ConvertParameter;
import APP.Comm.DataBase.Parameter.AppDbType;
import APP.Comm.Util.HLogger;
import APP.Model.BaseEntity;
import APP.Model.Base.Ext.SYS_DB_LOG_EXT;

/**
 * comments:从概念上定义为工具类，不再作为DataBase处理
 * 
 * sjl modify 2013-10-5下午3:25:29
 */
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public class DBLog {
	// private static IDataBase HrpDB;
	// HRP参数已经作废
	// 施建龙
	// 2013年7月12日15:19:46
	// private static string connectionString =
	// ConfigurationManager.AppSettings["HRP"].ToString().Trim();

	public static java.util.List<BaseEntity> LogList = new java.util.ArrayList<BaseEntity>();

	/**
	 * 说明：向HRP数据库中插入数据库操作记录 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 */
	public static Object SaveLog(AbstractDataBase appDB,
			java.util.List<BaseEntity> LogList, String MODULE_NAME) {
		if (LogList == null || LogList.isEmpty()) {
			return 0;
		}
		int ret = 0;
		StringBuilder strSql = null;
		String logSqlString = null;
		try {

			java.util.List<AppDbParameter> dbParameters = null;

			for (BaseEntity baseEntity : LogList) {
				SYS_DB_LOG_EXT sysDbLog = (SYS_DB_LOG_EXT) baseEntity;

				strSql = new StringBuilder();
				strSql.append(" insert into sys_db_log ");
				strSql.append("  ( USER_ID,LOG_TYPE,LOG_CONTENT");
				strSql.append("    ,CREATOR_NAME,CREATE_TIME ");
				strSql.append("    ,MODULE_NAME ");
				strSql.append("   )");
				strSql.append(" Values  ");
				strSql.append("  (  :USER_ID,:LOG_TYPE ,:LOG_CONTENT");
				strSql.append("     ,:CREATOR_NAME,:CREATE_TIME ");
				strSql.append("     ,:MODULE_NAME ");
				strSql.append("  ) ");

				// 去掉sysDbLog.LOG_TYPE.ToString().Trim()类似写法中的.ToString().Trim()
				// 1、因未做非空判断，此处理方式会导致空指针错误
				//
				dbParameters = new java.util.ArrayList<AppDbParameter>();
				dbParameters.add(new AppDbParameter("USER_ID",
						AppDbType.VarChar, sysDbLog.getUSER_ID()));
				dbParameters.add(new AppDbParameter("LOG_TYPE",
						AppDbType.VarChar, sysDbLog.getLOG_TYPE()));
				dbParameters.add(new AppDbParameter("LOG_CONTENT",
						AppDbType.Clob, sysDbLog.getLOG_CONTENT()));
				dbParameters.add(new AppDbParameter("CREATOR_NAME",
						AppDbType.VarChar, sysDbLog.getCREATOR_NAME()));
				dbParameters.add(new AppDbParameter("CREATE_TIME",
						AppDbType.DateTime, sysDbLog.getCREATE_TIME()));
				dbParameters.add(new AppDbParameter("MODULE_NAME",
						AppDbType.VarChar, MODULE_NAME));
				appDB.DbCommand.Parameters.clear();
				appDB.DbCommand.CommandText = "";
				appDB.DbCommand.CommandText = strSql.toString();

				// 测试
				// sjl
				// 2014年1月17日19:06:37

				logSqlString = GetLogContent(strSql.toString(), dbParameters);

				if (dbParameters != null) {
					OracleParameter[] cmdParameter = ConvertParameter
							.ConvertToDbParameter(dbParameters);
					appDB.DbCommand.Parameters.addAll(Arrays
							.asList(cmdParameter));
				}
				appDB.DbCommand.parameterOrders = ((AbstractDataBase) appDB)
						.getSqlParameters(strSql.toString());
				ret += appDB.DbCommand.ExecuteNonQuery();
			}
			appDB.connection.commit();
		} catch (Exception e) {
			HLogger.error("sql:" + logSqlString);
			HLogger.Error(e);
			try {
				appDB.connection.rollback();
			} catch (Exception e2) {
				HLogger.error(e2);
			}
		} finally {
			LogList.clear(); // 清空记录列表
			// Connection连接关闭放到创建Connection的层
			// 施建龙
			// 2013年10月14日15:07:22
			// try {
			// if (!hrpDB.connection.isClosed()) {
			// hrpDB.connection.close();
			// }
			// } catch (Exception e) {
			// HLogger.error(e);
			// }
		}

		return ret;
	}

	/**
	 * comments:从AbstractDataBase迁移过来，主要用于跟踪log的语句
	 * 
	 * sjl modify 2014年1月17日下午7:08:20
	 */
	public static final String GetLogContent(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters) {
		if (AppDbParameters == null) {
			return sqlString;
		}

		String tmpSql = sqlString.toUpperCase();
		String tmpString = "";

		for (AppDbParameter pars : AppDbParameters) {
			// 修改内容：对于SQL Server 和Oracle的参数区分处理
			// 临时处理方案，使用if进行判断
			// 2013年5月29日17:03:00
			// 施建龙
			String parName;
			if (pars.getColumnName().toString().startsWith("@")) {
				// sql server
				parName = pars.getColumnName().trim().toUpperCase();
			} else {
				// oracle
				parName = ":" + pars.getColumnName().trim().toUpperCase();
			}

			String parValue = pars.getColumnValue() == null ? "" : pars
					.getColumnValue().toString().trim();

			if (tmpSql.contains(parName)) {
				tmpString = tmpSql.replace(parName, "'" + parValue + "'");
				tmpSql = tmpString;
			}
		}

		// 将最终字符串中的连续的空格转换成一个空格
		// return new Regex("[\\s]+").Replace(tmpSql, " ").trim();
		return tmpSql;
	}
}