package io;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
	
	public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static HashMap<String,Object> JsonToMap(String json) {
		if(json != null || gson != null ) {
			return gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
		}
		return null;
	}
	
	public JsonElement get(String key, JsonObject map) {
		return (map.has(key) ? map.get(key) : null);//throws a jsonoperatino exception?
	}
		
	public String getAsString(String key, JsonObject map) {
		return (map.has(key) ? map.get(key).getAsString() : "null");
	}
	public int getAsInt(String key, JsonObject map) {
		return (map.has(key) ? map.get(key).getAsInt() : -1);
	}
	public boolean getAsBoolean(String key, JsonObject map) {
		return (map.has(key) ? map.get(key).getAsBoolean(): false);
	}
	public long getAsLong(String key, JsonObject map) {
		return (map.has(key) ? map.get(key).getAsLong(): -1);
	}
	public double getAsDouble(String key, JsonObject map) {
		return (map.has(key) ? map.get(key).getAsDouble(): -1.00);
	}
	
	

}
