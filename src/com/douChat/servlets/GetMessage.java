package com.douChat.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douChat.dao.DouIconDao;
import com.douChat.dao.impl.DouIconDaoImpl;
import com.douChat.entities.DouPic;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

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
		HttpSession session = request.getSession(true);  
		session.setAttribute("name", "default");
		System.out.println(session.getId());
		DouIconDao dpc = new DouIconDaoImpl();
		try {
			DouPic pic = dpc.getRandomPic();
			response.setContentType("image/jpeg");
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response
					.getOutputStream());
			encoder.encode(pic.getImage());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
