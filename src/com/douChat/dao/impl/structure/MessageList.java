package com.douChat.dao.impl.structure;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.douChat.entities.DouMessage;

public class MessageList {
	private static MessageList instance;
	private int maxOpacity = 50;
	private int free = 10;
	private List<DouMessage> list;

	private MessageList() {
		list = Collections.synchronizedList(new LinkedList<DouMessage>());
	}

	public static MessageList getInstance() {
		return instance == null ? (instance = new MessageList()) : (instance);
	}

	public DouMessage[] getMessage(String username, long startStamp) {
		if (startStamp == 0) {
			return list.toArray(new DouMessage[list.size()]);
		}
		Stack<DouMessage> msgStack = new Stack<DouMessage>();
		int startIndex = list.size() - 1;
		while (startIndex >= 0) {
			DouMessage message = list.get(startIndex);
			if (message.getTimeStamp() >= startStamp) {
				if (!message.getSender().equals(username)) {
					msgStack.push(message);
				}
				startIndex--;
			}
		}
		DouMessage[] msgWillShow = new DouMessage[msgStack.size()];
		for (int i = 0; i < msgWillShow.length; i++) {
			msgWillShow[i] = msgStack.pop();
		}
		return msgWillShow;
	}

	public void addMessage(DouMessage msg) {
		if (list.size() > maxOpacity) {
			for (int i = 0; i < free; i++)
				list.remove(0);
		}
		list.add(msg);
	}
}
