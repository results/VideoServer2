package task.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.Logger;
import io.packet.DecodePacket;
import io.packet.Packet;
import io.packet.PacketHandler;
import server.Config;
import server.Server;
import task.Task;
import user.User;
import user.UserHandler;

public class PacketReceiverTask extends Task{

	@Override
	public Object call() {
		if(!PacketHandler.getPacketInQueue().isEmpty()) {
			User client = null;
			Packet packet = PacketHandler.getPacketInQueue().poll();//CHANGE TO POLL TO WORK
			if(packet != null) {
				client = UserHandler.getUser(packet.getUID());
				if(client != null || (client == null && packet.getType() == 4)) {//CHNAGE TO LOGIN PACKET HERE
					if(PacketHandler.validPacket(packet)) {
						try {
							PacketHandler.getPacketType()[packet.getType()].handlePacket(client, packet);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						Logger.log("Unhandled packet received.Id "+packet.getType()+" : len "+packet.getLength()+" UID"+packet.getUID());
					}			
				}
				packet = null;
				client = null;
			}
		}
		return null;
	}
}
