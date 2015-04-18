package com.douChat.dao.impl.helper;

public class ColorHelper {
	public static int HexToRGB(String color) throws Exception {
		color = color.trim();
		if(!color.startsWith("#")) {
			throw new Exception("Unknown color format");
		}
		return Integer.parseInt(color.substring(1),16);
	}
}
