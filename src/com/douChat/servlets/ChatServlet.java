package com.douChat.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douChat.beans.ChatBean;
import com.douChat.beans.impl.ChatBeanImpl;

/**
 * Servlet implementation class Test
 */
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ChatBean chatBean;

	/**
	 * @throws Exception
	 * @see HttpServlet#HttpServlet()
	 */
	public ChatServlet() throws Exception {
		super();
		chatBean = new ChatBeanImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.setContentType("application/json;charset=UTF-8");
		// JSONObject feedback = new JSONObject();
		// try {
		// // if client is explorer
		// if (!request.getHeader("User-Agent").contains("DouChatClient")) {
		// feedback.put("status", "ok");
		// HttpSession session = request.getSession();
		// Object sessionAttrObj = null;
		// String username = null;
		// // Check if login
		// if ((sessionAttrObj = session.getAttribute("username")) != null) {
		// username = sessionAttrObj.toString();
		// }
		// try {
		// String timestamp = request.getParameter("timestamp");
		// long lastTimestamp = Long.parseLong(timestamp);
		// DouMessage[] messages = chatBean.getMessage(username, lastTimestamp);
		// JSONArray msgArr = new JSONArray();
		// for (int i = 0; i < messages.length; i++) {
		// JSONObject msg = new JSONObject();
		// msg.put("username", messages[i].getSender());
		// msg.put("time", messages[i].getTimeStamp());
		// msg.put("imageId", messages[i].getDouPicId());
		// msgArr.put(msg);
		// }
		// feedback.put("messageList", msgArr);
		// String timeStampStr = "0";
		// if(messages.length != 0) {
		// timeStampStr = messages[messages.length - 1].getTimeStamp()+"";
		// }
		// feedback.put("timestamp", timeStampStr);
		// } catch (Exception e) {
		// feedback.put("status", "error");
		// feedback.put("message",
		// "Timestamp is not provided or has wrong format!");
		// e.printStackTrace();
		// }
		// }
		// // if client is cellphone app
		// else {
		// // TODO cellphone app request
		//
		// }
		// } catch (Exception e) {
		// try {
		// feedback.put("status", "serverError");
		// } catch (JSONException e1) {
		// }
		// e.printStackTrace();
		// }
		// PrintWriter writer = response.getWriter();
		// writer.print(feedback.toString());
		// writer.flush();
		// writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.setContentType("application/json;charset=UTF-8");
		// JSONObject feedback = new JSONObject();
		//
		// try {
		// String message = request.getParameter("message");
		// // Check if message is empty
		// if (message == null || message.length() == 0) {
		// feedback.put("status", "error");
		// feedback.put("message", "Empty message!");
		// } else {
		// // if client is explorer
		// if (!request.getHeader("User-Agent").contains("DouChatClient")) {
		// HttpSession session = request.getSession();
		// Object sessionAttrObj = null;
		// // Check if login
		// if ((sessionAttrObj = session.getAttribute("username")) == null) {
		// feedback.put("status", "error");
		// feedback.put("message", "Login first, please!");
		// } else {
		// feedback.put("status", "ok");
		// String username = sessionAttrObj.toString();
		// DouMessage douMessage = chatBean.sendMessage(username, message);
		// feedback.put("status", "ok");
		// feedback.put("imageId", douMessage.getDouPicId());
		// }
		// }
		// // if client is cellphone app
		// else {
		// // TODO cellphone app request
		//
		// }
		// }
		// } catch (Exception e) {
		// try {
		// feedback.put("status", "serverError");
		// } catch (JSONException e1) {
		// }
		// e.printStackTrace();
		// }
		// PrintWriter writer = response.getWriter();
		// writer.print(feedback.toString());
		// writer.flush();
		// writer.close();
	}

}
