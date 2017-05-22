package org.kyee.core.framework.SysParamMgr.base;

import APP.Model.BaseEntity;

@SuppressWarnings("serial")
public class SYS_PARAMETER extends BaseEntity {

	private long SYS_PARA_ID;
	private String PARA_TYPE;
	private long IS_GLOBAL;
	private String PARA_CODE;
	private String PARA_NAME;
	private String PARA_VALUE;
	private String PARA_DESC;
	private long IS_INIT_PARA;
	private String COMMENTS;
	private String SUB_SYSTEM;
	private String BUSINESS_CODE;
	private long IS_DISPLAY;
	private long IF_HAVE_SOURCE;
	private String CONTROL_TYPE;
	private String PARA_VALUES;
	private String SOURCE_TABLE;
	private String VALUE_COLUMN;
	private String DISPLAY_COLUMN;
	private long IF_MULTI_CHECK;
	private String FILTER;
	private String CREATOR_NAME;
	private String CREATOR;
	private String CREATE_TIME;
	private String UPDATER;
	private String UPDATE_TIME;
	private long DISPLAY_ORDER;
	private String CONTENT_HREF_LINK;
	private long COLSPAN;
	
	/**
	 * <summary> /// SYS_PARA_ID /// </summary>
	 */
	public void setSYS_PARA_ID(long value) {
		SYS_PARA_ID = value;
		this.AddUpdateAttribute("SYS_PARA_ID");
	}

	public long getSYS_PARA_ID() {
		return SYS_PARA_ID;
	}

	/**
	 * <summary> /// 参数类型 /// </summary>
	 */
	public void setPARA_TYPE(String value) {
		PARA_TYPE = value;
		this.AddUpdateAttribute("PARA_TYPE");
	}

	public String getPARA_TYPE() {
		return PARA_TYPE;
	}

	/**
	 * <summary> /// 是否全局参数 /// </summary>
	 */
	public void setIS_GLOBAL(long value) {
		IS_GLOBAL = value;
		this.AddUpdateAttribute("IS_GLOBAL");
	}

	public long getIS_GLOBAL() {
		return IS_GLOBAL;
	}

	/**
	 * <summary> /// 参数代码 /// </summary>
	 */
	public void setPARA_CODE(String value) {
		PARA_CODE = value;
		this.AddUpdateAttribute("PARA_CODE");
	}

	public String getPARA_CODE() {
		return PARA_CODE;
	}

	/**
	 * <summary> /// 参数名称 /// </summary>
	 */
	public void setPARA_NAME(String value) {
		PARA_NAME = value;
		this.AddUpdateAttribute("PARA_NAME");
	}

	public String getPARA_NAME() {
		return PARA_NAME;
	}

	/**
	 * <summary> /// 参数值 /// </summary>
	 */
	public void setPARA_VALUE(String value) {
		PARA_VALUE = value;
		this.AddUpdateAttribute("PARA_VALUE");
	}

	public String getPARA_VALUE() {
		return PARA_VALUE;
	}

	/**
	 * <summary> /// 参数说明 /// </summary>
	 */
	public void setPARA_DESC(String value) {
		PARA_DESC = value;
		this.AddUpdateAttribute("PARA_DESC");
	}

	public String getPARA_DESC() {
		return PARA_DESC;
	}

	/**
	 * <summary> /// 是否初始化参数 /// </summary>
	 */
	public void setIS_INIT_PARA(long value) {
		IS_INIT_PARA = value;
		this.AddUpdateAttribute("IS_INIT_PARA");
	}

	public long getIS_INIT_PARA() {
		return IS_INIT_PARA;
	}

	/**
	 * <summary> /// 备注 /// </summary>
	 */
	public void setCOMMENTS(String value) {
		COMMENTS = value;
		this.AddUpdateAttribute("COMMENTS");
	}

	public String getCOMMENTS() {
		return COMMENTS;
	}

	/**
	 * <summary> /// 所属系统 /// </summary>
	 */
	public void setSUB_SYSTEM(String value) {
		SUB_SYSTEM = value;
		this.AddUpdateAttribute("SUB_SYSTEM");
	}

	public String getSUB_SYSTEM() {
		return SUB_SYSTEM;
	}

	/**
	 * <summary> /// 业务分类 /// </summary>
	 */
	public void setBUSINESS_CODE(String value) {
		BUSINESS_CODE = value;
		this.AddUpdateAttribute("BUSINESS_CODE");
	}

	public String getBUSINESS_CODE() {
		return BUSINESS_CODE;
	}

	/**
	 * <summary> /// 是否显示 /// </summary>
	 */
	public void setIS_DISPLAY(long value) {
		IS_DISPLAY = value;
		this.AddUpdateAttribute("IS_DISPLAY");
	}

	public long getIS_DISPLAY() {
		return IS_DISPLAY;
	}

	/**
	 * <summary> /// 选择项取值方式 /// </summary>
	 */
	public void setIF_HAVE_SOURCE(long value) {
		IF_HAVE_SOURCE = value;
		this.AddUpdateAttribute("IF_HAVE_SOURCE");
	}

