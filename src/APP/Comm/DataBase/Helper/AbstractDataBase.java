package APP.Comm.DataBase.Helper;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import APP.Comm.BLL.AppUser;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.CommandType;
import APP.Comm.DataBase.DotNet.DbCommand;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.DataBase.Parameter.AppDbType;
import APP.Comm.Util.EntitySql;
import APP.Comm.Util.EntityUtil;
import APP.Comm.Util.HLogger;
import APP.Comm.View.GridRequestParameters;
import APP.Model.BaseEntity;
import APP.Model.Base.Ext.SYS_DB_LOG_EXT;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * 
 * 修改人： ypf <br/>
 * 修改时间：2014年10月29日 11:28 <br/>
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * 
 * @创建人 ypf
 * @版本
 */
public abstract class AbstractDataBase implements IDataBase {
	// add by jiankang.song on 2013-01-038
	// 定义本次操作是否需要启动事务控制
	public boolean _needTransaction;
	public boolean _needOpen = false;

	public java.util.List<BaseEntity> LogList = new java.util.ArrayList<BaseEntity>();
	public AppUser appUser = null;
	public String SystemName = "";

	// 定义是否执行过增删改操作，当下面增删改的方法被调用时，置为true
	// 请宝峰实现此逻辑，建议在基类的实现，ORACLE等具体实现类直接继承这个逻辑
	private boolean _ifUpdated;

	// 由具体实现类提升至抽象类
	// 施建龙
	// 2013年2月15日10:50:22
	protected Connection connection; // Connection(连接) 对象

	private ConnectionParameter conParameter = null;

	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	protected static SimpleDateFormat sdfLong = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	protected static DBConManager dbConManager = new DBConManager();

	public DbCommand DbCommand = null;
	/**
	 * comments:默认为ASCII
	 * 
	 * sjl modify 2013-10-15上午11:24:41
	 */
	private String charSet = "ASCII";

	public final boolean getIfUpdated() {
		return _ifUpdated;
	}

	public final void setIfUpdated(boolean value) {
		_ifUpdated = value;

		if (_ifUpdated) {
			setIfProcessed(true);
		}
	}

	// 定义是否执行过数据库操作，当下面与数据库交互的方法被调用时，置为true
	// 请宝峰实现此逻辑
	private boolean privateIfProcessed;

	public final boolean getIfProcessed() {
		return privateIfProcessed;
	}

	public final void setIfProcessed(boolean value) {
		privateIfProcessed = value;
	}

	// add by jiankang.song on 2013-01-03
	// 定义构造函数，初始化参数
	public AbstractDataBase() {
		_needTransaction = false;
		setIfUpdated(false);
		setIfProcessed(false);
		DbCommand = new DbCommand();
		DbCommand.setDbCharSet(charSet);
	}

	public Connection getConnection() {
		// throw new NotImplementedException();
		return connection;
	}

	// public void Open(String connString) throws BaseBllException {
	// // return;
	// throw new BaseBllException("此方法废弃！");
	// }

