package APP.Login.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 从Json对象中获取指定参数
 * @author MA
 *
 */
public class ListFromJson {
	public static String getValueFromData(JSONObject json,String param){
		String value = "";
		JSONObject jsonData = (JSONObject) json.get("data");
		if(jsonData != null){
			value = jsonData.getString(param);
		}
		return value;
	}
	public static List<Map<String,String>> getListFromRows(JSONObject json, String... param){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(json.get("data") instanceof JSONObject){
			JSONObject jsonData = (JSONObject) json.get("data");
			if(jsonData != null){
				if(jsonData.get("rows") != null && !jsonData.get("rows").equals("null")){
					List<JSONObject> listRow = jsonData.getJSONArray("rows");
					if(listRow != null){
						for(JSONObject j : listRow){
							Map<String,String> map = new HashMap<String,String>();
							for(int i=0;i<param.length;i++){
								if(j.containsKey(param[i])){
									map.put(param[i], j.get(param[i]).toString());
								}
							}
							list.add(map);
						}
					}
				}
			}
			return list;
		}else if(json.get("data") instanceof List){
			list = (List<Map<String,String>>)json.get("data");
		}
		return list;
	}
	
	public static List getSonListFromJson(JSONObject json, String prop, String value, String param){
		List list = new ArrayList();
		JSONObject jsonData = (JSONObject) json.get("data");
		if(jsonData != null){
			List<JSONObject> listRow  =jsonData.getJSONArray("rows");
			if(listRow != null){
				for(JSONObject j : listRow){
					if(j.getString(prop).equals(value)){
						list = j.getJSONArray(param);
					}
				}
			}
		}
		return list;
	}
}
