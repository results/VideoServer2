package io.packet.impl;

import java.net.DatagramPacket;

import io.Logger;
import io.packet.IPacket;
import io.packet.Packet;
import user.User;

public class EmptyPacket extends IPacket {

	@Override
	public void execute() {
		Logger.log("Empty packet Received.");
	}

}
