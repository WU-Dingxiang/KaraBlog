package top.karablog.model;

import java.util.ArrayList;

public class SQL {
	private int sqlType = 0;
	private String sql = null;
	private ArrayList<Object> args = new ArrayList<Object>();

	public SQL(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public ArrayList<Object> getArgs() {
		return args;
	}

	public void setArgs(ArrayList<Object> args) {
		this.args = args;
	}

	public SQL add(Object arg) {
		args.add(arg);
		return this;
	}

	public int getSqlType() {
		return sqlType;
	}

	public SQL setSqlType(int sqlType) {
		this.sqlType = sqlType;
		return this;
	}
}
