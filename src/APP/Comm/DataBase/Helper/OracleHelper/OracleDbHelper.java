package APP.Comm.DataBase.Helper.OracleHelper;

import APP.Comm.DataBase.Helper.Devart.DevartOracleDbHelper;

//模块编号：<模块编号，可以引用系统设计中的模块编号>
//作用：<数据库执行类，执行各种数据访问操作>
//作者：张宝锋
//编写日期：<2012-12-27>

public class OracleDbHelper extends DevartOracleDbHelper {
	/**
	 * 当前DataBase事务，Container创建后放入DataBase
	 * 
	 * //public DbTransaction DbTransaction { get; set; }
	 */

	public OracleDbHelper() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param connString
	 *            连接字符串
	 */
	public OracleDbHelper(String _systemName) {
		SystemName = _systemName;
	}

}