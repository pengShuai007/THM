package APP.Comm.DataBase.DotNet;

import java.sql.Types;

/**
 * comments:为减少从dotnet迁移至java的工作量，使用此类型进行占位，保持之前的逻辑 降低编译错误数
 * 
 * sjl modify 2013-10-5上午8:57:28
 */
public class OracleParameter {

	private String parName = null;
	private OracleType parType;
	private int parSize = 0;
	private Object value = null;
	// 针对存储过程有效
	// inOrOut=0表示为传入参数，inOrOut=1表示为传出参数
	// 施建龙
	// 2013年10月22日16:41:51
	private int inOrOut = 0;
	private int returnDataType = Types.VARCHAR;

	public OracleParameter(String parName, OracleType parType, int parSize,
			String value,int inOrOut,int returnDataType) {

		this.parName = parName;
		this.parType = parType;
		this.parSize = parSize;
		this.value = value;
		this.inOrOut=inOrOut;
		this.returnDataType=returnDataType;
	}
	
	public OracleParameter(String parName, OracleType parType, int parSize,
			String value) {

		this.parName = parName;
		this.parType = parType;
		this.parSize = parSize;
		this.value = value;
	}

	public OracleParameter(String parName, OracleType parType, int parSize) {

		this.parName = parName;
		this.parType = parType;
		this.parSize = parSize;
	}

	public OracleParameter(String parName, OracleType parType) {

		this.parName = parName;
		this.parType = parType;
	}

	public OracleParameter(String parName, Object value) {

		this.parName = parName;
		this.value = value;
	}

	public void setValue(Object value) {

		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public String getParName() {
		return parName;
	}

	public void setParName(String parName) {
		this.parName = parName;
	}

	public OracleType getParType() {
		return parType;
	}

	public void setParType(OracleType parType) {
		this.parType = parType;
	}

	public int getParSize() {
		return parSize;
	}

	public void setParSize(int parSize) {
		this.parSize = parSize;
	}

	public int getInOrOut() {
		return inOrOut;
	}

	public void setInOrOut(int inOrOut) {
		this.inOrOut = inOrOut;
	}

	public int getReturnDataType() {
		return returnDataType;
	}

	public void setReturnDataType(int returnDataType) {
		this.returnDataType = returnDataType;
	}

}
