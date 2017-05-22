package model.base.ext;

import model.base.SYS_POST_DICT;

public class SYS_POST_DICT_EXT extends SYS_POST_DICT {
	@Override
	public String GetId() {
		return super.getPOST_ID()+"";
	}
}