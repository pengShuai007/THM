package APP.Comm.Config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

import APP.Comm.Config.Parser.SysConfigParse;
import APP.Comm.Util.HLogger;

public class SystemParams implements Serializable, ISystemParams {

	protected int inputCalendarLength = 12;
	protected int inputTextLength = 15;
	// protected String
	// onMouseOver="this.style.cssText='background-image:url(images/button/primary-enabled-mast.gif);';";
	// protected String
	// onMouseOut="this.style.cssText='background-image:url(images2/button/secondary-enabled.gif);';";
	protected String onMouseOver = "this.style.cssText='background-image:url(../images/button/primary-enabled-mast.gif);';";
	protected String onMouseOut = "this.style.cssText='background-image:url(../images2/button/primary-mini-roll.gif);';";
	//
	protected String defaultHiddenCSS = "visibility: hidden;";
	protected static HashMap sysParams = new HashMap();
	protected static Properties wsdlParams = null;
	protected static HashMap dictionarys = new HashMap();
	static {

	}

	public SystemParams() {
		if (sysParams.size() == 0) {
			SysConfigParse sp = new SysConfigParse();
			sysParams = sp.parse();
		}
		if (wsdlParams == null) {
			SysConfigParse sp = new SysConfigParse();
			wsdlParams = sp.parseProperties();
		}
	}

	/**
	 * comments:�������������¹���
	 * 
	 * sjl modify 2013-10-11����2:57:15
	 */
	public void clear() {
		sysParams = null;
		dictionarys = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#getInputCalendarLength()
	 */
	public int getInputCalendarLength() {
		return inputCalendarLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#setInputCalendarLength(int)
	 */
	public void setInputCalendarLength(int inputCalendarLength) {
		this.inputCalendarLength = inputCalendarLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#getInputTextLength()
	 */
	public int getInputTextLength() {
		return inputTextLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#setInputTextLength(int)
	 */
	public void setInputTextLength(int inputTextLength) {
		this.inputTextLength = inputTextLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#getOnMouseOut()
	 */
	public String getOnMouseOut() {
		return onMouseOut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#setOnMouseOut(java.lang.String)
	 */
	public void setOnMouseOut(String onMouseOut) {
		this.onMouseOut = onMouseOut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#getOnMouseOver()
	 */
	public String getOnMouseOver() {
		return onMouseOver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#setOnMouseOver(java.lang.String)
	 */
	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#addParamas(airsystem.common.Param)
	 */
	public void addParamas(Param param) {
		sysParams.put(param.getKey().toUpperCase(), param.getValue());
	}

	public static String getParamaWSDLValue(String name) {
		HashMap sysParams0 = null;
		SystemParams systemParams = null;
		try {
			systemParams = new SystemParams();
			return systemParams.getWSDLParams().get(name).toString();
		} catch (Exception e) {
			HLogger.error("name:" + name + "----" + e);
		}
		return null;
	}

	public static String getParamaValue(String name) {
		SystemParams systemParams = null;
		try {
			systemParams = new SystemParams();
			return systemParams.getSysParams().get(name.toUpperCase())
					.toString();
		} catch (Exception e) {
			HLogger.error("name:" + name + "----" + e);
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#getSysParams()
	 */
	public HashMap getSysParams() {
		return sysParams;
	}

	public Properties getWSDLParams() {
		return wsdlParams;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#setSysParams(java.util.HashMap)
	 */
	public void setSysParams(HashMap sysParams) {
		this.sysParams = sysParams;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#getDefaultHiddenCSS()
	 */
	public String getDefaultHiddenCSS() {
		return defaultHiddenCSS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#setDefaultHiddenCSS(java.lang.String)
	 */
	public void setDefaultHiddenCSS(String defaultHiddenCSS) {
		this.defaultHiddenCSS = defaultHiddenCSS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#getDictionarys()
	 */
	public HashMap getDictionarys() {
		return dictionarys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see airsystem.common.ISystemParams#setDictionarys(java.util.HashMap)
	 */
	public void setDictionarys(HashMap dictionarys) {
		dictionarys = dictionarys;
	}

}
