package APP.Comm.DataBase.DotNet;

/**
 * comments:引用自网络
 * 
 * sjl modify 2013-10-5上午10:45:50
 */
public class DataColumn {

	String key;
	Object value;

	public DataColumn(String k, Object v) {
		key = k;
		value = v;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
