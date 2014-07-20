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
		
		User insertUser = new User();
		insertUser.setId("lvev9925b");
		insertUser.setName("Jung");
		insertUser.setPassword("Yoonsung");
		
		dao.add(insertUser);
		User selectUser = dao.get(insertUser.getId());
		assertThat(insertUser.getName(), is(selectUser.getName()));
		assertThat(insertUser.getPassword(), is(selectUser.getPassword()));
	}
}
