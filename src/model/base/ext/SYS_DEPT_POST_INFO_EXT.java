package model.base.ext;

import model.base.SYS_DEPT_POST_INFO;

public class SYS_DEPT_POST_INFO_EXT extends SYS_DEPT_POST_INFO {
	@Override
	public String GetId() {
		return super.getINFO_ID()+"";
	}
}