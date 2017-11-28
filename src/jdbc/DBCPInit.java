package jdbc;

import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DBCPInit extends HttpServlet {
	@Override
	public void init() throws ServletException {
		loadJDBCDriver();
		initConnectionPool();
	}
	
	private void loadJDBCDriver(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch (ClassNotFoundException ex){
			throw new RuntimeException("fail to load JDBC Driver", ex);
		}
	}
	
	private void initConnectionPool(){
		try{
			String jdbcURL = "jdbc:mysql://gondr.iptime.org:3306/info14?" 
					+ "useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul";
			String dbUser = "info14";
			String dbPass = "1234";
			
			//ConnectionFactory 객체를 만들어서 연결을 생성할 준비를 한다.
			ConnectionFactory cFactory 
				= new DriverManagerConnectionFactory(jdbcURL, dbUser, dbPass);
			//연결을 Pool로 만들어서 관리해줄 PoolableConnectionFactory생성
			PoolableConnectionFactory pcFactory 
				= new PoolableConnectionFactory(cFactory, null);
			//연결이 올바른지 테스트할 쿼리로 SELECT 1을 선언해둠
			pcFactory.setValidationQuery("SELECT 1");
			
			GenericObjectPoolConfig poolCfg = new GenericObjectPoolConfig();
			//연결 검사주기 5분으로 설정
			poolCfg.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			//idle상태일때 검사 실행
			poolCfg.setTestWhileIdle(true);
			//연결의 최소개수
			poolCfg.setMinIdle(4);
			//연결의 최대개수
			poolCfg.setMaxTotal(50);
			
			//위의 설정값을 pcFactory를 통해 풀을 생성함.
			GenericObjectPool<PoolableConnection> connPool 
				= new GenericObjectPool<>(pcFactory, poolCfg);
			//만들어진 풀을 pcFactory에 설정해줌.
			pcFactory.setPool(connPool);
			
			//해당 풀을 관리해줄 풀 드라이버를 만들고 거기에 이름과 풀을 연결해줌. 앞으로 gondr 이란 이름을 DB풀에 접근 가능
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver pDriver 
				= (PoolingDriver)DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			pDriver.registerPool("gondr", connPool);
			
		} catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

}

