package APP.Model.Base.Ext;

import APP.Model.Base.C_SYSTEM_OPERATOR;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年2月20日 下午9:16:06
 * 描述:
 * </pre>
 */
@SuppressWarnings("serial")
public class C_SYSTEM_OPERATOR_EXT extends C_SYSTEM_OPERATOR {
	@Override
	public String GetId() {
		return getOPERATOR_ID() + "";
	}
}
