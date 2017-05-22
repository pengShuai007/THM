package APP.Model;
import java.util.Date;

import APP.Model.BaseEntity;

/**
*
*<pre>
* 任务: 
* 描述：实体类
* 作者：1024c
* 时间：2016年12月05日 20:50:41
* 类名：SC_TOTAL_INF
*
* <pre>
*/
public class SC_TOTAL_INF extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private int ID;
	private Date ST_DATE; // 统计时间
	private int SUFFER_USER_COUNT; // 病友圈患者用户量
	private int GETIN_GROUP_USER_COUNT; // 加群患者用户量
	private int NEW_SUFFER_USER_COUNT; // 新增病友圈患者用户量
	private int NEW_GROUP_USER_COUNT; // 新增加群患者用户量
	private int ACT_USER_COUNT; // 活跃用户数
	private int LOST_USER_COUNT; // 流失加群用户
	private int TOTAL_GROUP_COUNT; // 群总量
	private int CHILD_GROUP_COUNT; // 生成子群个数
	private int PRO_DEPT_COUNT; // 省科室群个数
	private int PRO_DEPT_ACT_COUNT; // 省科室群活跃用户个数
	private int PRO_DEPT_MEMEMBER_COUNT; // 省科室群人数总量
	private int PRO_DEPT_NEW_COUNT; // 省科室群新增人数
	private int HOSP_GROUP_COUNT; // 医院群个数
	private int HOSP_GROUP_ACT_COUNT; // 医院群活跃用户个数
	private int HOSP_GROUP_MEMEMBER_COUNT; // 医院群人数总量
	private int HOSP_GROUP_NEW_COUNT; // 医院群新增人数
	private int HOSP_DEPT_COUNT; // 医院科室群个数
	private int HOSP_DEPT_ACT_COUNT; // 医院科室群活跃用户个数
	private int HOSP_DEPT_MEMEMBER_COUNT; // 医院科室群人数总量
	private int HOSP_DEPT_NEW_COUNT; // 医院科室群新增人数
	private String CREATOR;
	private Date CREATE_TIME;
	private String UPDATER;
	private Date UPDATE_TIME;

	public int getID(){
		return ID;
	}
	public void setID(int iD){
		this.ID = iD;
		this.AddUpdateAttribute("ID");
	}
	public Date getST_DATE(){
		return ST_DATE;
	}
	public void setST_DATE(Date sT_DATE){
		this.ST_DATE = sT_DATE;
		this.AddUpdateAttribute("ST_DATE");
	}
	public int getSUFFER_USER_COUNT(){
		return SUFFER_USER_COUNT;
	}
	public void setSUFFER_USER_COUNT(int sUFFER_USER_COUNT){
		this.SUFFER_USER_COUNT = sUFFER_USER_COUNT;
		this.AddUpdateAttribute("SUFFER_USER_COUNT");
	}
	public int getGETIN_GROUP_USER_COUNT(){
		return GETIN_GROUP_USER_COUNT;
	}
	public void setGETIN_GROUP_USER_COUNT(int gETIN_GROUP_USER_COUNT){
		this.GETIN_GROUP_USER_COUNT = gETIN_GROUP_USER_COUNT;
		this.AddUpdateAttribute("GETIN_GROUP_USER_COUNT");
	}
	public int getNEW_SUFFER_USER_COUNT(){
		return NEW_SUFFER_USER_COUNT;
	}
	public void setNEW_SUFFER_USER_COUNT(int nEW_SUFFER_USER_COUNT){
		this.NEW_SUFFER_USER_COUNT = nEW_SUFFER_USER_COUNT;
		this.AddUpdateAttribute("NEW_SUFFER_USER_COUNT");
	}
	public int getNEW_GROUP_USER_COUNT(){
		return NEW_GROUP_USER_COUNT;
	}
	public void setNEW_GROUP_USER_COUNT(int nEW_GROUP_USER_COUNT){
		this.NEW_GROUP_USER_COUNT = nEW_GROUP_USER_COUNT;
		this.AddUpdateAttribute("NEW_GROUP_USER_COUNT");
	}
	public int getACT_USER_COUNT(){
		return ACT_USER_COUNT;
	}
	public void setACT_USER_COUNT(int aCT_USER_COUNT){
		this.ACT_USER_COUNT = aCT_USER_COUNT;
		this.AddUpdateAttribute("ACT_USER_COUNT");
	}
	public int getLOST_USER_COUNT(){
		return LOST_USER_COUNT;
	}
	public void setLOST_USER_COUNT(int lOST_USER_COUNT){
		this.LOST_USER_COUNT = lOST_USER_COUNT;
		this.AddUpdateAttribute("LOST_USER_COUNT");
	}
	public int getTOTAL_GROUP_COUNT(){
		return TOTAL_GROUP_COUNT;
	}
	public void setTOTAL_GROUP_COUNT(int tOTAL_GROUP_COUNT){
		this.TOTAL_GROUP_COUNT = tOTAL_GROUP_COUNT;
		this.AddUpdateAttribute("TOTAL_GROUP_COUNT");
	}
	public int getCHILD_GROUP_COUNT(){
		return CHILD_GROUP_COUNT;
	}
	public void setCHILD_GROUP_COUNT(int cHILD_GROUP_COUNT){
		this.CHILD_GROUP_COUNT = cHILD_GROUP_COUNT;
		this.AddUpdateAttribute("CHILD_GROUP_COUNT");
	}
	public int getPRO_DEPT_COUNT(){
		return PRO_DEPT_COUNT;
	}
	public void setPRO_DEPT_COUNT(int pRO_DEPT_COUNT){
		this.PRO_DEPT_COUNT = pRO_DEPT_COUNT;
		this.AddUpdateAttribute("PRO_DEPT_COUNT");
	}
	public int getPRO_DEPT_ACT_COUNT(){
		return PRO_DEPT_ACT_COUNT;
	}
	public void setPRO_DEPT_ACT_COUNT(int pRO_DEPT_ACT_COUNT){
		this.PRO_DEPT_ACT_COUNT = pRO_DEPT_ACT_COUNT;
		this.AddUpdateAttribute("PRO_DEPT_ACT_COUNT");
	}
	public int getPRO_DEPT_MEMEMBER_COUNT(){
		return PRO_DEPT_MEMEMBER_COUNT;
	}
	public void setPRO_DEPT_MEMEMBER_COUNT(int pRO_DEPT_MEMEMBER_COUNT){
		this.PRO_DEPT_MEMEMBER_COUNT = pRO_DEPT_MEMEMBER_COUNT;
		this.AddUpdateAttribute("PRO_DEPT_MEMEMBER_COUNT");
	}
	public int getPRO_DEPT_NEW_COUNT(){
		return PRO_DEPT_NEW_COUNT;
	}
	public void setPRO_DEPT_NEW_COUNT(int pRO_DEPT_NEW_COUNT){
		this.PRO_DEPT_NEW_COUNT = pRO_DEPT_NEW_COUNT;
		this.AddUpdateAttribute("PRO_DEPT_NEW_COUNT");
	}
	public int getHOSP_GROUP_COUNT(){
		return HOSP_GROUP_COUNT;
	}
	public void setHOSP_GROUP_COUNT(int hOSP_GROUP_COUNT){
		this.HOSP_GROUP_COUNT = hOSP_GROUP_COUNT;
		this.AddUpdateAttribute("HOSP_GROUP_COUNT");
	}
	public int getHOSP_GROUP_ACT_COUNT(){
		return HOSP_GROUP_ACT_COUNT;
	}
	public void setHOSP_GROUP_ACT_COUNT(int hOSP_GROUP_ACT_COUNT){
		this.HOSP_GROUP_ACT_COUNT = hOSP_GROUP_ACT_COUNT;
		this.AddUpdateAttribute("HOSP_GROUP_ACT_COUNT");
	}
	public int getHOSP_GROUP_MEMEMBER_COUNT(){
		return HOSP_GROUP_MEMEMBER_COUNT;
	}
	public void setHOSP_GROUP_MEMEMBER_COUNT(int hOSP_GROUP_MEMEMBER_COUNT){
		this.HOSP_GROUP_MEMEMBER_COUNT = hOSP_GROUP_MEMEMBER_COUNT;
		this.AddUpdateAttribute("HOSP_GROUP_MEMEMBER_COUNT");
	}
	public int getHOSP_GROUP_NEW_COUNT(){
		return HOSP_GROUP_NEW_COUNT;
	}
	public void setHOSP_GROUP_NEW_COUNT(int hOSP_GROUP_NEW_COUNT){
		this.HOSP_GROUP_NEW_COUNT = hOSP_GROUP_NEW_COUNT;
		this.AddUpdateAttribute("HOSP_GROUP_NEW_COUNT");
	}
	public int getHOSP_DEPT_COUNT(){
		return HOSP_DEPT_COUNT;
	}
	public void setHOSP_DEPT_COUNT(int hOSP_DEPT_COUNT){
		this.HOSP_DEPT_COUNT = hOSP_DEPT_COUNT;
		this.AddUpdateAttribute("HOSP_DEPT_COUNT");
	}
	public int getHOSP_DEPT_ACT_COUNT(){
		return HOSP_DEPT_ACT_COUNT;
	}
	public void setHOSP_DEPT_ACT_COUNT(int hOSP_DEPT_ACT_COUNT){
		this.HOSP_DEPT_ACT_COUNT = hOSP_DEPT_ACT_COUNT;
		this.AddUpdateAttribute("HOSP_DEPT_ACT_COUNT");
	}
	public int getHOSP_DEPT_MEMEMBER_COUNT(){
		return HOSP_DEPT_MEMEMBER_COUNT;
	}
	public void setHOSP_DEPT_MEMEMBER_COUNT(int hOSP_DEPT_MEMEMBER_COUNT){
		this.HOSP_DEPT_MEMEMBER_COUNT = hOSP_DEPT_MEMEMBER_COUNT;
		this.AddUpdateAttribute("HOSP_DEPT_MEMEMBER_COUNT");
	}
	public int getHOSP_DEPT_NEW_COUNT(){
		return HOSP_DEPT_NEW_COUNT;
	}
	public void setHOSP_DEPT_NEW_COUNT(int hOSP_DEPT_NEW_COUNT){
		this.HOSP_DEPT_NEW_COUNT = hOSP_DEPT_NEW_COUNT;
		this.AddUpdateAttribute("HOSP_DEPT_NEW_COUNT");
	}
	public String getCREATOR(){
		return CREATOR;
	}
	public void setCREATOR(String cREATOR){
		this.CREATOR = cREATOR;
		this.AddUpdateAttribute("CREATOR");
	}
	public Date getCREATE_TIME(){
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Date cREATE_TIME){
		this.CREATE_TIME = cREATE_TIME;
		this.AddUpdateAttribute("CREATE_TIME");
	}
	public String getUPDATER(){
		return UPDATER;
	}
	public void setUPDATER(String uPDATER){
		this.UPDATER = uPDATER;
		this.AddUpdateAttribute("UPDATER");
	}
	public Date getUPDATE_TIME(){
		return UPDATE_TIME;
	}
	public void setUPDATE_TIME(Date uPDATE_TIME){
		this.UPDATE_TIME = uPDATE_TIME;
		this.AddUpdateAttribute("UPDATE_TIME");
	}
	@Override
	public String GetPrimaryKeyName() {
		return "ID";
	}
}
