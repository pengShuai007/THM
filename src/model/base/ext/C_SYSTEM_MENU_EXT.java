package model.base.ext;

import model.base.C_SYSTEM_MENU;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年2月21日 上午9:32:05
 * 描述:
 * </pre>
 */
@SuppressWarnings("serial")
public class C_SYSTEM_MENU_EXT extends C_SYSTEM_MENU {
	@Override
	public String GetId() {
		return getMENU_ID() + "";
	}
}
