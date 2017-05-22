package model.base.ext;

import model.base.SYS_CODE_ITEM;

/**
* <pre>
* 任务:
* 描述:
* 作者:luojing
* 日期:2015年1月8日 下午10:24:09
* </pre>
*/
public class SYS_CODE_ITEM_EXT extends SYS_CODE_ITEM {
	public String GetId() {
		return getITEM_ID() + "";
	}
}
