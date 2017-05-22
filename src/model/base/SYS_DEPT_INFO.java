package model.base;

import java.util.Date;

import APP.Model.BaseEntity;

public class SYS_DEPT_INFO extends BaseEntity
{
	public SYS_DEPT_INFO() {}

	private long DEPT_ID;
	private String DEPT_CODE;
	private String DEPT_NAME;
	private int VM_FLAG;
	private String DEPT_LIST_CODE;
	private String COMMENTS;
	private String OPERATOR;
	private Date OPERATOR_DATE;
	private String OPERATOR_NAME;
	
	public long getDEPT_ID() {
		return DEPT_ID;
	}
	public void setDEPT_ID(long dEPT_ID) {
		DEPT_ID = dEPT_ID;
		this.AddUpdateAttribute("DEPT_ID");
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
	public int getVM_FLAG() {
		return VM_FLAG;
	}
	public void setVM_FLAG(int vM_FLAG) {
		VM_FLAG = vM_FLAG;
		this.AddUpdateAttribute("VM_FLAG");
	}
	public String getDEPT_LIST_CODE() {
		return DEPT_LIST_CODE;
	}
	public void setDEPT_LIST_CODE(String dEPT_LIST_CODE) {
		DEPT_LIST_CODE = dEPT_LIST_CODE;
		this.AddUpdateAttribute("DEPT_LIST_CODE");
	}
	public String getCOMMENTS() {
		return COMMENTS;
	}
	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
		this.AddUpdateAttribute("COMMENTS");
	}
	public String getOPERATOR_NAME() {
		return OPERATOR_NAME;
	}
	public void setOPERATOR_NAME(String oPERATOR_NAME) {
		OPERATOR_NAME = oPERATOR_NAME;
		this.AddUpdateAttribute("OPERATOR_NAME");
	}
	public Date getOPERATOR_DATE() {
		return OPERATOR_DATE;
	}
	public void setOPERATOR_DATE(Date oPERATOR_DATE) {
		OPERATOR_DATE = oPERATOR_DATE;
		this.AddUpdateAttribute("OPERATOR_DATE");
	}
	public String getOPERATOR() {
		return OPERATOR;
	}
	public void setOPERATOR(String oPERATOR) {
		OPERATOR = oPERATOR;
		this.AddUpdateAttribute("OPERATOR");
	}

	public  String GetPrimaryKeyName() {
	     return "DEPT_ID";
	}
}