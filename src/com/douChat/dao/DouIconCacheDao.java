package com.douChat.dao;

import java.awt.image.BufferedImage;

public interface DouIconCacheDao {
	public String doCache(BufferedImage img) throws Exception;

	public BufferedImage getCache(String cacheName) throws Exception;
}
