package APP.Comm.DataBase.Helper;

/**
 * 系统类型
 */
public enum SystemType {
	APP, HIS;

	public int getValue() {
		return this.ordinal();
	}

	public static SystemType forValue(int value) {
		return values()[value];
	}
}