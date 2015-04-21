package com.douChat.dao.impl.structure;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.douChat.entities.DouMessage;

@Deprecated
public class MessageList {
	private static MessageList instance;
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
		for (int i = list.size() - 1; i >= 0; i--) {
			DouMessage message = list.get(i);
			if (message.getTimeStamp() > startStamp) {
				if (username == null) {
					msgStack.push(message);
				} else {
					if (!message.getSender().equals(username)) {
						msgStack.push(message);
					}
				}
			}
		}
		DouMessage[] msgWillShow = new DouMessage[msgStack.size()];
		for (int i = 0; i < msgWillShow.length; i++) {
			msgWillShow[i] = msgStack.pop();
		}
		return msgWillShow;
	}

	public void addMessage(DouMessage msg) {
		// if (list.size() > maxOpacity) {
		// for (int i = 0; i < free; i++)
		// list.remove(0);
		// }
		list.add(msg);
	}
}
