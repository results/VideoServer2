package user;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.Logger;
import server.Config;

public class UserHandler {
	
	private static HashMap<String, User> userList = new HashMap<String,User>();
	
	public static User getUser(String UID) {
		return userList.get(UID);
	}
	
	public static void addUser(String UID, User client) {
		userList.put(UID, client);
	}
	
	public void startUp() {
		activeUsers = new ArrayList<User>();
		activeSessions = new ArrayList<String>();
	}
	
	private  ArrayList<User> activeUsers;

	public  ArrayList<User> getUserList() {
		return activeUsers;
	}
	
	public  User getUserByAddress(InetAddress address) {
		for(User find : getUserList()) {
			if(find.getiAddress().equals(address)) {
				return find;
			}
		}
			return null;
	}
	
	public  User getUserByName(String name) {
		for(User find : getUserList()) {
			if(find.getName().equals(name)) {
				return find;
			}
		}
			return null;
	}
	
	static ArrayList<String> activeSessions;
	
	final static int sessionStringLength = 12;
	
	final static char[] VALID_CHARS = { '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	
	public  String createSessionID() {
		String sessionID = "";
		SecureRandom random = new SecureRandom();
		for(int i = 0; i < sessionStringLength; i++) {
			sessionID += (VALID_CHARS[random.nextInt((VALID_CHARS.length))]);
		}
		for(String find : activeSessions) {
			if(sessionID.equals(find)) {
				sessionID = createSessionID();//recursion to get new random, shouldnt really happen
			}
			activeSessions.add(sessionID);
		}
		Logger.log(sessionID);
		return sessionID;
	}
	
	public  User getUserBySession(String sessionID) {
		for(User find : getUserList()) {
			if(find.getSessionID().equals(sessionID)) {
				return find;
			}
		}
		return null;
	}
	
	public  boolean userOnline(String sessionID, InetAddress address) {
		if(getUserBySession(sessionID) != null) {
			return true;
		} else if(getUserByAddress(address) != null) {//may delete, should all be assigned session unless first packet
			return true;
		}
		return false;
	}
	
	public User getUser(String sessionID, InetAddress address) {
		User client;
		client = getUserBySession(sessionID);
		if(client == null) {
			client = getUserByAddress(address);
		}
		return client;
	}
	
	public boolean activeUser(User client) {
		if(getUserList() != null && client != null) {
			if(getUserList().contains(client) == true) {
				return true;//
			}
		}
		return false;
	}
	
	public  void addUser(User client) {
		if(getUserList() != null && client != null) {
			getUserList().add(client);
		}
	}
	
	public void removeUser(User client) {
		if(getUserList() != null && client != null) {
			getUserList().remove(client);
		}
	}
	
	
	
	
	//useSQL later
	public void loadUser() {
		
	}
	
	public void saveUser() {
		
	}
	
	public void destroyUser() {
		
	}

}
