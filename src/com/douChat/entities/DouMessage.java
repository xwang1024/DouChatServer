package com.douChat.entities;

import java.util.Date;

public class DouMessage {
	private String sender;
	private int timeStamp;
	private String douPicUrl;
	private String content;

	public DouMessage(String sender, String douPicUrl, String content) {
		this.sender = sender;
		this.timeStamp = genTimeStamp();
		this.douPicUrl = douPicUrl;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDouPicUrl() {
		return douPicUrl;
	}

	public void setDouPicUrl(String douPicUrl) {
		this.douPicUrl = douPicUrl;
	}

}
