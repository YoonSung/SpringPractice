package springbook.user.dao;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import springbook.user.domain.User;

public class UserDaoTest {
	
	@Test
	public void addAndGet() throws SQLException {
		DaoFactory daoFactory = new DaoFactory();
		UserDao dao = daoFactory.userDao();
		
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
		DaoFactory daoFactory = new DaoFactory();
		UserDao dao = daoFactory.userDao();
		
		User user1 = new User("lvev99251", "정윤성1", "윤성1");
		User user2 = new User("lvev99252", "정윤성2", "윤성2");
		User user3 = new User("lvev99253", "정윤성3", "윤성3");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
}
