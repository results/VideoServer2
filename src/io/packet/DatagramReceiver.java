package io.packet;

import java.net.DatagramPacket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.Logger;
import server.Config;
import server.Server;


/**
 * Since datagramsocket.receive is blocking it needs to be on its own thread
 * @author sejte
 *
 */
public class DatagramReceiver implements Runnable {
	
	
	public DatagramReceiver() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		Thread.currentThread().setName("DatagramPacket Receiver");
		Logger.log("Started : "+Thread.currentThread().getName());
		scheduler.scheduleAtFixedRate(this, 600, 600, TimeUnit.MILLISECONDS);
	}

	/**
	 * Will block, after UDP received will wait 600ms before checking again
	 */
	@Override
	public void run() {
		byte[] buffer = new byte[Config.RECEIVE_BUFFER];	 
		DatagramPacket received = new DatagramPacket(buffer, buffer.length);
		try {
			if(Server.getSocket() != null) {
				Server.getSocket().receive(received);
				if(received != null) {
					if(received.getLength() > 0) {
						DecodePacket.decode(received,true);
					}
				}
			}
			buffer = null;
			received = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
