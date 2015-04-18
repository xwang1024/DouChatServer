package com.douChat.entities;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class DouPic {
	private String path;
	private BufferedImage image;
	private Integer[] coverVerticeX;
	private Integer[] coverVerticeY;
	private Color coverColor;
	private String fontFamily;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Integer[] getCoverVerticeX() {
		return coverVerticeX;
	}

	public void setCoverVerticeX(Integer[] coverVerticeX) {
		this.coverVerticeX = coverVerticeX;
	}

	public Integer[] getCoverVerticeY() {
		return coverVerticeY;
	}

	public void setCoverVerticeY(Integer[] coverVerticeY) {
		this.coverVerticeY = coverVerticeY;
	}

	public Color getCoverColor() {
		return coverColor;
	}

	public void setCoverColor(Color coverColor) {
		this.coverColor = coverColor;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

}
