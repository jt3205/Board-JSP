package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
	static final String driver = "com.mysql.cj.jdbc.Driver";
	private final static String ID = "";
	private final static String PASSWORD = "";
	static final String url = "jdbc:mysql://gmsgondr.net:3306/"+ ID +"?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul";

	public static Connection getConnection() throws Exception {
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, ID, PASSWORD);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(PreparedStatement pstmt, Connection conn) {
		if (pstmt != null) {
			try {
				if (!pstmt.isClosed())
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pstmt = null;
			}
		}
		close(conn);
	}

	public static void close(ResultSet rs, PreparedStatement pstmt, Connection con) {
		if (rs != null) {
			try {
				if (!rs.isClosed())
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
		close(pstmt, con);
	}

	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
