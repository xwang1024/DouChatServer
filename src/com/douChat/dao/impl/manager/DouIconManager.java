package com.douChat.dao.impl.manager;

import java.awt.Color;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import com.douChat.dao.impl.conf.DaoConfig;
import com.douChat.dao.impl.helper.FileTypeHelper;
import com.douChat.dao.impl.helper.ImageHelper;
import com.douChat.dao.impl.helper.XMLHelper;
import com.douChat.dao.impl.structure.LRUHashMap;
import com.douChat.entities.DouPic;
import com.douChat.util.ColorUtil;

public class DouIconManager {
	private static final int cacheVolume = 10;
	private LRUHashMap<String, DouPic> cache;
	private ArrayList<File> fileList;

	private static DaoConfig conf;
	private static DouIconManager instance;
	private int picCnt;

	public DouPic getRandomDouPic() {
		DouPic pic = null;
		while (!fileList.isEmpty() && pic == null) {
			int index = (int) (Math.random() * picCnt);
			String name = fileList.get(index).getName();
			if (cache.containsKey(name)) {
				pic = cache.get(name);
			} else {
				try {
					pic = getDouPic(name);
					cache.put(name, pic);
				} catch (Exception e) {
					fileList.remove(index);
					cache.remove(name);
					picCnt--;
				}
			}
		}
		return pic;
	}

	public static DouIconManager getInstance() throws Exception {
		return instance == null ? (instance = new DouIconManager()) : instance;
	}

	private DouIconManager() throws Exception {
		conf = DaoConfig.getInstance();
		File imagePath = new File(conf.getImagePath());
		System.out.println(imagePath.getAbsolutePath());
		System.out.println(imagePath.exists());
		File[] fileArr = imagePath.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String fileName) {
				return FileTypeHelper.isImageFile(fileName);
			}
		});
		fileList = new ArrayList<File>();
		for (int i = 0; i < fileArr.length; i++) {
			fileList.add(fileArr[i]);
		}
		cache = new LRUHashMap<String, DouPic>(cacheVolume);
		picCnt = fileList.size();
	}

	private DouPic getDouPic(String imageName) throws Exception {
		File imageFile = new File(conf.getImagePath(), imageName);
		File confFile = new File(conf.getConfigPath(), imageName.concat(".xml"));

		DouPic p = new DouPic();
		p.setPath(imageFile.getAbsolutePath());
		p.setImage(ImageHelper.getImage(imageFile));

		XMLHelper xmlHelper = new XMLHelper(confFile);
		Integer[] coverVerticeX = xmlHelper.getIntegerList("/Doupic/coverArea/point/x");
		p.setCoverVerticeX(coverVerticeX);
		Integer[] coverVerticeY = xmlHelper.getIntegerList("/Doupic/coverArea/point/y");
		p.setCoverVerticeY(coverVerticeY);
		int coverColor = ColorUtil.HexToRGB(xmlHelper.getString("/Doupic/coverArea/color"));
		p.setCoverColor(new Color(coverColor));
		String fontFamily = xmlHelper.getString("/Doupic/messageArea/font/family");
		p.setFontFamily(fontFamily);
		return p;
	}
}