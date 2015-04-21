package com.douChat.dao.impl;

import com.douChat.dao.UserDao;
import com.douChat.dao.impl.structure.UserList;

public class UserDaoImpl implements UserDao {

	private UserList list;

	public UserDaoImpl() {
		System.out.println("UserDaoImpl construct");
		list = UserList.getInstance();
	}

	@Override
	public boolean existsUser(String username) {
		System.out.println("UserDaoImpl existsUser");
		return list.existsUser(username);
	}

	@Override
	public String addUser(String username, String ip, String devId) {
		System.out.println("UserDaoImpl addUser");
		return list.addUser(username, ip, devId);
	}
	
	@Override
	public String getUsername(String accessKey) {
		System.out.println("UserDaoImpl getUsername");
		return list.getUsername(accessKey);
	}
}
