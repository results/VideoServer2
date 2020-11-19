package io.packet;

import java.net.DatagramPacket;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.JsonUtil;
import io.Logger;

public class DecodePacket {
		static int pkt = 0;
		public static void decode(DatagramPacket received, boolean queue) {
			try {
				byte[] byt = received.getData();
				HashMap<String,Object> decodedPacket = JsonUtil.JsonToMap(new String(byt,0,byt.length).trim());
				decodedPacket.putIfAbsent("Address", received.getAddress().toString());
				decodedPacket.putIfAbsent("Port", received.getPort());
				Logger.log("Decoded packet! as "+decodedPacket.toString());
				if(queue) {//add packet to queue
					PacketHandler.getPacketInQueue().offer(new Packet(decodedPacket));
					Logger.log("Receievd : "+(++pkt));
				}
				decodedPacket = null;
				received = null;
				byt = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
}
