package com.douChat.webSocketServer.structure;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.Session;

public class WsSessionList {
	private static final Logger LOGGER = Logger.getLogger(WsSessionList.class.getName());
	private static WsSessionList instance;
	private List<Session> l;

	private WsSessionList() {
		l = new LinkedList<Session>();
	}

	public static WsSessionList getInstance() {
		return instance == null ? (instance = new WsSessionList()) : (instance);
	}

	public synchronized void registerSession(Session session) {
		this.l.add(session);
	}

	public synchronized void unregisterSession(Session session) {
		this.l.remove(session);
	}

	public synchronized void boardcast(Session except, String message) {
		for (int i = 0; i < l.size(); i++) {
			Session cur = l.get(i);
			try {
				if (except == null || !cur.getId().equals(except.getId())) {
					cur.getBasicRemote().sendText(message);
				}
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Session[{0}] exception: {1}", new Object[] { cur.getId(), e.getMessage() });
				l.remove(i);
				i--;
				continue;
			}
		}
	}
}
