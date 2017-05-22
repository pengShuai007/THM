package model.base;

import java.util.Date;

import APP.Model.BaseEntity;

/**
*
*<pre>
* 任务: 
* 描述：记录系统异常表实体类
* 作者：Administrator
* 时间：2016年12月08日 18:42:51
* 类名：M_SYSTEM_EXCEPTION_RECORDS
*
* <pre>
*/
public class M_SYSTEM_EXCEPTION_RECORDS extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String ID; // ID
	private Date VISIT_TIME; // 访问时间
	private String ERROR_MESSAGE; // 异常信息
	private String ERROR_MESSAGE_SIGN; //异常信息MD5值
	private int EXCEPTION_TYPE; // 异常类型，1:info、2:warn、3:debug、4:error
	private String JIRA_NO; // 急了任务号
	private String JIRA_COMMENT; // 急了备注
	private String JIRA_STATE; // 是否已经解决，1：未解决，2解决
	private String EXCEPTION_SOURCE; // 异常来源0:未设置异常来源,1:系统异常,2:非系统异常
	private String ERROR_SQL; // SQL异常是具体的sql语句
	private String CLOUD_OR_TERMINAL; // 异常来源，1：端异常，0：云异常，3:未设置
	private String IF_SQL_EXCEPTION; // 是否为SQL异常，0：否，1：是，默认为否
	private String USER_RESOURCE; //用户来源
	private Date CREATE_TIME;

	public String getID(){
		return ID;
	}
	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
		this.AddUpdateAttribute("CREATE_TIME");
	}
	public void setID(String iD){
		this.ID = iD;
		this.AddUpdateAttribute("ID");
	}
	public Date getVISIT_TIME(){
		return VISIT_TIME;
	}
	public void setVISIT_TIME(Date vISIT_TIME){
		this.VISIT_TIME = vISIT_TIME;
		this.AddUpdateAttribute("VISIT_TIME");
	}
	public String getERROR_MESSAGE(){
		return ERROR_MESSAGE;
	}
	public void setERROR_MESSAGE(String eRROR_MESSAGE){
		this.ERROR_MESSAGE = eRROR_MESSAGE;
		this.AddUpdateAttribute("ERROR_MESSAGE");
	}
	public String getERROR_MESSAGE_SIGN() {
		return ERROR_MESSAGE_SIGN;
	}
	public void setERROR_MESSAGE_SIGN(String eRROR_MESSAGE_SIGN) {
		ERROR_MESSAGE_SIGN = eRROR_MESSAGE_SIGN;
		this.AddUpdateAttribute("ERROR_MESSAGE_SIGN");
	}
	public int getEXCEPTION_TYPE(){
		return EXCEPTION_TYPE;
	}
	public void setEXCEPTION_TYPE(int eXCEPTION_TYPE){
		this.EXCEPTION_TYPE = eXCEPTION_TYPE;
		this.AddUpdateAttribute("EXCEPTION_TYPE");
	}
	public String getJIRA_NO(){
		return JIRA_NO;
	}
	public void setJIRA_NO(String jIRA_NO){
		this.JIRA_NO = jIRA_NO;
		this.AddUpdateAttribute("JIRA_NO");
	}
	public String getJIRA_COMMENT(){
		return JIRA_COMMENT;
	}
	public void setJIRA_COMMENT(String jIRA_COMMENT){
		this.JIRA_COMMENT = jIRA_COMMENT;
		this.AddUpdateAttribute("JIRA_COMMENT");
	}
	public String getJIRA_STATE(){
		return JIRA_STATE;
	}
	public void setJIRA_STATE(String jIRA_STATE){
		this.JIRA_STATE = jIRA_STATE;
		this.AddUpdateAttribute("JIRA_STATE");
	}
	public String getEXCEPTION_SOURCE(){
		return EXCEPTION_SOURCE;
	}
	public void setEXCEPTION_SOURCE(String eXCEPTION_SOURCE){
		this.EXCEPTION_SOURCE = eXCEPTION_SOURCE;
		this.AddUpdateAttribute("EXCEPTION_SOURCE");
	}
	public String getERROR_SQL(){
		return ERROR_SQL;
	}
	public void setERROR_SQL(String eRROR_SQL){
		this.ERROR_SQL = eRROR_SQL;
		this.AddUpdateAttribute("ERROR_SQL");
	}
	public String getCLOUD_OR_TERMINAL(){
		return CLOUD_OR_TERMINAL;
	}
	public void setCLOUD_OR_TERMINAL(String cLOUD_OR_TERMINAL){
		this.CLOUD_OR_TERMINAL = cLOUD_OR_TERMINAL;
		this.AddUpdateAttribute("CLOUD_OR_TERMINAL");
	}
	public String getIF_SQL_EXCEPTION(){
		return IF_SQL_EXCEPTION;
	}
	public void setIF_SQL_EXCEPTION(String iF_SQL_EXCEPTION){
		this.IF_SQL_EXCEPTION = iF_SQL_EXCEPTION;
		this.AddUpdateAttribute("IF_SQL_EXCEPTION");
	}
	public void setUSER_RESOURCE(String value)
	{
		this.USER_RESOURCE = value;
		this.AddUpdateAttribute("USER_RESOURCE");
	}
	public String getUSER_RESOURCE()
	{
		return this.USER_RESOURCE;
	}
	@Override
	public String GetPrimaryKeyName() {
		return "ID";
	}
}
