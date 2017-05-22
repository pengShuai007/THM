package org.kyee.core.framework.SysDictMgr.base;

import APP.Model.BaseEntity;

/**
 * 任务:
 * 描述:
 * 作者:luojing
 * 日期:2015年1月8日下午10:25:48
 * OCS
 */
public  class SYS_CODE_DICT extends BaseEntity
{ 
	private String DICT_ID;
	private String SUB_SYSTEM;
	private String DICT_NAME;
	private String DICT_SCOPE;
	private String BUSINESS_CODE;
	private long IS_DISPLAY;
	private String COMMENTS;
	private String CREATOR;
	private String CREATE_TIME;
	private String UPDATER;
	private String UPDATE_TIME;
	private String UPDATER_NAME;
	private String CREATOR_NAME;

	/**
	 * <summary> /// 代码编号 /// </summary>
	 */
	public void setDICT_ID(String value) {
		DICT_ID = value;
		this.AddUpdateAttribute("DICT_ID");
	}

	public String getDICT_ID() {
		return DICT_ID;
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
	 * <summary> /// 代码说明 /// </summary>
	 */
	public void setDICT_NAME(String value) {
		DICT_NAME = value;
		this.AddUpdateAttribute("DICT_NAME");
	}

	public String getDICT_NAME() {
		return DICT_NAME;
	}

	/**
	 * <summary> /// 代码类型 /// </summary>
	 */
	public void setDICT_SCOPE(String value) {
		DICT_SCOPE = value;
		this.AddUpdateAttribute("DICT_SCOPE");
	}

	public String getDICT_SCOPE() {
		return DICT_SCOPE;
	}

	/**
	 * <summary> /// /// </summary>
	 */
	public void setBUSINESS_CODE(String value) {
		BUSINESS_CODE = value;
		this.AddUpdateAttribute("BUSINESS_CODE");
	}

	public String getBUSINESS_CODE() {
		return BUSINESS_CODE;
	}

	/**
	 * <summary> /// /// </summary>
	 */
	public void setIS_DISPLAY(long value) {
		IS_DISPLAY = value;
		this.AddUpdateAttribute("IS_DISPLAY");
	}

	public long getIS_DISPLAY() {
		return IS_DISPLAY;
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
	public void setUPDATE_TIME(String value) {
		UPDATE_TIME = value;
		this.AddUpdateAttribute("UPDATE_TIME");
	}

	public String getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	/**
	 * <summary> /// 更新者姓名 /// </summary>
	 */
	public void setUPDATER_NAME(String value) {
		UPDATER_NAME = value;
		this.AddUpdateAttribute("UPDATER_NAME");
	}

	public String getUPDATER_NAME() {
		return UPDATER_NAME;
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

	// / <summary>
	// / 获取主键字段
	// / </summary>
	@Override
	public String GetPrimaryKeyName() {
		return "DICT_ID";
	}
}

