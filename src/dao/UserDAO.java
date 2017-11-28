package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User;
import jdbc.JdbcUtil;


public class UserDao {
	//싱글톤 방식으로 선언해서 요청시마다 인스턴스가 생기는 것을 방지함.
	private static UserDao ud = new UserDao(); 
	
	public static UserDao getInstance(){
		return ud;
	}
	
	private UserDao(){
		//생성자는 아무것도 없는 private로 선언하여 외부 생성이 불가능하게 한다. 
		//이는 다른 인스턴스는 생성될 수 없도록 막아주고 해당 객체를 싱글톤으로 만들어준다.
	}
	
	//새로운 사용자를 넣어주는 매서드
	public void insert(Connection conn, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try{
			 pstmt = conn.prepareStatement(	"INSERT INTO users(id, name, password) VALUES (?,?,?)"); 
					
			 pstmt.setString(1, user.getId());
			 pstmt.setString(2, user.getName());
			 pstmt.setString(3, user.getPassword());
			 pstmt.executeUpdate();
		} finally{
			JdbcUtil.close(pstmt);
		}
	}
	
	//id에 맞는 사용자 정보를 불러오는 매서드
	public User selectById(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			User user = null;
			if(rs.next()){
				user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
			}
			return user;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

}
