package APP.Model.Base;

import APP.Model.M_S_HOSP_BUSSINESS;

/**
 * <pre>
 * 作者:caofanglin
 * 日期:2016年12月26日14:28:49
 * 描述:
 * </pre>
 */
@SuppressWarnings("serial")
public class M_S_HOSP_BUSSINESS_EXT extends M_S_HOSP_BUSSINESS {
	@Override
	public String GetId() {
		return getHID() + "";
	}

}