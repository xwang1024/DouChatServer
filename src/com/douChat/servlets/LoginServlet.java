package com.douChat.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.douChat.beans.UserBean;
import com.douChat.beans.impl.UserBeanImpl;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserBean userBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
			String username = request.getParameter("username");
			// Check if username is empty
			if (username == null || username.length() == 0) {
				feedback.put("status", "error");
				feedback.put("message", "Empty username!");
			} else {
				String accessKey = userBean.login(username, request.getRemoteHost(), request.getSession().getId());
				if (accessKey == null) {
					feedback.put("status", "error");
					feedback.put("message", "Username already exist!");
				} else {
					feedback.put("status", "ok");
					HttpSession session = request.getSession();
					session.setAttribute("username", username);
//					session.setAttribute("accessKey", accessKey);
				}
			}
		} catch (Exception e) {
			try {
				feedback.put("status", "serverError");
			} catch (JSONException e1) {
			}
			e.printStackTrace();
		}
		PrintWriter writer = response.getWriter();
		writer.print(feedback.toString());
		writer.flush();
		writer.close();
	}

}
