package APP.Comm.DataBase.DotNet;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.jtds.jdbc.JtdsConnection;
import net.sourceforge.jtds.jdbc.JtdsPreparedStatement;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.Util.CharSetUtil;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;

import com.util.UnicodeUtil;

/**
 * comments:模拟DotNet
 * 
 * sjl modify 2013-10-5上午10:27:10
 */
public class DbCommand {

	public String CommandText = null;
	public CommandType CommandType;
	public Connection Connection = null;
	public Collection<OracleParameter> Parameters = new ArrayList<OracleParameter>();

	public ArrayList<String> parameterOrders = new ArrayList<String>();
	// 数据库字符集
	private String dbCharSet = null;
	// 默认显示字符集
	private String displayCharSet = "GBK";

	// 输出含有具体值的SQL语句，用以日志输出
	// 施建龙
	// 2014年2月15日18:59:13
	private String sqlLogString = "";
	
	/**
	 * comments:模拟DotNet的方法，使用DotNet的方法名
	 * 
	 * sjl modify 2013-10-5下午10:24:59
	 */
	/**
	 *任务：
	 *描述：至针对特定的表进行数据过滤
	 *C_ACCOUNT_USER    
	 *C_ACCOUNT_USERID_VS_IDNO
	 *当transferUnicodeFlag=true时，执行Unicode转换
	 *人员：施建龙
	 *时间：2014年11月5日下午7:01:13
	 **/
	private boolean transferUnicodeFlag=false;
	
	private ArrayList<String> unicodeTable=new ArrayList<>();
	
	public DbCommand() {
		super();
		// TODO Auto-generated constructor stub
		unicodeTable.add("C_ACCOUNT_USER");
		unicodeTable.add("C_ACCOUNT_USERID_VS_IDNO");
	}
	
