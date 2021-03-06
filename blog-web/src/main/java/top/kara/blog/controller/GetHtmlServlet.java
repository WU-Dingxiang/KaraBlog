package top.kara.blog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.kara.blog.util.HtmlUtil;

public class GetHtmlServlet extends HttpServlet {
	private static final long serialVersionUID = 5107652379514786693L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("GetHtmlServlet : back to 'http://localhost:8080/blog-web'");
		response.sendRedirect("/blog-web");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1- 检查session
		Long userid = (Long) request.getSession().getAttribute("userid");
		if (userid == null || userid.equals("")) {
			doGet(request, response);
			return;
		}

		// 1- 检查 html name
		String htmlName_server = (String) request.getSession().getAttribute(
				"htmlName");
		System.out.println("htmlName_server =" + htmlName_server);
		String htmlName = request.getParameter("htmlName");
		System.out.println("htmlName =" + htmlName);
		if (htmlName_server != null && htmlName_server.equals(htmlName)
				&& !htmlName_server.equals("info.html")) {
			response.getWriter().print("samepage");
			System.out.println("samepage");
			return;
		} else {
			request.getSession().setAttribute("htmlName", htmlName);
		}

		// 2- 输出目标页面
		HtmlUtil.printHtml(this, request, response, htmlName);
	}
}
