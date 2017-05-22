package APP.Login.util;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtil {
	private static ComboPooledDataSource mainDs = null;
	private static ComboPooledDataSource testClientDs = null;
	private static ComboPooledDataSource testCloudDs = null;
	private static ComboPooledDataSource devCloudDs = null;
	private static JDBCUtil dbUtil = null;

	private JDBCUtil() {
	}

	public static JDBCUtil getInstance() {

		if (dbUtil == null) {
			dbUtil = new JDBCUtil();
		}

		return dbUtil;
	}
	
	public static void openMainDatasource() throws Exception {

		try {
			InputStream in = JDBCUtil.class.getResourceAsStream("/c3p0-config.properties"); 
			Properties props = new Properties();
			props.load(in);
			in.close();
			mainDs = new ComboPooledDataSource();
			setMainDsProperties(mainDs, props);
			// 等待连接的时间为10秒
			if (mainDs.getCheckoutTimeout() == 0) {
				mainDs.setCheckoutTimeout(30000);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	public static void openTestClientDatasource() throws Exception {

		try {
			InputStream in = JDBCUtil.class.getResourceAsStream("/c3p0-config.properties"); 
			Properties props = new Properties();
			props.load(in);
			in.close();
			testClientDs = new ComboPooledDataSource();
			setTestClientDsProperties(testClientDs, props);
			// 等待连接的时间为10秒
			if (testClientDs.getCheckoutTimeout() == 0) {
				testClientDs.setCheckoutTimeout(30000);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	public static void openTestCloudDatasource() throws Exception {

		try {
			InputStream in = JDBCUtil.class.getResourceAsStream("/c3p0-config.properties"); 
			Properties props = new Properties();
			props.load(in);
			in.close();
			testCloudDs = new ComboPooledDataSource();
			setTestCloudDsProperties(testCloudDs, props);
			// 等待连接的时间为10秒
			if (testCloudDs.getCheckoutTimeout() == 0) {
				testCloudDs.setCheckoutTimeout(30000);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	public static void openDevCloudDatasource() throws Exception {

		try {
			InputStream in = JDBCUtil.class.getResourceAsStream("/c3p0-config.properties"); 
			Properties props = new Properties();
			props.load(in);
			in.close();
			devCloudDs = new ComboPooledDataSource();
			setDevCloudDsProperties(devCloudDs, props);
			// 等待连接的时间为10秒
			if (devCloudDs.getCheckoutTimeout() == 0) {
				devCloudDs.setCheckoutTimeout(30000);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private static void setMainDsProperties(ComboPooledDataSource ds,
			Properties properties) throws Exception {
		Enumeration<Object> e1 = properties.keys();
		while (e1.hasMoreElements()) {
			String key = (String) e1.nextElement();
			String value = (String) properties.getProperty(key);
			switch (key) {
			case "main.acquireIncrement":
				ds.setAcquireIncrement(Integer.parseInt(value));
				break;
			case "main.acquireRetryAttempts":
				ds.setAcquireRetryAttempts(Integer.parseInt(value));
				break;
			case "main.acquireRetryDelay":
				ds.setAcquireRetryDelay(Integer.parseInt(value));
				break;
			case "main.autoCommitOnClose":
				ds.setAutoCommitOnClose(Boolean.parseBoolean(value));
				break;
			case "main.automaticTestTable":
				ds.setAutomaticTestTable(value);
				break;
			case "main.breakAfterAcquireFailure":
				ds.setBreakAfterAcquireFailure(Boolean.parseBoolean(value));
				break;
			case "main.connectionCustomizerClassName":
				ds.setConnectionCustomizerClassName(value);
				break;
			case "main.connectionTesterClassName":
				try {
					ds.setConnectionTesterClassName(value);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					throw new Exception(e);
				}
				break;
//			case "main.contextClassLoaderSource":
//				try {
//					ds.setContextClassLoaderSource(value);
//				} catch (PropertyVetoException e) {
//					// TODO Auto-generated catch block
//					throw new Exception(e);
//				}
//				break;
			case "main.dataSourceName":
				ds.setDataSourceName(value);
				break;
			case "main.debugUnreturnedConnectionStackTraces":
				ds.setDebugUnreturnedConnectionStackTraces(Boolean
						.parseBoolean(value));
				break;
			case "main.checkoutTimeout":
				ds.setCheckoutTimeout(Integer.parseInt(value));
				break;
			case "main.driverClass":
				try {
					ds.setDriverClass(value);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					throw new Exception(e);
				}
				break;
			case "main.factoryClassLocation":
				ds.setFactoryClassLocation(value);
				break;
			case "main.forceIgnoreUnresolvedTransactions":
				ds.setForceIgnoreUnresolvedTransactions(Boolean
						.parseBoolean(value));
				break;
//			case "main.forceUseNamedDriverClass":
//				ds.setForceUseNamedDriverClass(Boolean.parseBoolean(value));
//				break;
			case "main.idleConnectionTestPeriod":
				ds.setIdleConnectionTestPeriod(Integer.parseInt(value));
				break;
			case "main.initialPoolSize":
				ds.setInitialPoolSize(Integer.parseInt(value));
				break;
			case "main.jdbcUrl":
				ds.setJdbcUrl(value);
				break;
			case "main.maxAdministrativeTaskTime":
				ds.setMaxAdministrativeTaskTime(Integer.parseInt(value));
				break;
			case "main.maxConnectionAge":
				ds.setMaxConnectionAge(Integer.parseInt(value));
				break;
			case "main.maxIdleTime":
				ds.setMaxIdleTime(Integer.parseInt(value));
				break;
			case "main.maxIdleTimeExcessConnections":
				ds.setMaxIdleTimeExcessConnections(Integer.parseInt(value));
				break;
			case "main.maxPoolSize":
				ds.setMaxPoolSize(Integer.parseInt(value));
				break;
			case "main.maxStatements":
				ds.setMaxStatements(Integer.parseInt(value));
				break;
			case "main.maxStatementsPerConnection":
				ds.setMaxStatementsPerConnection(Integer.parseInt(value));
				break;
			case "main.minPoolSize":
				ds.setMinPoolSize(Integer.parseInt(value));
				break;
			case "main.numHelperThreads":
				ds.setNumHelperThreads(Integer.parseInt(value));
				break;
			case "main.overrideDefaultUser":
				ds.setOverrideDefaultUser(value);
				break;
			case "main.overrideDefaultPassword":
				ds.setOverrideDefaultPassword(value);
				break;
			case "main.password":
				ds.setPassword(value);
				break;
			case "main.preferredTestQuery":
				ds.setPreferredTestQuery(value);
				break;
//			case "main.privilegeSpawnedThreads":
//				ds.setPrivilegeSpawnedThreads(Boolean.parseBoolean(value));
//				break;
			case "main.propertyCycle":
				ds.setPropertyCycle(Integer.parseInt(value));
				break;
			case "main.statementCacheNumDeferredCloseThreads":
				ds.setStatementCacheNumDeferredCloseThreads(Integer
						.parseInt(value));
				break;
			case "main.testConnectionOnCheckin":
				ds.setTestConnectionOnCheckin(Boolean.parseBoolean(value));
				break;
			case "main.testConnectionOnCheckout":
				ds.setTestConnectionOnCheckout(Boolean.parseBoolean(value));
				break;
			case "main.unreturnedConnectionTimeout":
				ds.setUnreturnedConnectionTimeout(Integer.parseInt(value));
				break;
			case "main.user":
				ds.setUser(value);
				break;

			default:
				break;
			}
		}
	}
	private static void setTestClientDsProperties(ComboPooledDataSource ds,
			Properties properties) throws Exception {
		Enumeration<Object> e1 = properties.keys();
		while (e1.hasMoreElements()) {
			String key = (String) e1.nextElement();
			String value = (String) properties.getProperty(key);
			switch (key) {
			case "testClient.acquireIncrement":
				ds.setAcquireIncrement(Integer.parseInt(value));
				break;
			case "testClient.acquireRetryAttempts":
				ds.setAcquireRetryAttempts(Integer.parseInt(value));
				break;
			case "testClient.acquireRetryDelay":
				ds.setAcquireRetryDelay(Integer.parseInt(value));
				break;
			case "testClient.autoCommitOnClose":
				ds.setAutoCommitOnClose(Boolean.parseBoolean(value));
				break;
			case "testClient.automaticTestTable":
				ds.setAutomaticTestTable(value);
				break;
			case "testClient.breakAfterAcquireFailure":
				ds.setBreakAfterAcquireFailure(Boolean.parseBoolean(value));
				break;
			case "testClient.connectionCustomizerClassName":
				ds.setConnectionCustomizerClassName(value);
				break;
			case "testClient.connectionTesterClassName":
				try {
					ds.setConnectionTesterClassName(value);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					throw new Exception(e);
				}
				break;
//			case "testClient.contextClassLoaderSource":
//				try {
//					ds.setContextClassLoaderSource(value);
//				} catch (PropertyVetoException e) {
//					// TODO Auto-generated catch block
//					throw new Exception(e);
//				}
//				break;
			case "testClient.dataSourceName":
				ds.setDataSourceName(value);
				break;
			case "testClient.debugUnreturnedConnectionStackTraces":
				ds.setDebugUnreturnedConnectionStackTraces(Boolean
						.parseBoolean(value));
				break;
			case "testClient.checkoutTimeout":
				ds.setCheckoutTimeout(Integer.parseInt(value));
				break;
			case "testClient.driverClass":
				try {
					ds.setDriverClass(value);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					throw new Exception(e);
				}
				break;
			case "testClient.factoryClassLocation":
				ds.setFactoryClassLocation(value);
				break;
			case "testClient.forceIgnoreUnresolvedTransactions":
				ds.setForceIgnoreUnresolvedTransactions(Boolean
						.parseBoolean(value));
				break;
//			case "testClient.forceUseNamedDriverClass":
//				ds.setForceUseNamedDriverClass(Boolean.parseBoolean(value));
//				break;
			case "testClient.idleConnectionTestPeriod":
				ds.setIdleConnectionTestPeriod(Integer.parseInt(value));
				break;
			case "testClient.initialPoolSize":
				ds.setInitialPoolSize(Integer.parseInt(value));
				break;
			case "testClient.jdbcUrl":
				ds.setJdbcUrl(value);
				break;
			case "testClient.maxAdministrativeTaskTime":
				ds.setMaxAdministrativeTaskTime(Integer.parseInt(value));
				break;
			case "testClient.maxConnectionAge":
				ds.setMaxConnectionAge(Integer.parseInt(value));
				break;
			case "testClient.maxIdleTime":
				ds.setMaxIdleTime(Integer.parseInt(value));
				break;
			case "testClient.maxIdleTimeExcessConnections":
				ds.setMaxIdleTimeExcessConnections(Integer.parseInt(value));
				break;
			case "testClient.maxPoolSize":
				ds.setMaxPoolSize(Integer.parseInt(value));
				break;
			case "testClient.maxStatements":
				ds.setMaxStatements(Integer.parseInt(value));
				break;
			case "testClient.maxStatementsPerConnection":
				ds.setMaxStatementsPerConnection(Integer.parseInt(value));
				break;
			case "testClient.minPoolSize":
				ds.setMinPoolSize(Integer.parseInt(value));
				break;
			case "testClient.numHelperThreads":
				ds.setNumHelperThreads(Integer.parseInt(value));
				break;
			case "testClient.overrideDefaultUser":
				ds.setOverrideDefaultUser(value);
				break;
			case "testClient.overrideDefaultPassword":
				ds.setOverrideDefaultPassword(value);
				break;
			case "testClient.password":
				ds.setPassword(value);
				break;
			case "testClient.preferredTestQuery":
				ds.setPreferredTestQuery(value);
				break;
//			case "testClient.privilegeSpawnedThreads":
//				ds.setPrivilegeSpawnedThreads(Boolean.parseBoolean(value));
//				break;
			case "testClient.propertyCycle":
				ds.setPropertyCycle(Integer.parseInt(value));
				break;
			case "testClient.statementCacheNumDeferredCloseThreads":
				ds.setStatementCacheNumDeferredCloseThreads(Integer
						.parseInt(value));
				break;
			case "testClient.testConnectionOnCheckin":
				ds.setTestConnectionOnCheckin(Boolean.parseBoolean(value));
				break;
			case "testClient.testConnectionOnCheckout":
				ds.setTestConnectionOnCheckout(Boolean.parseBoolean(value));
				break;
			case "testClient.unreturnedConnectionTimeout":
				ds.setUnreturnedConnectionTimeout(Integer.parseInt(value));
				break;
			case "testClient.user":
				ds.setUser(value);
				break;

			default:
				break;
			}
		}
	}
	private static void setTestCloudDsProperties(ComboPooledDataSource ds,
			Properties properties) throws Exception {
		Enumeration<Object> e1 = properties.keys();
		while (e1.hasMoreElements()) {
			String key = (String) e1.nextElement();
			String value = (String) properties.getProperty(key);
			switch (key) {
			case "testCloud.acquireIncrement":
				ds.setAcquireIncrement(Integer.parseInt(value));
				break;
			case "testCloud.acquireRetryAttempts":
				ds.setAcquireRetryAttempts(Integer.parseInt(value));
				break;
			case "testCloud.acquireRetryDelay":
				ds.setAcquireRetryDelay(Integer.parseInt(value));
				break;
			case "testCloud.autoCommitOnClose":
				ds.setAutoCommitOnClose(Boolean.parseBoolean(value));
				break;
			case "testCloud.automaticTestTable":
				ds.setAutomaticTestTable(value);
				break;
			case "testCloud.breakAfterAcquireFailure":
				ds.setBreakAfterAcquireFailure(Boolean.parseBoolean(value));
				break;
			case "testCloud.connectionCustomizerClassName":
				ds.setConnectionCustomizerClassName(value);
				break;
			case "testCloud.connectionTesterClassName":
				try {
					ds.setConnectionTesterClassName(value);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					throw new Exception(e);
				}
				break;
//			case "testCloud.contextClassLoaderSource":
//				try {
//					ds.setContextClassLoaderSource(value);
//				} catch (PropertyVetoException e) {
//					// TODO Auto-generated catch block
//					throw new Exception(e);
//				}
//				break;
			case "testCloud.dataSourceName":
				ds.setDataSourceName(value);
				break;
			case "testCloud.debugUnreturnedConnectionStackTraces":
				ds.setDebugUnreturnedConnectionStackTraces(Boolean
						.parseBoolean(value));
				break;
			case "testCloud.checkoutTimeout":
				ds.setCheckoutTimeout(Integer.parseInt(value));
				break;
			case "testCloud.driverClass":
				try {
					ds.setDriverClass(value);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					throw new Exception(e);
				}
				break;
			case "testCloud.factoryClassLocation":
				ds.setFactoryClassLocation(value);
				break;
			case "testCloud.forceIgnoreUnresolvedTransactions":
				ds.setForceIgnoreUnresolvedTransactions(Boolean
						.parseBoolean(value));
				break;
//			case "testCloud.forceUseNamedDriverClass":
//				ds.setForceUseNamedDriverClass(Boolean.parseBoolean(value));
//				break;
			case "testCloud.idleConnectionTestPeriod":
				ds.setIdleConnectionTestPeriod(Integer.parseInt(value));
				break;
			case "testCloud.initialPoolSize":
				ds.setInitialPoolSize(Integer.parseInt(value));
				break;
			case "testCloud.jdbcUrl":
				ds.setJdbcUrl(value);
				break;
			case "testCloud.maxAdministrativeTaskTime":
				ds.setMaxAdministrativeTaskTime(Integer.parseInt(value));
				break;
			case "testCloud.maxConnectionAge":
				ds.setMaxConnectionAge(Integer.parseInt(value));
				break;
			case "testCloud.maxIdleTime":
				ds.setMaxIdleTime(Integer.parseInt(value));
				break;
			case "testCloud.maxIdleTimeExcessConnections":
				ds.setMaxIdleTimeExcessConnections(Integer.parseInt(value));
				break;
			case "testCloud.maxPoolSize":
				ds.setMaxPoolSize(Integer.parseInt(value));
				break;
			case "testCloud.maxStatements":
				ds.setMaxStatements(Integer.parseInt(value));
				break;
			case "testCloud.maxStatementsPerConnection":
				ds.setMaxStatementsPerConnection(Integer.parseInt(value));
				break;
			case "testCloud.minPoolSize":
				ds.setMinPoolSize(Integer.parseInt(value));
				break;
			case "testCloud.numHelperThreads":
				ds.setNumHelperThreads(Integer.parseInt(value));
				break;
			case "testCloud.overrideDefaultUser":
				ds.setOverrideDefaultUser(value);
				break;
			case "testCloud.overrideDefaultPassword":
				ds.setOverrideDefaultPassword(value);
				break;
			case "testCloud.password":
				ds.setPassword(value);
				break;
			case "testCloud.preferredTestQuery":
				ds.setPreferredTestQuery(value);
				break;
//			case "testCloud.privilegeSpawnedThreads":
//				ds.setPrivilegeSpawnedThreads(Boolean.parseBoolean(value));
//				break;
			case "testCloud.propertyCycle":
				ds.setPropertyCycle(Integer.parseInt(value));
				break;
			case "testCloud.statementCacheNumDeferredCloseThreads":
				ds.setStatementCacheNumDeferredCloseThreads(Integer
						.parseInt(value));
				break;
			case "testCloud.testConnectionOnCheckin":
				ds.setTestConnectionOnCheckin(Boolean.parseBoolean(value));
				break;
			case "testCloud.testConnectionOnCheckout":
				ds.setTestConnectionOnCheckout(Boolean.parseBoolean(value));
				break;
			case "testCloud.unreturnedConnectionTimeout":
				ds.setUnreturnedConnectionTimeout(Integer.parseInt(value));
				break;
			case "testCloud.user":
				ds.setUser(value);
				break;

			default:
				break;
			}
		}
	}
	private static void setDevCloudDsProperties(ComboPooledDataSource ds,
			Properties properties) throws Exception {
		Enumeration<Object> e1 = properties.keys();
		while (e1.hasMoreElements()) {
			String key = (String) e1.nextElement();
			String value = (String) properties.getProperty(key);
			switch (key) {
			case "devCloud.acquireIncrement":
				ds.setAcquireIncrement(Integer.parseInt(value));
				break;
			case "devCloud.acquireRetryAttempts":
				ds.setAcquireRetryAttempts(Integer.parseInt(value));
				break;
			case "devCloud.acquireRetryDelay":
				ds.setAcquireRetryDelay(Integer.parseInt(value));
				break;
			case "devCloud.autoCommitOnClose":
				ds.setAutoCommitOnClose(Boolean.parseBoolean(value));
				break;
			case "devCloud.automaticTestTable":
				ds.setAutomaticTestTable(value);
				break;
			case "devCloud.breakAfterAcquireFailure":
				ds.setBreakAfterAcquireFailure(Boolean.parseBoolean(value));
				break;
			case "devCloud.connectionCustomizerClassName":
				ds.setConnectionCustomizerClassName(value);
				break;
			case "devCloud.connectionTesterClassName":
				try {
					ds.setConnectionTesterClassName(value);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					throw new Exception(e);
				}
				break;
//			case "devCloud.contextClassLoaderSource":
//				try {
//					ds.setContextClassLoaderSource(value);
//				} catch (PropertyVetoException e) {
//					// TODO Auto-generated catch block
//					throw new Exception(e);
//				}
//				break;
			case "devCloud.dataSourceName":
				ds.setDataSourceName(value);
				break;
			case "devCloud.debugUnreturnedConnectionStackTraces":
				ds.setDebugUnreturnedConnectionStackTraces(Boolean
						.parseBoolean(value));
				break;
			case "devCloud.checkoutTimeout":
				ds.setCheckoutTimeout(Integer.parseInt(value));
				break;
			case "devCloud.driverClass":
				try {
					ds.setDriverClass(value);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					throw new Exception(e);
				}
				break;
			case "devCloud.factoryClassLocation":
				ds.setFactoryClassLocation(value);
				break;
			case "devCloud.forceIgnoreUnresolvedTransactions":
				ds.setForceIgnoreUnresolvedTransactions(Boolean
						.parseBoolean(value));
				break;
//			case "devCloud.forceUseNamedDriverClass":
//				ds.setForceUseNamedDriverClass(Boolean.parseBoolean(value));
//				break;
			case "devCloud.idleConnectionTestPeriod":
				ds.setIdleConnectionTestPeriod(Integer.parseInt(value));
				break;
			case "devCloud.initialPoolSize":
				ds.setInitialPoolSize(Integer.parseInt(value));
				break;
			case "devCloud.jdbcUrl":
				ds.setJdbcUrl(value);
				break;
			case "devCloud.maxAdministrativeTaskTime":
				ds.setMaxAdministrativeTaskTime(Integer.parseInt(value));
				break;
			case "devCloud.maxConnectionAge":
				ds.setMaxConnectionAge(Integer.parseInt(value));
				break;
			case "devCloud.maxIdleTime":
				ds.setMaxIdleTime(Integer.parseInt(value));
				break;
			case "devCloud.maxIdleTimeExcessConnections":
				ds.setMaxIdleTimeExcessConnections(Integer.parseInt(value));
				break;
			case "devCloud.maxPoolSize":
				ds.setMaxPoolSize(Integer.parseInt(value));
				break;
			case "devCloud.maxStatements":
				ds.setMaxStatements(Integer.parseInt(value));
				break;
			case "devCloud.maxStatementsPerConnection":
				ds.setMaxStatementsPerConnection(Integer.parseInt(value));
				break;
			case "devCloud.minPoolSize":
				ds.setMinPoolSize(Integer.parseInt(value));
				break;
			case "devCloud.numHelperThreads":
				ds.setNumHelperThreads(Integer.parseInt(value));
				break;
			case "devCloud.overrideDefaultUser":
				ds.setOverrideDefaultUser(value);
				break;
			case "devCloud.overrideDefaultPassword":
				ds.setOverrideDefaultPassword(value);
				break;
			case "devCloud.password":
				ds.setPassword(value);
				break;
			case "devCloud.preferredTestQuery":
				ds.setPreferredTestQuery(value);
				break;
//			case "devCloud.privilegeSpawnedThreads":
//				ds.setPrivilegeSpawnedThreads(Boolean.parseBoolean(value));
//				break;
			case "devCloud.propertyCycle":
				ds.setPropertyCycle(Integer.parseInt(value));
				break;
			case "devCloud.statementCacheNumDeferredCloseThreads":
				ds.setStatementCacheNumDeferredCloseThreads(Integer
						.parseInt(value));
				break;
			case "devCloud.testConnectionOnCheckin":
				ds.setTestConnectionOnCheckin(Boolean.parseBoolean(value));
				break;
			case "devCloud.testConnectionOnCheckout":
				ds.setTestConnectionOnCheckout(Boolean.parseBoolean(value));
				break;
			case "devCloud.unreturnedConnectionTimeout":
				ds.setUnreturnedConnectionTimeout(Integer.parseInt(value));
				break;
			case "devCloud.user":
				ds.setUser(value);
				break;

			default:
				break;
			}
		}
	}
	
	public Connection getMainConnection() throws SQLException {
		try {
			if (mainDs == null) {
				openMainDatasource();
			}
		} catch (Exception e) {
		}
		Connection conn = mainDs.getConnection();
		return conn;
	}
	public Connection getTestClientConnection() throws SQLException {
		try {
			if (testClientDs == null) {
				openTestClientDatasource();
			}
		} catch (Exception e) {
		}
		Connection conn = testClientDs.getConnection();
		return conn;
	}
	public Connection getTestCloudConnection() throws SQLException {
		try {
			if (testCloudDs == null) {
				openTestCloudDatasource();
			}
		} catch (Exception e) {
		}
		Connection conn = testCloudDs.getConnection();
		return conn;
	}
	public Connection getDevCloudConnection() throws SQLException {
		try {
			if (devCloudDs == null) {
				openDevCloudDatasource();
			}
		} catch (Exception e) {
		}
		Connection conn = devCloudDs.getConnection();
		return conn;
	}
	
	public static void close(Connection con,Statement state){
		try {
			if(con != null){
				con.close();
			}
			if(state != null){
				state.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
