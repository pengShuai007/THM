package APP.Model;

import java.util.Date;

/**
*
*<pre>
* 任务: 
* 描述：实体类
* 作者：mo
* 时间：2016年12月06日 10:08:20
* 类名：C_MESSAGE_CHECK_INFO
*
* <pre>
*/
public class C_MESSAGE_CHECK_INFO extends BaseEntity {

	private static final long serialVersionUID = 1L;

//	private int ID; // 主键
	private int CHANNEL_ID; // 服务商id
	private String CHANNEL_NAME; // 服务商名称
	private Date SEND_TIME; // 发送时间
	private int MAXTRANS; // 调用总量
	private int TRANSYES; // 调用成功量
	private int TRANSNO; // 调用失败量
	private float TRANSPER; // 调用失败率
	private int MAXPUSH; // 服务商返回总量
	private int PUSHYES; // 服务商返回成功量


	public int getCHANNEL_ID(){
		return CHANNEL_ID;
	}
	public void setCHANNEL_ID(int cHANNEL_ID){
		this.CHANNEL_ID = cHANNEL_ID;
		this.AddUpdateAttribute("CHANNEL_ID");
	}
	public String getCHANNEL_NAME(){
		return CHANNEL_NAME;
	}
	public void setCHANNEL_NAME(String cHANNEL_NAME){
		this.CHANNEL_NAME = cHANNEL_NAME;
		this.AddUpdateAttribute("CHANNEL_NAME");
	}
	public Date getSEND_TIME(){
		return SEND_TIME;
	}
	public void setSEND_TIME(Date sEND_TIME){
		this.SEND_TIME = sEND_TIME;
		this.AddUpdateAttribute("SEND_TIME");
	}
	public int getMAXTRANS(){
		return MAXTRANS;
	}
	public void setMAXTRANS(int mAXTRANS){
		this.MAXTRANS = mAXTRANS;
		this.AddUpdateAttribute("MAXTRANS");
	}
	public int getTRANSYES(){
		return TRANSYES;
	}
	public void setTRANSYES(int tRANSYES){
		this.TRANSYES = tRANSYES;
		this.AddUpdateAttribute("TRANSYES");
	}
	public int getTRANSNO(){
		return TRANSNO;
	}
	public void setTRANSNO(int tRANSNO){
		this.TRANSNO = tRANSNO;
		this.AddUpdateAttribute("TRANSNO");
	}
	public float getTRANSPER(){
		return TRANSPER;
	}
	public void setTRANSPER(float tRANSPER){
		this.TRANSPER = tRANSPER;
		this.AddUpdateAttribute("TRANSPER");
	}
	public int getMAXPUSH(){
		return MAXPUSH;
	}
	public void setMAXPUSH(int mAXPUSH){
		this.MAXPUSH = mAXPUSH;
		this.AddUpdateAttribute("MAXPUSH");
	}
	public int getPUSHYES(){
		return PUSHYES;
	}
	public void setPUSHYES(int pUSHYES){
		this.PUSHYES = pUSHYES;
		this.AddUpdateAttribute("PUSHYES");
	}
	@Override
	public String GetPrimaryKeyName() {
		return "ID";
	}
}
