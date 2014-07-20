package springbook.user.dao;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserDaoTest {
	
	@Autowired//변수타입과 일치하는 컨텍스트 내의 빈을 찾는다. ApplicationContext는 자동으로 bean에 추가된다.
	private ApplicationContext context;
	
	private UserDao dao;
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
		dao = this.context.getBean("userDao", UserDao.class);
		user1 = new User("lvev99251", "정윤성1", "윤성1");
		user2 = new User("lvev99252", "정윤성2", "윤성2");
		user3 = new User("lvev99253", "정윤성3", "윤성3");
	}
	
	@Test
	public void addAndGet() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		User insertUser = new User();
		insertUser.setId("lvev9925b");
		insertUser.setName("Jung");
		insertUser.setPassword("Yoonsung");
		
		dao.add(insertUser);
		
		assertThat(dao.getCount(), is(1));
		
		User selectUser = dao.get(insertUser.getId());
		assertThat(insertUser.getName(), is(selectUser.getName()));
		assertThat(insertUser.getPassword(), is(selectUser.getPassword()));
	}
	
	@Test
	public void count() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
	}
}
