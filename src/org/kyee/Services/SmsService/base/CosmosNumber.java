package org.kyee.Services.SmsService.base;

import java.util.Date;

public class CosmosNumber {
	private Date REC_DATE; //记录时间
	private String ECPID; //企业ID
	private String BCPID; //请求查询余额的 CPID
//	private String TOTAL_NUMBER;  // 总条数（已用条数+剩余条数）
//	private String USED_NUMBER;  // 购买条数
	private String REMAIN_NUMBER;  //剩余条数
	private Date UPDATE_TIME; //修改时间
	public Date getREC_DATE() {
		return REC_DATE;
	}
	public void setREC_DATE(Date rEC_DATE) {
		REC_DATE = rEC_DATE;
	}
	public String getECPID() {
		return ECPID;
	}
	public void setECPID(String eCPID) {
		ECPID = eCPID;
	}
	public String getBCPID() {
		return BCPID;
	}
	public void setBCPID(String bCPID) {
		BCPID = bCPID;
	}
//	public String getTOTAL_NUMBER() {
//		return TOTAL_NUMBER;
//	}
//	public void setTOTAL_NUMBER(String tOTAL_NUMBER) {
//		TOTAL_NUMBER = tOTAL_NUMBER;
//	}
//	public String getUSED_NUMBER() {
//		return USED_NUMBER;
//	}
//	public void setUSED_NUMBER(String uSED_NUMBER) {
//		USED_NUMBER = uSED_NUMBER;
//	}
	public String getREMAIN_NUMBER() {
		return REMAIN_NUMBER;
	}
	public void setREMAIN_NUMBER(String rEMAIN_NUMBER) {
		REMAIN_NUMBER = rEMAIN_NUMBER;
	}
	public Date getUPDATE_TIME() {
		return UPDATE_TIME;
	}
	public void setUPDATE_TIME(Date uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}
	
}
