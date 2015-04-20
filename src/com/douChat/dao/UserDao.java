package com.douChat.dao;

public interface UserDao {
	public boolean existsUser(String username);

	public String addUser(String username, String ip, String devId);
}
