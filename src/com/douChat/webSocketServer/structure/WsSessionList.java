package com.douChat.webSocketServer.structure;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.Session;

public class WsSessionList {
	private static final Logger LOGGER = Logger.getLogger(WsSessionList.class.getName());
	private static WsSessionList instance;
	private Map<Session, String> map;

	private WsSessionList() {
		map = new ConcurrentHashMap<Session, String>();
	}

	public static WsSessionList getInstance() {
		return instance == null ? (instance = new WsSessionList()) : (instance);
	}

	public synchronized void registerSession(Session session) {
		this.map.put(session, "");
	}

	public synchronized void unregisterSession(Session session) {
		this.map.remove(session);
	}

	public synchronized void sessionLogin(Session session, String username) {
		this.map.put(session, username);
	}

	public synchronized String getUsername(Session session) {
		return this.map.get(session);
	}

	public synchronized void boardcast(Session except, String message) {
		for (Session cur : map.keySet()) {
			try {
				if (except == null || !cur.getId().equals(except.getId())) {
					cur.getBasicRemote().sendText(message);
				}
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Session[{0}] exception: {1}", new Object[] { cur.getId(), e.getMessage() });
				continue;
			}
		}
	}
}
