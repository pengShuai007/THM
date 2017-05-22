package model.base;

import APP.Model.BaseEntity;

/**
 * <pre>
 * 创建人:ZHANG YONG
 * 日期:2014年2月22日上午10:25:09
 * 描述:科室实体
 * </pre>
 */
public class C_DEPT_DICT extends BaseEntity {
	
	private int HOSPITAL_ID;
	private String DEPT_CODE;
	private String DEPT_NAME;
	private String FULL_UPPER_SPELL;
	private String HOSPITAL_NAME;
	
	
	
	
	
	public String getHOSPITAL_NAME() {
		return HOSPITAL_NAME;
	}
	public void setHOSPITAL_NAME(String hOSPITAL_NAME) {
		HOSPITAL_NAME = hOSPITAL_NAME;
		this.AddUpdateAttribute("HOSPITAL_NAME");
	}
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}
	public void setDEPT_CODE(String dEPT_CODE) {
		DEPT_CODE = dEPT_CODE;
		this.AddUpdateAttribute("DEPT_CODE");
	}
	public String getDEPT_NAME() {
		return DEPT_NAME;
	}
	public void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
		this.AddUpdateAttribute("DEPT_NAME");
	}
	public String getFULL_UPPER_SPELL() {
		return FULL_UPPER_SPELL;
	}
	public void setFULL_UPPER_SPELL(String fULL_UPPER_SPELL) {
		FULL_UPPER_SPELL = fULL_UPPER_SPELL;
		this.AddUpdateAttribute("FULL_UPPER_SPELL");
	}
	public int getHOSPITAL_ID() {
		return HOSPITAL_ID;
	}
	public void setHOSPITAL_ID(int hOSPITAL_ID) {
		HOSPITAL_ID = hOSPITAL_ID;
		this.AddUpdateAttribute("HOSPITAL_ID");
	}
}
