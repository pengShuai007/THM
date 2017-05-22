package APP.Comm.DataBase.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.Config.SystemParams;
import APP.Comm.DotNet.Application;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.MD5Pass;

/**
 * comments:
 * 
 * sjl modify 2013-10-15����1:17:18
 */
public class DBUtil {

	private static DBUtil dbUtil = null;
	/**
	 *任务：
	 *描述：测试，存放url和user、password
	 *人员：施建龙
	 *时间：2014年11月19日下午4:54:28
	 **/
	//运维数据库链接
	private String driverClassBase = null;
	private String jdbcUrlBase = null;
	private String userBase = null;
	private String passwordBase = null;
	
	/**
	 *任务：
	 *描述：尝试使用dbcp连接池
	 *人员：施建龙
	 *时间：2014年11月21日上午9:38:33
	 **/
	
	private DBUtil() {
	}

	public static DBUtil getInstance() {

		if (dbUtil == null) {
			dbUtil = new DBUtil();
		}

		return dbUtil;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-15����1:25:21
	 * 
	 * @throws BaseBllException
	 */
	public void openDatasource(String configFile) throws BaseBllException {

		try {
			Properties props = new Properties();
			InputStream in = new FileInputStream(Application.get("APP_HOME")
					+ "" + File.separatorChar + "config" + File.separatorChar
					+ SystemParams.getParamaValue("DATASOURCECONFIGFILE"));
			props.load(in);
		
			//运维数据库链接
			this.driverClassBase = props.getProperty("driverClassBase");
			this.jdbcUrlBase = props.getProperty("jdbcUrlBase");
			this.userBase = props.getProperty("userBase");
			this.passwordBase = MD5Pass.DecryptString(props.getProperty("passwordBase"));
		} catch (Exception e) {
			throw new BaseBllException(e);
		}
	}

	/**
	 * 任务号：
	 * 描述：建立运维数据库链接
	 * 作者：liuxingping
	 * 时间：2015年5月22日下午5:25:54
	 * @return
	 * @throws BaseBllException
	 */
	public Connection getBaseConnection() throws BaseBllException {
		Connection con = null;
		try {
			Class.forName(this.driverClassBase);
			con = DriverManager.getConnection(this.jdbcUrlBase, this.userBase, this.passwordBase);
			con.setAutoCommit(true);
		} catch (Exception e) {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					HLogger.Error(e);
				}
			}
			throw new BaseBllException(e);
		}
		return con;
	}
}
