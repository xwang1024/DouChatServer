package com.douChat.entities;

import java.util.Date;

public class ChatMessage {
	private String sender;
	private int timeStamp;
	private String douPicName;
	private String cacheId;
	private String content;

	public ChatMessage(String sender, String douPicName, String content) {
		this.sender = sender;
		this.timeStamp = genTimeStamp();
		this.douPicName = douPicName;
		this.content = content;
	}

	private int genTimeStamp() {
		Date date = new Date();
		return (int) (date.getTime() / 10000);
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getDouPicName() {
		return douPicName;
	}

	public void setDouPicName(String douPicName) {
		this.douPicName = douPicName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCacheId() {
		return cacheId;
	}

	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}

	
}
