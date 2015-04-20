package com.douChat.dao.impl.conf;

import java.io.File;

import com.douChat.dao.impl.helper.XMLHelper;

public class DaoConfig {
	private static DaoConfig instance;
	private static final String XML_NAME = "DouData.xml";
	private String imagePath;
	private String configPath;
	private String cachePath;

	private DaoConfig() throws Exception {
		System.out.println(this.getClass().getResource(XML_NAME));
		File f = new File(this.getClass().getResource(XML_NAME).getPath());
		XMLHelper xmlHelper = new XMLHelper(f);
		imagePath = xmlHelper.getString("/DouData/DouImage/Path");
		configPath = xmlHelper.getString("/DouData/DouConfig/Path");
		cachePath = xmlHelper.getString("/DouData/DouCache/Path");
	}
	
	public static DaoConfig getInstance() throws Exception {
		return instance==null?(instance = new DaoConfig()):(instance);
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public String getCachePath() {
		return cachePath;
	}

	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}
	
	
}
