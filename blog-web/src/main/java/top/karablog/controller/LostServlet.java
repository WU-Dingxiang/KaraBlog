package top.karablog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.karablog.model.Lost;
import top.karablog.util.CaptchaUtil;
import top.karablog.util.DbUtil;

public class LostServlet extends HttpServlet {
	private static final long serialVersionUID = 5107652379514786693L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("LostServlet : back to 'http://localhost:8080/karablog'");
		response.sendRedirect("/karablog");
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

		// 3- 检查 captcha
		Lost lost = new Lost(userid, request);

		// 4- 检查用户名/密码
		userid = DbUtil.getLostId(lost);
		if (userid == null || userid.equals("")) {
			doGet(request, response);
			return;
		}
		// 5- 设置卡状态为挂失
		// DbUtil.alterCardStatus("lost");
		
		// 6- 操作成功
		response.getWriter().print("success");
		System.out.println("success");
	}
}