	public long getIF_HAVE_SOURCE() {
		return IF_HAVE_SOURCE;
	}

	/**
	 * <summary> /// 控件类型 /// </summary>
	 */
	public void setCONTROL_TYPE(String value) {
		CONTROL_TYPE = value;
		this.AddUpdateAttribute("CONTROL_TYPE");
	}

	public String getCONTROL_TYPE() {
		return CONTROL_TYPE;
	}

	/**
	 * <summary> /// 参数值集合 /// </summary>
	 */
	public void setPARA_VALUES(String value) {
		PARA_VALUES = value;
		this.AddUpdateAttribute("PARA_VALUES");
	}

	public String getPARA_VALUES() {
		return PARA_VALUES;
	}

	/**
	 * <summary> /// 取值来源 /// </summary>
	 */
	public void setSOURCE_TABLE(String value) {
		SOURCE_TABLE = value;
		this.AddUpdateAttribute("SOURCE_TABLE");
	}

	public String getSOURCE_TABLE() {
		return SOURCE_TABLE;
	}

	/**
	 * <summary> /// 取值字段 /// </summary>
	 */
	public void setVALUE_COLUMN(String value) {
		VALUE_COLUMN = value;
		this.AddUpdateAttribute("VALUE_COLUMN");
	}

	public String getVALUE_COLUMN() {
		return VALUE_COLUMN;
	}

	/**
	 * <summary> /// 显示字段 /// </summary>
	 */
	public void setDISPLAY_COLUMN(String value) {
		DISPLAY_COLUMN = value;
		this.AddUpdateAttribute("DISPLAY_COLUMN");
	}

	public String getDISPLAY_COLUMN() {
		return DISPLAY_COLUMN;
	}

	/**
	 * <summary> /// 是否可以复选 /// </summary>
	 */
	public void setIF_MULTI_CHECK(long value) {
		IF_MULTI_CHECK = value;
		this.AddUpdateAttribute("IF_MULTI_CHECK");
	}

	public long getIF_MULTI_CHECK() {
		return IF_MULTI_CHECK;
	}

	/**
	 * <summary> /// 过滤条件 /// </summary>
	 */
	public void setFILTER(String value) {
		FILTER = value;
		this.AddUpdateAttribute("FILTER");
	}

	public String getFILTER() {
		return FILTER;
	}

	/**
	 * <summary> /// 创建者姓名 /// </summary>
	 */
	public void setCREATOR_NAME(String value) {
		CREATOR_NAME = value;
		this.AddUpdateAttribute("CREATOR_NAME");
	}

	public String getCREATOR_NAME() {
		return CREATOR_NAME;
	}

	/**
	 * <summary> /// 创建者 /// </summary>
	 */
	public void setCREATOR(String value) {
		CREATOR = value;
		this.AddUpdateAttribute("CREATOR");
	}

	public String getCREATOR() {
		return CREATOR;
	}

	/**
	 * <summary> /// 创建时间 /// </summary>
	 */
	public void setCREATE_TIME(String value) {
		CREATE_TIME = value;
		this.AddUpdateAttribute("CREATE_TIME");
	}

	public String getCREATE_TIME() {
		return CREATE_TIME;
	}

	/**
	 * <summary> /// 更新者 /// </summary>
	 */
	public void setUPDATER(String value) {
		UPDATER = value;
		this.AddUpdateAttribute("UPDATER");
	}

	public String getUPDATER() {
		return UPDATER;
	}

	/**
	 * <summary> /// 更新时间 /// </summary>
	 */
	public void setUPDATE_TIME(String currentTime) {
		UPDATE_TIME = currentTime;
		this.AddUpdateAttribute("UPDATE_TIME");
	}

	public String getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	/**
	 * <summary> /// 显示顺序 /// </summary>
	 */
	public void setDISPLAY_ORDER(long value) {
		DISPLAY_ORDER = value;
		this.AddUpdateAttribute("DISPLAY_ORDER");
	}

	public long getDISPLAY_ORDER() {
		return DISPLAY_ORDER;
	}

	/**
	 * <summary> /// 页面内容URL /// </summary>
	 */
	public void setCONTENT_HREF_LINK(String value) {
		CONTENT_HREF_LINK = value;
		this.AddUpdateAttribute("CONTENT_HREF_LINK");
	}

	public String getCONTENT_HREF_LINK() {
		return CONTENT_HREF_LINK;
	}

	/**
	 * <summary> /// COLSPAN /// </summary>
	 */
	public void setCOLSPAN(long value) {
		COLSPAN = value;
		this.AddUpdateAttribute("COLSPAN");
	}

	public long getCOLSPAN() {
		return COLSPAN;
	}

	// / <summary>
	// / 获取主键字段
	// / </summary>
	@Override
	public String GetPrimaryKeyName() {
		return "SYS_PARA_ID";
	}
}
