package APP.Comm.DataBase.Helper;

import java.sql.Connection;

import APP.Comm.Util.HLogger;

/**
 * comments:
 * 
 * sjl modify 2013-10-5����10:46:26
 */
public class DBConManager {

	public DBConManager() {
	}
	
	/**
	 * 任务号：
	 * 描述：获取运维数据库实例链接
	 * 作者：liuxingping
	 * 时间：2015年5月22日下午5:46:59
	 * @return
	 * @throws Exception
	 */
	public static Connection getBaseConByDataSource() throws Exception {
		HLogger.info("Create Database BaseConnectionByDataSource Start!");
		Connection connection = null;
		connection = DBUtil.getInstance().getBaseConnection();
		connection.setAutoCommit(false);
		HLogger.info("Create DataBase BaseConnectionByDataSource End!");
		return connection;
	}
}
