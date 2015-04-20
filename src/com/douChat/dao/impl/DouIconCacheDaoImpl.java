package com.douChat.dao.impl;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.douChat.dao.DouIconCacheDao;
import com.douChat.dao.impl.conf.DaoConfig;
import com.douChat.util.RandomUtil;

public class DouIconCacheDaoImpl implements DouIconCacheDao {
	private DaoConfig daoConfig;
	private File cacheRoot;

	public DouIconCacheDaoImpl() throws Exception {
		daoConfig = DaoConfig.getInstance();
		cacheRoot = new File(daoConfig.getCachePath());
		if (!cacheRoot.exists()) {
			cacheRoot.mkdirs();
		}
	}

	@Override
	public String doCache(BufferedImage img) throws Exception {
		File target;
		String fileName;
		do {
			fileName = RandomUtil.generateRandomString(16);
			target = new File(cacheRoot, fileName.concat(".jpg"));
		} while (target.exists());
		ImageIO.write(img, "jpg", target);
		return fileName;
	}

	@Override
	public BufferedImage getCache(String cacheName) throws Exception {
		return ImageIO.read(new File(cacheRoot, cacheName.concat(".jpg")));
	}

}
