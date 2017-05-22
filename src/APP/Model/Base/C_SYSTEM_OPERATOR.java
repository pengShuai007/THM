package APP.Model.Base;

import APP.Model.BaseEntity;

public class C_SYSTEM_OPERATOR extends BaseEntity {
	private long OPERATOR_ID;
	private String OPERATOR_NAME;
	private String PASS_WORD;

	public long getOPERATOR_ID() {
		return OPERATOR_ID;
	}

	public void setOPERATOR_ID(long oPERATOR_ID) {
		OPERATOR_ID = oPERATOR_ID;
		this.AddUpdateAttribute("OPERATOR_ID");
	}

	public String getOPERATOR_NAME() {
		return OPERATOR_NAME;
	}

	public void setOPERATOR_NAME(String oPERATOR_NAME) {
		OPERATOR_NAME = oPERATOR_NAME;
		this.AddUpdateAttribute("OPERATOR_NAME");
	}

	public String getPASS_WORD() {
		return PASS_WORD;
	}

	public void setPASS_WORD(String pASS_WORD) {
		PASS_WORD = pASS_WORD;
		this.AddUpdateAttribute("PASS_WORD");
	}

	/**
	 * 获取主键字段
	 */
	@Override
	public String GetPrimaryKeyName() {
		return "OPERATOR_ID";
	}

}
