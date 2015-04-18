package com.douChat.dao.impl;

import com.douChat.dao.DouIconDao;
import com.douChat.entities.DouPic;

public class DouIconDaoImpl implements DouIconDao {

	@Override
	public DouPic getRandomPic() throws Exception {
		DouIconManager dpm = DouIconManager.getInstance();
		return dpm.getRandomDouPic();
	}

}