	public void Close() throws BaseBllException {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			throw new BaseBllException(e);
		}
	}

	/**
	 * 插入新数据 执行Insert操作时调用
	 * 
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	public int Save(String sqlString, List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		// sjl add
		// 2013年4月18日13:00:34
		// HLogger.Info(sqlString);
		// 统一在collectlog中输出sql语句至日志文件
		return 0;
	}

	/**
	 * 更细数据 执行Update操作时调用
	 * 
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	public int Update(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		// sjl add
		// 2013年4月18日13:00:34
		// HLogger.Info(sqlString);
		// 统一在collectlog中输出sql语句至日志文件
		return 0;
	}

	/**
	 * 删除数据 执行Delete操作时调用
	 * 
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	public int Delete(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		// Edit >>> zhangbf 2013-04-18
		// HLogger.Info(sqlString + AppDbParameters.ToString());
		// HLogger.Info(sqlString);
		// Edit <<< zhangbf 2013-04-18
		// 统一在collectlog中输出sql语句至日志文件
		return 0;
	}

	/**
	 * 执行存储过程时调用
	 * 
	 * @param sqlString
	 *            存储过程名
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	public Map<String, Object> ExecProcedure(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		setIfProcessed(true);

		// 收集执行语句
		// CollectLogString(sqlString, AppDbParameters, SystemName);
		//
		// PrepareParameter(CommandType.StoredProcedure, sqlString,
		// AppDbParameters);
		//
		// return DbCommand.ExecuteNonQuery();
		return null;
	}

	/**
	 * 获取某行一个字段的值，可以是任意类型 当需要获取某一个具体的字段值时调用 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 *            要执行的Sql语句
	 * @param AppDbParameters
	 *            IList AppDbParameter>类型的参数列表
	 * @return object
	 * @throws BaseBllException
	 */
	public Object QueryObject(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		// sjl add
		// 2013年4月18日13:00:34
		// HLogger.Info(sqlString);
		// 统一在collectlog中输出sql语句至日志文件
		return 0;
	}

	public Blob QueryBlob(String sqlString, List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		return null;
	}

	public void OpenBase() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}

	public void OpenAppRd() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}

	public void OpenAppRW() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}

	public void OpenAppC() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}

	public void OpenAppSys() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}

	public void OpenAppHD() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}

	public void OpenApplicationRd() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}
	public void OpenAppHPP() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}
	public void OpenHPLUSYW() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}
	
	public void OpenHPLUSYeWu() throws BaseBllException {//李添加的
		throw new BaseBllException("not null Exception");
	}
	
	//医院+监控
	public void OpenHPLUSMC() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}
	
	
	public void OpenAppME() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}
	public void OpenAppHDC() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}
	
	public void OpenPatientDB() throws BaseBllException {
		throw new BaseBllException("not null Exception");
	}
	/**
	 * 执行一般查询，返回一个HrpDataTable类型数据集 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 *            Sql语句
	 * @param AppDbParameters
	 *            IList AppDbParameter>类型参数列表
	 * @return AppDataTable
	 * @throws BaseBllException
	 */
	public AppDataTable Query(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		// sjl add
		// 2013年4月18日13:00:34
		// HLogger.Info(sqlString);
		// 统一在collectlog中输出sql语句至日志文件
		return null;
	}

	/**
	 * 翻页查询用 返回一个HrpDataTable类型数据集 创建者：张宝锋 创建时间：2012-12-28/
	 * 
	 * @param sqlString
	 *            sqlString必须是普通查询语句，且不能出现Order By！返回要查询的数据集
	 * @param primaryKey
	 *            primaryKey必须是select count(*)
	 *            格式，并且这两个sql语句的where条件必须相同！返回满足查询条件的总记录行数
	 * @param AppDbParameters
	 *            IList AppDbParameter 类型的参数列表
	 * @param gridRequestParameters
	 *            GridRequestParameters类型的列表
	 * @return 返回HrpDataTable
	 * @throws BaseBllException
	 */
	public AppDataTable QueryByPage(String sqlString, String primaryKey,
			java.util.List<AppDbParameter> AppDbParameters,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		// sjl add
		// 2013年4月18日13:00:34
		// HLogger.Info(sqlString);
		// 统一在collectlog中输出sql语句至日志文件
		return null;
	}

	/**
	 * 作用：返回一个具体的实体对象列表 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param baseEntity
	 *            BaseEntity对象类型,Model实体类型
	 * @param sqlString
	 *            Sql查询语句
	 * @param AppDbParameters
	 *            IList AppDbParameter>类型的参数列表
	 * @return 返回AppEntitys
	 * @throws BaseBllException
	 */
	public AppEntitys QueryEntity(Class entityType, String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		return null;
	}

	public AppEntitys QueryEntityWithoutLog(Class type, String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		return null;
	}

	/**
	 * 翻页查询用，返回一个具体的实体对象列表 创建者：张宝锋 创建时间：2012-12-28
	 * 
	 * @param baseEntity
	 *            BaseEntity对象类型,Model实体类型
	 * @param sqlString
	 *            Sql查询语句
	 * @param AppDbParameters
	 *            IList AppDbParameter>类型的参数列表
	 * @param gridRequestParameters
	 *            GridRequestParameters类型参数列表
	 * @return
	 * @throws BaseBllException
	 */
	public AppEntitys QueryEntityByPage(Class entityType, String sqlString,
			String primaryKey, java.util.List<AppDbParameter> AppDbParameters,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		return null;
	}

	/**
	 * 添加readOnly属性 施建龙 2013年3月4日11:45:16
	 * 
	 * @param entityType
	 * @param sqlString
	 * @param primaryKey
	 * @param AppDbParameters
	 * @param gridRequestParameters
	 * @param readOnly
	 * @return
	 * @throws BaseBllException
	 * @throws NumberFormatException
	 */
	public AppEntitys QueryEntityByPage(Class entityType, String sqlString,
			String primaryKey, java.util.List<AppDbParameter> AppDbParameters,
			GridRequestParameters gridRequestParameters, boolean readOnly)
			throws NumberFormatException, BaseBllException {
		throw new NotImplementedException();
	}

	/**
	 * 添加ReadOnly属性 施建龙 2013年3月4日11:45:40
	 * 
	 * @param entityType
	 * @param sqlString
	 * @param AppDbParameters
	 * @param readOnly
	 * @return
	 * @throws BaseBllException
	 */
	public AppEntitys QueryEntity(Class entityType, String sqlString,
			java.util.List<AppDbParameter> AppDbParameters, boolean readOnly)
			throws BaseBllException {
		throw new NotImplementedException();
	}

	/**
	 * 保存实体至数据库 编写：施建龙 时间：2013年1月11日 11:49:30
	 * 
	 * @param entity
	 * @return
	 * @throws BaseBllException
	 */
	public final int Save(BaseEntity entity) throws BaseBllException {
		EntitySql entitySql = null;
		entitySql = EntityUtil.CreateSaveEntitySql(entity);
		HLogger.info("Save Entity sql:" + entitySql.getSqlString());
		HLogger.info("Save Entity parameters:" + entitySql.getAppDbParameters());
		// sjl add
		// 2013年4月18日13:00:34
		// HLogger.Info(entitySql.SqlString);
		// 统一在collectlog中输出sql语句至日志文件
		int count = this.Save(entitySql.getSqlString(),
				entitySql.getAppDbParameters());
		// 清除更新列
		// 去掉属性清除操作，前端页面可重复save
		// sjl 2013年4月18日13:07:57
		// entity.ClearUpdateAttribute();
		return count;
	}

	/**
	 * 更新实体至数据库 编写：施建龙 时间：2013年1月11日 11:49:30
	 * 
	 * @param entity
	 * @return
	 * @throws BaseBllException
	 */
	public int Update(BaseEntity entity) throws BaseBllException {
		EntitySql entitySql = null;
		entitySql = EntityUtil.CreateUpdateEntitySql(entity);

		// sjl add
		// 2013年4月18日13:00:34
		// HLogger.Info(entitySql.SqlString);
		// 统一在collectlog中输出sql语句至日志文件
		int count = this.Update(entitySql.getSqlString(),
				entitySql.getAppDbParameters());
		// 当更新记录返回影响记录数==0时，抛出异常
		if (count == 0) {
			throw new BaseBllException("本次未更新成功" + entity.GetTableName()
					+ "数据：" + entity.GetId() + "。");
		}
		// 清除更新列
		entity.ClearUpdateAttribute();
		return count;
	}

	public int Delete(BaseEntity entity) {
		throw new NotImplementedException();
	}

	/**
	 * 刷新实体 编写：施建龙 时间：2013年1月11日 11:49:30
	 * 
	 * @param entity
	 * @return
	 * @throws BaseBllException
	 */
	public BaseEntity Refresh(BaseEntity entity) throws BaseBllException {
		EntitySql entitySql = null;
		entitySql = EntityUtil.CreateSelectEntitySql(entity);

		AppEntitys hrpEntitys = null;

		hrpEntitys = QueryEntity(entity.getClass(), entitySql.getSqlString(),
				entitySql.getAppDbParameters());
		return hrpEntitys.getEntityList().get(0);
	}

	/**
	 * 批量新增 施建龙
	 * 
	 * @param entitys
	 *            需要提交的记录总数
	 * @param rows
	 *            一次提交的记录数
	 * @return
	 * @throws BaseBllException
	 */
	public int BulkInsert(java.util.List<BaseEntity> entitys, int rows)
			throws BaseBllException {
		throw new NotImplementedException();
	}

	/**
	 * 施建龙 2013年2月8日10:43:08
	 * 
	 * @param entitys
	 * @param rows
	 * @return
	 * @throws BaseBllException
	 */
	public int BulkUpdate(java.util.List<BaseEntity> entitys, int rows)
			throws BaseBllException {
		throw new NotImplementedException();
	}

	/**
	 * 转换字符串至date类型 施建龙 2013年2月8日7:42:48
	 * 
	 * @param value
	 * @return
	 */
	public String StringToDate(String value) {
		return "DATE_FORMAT(" + value + ",'%Y-%m-%d')";// "TO_DATE('" + value +
														// "','YYYY-MM-DD')";
	}

	public String StringToDateTime(String value) {
		return "DATE_FORMAT('" + value + "','%Y-%M-%D %H:%i:%s')";
	}

	public int Save(java.util.List<BaseEntity> entitys) {
		throw new NotImplementedException();
	}

	public int Update(java.util.List<BaseEntity> entitys) {
		throw new NotImplementedException();
	}

	public int Delete(java.util.List<BaseEntity> entitys) {
		throw new NotImplementedException();
	}

	/**
	 * 说明：设置是否需要启动事务控制。对于纯查询的的操作如报表统计等，不需要开启事务 创建者：宋建康 创建时间：2013-01-03
	 */
	public final void NeedTransaction(boolean needTransaction) {
		_needTransaction = needTransaction;
	}

	public final boolean NeedTransaction() {
		return _needTransaction;
	}

	public final void NeedOpen(boolean need) {
		this._needOpen = need;
	}

	public final boolean NeedOpen() {
		return _needOpen;
	}

	/**
	 * 收集用户执行的sql语句
	 * 
	 * @param sqlString
	 *            sql语句
	 * @param AppDbParameters
	 *            IList AppDbParameter型参数列表
	 * @throws BaseBllException
	 */
	public final void CollectLogString(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters, String systemName)
			throws BaseBllException {
		SYS_DB_LOG_EXT sysDbLog = new SYS_DB_LOG_EXT();

		sysDbLog.setUSER_ID(appUser != null ? appUser.getLoginName() : "GUEST");
		sysDbLog.setLOG_TYPE(GetOperateTypeString(sqlString));
		sysDbLog.setLOG_CONTENT(GetLogContent(sqlString, AppDbParameters));
		// 输出带有参数具体值的sql语句至日志文件
		// sjl
		// 2013年4月19日12:01:14
		HLogger.Info(sysDbLog.getLOG_CONTENT());

		sysDbLog.setCREATOR_NAME(appUser != null ? appUser.getUserName() : "游客");
		sysDbLog.setCREATE_TIME(ServerDate()); // 服务器时间
		sysDbLog.setDEPT_NAME(appUser != null ? appUser.getDeptName() : "");
		sysDbLog.setSYSTEM_NAME(systemName);

		LogList.add(sysDbLog);
	}

	/**
	 * 截取sql语句第一个关键字，判断数据操作类型，并返回字符串 创建人：张宝锋 创建时间：2013-01-07
	 * 
	 * @param sqlString
	 *            Sql语句
	 * @return
	 */
	public final String GetOperateTypeString(String sqlString) {
		String tmpStr = sqlString.trim().substring(0, 6).toUpperCase();
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (tmpStr)
		// ORIGINAL LINE: case "INSERT":
		if (tmpStr.equals("INSERT")) {
			return "I";
		}
		// ORIGINAL LINE: case "UPDATE":
		else if (tmpStr.equals("UPDATE")) {
			return "U";
		}
		// ORIGINAL LINE: case "DELETE":
		else if (tmpStr.equals("DELETE")) {
			return "D";
		}
		// ORIGINAL LINE: case "SELECT":
		else if (tmpStr.equals("SELECT")) {
			return "S";
		} else {
			return "P";
		}
	}

	/**
	 * 提取IList AppDbParameter 参数列表中参数值，替换掉sql语句中的参数变量（Oracle中:No或SQL中：@No）
	 * 创建人：张宝锋 创建时间：2013-01-07
	 * 
	 * @param sqlString
	 *            sql语句
	 * @param AppDbParameters
	 *            IList AppDbParameter 类型参数列表
	 * @return
	 */
	public final String GetLogContent(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters) {
		if (AppDbParameters == null) {
			return sqlString;
		}
		/**
		 * 任务： 描述：解决NullPointer，当传入sqlString为null的时候，会出现NullPointer 人员：施建龙
		 * 时间：2014年11月19日下午2:12:01
		 **/
		String tmpSql = sqlString == null ? "" : sqlString.toUpperCase();

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
				// tmpString = tmpSql.replace(parName, "'" + parValue + "'");
				tmpSql = tmpSql.replace((parName + " "), "'" + parValue + "' ");
				tmpSql = tmpSql.replace((parName + ","), "'" + parValue + "',");
				tmpSql = tmpSql.replace((parName + ")"), "'" + parValue + "')");
				// 如果参数未sql语句的最后项，并且开发人员没写写空格
				// 施建龙
				// 2014年1月24日11:24:11
				if (tmpSql.endsWith(parName)) {
					tmpSql = tmpSql.substring(0,
							tmpSql.length() - parName.length())
							+ "'" + parValue + "'";
				}
				// tmpSql = tmpString;
			}
		}

		// 将最终字符串中的连续的空格转换成一个空格
		// return new Regex("[\\s]+").Replace(tmpSql, " ").trim();
		return tmpSql;
	}

	/**
	 * comments:根据SQL语句，提取参数名称和顺序
	 * 
	 * sjl modify 2013-10-14下午1:15:03
	 * 
	 * @throws BaseBllException
	 */
	public final ArrayList<String> getSqlParameters(String sqlString)
			throws BaseBllException {
		ArrayList<String> theReturn = new ArrayList<String>();

		String tmpSql = sqlString.toUpperCase();

		String[] parameters = tmpSql.split(":");
		int index = 0;
		for (String par : parameters) {
			// 数字第一项忽略
			// 施建龙
			// 2013年10月14日13:50:36
			if (index == 0) {
				index++;
				continue;
			}
			String parName = null;
			//
			int brackIndex = -1;
			int blankIndex = -1;
			int dotIndex = -1;
			int okIndex = -1;
			brackIndex = par.indexOf(")");
			dotIndex = par.indexOf(",");
			blankIndex = par.indexOf(" ");
			// 使用排序Sort自动进行排序
			SortedSet<Integer> set = new TreeSet<Integer>();
			if (brackIndex >= 0)
				set.add(brackIndex);
			if (dotIndex >= 0)
				set.add(dotIndex);
			if (blankIndex >= 0)
				set.add(blankIndex);
			if (set.size() > 0) {
				okIndex = set.first();
			} else {
				okIndex = par.length();
			}

			parName = par.substring(0, okIndex).trim();
			// 忽略空格
			if (parName.length() > 0) {
				theReturn.add(parName);
			}
			index++;
		}
		// 将最终字符串中的连续的空格转换成一个空格
		// return new Regex("[\\s]+").Replace(tmpSql, " ").trim();
		return theReturn;
	}

	/**
	 * 获取数据库服务器时间 创建者：张宝锋 创建时间：2013-01-17
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public Date ServerDate() throws BaseBllException {
		throw new NotImplementedException();
	}

	/**
	 * 获取数据库中最新的ID
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public long GetNewID() throws BaseBllException {
		throw new NotImplementedException();
	}

	public long GetNewID(String seqName) throws BaseBllException {
		throw new NotImplementedException();
	}

	public final void WriteLog() throws BaseBllException {
		WriteLog(LogList);
	}

	public void SaveLog(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		return;
	}

	/**
	 * 向数据库日志表中插入操作日志 创建者：张宝锋 创建时间：2013-01-07
	 * 
	 * @param sysDbLogList
	 * @throws BaseBllException
	 */
	private void WriteLog(java.util.List<BaseEntity> sysDbLogList)
			throws BaseBllException {
		if (sysDbLogList.isEmpty()) {
			return;
		}

		for (BaseEntity baseEntity : sysDbLogList) {
			SYS_DB_LOG_EXT sysDbLog = (SYS_DB_LOG_EXT) baseEntity;

			StringBuilder strSql = new StringBuilder();
			strSql.append(" insert into sys_db_log ");
			strSql.append("  ( USER_ID,LOG_TYPE,LOG_CONTENT");
			strSql.append("    ,CREATOR_NAME,CREATE_TIME ");
			strSql.append("   )");
			strSql.append(" Values  ");
			strSql.append("  (  :USER_ID,:LOG_TYPE ,:LOG_CONTENT");
			strSql.append("     ,:CREATOR_NAME,:CREATE_TIME ");
			strSql.append("  ) ");

			java.util.List<AppDbParameter> AppDbParameters = new java.util.ArrayList<AppDbParameter>();
			AppDbParameters.add(new AppDbParameter("USER_ID", AppDbType.Char,
					sysDbLog.getUSER_ID()));
			AppDbParameters.add(new AppDbParameter("LOG_TYPE", AppDbType.Char,
					sysDbLog.getLOG_TYPE()));
			AppDbParameters.add(new AppDbParameter("LOG_CONTENT",
					AppDbType.Char, sysDbLog.getLOG_CONTENT()));
			AppDbParameters.add(new AppDbParameter("CREATOR_NAME",
					AppDbType.Char, sysDbLog.getCREATOR_NAME()));
			AppDbParameters.add(new AppDbParameter("CREATE_TIME",
					AppDbType.DateTime, sysDbLog.getCREATE_TIME()));
			SaveLog(strSql.toString(), AppDbParameters);
		}

	}

	/**
	 * 检查一个表中某个字段内容是否已经存在 若存在，返回true；否则返回false 创建者：张宝锋 创建时间：2013-01-07
	 * 
	 * @param tableName
	 *            要检查的表名
	 * @param columnName
	 *            要检查的字段名
	 * @param columnValue
	 *            要检查的字段值
	 * @return 存在返回True，否则返回False
	 * @throws BaseBllException
	 */
	public boolean CheckExists(String tableName, String columnName,
			String columnValue) throws BaseBllException {
		String value = columnValue.trim().toUpperCase();
		StringBuilder strSql = new StringBuilder();
		strSql.append(" SELECT COUNT(" + columnName + ") ");
		strSql.append(" FROM " + tableName + "");
		strSql.append(" WHERE 1=1 AND UPPER(" + columnName + ")='" + value
				+ "'");

		// AppDbParameter AppDbParameters[] = new AppDbParameter[] { new
		// AppDbParameter(
		// "columnValue", columnValue.trim().toUpperCase()) };

		// Object obj = QueryObject(strSql.toString(),
		// Arrays.asList(AppDbParameters));
		Object obj = QueryObject(strSql.toString(), null);
		// 当查询结果值大于0时，说明满足条件的记录已经存在，返回true,否则返回false
		return Integer.parseInt(obj.toString()) > 0 ? true : false;
	}

	/**
	 * 检查一个表中某个字段内容是否已经存在 若存在，返回true；否则返回false 创建者：张宝锋 创建时间：2013-01-07
	 * 
	 * @param tableName
	 *            要检查的表名
	 * @param columnName
	 *            要检查的字段名,若是多个字段，则此处任意指定一个字段即可
	 * @param dictionary
	 *            要检查的字段键、值字典
	 * @return 存在返回True，否则返回False
	 * @throws BaseBllException
	 */
	public boolean CheckExists(String tableName, String columnName,
			java.util.Map<String, String> dictionary) throws BaseBllException {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" SELECT COUNT(" + columnName + ") ");
		strSql.append(" FROM " + tableName + "");
		strSql.append(" WHERE 1=1 ");

		java.util.ArrayList<AppDbParameter> AppDbParameters = new java.util.ArrayList<AppDbParameter>();

		for (Map.Entry<String, String> variable : dictionary.entrySet()) {
			strSql.append(" AND UPPER(" + variable.getKey().trim() + ")=:"
					+ variable.getKey().trim().toUpperCase() + "");
			AppDbParameters.add(new AppDbParameter(variable.getKey().trim(),
					variable.getValue().trim().toUpperCase()));
		}

		Object obj = QueryObject(strSql.toString(), AppDbParameters);
		// 当查询结果值大于0时，说明满足条件的记录已经存在，返回true,否则返回false
		return Integer.parseInt(obj.toString()) > 0 ? true : false;
	}

	/**
	 * 检查一个表中某个字段内容是否已经存在 若存在，返回true；否则返回false 创建者：张宝锋 创建时间：2013-03-12
	 * 
	 * @param tableName
	 *            要检查的表名
	 * @param columnName
	 *            要检查的字段名,若是多个字段，则此处任意指定一个字段即可
	 * @param dictionary
	 *            要检查的字段键、值字典
	 * @return 存在返回True，否则返回False
	 * @throws BaseBllException
	 */
	public boolean CheckExists(String tableName,
			java.util.Map<String, String> dictionary) throws BaseBllException {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" SELECT COUNT(1) ");
		strSql.append(" FROM " + tableName + "");
		strSql.append(" WHERE 1=1 ");

		java.util.ArrayList<AppDbParameter> AppDbParameters = new java.util.ArrayList<AppDbParameter>();

		for (Map.Entry<String, String> variable : dictionary.entrySet()) {
			strSql.append(" AND UPPER(" + variable.getKey().trim() + ")=:"
					+ variable.getKey().trim().toUpperCase() + "");
			AppDbParameters.add(new AppDbParameter(variable.getKey().trim(),
					variable.getValue().trim().toUpperCase()));
		}

		Object obj = QueryObject(strSql.toString(), AppDbParameters);
		// 当查询结果值大于0时，说明满足条件的记录已经存在，返回true,否则返回false
		return Integer.parseInt(obj.toString()) > 0 ? true : false;
	}

	/**
	 * 检查一个表中某个记录是否已经存在 若存在，返回true；否则返回false 创建者：张宝锋 创建时间：2013-09-05
	 * 
	 * @param tableName
	 *            要检查的表名
	 * @param dictionary
	 *            要检查的字段键、值字典
	 * @return 存在返回True，否则返回False
	 * @throws BaseBllException
	 */
	public final boolean CheckExistsOriginal(String tableName,
			java.util.Map<String, String> dictionary) throws BaseBllException {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" SELECT COUNT(1) ");
		strSql.append(" FROM " + tableName + "");
		strSql.append(" WHERE 1=1 ");

		java.util.ArrayList<AppDbParameter> AppDbParameters = new java.util.ArrayList<AppDbParameter>();

		for (Map.Entry<String, String> variable : dictionary.entrySet()) {
			strSql.append(" AND UPPER(" + variable.getKey().trim() + ")=:"
					+ variable.getKey().trim().toUpperCase() + "");
			AppDbParameters.add(new AppDbParameter(variable.getKey().trim()
					.toUpperCase(), variable.getValue()));
		}

		Object obj = QueryObject(strSql.toString(), AppDbParameters);
		// 当查询结果值大于0时，说明满足条件的记录已经存在，返回true,否则返回false
		return Integer.parseInt(obj.toString()) > 0 ? true : false;
	}

	/**
	 * 测试当前数据库连接字符串是否正确 调用本方法之前，确保DataBase对象的连接字符串已经设置 施建龙 2013年2月25日13:44:07
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	public final boolean TestConnection() throws BaseBllException {
		boolean result = false;
		try {
			// 需要关注是否会影响调用测试连接的业务
			// 因此，使用本方法，必须要保证是使用新创建的DataBase
			HLogger.info("TestConnection Start");
			this.OpenBase();
			result = true;
		} catch (Throwable e) {
			HLogger.Error(e);
			result = false;
		} finally {
			if (this.connection != null) {
				try {
					Close();
				} catch (Throwable e1) {
					HLogger.Error(e1);
				}
			}
		}
		return result;
	}

	/**
	 * 说明：根据外键名称，查找包含此外键的表名称 用于校验某条数据是否可以删除，用于业务校验 作者：张宝锋 创建日期：2013-03-11
	 * 
	 * @param foreignKeyName
	 * @return
	 * @throws BaseBllException
	 */
	public final String GetForeignTableName(String foreignKeyName)
			throws BaseBllException {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" select b.comments ");
		strSql.append("  from ALL_TAB_COMMENTS b ");
		strSql.append(" where b.TABLE_NAME =(select TABLE_NAME from user_cons_columns cl ");
		strSql.append("                     where cl.constraint_name = :foreignKeyName) ");
		java.util.List<AppDbParameter> AppDbParameters = new java.util.ArrayList<AppDbParameter>();
		AppDbParameters.add(new AppDbParameter("foreignKeyName", foreignKeyName
				.trim()));
		Object obj = this.QueryObject(strSql.toString(), AppDbParameters);
		return obj != null ? obj.toString() : "";
	}

	// Edit Add >>> zhangbf 2013-11-15 12:36:27
	/**
	 * 说明：根据索引名称，查找包含此索引的表名称 用于防止重复插入数据 作者：张宝锋 创建日期：2013-11-15
	 * 
	 * @param foreignKeyName
	 * @return
	 */
	public final String GetTableNameByIndexName(String indexName)
			throws BaseBllException {
		StringBuilder strSql = new StringBuilder();
		strSql.append(" SELECT MAX(B.COMMENTS) COMMENTS ");
		strSql.append("  FROM ALL_TAB_COMMENTS B ");
		strSql.append(" WHERE B.TABLE_NAME =(SELECT MAX(TABLE_NAME) FROM USER_IND_COLUMNS CL ");
		strSql.append("                     WHERE CL.INDEX_NAME = :INDEX_NAME) ");
		java.util.List<AppDbParameter> AppDbParameters = new java.util.ArrayList<AppDbParameter>();
		AppDbParameters.add(new AppDbParameter("INDEX_NAME", indexName.trim()));
		Object obj = this.QueryObject(strSql.toString(), AppDbParameters);
		return obj != null ? obj.toString() : "";
	}

	// Edit Add <<< zhangbf 2013-11-15 12:36:27

	/**
	 * 说明：获取数据权限之 核算单元权限 (key:RECKON_UNIT_CODE) , 只对一般用户进行数据权限校验 作者：左仁帅
	 * 时间：2013年3月24日 16:46:15
	 * 
	 * @param key
	 * @return AND EXISTS (SELECT a.RECKON_UNIT_CODE FROM (SELECT
	 *         unit.RECKON_UNIT_CODE,per.USER_ID FROM DR_RECKON_UNIT unit LEFT
	 *         JOIN SYS_USER_RECKON_PERM per ON
	 *         per.RECKON_UNIT_ID=unit.RECKON_UNIT_ID WHERE
	 *         per.RECKON_UNIT_ID=unit.RECKON_UNIT_ID AND per.PERM_FLAG=1 ) a
	 *         WHERE a.USER_ID='" + base.appUser.LoginName + "')
	 */
	public final String GetDataAccessReckonUnit(String key) {
		if (("USER").equals(appUser.getUserIdEntity())) {
			String reckonUnit = " AND EXISTS (" + "\r\n"
					+ "                    SELECT " + "\r\n"
					+ "                        P.RECKON_UNIT_ID" + "\r\n"
					+ "                    FROM " + "\r\n"
					+ "                        V_SYS_USER_RU_PERM P" + "\r\n"
					+ "                    WHERE " + "\r\n"
					+ "                    P.USER_ID = '"
					+ appUser.getLoginName() + "'" + "\r\n"
					+ "                    AND RECKON_UNIT_ID =" + key + " "
					+ "\r\n" + "                    )    " + "\r\n"
					+ "                ";
			return reckonUnit;
		} else {
			return "";
		}
	}

	/**
	 * 说明：获取数据权限之 核算项目权限 (key:RECKON_ITEM_CODE),只对一般用户进行数据权限校验 作者：左仁帅
	 * 时间：2013年3月24日 16:46:15
	 * 
	 * @return AND EXISTS ( SELECT RECKON_ITEM_CODE FROM SYS_RECKON_ITEM_PERM
	 *         WHERE PERM_FLAG=1 AND USER_ID='" + base.appUser.LoginName + "')
	 */
	public final String GetDataAccessReckonItem(String key) {
		if (("USER").equals(appUser.getUserIdEntity())) {
			String reckonItem = " AND EXISTS (" + "\r\n"
					+ "                SELECT" + "\r\n"
					+ "                    RECKON_ITEM_CODE" + "\r\n"
					+ "                FROM" + "\r\n"
					+ "                    V_SYS_USER_RI_PERM" + "\r\n"
					+ "                WHERE USER_ID='"
					+ appUser.getLoginName() + "'" + "\r\n"
					+ "                AND RECKON_ITEM_CODE= " + key + "    "
					+ "\r\n" + "                )" + "\r\n" + "            ";
			return reckonItem;
		} else {
			return "";
		}
	}

	/**
	 * 说明：获取数据权限之 成本项目权限 (key:COST_ITEM_NO) ,只对一般用户进行数据权限校验 作者：左仁帅 时间：2013年3月24日
	 * 16:46:15
	 * 
	 * @return AND EXISTS ( SELECT COST_ITEM_NO FROM SYS_COST_ITEM_PERM WHERE
	 *         PERM_FLAG=1 AND USER_ID='" + base.appUser.LoginName + "')
	 */
	public final String GetDataAccessCostItem(String key) {
		if (("USER").equals(appUser.getUserIdEntity())) {
			String costItem = "" + "\r\n" + "                  AND EXISTS ("
					+ "\r\n" + "                    SELECT" + "\r\n"
					+ "                        COST_ITEM_NO" + "\r\n"
					+ "                    FROM" + "\r\n"
					+ "                        V_SYS_USER_CI_PERM" + "\r\n"
					+ "                    WHERE" + "\r\n"
					+ "                    USER_ID='" + appUser.getLoginName()
					+ "'" + "\r\n" + "                    AND COST_ITEM_NO="
					+ key + " " + "\r\n" + "                    )" + "\r\n"
					+ "                    ";
			return costItem;
		} else {
			return "";
		}

	}

	/**
	 * 施建龙 2013年10月29日14:49:00
	 * 
	 * @return
	 */
	public final String GetDatabaseName() {
		return this.SystemName;
	}

	protected ConnectionParameter getConParameter() {
		return conParameter;
	}

	protected void setConParameter(ConnectionParameter conParameter) {
		this.conParameter = conParameter;
	}

	/**
	 * comments:对子类方法进行重构，java平台中异构数据库在使用jdbc调用时没有区别
	 * 
	 * sjl modify 2013-10-5下午7:22:12
	 */
	protected void PrepareParameter(CommandType cmdType, String sqlString,
			List<AppDbParameter> commandParameters) {

	}

	@Override
	public String getCharSet() {
		return charSet;
	}

	@Override
	public void setCharSet(String charSet) {
		this.charSet = charSet;
		//
		if (DbCommand != null) {
			this.DbCommand.setDbCharSet(charSet);
		}
	}

	/**
	 * 说明：获取存储过程的out类型返回
	 * 
	 * @param sqlString
	 *            存储过程
	 * @param AppDbParameters
	 * @return
	 */
	public java.util.Map<String, Object> ExecResultProcedure(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		throw new BaseBllException("未实现此方法！");
	}
	/**
	 * 任务： 描述：通过提供failer方法完成事务的回滚，close默认提交事务 人员：施建龙 时间：2015年1月25日上午10:39:36
	 * edit KYEEAPPC-2900 APP底层DB操作类开放事务提交接口 李君强 2015年7月30日10:09:59
	 **/
	public void commit() throws BaseBllException {
		try {
			if (this.connection != null
					&& this.connection.getAutoCommit() == false) {
				this.connection.commit();
			}
		} catch (SQLException e) {
			throw new BaseBllException(e);
		}
	}

	/**
	 * 任务： 描述：通过failer方法回滚事务 人员：施建龙 时间：2015年1月29日下午7:04:47
	 **/
	@Override
	public void failure() throws BaseBllException {
		try {
			if (connection != null && !connection.isClosed()) {

				if (this.connection.getAutoCommit() == false) {
					this.connection.rollback();
				}
				connection.close();
			}
		} catch (SQLException e) {
			throw new BaseBllException(e);
		}
	}

	/**
	 * 事务回滚
	 */
	@Override
	public void rollback() throws BaseBllException {
		try {
			if (connection != null && !connection.isClosed()) {

				if (this.connection.getAutoCommit() == false) {
					this.connection.rollback();
				}
			}
		} catch (SQLException e) {
			throw new BaseBllException(e);
		}
	}

	protected void resetTransactionMode() throws BaseBllException {

		try {
			if (NeedTransaction()) {
				if (connection != null) {
					if (connection.getAutoCommit() != false) {
						connection.setAutoCommit(false);
					}
				}
			}
			else {
				if (connection != null) {
					if (connection.getAutoCommit() == false) {
						connection.setAutoCommit(true);
					}
				}
			}

		} catch (SQLException e) {
			throw new BaseBllException(e);
		}
	}

}