package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Board;
import jdbc.JdbcUtil;

public class BoardDAO {
	private static BoardDAO bd = new BoardDAO();
	
	public static BoardDAO getInstance(){
		return bd;
	}
	
	private BoardDAO(){
		
	}
	
	public void insert(Connection conn, Board board) throws SQLException {
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
	
	public Board getBoard(Connection conn, int id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null; //SQL결과를 저장할 resultSet
		try {
			pstmt = conn.prepareStatement("SELECT * FROM boards WHERE id = ?");
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				return makeBoardFromRS(rs); //존재하면 리턴
			}else {
				return null; //존재하지 않으면 null 리턴
			}
			
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public List<Board> getList(Connection conn, int start, int cnt) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null; //SQL결과를 저장할 resultSet
		List<Board> boards = new ArrayList<Board>(); //Board 객체를 담을 리스트를 만듦
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
	
	private Board makeBoardFromRS(ResultSet rs) throws SQLException{
		Board board = new Board();
		board.setId(rs.getInt("id"));
		board.setTitle(rs.getString("title"));
		board.setWriter(rs.getString("writer"));
		board.setDate(rs.getDate("date"));
		board.setContent(rs.getString("content"));
		
		return board;
	}
	
	public int deleteBoard(Connection conn, int id) throws SQLException{
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("DELETE FROM boards WHERE id = ?");
			
			pstmt.setInt(1, id);
			
			pstmt.executeUpdate();
			
			return 1;
		} catch (Exception e){
			e.printStackTrace();
			return -1;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public int getTotalCnt(Connection conn) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT count(*) as cnt FROM boards");
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("cnt");
			}else {
				return -1;
			}
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}finally {
			JdbcUtil.close(pstmt);
		}
	}
	
}
