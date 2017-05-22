package APP.Comm.DotNet;


import APP.Comm.Util.CommonUtil;
/**
 * comments:DotNet 迁移至Java的辅助类
 * 
 * sjl modify 2013-11-8上午9:28:26
 */
public class HDate extends java.util.Date {

	
	public HDate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HDate(long arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public String ToShortDateString(){
		return CommonUtil.formatDate(this);
	}
	
	public String ToString(){
		return CommonUtil.formatDate(this);
	}
	
}
