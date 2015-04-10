package com.ron.utils;

public class StringUtil {

	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}
	
	public static void output(String message){
		System.out.println(message);
	}
}
