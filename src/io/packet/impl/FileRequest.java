package io.packet.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AllPermission;
import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.reflect.TypeToken;

import io.Logger;
import io.packet.IPacket;
import io.packet.PacketHandler;
import user.User;

public class FileRequest extends IPacket {
	
	double fileSize = -1;
	byte[] fileBytes;
	
	@Override
	public void execute() {
		socket = PacketHandler.getSocketAddress(packet.getAddress(), packet.getPort());
		Path path = Paths.get("./data/files/image.jpg");
		try {
			fileSize = Files.size(path);
			if(!Files.exists(path,new LinkOption[]{ LinkOption.NOFOLLOW_LINKS})) {
				Logger.log("No file");
		}
			if(fileSize> 1) {//catch empty files
				fileBytes = Files.readAllBytes(path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public byte[][] dividedFile () {
		int position = 0;
		byte[] toAdd;
		byte[][] dogs = new byte[getBlocksForFile()][MAX_SIZE];
		for(int i = 0; i < getBlocksForFile(); i ++) {
			toAdd = Arrays.copyOfRange(fileBytes, position,1);
			dogs[i] = toAdd;
		}
		
		return null;
	}
	
	public double getTo (int block) {
		double to = (block*2) * MAX_SIZE-1;
		if(block == 0) {
			to = MAX_SIZE -1;
		}
		if(to > fileSize) {
			to = fileSize;
		}
		return to;
	}
	
	public byte[] getBlock(int block) {
		double to = getTo(block);
		int from = (block == 0 ? 0 : block * MAX_SIZE);
		byte[] dataBlock = Arrays.copyOfRange(fileBytes, from,(int)to);
		byte[] blockInt = blockInt(block);
		ByteBuffer bb = ByteBuffer.allocate(blockInt.length+dataBlock.length);
		bb.put(blockInt);
		bb.put(dataBlock);
		//Arrays.copyOfRange(fileBytes, from,to);
		return bb.array();
	}
	
	public byte[] blockInt(int value) {
	    return new byte[] {
	            (byte)(value >> 24),
	            (byte)(value >> 16),
	            (byte)(value >> 8),
	            (byte)value};
	}
	
	private int MAX_SIZE = 65503;
	
	public int getBlocksForFile() {
		double blocks = 0;
		if(fileSize > 0) {
			blocks = Math.ceil(fileSize / MAX_SIZE);
		}
		return (int)blocks;
	}
	
	
	public void sendBlocks() {
		for(int i = 0; i < getBlocksForFile(); i ++) {
			Logger.log("Block "+ i +": "+getBlock(i));
		}
		Logger.log("All blocks sent.");
	}
	
	
	@Override
	public void respond() {
		responsePacket.put("Packet",4);
		responsePacket.put("FileName", "image");
		responsePacket.put("FileSize", fileSize);
		responsePacket.put("FileBlocks", getBlocksForFile());
		responsePacket.put("Format", ".jpg");
		sendBlocks();
	}
	

}
