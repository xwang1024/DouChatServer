package com.douChat.beans;

import com.douChat.entities.DouMessage;

public interface ChatBean {
	public DouMessage[] getMessage(String username, long lastTimeStamp) throws Exception;
	public DouMessage sendMessage(String username, String message) throws Exception;
}
