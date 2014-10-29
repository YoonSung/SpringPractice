package springbook.user.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserServiceTest {

	@Autowired
	UserDao userDao;
	
	@Autowired
	UserService userService;
	
	List<User> users;
	
	@Before
	public void setUp() {
		users = new ArrayList<User>();
		
		users.add(new User("userId_1", "userName_1", "userPasswordk_1", Level.BASIC, UserService.MIN_LOGCOUNT_FOR_SILVER-1, 0));
		users.add(new User("userId_2", "userName_2", "userPasswordk_2", Level.BASIC, UserService.MIN_LOGCOUNT_FOR_SILVER, 0));
		users.add(new User("userId_3", "userName_3", "userPasswordk_3", Level.SILVER, 60, UserService.MIN_RECCOMEND_FOR_GOLD-1));
		users.add(new User("userId_4", "userName_4", "userPasswordk_4", Level.SILVER, 60, UserService.MIN_RECCOMEND_FOR_GOLD));
		users.add(new User("userId_5", "userName_5", "userPasswordk_5", Level.GOLD, 100, Integer.MAX_VALUE));
	}
	
	@Test
	public void upgradeLevels() throws Exception {
		userDao.deleteAll();
		
		for (User user : users) {
			userDao.add(user);
		}
		
		userService.upgradeLevels();
		
		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), false);
	}

	private void checkLevel(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		
		if (upgraded)
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		else
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
	}
}