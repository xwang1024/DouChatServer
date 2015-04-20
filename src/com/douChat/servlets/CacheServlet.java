package com.douChat.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douChat.dao.DouIconCacheDao;
import com.douChat.dao.impl.DouIconCacheDaoImpl;

/**
 * Servlet implementation class Cache
 */
@WebServlet("/cache")
public class CacheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DouIconCacheDao dicd;

	/**
	 * @throws Exception
	 * @see HttpServlet#HttpServlet()
	 */
	public CacheServlet() throws Exception {
		super();
		// TODO Auto-generated constructor stub
		dicd = new DouIconCacheDaoImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null) {
			return;
		}
		BufferedImage img;
		try {
			img = dicd.getCache(id);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if (img == null) {
			return;
		}
		response.setHeader("Cache-control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(img, "jpg", out);
		out.flush();
		out.close();
	}
}
