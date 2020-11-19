package task.impl;

import java.io.IOException;
import java.net.DatagramPacket;

import io.Logger;
import io.packet.IPacket;
import io.packet.Packet;
import io.packet.PacketHandler;
import server.Server;
import task.Task;

public class PacketSenderTask extends Task{

	@Override
	public Object call() {
		if(!PacketHandler.getPacketOutQueue().isEmpty()) {
			//Logger.log(PacketHandler.getPacketOutQueue().toArray().toString());
			Packet packet = PacketHandler.getPacketOutQueue().poll();
			if(packet != null) {
				Logger.log(packet.getAddress());
				DatagramPacket outbound = new DatagramPacket(packet.getBytes(),packet.getBytes().length,
						PacketHandler.getSocketAddress(packet.getAddress(),packet.getPort()));//error here
				Logger.log("Sentaaa");
				try {
					Server.getSocket().send(outbound);
					outbound = null;
					packet = null;
					Logger.log("Sent");
				} catch (IOException e) {
					outbound = null;
					packet = null;
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
