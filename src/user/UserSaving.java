package user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import io.Logger;

public class UserSaving {
	
	private String basePath = "./data/users/";
	
	private static enum loadState {
		NEW_ACCOUNT,
		SUCCESS,
		INVALID_PASS,
		ERROR;
	}
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	
	/**
	 * Saves user in json format from hashmap
	 * @param client
	 * @return loadState
	 */
	public int saveGame(User client) {
		if(client.getName() == null) {
			Logger.log("asd");
		}
		Path path = Paths.get(basePath+client.getName()+".json");
		String out = gson.toJson(client.getAttributes());
		try(BufferedWriter writer = Files.newBufferedWriter(path,Charset.forName("UTF-8"))){
			writer.write(out);
			writer.close();
			Logger.log("Saved Game: "+out);
			return loadState.SUCCESS.ordinal();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return loadState.ERROR.ordinal();
	}
		
	/**
	 * Loads local user saves
	 * @param Username
	 * @param Password
	 * @return Object{Client, loginResponse}
	 * @throws IOException 
	 */
	public Object[] loadGame(String Username, String Password) throws IOException {
		Path path = Paths.get(basePath+Username+".json");
		if(!Files.exists(path,new LinkOption[]{ LinkOption.NOFOLLOW_LINKS})) {
			User client = new User(Username,Password);
			return (this.saveGame(client) == loadState.SUCCESS.ordinal() ? new Object[]{client, loadState.NEW_ACCOUNT.ordinal()} : new Object[]{null, loadState.ERROR.ordinal()});//return client + sucess or null + error
		}
		try {
		     if(Files.size(path) > 1) {//catch empty files
			     BufferedReader reader = Files.newBufferedReader(path,Charset.forName("UTF-8"));
			     HashMap<String, Object> jsonMap = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>(){}.getType());
				 reader.close();
				 reader = null;
				 if(!matches(jsonMap.get("Password"),Password)) {
					 return new Object[]{null, loadState.INVALID_PASS.ordinal()};
				 }
				 User client = new User(jsonMap);
				 jsonMap = null;
				 return new Object[]{client, 1};//new User(Username,Password);
		     }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Object[]{null, loadState.ERROR.ordinal()};
	}
	
	public boolean matches(Object one, String two) {
		if(one == null || one.toString() == null || two == null) {
			return false;
		}
		return one.equals(two);
	}
}