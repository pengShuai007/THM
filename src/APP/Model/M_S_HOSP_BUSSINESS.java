package APP.Model;
import java.util.Date;

import APP.Model.BaseEntity;

/**
*
*<pre>
* 任务: 
* 描述：实体类
* 作者：caofanglin
* 时间：2016年12月20日 16:37:05
* 类名：M_S_HOSP_BUSSINESS
*
* <pre>
*/
public class M_S_HOSP_BUSSINESS extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private int HID; // 主键
	private Date TJ_DATE; // 统计日期
	private int PROVINCE_ID; // 省ID
	private int CITY_ID; // 市ID
	private int HOSPITAL_ID; // 医院ID
	private int ONLINE_DAY; // 医院是否当天上线（1 是，0 不是）
	private String HOSPITAL_LEVEL; // 医院等级参数
	private String PLATFORM; // 医院平台
	private int APPOINT_COUNT; // 预约总量
	private int D_APPOINT_COUNT; // 当天预约总量
	private int SUCCESS_APPOINT; // 预约成功量
	private int FAIL_APPOINT; // 预约失败量
	private int VISIT_APPOINT; // 预约就诊量
	private int REGIST_COUNT; // 挂号总量
	private int SUCCESS_REGIST; // 挂号成功量
	private int FAIL_REGIST; // 挂号失败量
	private int VISIT_REGIST; // 挂号就诊量
	private int FX_COUNT; // 返现总量
	private int FX_SUM; // 返现总金额
	private int EXAMNUM; // 检查单总量
	private int LABNUM; // 检验单总量
	private int PENUM; // 体检单总量
	private int PAYNUM; // 支付订单总量
	private int GUIDE_COUNT; // 导医数量
	private int D_SUCCESS_APPOINT; // 预约成功量（就诊日期）
	private int D_FAIL_APPOINT; // 预约失败量（就诊日期）
	private int D_VISIT_APPOINT; // 预约就诊量（就诊日期）
	private int D_CANCEL_APPOINT; // 取消预约量（就诊日期）
	private int CANCEL_APPOINT; // 取消预约量
	private int CANCEL_REGIST; // 取消挂号量

	public int getHID(){
		return HID;
	}
	public void setHID(int hID){
		this.HID = hID;
		this.AddUpdateAttribute("HID");
	}
	public Date getTJ_DATE(){
		return TJ_DATE;
	}
	public void setTJ_DATE(Date tJ_DATE){
		this.TJ_DATE = tJ_DATE;
		this.AddUpdateAttribute("TJ_DATE");
	}
	public int getPROVINCE_ID(){
		return PROVINCE_ID;
	}
	public void setPROVINCE_ID(int pROVINCE_ID){
		this.PROVINCE_ID = pROVINCE_ID;
		this.AddUpdateAttribute("PROVINCE_ID");
	}
	public int getCITY_ID(){
		return CITY_ID;
	}
	public void setCITY_ID(int cITY_ID){
		this.CITY_ID = cITY_ID;
		this.AddUpdateAttribute("CITY_ID");
	}
	public int getHOSPITAL_ID(){
		return HOSPITAL_ID;
	}
	public void setHOSPITAL_ID(int hOSPITAL_ID){
		this.HOSPITAL_ID = hOSPITAL_ID;
		this.AddUpdateAttribute("HOSPITAL_ID");
	}
	public int getONLINE_DAY(){
		return ONLINE_DAY;
	}
	public void setONLINE_DAY(int oNLINE_DAY){
		this.ONLINE_DAY = oNLINE_DAY;
		this.AddUpdateAttribute("ONLINE_DAY");
	}
	public String getHOSPITAL_LEVEL(){
		return HOSPITAL_LEVEL;
	}
	public void setHOSPITAL_LEVEL(String hOSPITAL_LEVEL){
		this.HOSPITAL_LEVEL = hOSPITAL_LEVEL;
		this.AddUpdateAttribute("HOSPITAL_LEVEL");
	}
	public String getPLATFORM(){
		return PLATFORM;
	}
	public void setPLATFORM(String pLATFORM){
		this.PLATFORM = pLATFORM;
		this.AddUpdateAttribute("PLATFORM");
	}
	public int getAPPOINT_COUNT(){
		return APPOINT_COUNT;
	}
	public void setAPPOINT_COUNT(int aPPOINT_COUNT){
		this.APPOINT_COUNT = aPPOINT_COUNT;
		this.AddUpdateAttribute("APPOINT_COUNT");
	}
	public int getD_APPOINT_COUNT(){
		return D_APPOINT_COUNT;
	}
	public void setD_APPOINT_COUNT(int d_APPOINT_COUNT){
		this.D_APPOINT_COUNT = d_APPOINT_COUNT;
		this.AddUpdateAttribute("D_APPOINT_COUNT");
	}
	public int getSUCCESS_APPOINT(){
		return SUCCESS_APPOINT;
	}
	public void setSUCCESS_APPOINT(int sUCCESS_APPOINT){
		this.SUCCESS_APPOINT = sUCCESS_APPOINT;
		this.AddUpdateAttribute("SUCCESS_APPOINT");
	}
	public int getFAIL_APPOINT(){
		return FAIL_APPOINT;
	}
	public void setFAIL_APPOINT(int fAIL_APPOINT){
		this.FAIL_APPOINT = fAIL_APPOINT;
		this.AddUpdateAttribute("FAIL_APPOINT");
	}
	public int getVISIT_APPOINT(){
		return VISIT_APPOINT;
	}
	public void setVISIT_APPOINT(int vISIT_APPOINT){
		this.VISIT_APPOINT = vISIT_APPOINT;
		this.AddUpdateAttribute("VISIT_APPOINT");
	}
	public int getREGIST_COUNT(){
		return REGIST_COUNT;
	}
	public void setREGIST_COUNT(int rEGIST_COUNT){
		this.REGIST_COUNT = rEGIST_COUNT;
		this.AddUpdateAttribute("REGIST_COUNT");
	}
	public int getSUCCESS_REGIST(){
		return SUCCESS_REGIST;
	}
	public void setSUCCESS_REGIST(int sUCCESS_REGIST){
		this.SUCCESS_REGIST = sUCCESS_REGIST;
		this.AddUpdateAttribute("SUCCESS_REGIST");
	}
	public int getFAIL_REGIST(){
		return FAIL_REGIST;
	}
	public void setFAIL_REGIST(int fAIL_REGIST){
		this.FAIL_REGIST = fAIL_REGIST;
		this.AddUpdateAttribute("FAIL_REGIST");
	}
	public int getVISIT_REGIST(){
		return VISIT_REGIST;
	}
	public void setVISIT_REGIST(int vISIT_REGIST){
		this.VISIT_REGIST = vISIT_REGIST;
		this.AddUpdateAttribute("VISIT_REGIST");
	}
	public int getFX_COUNT(){
		return FX_COUNT;
	}
	public void setFX_COUNT(int fX_COUNT){
		this.FX_COUNT = fX_COUNT;
		this.AddUpdateAttribute("FX_COUNT");
	}
	public int getFX_SUM(){
		return FX_SUM;
	}
	public void setFX_SUM(int fX_SUM){
		this.FX_SUM = fX_SUM;
		this.AddUpdateAttribute("FX_SUM");
	}
	public int getEXAMNUM(){
		return EXAMNUM;
	}
	public void setEXAMNUM(int eXAMNUM){
		this.EXAMNUM = eXAMNUM;
		this.AddUpdateAttribute("EXAMNUM");
	}
	public int getLABNUM(){
		return LABNUM;
	}
	public void setLABNUM(int lABNUM){
		this.LABNUM = lABNUM;
		this.AddUpdateAttribute("LABNUM");
	}
	public int getPENUM(){
		return PENUM;
	}
	public void setPENUM(int pENUM){
		this.PENUM = pENUM;
		this.AddUpdateAttribute("PENUM");
	}
	public int getPAYNUM(){
		return PAYNUM;
	}
	public void setPAYNUM(int pAYNUM){
		this.PAYNUM = pAYNUM;
		this.AddUpdateAttribute("PAYNUM");
	}
	public int getGUIDE_COUNT(){
		return GUIDE_COUNT;
	}
	public void setGUIDE_COUNT(int gUIDE_COUNT){
		this.GUIDE_COUNT = gUIDE_COUNT;
		this.AddUpdateAttribute("GUIDE_COUNT");
	}
	public int getD_SUCCESS_APPOINT(){
		return D_SUCCESS_APPOINT;
	}
	public void setD_SUCCESS_APPOINT(int d_SUCCESS_APPOINT){
		this.D_SUCCESS_APPOINT = d_SUCCESS_APPOINT;
		this.AddUpdateAttribute("D_SUCCESS_APPOINT");
	}
	public int getD_FAIL_APPOINT(){
		return D_FAIL_APPOINT;
	}
	public void setD_FAIL_APPOINT(int d_FAIL_APPOINT){
		this.D_FAIL_APPOINT = d_FAIL_APPOINT;
		this.AddUpdateAttribute("D_FAIL_APPOINT");
	}
	public int getD_VISIT_APPOINT(){
		return D_VISIT_APPOINT;
	}
	public void setD_VISIT_APPOINT(int d_VISIT_APPOINT){
		this.D_VISIT_APPOINT = d_VISIT_APPOINT;
		this.AddUpdateAttribute("D_VISIT_APPOINT");
	}
	public int getD_CANCEL_APPOINT(){
		return D_CANCEL_APPOINT;
	}
	public void setD_CANCEL_APPOINT(int d_CANCEL_APPOINT){
		this.D_CANCEL_APPOINT = d_CANCEL_APPOINT;
		this.AddUpdateAttribute("D_CANCEL_APPOINT");
	}
	public int getCANCEL_APPOINT(){
		return CANCEL_APPOINT;
	}
	public void setCANCEL_APPOINT(int cANCEL_APPOINT){
		this.CANCEL_APPOINT = cANCEL_APPOINT;
		this.AddUpdateAttribute("CANCEL_APPOINT");
	}
	public int getCANCEL_REGIST(){
		return CANCEL_REGIST;
	}
	public void setCANCEL_REGIST(int cANCEL_REGIST){
		this.CANCEL_REGIST = cANCEL_REGIST;
		this.AddUpdateAttribute("CANCEL_REGIST");
	}
	@Override
	public String GetPrimaryKeyName() {
		return "HID";
	}
}
