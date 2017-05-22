package model.base.ext;

import model.base.SYS_CODE_DICT;

/**
 * 任务:
 * 描述:
 * 作者:luojing
 * 日期:2015年1月8日下午10:27:58
 * OCS
 */
public class SYS_CODE_DICT_EXT extends SYS_CODE_DICT{
	public String getID(){
		return getDICT_ID() + "";
	}
}
