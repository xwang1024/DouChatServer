package com.douChat.dao.impl.structure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.douChat.util.RandomUtil;

public class UserList {
	private static UserList instance;
	private Map<String, UserList.LoginInfo> key_info;
	private Map<String, String> name_key;

	private UserList() {
		key_info = new ConcurrentHashMap<String, UserList.LoginInfo>();
		name_key = new ConcurrentHashMap<String, String>();
	}

	public static UserList getInstance() {
		return instance == null ? (instance = new UserList()) : (instance);
	}
	
	public String getUsername(String accessKey) {
		return key_info.get(accessKey).name;
	}

	public synchronized boolean existsUser(String name) {
		return name_key.containsKey(name);
	}

	public synchronized String addUser(String username, String ip, String devId) {
		// Gen accessKey
		String accessKey;
		do {
			accessKey = RandomUtil.generateRandomString(32);
		} while (key_info.keySet().contains(accessKey));
		// Login
		if (name_key.containsKey(username)) {
			key_info.remove(name_key.get(username));
			name_key.remove(username);
		}
		name_key.put(username, accessKey);
		key_info.put(accessKey, new LoginInfo(accessKey, ip, username, devId));

		return accessKey;
	}

	class LoginInfo {
		String accessKey;
		String ipAddress;
		String name;
		String devId;

		public LoginInfo(String accessKey, String ipAddress, String name, String devId) {
			super();
			this.accessKey = accessKey;
			this.ipAddress = ipAddress;
			this.name = name;
			this.devId = devId;
		}
	}
}
