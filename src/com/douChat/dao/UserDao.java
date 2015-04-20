package com.douChat.dao;

public interface UserDao {
	public boolean existUser(String username);

	public boolean addUser(String username);
}
