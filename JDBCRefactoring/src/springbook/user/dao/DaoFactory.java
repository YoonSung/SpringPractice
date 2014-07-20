package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration //애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
	
	@Bean //오브젝트 생성을 담당하는 IOC용 메소드라는 표시
	public UserDao userDao() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
		UserDao userDao = context.getBean("userDao", UserDao.class);
		context.close();
		return userDao;
	}
}
