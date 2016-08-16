package top.kara.blog.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.kara.blog.util.CaptchaUtil;
import top.kara.blog.util.DesUtil;

public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 5107652379514786693L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1- 设置无缓存
		resp.setContentType("image/jpeg");
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "No-cache");
		resp.setDateHeader("Expires", 0);

		// 2- 获取验证码
		String captcha = CaptchaUtil.getCaptcha();
		System.out.println("CaptchaServlet.captcha = " + captcha);

		// 3- 验证码加密保存
		String captcha_des = null;
		try {
			captcha_des = DesUtil.encrypt(captcha, CaptchaUtil.KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("CaptchaServlet.captcha_des = " + captcha_des);
		req.getSession().setAttribute("captcha_des", captcha_des);// 加密后的验证码存入Session中

		// 4- 输出验证码图形
		BufferedImage image = CaptchaUtil.getCaptchaImage(captcha);// 获取验证码图形
		OutputStream os = resp.getOutputStream();
		ImageIO.write(image, "jpg", os); // 图像输出到页面
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
