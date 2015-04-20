package com.douChat.dao.impl;

import com.douChat.dao.MessageDao;
import com.douChat.dao.impl.structure.MessageList;
import com.douChat.entities.DouMessage;

public class MessageDaoImpl implements MessageDao {
	private MessageList list;

	public MessageDaoImpl() {
		System.out.println("MessageDaoImpl construct");
		list = MessageList.getInstance();
	}

	@Override
	public DouMessage[] getMessage(String username, long lastTimeStamp) throws Exception {
		System.out.println("MessageDaoImpl getMessage");
		return list.getMessage(username, lastTimeStamp);
	}

	@Override
	public void addMessage(DouMessage message) throws Exception {
		System.out.println("MessageDaoImpl addMessage");
		list.addMessage(message);
	}
}
