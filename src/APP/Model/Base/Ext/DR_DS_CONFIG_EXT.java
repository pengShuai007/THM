package APP.Model.Base.Ext;

import APP.Model.Base.DR_DS_CONFIG;

/// <summary>
/// 模块：DR_DS_CONFIG 实体扩展类
/// 功能：
/// 创建者：sjl
/// 创建时间：2013-10-06
/// </summary>
public class DR_DS_CONFIG_EXT extends DR_DS_CONFIG {
	private String charSet = null;

	// / <summary>
	// / 覆盖GetId方法
	// / </summary>
	@Override
	public String GetId() {
		return getDS_CONFIG_ID() + "";
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
}
