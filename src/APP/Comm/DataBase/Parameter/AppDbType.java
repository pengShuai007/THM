package APP.Comm.DataBase.Parameter;

//模块编号：<模块编号，可以引用系统设计中的模块编号>
//作用：<数据库参数类型转换，将HrpDbParameter类型参数转化为OracleParameter/SqlParameter类型>
//作者：张宝锋
//编写日期：<2012-12-27>

//摘要：指定字段或属性的数据类型，以用于 System.Data.OracleClient.OracleParameter。
//创建者：张宝锋
//创建时间：2012-12-27
 /**
 *  
 * 修改人：  ypf <br/>  
 * 修改时间：2014年10月29日 11:28 <br/>  
 * 修改备注：修改文件编译时报编码错误的提示<br/>
 * 任务号：KYEEAPP-692
 * @创建人 ypf
 * @版本
 */
public enum AppDbType
{
	BFile(1),
	Blob(2),
	Char(3),
	Clob(4),
	Cursor(5),
	DateTime(6),
	IntervalDayToSecond(7),
	IntervalYearToMonth(8),
	LongRaw(9),
	LongVarChar(10),
	NChar(11),
	NClob(12),
	Number(13),
	NVarChar(14),
	Raw(15),
	RowId(16),
	Timestamp(18),
	TimestampLocal(19),
	TimestampWithTZ(20),
	VarChar(22),
	Byte(23),
	UInt16(24),
	UInt32(25),
	SByte(26),
	Int16(27),
	Int32(28),
	Float(29),
	Double(30),
	AnsiString(99); //默认值，没有指定数据类型

	private int intValue;
	private static java.util.HashMap<Integer, AppDbType> mappings;
	private synchronized static java.util.HashMap<Integer, AppDbType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, AppDbType>();
		}
		return mappings;
	}

	private AppDbType(int value)
	{
		intValue = value;
		AppDbType.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static AppDbType forValue(int value)
	{
		return getMappings().get(value);
	}
}