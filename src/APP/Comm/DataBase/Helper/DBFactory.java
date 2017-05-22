package APP.Comm.DataBase.Helper;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.Config.SystemParams;
import APP.Comm.Config.SystemParamsFactory;
import APP.Comm.DataBase.Helper.MySQLHelper.MySQLDbHelper;
import APP.Comm.DataBase.Helper.OracleHelper.OracleDbHelper;
import APP.Comm.DataBase.Helper.SqlHelper.SqlDbHelper;
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public class DBFactory {
	static SystemParams systemParams = (SystemParams) SystemParamsFactory
			.getSystemParams();

	/**
	 * 私有构造函数
	 */
	private DBFactory() {
	}

	/**
	 * 创建数据库类实例 用于需要从系统配置表中获取数据库信息的情况下调用 创建者：张宝锋 创建时间：2013-01-04
	 * 
	 * @param databaseType
	 *            数据库类型
	 * @param connString
	 *            连接字符串
	 * @return
	 * @throws BaseBllException
	 */
	private static IDataBase CreateDataBase(DatabaseType databaseType,
			ConnectionParameter conParameter, String systemName)
			throws BaseBllException {
		OracleDbHelper oracleDbHelper = null;
		switch (databaseType) {
		case ORACLE:
			oracleDbHelper = new OracleDbHelper();
			oracleDbHelper.setConParameter(conParameter);
			if (conParameter.getCharSet() != null) {
				oracleDbHelper.setCharSet(conParameter.getCharSet());
			}
			return oracleDbHelper;
		case SQLSERVER:
			oracleDbHelper = new SqlDbHelper();
			oracleDbHelper.setConParameter(conParameter);
			if (conParameter.getCharSet() != null) {
				oracleDbHelper.setCharSet(conParameter.getCharSet());
			}
			return oracleDbHelper;
		case MYSQL:
			oracleDbHelper = new MySQLDbHelper();
			oracleDbHelper.setConParameter(conParameter);
			if (conParameter.getCharSet() != null) {
				oracleDbHelper.setCharSet(conParameter.getCharSet());
			}
			return oracleDbHelper;
		case ODBC:
			throw new BaseBllException("未实现ODBC连接！");
		case OLE:
			throw new BaseBllException("未实现OLE链接！");
		default:
			throw new BaseBllException("类型不存在" + databaseType + "！");
		}
	}

	/**
	 * 任务号：
	 * 描述：创建运维数据库对象
	 * 作者：liuxingping
	 * 时间：2015年1月16日下午3:58:38
	 * @return
	 * @throws BaseBllException
	 */
	@SuppressWarnings("static-access")
	public static IDataBase CreateBaseDB() throws BaseBllException {

		ConnectionParameter conParameter = new ConnectionParameter();
		IDataBase dbBase = null;
		dbBase = CreateDataBase(DatabaseType.MYSQL, conParameter, "BASE");
		dbBase.setCharSet(systemParams.getParamaValue("CHARSET"));
		return dbBase;
	}
}