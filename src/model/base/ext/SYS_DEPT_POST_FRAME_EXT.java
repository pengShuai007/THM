package model.base.ext;

import model.base.SYS_DEPT_POST_FRAME;

public class SYS_DEPT_POST_FRAME_EXT extends SYS_DEPT_POST_FRAME {
	@Override
	public String GetId() {
		return super.getDEPT_POST_ID();
	}
}