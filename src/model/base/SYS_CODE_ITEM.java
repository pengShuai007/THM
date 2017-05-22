package model.base;

import java.math.BigDecimal;

import APP.Model.BaseEntity;


/**
* <pre>
* 任务:
* 描述:
* 作者:luojing
* 日期:2015年1月8日 下午10:19:14
* </pre>
*/
public class SYS_CODE_ITEM extends BaseEntity {

	private String ITEM_ID;
	private String DICT_ID;
	private String ITEM_CODE;
	private String ITEM_NAME;
	private String ITEM_VALUE;
	private BigDecimal PARENT_ITEM_CODE;
	private long ITEM_ORDER;
	private long VALIDATE_FLAG;
	private String COMMENTS;
	private String CREATOR;
	private String CREATE_TIME;
	private String UPDATER;
	private String UPDATE_TIME;
	private String ITEM_SCOPE;

	/**
	 * <summary> /// 项目NO /// </summary>
	 */
	public void setITEM_ID(String value) {
		ITEM_ID = value;
		this.AddUpdateAttribute("ITEM_ID");
	}

	public String getITEM_ID() {
		return ITEM_ID;
	}

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
	 * <summary> /// 项目代码 /// </summary>
	 */
	public void setITEM_CODE(String value) {
		ITEM_CODE = value;
		this.AddUpdateAttribute("ITEM_CODE");
	}

	public String getITEM_CODE() {
		return ITEM_CODE;
	}

	/**
	 * <summary> /// 项目名字 /// </summary>
	 */
	public void setITEM_NAME(String value) {
		ITEM_NAME = value;
		this.AddUpdateAttribute("ITEM_NAME");
	}

	public String getITEM_NAME() {
		return ITEM_NAME;
	}

	/**
	 * <summary> /// 项目值 /// </summary>
	 */
	public void setITEM_VALUE(String value) {
		ITEM_VALUE = value;
		this.AddUpdateAttribute("ITEM_VALUE");
	}

	public String getITEM_VALUE() {
		return ITEM_VALUE;
	}

	/**
	 * <summary> /// 项目级别 /// </summary>
	 */
	public void setPARENT_ITEM_CODE(BigDecimal value) {
		PARENT_ITEM_CODE = value;
		this.AddUpdateAttribute("PARENT_ITEM_CODE");
	}

	public BigDecimal getPARENT_ITEM_CODE() {
		return PARENT_ITEM_CODE;
	}

	/**
	 * <summary> /// 代码项排序顺序 /// </summary>
	 */
	public void setITEM_ORDER(long value) {
		ITEM_ORDER = value;
		this.AddUpdateAttribute("ITEM_ORDER");
	}

	public long getITEM_ORDER() {
		return ITEM_ORDER;
	}

	/**
	 * <summary> /// 是否启用 /// </summary>
	 */
	public void setVALIDATE_FLAG(long value) {
		VALIDATE_FLAG = value;
		this.AddUpdateAttribute("VALIDATE_FLAG");
	}

	public long getVALIDATE_FLAG() {
		return VALIDATE_FLAG;
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
	 * <summary> /// 瀛楀吀椤硅寖鍥? /// </summary>
	 */
	public void setITEM_SCOPE(String value) {
		ITEM_SCOPE = value;
		this.AddUpdateAttribute("ITEM_SCOPE");
	}

	public String getITEM_SCOPE() {
		return ITEM_SCOPE;
	}

	// / <summary>
	// / 获取主键字段
	// / </summary>
	@Override
	public String GetPrimaryKeyName() {
		return "ITEM_ID";
	}
}
