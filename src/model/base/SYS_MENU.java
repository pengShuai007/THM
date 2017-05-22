package model.base;

import java.util.Date;

import APP.Model.BaseEntity;

/**
 * KYEEAPPMAINTENANCE-22
 * @author liuxingping
 *
 */
public class SYS_MENU extends BaseEntity {
	private int MENU_ID;
	private String MENU_NAME;
	private String MENU_URL;
	private String MENU_CODE;
	private String OPERATOR_NAME;
	private Date OPERATOR_TIME;
	private String MODIFIER;
	private Date MODIFY_TIME;
	private int C_S_MENU_ID;
	private int MENU_ORDER;

	public int getMENU_ID() {
		return MENU_ID;
	}

	public void setMENU_ID(int mENU_ID) {
		MENU_ID = mENU_ID;
		this.AddUpdateAttribute("MENU_ID");
	}

	public String getMENU_NAME() {
		return MENU_NAME;
	}

	public void setMENU_NAME(String mENU_NAME) {
		MENU_NAME = mENU_NAME;
		this.AddUpdateAttribute("MENU_NAME");
	}

	public String getMENU_URL() {
		return MENU_URL;
	}

	public void setMENU_URL(String mENU_URL) {
		MENU_URL = mENU_URL;
		this.AddUpdateAttribute("MENU_URL");
	}

	public String getMENU_CODE() {
		return MENU_CODE;
	}

	public void setMENU_CODE(String mENU_CODE) {
		MENU_CODE = mENU_CODE;
		this.AddUpdateAttribute("MENU_CODE");
	}

	public String getOPERATOR_NAME() {
		return OPERATOR_NAME;
	}

	public void setOPERATOR_NAME(String oPERATOR_NAME) {
		OPERATOR_NAME = oPERATOR_NAME;
		this.AddUpdateAttribute("OPERATOR_NAME");
	}

	public Date getOPERATOR_TIME() {
		return OPERATOR_TIME;
	}

	public void setOPERATOR_TIME(Date oPERATOR_TIME) {
		OPERATOR_TIME = oPERATOR_TIME;
		this.AddUpdateAttribute("OPERATOR_TIME");
	}

	public String getMODIFIER() {
		return MODIFIER;
	}

	public void setMODIFIER(String mODIFIER) {
		MODIFIER = mODIFIER;
		this.AddUpdateAttribute("MODIFIER");
	}

	public Date getMODIFY_TIME() {
		return MODIFY_TIME;
	}

	public void setMODIFY_TIME(Date mODIFY_TIME) {
		MODIFY_TIME = mODIFY_TIME;
		this.AddUpdateAttribute("MODIFY_TIME");
	}

	public int getC_S_MENU_ID() {
		return C_S_MENU_ID;
	}

	public void setC_S_MENU_ID(int c_S_MENU_ID) {
		C_S_MENU_ID = c_S_MENU_ID;
		this.AddUpdateAttribute("C_S_MENU_ID");
	}
	
	public int getMENU_ORDER() {
		return MENU_ORDER;
	}

	public void setMENU_ORDER(int mENU_ORDER) {
		MENU_ORDER = mENU_ORDER;
		this.AddUpdateAttribute("MENU_ORDER");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see APP.Model.BaseEntity#GetPrimaryKeyName()
	 */
	@Override
	public String GetPrimaryKeyName() {
		return "MENU_ID";
	}
}
