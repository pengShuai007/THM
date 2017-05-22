package model.base;

import java.util.Date;

import APP.Model.BaseEntity;



/// <summary>
/// 模块：SYS_POST_DICT 实体类
/// 创建者：LIZHIBO
/// 创建时间：2014年8月29日14:44:33
/// </summary>
public class SYS_POST_DICT extends BaseEntity {
	
	public SYS_POST_DICT() {}

	private long POST_ID;
	private String POST_CODE;
	private String POST_NAME;
	private int VALIDATE_FLAG;
	private int PUB_ATTR;
	private String COMMENTS;
	private String CREATOR;
	private Date CREATE_TIME;
	private String CREATOR_NAME;

	public long getPOST_ID() {
		return POST_ID;
	}

	public void setPOST_ID(long pOST_ID) {
		POST_ID = pOST_ID;
		this.AddUpdateAttribute("POST_ID");
	}

	public String getPOST_CODE() {
		return POST_CODE;
	}

	public void setPOST_CODE(String pOST_CODE) {
		POST_CODE = pOST_CODE;
		this.AddUpdateAttribute("POST_CODE");
	}

	public String getPOST_NAME() {
		return POST_NAME;
	}

	public void setPOST_NAME(String pOST_NAME) {
		POST_NAME = pOST_NAME;
		this.AddUpdateAttribute("POST_NAME");
	}

	public int getVALIDATE_FLAG() {
		return VALIDATE_FLAG;
	}

	public void setVALIDATE_FLAG(int vALIDATE_FLAG) {
		VALIDATE_FLAG = vALIDATE_FLAG;
		this.AddUpdateAttribute("VALIDATE_FLAG");
	}

	public int getPUB_ATTR() {
		return PUB_ATTR;
	}

	public void setPUB_ATTR(int vPUB_ATTR) {
		PUB_ATTR = vPUB_ATTR;
		this.AddUpdateAttribute("PUB_ATTR");
	}

	public String getCOMMENTS() {
		return COMMENTS;
	}

	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
		this.AddUpdateAttribute("COMMENTS");
	}

	public String getCREATOR() {
		return CREATOR;
	}

	public void setCREATOR(String cREATOR) {
		CREATOR = cREATOR;
		this.AddUpdateAttribute("CREATOR");
	}

	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(Date date) {
		CREATE_TIME = date;
		this.AddUpdateAttribute("CREATE_TIME");
	}

	public String getCREATOR_NAME() {
		return CREATOR_NAME;
	}

	public void setCREATOR_NAME(String cREATOR_NAME) {
		CREATOR_NAME = cREATOR_NAME;
		this.AddUpdateAttribute("CREATOR_NAME");
	}

	public String GetPrimaryKeyName() {
		return "POST_ID";
	}
}
