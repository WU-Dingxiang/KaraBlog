package top.karablog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 5107652379514786693L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1- 清除 userid
		request.getSession().removeAttribute("userid");
		System.out.println("UserLoginServlet : 清除 userid");

		// 2- 跳转首页 [此功能由前台JavaScript完成]
		// response.sendRedirect("/karablog");
	}
}
