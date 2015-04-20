package com.douChat.dao.impl;

import com.douChat.dao.DouIconDao;
import com.douChat.dao.impl.manager.DouIconManager;
import com.douChat.entities.DouPic;

public class DouIconDaoImpl implements DouIconDao {
	private DouIconManager dpm;
	
	public DouIconDaoImpl() throws Exception {
		dpm = DouIconManager.getInstance();
	}

	@Override
	public DouPic getRandomPic() {
		return dpm.getRandomDouPic();
	}
}
