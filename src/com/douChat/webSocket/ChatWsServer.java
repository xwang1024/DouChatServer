package com.douChat.webSocket;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

import com.douChat.beans.ChatBean;
import com.douChat.beans.UserBean;
import com.douChat.entities.DouMessage;

@ServerEndpoint(value = "/")
public class ChatWsServer {
	private static final Logger LOGGER = Logger.getLogger(ChatWsServer.class.getName());
	private UserBean userBean;
	private ChatBean chatBean;
	@OnOpen
	public void onOpen(Session session) {
		LOGGER.log(Level.INFO, "New connection with client: {0}", session.getId());
		WsSessionList.getInstance().registerSession(session);
	}

	@OnMessage
	public String onMessage(String content, Session session) {
		LOGGER.log(Level.INFO, "New message from Client [{0}]: {1}", new Object[] { session.getId(), content });
		JSONObject feedback = new JSONObject();
		try {
			JSONObject json = new JSONObject(content);
			String operation = json.getString("function");
			String username, accessKey, message;
			switch(operation) {
			case "login":
				username = json.getString("username");
				accessKey = userBean.login(username, "WebSocket", "ws" + session.getId());
				if (accessKey == null) {
					feedback.put("status", "error");
					feedback.put("message", "Username already exist!");
				} else {
					feedback.put("status", "ok");
					feedback.put("accessKey", accessKey);
				}
				break;
			case "send":
				message = json.getString("message");
				accessKey = json.getString("accessKey");
				if(accessKey == null) {
					feedback.put("status", "error");
					feedback.put("message", "Login first, please!");
					break;
				}
				if(message == null || message.equals("")) {
					feedback.put("status", "error");
					feedback.put("message", "Empty message!");
					break;
				}
				DouMessage douMessage = chatBean.sendMessage(accessKey, message);
				if(douMessage == null) {
					feedback.put("status", "error");
					feedback.put("message", "Unknown accessKey");
					break;
				}
				feedback.put("status", "ok");
				feedback.put("imageId", douMessage.getDouPicId());
				JSONObject boardcast = new JSONObject();
				boardcast.put("username", douMessage.getSender());
				boardcast.put("time", douMessage.getTimeStamp());
				boardcast.put("imageId", douMessage.getDouPicId());
				WsSessionList.getInstance().boardcast(session, boardcast.toString());
				break;
			}
		} catch (Exception e) {
			try {
				feedback.put("status", "serverError");
				feedback.put("message", e.getMessage());
			} catch (JSONException e1) {
			}
			e.printStackTrace();
		}
		return feedback.toString();
	}

	@OnClose
	public void onClose(Session session) {
		LOGGER.log(Level.INFO, "Close connection for client: {0}", session.getId());
		WsSessionList.getInstance().unregisterSession(session);
	}

	@OnError
	public void onError(Throwable exception, Session session) {
		LOGGER.log(Level.INFO, "Error for client: {0}", session.getId());
		WsSessionList.getInstance().unregisterSession(session);
	}
}
