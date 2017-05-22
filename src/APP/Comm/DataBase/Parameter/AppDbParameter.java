package APP.Comm.DataBase.Parameter;

import java.sql.Types;

//模块编号：<模块编号，可以引用系统设计中的模块编号>
//作用：<用户自定义数据参数类，最终>
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
public class AppDbParameter {
	/**
	 * comments:_hrpDbType在当前版本中没有正确维护，慎用此字段
	 * 
	 * 
	 * sjl modify 2013-10-20下午7:45:54
	 */
	private AppDbType _hrpDbType = AppDbType.AnsiString;
	private int _size = 0;

	/**
	 * comments:转换为java后， 在调用存储过程时，要求前端调用时设定参数必须和存储过程本身的参数顺序相同
	 * 同时添加存储过程返回参数类型标识inOrOut,0:in,1:out sjl modify 2013-10-5上午8:44:51
	 */
	private int inOrOut = 0;
	private int returnDataType = Types.VARCHAR;

	private String privateColumnName;

	public final String getColumnName() {
		return privateColumnName;
	}

	public final void setColumnName(String value) {
		privateColumnName = value;
	}

	private Object privateColumnValue;

	public final Object getColumnValue() {
		return privateColumnValue;
	}

	public final void setColumnValue(Object value) {
		privateColumnValue = value;
	}

	private AppDbType privateHrpDbType = AppDbType.forValue(0);

	public final AppDbType getAppDbType() {
		return privateHrpDbType;
	}

	public final void setAppDbType(AppDbType value) {
		privateHrpDbType = value;
	}

	private int privatesize;

	public final int getsize() {
		return privatesize;
	}

	public final void setsize(int value) {
		privatesize = value;
	}

	// C#属性类型
	// 施建龙
	// 2013年2月15日9:05:22
	private java.lang.Class privateColumnType;

	public final java.lang.Class getColumnType() {
		return privateColumnType;
	}

	public final void setColumnType(java.lang.Class value) {
		privateColumnType = value;
	}

	// 构造
	public AppDbParameter(String columnName, AppDbType dbType, int size,
			Object value) {
		this.setColumnName(columnName);
		this.setAppDbType(dbType);
		this.setsize(size);
		this.setColumnValue(value);
	}

	// 构造
	public AppDbParameter(String columnName, AppDbType dbType, Object value) {
		this.setColumnName(columnName);
		this.setAppDbType(dbType);
		this.setColumnValue(value);
	}

	// 构造
	public AppDbParameter(String columnName, Object value) {
		this.setColumnName(columnName);
		this.setColumnValue(value);
		// 默认给与AnsiString
		// 编写:施建龙
		// 时间:2013年1月8日 18:10:07
		this.setAppDbType(AppDbType.AnsiString);
	}

	/**
	 * 设定原生语言字段类型 施建龙 2013年2月15日9:08:39
	 * 
	 * @param type
	 * @return
	 */
	public final AppDbParameter AddColumnType(java.lang.Class type) {
		this.setColumnType(type);
		return this;
	}

	public int getInOrOut() {
		return inOrOut;
	}

	public void setInOrOut(int inOrOut) {
		this.inOrOut = inOrOut;
	}

	public int getReturnDataType() {
		return returnDataType;
	}

	public void setReturnDataType(int returnDataType) {
		this.returnDataType = returnDataType;
	}

}