package com.douChat.beans.impl;

import java.awt.image.BufferedImage;

import com.douChat.beans.ChatBean;
import com.douChat.beans.UserBean;
import com.douChat.dao.DouIconCacheDao;
import com.douChat.dao.DouIconDao;
import com.douChat.dao.impl.DouIconCacheDaoImpl;
import com.douChat.dao.impl.DouIconDaoImpl;
import com.douChat.entities.DouMessage;
import com.douChat.entities.DouPic;

public class ChatBeanImpl implements ChatBean {
	private DouIconDao did;
	private DouIconCacheDao dicd;
	private UserBean ub;

	public ChatBeanImpl() throws Exception {
		did = new DouIconDaoImpl();
		dicd = new DouIconCacheDaoImpl();
		ub = new UserBeanImpl();
	}

	@Override
	public DouMessage sendMessage(String accessKey, String message) throws Exception {
		DouPic douPic = did.getRandomPic();
		BufferedImage generatedImage = DouPicHelper.generate(douPic, message);
		String picId = dicd.doCache(generatedImage);
		String username = ub.getUsername(accessKey);
		if (username == null) {
			return null;
		}
		DouMessage douMessage = new DouMessage(username, picId, message);
//		md.addMessage(douMessage);
		return douMessage;
	}

}
