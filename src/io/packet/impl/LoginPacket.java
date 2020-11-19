package io.packet.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.HashMap;

import io.Logger;
import io.Util;
import io.packet.IPacket;
import io.packet.Packet;
import io.packet.PacketHandler;
import server.Config;
import server.Server;
import user.User;
import user.UserHandler;
import user.UserSaving;

public class LoginPacket extends IPacket {
	
	public static enum loginResponse {
		ERROR,
		INVALID_PASSWORD,
		NEW_ACCOUNT,
		SUCCESS;
	}
	
	private int response = loginResponse.ERROR.ordinal();
	
	
	@Override
	public void execute() {
		//String UID = packet.getUID();//shouldnt be anything
		String username =  packet.getString("Username");
		String password =  packet.getString("Password");
		socket = PacketHandler.getSocketAddress(packet.getAddress(), packet.getPort());// so we know where to respond
		//Logger.log(username+password);
		Object[] loginResponse = new Object[2];
		try {
			loginResponse = Server.getUserSaving().loadGame(username, password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		client = (User) loginResponse[0];
		response = (int) loginResponse[1];
	}
	
	@Override
	public void respond() {
		responsePacket.put("Packet", 3);//Packet ID
		responsePacket.put("LoginResponse", response);
		if(client != null && socket != null && response == loginResponse.SUCCESS.ordinal()) {
			String uid = Util.generateUID();
			client.setUID(uid);
			client.setSocketAddress(socket);
			UserHandler.addUser(uid, client);
			client.addAttribute("UID", uid);
			responsePacket.put("UID", uid);
			client.addAttribute("Socket", socket);
			//Server.getUserSaving().saveGame(client);			
		}
	}

}
