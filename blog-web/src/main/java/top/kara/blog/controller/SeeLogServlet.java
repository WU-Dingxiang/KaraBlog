package top.kara.blog.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.kara.blog.model.Log;
import top.kara.blog.util.DbUtil;

public class SeeLogServlet extends HttpServlet {
	private static final long serialVersionUID = -8563501734829360475L;
	private static final Long PAGE_SIZE = (long) 15;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {

		// 1- Session 检查
		Long userid = (Long) req.getSession().getAttribute("userid");
		if (userid == null || userid.equals("")) {
			doGet(req, resp);
			return;
		}

		// 1- 检查 html name
		String htmlName_server = (String) req.getSession().getAttribute(
				"htmlName");
		System.out.println("htmlName_server =" + htmlName_server);
		String htmlName = "seelog";
		System.out.println("htmlName =" + htmlName);
		boolean samepage = false;
		if (htmlName_server != null && htmlName_server.equals(htmlName)) {
			samepage = true;
		} else {
			req.getSession().setAttribute("htmlName", htmlName);
		}

		// 3- 检查当前页
		Long pageIndex_server = (Long) req.getSession().getAttribute(
				"pageIndex");
		System.out.println("pageIndex_server =" + pageIndex_server);
		Long pageIndex = Long.valueOf(req.getParameter("pageIndex"));
		System.out.println("pageIndex =" + pageIndex);
		if (samepage && pageIndex_server != null
				&& pageIndex_server.equals(pageIndex)) {
			resp.getWriter().print("samepage");
			System.out.println("samepage");
			return;
		} else {
			req.getSession().setAttribute("pageIndex", pageIndex);
		}

		// 2- 获得对应user id的数据条目数
		Long logCount = DbUtil.countLog(userid, "consume");
		Long pageCount = logCount / PAGE_SIZE;
		if (pageCount * PAGE_SIZE < logCount)
			pageCount++;

		// 3- 修正目标页
		if (pageIndex < 1)
			pageIndex = (long) 1;
		else if (pageIndex > pageCount)
			pageIndex = pageCount;

		// 3- 获得对应区间的数据
		List<Log> result = DbUtil.seeLog(userid, pageIndex, "consume");

		// 4- 创建表格
		StringBuffer buffer = new StringBuffer();
		buffer.append("<table id=\"display\">\n");
		buffer.append("<tr>");
		buffer.append("<th>记录ID</th>");
		buffer.append("<th>用户ID</th>");
		buffer.append("<th>交易日期</th>");
		buffer.append("<th>交易地点</th>");
		buffer.append("<th>交易类型</th>");
		buffer.append("<th>金额</th>");
		buffer.append("<th>交易后余额</th>");
		buffer.append("</tr>\n");

		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd hh:mm:ss");

		for (Log log : result) {
			buffer.append("<tr>\n");
			buffer.append("<td>" + log.getId() + "</td>\n");
			buffer.append("<td>" + log.getUserid() + "</td>\n");
			buffer.append("<td>" + df.format(log.getDate()) + "</td>\n");
			buffer.append("<td>" + log.getPlace() + "</td>\n");
			buffer.append("<td>" + log.getType() + "</td>\n");
			buffer.append("<td>" + log.getPrice() + "</td>\n");
			buffer.append("<td>" + log.getRemain() + "</td>\n");
			buffer.append("</tr>\n");
		}
		buffer.append("</table>");

		// 5- 首页+上一页
		buffer.append("<ul id=\"pagemenu\">\n");
		if (pageIndex > 2) {
			buffer = makeRow(buffer, (long) 1, "首页");
		}
		if (pageIndex > 1) {
			buffer = makeRow(buffer, pageIndex - 1, "上一页");
		}

		// 6- 起始页
		Long pageLenR = pageCount - pageIndex;
		if (pageLenR > 5) {
			pageLenR = (long) 5;
		}
		Long pageStart = pageIndex + pageLenR - 10;
		if (pageStart < 1)
			pageStart = (long) 1;

		// 7- 一般页
		for (Long i = pageStart; i <= pageCount && i <= pageStart + 10; i++) {
			buffer = makeRow(buffer, i, i.toString());
		}

		// 5- 尾页+下一页
		if (pageIndex < pageCount) {
			buffer = makeRow(buffer, pageIndex + 1, "下一页");
		}
		if (pageIndex < pageCount - 1) {
			buffer = makeRow(buffer, pageCount, "尾页");
		}
		buffer.append("</ul>\n");

		// 6- 输出表格与页码
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(buffer);
		System.out.println(buffer);
	}

	private StringBuffer makeRow(StringBuffer buffer, Long pageIndex,
			String string) {
		buffer.append("<li id=\"pageIndex");
		buffer.append(string);
		buffer.append("\" onclick=\"$.goLogPage(");
		buffer.append(pageIndex);
		buffer.append(")\">");
		buffer.append(string);
		buffer.append("</li>\n");
		return buffer;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		System.out.println("SeeLogServlet : back to 'http://localhost:8080/blog-web'");
		resp.sendRedirect("/");
	}

}