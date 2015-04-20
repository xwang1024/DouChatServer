package com.douChat.beans.impl;

import java.awt.image.BufferedImage;

import com.douChat.beans.ChatBean;
import com.douChat.dao.DouIconCacheDao;
import com.douChat.dao.DouIconDao;
import com.douChat.dao.MessageDao;
import com.douChat.dao.impl.DouIconCacheDaoImpl;
import com.douChat.dao.impl.DouIconDaoImpl;
import com.douChat.dao.impl.MessageDaoImpl;
import com.douChat.entities.DouMessage;
import com.douChat.entities.DouPic;

public class ChatBeanImpl implements ChatBean {
	private DouIconDao did;
	private DouIconCacheDao dicd;
	private MessageDao md;
	
	public ChatBeanImpl() throws Exception {
		did = new DouIconDaoImpl();
		dicd = new DouIconCacheDaoImpl();
		md = new MessageDaoImpl();
	}
	@Override
	public DouMessage[] getMessage(String username, long lastTimeStamp) throws Exception  {
		return md.getMessage(username, lastTimeStamp);
	}

	@Override
	public DouMessage sendMessage(String username, String message) throws Exception {
		DouPic douPic = did.getRandomPic();
		BufferedImage generatedImage = DouPicHelper.generate(douPic, message);
		String picId = dicd.doCache(generatedImage);
		DouMessage douMessage = new DouMessage(message, picId, message);
		md.addMessage(douMessage);
		return douMessage;
	}

}
