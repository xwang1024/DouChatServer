package com.douChat.beans;

public interface UserBean {
	public String login(String username, String remoteIp, String devId) throws Exception;
	public String getUsername(String accessKey) throws Exception;
	public void removeUser(String username) throws Exception;
}
