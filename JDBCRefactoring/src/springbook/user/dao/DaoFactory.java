package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration //애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
	
	@Bean //오브젝트 생성을 담당하는 IOC용 메소드라는 표시
	public UserDao userDao() {
		//의존성 주입방식
		//return new UserDao(connectionMaker());
		
//		DaoFactory방식 1. (생성자)
		//return new UserDao();
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
		UserDao userDao = context.getBean("userDao", UserDao.class);
		return userDao;
	}
}
