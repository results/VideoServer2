package io.packet;

import io.packet.impl.*;

public enum PacketType {
	
	/******
	 * Inbound Packets
	 *******/
	EMPTY_PACKET(0, new EmptyPacket()),
	LOGIN_PACKET(3, new LoginPacket()),
	FILE_REQUEST(4, new FileRequest());
	/********
	 * Outbound Packets
	 ********/
	//SESSION_REPONSE(4);
	
	int packetType = 0;
	IPacket refPacket;
	
	public IPacket packet() {
		return this.refPacket;
	}
	public int type() {
		return this.packetType;
	}
	
	PacketType(int pktType, IPacket packet) {
		this.packetType = pktType;
		this.refPacket = packet;
	}

}
