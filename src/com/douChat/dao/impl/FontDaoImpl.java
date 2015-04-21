package com.douChat.dao.impl;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.douChat.dao.FontDao;
import com.douChat.dao.impl.conf.DaoConfig;

public class FontDaoImpl implements FontDao {

	@Override
	public Font getFont(String filename, int size) throws Exception {
		File fontDir = new File(DaoConfig.getInstance().getFontPath());
		File fontFile = new File(fontDir, filename);
		try {
			FileInputStream fis = new FileInputStream(fontFile);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, fis);
			Font dynamicFontPt = dynamicFont.deriveFont((float)size);
			fis.close();
			return dynamicFontPt;
		} catch (Exception e) {
			e.printStackTrace();
			return new Font("", Font.PLAIN, 14);
		}
	}
	
	public static void main(String[] args) throws Exception {
		FontDaoImpl fdi = new FontDaoImpl();
		Font font = fdi.getFont("simsun.ttc", 100);
		JFrame f = new JFrame();
		JLabel lb = new JLabel("123测试");
		lb.setFont(font);
		f.add(lb,BorderLayout.CENTER);
		f.setVisible(true);
	}
}
