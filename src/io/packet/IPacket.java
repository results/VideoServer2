package io.packet;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;

import io.Logger;
import user.User;

public abstract class IPacket {
	
	public User client;
	public Packet packet;
	
	public HashMap<String,Object> responsePacket = new HashMap<String,Object>();
	
	public InetSocketAddress socket;
	
	public void execute() {
		
	}
	
	public void respond() {
		
	}
	
	public void setVars(User c, Packet packet) {
		this.client = c;
		this.packet = packet;
	}
	
	public void handlePacket(User c, Packet packet) {
		this.setVars(c,packet);
		this.execute();
		this.respond();
		if(!this.responsePacket.isEmpty()) {//packet response has data, encode and queue
			if(c != null) {
				responsePacket.putIfAbsent("Address", c.getString("Address"));
				responsePacket.putIfAbsent("Port", c.getInt("Port"));
			}
			if(socket != null && c == null) {
				responsePacket.putIfAbsent("Address", socket.getHostString().replace("/", ""));
				responsePacket.putIfAbsent("Port", socket.getPort());
			}
			if(responsePacket.containsKey("Address") && responsePacket.containsKey("Port")) {
				EncodePacket.encode(responsePacket, true);
				Logger.log("Encoded and queued: "+responsePacket.toString());
			} else {
				Logger.log("Failed to encode packet. Missing socket information: "+responsePacket.toString());
			}
		}
		
	}
	
}

