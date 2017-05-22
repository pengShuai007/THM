
package APP.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import APP.Model.Base.Ext.C_SYSTEM_CITY_EXT;



/**
 * <pre>
 * 作者:刘健
 * 日期:2014年3月19日 上午11:21:35
 * 描述:
 * </pre>
 */
public class C_SYSTEM_PROVINCE extends BaseEntity{
	private int PROVINCE_ID;
	private String PROVINCE_CODE;
	private String PROVINCE_NAME;
	private String OPERATOR_NAME;
	private Date OPERATOR_DATE;
	private String PROVINCE_FLG;
	
	private List<C_SYSTEM_CITY_EXT> CITY;

	public int getPROVINCE_ID() {
		return PROVINCE_ID;
	}
	public void setPROVINCE_ID(int pROVINCE_ID) {
		PROVINCE_ID = pROVINCE_ID;
		this.AddUpdateAttribute("PROVINCE_ID");
	}
	public String getPROVINCE_CODE() {
		return PROVINCE_CODE;
	}
	public void setPROVINCE_CODE(String pROVINCE_CODE) {
		PROVINCE_CODE = pROVINCE_CODE;
		this.AddUpdateAttribute("PROVINCE_CODE");
	}
	public String getPROVINCE_NAME() {
		return PROVINCE_NAME;
	}
	public void setPROVINCE_NAME(String pROVINCE_NAME) {
		PROVINCE_NAME = pROVINCE_NAME;
		this.AddUpdateAttribute("PROVINCE_NAME");
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
	public String getPROVINCE_FLG() {
		return PROVINCE_FLG;
	}
	public void setPROVINCE_FLG(String pROVINCE_FLG) {
		PROVINCE_FLG = pROVINCE_FLG;
		this.AddUpdateAttribute("PROVINCE_FLG");
	}
	public List<C_SYSTEM_CITY_EXT> getCITY() {
		return CITY;
	}
	public void setCITY(List<BaseEntity> cITY) {
		List<C_SYSTEM_CITY_EXT> tmpCity=new ArrayList<C_SYSTEM_CITY_EXT>();
		for (BaseEntity c_SYSTEM_CITY_EXT:cITY ){
			tmpCity.add((C_SYSTEM_CITY_EXT)c_SYSTEM_CITY_EXT);
		}
		
		CITY = tmpCity;
	}
}
