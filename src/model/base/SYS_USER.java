package model.base;

import APP.Model.BaseEntity;

/**
 * 任务号：KYEEAPPMAINTENANCE-22
 * @author liuxingping
 *
 */
public class SYS_USER extends BaseEntity{
	private int USER_ID;
	private String USER_CODE;
	private String USER_NAME;
	private String PASS_WORD;
	private int ROLE_ID;
	private String OLDPASS_WORD;
	private int SOURCE;
	private String PHONE_NUM;
	private String EMAIL;
	private String JOB_ID;
	private int IS_PROTECTED;
	// edit start 增加管理电话号码和邮箱  杨博申 2015年7月6日09:06:45
	public String getPHONE_NUM() {
		return PHONE_NUM;
	}
	public void setPHONE_NUM(String pHONE_NUM) {
		this.PHONE_NUM = pHONE_NUM;
		this.AddUpdateAttribute("PHONE_NUM");
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		this.EMAIL = eMAIL;
		this.AddUpdateAttribute("EMAIL");
	}
	// edit end 增加管理电话号码和邮箱  杨博申 2015年7月6日09:06:59
	// edit start 增加管理工号  黄鹏   2016年6月6日10:06:45
	public String getJOB_ID() {
		return JOB_ID;
	}
	public void setJOB_ID(String JobId) {
		this.JOB_ID = JobId;
		this.AddUpdateAttribute("JOB_ID");
	}
	// edit end 增加管理工号  黄鹏 2016年6月6日10:06:59
	// edit start 增加管理是否保护 黄鹏   2016年8月4日
	public int getIS_PROTECTED() {
		return IS_PROTECTED;
	}
	public void setIS_PROTECTED(int iS_PROTECTED) {
		this.IS_PROTECTED = iS_PROTECTED;
		this.AddUpdateAttribute("IS_PROTECTED");
	}
	// edit end 增加管理是否保护 黄鹏 2016年8月4日
	// edit start 增加验证key值  罗京 2015年2月9日22:19:48
	private String AUTH_KEY;
	
	public String getAUTH_KEY() {
		return AUTH_KEY;
	}
	public void setAUTH_KEY(String aUTH_KEY) {
		AUTH_KEY = aUTH_KEY;
		this.AddUpdateAttribute("AUTH_KEY");
	}
	// edit end 增加验证key值  罗京 2015年2月9日22:19:48
	public int getSOURCE() {
		return SOURCE;
	}
	public void setSOURCE(int SOURCE) {
		this.SOURCE = SOURCE;
		this.AddUpdateAttribute("SOURCE");
	}
	public String getOLDPASS_WORD() {
		return OLDPASS_WORD;
	}
	public void setOLDPASS_WORD(String oLDPASS_WORD) {
		OLDPASS_WORD = oLDPASS_WORD;
		this.AddUpdateAttribute("OLDPASS_WORD");
	}
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int USER_ID) {
		this.USER_ID = USER_ID;
		this.AddUpdateAttribute("USER_ID");
	}
	
	public int getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(int rOLE_ID) {
		ROLE_ID = rOLE_ID;
		this.AddUpdateAttribute("ROLE_ID");
	}
	public String getUSER_CODE() {
		return USER_CODE;
	}
	public void setUSER_CODE(String USER_CODE) {
		this.USER_CODE = USER_CODE;
		this.AddUpdateAttribute("USER_CODE");
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String USER_NAME) {
		this.USER_NAME = USER_NAME;
		this.AddUpdateAttribute("USER_NAME");
	}
	public String getPASS_WORD() {
		return PASS_WORD;
	}
	public void setPASS_WORD(String pASS_WORD) {
		PASS_WORD = pASS_WORD;
		this.AddUpdateAttribute("PASS_WORD");
	}
}
