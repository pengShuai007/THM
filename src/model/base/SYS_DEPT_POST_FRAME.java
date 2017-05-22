package model.base;

import java.math.BigDecimal;

import APP.Model.BaseEntity;


public class SYS_DEPT_POST_FRAME extends BaseEntity {
	
	public SYS_DEPT_POST_FRAME() {}

	private String DEPT_POST_ID;
	private String DEPT_POST_NAME;
	private String DEPT_LIST_CODE;
	private String DEPT_OR_POST;
	private String DEPT_CODE;

	public String getDEPT_POST_ID() {
		return DEPT_POST_ID;
	}

	public void setDEPT_POST_ID(String dEPT_POST_ID) {
		DEPT_POST_ID = dEPT_POST_ID;
		this.AddUpdateAttribute("DEPT_POST_ID");
	}

	public String getDEPT_POST_NAME() {
		return DEPT_POST_NAME;
	}

	public void setDEPT_POST_NAME(String dEPT_POST_NAME) {
		DEPT_POST_NAME = dEPT_POST_NAME;
		this.AddUpdateAttribute("DEPT_POST_NAME");
	}

	public String getDEPT_LIST_CODE() {
		return DEPT_LIST_CODE;
	}

	public void setDEPT_LIST_CODE(String dEPT_LIST_CODE) {
		DEPT_LIST_CODE = dEPT_LIST_CODE;
		this.AddUpdateAttribute("DEPT_LIST_CODE");
	}

	public String getDEPT_OR_POST() {
		return DEPT_OR_POST;
	}

	public void setDEPT_OR_POST(String dEPT_OR_POST) {
		DEPT_OR_POST = dEPT_OR_POST;
		this.AddUpdateAttribute("DEPT_OR_POST");
	}

	public String getDEPT_CODE() {
		return DEPT_CODE;
	}

	public void setDEPT_CODE(String dEPT_CODE) {
		DEPT_CODE = dEPT_CODE;
		this.AddUpdateAttribute("DEPT_CODE");
	}
}
