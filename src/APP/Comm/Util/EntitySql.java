package APP.Comm.Util;

import APP.Comm.DataBase.Parameter.AppDbParameter;

/**
 * 存储带有hrp参数格式的sql语句以及参数对应的hrpDbParameter 编写者：施建龙 时间：2013年1月11日 11:17:20
 */
public class EntitySql {
	private String _sqlString;
	private java.util.List<AppDbParameter> _appDbParameters = null;

	public EntitySql(String sqlString, java.util.List<AppDbParameter> parameters) {
		_sqlString = sqlString;
		_appDbParameters = parameters;
	}

	public final String getSqlString() {
		return _sqlString;
	}

	public final java.util.List<AppDbParameter> getAppDbParameters() {
		return _appDbParameters;
	}
}