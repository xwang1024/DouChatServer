package com.douChat.entities;

import java.util.Date;

public class DouMessage {
	private String sender;
	private long timeStamp;
	private String douPicId;
	private String content;

	public DouMessage(String sender, String douPicId, String content) {
		this.sender = sender;
		this.timeStamp = genTimeStamp();
		this.douPicId = douPicId;
		this.content = content;
	}

	private long genTimeStamp() {
		Date date = new Date();
		return date.getTime();
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDouPicId() {
		return douPicId;
	}

	public void setDouPicId(String douPicId) {
		this.douPicId = douPicId;
	}
}
