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
	static final String driver = "com.mysql.cj.jdbc.Driver";
	private final static String ID = "Y20112";
	private final static String PASSWORD = "1234";
	static final String URL = "jdbc:mysql://gondr.iptime.org:3306/" + ID + "?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul";

	@Override
	public void init() throws ServletException {
		System.out.println("서버 시작했음!");
		loadJDBCDriver();
		initConnectionPool();
	}

	private void loadJDBCDriver() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("fail to load JDBC Driver", e);
		}
	}

	private void initConnectionPool() {
		try {
			// ConnectionFactory 객체를 만들어서 연결을 생성할 준비를 한다.
			ConnectionFactory cFactory = new DriverManagerConnectionFactory(URL, ID, PASSWORD);
			// 연결을 Pool로 만들어서 관리해줄 PoolableConnectionFactory 생성
			PoolableConnectionFactory pcFactory = new PoolableConnectionFactory(cFactory, null);
			// 연결이 올바른지 테스트할 쿼리로 SELECT 1을 선언해둠
			pcFactory.setValidationQuery("SELECT 1");
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			// 연결 검사주기 5분으로 설정
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			// idle상태일때 검사 실행
			poolConfig.setTestWhileIdle(true);
			// 연결의 최소개수
			poolConfig.setMinIdle(4);
			// 연결의 최대개수
			poolConfig.setMaxTotal(50);

			// 위의 설정값을 pcFactory를 통해 풀을 생성함.
			GenericObjectPool<PoolableConnection> connPool = new GenericObjectPool<>(pcFactory, poolConfig);
			// 만들어진 풀을 pcFactory에 설정해줌.
			pcFactory.setPool(connPool);
			// 해당 풀을 관리해줄 풀 드라이버를 만들고 거기에 이름과 풀을 연결해줌. 앞으로 gondr 이란 이름을 DB풀에 접근 가능
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver pDriver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			pDriver.registerPool("OrOl", connPool);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
