package APP.Comm.DataBase.Helper.OleHelper;


/** 
 根据不同类型数据库和连接方式重构完成后的SQL语句和满足参数顺序的参数集合
 在参数赋值阶段，会根据此类的参数顺序构造Command参数
 Oledb对参数顺序有严格要求
 编写:施建龙
 时间:2013年1月8日 16:03:30 
 
*/
public class SqlDomain
{
	public SqlDomain()
	{
		setParameters(new java.util.ArrayList<String>());
	}

	private String privateSqlString;
	public final String getSqlString()
	{
		return privateSqlString;
	}
	public final void setSqlString(String value)
	{
		privateSqlString = value;
	}

	private java.util.List<String> privateParameters;
	public final java.util.List<String> getParameters()
	{
		return privateParameters;
	}
	public final void setParameters(java.util.List<String> value)
	{
		privateParameters = value;
	}

}