package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.BoardVO;
import jdbc.JdbcUtil;

public class BoardDAO {
	private static BoardDAO bd = new BoardDAO();
	
	public static BoardDAO getInstance(){
		return bd;
	}
	
	private BoardDAO(){
		
	}
	
	public void insert(Connection conn, BoardVO board) throws SQLException {
		PreparedStatement pstmt = null;
		System.out.println(board.getContent());
		try {
			pstmt = conn.prepareStatement("INSERT INTO boards(`title`, `writer`, `date`, `content`) VALUES(?, ?, ?, ?)");
			
			pstmt.setString(1,board.getTitle());
			pstmt.setString(2,board.getWriter());
			pstmt.setDate(3, board.getDate());
			pstmt.setString(4,board.getContent());
			
			pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public List<BoardVO> getList(Connection conn, int start, int cnt) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null; //SQL결과를 저장할 resultSet
		List<BoardVO> boards = new ArrayList<BoardVO>(); //Board 객체를 담을 리스트를 만듦
		try {
			pstmt = conn.prepareStatement("SELECT * FROM boards ORDER BY id DESC LIMIT ?, ?");
			
			pstmt.setInt(1, start); //시작지점부터 
			pstmt.setInt(2, cnt); //몇개
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				do{
					boards.add(makeBoardFromRS(rs));
				}while(rs.next());
			}
			
			return boards;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	private BoardVO makeBoardFromRS(ResultSet rs) throws SQLException{
		BoardVO board = new BoardVO();
		board.setId(rs.getInt("id"));
		board.setTitle(rs.getString("title"));
		board.setWriter(rs.getString("writer"));
		board.setDate(rs.getDate("date"));
		board.setContent(rs.getString("content"));
		
		return board;
	}
	
}

