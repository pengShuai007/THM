package APP.Model.Base;

import APP.Model.C_SYSTEM_HOSPITAL;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年2月22日 下午5:54:59
 * 描述:
 * </pre>
 */
@SuppressWarnings("serial")
public class C_SYSTEM_HOSPITAL_EXT extends C_SYSTEM_HOSPITAL {
	private String ADV_URL; // 医院图片    修改人：涂玉和    任务：KYEEAPPC-2374
	private String ADV_HOSPITAL_INTRO; // 医院简介    修改人：涂玉和    任务：KYEEAPPC-2374

	public String getADV_URL() {
		return ADV_URL;
	}

	public void setADV_URL(String aDV_URL) {
		ADV_URL = aDV_URL;
		this.AddUpdateAttribute("ADV_URL");
	}

	public String getADV_HOSPITAL_INTRO() {
		return ADV_HOSPITAL_INTRO;
	}

	public void setADV_HOSPITAL_INTRO(String aDV_HOSPITAL_INTRO) {
		ADV_HOSPITAL_INTRO = aDV_HOSPITAL_INTRO;
		this.AddUpdateAttribute("ADV_HOSPITAL_INTRO");
	}

	@Override
	public String GetId() {
		return getHOSPITAL_ID() + "";
	}

}