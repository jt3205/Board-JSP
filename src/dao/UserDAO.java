package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.UserVO;
import jdbc.JdbcUtil;

public class UserDAO {
	private static UserDAO dao = new UserDAO();
	
	private UserDAO() {}

	public static UserDAO getInstance() {
		return dao;
	}
	
	public void insert(Connection conn, UserVO vo) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("INSERT INTO users(id, name, password) VALUES (?,?,?)");

			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPassword());
			pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	// id에 맞는 사용자 정보를 불러오는 매서드
	public UserVO selectById(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			UserVO vo = null;
			if (rs.next()) {
				vo = new UserVO();
				vo.setId(rs.getString("id"));
				vo.setName(rs.getString("name"));
				vo.setPassword(rs.getString("password"));
			}
			return vo;
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
	}

}
