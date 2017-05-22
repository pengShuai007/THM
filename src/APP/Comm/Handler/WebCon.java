package APP.Comm.Handler;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DotNet.HttpContext;

public class WebCon {
	private String keyName;
	private String keyValue;

	public WebCon(HttpContext context) throws BaseBllException {
	}

	public final String getKeyName(HttpContext context) throws BaseBllException {
		return keyName;
	}

	public final void setkeyName(String keyName) throws BaseBllException {
		this.keyName = keyName;
	}

	public final String getKeyValue(HttpContext context)
			throws BaseBllException {
		return keyValue;
	}

	public final void setKeyValue(String keyValue) throws BaseBllException {
		this.keyValue = keyValue;
	}
}
