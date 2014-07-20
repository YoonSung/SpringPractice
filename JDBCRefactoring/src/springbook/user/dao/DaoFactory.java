package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration //애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
	
	@Bean //오브젝트 생성을 담당하는 IOC용 메소드라는 표시
	public UserDao userDao() {
		//의존성 주입방식
		//return new UserDao(connectionMaker());
		
//		DaoFactory방식 1. (생성자)
		//return new UserDao();
		
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}
	
	@Bean
	public DataSource dataSource() {
		//Spring에서 제공해주는 테스트환경에서 간단하게 사용할 수 있는 클래스
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource(); 
		
		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		//Connection connection = DriverManager.getConnection("jdbc:mysql://54.178.137.153:3306/springPractice", "yoon", "spring");
		dataSource.setUrl("jdbc:mysql://54.178.137.153:3306/springPractice");
		dataSource.setUsername("yoon");
		dataSource.setPassword("spring");
		
		return dataSource;
	}
}
