package _utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CaptchaUtil {
	private static final int width = 83, height = 30;
	private static final char[] chars = "0123456789".toCharArray();// 可扩展其他字符
	private static final Font font = new Font("微软雅黑", Font.BOLD, 24);

	public static String getCaptcha() {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();

		char randomChar = 0;
		for (int i = 0; i < 4; i++) {
			randomChar = chars[random.nextInt(chars.length)];// 在chars中获得随机位置的一个字符
			buffer.append(randomChar);
		}

		return buffer.toString();
	}

	public static BufferedImage getCaptchaImage(String captcha) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Color originalColor = g.getColor();// 保存画笔

		g.fillRect(0, 0, width, height);
		g.setFont(font);

		Random random = new Random();

		for (int i = 0, len = captcha.length(); i < len; i++) {
			g.setColor(new Color(random.nextInt(255), random.nextInt(255),
					random.nextInt(255)));

			g.drawString(captcha.substring(i, i + 1), 20 * i + 6, 25);// 核心步骤：在矩形上绘制验证码
		}

		for (int i = 0; i < 20; i++) {
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			g.drawOval(x1, y1, 2, 2);// 产生随即干扰点
		}

		g.setColor(originalColor);// 恢复画笔
		g.dispose();

		return image;
	}

	public static boolean isCaptchaValid(HttpServletRequest request) {
		String captcha = request.getParameter("captcha");
		System.out.println("CaptchaUtil.captcha = " + captcha);

		HttpSession session = request.getSession();
		String captcha_des = (String) session.getAttribute("captcha_des");// 从Session中获取加密后的验证码
		System.out.println("CaptchaUtil.captcha_des = " + captcha_des);

		String server_captcha = null;
		try {
			server_captcha = DesUtil.decrypt(captcha_des, "captcha_des");// 验证码解码
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("CaptchaUtil.server_captcha = " + server_captcha);

		boolean captchaValid = server_captcha != null
				&& server_captcha.equals(captcha);
		return captchaValid;
	}
}
