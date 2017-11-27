package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BoardDAO;
import domain.BoardVO;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;

public class BoardService {
	private static BoardService bs = new BoardService();

	public static BoardService getInstance() {
		return bs;
	}

	private BoardService() {}

	public List<BoardVO> getList(int start, int cnt) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			List<BoardVO> list = BoardDAO.getInstance().getList(conn, start, cnt);

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	public int write(BoardVO data) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			BoardDAO.getInstance().insert(conn, data);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	public BoardVO read(int id) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			return BoardDAO.getInstance().getBoard(conn, id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}
}
