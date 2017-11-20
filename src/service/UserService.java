package service;

import java.sql.Connection;

import dao.UserDAO;
import domain.UserVO;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;

public class UserService {
	// 싱글톤 구현
	private static UserService us = new UserService();

	public static UserService getInstance() {
		return us;
	}

	private UserService() {
	}
	// 싱글톤 구현 끝.

	// 회원가입 서비스.
	public int join(UserVO vo) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection(); // DB연결 가져오고
			// UserVO data = UserDAO.getInstance().selectById(conn, user.getId()); // 해당
			// 아이디의 유저가 있는지 찾아보고
			UserDAO dao = UserDAO.getInstance();
			UserVO data = dao.selectById(conn, vo.getId());
			if (vo != null) { // 회원이 존재할경우에는 0 리턴
				return 0;
			}
			dao.insert(conn, vo);
			// UserDAO.getInstance().insert(conn, vo);
			return 1; // 정상적으로 가입시 1리턴.
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // 회원 삽입 과정중 오류 발생시에는 -1 리턴
		} finally {
			JdbcUtil.close(conn);
		}
	}

	public UserVO getUser(String id) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			UserVO data = UserDAO.getInstance().selectById(conn, id);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
