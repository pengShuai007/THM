package APP.Login.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SystemUtil {
	public static final Long token = 1463022080349l;
	public static final String URLCLOUD  = "app.quyiyuan.com:8888";
	public static Map<String,String> URL = null;

	public static String getToken(String user_id) {
		Connection con = null;
		Statement state = null;
		try {
			con = JDBCUtil.getInstance().getDevCloudConnection();
			state = con.createStatement();
			String sqlSelect = "SELECT TOKEN FROM sys_user_token WHERE USER_ID='" + user_id + "'";
			ResultSet rs = state.executeQuery(sqlSelect);
			if (rs.next()) {
				String token = rs.getString("TOKEN");
				if (!token.equals("")) {
					return token;
				} else {
					String temp = "1111111111111";
					String sqlUpdate = "UPDATE sys_user_token SET TOKEN='"+temp+"' WHERE USER_ID="+user_id;
					state.execute(sqlUpdate);
					return temp;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(con, state);
		}
		return null;
	}

	public static void initURL(String param){
		if(URL == null ){
			URL = new HashMap<String,String>();
			URL.put("serverName", param);
			URL.put("serverLocation", getValue(param));
		}else if(!URL.get("serverName").equals(param)){
			URL.put("serverName", param);
			URL.put("serverLocation", getValue(param));
		}
	}
	
	public static Map<String,String> getURL(){
		return URL;
	}
	
	private static String getValue(String param){
		Connection con = null;
		Statement state = null;
		String value = "";
		try {
			con = JDBCUtil.getInstance().getMainConnection();
			state = con.createStatement();
			String sql = "select SERVER_LOCATION from target_server where SERVER_NAME='"+param+"'";
			ResultSet rs = state.executeQuery(sql);
			if(rs.next()){
				value=rs.getString("SERVER_LOCATION");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(con, state);
		}
		return value;
	}
	
	public static Map<String,String> copyFrom(Map<String,String> map){
		Map<String,String> map2 = new HashMap<String,String>();
		Set<String> setKey = map.keySet();
		Iterator i = setKey.iterator();
		while(i.hasNext()){
			String key = (String) i.next();
			String value = map.get(key);
			map2.put(key, value);
		}	
		return map2;
	}
	
	public static void main(String[] args) {
		System.out.println(getToken("10026301"));
	}
	
}
