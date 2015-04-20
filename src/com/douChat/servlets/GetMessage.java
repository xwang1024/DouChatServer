package com.douChat.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Test
 */
@WebServlet("/chat/getMessage")
public class GetMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetMessage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// HttpSession session = request.getSession(true);
		// session.setAttribute("name", "default");
		// System.out.println(session.getId());
		// DouIconDao dpc = new DouIconDaoImpl();
		// try {
		// DouPic pic = dpc.getRandomPic();
		// response.setContentType("image/jpeg");
		// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response
		// .getOutputStream());
		// encoder.encode(pic.getImage());
		// response.getOutputStream().flush();
		// response.getOutputStream().close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject feedback = new JSONObject();
		try {
			feedback.put("status", "ok");
			JSONArray msgArr = new JSONArray();
			JSONObject msg = new JSONObject();
			msg.put("username", "TestName");
			msg.put("imageUrl", "images/3.jpg");
			msgArr.put(msg);
			feedback.put("messageList", msgArr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		out.print(feedback.toString());
		out.flush();
		out.close();
	}

}
