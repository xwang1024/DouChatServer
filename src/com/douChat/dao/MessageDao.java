package com.douChat.dao;

import com.douChat.entities.DouMessage;

public interface MessageDao {
	public DouMessage[] getMessage(String username, long lastTimeStamp) throws Exception;

	public void addMessage(DouMessage message) throws Exception;
}
