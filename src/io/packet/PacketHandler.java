package io.packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import io.Logger;
import io.packet.impl.*;
import server.Config;
import server.Server;
import user.User;

public class PacketHandler {
	
	private static IPacket packetType[] = new IPacket[Config.PACKET_COUNT];
		
	private static Queue<Packet> packetInQueue = new LinkedList<>();
	
	public static Queue<Packet> getPacketInQueue() {
		return packetInQueue;
	}
	
	private static Queue<Packet> packetOutQueue = new LinkedList<>();
	
	public static Queue<Packet> getPacketOutQueue() {
		return packetOutQueue;
	}
	
	public static void addPacketToInQueue(Packet packet) {
		packetInQueue.offer(packet);
	}
	
	static {
		for (int i = 0; i < Config.PACKET_COUNT; i++) {
			getPacketType()[i] = new EmptyPacket();//set all packets to empty packet, at least handled
		}
		for(PacketType packetTypeEnum : PacketType.values()) {
			getPacketType()[packetTypeEnum.type()] = packetTypeEnum.packet();
			
		}
	}
	
	public static InetSocketAddress getSocketAddress(String IP, int port) {
		InetSocketAddress address = new InetSocketAddress(IP, port);
		return address;
	}

	public static boolean validPacket(Packet packet) {
		if(packet != null) {
			if(!packet.getUID().isEmpty() || (packet.getUID().isEmpty() && packet.getType() == 4)) {//PacketType.LOGIN_PACKET.type())) {
				//if(!packet.getSessionID().isEmpty()) {
					if(packet.getType() >= 0 && packet.getType() <= Config.PACKET_COUNT) {
						return true;
					}
				}
			//}
		}
		return false;
	}

	public static IPacket[] getPacketType() {
		return packetType;
	}

}
