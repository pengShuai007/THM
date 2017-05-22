package model.base;

import java.sql.Date;
import APP.Model.BaseEntity;


public class SYS_DEPT_POST_INFO extends BaseEntity {
	public SYS_DEPT_POST_INFO() {
	}

	private long INFO_ID;
	private long DEPT_ID;
	private long POST_ID;
	private String CREATOR;
	private Date CREATE_TIME;
	private String CREATOR_NAME;


	public long getINFO_ID() {
		return INFO_ID;
	}

	public void setINFO_ID(long iNFO_ID) {
		INFO_ID = iNFO_ID;
		this.AddUpdateAttribute("INFO_ID");
	}

	public long getDEPT_ID() {
		return DEPT_ID;
	}

	public void setDEPT_ID(long dEPT_ID) {
		DEPT_ID = dEPT_ID;
		this.AddUpdateAttribute("DEPT_ID");
	}

	public long getPOST_ID() {
		return POST_ID;
	}

	public void setPOST_ID(long pOST_ID) {
		POST_ID = pOST_ID;
		this.AddUpdateAttribute("POST_ID");
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

	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
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
		return "INFO_ID";
	}
}
