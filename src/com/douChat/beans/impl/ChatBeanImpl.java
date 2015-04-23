package com.douChat.beans.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
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
		BufferedImage generatedImage = generate(douPic, message);
		String picId = dicd.doCache(generatedImage);
		String username = ub.getUsername(accessKey);
		if (username == null) {
			return null;
		}
		DouMessage douMessage = new DouMessage(username, picId, message);
		// md.addMessage(douMessage);
		return douMessage;
	}

	private BufferedImage generate(DouPic douPic, String message) throws Exception {
		BufferedImage buffImg = douPic.getImage();
		buffImg = buffImg.getSubimage(0, 0, buffImg.getWidth(), buffImg.getHeight());

		Color coverColor = douPic.getCoverColor();
		Integer[] coverAreaX = douPic.getCoverVerticeX();
		Integer[] coverAreaY = douPic.getCoverVerticeY();
		String fontFamily = douPic.getFontFamily();
		Graphics2D g2d = (Graphics2D) buffImg.getGraphics();

		g2d.setPaint(coverColor);
		g2d.fillRect(coverAreaX[0], coverAreaY[0], coverAreaX[1], coverAreaY[1]);

		int coverWidth = Math.abs(coverAreaX[1] - coverAreaX[0]);
		int coverHeight = Math.abs(coverAreaY[1] - coverAreaY[0]);
		int fontSize = coverWidth / message.length();
		if (fontSize >= coverHeight) {
			fontSize = (int) (coverHeight * 0.9);
		} else {
			fontSize = (int) (Math.sqrt(coverWidth * coverHeight / message.length()) * 0.9);
		}
		int lineLen = message.length();
		int lineCnt = 1;
		if (lineLen > coverWidth / fontSize) {
			lineLen = coverWidth / fontSize;
			lineCnt = message.length() / lineLen + 1;
		}

		Font font = new Font("宋体", Font.PLAIN, fontSize);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		fontSize = fontSize * fontSize / fm.charWidth('测');
		int posX = (coverWidth - fontSize * lineLen) / 3 + (coverAreaX[0] < coverAreaX[1] ? coverAreaX[0] : coverAreaX[1]);
		int posY = (coverHeight - fontSize * lineCnt) / 4 + (coverAreaY[0] < coverAreaY[1] ? coverAreaY[0] : coverAreaY[1]);
		font = new Font(fontFamily, Font.BOLD, fontSize);
		g2d.setFont(font);
		g2d.setPaint(Color.black);
		for (int i = 0; i < lineCnt; i++) {
			posY = posY + fontSize;
			if (i != lineCnt - 1) {
				g2d.drawString(message.substring(i * lineLen, (i + 1) * lineLen), posX, posY);
			} else {
				g2d.drawString(message.substring(i * lineLen), posX, posY);
			}
		}
		g2d.dispose();
		return buffImg;
	}
}