	public Object ExecuteScalar() throws BaseBllException {
		PreparedStatement st = null;
		Object theReturn = null;
		ResultSet rs = null;
		//edit add start huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 11:25:26
		String sqlString=this.getSqlLogString();
		//edit add end huangpeng KYEEAPPMAINTENANCE-1162 2016年12月12日 11:25:32
		try {
			if (!DotNetToJavaStringHelper.isNullOrEmpty(this.getSqlLogString())) {
				HLogger.info("ExecuteScalar Start!");
				HLogger.warn("SQL-"+this.getSqlLogString());
			}
			/**
			 * comments:对SQL语句进行转码，主要处理SQL语句中的硬编码中文
			 * 
			 * sjl modify 2013-11-11下午3:47:16
			 */
			// 字符集转码放入prepare方法中
			// CommandText=CharSetUtil.transfer(CommandText, displayCharSet,
			// dbCharSet);

			st = prepare(Connection, CommandText);
			rs = st.executeQuery();
			if (rs.next()) {
				theReturn = rs.getObject(1);
			}
			//
			String columnClassType = null;
			int columnScale = 0;
			int columnPrecision = 0;
			if (theReturn != null) {
				columnClassType = theReturn.getClass().getName().toUpperCase();
				theReturn = transferDb2UiValue(theReturn, columnClassType,
						columnPrecision, columnScale, dbCharSet, displayCharSet);
			}
		} catch (Exception e) {
//			throw new BaseBllException(e);
			throw new BaseBllException(CharSetUtil.transfer(e.getMessage(),
					dbCharSet, displayCharSet));
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				throw new BaseBllException(e);
			}
		}
		HLogger.info("ExecuteScalar end!");
		return theReturn;
	}
	
	public Blob ExecuteBlob() throws BaseBllException {
		PreparedStatement st = null;
		Blob theReturn = null;
		ResultSet rs = null;
		try {
			if (!DotNetToJavaStringHelper.isNullOrEmpty(this.getSqlLogString())) {
				HLogger.info("ExecuteScalar Start!");
				HLogger.warn("SQL-"+this.getSqlLogString());
			}
			st = prepare(Connection, CommandText);
			rs = st.executeQuery();
			if (rs.next()) {
				theReturn = rs.getBlob(1);
			}
			
		} catch (Exception e) {
			throw new BaseBllException(CharSetUtil.transfer(e.getMessage(),
					dbCharSet, displayCharSet));
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				throw new BaseBllException(e);
			}
		}
		HLogger.info("ExecuteScalar end!");
		return theReturn;
	}

	/**
	 * comments:模拟DotNet的方法，使用DotNet的方法名,执行非查询操作
	 * 
	 * sjl modify 2013-10-5下午10:27:01
	 */
	public int ExecuteNonQuery() throws BaseBllException {
		PreparedStatement st = null;
		int theReturn = 0;
		String sqlString=this.getSqlLogString();
		try {
			if (!DotNetToJavaStringHelper.isNullOrEmpty(this.getSqlLogString())) {
				HLogger.info("ExecuteNonQuery Start!");
				HLogger.warn("SQL-"+this.getSqlLogString());
			}
			/**
			 *任务：
			 *描述：判断SQL语句是否有需要转码的数据库表
			 *人员：施建龙
			 *时间：2014年11月5日下午7:17:03
			 **/
			transferUnicodeFlag=checkSQLUnicdoe(CommandText);
			
			st = prepare(Connection, CommandText);
			theReturn = st.executeUpdate();

		} catch (Exception e) {
			throw new BaseBllException(CharSetUtil.transfer(e.getMessage(),
					dbCharSet, displayCharSet));
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					throw new BaseBllException(e);
				}
			}
		}
		HLogger.info("ExecuteNonQuery End!");
		return theReturn;
	}

	/**
	 * comments:批量执行Insert或者Update、Delete
	 * 
	 * sjl modify 2014年1月2日下午6:45:50
	 */
	public int ExecuteNonQueryBatch(java.util.List<String> sqlStrings)
			throws BaseBllException {
		Statement st = null;
		int theReturn = 0;
		try {
			HLogger.info("ExecuteNonQuery Start!");
			//
			st = Connection.createStatement();
			for (String sqlString : sqlStrings) {
				sqlString = prepare(Connection, null, sqlString);
				st.addBatch(sqlString);
			}
			// theReturn = st.executeUpdate();
			st.executeBatch();
		} catch (Exception e) {
//			throw new BaseBllException(e);
			throw new BaseBllException(CharSetUtil.transfer(e.getMessage(),
					dbCharSet, displayCharSet));
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					throw new BaseBllException(e);
				}
			}
		}
		HLogger.info("ExecuteNonQuery End!");
		return theReturn;
	}

	public DataTable ExecuteQuery() throws BaseBllException {
		HLogger.info("ExecuteQuery Start!");
		if (CommandText == null || CommandText.trim().length() == 0) {
			throw new BaseBllException("SQL语句不正确:" + CommandText);
		}
		
		/**
		 *任务：
		 *描述：判断SQL语句是否有需要转码的数据库表
		 *人员：施建龙
		 *时间：2014年11月5日下午7:17:03
		 **/
		transferUnicodeFlag=checkSQLUnicdoe(CommandText);
		
		// sjl
		// 转换了2此，此处的转换去掉
		// 2014年1月17日19:54:47
		// CommandText=CharSetUtil.transfer(CommandText, displayCharSet,
		// dbCharSet);

		DataTable result = this.getDataTable(CommandText,
				(OracleParameter[]) Parameters.toArray(new OracleParameter[0]));
		HLogger.info("ExecuteQuery End!");
		return result;
	}

	/**
	 * comments:调用存储过程，同时可处理存储过程的返回值 返回值类型为Map，Key为参数名称，Value为Object类型
	 * 
	 * sjl modify 2013-10-22下午4:49:37
	 */
	public Map<String, Object> execProcedure() throws BaseBllException {
		Map<String, Object> result = new HashMap<String, Object>();
		CallableStatement proc = null;
		StringBuilder sqlString = new StringBuilder();
		try {
			sqlString.append("{ call ");
			sqlString.append(CommandText).append("(");
			for (String para : parameterOrders) {
				sqlString.append("?,");
			}
			if (sqlString.charAt(sqlString.length() - 1) == ',') {
				sqlString.deleteCharAt(sqlString.length() - 1);
			}
			sqlString.append(")} ");
			proc = Connection.prepareCall(sqlString.toString());
			setParameters(proc);
			proc.execute();
			//
			for (OracleParameter parameter : Parameters) {
				if (parameter.getInOrOut() == 1) {
					result.put(parameter.getParName(),
							proc.getObject(parameter.getParName()));
				}
			}
			// return cmd.ExecuteNonQuery();
		} catch (Exception e) {
			throw new BaseBllException(e);
		} finally {
			if (proc != null) {
				try {
					proc.close();
				} catch (SQLException e) {
					throw new BaseBllException(e);
				}
			}
		}

		return result;
	}

	/**
	 * comments:引用自网络
	 * 
	 * sjl modify 2013-10-5上午10:28:09
	 * 
	 * @throws BaseBllException
	 */
	private DataTable getDataTable(String sql, OracleParameter[] p)
			throws BaseBllException {
		// Connection conn = DB.createConn();
		PreparedStatement pst = null;
		DataTable dataTable = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		List<DataRow> rows = new ArrayList<DataRow>();// 表所有行集合
		List<DataColumn> cols = null;// 行所有列集合
		DataRow dataRow = null; // 单独一行
		DataColumn dataColumn = null;// 单独一列
		String sqlString=this.getSqlLogString();
		try {
			pst = prepare(Connection, sql);
			// addSqlParameter(pst, p);
			setParameters(pst);
			/**
			 * 说明：日志中只记录有效信息 作者：施建龙 时间：2014年3月13日-下午2:17:24
			 */
			if (!DotNetToJavaStringHelper.isNullOrEmpty(this.getSqlLogString())) {
				HLogger.info("Start DB Query!"+"\n"
							+"SQL-"+this.getSqlLogString());
			}
			rs = pst.executeQuery();
			HLogger.info("End DB Query!");
			rsmd = rs.getMetaData();
			// 列JAVA类型
			String columnClassType = null;
			int columnScale = 0;
			int columnPrecision = 0;
			// 此处开始循环读数据，每次往表格中插入一行记录
			while (rs.next()) {
				// 初始化行集合，
				// 初始化列集合
				cols = new ArrayList<DataColumn>();

				// 此处开始列循环，每次向一行对象插入一列
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String columnName = CharSetUtil.transfer(
							rsmd.getColumnName(i), dbCharSet, displayCharSet);
					// String
					// columnDbType=rsmd.getColumnTypeName(i).toUpperCase();
					columnScale = rsmd.getScale(i);
					columnPrecision = rsmd.getPrecision(i);
					Object value = rs.getObject(i);
					if (value != null) {
						columnClassType = value.getClass().getName()
								.toUpperCase();
						value = transferDb2UiValue(value, columnClassType,
								columnPrecision, columnScale, dbCharSet,
								displayCharSet);
					}
					// 初始化单元列
					dataColumn = new DataColumn(columnName, value);
					// 将列信息加入列集合
					cols.add(dataColumn);
				}
				// 初始化单元行
				dataRow = new DataRow(cols);
				// 将行信息降入行结合
				rows.add(dataRow);
			}
			// 得到数据表
			dataTable = new DataTable(rows);
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				dataTable.Columns.add(CharSetUtil.transfer(
						rsmd.getColumnName(i), dbCharSet, displayCharSet));
			}

		} catch (Exception e) {
			// e.printStackTrace();
			// HLogger.error(e);
//			throw new BaseBllException(e);
			throw new BaseBllException(CharSetUtil.transfer(e.getMessage(),
					dbCharSet, displayCharSet));
		} finally {
			try {
				if (rs != null) {
					// if(!rs.isClosed())
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				throw new BaseBllException(e);
			}
		}
		return dataTable;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-20下午1:28:04
	 */
	private Object transferDb2UiValue(Object value, String columnClassType,
			int columnPrecision, int columnScale, String sourceCharSet,
			String destCharSet) {
		if (columnClassType.indexOf("DECIMAL") >= 0) {
			// 小数位数为0，则使用long类型
			columnPrecision = ((BigDecimal) value).precision();
			columnScale = ((BigDecimal) value).scale();
			if (columnScale <= 0 && columnPrecision <= 12) {
				try {
					if (columnPrecision > 8) {
						value = Long.valueOf(value + "");
					} else {
						value = Integer.valueOf(value + "").intValue();
					}
				} catch (Exception e) {
					HLogger.error(e);
				}
			}
		} else if (columnClassType.indexOf("STRING") >= 0) {
			/**
			 * comments:源字符集不为GBK时，执行字符集转换
			 * 
			 * sjl modify 2013-10-15下午12:40:49
			 */
			// && columnDbType.indexOf("CHAR")>=0
			/**
			 *任务：
			 *描述：如果数据库中的数据存在UNICODE格式，则进行转换，数据库中的UNICODE格式为/u
			 *	   严格添加约束条件：
			 *人员：施建龙
			 *时间：2014年11月5日下午6:39:24
			 **/
			
			String tempValue=value.toString().trim();
			if(transferUnicodeFlag){
				if(checkUnicodeValue(tempValue)){
					tempValue=UnicodeUtil.decodeUnicode(tempValue.replace("/u", "\\u"));
					HLogger.Warn("读取ETL输入的数据出现乱码："+value.toString());
                    recordError(sqlLogString,"读取ETL输入的数据出现乱码："+value+",转码后值:"+tempValue);
				}
			}
			// if (transferCharset) {
			value = CharSetUtil.transfer(tempValue, dbCharSet,
					displayCharSet);
			// }

		}
		// else if (columnClassType.indexOf("TIMESTAMP") >= 0 ||
		// columnClassType.indexOf("DATE") >= 0){
		// // 日期类型进行转换，转换为Hrp的Date扩展类型
		// value=new Date().setActualDate((java.util.Date)value);
		//
		// }
		return value;
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-20下午2:49:43
	 */
	private Object transferUi2DbValue(Object value, String columnClassType,
			int columnPrecision, int columnScale, String sourceCharSet,
			String destCharSet) {
		if (columnClassType.indexOf("STRING") >= 0) {
			/**
			 * comments:源字符集不为GBK时，执行字符集转换
			 * 
			 * sjl modify 2013-10-15下午12:40:49
			 */
			// && columnDbType.indexOf("CHAR")>=0
			/**
			 *任务：
			 *描述：
			 *人员：施建龙
			 *时间：2014年11月5日下午6:45:42
			 **/
			/**
			 *任务：
			 *描述：如果数据库中的数据存在UNICODE格式，则进行转换，数据库中的UNICODE格式为/u
			 *人员：施建龙
			 *时间：2014年11月5日下午6:39:24
			 **/
			String tempValue=value.toString().trim();
			if(transferUnicodeFlag){
				if(checkUnicodeValue(tempValue)){
					tempValue=UnicodeUtil.decodeUnicode(tempValue.replace("/u", "\\u"));
					HLogger.Warn("前端输入数据出现乱码："+value.toString());
					recordError(sqlLogString,"前端输入数据出现乱码："+value+",转码后值:"+tempValue);
				}
			}
			value = CharSetUtil.transfer(tempValue,
					displayCharSet, dbCharSet);

		}
		return value;
	}

	/**
	 *任务：
	 *描述：记录错误信息至数据库异常信息表中  KYEEAPP-974
	 *人员：施建龙   
	 *时间：2014年11月5日下午8:40:47
	 **/
	private void recordError(String sql,Object value) {
		//输出到数据库
		String stackTraceString=null;		stackTraceString=HLogger.warnStackTrace(value.toString());
		String sqlString=null;
		String sql2=sql.replace("'", "''");  
//		KYEEAPP-982-记录数据unicode日志
		sqlString="INSERT INTO C_SYS_ERROR_LOG(ERROR_SQL,TRACE_LOG,CREATE_TIME) VALUES(?,?,NOW())";

		PreparedStatement st=null;
		try {
			HLogger.warn(sqlString);
			st = Connection.prepareStatement(sqlString);
			st.setString(1, sql);
//			st.setString(1, sql2);
			st.setString(2, " ___ "+value+" ___ "+stackTraceString);
			

			st.executeUpdate();
			Connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(st!=null){
					st.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-20下午1:25:55
	 */
	private boolean isTransferCharset(String sourceCharSet, String destCharSet) {
		boolean transferCharSet = false;
		if (this.dbCharSet != null && !this.dbCharSet.equalsIgnoreCase("GBK")) {
			transferCharSet = true;
		}
		return transferCharSet;
	}

	/**
	 * comments:引用自网络
	 * 
	 * sjl modify 2013-10-5上午10:27:38
	 */
	@Deprecated
	private void addSqlParameter(PreparedStatement ps, OracleParameter[] p)
			throws Exception {
		/**
		 * comments:用setParameters方法替换，此方法废弃
		 * 
		 * sjl modify 2013-10-14下午5:58:21
		 */
		// for (int j = 0; j < p.length; j++) {
		// wl(p[j].getValue() + "--" + p[j].getType() + "--" + j);
		// if (p[j].getParType().equals(OracleType.Int16) ||
		// p[j].getParType().equals(OracleType.Int32)) {
		// ps.setInt(j + 1, Integer.parseInt(p[j].getValue()+""));
		// }
		// if (p[j].getParType().equals(OracleType.AnsiString)) {
		// ps.setString(j + 1, p[j].getValue()+"");
		// }
		// // if (p[j].getParType().equals(OracleType.)) {
		// // ps.setBoolean(j + 1, p[j].getBoolValue());
		// // }
		// if (p[j].getParType().equals(OracleType.DateTime)) {
		// ps.setDate(j + 1, (java.sql.Date)p[j].getValue());
		// }
		// if (p[j].getParType().equals(OracleType.Blob)) {
		// // ps.setBlob(j + 1, p[j].getBlobValue());
		// ps.setString(j + 1, p[j].getValue()+"");
		// }
		// }
	}

	/**
	 * comments:实际样例，引用自网络
	 * 
	 * sjl modify 2013-10-5上午10:26:20
	 */
	// public DataTable getByParentId(int pId) {
	// String sql = "select * from kpxz where fbh=? order by kpxzsx asc";
	// SqlParameter[] p = new SqlParameter[1];
	// p[0] = new SqlParameter("int", pId);
	// return getDataTable(sql, p);
	// }

	/**
	 * comments:
	 * 
	 * sjl modify 2013-10-5上午10:30:32
	 * 
	 * @throws SQLException
	 * @throws BaseBllException
	 */
	private PreparedStatement prepare(Connection conn, String sql)
			throws SQLException, BaseBllException {

		String sqlString = null;
		sqlString = parseSqlString(sql);
		/**
		 * comments:对SQL语句进行转码，主要处理SQL语句中的硬编码中文
		 * 
		 * sjl modify 2013-11-11下午3:47:16
		 */
		if (conn instanceof JtdsConnection) {
			dbCharSet = "GBK";
		}
		sqlString = CharSetUtil.transfer(sqlString, displayCharSet, dbCharSet);
		conn.setAutoCommit(false);
		PreparedStatement st = conn.prepareStatement(sqlString);
		setParameters(st);
		return st;
	}

	/**
	 * comments:针对批量执行提取的方法
	 * 
	 * sjl modify 2014年1月2日下午6:50:50
	 */
	private String prepare(Connection conn, Statement st, String sql)
			throws SQLException, BaseBllException {

		String sqlString = null;
		sqlString = parseSqlString(sql);
		/**
		 * comments:对SQL语句进行转码，主要处理SQL语句中的硬编码中文
		 * 
		 * sjl modify 2013-11-11下午3:47:16
		 */
		if (conn instanceof JtdsConnection) {
			dbCharSet = "GBK";
		}
		conn.setAutoCommit(false);
		sqlString = CharSetUtil.transfer(sqlString, displayCharSet, dbCharSet);

		// PreparedStatement st = conn.prepareStatement(sqlString);
		// if(st==null){
		// st = conn.prepareStatement(sqlString);
		// }

		// setParameters(st);
		// return st;
		return sqlString;
	}

	/**
	 * comments: 对SQL语句的参数赋值，不能使用参数名直接传入，只能使用参数顺序号进行传递 sjl modify
	 * 2013-10-5下午10:04:33
	 * 
	 * 更新数据库时，需要对字符集进行转换 施建龙 2013年10月20日13:29:38
	 * 
	 * @throws BaseBllException
	 */
	protected void setParameters(PreparedStatement st) throws BaseBllException {
		Iterator<String> ie = null;
		ie = parameterOrders.iterator();
		String dbParameter = null;
		int i = 1;
		boolean found = false;
		boolean transferCharset = false;
		try {
			transferCharset = isTransferCharset(dbCharSet, displayCharSet);
			while (ie.hasNext()) {
				dbParameter = (String) ie.next();
				found = false;
				String columnClassType = null;
				for (OracleParameter oracleParameter : Parameters) {
					if (oracleParameter.getParName().equalsIgnoreCase(
							dbParameter)) {
						// 转换字符集
						Object value = oracleParameter.getValue();
						// 不为空时，进行字符集转换
						if (value != null) {
							columnClassType = value.getClass().getName()
									.toUpperCase();
							if (columnClassType.contains("STRING")) {
								value = transferUi2DbValue(value + "",
										columnClassType, 40, 40,
										displayCharSet, dbCharSet);
							}
							// else
							// if(columnClassType.contains("APP.Comm.DOTNET.DATE")){
							// // HRP Date类型转换为util.Date类型
							// value=((APP.Comm.DotNet.Date)value).getActualDate();
							// }
						}
						// 需识别是否为输出参数
						// 施建龙
						if (oracleParameter.getInOrOut() == 1) {
							((CallableStatement) st).registerOutParameter(
									oracleParameter.getParName(),
									oracleParameter.getReturnDataType());
						} else {
							// JtdsPreparedStatement jtds=null;
							if (st instanceof CallableStatement) {
								((CallableStatement) st).setObject(dbParameter,
										value);
							} else {
								if (st instanceof JtdsPreparedStatement) {
									((JtdsPreparedStatement) st).setObject(i,
											value);
								} else {
									st.setObject(i, value);
								}
							}
						}
						found = true;
					}
				}
				if (!found) {
					throw new BaseBllException(
							"SQL语句中的参数数量和传入的参数数量不匹配！请检查是否传入参数：" + dbParameter
									+ "的值！");
				}
				i++;
			}
		} catch (Exception e) {
			throw new BaseBllException(e);
		}
	}

	/**
	 * comments:使用正则表达式替换参数为？ 正则表达好不能很好的处理非参数字符串中包含：的情况，比如时间中的时分秒 sjl modify
	 * 2013-11-12下午5:16:15
	 */
	public String parseSqlString(String sql) {

		// String regex = "(:(//w+))";
		// String regex = "[:@][A-Za-z0-9_]+[!']";
		// Pattern p = Pattern.compile(regex);
		// Matcher m = p.matcher(sql);
		HLogger.debug("转换前SQL:" + sql);
		// String result = sql.replaceAll(regex, "?");
		StringBuffer result = new StringBuffer(sql + " ");
		Iterator<String> ie = null;
		sql += " ";
		ie = parameterOrders.iterator();
		String dbParameter = null;
		int i = 1;
		boolean found = false;
		boolean transferCharset = false;
		while (ie.hasNext()) {
			dbParameter = (String) ie.next();
			/**
			 * comments:为了不替换错误，使用参数名+特殊符号进行匹配
			 * 
			 * sjl modify 2014年1月4日下午2:30:41
			 */
			String parameter=":" + dbParameter;
			sql = sql.replace(( parameter+ " "), "? ");
			sql = sql.replace((parameter + ","), "?,");
			sql = sql.replace((parameter + ")"), "?)");
			//如果参数未sql语句的最后项，并且开发人员没写写空格
			//施建龙
			//2014年1月24日11:24:11
			if(sql.endsWith(parameter)){
				sql=sql.substring(0,sql.length()-parameter.length())+"?";
			}
		}

		HLogger.debug("转换后SQL:" + sql);

		return sql;
	}

	public String getDbCharSet() {
		return dbCharSet;
	}

	public void setDbCharSet(String charSet) {
		this.dbCharSet = charSet;
	}

	
	public String getSqlLogString() {
		return sqlLogString;
	}

	public void setSqlLogString(String sqlLogString) {
		this.sqlLogString = sqlLogString;
	}
	
	/**
	 *任务：
	 *描述：检查执行的SQL是否包含特定的转码表
	 *人员：施建龙
	 *时间：2014年11月5日下午7:20:35
	 **/
	private boolean checkSQLUnicdoe(String sql){
		Iterator<String> i1=unicodeTable.iterator();
		String tempSql=sql.toUpperCase();
		while(i1.hasNext()){
			String tempString=i1.next();
			if(tempSql.indexOf(tempString)>=0){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 *任务：
	 *描述：判断字符串是否为unicode的16进制编码
	 *人员：施建龙
	 *时间：2014年11月5日下午7:22:53
	 **/
	private boolean checkUnicodeValue(String value){
		String value2=value+"";
		boolean errorFlag=false;
		if(value2.startsWith("/u")&& value2.length()%6==0){
			String[] subValues=value2.split("/u");
			for (String unicodeValue : subValues) {
				if(unicodeValue.length()>0){
					try{
						Integer.parseInt(unicodeValue, 16);
					}catch(Exception e){
						errorFlag=true;
					}
				}
			}
		}else{
			return false;
		}
		if(!errorFlag){
			return true;
		}
		return false;
	}
	
}
