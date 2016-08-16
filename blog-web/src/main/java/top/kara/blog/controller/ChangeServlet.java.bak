package top.kara.blog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.kara.blog.model.Change;
import top.kara.blog.util.CaptchaUtil;
import top.kara.blog.util.DbUtil;

public class ChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 5107652379514786693L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ChangeServlet : back to 'http://localhost:8080/blog-web'");
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

		// 2- 检查验证码
		boolean captchaValid = CaptchaUtil.isCaptchaValid(request);
		if (!captchaValid) {
			doGet(request, response);
			return;
		}

		// 3- 检查确认密码
		Change change = new Change(userid, request);
		if (!change.isNewpasswordValid()) {
			doGet(request, response);
			return;
		}

		// 4- 检查当前密码
		userid = DbUtil.getChangeId(change);
		if (userid == null || userid.equals("")) {
			doGet(request, response);
			return;
		}

		// 4- 修改密码
		Boolean change_success = DbUtil.changePassword(change);
		if (change_success) {
			response.getWriter().print("success");
			System.out.println("success");
		} else {
			doGet(request, response);
		}
	}
}
