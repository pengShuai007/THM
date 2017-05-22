
package APP.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <pre>
 * 作者:刘健
 * 日期:2014年3月19日 上午11:27:00
 * 描述:
 * </pre>
 */
public class HEIR_DAILY_BUSSINESS extends BaseEntity{
	private String NAME;
	private String OUT_NAME;
	private String OUT_NUM;
	private String IN_NUM;
	
	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
		this.AddUpdateAttribute("NAME");
	}
	public String getOUT_NAME() {
		return OUT_NAME;
	}

	public void setOUT_NAME(String oUT_NAME) {
		OUT_NAME = oUT_NAME;
		this.AddUpdateAttribute("OUT_NAME");
	}

	public String getOUT_NUM() {
		return OUT_NUM;
	}

	public void setOUT_NUM(String oUT_NUM) {
		OUT_NUM = oUT_NUM;
		this.AddUpdateAttribute("OUT_NUM");
	}

	public String getIN_NUM() {
		return IN_NUM;
	}

	public void setIN_NUM(String iN_NUM) {
		IN_NUM = iN_NUM;
		this.AddUpdateAttribute("IN_NUM");
	}

	
}
