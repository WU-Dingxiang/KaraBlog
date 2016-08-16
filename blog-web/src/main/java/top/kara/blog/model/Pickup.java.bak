package top.kara.blog.model;

import javax.servlet.http.HttpServletRequest;

public class Pickup {
	private Long userid;
	private String school;
	private String stuid;
	private String name;
	private Long cardid;

	public Pickup(Long userid, HttpServletRequest request) {
		this.setUserid(userid);
		this.school = request.getParameter("school");
		this.stuid = request.getParameter("stuid");
		this.name = request.getParameter("name");
		this.cardid = valueOf(request.getParameter("cardid"));
	}

	private Long valueOf(String src) {
		Long result = null;
		try {
			result = Long.valueOf(src);
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCardid() {
		return cardid;
	}

	public void setCardid(Long cardid) {
		this.cardid = cardid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public boolean isInfoValid() {
		boolean stuidEmpty = stuid == null || "".endsWith(stuid);
		boolean cardidEmpty = cardid == null || cardid == 0;
		return !(stuidEmpty && cardidEmpty);
	}

}
