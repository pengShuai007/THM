package APP.Comm.Config;

import java.io.Serializable;

public class Param implements Serializable {
	protected String key=null;
	protected String value=null;
	public Param() {
		super();
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
