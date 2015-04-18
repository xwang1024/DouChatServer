package com.douChat.dao.impl.helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHelper {
	public static BufferedImage getImage(String path) throws IOException {
		File f = new File(path);
		if (!f.exists()) {
			throw new IOException("File \"" + path + "\" not found");
		}
		return getImage(f);
	}

	public static BufferedImage getImage(File file) throws IOException {
		BufferedImage img = ImageIO.read(file);
		return img;
	}
}
