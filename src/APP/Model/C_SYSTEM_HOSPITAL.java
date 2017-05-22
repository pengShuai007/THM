package APP.Model;

import APP.Model.BaseEntity;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年2月22日 下午5:05:37
 * 描述:医院表实体类
 * </pre>
 */
public class C_SYSTEM_HOSPITAL extends BaseEntity {
	private long HOSPITAL_ID;
	private String PROVINCE_CODE;
	private String CITY_CODE;
	private String CITY_NAME;
	private int CITY_ID;
	private String HOSPITAL_NAME;
	private String UNIT_CODE;
	private String MAILING_ADDRESS;
	private String ZIP_CODE;
	private int APPROVED_BED_NUM;
	private Double LONGITUDE;
	private Double LATITUDE;
	private String HOSPITAL_PROPERTY;
	private String HOSPITAL_LEVEL;
	private String HOSPITAL_NUMBER;
	private String LINKMAN;
	private String BANK_DEPOSIT_ADDRESS;
	private String BANK_ACCOUNT;
	private String OPERATOR_NAME;
	private String OPERATOR_DATE;
	private String STATUS;
	private String TERMAIL_ADD;
	private String FRONT_ADD;
	private String PATTERN;
	private String LOGO_PHOTO; //KYEEAPP-991-增加医院logo图片字段-郭文奎
	private String PLATFORM;	//医院平台 KYEEAPP-986 章剑飞
	private int SORT_CODE;
	
	public int getSORT_CODE() {
		return SORT_CODE;
	}

	public void setSORT_CODE(int sORT_CODE) {
		SORT_CODE = sORT_CODE;
	}

	public String getCITY_NAME() {
		return CITY_NAME;
	}

	public void setCITY_NAME(String cITY_NAME) {
		CITY_NAME = cITY_NAME;
	}
	
	public String getPLATFORM() {
		return PLATFORM;
	}

	public void setPLATFORM(String pLATFORM) {
		PLATFORM = pLATFORM;
		this.AddUpdateAttribute("PLATFORM");
	}

	public String getPROVINCE_CODE() {
		return PROVINCE_CODE;
	}

	public void setPROVINCE_CODE(String pROVINCE_CODE) {
		PROVINCE_CODE = pROVINCE_CODE;
		this.AddUpdateAttribute("PROVINCE_CODE");
	}

	public String getCITY_CODE() {
		return CITY_CODE;
	}

	public void setCITY_CODE(String cITY_CODE) {
		CITY_CODE = cITY_CODE;
		this.AddUpdateAttribute("CITY_CODE");
	}

	public String getLOGO_PHOTO() {
		return LOGO_PHOTO;
	}

	public void setLOGO_PHOTO(String lOGO_PHOTO) {
		LOGO_PHOTO = lOGO_PHOTO;
		this.AddUpdateAttribute("LOGO_PHOTO");
	}

	public String getPATTERN() {
		return PATTERN;
	}

	public void setPATTERN(String pATTERN) {
		PATTERN = pATTERN;
		this.AddUpdateAttribute("PATTERN");
	}

	public String getTERMAIL_ADD() {
		return TERMAIL_ADD;
	}

	public void setTERMAIL_ADD(String tERMAIL_ADD) {
		TERMAIL_ADD = tERMAIL_ADD;
		this.AddUpdateAttribute("TERMAIL_ADD");
	}

	public String getFRONT_ADD() {
		return FRONT_ADD;
	}

	public void setFRONT_ADD(String fRONT_ADD) {
		FRONT_ADD = fRONT_ADD;
		this.AddUpdateAttribute("FRONT_ADD");
	}

	public long getHOSPITAL_ID() {
		return HOSPITAL_ID;
	}

	public void setHOSPITAL_ID(long hOSPITAL_ID) {
		HOSPITAL_ID = hOSPITAL_ID;
		this.AddUpdateAttribute("HOSPITAL_ID");
	}

	public int getCITY_ID() {
		return CITY_ID;
	}

	public void setCITY_ID(int cITY_ID) {
		CITY_ID = cITY_ID;
		this.AddUpdateAttribute("CITY_ID");
	}

	public String getHOSPITAL_NAME() {
		return HOSPITAL_NAME;
	}

	public void setHOSPITAL_NAME(String hOSPITAL_NAME) {
		HOSPITAL_NAME = hOSPITAL_NAME;
		this.AddUpdateAttribute("HOSPITAL_NAME");
	}

