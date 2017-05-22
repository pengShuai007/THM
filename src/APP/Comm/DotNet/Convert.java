package APP.Comm.DotNet;

import java.math.BigDecimal;
import java.util.Date;

import APP.Comm.Util.CommonUtil;
import APP.Comm.BLL.BaseBllException;
/**
 * comments:辅助DotNet迁移类
 * 
 * sjl modify 2013-11-7下午10:14:58
 */
public class Convert {

	public static BigDecimal ToDecimal(String value){
		return new BigDecimal(value);
	}
	
	public static Date ToDateTime(String value) throws BaseBllException{
		return CommonUtil.parse2SqlDate(value);
	}
	
	
	public static Date ToDateTime(String value,String pattern) throws BaseBllException{
		return CommonUtil.parse2SqlDate(value,pattern);
	}
	
}
