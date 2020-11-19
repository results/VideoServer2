package io.packet;

import java.awt.event.ActionListener;

public class test {
	
	public static void main(String[] args) {
		
		System.out.println(iss(alter("ab1221ba")));
	}
	
public static boolean iss(String text) {
	String temp = alter(text);
	return text.equals(temp);
}

public static String alter(String input) {
	if(input == null || input.isEmpty()) {
		return input;
	}
	return input.charAt(input.length()-1) + alter(input.substring(0, input.length()-1));
}

}
