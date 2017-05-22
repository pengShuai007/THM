package APP.Comm.DataBase.Helper.Devart;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.CommandType;
import APP.Comm.DataBase.DotNet.DataTable;
import APP.Comm.DataBase.DotNet.DbCommand;
import APP.Comm.DataBase.DotNet.OracleParameter;
import APP.Comm.DataBase.DotNet.OracleType;
import APP.Comm.DataBase.Helper.AbstractDataBase;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.DataBase.Helper.AppEntitys;
import APP.Comm.DataBase.Helper.DBConManager;
import APP.Comm.DataBase.Parameter.AppDbParameter;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.EntitySql;
import APP.Comm.Util.EntityUtil;
import APP.Comm.Util.HLogger;
import APP.Comm.View.GridRequestParameters;
import APP.Model.BaseEntity;

//模块编号：<模块编号，可以引用系统设计中的模块编号>
//作用：<数据库执行类，执行各种数据访问操作>
//作者：张宝锋
//编写日期：<2012-12-27>

public class DevartOracleDbHelper extends AbstractDataBase {

	private String ConnString = "";

	public DevartOracleDbHelper() {
		super();

	}

	/**
	 * 构造函数
	 * 
	 * @param connString
	 *            连接字符串
	 */
	public DevartOracleDbHelper(String connString, String _systemName) {
		ConnString = connString;
		SystemName = _systemName;
	}



	/**
	 * 任务号：
	 * 描述：打开运维数据库
	 * 作者：liuxingping
	 * 时间：2015年5月22日下午6:13:01
	 * @throws BaseBllException
	 */
	public void OpenBase() throws BaseBllException {
		try {
			connection = DBConManager.getBaseConByDataSource();
		} catch (Exception e) {
			throw new BaseBllException(e);
		}
		this.DbCommand.Connection = connection;
	}
	
	/**
	 * 关闭数据库
	 * 
	 * @throws BaseBllException
	 */
	@Override
	public void Close() throws BaseBllException {
		try {
			if (connection != null){
				/**
				 *任务：
				 *描述：屏蔽commit，在关闭时自动commit
				 *人员：施建龙
				 *时间：2014年11月20日下午10:37:16
				 **/
				if(!connection.getAutoCommit()){
					connection.commit();
				}
				connection.close();
			}
		} catch (Exception e) {
			throw new BaseBllException(e);
		}
	}

	/**
	 * 仅执行Insert语句时调用 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 * @param commandParameters
	 * @return
	 * @throws BaseBllException
	 */
	@Override
	public int Save(String sqlString,
			java.util.List<AppDbParameter> commandParameters)
			throws BaseBllException {
		return ExecuteNonQuery(sqlString, commandParameters);
	}

	/**
	 * 仅执行Update语句时调用 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 * @param commandParameters
	 * @return
	 * @throws BaseBllException
	 */
	@Override
	public int Update(String sqlString,
			java.util.List<AppDbParameter> commandParameters)
			throws BaseBllException {
		return ExecuteNonQuery(sqlString, commandParameters);
	}

	/**
	 * 仅执行Delete语句时调用 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 * @param commandParameters
	 * @return
	 * @throws BaseBllException
	 */
	@Override
	public int Delete(String sqlString,
			java.util.List<AppDbParameter> commandParameters)
			throws BaseBllException {
		super.Delete(sqlString, commandParameters);
		return ExecuteNonQuery(sqlString, commandParameters);
	}

	/**
	 * 执行存储过程 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	@Override
	public Map<String, Object> ExecProcedure(String sqlString,
			List<AppDbParameter> AppDbParameters) throws BaseBllException {
		super.setIfProcessed(true);

		// 收集执行语句
		// base.CollectLogString(sqlString, AppDbParameters);
		// super.CollectLogString(sqlString, AppDbParameters, SystemName);

		// its Java equivalent:
		// using (OracleCommand cmd = new OracleCommand())
		// OracleCommand cmd = new OracleCommand();
		CallableStatement proc = null;
		Map<String, Object> result = null;
		try {
			PrepareParameter(CommandType.StoredProcedure, sqlString,
					AppDbParameters);
			// 前端只需要传入存储过程名称和参数即可，参数通过常规参数方式传入
			// 在此处拼装为jdbc的调用语句格式
			// 施建龙
			// 2013年10月22日16:48:05

			// super.DbCommand.parameterOrders.addAll(this
			// .getSqlParameters(sqlString));
			/**
			 * 说明： 作者：施建龙 时间：2014年3月25日-下午4:06:51
			 */
			super.DbCommand.parameterOrders.clear();
			super.DbCommand.parameterOrders
					.addAll(getParameterOrders(AppDbParameters));
			super.DbCommand.setDbCharSet(getCharSet());
			result = DbCommand.execProcedure();

