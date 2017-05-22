package model.base.ext;

import model.base.SYS_USER;

/**
 * 任务号：KYEEAPPMAINTENANCE-22
 * @author liuxingping
 *
 */
public class SYS_USER_EXT extends SYS_USER {
	
		public String GetId() {
			return getUSER_ID() + "";
		}
	}
