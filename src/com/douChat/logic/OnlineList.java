package com.douChat.logic;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.douChat.util.RandomUtil;

public class OnlineList {
	private static OnlineList instance;
	private Map<String, OnlineList.LoginInfo> key_info;
	private Map<String, String> name_key;

	private OnlineList() {
		key_info = new ConcurrentHashMap<String, OnlineList.LoginInfo>();
		name_key = new ConcurrentHashMap<String, String>();
	}

	public static OnlineList getInstance() {
		return instance == null ? (instance = new OnlineList()) : (instance);
	}

	public String login(String name, String ip) {
		// Gen accessKey
		String accessKey;
		do {
			accessKey = RandomUtil.generateRandomString(32);
		} while (!key_info.keySet().contains(accessKey));
		// Login
		if (name_key.containsKey(name)) {
			key_info.remove(name_key.get(name));
			name_key.remove(name);
		}
		name_key.put(name, accessKey);
		key_info.put(accessKey, new LoginInfo(accessKey, ip, name));

		return accessKey;
	}

	class LoginInfo {
		String accessKey;
		String ipAddress;
		String name;

		public LoginInfo(String accessKey, String ipAddress, String name) {
			super();
			this.accessKey = accessKey;
			this.ipAddress = ipAddress;
			this.name = name;
		}
	}
}
