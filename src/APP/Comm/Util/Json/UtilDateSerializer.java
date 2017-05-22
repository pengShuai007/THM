package APP.Comm.Util.Json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * comments:
 * 
 * sjl modify 2013-10-4����12:38:00
 */
public class UtilDateSerializer implements JsonSerializer<java.util.Date> {

	@Override
	public JsonElement serialize(java.util.Date src, Type typeOfSrc,
			JsonSerializationContext context) {
		return new JsonPrimitive(src.getTime());
	}

}
