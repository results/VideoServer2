package server;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import io.Logger;
import io.packet.DatagramReceiver;
import io.packet.PacketHandler;
import task.TaskHandler;
import task.impl.PacketReceiverTask;
import task.impl.PacketSenderTask;
import user.UserHandler;
import user.UserSaving;

public class Server {
		
	private static DatagramSocket socket;
	private static InetAddress address;
	
	public static DatagramSocket getSocket() {
		return socket;
	}
	
	public static InetAddress getAddress() {
		return address;
	}
	
	//bind port and start server
	private static void bind() {
		try {
			address = InetAddress.getByName(Config.SERVER_IP);
			socket = new DatagramSocket(Config.PORT);
			Logger.log("Sucessfully bound to "+Config.SERVER_IP+" at port "+Config.PORT);
		} catch (SocketException | UnknownHostException e) {
			Logger.log("Failed to bind to "+Config.SERVER_IP+" at port "+Config.PORT);
			e.printStackTrace();
		}
	}
	
	static HashMap<String, Object> packetDatas;
	
	
	private static void loadServer() {
		packetHandler = new PacketHandler();
		userSaving = new UserSaving();
		userHandler = new UserHandler();
		userHandler.startUp();
		new DatagramReceiver();
		new TaskHandler();
		TaskHandler.addTask(new PacketReceiverTask());
		TaskHandler.addTask(new PacketSenderTask());
		bind();
	}
	
	public static void main(String[] args) {
		loadServer();
	}
	
	private static UserSaving userSaving;
	
	public static UserSaving getUserSaving() {
		return userSaving;		
	}
	private static UserHandler userHandler;
	
	public static UserHandler getUserHandler() {
		return userHandler;
	}
	
	private static PacketHandler packetHandler;

	public static PacketHandler getPacketHandler() {
		return packetHandler;
	}

}
