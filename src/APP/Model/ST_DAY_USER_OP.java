package APP.Model;

public class ST_DAY_USER_OP extends BaseEntity{
//	private int HOSPITAL_ID;
	private String ST_DATE;
	private String OP_TYPE;
	private Number OP_AMT;
	
//	public int getHOSPITAL_ID() {
//		return HOSPITAL_ID;
//	}
//	public void setHOSPITAL_ID(int hOSPITAL_ID) {
//		HOSPITAL_ID = hOSPITAL_ID;
//		this.AddUpdateAttribute("HOSPITAL_ID");
//	}
	public String getST_DATE() {
		return ST_DATE;
	}
	public void setST_DATE(String sT_DATE) {
		ST_DATE = sT_DATE;
		this.AddUpdateAttribute("ST_DATE");
	}
	
	public String getOP_TYPE() {
		return OP_TYPE;
	}
	public void setOP_TYPE(String oP_TYPE) {
		OP_TYPE = oP_TYPE;
		this.AddUpdateAttribute("OP_TYPE");
	}
	
	public Number getOP_AMT() {
		return OP_AMT;
	}
	public void setOP_AMT(Number oP_AMT) {
		OP_AMT = oP_AMT;
		this.AddUpdateAttribute("OP_AMT");
	}
}
