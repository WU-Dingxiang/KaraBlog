package model;

import java.util.Date;

public class Log {
	private int id;
	private int userid;
	private Date date;
	private String place;
	private String type;
	private float price;
	private double remain;

	public int getId() {
		return id;
	}

	public Log setId(int id) {
		this.id = id;
		return this;
	}

	public int getUserid() {
		return userid;
	}

	public Log setUserid(int userid) {
		this.userid = userid;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public Log setDate(Date date) {
		this.date = date;
		return this;
	}

	public String getPlace() {
		return place;
	}

	public Log setPlace(String place) {
		this.place = place;
		return this;
	}

	public String getType() {
		return type;
	}

	public Log setType(String type) {
		this.type = type;
		return this;
	}

	public float getPrice() {
		return price;
	}

	public Log setPrice(float price) {
		this.price = price;
		return this;
	}

	public double getRemain() {
		return remain;
	}

	public Log setRemain(double remain) {
		this.remain = remain;
		return this;
	}
}
