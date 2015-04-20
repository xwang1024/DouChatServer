package com.douChat.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.douChat.beans.ChatBean;
import com.douChat.beans.impl.ChatBeanImpl;
import com.douChat.entities.DouMessage;
import com.douChat.servlets.helper.PostHelper;

/**
 * Servlet implementation class Test
 */
@WebServlet("/chat")
public class Chat extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ChatBean chatBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Chat() {
		super();
		chatBean = new ChatBeanImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		JSONObject feedback = new JSONObject();
		try {
			// if client is explorer
			if (!request.getHeader("User-Agent").contains("DouChatClient")) {
				HttpSession session = request.getSession();
				Object sessionAttrObj = null;
				// Check if login
				if ((sessionAttrObj = session.getAttribute("username")) == null) {
					feedback.put("status", "error");
					feedback.put("message", "Login first, please!");
				} else {
					feedback.put("status", "ok");
					String username = sessionAttrObj.toString();
					DouMessage[] messages = chatBean.getMessage(username);
					JSONArray msgArr = new JSONArray();
					for (int i = 0; i < messages.length; i++) {
						JSONObject msg = new JSONObject();
						msg.put("username", messages[i].getSender());
						msg.put("time", messages[i].getTimeStamp());
						msg.put("imageUrl", messages[i].getDouPicUrl());
						msgArr.put(msg);
					}
					feedback.put("messageList", msgArr);
				}
			}
			// if client is cellphone app
			else {
				// TODO cellphone app request
				
			}
		} catch (JSONException e) {
			try {
				feedback.put("status", "serverError");
			} catch (JSONException e1) {
			}
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		out.print(feedback.toString());
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		JSONObject feedback = new JSONObject();

		try {
			Map<String, String> postMap = PostHelper.getPostContent(request.getInputStream());
			String message = postMap.get("message");
			// Check if message is empty
			if (message == null || message.length() == 0) {
				feedback.put("status", "error");
				feedback.put("message", "Empty message!");
			} else {
				// if client is explorer
				if (!request.getHeader("User-Agent").contains("DouChatClient")) {
					HttpSession session = request.getSession();
					Object sessionAttrObj = null;
					// Check if login
					if ((sessionAttrObj = session.getAttribute("username")) == null) {
						feedback.put("status", "error");
						feedback.put("message", "Login first, please!");
					} else {
						feedback.put("status", "ok");
						String username = sessionAttrObj.toString();
						DouMessage douMessage = chatBean.sendMessage(username, message);
						feedback.put("status", "ok");
						feedback.put("imageUrl", douMessage.getDouPicUrl());
					}
				}
				// if client is cellphone app
				else {
					// TODO cellphone app request
					
				}
			}
		} catch (JSONException e) {
			try {
				feedback.put("status", "serverError");
			} catch (JSONException e1) {
			}
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		out.print(feedback.toString());
		out.flush();
		out.close();
	}

}
