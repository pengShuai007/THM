package APP.Comm.Util;

import APP.Comm.View.*;

/** 
 json输出的类型
 
*/
public enum OutJsonType
{
   /** 
	通用的格式
	
   */
	Common,
	/** 
	 Grid 插件类型的格式
	 
	*/
	Grid,
	/** 
	 combox 插件类型的格式
	 
	*/
	Combox,
	/** 
	 tree  插件类型的格式
	 
	*/
	Tree;

	public int getValue()
	{
		return this.ordinal();
	}

	public static OutJsonType forValue(int value)
	{
		return values()[value];
	}
}