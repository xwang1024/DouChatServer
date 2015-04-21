package com.douChat.beans;

import com.douChat.entities.DouMessage;

public interface ChatBean {
	public DouMessage sendMessage(String accessKey, String message) throws Exception;
}
