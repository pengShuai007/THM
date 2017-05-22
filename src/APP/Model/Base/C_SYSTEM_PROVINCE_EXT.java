
package APP.Model.Base;

import APP.Model.C_SYSTEM_PROVINCE;



/**
 * <pre>
 * 作者:刘健
 * 日期:2014年3月19日 上午11:23:59
 * 描述:
 * </pre>
 */
@SuppressWarnings("serial")
public class C_SYSTEM_PROVINCE_EXT extends C_SYSTEM_PROVINCE {
	@Override
	public String GetId() {
		return getPROVINCE_ID() + "";
	}
}
