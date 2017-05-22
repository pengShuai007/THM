package org.kyee.Services.SmsService.base;

import java.util.Date;

public class SmsPoint {
	private String Ims; //平台号码
	
	private String totalPoint;  // 总点数（用户点数+剩余点数）
	private String UserPoint;  // 用户点数
	private String RemainPoint;  //剩余点数
	private Date UpdateTime; //查询时间
	
	public String getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(String totalPoint) {
		this.totalPoint = totalPoint;
	}
	public String getUserPoint() {
		return UserPoint;
	}
	public void setUserPoint(String userPoint) {
		UserPoint = userPoint;
	}
	public String getRemainPoint() {
		return RemainPoint;
	}
	public void setRemainPoint(String remainPoint) {
		RemainPoint = remainPoint;
	}
	public String getIms() {
		return Ims;
	}
	public void setIms(String ims) {
		Ims = ims;
	}
	public Date getUpdateTime() {
		return UpdateTime;
	}
	public void setUpdateTime(Date updateTime) {
		UpdateTime = updateTime;
	}
}