	public String getUNIT_CODE() {
		return UNIT_CODE;
	}

	public void setUNIT_CODE(String uNIT_CODE) {
		UNIT_CODE = uNIT_CODE;
		this.AddUpdateAttribute("UNIT_CODE");
	}

	public String getMAILING_ADDRESS() {
		return MAILING_ADDRESS;
	}

	public void setMAILING_ADDRESS(String mAILING_ADDRESS) {
		MAILING_ADDRESS = mAILING_ADDRESS;
		this.AddUpdateAttribute("MAILING_ADDRESS");
	}

	public String getZIP_CODE() {
		return ZIP_CODE;
	}

	public void setZIP_CODE(String zIP_CODE) {
		ZIP_CODE = zIP_CODE;
		this.AddUpdateAttribute("ZIP_CODE");
	}

	public int getAPPROVED_BED_NUM() {
		return APPROVED_BED_NUM;
	}

	public void setAPPROVED_BED_NUM(int aPPROVED_BED_NUM) {
		APPROVED_BED_NUM = aPPROVED_BED_NUM;
		this.AddUpdateAttribute("APPROVED_BED_NUM");
	}

	public Double getLONGITUDE() {
		return LONGITUDE;
	}

	public void setLONGITUDE(Double lONGITUDE) {
		LONGITUDE = lONGITUDE;
		this.AddUpdateAttribute("LONGITUDE");
	}

	public Double getLATITUDE() {
		return LATITUDE;
	}

	public void setLATITUDE(Double lATITUDE) {
		LATITUDE = lATITUDE;
		this.AddUpdateAttribute("LATITUDE");
	}

	public String getHOSPITAL_PROPERTY() {
		return HOSPITAL_PROPERTY;
	}

	public void setHOSPITAL_PROPERTY(String hOSPITAL_PROPERTY) {
		HOSPITAL_PROPERTY = hOSPITAL_PROPERTY;
		this.AddUpdateAttribute("HOSPITAL_PROPERTY");
	}

	public String getHOSPITAL_LEVEL() {
		return HOSPITAL_LEVEL;
	}

	public void setHOSPITAL_LEVEL(String hOSPITAL_LEVEL) {
		HOSPITAL_LEVEL = hOSPITAL_LEVEL;
		this.AddUpdateAttribute("HOSPITAL_LEVEL");
	}

	public String getHOSPITAL_NUMBER() {
		return HOSPITAL_NUMBER;
	}

	public void setHOSPITAL_NUMBER(String hOSPITAL_NUMBER) {
		HOSPITAL_NUMBER = hOSPITAL_NUMBER;
		this.AddUpdateAttribute("HOSPITAL_NUMBER");
	}

	public String getLINKMAN() {
		return LINKMAN;
	}

	public void setLINKMAN(String lINKMAN) {
		LINKMAN = lINKMAN;
		this.AddUpdateAttribute("LINKMAN");
	}

	public String getBANK_DEPOSIT_ADDRESS() {
		return BANK_DEPOSIT_ADDRESS;
	}

	public void setBANK_DEPOSIT_ADDRESS(String bANK_DEPOSIT_ADDRESS) {
		BANK_DEPOSIT_ADDRESS = bANK_DEPOSIT_ADDRESS;
		this.AddUpdateAttribute("BANK_DEPOSIT_ADDRESS");
	}

	public String getBANK_ACCOUNT() {
		return BANK_ACCOUNT;
	}

	public void setBANK_ACCOUNT(String bANK_ACCOUNT) {
		BANK_ACCOUNT = bANK_ACCOUNT;
		this.AddUpdateAttribute("BANK_ACCOUNT");
	}

	public String getOPERATOR_NAME() {
		return OPERATOR_NAME;
	}

	public void setOPERATOR_NAME(String oPERATOR_NAME) {
		OPERATOR_NAME = oPERATOR_NAME;
		this.AddUpdateAttribute("OPERATOR_NAME");
	}

	public String getOPERATOR_DATE() {
		return OPERATOR_DATE;
	}

	public void setOPERATOR_DATE(String oPERATOR_DATE) {
		OPERATOR_DATE = oPERATOR_DATE;
		this.AddUpdateAttribute("OPERATOR_DATE");
	}
	
	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
		this.AddUpdateAttribute("STATUS");
	}

	@Override
	public String GetPrimaryKeyName() {
		return "HOSPITAL_ID";
	}

}
