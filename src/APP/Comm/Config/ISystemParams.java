package APP.Comm.Config;

import java.io.Serializable;
import java.util.HashMap;


public interface ISystemParams extends Serializable {

	public abstract int getInputCalendarLength();

	public abstract void setInputCalendarLength(int inputCalendarLength);

	public abstract int getInputTextLength();

	public abstract void setInputTextLength(int inputTextLength);

	
	public abstract String getOnMouseOut();

	public abstract void setOnMouseOut(String onMouseOut);

	public abstract String getOnMouseOver();

	public abstract void setOnMouseOver(String onMouseOver);

	public abstract void addParamas(Param param);

	public abstract HashMap getSysParams();

	public abstract void setSysParams(HashMap sysParams);

	public abstract String getDefaultHiddenCSS();

	public abstract void setDefaultHiddenCSS(String defaultHiddenCSS);

	public abstract HashMap getDictionarys();

	public abstract void setDictionarys(HashMap dictionarys);

}