package model.base.ext;

import model.base.SYS_DEPT_INFO;

 
public class SYS_DEPT_INFO_EXT extends SYS_DEPT_INFO {
	
	public String GetId() {
		return super.getDEPT_ID() + "";
	}
}
