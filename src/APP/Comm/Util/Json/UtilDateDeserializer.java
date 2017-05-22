package APP.Comm.Util.Json;

import java.lang.reflect.Type;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.Util.CommonUtil;
import APP.Comm.Util.HLogger;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * comments:日期转换辅助类
 * 
 * sjl modify 2013-10-4下午12:35:58
 */
public class UtilDateDeserializer implements JsonDeserializer<java.util.Date> {

	@Override
	public java.util.Date deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		// return new java.util.Date(json.getAsJsonPrimitive().getAsString());
		java.sql.Date date = null;
		try {
			date = CommonUtil.parse2SqlDate(json.getAsJsonPrimitive()
					.getAsString());
		} catch (BaseBllException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			HLogger.error(e);
		}
		return date;
	}

}
