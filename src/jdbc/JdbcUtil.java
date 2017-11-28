package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class JdbcUtil {
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				//do nothing;
			}
		}
	}
	
	
	public static void close(Statement stmt){
		if(stmt != null){
			try{
				stmt.close();
			}catch(SQLException ex){
				//do nothing;
			}
		}
	}
	
	public static void close(PreparedStatement pstmt){
		if(pstmt != null){
			try{
				pstmt.close();
			}catch(SQLException ex){
				//do nothing;
			}
		}
	}
	
	public static void close(Connection conn){
		if(conn != null){
			try{
				conn.close();
			}catch(SQLException ex){
				//do nothing;
			}
		}
	}
	
	public static void rollback(Connection conn){
		if(conn != null){
			try{
				conn.rollback();
			}catch(SQLException ex){
				//do nothing;
			}
		}
	}
}

