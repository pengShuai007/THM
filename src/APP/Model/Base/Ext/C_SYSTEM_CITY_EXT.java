
package APP.Model.Base.Ext;

import APP.Model.C_SYSTEM_CITY;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年3月19日 上午11:29:19
 * 描述:
 * </pre>
 */
@SuppressWarnings("serial")
public class C_SYSTEM_CITY_EXT extends C_SYSTEM_CITY {
	@Override
	public String GetId() {
		return getCITY_ID() + "";
	}
	
}
