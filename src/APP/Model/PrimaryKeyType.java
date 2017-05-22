package APP.Model;

public enum PrimaryKeyType
{
	AUTO, //自动
	ASSIGNED; //手工指定

	public int getValue()
	{
		return this.ordinal();
	}

	public static PrimaryKeyType forValue(int value)
	{
		return values()[value];
	}
}