package APP.Model.Base.Ext;

import APP.Model.Base.SYS_MODULE_USE_HIS;

/// <summary>
/// 模块：SYS_MODULE_USE_HIS 实体扩展类
/// 功能：
/// 创建者：sjl
/// 创建时间：2013-10-06
/// </summary>
public class SYS_MODULE_USE_HIS_EXT extends SYS_MODULE_USE_HIS {
	// / <summary>
	// / 覆盖GetId方法
	// / </summary>
	@Override
	public String GetId() {
		return getMODULE_USE_HIS_ID() + "";
	}
}
