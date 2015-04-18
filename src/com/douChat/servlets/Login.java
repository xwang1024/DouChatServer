package com.douChat.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.douChat.logic.OnlineList;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject feedback = new JSONObject();
		try {
			feedback.put("status", "error");
			feedback.put("message", "Login method must be POST!");
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OnlineList onlineList = OnlineList.getInstance();
		String accessKey = onlineList.login("" + request.getAttribute("name"),
				request.getRemoteAddr());
		JSONObject feedback = new JSONObject();
		try {
			feedback.put("status", "ok");
			feedback.put("accessKey", accessKey);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		out.print(feedback.toString());
		out.flush();
		out.close();
	}

}
