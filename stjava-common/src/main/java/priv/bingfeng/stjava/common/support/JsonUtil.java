package priv.bingfeng.stjava.common.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 * @author 姜宇 jiangyu@blwit.com
 * @company Bluewit
 * @createDate 2018年3月23日 下午3:21:50
 */
public class JsonUtil {
	
	public static String toString(Object obj) {
		return JSON.toJSONString(obj);
	}

	public static String toStringAll(Object obj) {
		return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
	}

	public static <T> T parseToObject(String content, Class<T> t) {
		return JSON.parseObject(content, t);
	}
	
	public static <T> T parseToObject(String content, TypeReference<T> type) {
		return JSON.parseObject(content, type);
	}
	
	public static <T> List<T> parseToList(String content, Class<T> t) {
		return JSON.parseArray(content, t);
	}
	
	public static Map<String, String> parseToMap(String content) {
		return JSON.parseObject(content, new TypeReference<Map<String, String>>(){});
	}
	
	public static JSONObject parseToJsonObject(String content) {
		return JSON.parseObject(content);
	}
	
	public static JSONArray parseToJsonArray(String content) {
		return JSON.parseArray(content);
	}
	
	public static Object getAttribute(String content, String attr) {
		return JSON.parseObject(content).get(attr);
	}
	
	public static String parseToString(String content) {
		return JSON.parseObject("\"" + content + "\"", String.class);
	}

	
	/** 循环获取json属性 */
	public static JSONObject getJsonObject(JSONObject jsonObj, String... array) {
		JSONObject obj = getLastJsonObj(jsonObj, array);
		if (obj != null)
			return obj.getJSONObject(array[array.length - 1]);
		return null;
	}
	
	public static JSONArray getJsonArray(JSONObject jsonObj, String... array) {
		JSONObject obj = getLastJsonObj(jsonObj, array);
		if (obj != null)
			return obj.getJSONArray(array[array.length - 1]);
		return null;
	}
	
	public static String getString(JSONObject jsonObj, String... array) {
		JSONObject obj = getLastJsonObj(jsonObj, array);
		if (obj != null)
			return obj.getString(array[array.length - 1]);
		return "";
	}
	
	public static int getInt(JSONObject jsonObj, String... array) {
		JSONObject obj = getLastJsonObj(jsonObj, array);
		if (obj != null)
			return obj.getIntValue(array[array.length - 1]);
		return 0;
	}
	
	public static float getFloat(JSONObject jsonObj, String... array) {
		JSONObject obj = getLastJsonObj(jsonObj, array);
		if (obj != null)
			return obj.getFloatValue(array[array.length - 1]);
		return 0;
	}
	
	public static double getDouble(JSONObject jsonObj, String... array) {
		JSONObject obj = getLastJsonObj(jsonObj, array);
		if (obj != null)
			return obj.getDoubleValue(array[array.length - 1]);
		return 0;
	}
	
	public static long getLong(JSONObject jsonObj, String... array) {
		JSONObject obj = getLastJsonObj(jsonObj, array);
		if (obj != null)
			return obj.getLongValue(array[array.length - 1]);
		return 0;
	}
	
	public static boolean getBoolean(JSONObject jsonObj, String... array) {
		JSONObject obj = getLastJsonObj(jsonObj, array);
		if (obj != null)
			return obj.getBooleanValue(array[array.length - 1]);
		return false;
	}
	
	private static JSONObject getLastJsonObj(JSONObject jsonObj, String... array) {
		JSONObject result = jsonObj;
		for (int i = 0, len = array.length - 1; i < len; i++) {
			result = result.getJSONObject(array[i]);
			if (result == null)
				break;
		}
		return result;
	}

}
