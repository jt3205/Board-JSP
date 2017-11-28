package service;

import java.sql.Connection;

import dao.UserDao;
import domain.User;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;

public class UserService {
	//싱글톤 구현
	private static UserService us = new UserService();
	
	public static UserService getInstance()
	{
		return us;
	}
	
	private UserService()
	{
		
	}
	//싱글톤 구현 끝.
	
	//회원가입 서비스.
	public int join(User user)
	{
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection(); //DB연결 가져오고
			User data = UserDao.getInstance().selectById(conn, user.getId()); //해당 아이디의 유저가 있는지 찾아보고
			
			if(data != null){ //회원이 존재할경우에는 0 리턴
				return 0;
			}
			
			UserDao.getInstance().insert(conn, user);
			return 1; //정상적으로 가입시 1리턴.
		} catch (Exception e){
			e.printStackTrace();
			return -1; //회원 삽입 과정중 오류 발생시에는 -1 리턴
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	//회원 정보 조회 서비스
	public User getUser(String id)
	{
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection(); //DB연결 가져오고
			User data = UserDao.getInstance().selectById(conn, id); //해당 아이디의 유저가 있는지 찾아보고
			
			return data;
		} catch (Exception e){
			e.printStackTrace();
			return null; //회원 삽입 과정중 오류 발생시에는 null 리턴
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
