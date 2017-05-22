package APP.Comm.DataBase.Helper.SqlHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.DotNet.CommandType;
import APP.Comm.DataBase.Helper.OracleHelper.OracleDbHelper;
import APP.Comm.DotNet.HrpStringBuilder;
import APP.Comm.Util.EntityUtil;
import APP.Model.BaseEntity;

/**
 * 说明：SQL Sever 数据库对象 作者：张宝锋 创建日期：2013-02-27
 */
public class SqlDbHelper extends OracleDbHelper {

	public SqlDbHelper() {
		super();
		/**
		 * comments:SQL SERVER数据库默认使用GBK字符集 如实际字符集不同，可在DS_CONFIG中指定
		 * 
		 * sjl modify 2013-11-12上午11:26:03
		 */
		if (this.getCharSet() == null || this.getCharSet().trim().length() == 0) {
			this.setCharSet("GBK");
		}
	}

	public SqlDbHelper(String _systemName) {
		super(_systemName);
		/**
		 * comments:SQL SERVER数据库默认使用GBK字符集 如实际字符集不同，可在DS_CONFIG中指定
		 * 
		 * sjl modify 2013-11-12上午11:26:03
		 */
		this.setCharSet("GBK");
	}

//	@Override
//	public void Open() throws BaseBllException {
//		// super.Open();
//		connection = dbConManager.getSqlConnection(getConParameter());
//		this.DbCommand.Connection = connection;
//	}

//	@Override
//	public Date ServerDate() throws BaseBllException {
//		String strSql = "select getdate() ";
//		PrepareParameter(CommandType.Text, strSql, null);
//		super.DbCommand.parameterOrders = new ArrayList<String>();
//		Object obj = super.DbCommand.ExecuteScalar();
//		return (Date) obj;
//	}

//	@Override
//	public long GetNewID() throws BaseBllException {
//		return 0;
//	}

	// / <summary>
	// / 张宝锋
	// / </summary>
	// / <param name="entitys"></param>
	// / <param name="rows"></param>
	// / <returns></returns>
	@Override
	public int BulkInsert(List<BaseEntity> entitys, int rows)
			throws BaseBllException {
		int count = entitys.size();
		int pageCount = count / rows + ((count % rows > 0) ? 1 : 0);
		int page = 0;
		HrpStringBuilder entitySql = new HrpStringBuilder();
		for (; page < pageCount; page++) {
			entitySql.Clear();
			int i = page * rows;
			entitySql.append("begin");
			entitySql.append("\n");
			for (; (i < count && i < (page + 1) * rows); i++) {
				entitySql.append(EntityUtil.CreateSaveEntitySqlString(this,
						entitys.get(i)) + ";");
				entitySql.append("\n");
			}
			entitySql.append("end;");
			this.ExecuteNonQuery(entitySql.toString());
		}
		return count;
	}

	@Override
	public int BulkUpdate(List<BaseEntity> entitys, int rows)
			throws BaseBllException {
		int count = entitys.size();
		int pageCount = count / rows + ((count % rows > 0) ? 1 : 0);
		int page = 0;
		HrpStringBuilder entitySql = new HrpStringBuilder();
		for (; page < pageCount; page++) {
			entitySql.Clear();
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
}