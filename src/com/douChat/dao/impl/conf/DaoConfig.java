package com.douChat.dao.impl.conf;

import java.io.File;

import com.douChat.dao.impl.helper.XMLHelper;

public class DaoConfig {
	private static final String XML_NAME = "DouData.xml";
	private String imagePath;
	private String configPath;

	public DaoConfig() throws Exception {
		System.out.println(this.getClass().getResource(XML_NAME));
		File f = new File(this.getClass().getResource(XML_NAME).getPath());
		XMLHelper xmlHelper = new XMLHelper(f);
		imagePath = xmlHelper.getString("/DouData/DouImage/Path");
		configPath = xmlHelper.getString("/DouData/DouConfig/Path");
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
}
