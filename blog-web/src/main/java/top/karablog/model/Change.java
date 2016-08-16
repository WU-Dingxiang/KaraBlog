package top.karablog.model;

import javax.servlet.http.HttpServletRequest;

public class Change {
	private Long id;
	private String curpassword;
	private String newpassword;
	private String repassword;

	public Change(Long userid, HttpServletRequest request) {
		this.id = userid;
		this.curpassword = request.getParameter("curpassword");
		this.newpassword = request.getParameter("newpassword");
		this.repassword = request.getParameter("repassword");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurpassword() {
		return curpassword;
	}

	public void setCurpassword(String curpassword) {
		this.curpassword = curpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public boolean isNewpasswordValid() {
		// TODO Auto-generated method stub
		return newpassword != null && newpassword.equals(repassword);
	}

}