			// proc = connection.prepareCall("{ call set_death_age(?, ?) }");
			// proc.execute();
			// return cmd.ExecuteNonQuery();
		} catch (Exception e) {
			throw new BaseBllException(e);
		} finally {
		}
		return result;
	}

	/**
	 * 添加参数 20142014年1月16日 qxd
	 * 
	 * @param params
	 * @return
	 */
	public List<String> getParameterOrders(List<AppDbParameter> params) {
		List<String> ls = new ArrayList<String>();
		for (AppDbParameter par : params) {
			ls.add(par.getColumnName());
		}
		return ls;
	}

	/**
	 * 执行SQL查询，返回一个满足条件的某一行的某个字段值 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 *            SQL语句
	 * @param commandParameters
	 *            IList AppDbParameter>类型
	 * @return 返回object
	 * @throws BaseBllException
	 */
	@Override
	public Object QueryObject(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		/**
    	 * 修改人：党智康<br/>
    	 * 修改时间 ：2014年11月8日 18:08:24<br/>
    	 * 修改备注：这种数据查询方式，未设置是否进行了数据库操作<br/>
    	 * 任务单号：KYEEAPPTEST-726
    	 */
		super.setIfProcessed(true);
		// 收集执行语句
		// base.CollectLogString(sqlString, AppDbParameters);
		// super.CollectLogString(sqlString, AppDbParameters, SystemName);

		PrepareParameter(CommandType.Text, sqlString, AppDbParameters, null);
		/**
		 * comments:
		 * 
		 * sjl modify 2013-10-14下午1:23:41
		 */
		/**
		 * 修改时间：2014-11-18
		 * 修改点：一个DB可执行两次带：方式的SQL
		 */
		if(AppDbParameters != null){
			super.DbCommand.parameterOrders = this.getSqlParameters(sqlString);
		}else{
		    super.DbCommand.parameterOrders.clear();
		}
		/**
		 *任务：KYEEAPPTEST-844
		 *描述：输出带有具体参数值的SQL日志
		 *人员：施建龙
		 *时间：2014年12月2日下午5:22:44
		 **/
		String logSqlString = GetLogContent(sqlString, AppDbParameters);
		DbCommand.setSqlLogString(logSqlString);

		Object obj = super.DbCommand.ExecuteScalar();

		return obj;
	}
	
	/**
	 * 任务号：
	 * 描述：获取Blob
	 * 作者：liuxingping
	 * 时间：2015年6月15日上午10:01:47
	 * @param sqlString
	 * @param AppDbParameters
	 * @return
	 * @throws BaseBllException
	 */
	public Blob QueryBlob(String sqlString, List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		super.setIfProcessed(true);
		PrepareParameter(CommandType.Text, sqlString, AppDbParameters, null);
		if(AppDbParameters != null){
			super.DbCommand.parameterOrders = this.getSqlParameters(sqlString);
		}else{
		    super.DbCommand.parameterOrders.clear();
		}
		String logSqlString = GetLogContent(sqlString, AppDbParameters);
		DbCommand.setSqlLogString(logSqlString);
		Blob obj = super.DbCommand.ExecuteBlob();
		return obj;
	}

	/**
	 * 执行普通查询 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 *            Sql语句
	 * @param commandParameters
	 *            IList AppDbParameter>类型参数列表
	 * @return AppDataTable
	 * @throws BaseBllException
	 */
	@Override
	public AppDataTable Query(String sqlString,
			java.util.List<AppDbParameter> commandParameters)
			throws BaseBllException {
		return ExecuteDataTable(sqlString, commandParameters, null);
	}

	/**
	 * 翻页查询用 创建者：张宝锋 创建时间：2012-12-28
	 * 
	 * @param sqlString
	 *            sqlString必须是普通查询语句，且不能出现Order By！返回要查询的数据集
	 * @param primaryKey
	 *            primaryKey必须是sqlString语句中查询结果中某个主要字段
	 * @param commandParameters
	 *            IList AppDbParameter 类型的参数列表
	 * @param gridRequestParameters
	 *            GridRequestParameters类型的列表
	 * @return 返回HrpDataTable
	 * @throws BaseBllException
	 * @throws NumberFormatException
	 */
	@Override
	public AppDataTable QueryByPage(String sqlString, String primaryKey,
			java.util.List<AppDbParameter> commandParameters,
			GridRequestParameters gridRequestParameters)
			throws NumberFormatException, BaseBllException {
		return ExecuteDataTableByPage(sqlString, primaryKey, commandParameters,
				gridRequestParameters);
	}

	/**
	 * 执行SQL查询 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param entity
	 *            要转化成的实体类型
	 * @param sqlString
	 *            SQL语句
	 * @param commandParameters
	 *            IList AppDbParameter 类型
	 * @return 返回Model实体
	 * @throws BaseBllException
	 */
	@Override
	public AppEntitys QueryEntity(Class type, String sqlString,
			java.util.List<AppDbParameter> commandParameters)
			throws BaseBllException {
		return QueryEntity(type, sqlString, commandParameters, false);
	}

	/**
	 * 添加readOnly属性，无此属性的调用处理逻辑迁移到此方法中 施建龙 2013年3月4日11:49:39
	 * 
	 * @param type
	 * @param sqlString
	 * @param commandParameters
	 * @param readOnly
	 * @return
	 * @throws BaseBllException
	 */
	@Override
	public AppEntitys QueryEntity(Class type, String sqlString,
			java.util.List<AppDbParameter> commandParameters, boolean readOnly)
			throws BaseBllException {
		// base.QueryEntity(type, sqlString, commandParameters);
		AppDataTable hrpDt = ExecuteDataTable(sqlString, commandParameters,
				null);
		AppEntitys appEntitys = new AppEntitys();
		java.util.List<BaseEntity> entityList = null;
		entityList = EntityUtil.CreateEntityList(hrpDt.getDataTable(), type,
				readOnly);
		appEntitys.setEntityList(entityList);
		appEntitys.setCount(hrpDt.getCount());

		return appEntitys;
	}

	/**
	 * 执行分页查询，返回实体集合+实体个数 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param baseEntity
	 *            要转成的实体类型
	 * @param sqlString
	 *            Sql语句
	 * @param primaryKey
	 *            primaryKey必须是sqlString语句中查询结果中某个主要字段
	 * @param commandParameters
	 *            IList AppDbParameter>类型的参数列表
	 * @param gridRequestParameters
	 *            GridRequestParameters类型参数，提取分页用
	 * @return 返回实体集合
	 * @throws BaseBllException
	 * @throws NumberFormatException
	 */
	@Override
	public AppEntitys QueryEntityByPage(Class type, String sqlString,
			String primaryKey,
			java.util.List<AppDbParameter> commandParameters,
			GridRequestParameters gridRequestParameters)
			throws NumberFormatException, BaseBllException {
		// 获取满足sqlString查询条件的记录集
		// AppDataTable hrpDt = ExecuteDataTableByPage(sqlString, primaryKey,
		// commandParameters, gridRequestParameters);
		//
		// Type type = typeof(BaseEntity);
		// appEntitys appEntitys = new appEntitys();
		//
		// 生成实体集合
		// GenericList<BaseEntity> entityList = new
		// GenericList<BaseEntity>(hrpDt.DataTable, type);
		//
		// appEntitys.EntityList = entityList;
		// appEntitys.Count = hrpDt.Count;
		//
		// return appEntitys;
		return QueryEntityByPage(type, sqlString, primaryKey,
				commandParameters, gridRequestParameters, false);
	}

	/**
	 * 添加readOnly属性，无此属性的调用处理逻辑迁移到此方法中 施建龙 2013年3月4日11:49:39
	 * 
	 * @param type
	 * @param sqlString
	 * @param primaryKey
	 * @param commandParameters
	 * @param gridRequestParameters
	 * @param readOnly
	 * @return
	 * @throws BaseBllException
	 * @throws NumberFormatException
	 */
	@Override
	public AppEntitys QueryEntityByPage(Class type, String sqlString,
			String primaryKey,
			java.util.List<AppDbParameter> commandParameters,
			GridRequestParameters gridRequestParameters, boolean readOnly)
			throws NumberFormatException, BaseBllException {
		// 获取满足sqlString查询条件的记录集
		AppDataTable hrpDt = ExecuteDataTableByPage(sqlString, primaryKey,
				commandParameters, gridRequestParameters);

		// Type type = typeof(BaseEntity);
		AppEntitys appEntitys = new AppEntitys();

		// 生成实体集合
		// GenericList<BaseEntity> entityList = new
		// GenericList<BaseEntity>(hrpDt.DataTable, type);

		java.util.List<BaseEntity> entityList = EntityUtil.CreateEntityList(
				hrpDt.getDataTable(), type, readOnly);
		appEntitys.setEntityList(entityList);
		appEntitys.setCount(hrpDt.getCount());

		return appEntitys;
	}

	/**
	 * 执行SQL查询 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param entity
	 *            要转化成的实体类型
	 * @param sqlString
	 *            SQL语句
	 * @param AppDbParameters
	 *            IList AppDbParameter 类型
	 * @return 返回Model实体
	 * @throws BaseBllException
	 */
	@Override
	public AppEntitys QueryEntityWithoutLog(Class type, String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		AppDataTable hrpDt = ExecuteDataTableWithoutLog(sqlString,
				AppDbParameters, null);
		AppEntitys appEntitys = new AppEntitys();
		java.util.List<BaseEntity> entityList = null;
		entityList = EntityUtil.CreateEntityList(hrpDt.getDataTable(), type,
				false);
		appEntitys.setEntityList(entityList);
		appEntitys.setCount(hrpDt.getCount());

		return appEntitys;
	}

	/**
	 * 执行SQL查询 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 *            sql语句
	 * @param AppDbParameters
	 *            IList AppDbParameter>类型
	 * @param gridRequestParameters
	 *            GridRequestParameters 类型
	 * @return 返回HrpDataTable
	 * @throws BaseBllException
	 */
	protected AppDataTable ExecuteDataTableWithoutLog(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		super.setIfProcessed(true);

		// 输出SQL脚本至日志文件，使用参数的实际值
		// 施建龙
		// 2013年9月17日5:59:22
		HLogger.Info(super.GetLogContent(sqlString, AppDbParameters));

		PrepareParameter(CommandType.Text, sqlString, AppDbParameters,
				gridRequestParameters);
		// HLogger.Info(base.DbCommand.CommandText);

		// its Java equivalent:
		// using (DbDataAdapter da = new
		// OracleDataAdapter((OracleCommand)super.DbCommand))
		// DbDataAdapter da = new
		// OracleDataAdapter((OracleCommand)super.DbCommand);
		AppDataTable hrpDt = new AppDataTable();
		try {
			DataTable dt = null;
			if(AppDbParameters != null){
				super.DbCommand.parameterOrders = this.getSqlParameters(sqlString);
			}else {
				super.DbCommand.parameterOrders.clear();
			}
			
			super.DbCommand.setDbCharSet(getCharSet());
			
			/**
			 * 任务： 描述：对于无log输出的执行命令，需要使用空字符串进行填充，否则会输出上一条语句的SQL 人员：施建龙
			 * 时间：2014年9月26日下午1:26:05
			 **/
			DbCommand.setSqlLogString("");
			
			dt = DbCommand.ExecuteQuery();
			// da.Fill(dt);
			hrpDt.setDataTable(dt);
			// 施建龙
			// 2013年7月23日12:06:18
			hrpDt.setCount(dt.getRow().size());
		} finally {
			// da.dispose();
		}

		return hrpDt;
	}

	/**
	 * 设置Command对象各个属性，如：CommandType,CommandText,CommandParameter 创建者：张宝锋
	 * 创建时间：2012-12-27
	 * 
	 * @param cmd
	 *            Command
	 * @param cmdType
	 *            CommandType
	 * @param sqlString
	 *            CommandText
	 * @param commandParameters
	 *            IList AppDbParameter>类型参数列表
	 */
	@Override
	protected void PrepareParameter(CommandType cmdType, String sqlString,
			java.util.List<AppDbParameter> commandParameters) {
		super.DbCommand.Connection = connection;
		super.DbCommand.CommandType = cmdType;
		super.DbCommand.Parameters.clear();
		super.DbCommand.CommandText = "";

		if (commandParameters != null) {
			OracleParameter[] cmdParameter = DevartConvertParameter
					.ConvertToDbParameter(commandParameters);
			super.DbCommand.Parameters.addAll(Arrays.asList(cmdParameter));
		}
		super.DbCommand.CommandText = sqlString.toString();
	}

	/**
	 * 设置Command对象各个属性，如：CommandType,CommandText,CommandParameter 创建者：张宝锋
	 * 创建时间：2012-12-27
	 * 
	 * @param cmd
	 *            Command
	 * @param cmdType
	 *            CommandType
	 * @param sqlString
	 *            CommandText
	 * @param commandParameters
	 *            IList AppDbParameter>类型参数列表
	 * @param gridRequestParameters
	 *            GridRequestParameters类型参数列表
	 */
	private void PrepareParameter(CommandType cmdType, String sqlString,
			java.util.List<AppDbParameter> commandParameters,
			GridRequestParameters gridRequestParameters) {
		StringBuilder strSql = new StringBuilder();

		if (gridRequestParameters != null) {
			/*strSql.append(" SELECT TT.* FROM ( ");
			strSql.append(" SELECT ROWNUM As RowNo, T.* FROM (SELECT T2.* FROM ("
					+ sqlString + " ) T2 ");
			if (!DotNetToJavaStringHelper.isNullOrEmpty(gridRequestParameters
					.getSort())) {
				strSql.append(" ORDER BY T2." + gridRequestParameters.getSort()
						+ " " + gridRequestParameters.getOrder());
			}
			strSql.append(" ) T ");
			strSql.append(" ) TT ");
			strSql.append(" WHERE TT.RowNo > "
					+ (gridRequestParameters.getPage() - 1)
					* gridRequestParameters.getRows() + " ");
			strSql.append(" AND TT.RowNo <= " + gridRequestParameters.getPage()
					* gridRequestParameters.getRows() + "");*/
			//edit start huangnenghong 2015/1/4 KYEEAPPC-1218
			strSql.append(" SELECT TT.* FROM ( ");
			strSql.append(" SELECT  T.* FROM (SELECT T2.* FROM ("
					+ sqlString + " ) T2 ");
			if (!DotNetToJavaStringHelper.isNullOrEmpty(gridRequestParameters
					.getSort())) {
				strSql.append(" ORDER BY T2." + gridRequestParameters.getSort()
						+ " " + gridRequestParameters.getOrder());
			}
			strSql.append(" ) T ");
			strSql.append(" ) TT ");
			strSql.append(" LIMIT "
					+ (gridRequestParameters.getPage() - 1)
					* gridRequestParameters.getRows() + ",");
			strSql.append(gridRequestParameters.getRows() + "");
			//edit end huangnenghong 2015/1/4 KYEEAPPC-1218

		} else {
			strSql.append(sqlString);
		}

		// 重新设置Command对象
		PrepareParameter(cmdType, strSql.toString(), commandParameters);
	}

	/**
	 * 执行SQL查询 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 *            sql语句
	 * @param commandParameters
	 *            IList AppDbParameter>类型
	 * @param gridRequestParameters
	 *            GridRequestParameters 类型
	 * @return 返回HrpDataTable
	 * @throws BaseBllException
	 */
	protected AppDataTable ExecuteDataTable(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters,
			GridRequestParameters gridRequestParameters)
			throws BaseBllException {
		super.setIfProcessed(true);

		// 收集执行语句
		// base.CollectLogString(sqlString, AppDbParameters);
		super.CollectLogString(sqlString, AppDbParameters, SystemName);
		
		// 收集执行语句
		String logSqlString = null;
		
		PrepareParameter(CommandType.Text, sqlString, AppDbParameters,
				gridRequestParameters);
		
		/**
		 * 任务： 描述：使用super.DbCommand.CommandText作为SQL输出语句 作者：施建龙 日期：2014年5月22日
		 * 下午6:36:54
		 **/
		logSqlString = GetLogContent(super.DbCommand.CommandText,
				AppDbParameters);
		
		// HLogger.Info(base.DbCommand.CommandText);
		// SJL
		// 2013年4月19日12:08:34
		// 统一在collectlog中输出sql语句至日志文件
		// C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by
		// its Java equivalent:
		// using (OracleDataAdapter da = new
		// OracleDataAdapter((OracleCommand)super.DbCommand))
		// OracleDataAdapter da = new
		// OracleDataAdapter((OracleCommand)super.DbCommand);
		AppDataTable hrpDt = new AppDataTable();
		try {
			DataTable dt = null;
			// da.Fill(dt);
			/**
			 * comments:
			 * 
			 * sjl modify 2013-10-14下午1:29:01
			 * 
			 * 修改原因：如果参数对象为null无须进行参数扫描
			 * 修改人：张勇
			 * 修改时间：二〇一四年四月二十三日 16:49:03
			 */
			if(AppDbParameters != null){
				super.DbCommand.parameterOrders = this.getSqlParameters(sqlString);
			}else {
				super.DbCommand.parameterOrders.clear();
			}
			super.DbCommand.setDbCharSet(getCharSet());
			
			DbCommand.setSqlLogString(logSqlString);
			
			dt = DbCommand.ExecuteQuery();
			hrpDt.setDataTable(dt);
			// 返回结果集的行数
			// 施建龙
			// 2013年5月14日9:10:12
			hrpDt.setCount(dt.getRow().size());
			// end
		} catch (Exception e) {
			// HLogger.error(e);
			throw new BaseBllException(e);
		} finally {
			// da.dispose();
		}
		return hrpDt;
	}

	/**
	 * 执行SQL查询 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 *            sql语句
	 * @param commandParameters
	 *            IList AppDbParameter>类型
	 * @param gridRequestParameters
	 *            GridRequestParameters 类型
	 * @return 返回HrpDataTable
	 * @throws BaseBllException
	 * @throws NumberFormatException
	 */
	protected AppDataTable ExecuteDataTableByPage(String sqlString,
			String primaryKey, java.util.List<AppDbParameter> AppDbParameters,
			GridRequestParameters gridRequestParameters)
			throws NumberFormatException, BaseBllException {
		AppDataTable hrpDt = new AppDataTable();
		//edit start huangnenghong 2015/1/4 KYEEAPPC-1218
		String tmpSqlString = "Select Count(" + primaryKey + ") From ("
				+ sqlString + ") AS TC";
		//edit end huangnenghong 2015/1/4 KYEEAPPC-1218
		// 获取满足条件的记录行数
		hrpDt.setCount(Integer.parseInt(QueryObject(tmpSqlString,
				AppDbParameters).toString()));

		// 获取满足条件的记录集
		AppDataTable tmp = ExecuteDataTable(sqlString, AppDbParameters,
				gridRequestParameters);
		hrpDt.setDataTable(tmp.getDataTable());

		return hrpDt;
	}

	/**
	 * 执行SQL语句 创建者：张宝锋 创建时间：2012-12-27
	 * 
	 * @param sqlString
	 *            sql语句
	 * @param commandParameters
	 *            IList AppDbParameter>类型
	 * @return 返回执行响应的行数
	 * @throws BaseBllException
	 */
	protected int ExecuteNonQuery(String sqlString,
			java.util.List<AppDbParameter> AppDbParameters)
			throws BaseBllException {
		super.setIfUpdated(true);

		String logSqlString = null;

		logSqlString = GetLogContent(sqlString, AppDbParameters);
		
		// 收集执行语句
		// super.CollectLogString(sqlString, AppDbParameters, SystemName);
		PrepareParameter(CommandType.Text, sqlString, AppDbParameters);
		/**
		 * comments:
		 * 
		 * sjl modify 2013-10-14下午1:29:19
		 */
		if(AppDbParameters != null){
			super.DbCommand.parameterOrders = this.getSqlParameters(sqlString);
		}else {
			super.DbCommand.parameterOrders.clear();
		}
		super.DbCommand.setDbCharSet(getCharSet());
		
		DbCommand.setSqlLogString(logSqlString);
		
		return super.DbCommand.ExecuteNonQuery();
	}

	/**
	 * 暂时忽略日志存储，因sql语句可能过长 施建龙 2013年2月15日11:00:14
	 * 
	 * @param sqlString
	 * @return
	 * @throws BaseBllException
	 */
	protected int ExecuteNonQuery(String sqlString) throws BaseBllException {
		super.setIfUpdated(true);
		super.DbCommand.Connection = connection;
		super.DbCommand.CommandType = CommandType.Text;
		super.DbCommand.Parameters.clear();
		super.DbCommand.CommandText = "";
		super.DbCommand.CommandText = sqlString;
		
		String logSqlString = null;

		logSqlString = GetLogContent(sqlString, null);
		
		/**
		 * comments:
		 * 
		 * sjl modify 2013-10-14下午1:29:28
		 */
		//super.DbCommand.parameterOrders = this.getSqlParameters(sqlString);
		super.DbCommand.setDbCharSet(getCharSet());

		DbCommand.setSqlLogString(logSqlString);
		
		return super.DbCommand.ExecuteNonQuery();
	}

	/**
	 * comments:针对JAVA平台的批量更新入口
	 * 
	 * sjl modify 2014年1月2日下午7:02:39
	 */
	protected int ExecuteNonQueryBatch(java.util.List<String> sqlStrings)
			throws BaseBllException {
		super.setIfUpdated(true);
		super.DbCommand.Connection = connection;
		super.DbCommand.CommandType = CommandType.Text;
		super.DbCommand.Parameters.clear();
		super.DbCommand.CommandText = "";
		super.DbCommand.CommandText = "";
		/**
		 * comments:
		 * 
		 * sjl modify 2013-10-14下午1:29:28
		 */
		super.DbCommand.parameterOrders = this.getSqlParameters("");
		super.DbCommand.setDbCharSet(getCharSet());

		return super.DbCommand.ExecuteNonQueryBatch(sqlStrings);
	}

	/**
	 * 服务器时间
	 */
	@Override
	public Date ServerDate() throws BaseBllException {
		String strSql = "select sysdate from dual";

		PrepareParameter(CommandType.Text, strSql, null);

		super.DbCommand.parameterOrders = new ArrayList<String>();

		Object obj = super.DbCommand.ExecuteScalar();
		return (Date) obj;
	}

	/**
	 * 获取数据库最新ID
	 * 
	 * @return
	 * @throws BaseBllException
	 */
	@Override
	public long GetNewID() throws BaseBllException {
		String strSql = "select hrp_id.nextval from dual";
		Object obj = QueryObject(strSql, null);
		return Long.parseLong(obj.toString());
	}

	@Override
	public long GetNewID(String seqName) throws BaseBllException {
		String strSql = "select " + seqName + ".nextval from dual";
		Object obj = QueryObject(strSql, null);
		return Long.parseLong(obj.toString());
	}

	/**
	 * 施建龙
	 * 
	 * @param entitys
	 * @param rows
	 * @return
	 * @throws BaseBllException
	 */
	@Override
	public int BulkInsert(java.util.List<BaseEntity> entitys, int rows)
			throws BaseBllException {
		int count = entitys.size();
		int pageCount = count / rows + ((count % rows > 0) ? 1 : 0);
		int page = 0;
		StringBuilder entitySql = null;
		for (; page < pageCount; page++) {
			entitySql = new StringBuilder();
			int i = page * rows;
			java.util.List<String> sqlStrings = new ArrayList<String>();
			// entitySql.append("begin");
			// entitySql.append("\n");
			for (; (i < count && i < (page + 1) * rows); i++) {
				sqlStrings.add(EntityUtil.CreateSaveEntitySqlString(this,
						entitys.get(i)) + "");// + ";"
				// entitySql.append("\n");
			}
			// entitySql.append("end;");
			// sjl add
			// 2013年3月28日15:51:18
			// HLogger.Info(entitySql.ToString());
			// SJL
			// 2013年4月19日12:08:34
			// 统一在collectlog中输出sql语句至日志文件
			this.ExecuteNonQueryBatch(sqlStrings);
		}
		return count;
	}

	/**
	 * 批量插入数据 施建龙 2013年2月15日9:47:18 override
	 * 
	 * @param entitys
	 * @param rows
	 * @return
	 * @throws BaseBllException
	 */
	public final int BulkInsertTest(java.util.List<BaseEntity> entitys, int rows)
			throws BaseBllException {
		String[] columnNames = { "DEPT_COST_ID", "DEPT_CODE", "COST_ITEM_NO",
				"RECKON_UNIT_ID", "COST_MONTH", "COST_DATE",
				"DEPT_COST_AMOUNT", "OWNER_DEPT_TYPE", "AUDIT_FLAG",
				"AUDIT_USER", "AUDIT_TIME", "COMMENTS", "COST_SOURCE",
				"CREATOR", "CREATE_TIME", "UPDATER", "UPDATE_TIME", "VERSION",
				"CREATOR_NAME", "UPDATER_NAME", "DATA_STATE",
				"AUDIT_USER_NAME", "IN_OUT_TYPE", "SYSTEM_CODE",
				"MULTI_CLASSIFIED_SCHM_ID" };

		DbCommand command = (DbCommand) super.DbCommand;

		int count = entitys.size();

		if (count == 0) {
			return 0;
		}

		int pageCount = count / rows + ((count % rows > 0) ? 1 : 0);
		int page = 0;
		EntitySql entitySql = null;

		columnNames = new String[entitys.get(0).getUpdateAttributes().size()];
		int tempi = 0;
		for (String attribute : entitys.get(0).getUpdateAttributes()) {
			columnNames[tempi] = attribute;
			tempi++;
		}
		entitySql = EntityUtil.CreateBuckInsertEntitySql(entitys.get(0),
				columnNames, 1);
		for (; page < pageCount; page++) {
			int i = page * rows;
			int insertRows = 0;

			if ((page + 1) < pageCount) {
				insertRows = rows;
			} else {
				insertRows = count - (page) * rows;
			}
			command.CommandText = entitySql.getSqlString();

			// implicit typing in Java:
			// for (AppDbParameter parameter : entitySql.getAppDbParameters())
			// {
			// // command.Parameters.add(parameter.getColumnName(),
			// ConvertToOracleDbType(parameter.getColumnType()));
			// //
			// command.Parameters.[parameter.getColumnName()].setValue(EntityUtil.CreateTypeArray(parameter.getColumnType(),
			// insertRows));
			// }

			// for (; (i < count && i < (page + 1) * rows); i++)
			// {
			// // Set parameters values
			// implicit typing in Java:
			// for (var parameter : entitySql.getAppDbParameters())
			// {
			// (command.getParameters()[parameter.ColumnName])[i] =
			// EntityUtil.GetColumnValue(entitys.get(i), parameter.ColumnName);
			// }
			// }
			//
			// command.ExecuteArray(insertRows);
		}
		return count;
	}

	@Override
	public int BulkUpdate(java.util.List<BaseEntity> entitys, int rows)
			throws BaseBllException {
		int count = entitys.size();
		int pageCount = count / rows + ((count % rows > 0) ? 1 : 0);
		int page = 0;
		StringBuilder entitySql = null;
		for (; page < pageCount; page++) {
			entitySql = new StringBuilder();
			int i = page * rows;
			java.util.List<String> sqlStrings = new ArrayList<String>();
			// entitySql.append("begin");
			// entitySql.append("\n");
			for (; (i < count && i < (page + 1) * rows); i++) {
				sqlStrings.add(EntityUtil.CreateUpdateEntitySqlString(this,
						entitys.get(i)) + "");// + ";"
				// entitySql.append("\n");
			}
			// entitySql.append("end;");
			// sjl add
			// 2013年3月28日15:51:18
			// HLogger.Info(entitySql.ToString());
			// SJL
			// 2013年4月19日12:08:34
			// 统一在collectlog中输出sql语句至日志文件
			this.ExecuteNonQueryBatch(sqlStrings);
		}
		return count;
	}

	/**
	 * 批量构造SQL语句，一次提交至数据库 施建龙 2013年2月8日10:43:08 override
	 * 
	 * @param entitys
	 * @param rows
	 * @return
	 * @throws BaseBllException
	 */
	public final int BulkUpdateTest(java.util.List<BaseEntity> entitys, int rows)
			throws BaseBllException {
		int count = entitys.size();
		int pageCount = count / rows + ((count % rows > 0) ? 1 : 0);
		int page = 0;
		StringBuilder entitySql = null;
		for (; page < pageCount; page++) {
			entitySql = new StringBuilder();
			int i = page * rows;
			entitySql.append("begin");
			entitySql.append("\n");
			for (; (i < count && i < (page + 1) * rows); i++) {
				entitySql.append(EntityUtil.CreateUpdateEntitySqlString(this,
						entitys.get(i)) + ";");
				entitySql.append("\n");
			}
			entitySql.append("end;");
			this.ExecuteNonQuery(entitySql.toString());
		}
		return count;
	}

	/**
	 * 根据C#type，转换为Oracle数据库Type
	 * 
	 * @param type
	 * @return
	 */
	private OracleType ConvertToOracleDbType(Class type) {
		if (type.getName().toUpperCase().equals("STRING")) {
			return OracleType.VarChar;
		} else if (type.getName().toUpperCase().indexOf("INT") >= 0) {
			return OracleType.Int32;
		} else if (type.getName().toUpperCase().indexOf("DECIMAL") >= 0) {
			return OracleType.Number;
		} else if (type.getName().toUpperCase().indexOf("DATETIME") >= 0) {
			return OracleType.DateTime;
		} else {
			return OracleType.VarChar;
		}
	}
	// /#endregion
}