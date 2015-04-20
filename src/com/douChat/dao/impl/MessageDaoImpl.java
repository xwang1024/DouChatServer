package com.douChat.dao.impl;

import com.douChat.dao.MessageDao;
import com.douChat.dao.impl.structure.MessageList;
import com.douChat.entities.DouMessage;

public class MessageDaoImpl implements MessageDao {
	private MessageList list;

	public MessageDaoImpl() {
		list = MessageList.getInstance();
	}

	@Override
	public DouMessage[] getMessage(String username, long lastTimeStamp) throws Exception {
		list.getMessage(username, lastTimeStamp);
		return null;
	}

	@Override
	public void addMessage(DouMessage message) throws Exception {
		list.addMessage(message);
	}
}
