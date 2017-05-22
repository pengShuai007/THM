package model.base;

import java.util.Date;

import APP.Model.BaseEntity;

public class PATIENT extends BaseEntity {

	private int ID;
	private String PATIENT_NAME;
	private String PATIENT_GENDER;
	private String PATIENT_AGE;
	private String PATIENT_PHONE;
	private String STATUS;
	private String IS_VISIT;
	private String REMARK;
	private Date CREATETIME;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
		this.AddUpdateAttribute("ID");
	}
	public String getPATIENT_NAME() {
		return PATIENT_NAME;
	}
	public void setPATIENT_NAME(String pATIENT_NAME) {
		PATIENT_NAME = pATIENT_NAME;
		this.AddUpdateAttribute("PATIENT_NAME");
	}
	public String getPATIENT_GENDER() {
		return PATIENT_GENDER;
	}
	public void setPATIENT_GENDER(String pATIENT_GENDER) {
		PATIENT_GENDER = pATIENT_GENDER;
		this.AddUpdateAttribute("PATIENT_GENDER");
	}
	public String getPATIENT_AGE() {
		return PATIENT_AGE;
	}
	public void setPATIENT_AGE(String pATIENT_AGE) {
		PATIENT_AGE = pATIENT_AGE;
		this.AddUpdateAttribute("PATIENT_AGE");
	}
	public String getPATIENT_PHONE() {
		return PATIENT_PHONE;
	}
	public void setPATIENT_PHONE(String pATIENT_PHONE) {
		PATIENT_PHONE = pATIENT_PHONE;
		this.AddUpdateAttribute("PATIENT_PHONE");
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
		this.AddUpdateAttribute("STATUS");
	}
	public String getIS_VISIT() {
		return IS_VISIT;
	}
	public void setIS_VISIT(String iS_VISIT) {
		IS_VISIT = iS_VISIT;
		this.AddUpdateAttribute("IS_VISIT");
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
		this.AddUpdateAttribute("REMARK");
	}
	public Date getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(Date cREATETIME) {
		CREATETIME = cREATETIME;
		this.AddUpdateAttribute("CREATETIME");
	}
	
	@Override
	public String GetId() {
		// TODO Auto-generated method stub
		return getID()+"";
	}
}
