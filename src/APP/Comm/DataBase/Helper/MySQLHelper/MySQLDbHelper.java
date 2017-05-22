package APP.Comm.DataBase.Helper.MySQLHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.CommandType;
import APP.Comm.DataBase.Helper.OracleHelper.OracleDbHelper;
import APP.Comm.DataBase.Parameter.AppDbParameter;

public class MySQLDbHelper extends OracleDbHelper {

	@Override
	public Date ServerDate() throws BaseBllException {
		String sqlString = "select current_timestamp() ";
		PrepareParameter(CommandType.Text, sqlString, null);
		super.DbCommand.parameterOrders = new ArrayList<String>();
		Object obj = super.DbCommand.ExecuteScalar();
		return (Date) obj;
	}

	 
	@Override
	public long GetNewID() throws BaseBllException {
		String strSql = "SELECT nextval('APPCloudSeq')";
		Object obj = QueryObject(strSql, null);
		return Long.parseLong(obj.toString());
	}

	@Override
	public long GetNewID(String seqName) throws BaseBllException {
		String strSql = "select nextval('" + seqName + "')";
		Object obj = QueryObject(strSql, null);
		return Long.parseLong(obj.toString());
	}
	
	 
	@Override
	public Map<String, Object> ExecProcedure(String sqlString,
			List<AppDbParameter> AppDbParameters) throws BaseBllException {
		return super.ExecProcedure(sqlString, AppDbParameters);
	}
}
