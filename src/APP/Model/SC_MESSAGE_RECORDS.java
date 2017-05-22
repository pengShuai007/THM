package APP.Model;
/**
*
*<pre>
* 任务: 
* 描述：病友圈消息记录表实体类
* 作者：zhangbohong
* 时间：2017年01月03日 11:34:08
* 类名：SC_MESSAGE_RECORDS
*
* <pre>
*/
public class SC_MESSAGE_RECORDS extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private int ID;
	private String MSG_SENDER;
	private String MSG_RECEIVER;
	private int DEVICE_TYPE; // 发送者设备类型  1: Android Phone 2: iPhone  10: iPad  11: Android Pad  20: PC
	private int MSG_LEN;
	private int MSG_TYPE; // 消息类型 1：文本消息  2：语音消息   3：视频消息   4：表情消息  5：位置消息  6：文件
	private String MSG_CONTENT; // 消息内容
	private String MSG_FILE_URL; // 文件url
	private String MSG_FILE_NAME; // 文件名称
	private String GROUP_ID;
	private String MSG_ID;
	private String MSG_FILE_SIZE;
	private String CREATE_TIME; // 记录创建时间
	private String CUSTOM_DATA; // 用户自定义数据
	private String REMARK; // 备注

	public int getID(){
		return ID;
	}
	public void setID(int iD){
		this.ID = iD;
		this.AddUpdateAttribute("ID");
	}
	public String getMSG_SENDER(){
		return MSG_SENDER;
	}
	public void setMSG_SENDER(String mSG_SENDER){
		this.MSG_SENDER = mSG_SENDER;
		this.AddUpdateAttribute("MSG_SENDER");
	}
	public String getMSG_RECEIVER(){
		return MSG_RECEIVER;
	}
	public void setMSG_RECEIVER(String mSG_RECEIVER){
		this.MSG_RECEIVER = mSG_RECEIVER;
		this.AddUpdateAttribute("MSG_RECEIVER");
	}
	public int getDEVICE_TYPE(){
		return DEVICE_TYPE;
	}
	public void setDEVICE_TYPE(int dEVICE_TYPE){
		this.DEVICE_TYPE = dEVICE_TYPE;
		this.AddUpdateAttribute("DEVICE_TYPE");
	}
	public int getMSG_LEN(){
		return MSG_LEN;
	}
	public void setMSG_LEN(int mSG_LEN){
		this.MSG_LEN = mSG_LEN;
		this.AddUpdateAttribute("MSG_LEN");
	}
	public int getMSG_TYPE(){
		return MSG_TYPE;
	}
	public void setMSG_TYPE(int mSG_TYPE){
		this.MSG_TYPE = mSG_TYPE;
		this.AddUpdateAttribute("MSG_TYPE");
	}
	public String getMSG_CONTENT(){
		return MSG_CONTENT;
	}
	public void setMSG_CONTENT(String mSG_CONTENT){
		this.MSG_CONTENT = mSG_CONTENT;
		this.AddUpdateAttribute("MSG_CONTENT");
	}
	public String getMSG_FILE_URL(){
		return MSG_FILE_URL;
	}
	public void setMSG_FILE_URL(String mSG_FILE_URL){
		this.MSG_FILE_URL = mSG_FILE_URL;
		this.AddUpdateAttribute("MSG_FILE_URL");
	}
	public String getMSG_FILE_NAME(){
		return MSG_FILE_NAME;
	}
	public void setMSG_FILE_NAME(String mSG_FILE_NAME){
		this.MSG_FILE_NAME = mSG_FILE_NAME;
		this.AddUpdateAttribute("MSG_FILE_NAME");
	}
	public String getGROUP_ID(){
		return GROUP_ID;
	}
	public void setGROUP_ID(String gROUP_ID){
		this.GROUP_ID = gROUP_ID;
		this.AddUpdateAttribute("GROUP_ID");
	}
	public String getMSG_ID(){
		return MSG_ID;
	}
	public void setMSG_ID(String mSG_ID){
		this.MSG_ID = mSG_ID;
		this.AddUpdateAttribute("MSG_ID");
	}
	public String getMSG_FILE_SIZE(){
		return MSG_FILE_SIZE;
	}
	public void setMSG_FILE_SIZE(String mSG_FILE_SIZE){
		this.MSG_FILE_SIZE = mSG_FILE_SIZE;
		this.AddUpdateAttribute("MSG_FILE_SIZE");
	}
	public String getCREATE_TIME(){
		return CREATE_TIME;
	}
	public void setCREATE_TIME(String cREATE_TIME){
		this.CREATE_TIME = cREATE_TIME;
		this.AddUpdateAttribute("CREATE_TIME");
	}
	public String getCUSTOM_DATA(){
		return CUSTOM_DATA;
	}
	public void setCUSTOM_DATA(String cUSTOM_DATA){
		this.CUSTOM_DATA = cUSTOM_DATA;
		this.AddUpdateAttribute("CUSTOM_DATA");
	}
	public String getREMARK(){
		return REMARK;
	}
	public void setREMARK(String rEMARK){
		this.REMARK = rEMARK;
		this.AddUpdateAttribute("REMARK");
	}
	@Override
	public String GetPrimaryKeyName() {
		return "ID";
	}
}