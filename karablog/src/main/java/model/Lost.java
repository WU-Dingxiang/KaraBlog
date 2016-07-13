package model;

import javax.servlet.http.HttpServletRequest;

public class Lost {
	private Long id;
	private String password;

	public Lost(Long userid, HttpServletRequest request) {
		this.id = userid;
		this.password = request.getParameter("password");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
