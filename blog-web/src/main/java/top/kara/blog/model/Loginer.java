package top.kara.blog.model;

import javax.servlet.http.HttpServletRequest;

public class Loginer {
	private String username;
	private String password;

	public Loginer(HttpServletRequest request) {
		this.username = request.getParameter("inputUsername");
		this.password = request.getParameter("inputPassword");
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
