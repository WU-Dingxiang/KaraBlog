package top.kara.blog.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import top.kara.blog.model.Change;
import top.kara.blog.model.Log;
import top.kara.blog.model.Loginer;
import top.kara.blog.model.Lost;
import top.kara.blog.model.Pickup;
import top.kara.blog.model.SQL;
import top.kara.blog.model.User;

public class DbUtil {
	private static final ResourceBundle DB = ResourceBundle.getBundle("db");
	private static final String DB_CLASSNAME = DB.getString("classname");// 驱动类全名
	private static final String DB_URL = DB.getString("url");// 数据库全名
	private static final String DB_USERNAME = DB.getString("username");// 数据库用户
	private static final String DB_PASSWORD = DB.getString("password");// 数据库密码
	private static final String SQL_CHECKUSERNAME = "select id from user where username = ?";// 待执行的SQL语句

	private static String SQL_CHECKLOGINER(String userInfo) {
		return "select id from user where " + userInfo
				+ " = ? and password = ?";
	}

	private static final String SQL_ADD_USER = "insert into user (username, password) values (?,?)";// 待执行的SQL语句
	private static final String SQL_ADD_PICKUP = "insert into pickup (school, stuid, name, cardid, date) values (?,?,?,?,?)";// 待执行的SQL语句
	private static final String SQL_CHANGE_PASSWORD = "UPDATE user SET password = ? WHERE id = ? ";

	private static String SQL_SEE_LOG(String tableName) {
		return "SELECT * FROM " + tableName
				+ " WHERE userid = ? ORDER BY id DESC LIMIT ?,?";
	}

	private static String SQL_COUNT_LOG(String tableName) {
		return "SELECT count(*) FROM " + tableName + " WHERE userid = ?";
	}

	private static String SQL_ADD_LOG(String tableName) {
		return "insert into "
				+ tableName
				+ " (userid, date, place, type, price, remain) values (?,?,?,?,?,?)";
	}

	private static final int SQL_TYPE_CHECK = 0;
	private static final int SQL_TYPE_ADD = 1;
	private static final int SQL_TYPE_SEE = 2;
	private static final int SQL_TYPE_COUNT = 3;

