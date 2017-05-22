package APP.Comm.DataBase.DotNet;


public enum OracleType {
	
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
	private static java.util.HashMap<Integer, OracleType> mappings;
	private synchronized static java.util.HashMap<Integer, OracleType> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, OracleType>();
		}
		return mappings;
	}

	private OracleType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static OracleType forValue(int value)
	{
		return getMappings().get(value);
	}
}
