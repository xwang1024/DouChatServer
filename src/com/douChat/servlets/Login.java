package com.douChat.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.douChat.beans.UserBean;
import com.douChat.beans.impl.UserBeanImpl;
import com.douChat.servlets.helper.PostHelper;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserBean userBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		userBean = new UserBeanImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		JSONObject feedback = new JSONObject();
		try {
			feedback.put("status", "error");
			feedback.put("message", "Unimplemented!");
		} catch (JSONException e) {
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
			String username = postMap.get("username");
			// Check if username is empty
			if (username == null || username.length() == 0) {
				feedback.put("status", "error");
				feedback.put("message", "Empty username!");
			} else {
				String accessKey = userBean.login(username, request.getRemoteHost(), request.getSession().getId());
				feedback.put("status", "ok");
				feedback.put("accessKey", accessKey);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		out.print(feedback.toString());
		out.flush();
		out.close();
	}

}
