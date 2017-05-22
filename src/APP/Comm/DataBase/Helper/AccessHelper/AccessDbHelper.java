package APP.Comm.DataBase.Helper.AccessHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.CommandType;
import APP.Comm.DataBase.Helper.OracleHelper.OracleDbHelper;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.HLogger;

public class AccessDbHelper extends OracleDbHelper {

	// 数据库连接对象；
	private Connection conn;
	// SQL语句执行对象；
	private Statement statement;

	/**
	 * 执行插入语句 ；
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int insertDB(String sql) throws Exception {
		excuteSql(sql);
		return 0;
	}

	/**
	 * 执行更新语句 ；
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int updateDB(String sql) throws Exception {
		excuteSql(sql);
		return 0;
	}

	/**
	 * 执行查询语句；
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public ResultSet selectDB(String sql) throws Exception {
		ResultSet result = null;
		try {
			result = statement.executeQuery(sql);
		} catch (Exception e) {
			HLogger.Error(e);
		} finally {
			result.close();
			closeDB();
		}
		return result;
	}

	/**
	 * 执行删除语句；
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int deleteDB(String sql) throws Exception {
		excuteSql(sql);
		return 0;
	}

	/**
	 * 连接ACSSESS文件；
	 * 
	 * @param filePath
	 * @param user
	 * @param pass
	 * @throws Exception
	 */
	public void connAccessFile(String filePath, String user, String pass)
			throws Exception {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		String dburl = "jdbc:odbc:driver={Microsoft Access Driver(*.mdb)};DBQ="
				+ filePath;
		conn = DriverManager.getConnection(dburl, user, pass);
		statement = conn.createStatement();
	}

	/**
	 * 连接ACCESS数据源；
	 * 
	 * @param dsName
	 * @param user
	 * @param pass
	 * @throws Exception
	 */
	public void connAccessDataSource(String dsName, String user, String pass)
			throws Exception {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		String dburl = "jdbc:odbc:" + dsName;
		conn = DriverManager.getConnection(dburl, user, pass);
		statement = conn.createStatement();
	}

	/**
	 * 关闭数据库连接；
	 * 
	 * @throws Exception
	 */
	public void closeDB() throws Exception {
		if (statement != null)
			statement.close();
		if (conn != null)
			conn.close();
	}

	/**
	 * 执行SQL语句
	 * 
	 * @throws Exception
	 */
	private void excuteSql(String sql) throws Exception {
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.executeUpdate();
		ps.close();
		closeDB();
	}
}
