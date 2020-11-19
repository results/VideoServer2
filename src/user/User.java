package user;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;

import io.Logger;

public class User {
	
	private HashMap<String, Object> attributes = new HashMap<String,Object>();
	
	public Object get(String attribute) {
		if(attributes != null) {
			if(!attributes.isEmpty()) {
				//if(attributes.containsKey(attribute)) {
					return attributes.get(attribute);
				}
		//	}
		}
		return null;
	}
	
	public int getInt(String key) {
		return this.attributes.containsKey(key) ? (Integer.parseInt(this.attributes.get(key).toString())) : -1;
	}

	public Double getDouble(String key) {
		return this.attributes.containsKey(key) ? (Double.parseDouble(this.attributes.get(key).toString())) : -1;
	}

	public Long getLong(String key) {
		return this.attributes.containsKey(key) ? (Long.parseLong(this.attributes.get(key).toString())) : -1;
	}

	public String getString(String key) {
		return this.attributes.containsKey(key) ? (this.attributes.get(key).toString()) : "";
	}
	
	public boolean addAttribute(String key, Object value) {
		if(this.getAttributes() != null) {
		//if(this.getAttributes().containsKey(key)) {//do not add more than 1 with same key
				this.getAttributes().putIfAbsent(key, value);
				//Logger.log("att add" + key);
				//Logger.log(this.getAttributes().keySet());
				return true;
		//	}
		}
		return false;
	}
	
	public HashMap<String,Object> getAttributes() {
		return attributes;
	}
	
	public User(String username, String password, String sessionID, InetAddress address,int port) {
		setName(username);
		setPassword(password);
		setSessionID(sessionID);
		setiAddress(address);
		setPort(port);
		//this.attributes = new HashMap<String,Object>();
	}
	
	public User(String username, String password) {
		setName(username);
		setPassword(password);
		this.attributes.put("Username",username);
		this.attributes.put("Password", password);
		//this.addAttribute("Username", username);
		//this.addAttribute("Password", password);
	}
	
	public User(HashMap<String,Object> loadedAttributes) {
		this.attributes = loadedAttributes;
	}
	
	private InetSocketAddress socketAddress;
	
	
	
	
	private String UID;
	
	public String getUID() {
		return UID;
	}
	
	public void setUID(String UID) {
		this.UID = UID;
	}
	
	private String name;
	private String password;
	private int rights;
	
	private int port;
	private String address;
	private InetAddress iAddress;
	private String sessionID;
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
		this.attributes.put("Port", port);
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address.replace("/", "");
		this.attributes.put("Address", address.replace("/", ""));
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public InetAddress getiAddress() {
		return iAddress;
	}
	public void setiAddress(InetAddress iAddress) {
		this.iAddress = iAddress;
	}
	public String getName() {
		return (name != null ? name : this.get("Username").toString());
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRights() {
		return rights;
	}
	public void setRights(int rights) {
		this.rights = rights;
	}

	public InetSocketAddress getSocketAddress() {
		return socketAddress;
	}

	public void setSocketAddress(InetSocketAddress socketAddress) {
		this.socketAddress = socketAddress;
		this.attributes.put("Socket", socketAddress);
		this.setPort(socketAddress.getPort());
		this.setAddress(socketAddress.getHostString());
	}
	
	

}
