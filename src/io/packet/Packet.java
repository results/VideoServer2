package io.packet;

import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.Logger;

public class Packet {

	public Packet(HashMap<String, Object> data) {
		this.packetData = data;
	}
	
	HashMap<String, Object> packetData = new HashMap<>();
	
	public HashMap<String, Object> getDataMap() {
		return packetData;
	}

	private String json = "";

	public String getJson() {
		return this.json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public byte[] getBytes() {
		return this.json.getBytes();
	}

	public Object get(String key) {
		return this.getDataMap().get(key);
	}

	public int getInt(String key) {
		return this.getDataMap().containsKey(key) ? (Integer.parseInt(getDataMap().get(key).toString())) : -1;
	}

	public Double getDouble(String key) {
		return this.getDataMap().containsKey(key) ? (Double.parseDouble(getDataMap().get(key).toString())) : -1;
	}

	public Long getLong(String key) {
		return this.getDataMap().containsKey(key) ? (Long.parseLong(getDataMap().get(key).toString())) : -1;
	}

	public String getString(String key) {
		return this.getDataMap().containsKey(key) ? (getDataMap().get(key).toString()) : "";
	}

	public int getType() {
		return getInt("Packet");
	}

	public int getLength() {
		return this.json.length();
	}

	public String getAddress() {
		return this.getString("Address");
	}

	public String getUID() {
		return this.getString("UID");
	}

	public int getPort() {
		return this.getInt("Port");
	}

	@Override
	public String toString() {
		return this.json;
	}

}