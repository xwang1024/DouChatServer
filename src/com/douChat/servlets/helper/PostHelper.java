package com.douChat.servlets.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;

public class PostHelper {
	public static Map<String, String> getPostContent(ServletInputStream in) throws IOException {
		BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
		String line;
		Map<String, String> postMap = new HashMap<String, String>();
		while ((line = bfr.readLine()) != null) {
			int index = line.indexOf("=");
			postMap.put(line.substring(0, index), line.substring(index + 1));
		}
		bfr.close();
		return postMap;
	}
}