	private static Object excuteSQL(SQL sql) {

		// 1- 检查 SQL
		if (sql == null || sql.getSql() == null)
			return null;
		System.out.println("SQL = " + sql.getSql());

		// 2- 声明对象
		Connection conn = null;// |- 1- 数据库链接 java.sql.Connection
		ResultSet rs = null;// |- 2- 结果集
		PreparedStatement pstatement = null;// |- 3- 事务
		Object result = null;// |- 4- 结果

		// 3- 数据库操作
		try {
			// 4- 加载驱动类
			Class.forName(DB_CLASSNAME);
			// 5- 得到数据库连接
			conn = DriverManager
					.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			if (!conn.isClosed()) {
				// 6- 连接成功
				System.out.println("Database is connected!");
				// 7- 获得事务对象
				pstatement = conn.prepareStatement(sql.getSql());
				// 8- 设置参数
				int i = 0;
				for (Object arg : sql.getArgs()) {
					i++;
					pstatement.setObject(i, arg);
				}
				switch (sql.getSqlType()) {
				case SQL_TYPE_COUNT:// COUNT
					rs = pstatement.executeQuery();// 9- 执行事务，获得结果集
					while (rs.next()) {// 10- 打印所有的结果
						result = rs.getObject(1);
						System.out.println("COUNT = " + result);
					}
					break;
				case SQL_TYPE_CHECK:// Query
					rs = pstatement.executeQuery();// 9- 执行事务，获得结果集
					while (rs.next()) {// 10- 打印所有的结果
						result = rs.getObject("id");
						System.out.println("id = " + result);
					}
					break;
				case SQL_TYPE_ADD:/* Insert/Update */
					// 11- 执行事务，获得结果集
					int updateCount = pstatement.executeUpdate();

					// 12- 打印所有的结果
					if (updateCount > 0) {
						System.out.println("updateCount = " + updateCount);
						result = true;
					}
					break;
				case SQL_TYPE_SEE:// See*/
					// 13- 执行事务，获得结果集
					rs = pstatement.executeQuery();
					List<Log> results = new ArrayList<Log>();
					// 14- 打印所有的结果
					while (rs.next()) {
						System.out.println("date = " + rs.getTimestamp("date"));
						results.add(new Log().setId(rs.getInt("id"))
								.setUserid(rs.getInt("userid"))
								.setDate(rs.getTimestamp("date"))
								.setPlace(rs.getString("place"))
								.setType(rs.getString("type"))
								.setPrice(rs.getFloat("price"))
								.setRemain(rs.getDouble("remain")));
					}
					result = results;
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (pstatement != null)
				try {
					pstatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return result;
	}

	public static Long getLostId(Lost lost) {

		// 1- 构建SQL实例
		SQL sql = new SQL(SQL_CHECKLOGINER("id")).add(lost.getId())
				.add(lost.getPassword()).setSqlType(SQL_TYPE_CHECK);

		// 2- 执行并返回结果
		return (Long) excuteSQL(sql);
	}

	public static Long getChangeId(Change change) {

		// 1- 构建SQL实例
		SQL sql = new SQL(SQL_CHECKLOGINER("id")).add(change.getId())
				.add(change.getCurpassword()).setSqlType(SQL_TYPE_CHECK);

		// 2- 执行并返回结果
		return (Long) excuteSQL(sql);
	}

	public static Long getLoginerId(Loginer loginer) {

		// 1- 构建SQL实例
		SQL sql = new SQL(SQL_CHECKLOGINER("username"))
				.add(loginer.getUsername()).add(loginer.getPassword())
				.setSqlType(SQL_TYPE_CHECK);

		// 2- 执行并返回结果
		return (Long) excuteSQL(sql);
	}

	public static boolean addUser(User user) {

		// 1- 要执行的SQL语句
		SQL sql = new SQL(SQL_ADD_USER).add(user.getUsername())
				.add(user.getPassword()).setSqlType(SQL_TYPE_ADD);

		// 2- 执行并返回结果
		return (Boolean) excuteSQL(sql);
	}

	public static int getLoginerId(String username) {

		// 1- 要执行的SQL语句
		SQL sql = new SQL(SQL_CHECKUSERNAME).add(username).setSqlType(
				SQL_TYPE_CHECK);

		// 2- 执行并返回结果
		return (Integer) excuteSQL(sql);
	}

	@SuppressWarnings("unchecked")
	public static List<Log> seeLog(Long userid, Long pageIndex, String tableName) {

		// 0- 检查 page index 合法性
		if (pageIndex > 0)
			pageIndex--;

		// 1- 要执行的SQL语句
		SQL sql = new SQL(SQL_SEE_LOG(tableName)).setSqlType(SQL_TYPE_SEE)
				.add(userid).add(15 * pageIndex).add(15);

		// 2- 执行并返回结果
		return (List<Log>) excuteSQL(sql);
	}

	public static Long countLog(Long userid, String tableName) {

		// 1- 要执行的SQL语句
		SQL sql = new SQL(SQL_COUNT_LOG(tableName)).setSqlType(SQL_TYPE_COUNT)
				.add(userid);
		// 2- 执行并返回结果
		return (Long) excuteSQL(sql);
	}

	static char[] chars = "一二三四五六七八".toCharArray();

	public static void addLog(String tableName) {

		Random random = new Random();
		String place = null;

		for (int i = 0; i < 1000; i++) {

			// 0- 创建记录
			place = "2舍热水器";
			Log log = new Log().setUserid(147).setDate(new Date())
					.setPlace(place).setType("持卡人消费")
					.setPrice((float) (random.nextInt(300) / 100.0))
					.setRemain(random.nextInt(3000) / 100.0);

			// 1- 要执行的SQL语句
			SQL sql = new SQL(SQL_ADD_LOG(tableName)).setSqlType(SQL_TYPE_ADD)
					.add(log.getUserid()).add(log.getDate())
					.add(log.getPlace()).add(log.getType()).add(log.getPrice())
					.add(log.getRemain());

			// 2- 执行并返回结果
			excuteSQL(sql);
		}
	}

	public static void main(String[] args) {
		addLog("water");
	}

	public static Boolean changePassword(Change change) {

		// 1- 要执行的SQL语句
		SQL sql = new SQL(SQL_CHANGE_PASSWORD).add(change.getNewpassword())
				.add(change.getId()).setSqlType(SQL_TYPE_ADD);

		// 2- 执行并返回结果
		return (Boolean) excuteSQL(sql);
	}

	public static Boolean addPickup(Pickup pickup) {

		// 1- 要执行的SQL语句
		SQL sql = new SQL(SQL_ADD_PICKUP).add(pickup.getSchool())
				.add(pickup.getStuid()).add(pickup.getName())
				.add(pickup.getCardid()).add(new Date())
				.setSqlType(SQL_TYPE_ADD);

		// 2- 执行并返回结果
		return (Boolean) excuteSQL(sql);
	}
}
