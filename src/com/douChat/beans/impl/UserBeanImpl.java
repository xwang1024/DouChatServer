package com.douChat.beans.impl;

import com.douChat.beans.UserBean;
import com.douChat.dao.UserDao;
import com.douChat.dao.impl.UserDaoImpl;

public class UserBeanImpl implements UserBean {
	private UserDao userDao;

	public UserBeanImpl() {
		userDao = new UserDaoImpl();
	}

	@Override
	public String login(String username, String remoteIp, String devId) {
		if (userDao.existsUser(username)) {
			return null;
		}
		return userDao.addUser(username, remoteIp, devId);
	}

	@Override
	public String getUsername(String accessKey) throws Exception {
		return userDao.getUsername(accessKey);
	}

	@Override
	public void removeUser(String username) throws Exception {
		userDao.removeUser(username);
	}
}
