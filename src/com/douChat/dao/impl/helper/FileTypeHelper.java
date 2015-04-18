package com.douChat.dao.impl.helper;

public class FileTypeHelper {
	private static final String[] imagePostfix = {"jpg","png","bmp","jpeg"};
	public static boolean isImageFile(String fileName) {
		String[] sp = fileName.split("\\.");
		String postfix = sp[sp.length-1].toLowerCase();
		for(int i = 0; i < imagePostfix.length; i++) {
			if(imagePostfix[i].equals(postfix)) {
				return true;
			}
		}
		return false; 
	}
}
