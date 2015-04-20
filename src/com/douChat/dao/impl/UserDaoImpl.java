package com.douChat.dao.impl;

import com.douChat.dao.UserDao;
import com.douChat.dao.impl.structure.UserList;

public class UserDaoImpl implements UserDao {
	private UserList list;

	public UserDaoImpl() {
		list = UserList.getInstance();
	}

	@Override
	public boolean existsUser(String username) {
		return list.existsUser(username);
	}

	@Override
	public String addUser(String username, String ip, String devId) {
		return list.addUser(username, ip, devId);
	}
}
