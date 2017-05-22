
package APP.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import APP.Model.Base.C_SYSTEM_HOSPITAL_EXT;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年3月19日 上午11:27:00
 * 描述:
 * </pre>
 */
public class C_SYSTEM_CITY extends BaseEntity{
	private int CITY_ID;
	private int PROVINCE_ID;
	private String CITY_CODE;
	private String CITY_NAME;
	private String CITY_FLG;
	private String OPERATOR_NAME;
	private Date OPERATOR_DATE;
	private int SORT_CODE;
	
	public int getSORT_CODE() {
		return SORT_CODE;
	}

	public void setSORT_CODE(int sORT_CODE) {
		SORT_CODE = sORT_CODE;
	}

	private List<C_SYSTEM_HOSPITAL_EXT> HOSPITAL;
	public int getCITY_ID() {
		return CITY_ID;
	}

	public void setCITY_ID(int cITY_ID) {
		CITY_ID = cITY_ID;
		this.AddUpdateAttribute("CITY_ID");
	}

	public int getPROVINCE_ID() {
		return PROVINCE_ID;
	}

	public void setPROVINCE_ID(int pROVINCE_ID) {
		PROVINCE_ID = pROVINCE_ID;
		this.AddUpdateAttribute("PROVINCE_ID");
	}

	public String getCITY_CODE() {
		return CITY_CODE;
	}

	public void setCITY_CODE(String cITY_CODE) {
		CITY_CODE = cITY_CODE;
		this.AddUpdateAttribute("CITY_CODE");
	}

	public String getCITY_NAME() {
		return CITY_NAME;
	}

	public void setCITY_NAME(String cITY_NAME) {
		CITY_NAME = cITY_NAME;
		this.AddUpdateAttribute("CITY_NAME");
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
	
	public String getCITY_FLG() {
		return CITY_FLG;
	}

	public void setCITY_FLG(String cITY_FLG) {
		CITY_FLG = cITY_FLG;
		this.AddUpdateAttribute("CITY_FLG");
	}

	public List<C_SYSTEM_HOSPITAL_EXT> getHOSPITAL() {
		return HOSPITAL;
	}

	public void setHOSPITAL(List<BaseEntity> hOSPITAL) {
		List<C_SYSTEM_HOSPITAL_EXT> temp = new ArrayList<C_SYSTEM_HOSPITAL_EXT>();
		for (BaseEntity c_SYSTEM_HOSPITAL_EXT : hOSPITAL) {
			temp.add((C_SYSTEM_HOSPITAL_EXT)c_SYSTEM_HOSPITAL_EXT);
		}
		HOSPITAL = temp;
	}

}
