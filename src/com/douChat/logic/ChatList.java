package com.douChat.logic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.douChat.entities.ChatMessage;

public class ChatList {
	private int maxOpacity = 50;
	private int free = 10;
	private List<ChatMessage> list;

	public ChatList() {
		list = Collections.synchronizedList(new LinkedList<ChatMessage>());
	}

	public ChatMessage[] getMessage(int startStamp) {
		if (startStamp == 0) {
			return list.toArray(new ChatMessage[list.size()]);
		}
		int startIndex = list.size() - 1;
		while (startIndex >= 0) {
			if (list.get(startIndex).getTimeStamp() > startStamp) {
				startIndex--;
			}
		}
		ChatMessage[] msgWillShow = new ChatMessage[list.size() - startIndex];
		for (int i = 0; i < msgWillShow.length; i++) {
			msgWillShow[i] = list.get(startIndex + i);
		}
		return msgWillShow;
	}

	public void addMessage(ChatMessage msg) {
		if (list.size() > maxOpacity) {
			for (int i = 0; i < free; i++)
				list.remove(0);
		}
		list.add(msg);
	}
}
