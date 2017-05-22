package APP.Comm.DataBase.Helper;


//模块编号：<模块编号，可以引用系统设计中的模块编号>
//作用：<数据工厂类，生成各种业务数据库访问类>
//作者：张宝锋
//编写日期：<2012-12-27>
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public enum DatabaseType {
	ORACLE, // Oracle 数据库
	SQLSERVER, // SqlServer 数据库
	ODBC, // Odbc 数据库
	MYSQL, // MYSQL数据库
	OLE; // OleDb 数据库

	public int getValue() {
		return this.ordinal();
	}

	public static DatabaseType forValue(int value) {
		return values()[value];
	}
}