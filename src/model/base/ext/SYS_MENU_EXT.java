package model.base.ext;

import model.base.SYS_MENU;

/**
 * KYEEAPPMAINTENANCE-22
 * @author liuxingping
 *
 */
public class SYS_MENU_EXT extends SYS_MENU {
	public String GetId() {
		return getMENU_ID() + "";
	}
}
