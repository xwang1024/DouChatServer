package com.douChat.beans.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.douChat.entities.DouPic;

public class DouPicHelper {
	public static BufferedImage generate(DouPic douPic, String message) {
		BufferedImage buffImg = douPic.getImage();
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
			fontSize = coverHeight - 5;
		} else {
			fontSize = (int) Math.sqrt(coverWidth * coverHeight / message.length()) - 10;
		}
		int lineLen = message.length();
		int lineCnt = 1;
		if (lineLen > coverWidth / fontSize) {
			lineLen = coverWidth / fontSize;
			lineCnt = message.length() / lineLen + 1;
		}

		Font font = new Font(fontFamily, Font.BOLD, fontSize);
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
