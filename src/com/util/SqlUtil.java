/**
 * <pre>
 * 作者:张勇
 * 日期:2014年2月26日 下午12:21:35
 * 描述:
 * </pre>
 */
package com.util;

import java.util.Map;

/**
 * @author aydream
 *
 */
public class SqlUtil {

	/**
	 * 
	 * <pre>
	 * 作者:张勇
	 * 日期:2014年2月26日 下午12:22:38
	 * 描述:组装完整的SQL语句
	 * 修改:添加参数NULL值判断
	 * 修改时间：2014年5月7日 14:26:25
	 * 修改人：张勇
	 * </pre>
	 */
	public static String sqlFormat(String srcSql,Map<String, String> params){
		
		for(Map.Entry<String, String> entry : params.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			
			value = value == null?"":value; 
			
			srcSql = srcSql.replaceAll("@"+key, "'"+value+"'");
			
			value = value.equals("")?"0":value;
			
		    srcSql = srcSql.replaceAll("#"+key, value);
		}
		System.out.println("本次执行SQL:"+srcSql);
		return srcSql;
	}
}
