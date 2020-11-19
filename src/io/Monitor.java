package io;

import oshi.SystemInfo;

public class Monitor {
	
	
	
	
	
	/***
	 * JVM info
	 */
	public static final int NUMBER_OF_PROCESSORS = Runtime.getRuntime().availableProcessors();
	
	public static long MAX_MEMORY = Runtime.getRuntime().maxMemory();
	
	public static long AVAIL_MEMORY = Runtime.getRuntime().freeMemory();
	
	public static long TOTAL_MEMORY = Runtime.getRuntime().totalMemory();
	
	
	
	SystemInfo si = new SystemInfo();


}
