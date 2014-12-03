package springbook.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.handler.TransactionHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserServiceTest {

	@Autowired
	UserDao userDao;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	List<User> users;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	MailSender mailSender;
	
	@Before
	public void setUp() {
		users = new ArrayList<User>();
		
		users.add(new User("userId_1", "userName_1", "userPasswordk_1", Level.BASIC, UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER-1, 0, "testEmail"));
		users.add(new User("userId_2", "userName_2", "userPasswordk_2", Level.BASIC, UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER, 0, "testEmail"));
		users.add(new User("userId_3", "userName_3", "userPasswordk_3", Level.SILVER, 60, UserServiceImpl.MIN_RECCOMEND_FOR_GOLD-1, "testEmail"));
		users.add(new User("userId_4", "userName_4", "userPasswordk_4", Level.SILVER, 60, UserServiceImpl.MIN_RECCOMEND_FOR_GOLD, "testEmail"));
		users.add(new User("userId_5", "userName_5", "userPasswordk_5", Level.GOLD, 100, Integer.MAX_VALUE, "testEmail"));
	}
	
	@Test
	public void upgradeLevels() throws Exception {
		userDao.deleteAll();
		
		for (User user : users) {
			userDao.add(user);
		}
		
		userServiceImpl.upgradeLevels();
		
		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
	}

	@Test
	public void upgradeAllOrNothing() throws Exception {
		
		//4번째 유저에서 에러가 발생하도록
		UserServiceImpl testUserService = new TestUserService(users.get(3).getId());
		
		//Container에서 관리하지 않으므로 수동 DI
		testUserService.setUserDao(this.userDao);
		testUserService.setMailSender(this.mailSender);
		
		
		TransactionHandler txHandler = new TransactionHandler();
		txHandler.setTarget(testUserService);
		txHandler.setTransactionManager(transactionManager);
		txHandler.setPattern("upgradeLevels");
		UserService txUserService = (UserService)Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class[]{UserService.class}, 
				txHandler
		);
		
		
		userDao.deleteAll();
		
		//데이터 생성
		for (User user : users) {
			userDao.add(user);
		}
		
		try {
			
			//TestUserService 업그레이드 작업 중에 예외가 발생해야 한다. 정상 종료라면 문제가 있으니 실패.
			txUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {
			//TestUserService가 던져주는 예외를 잡아서 계속 진행되도록 한다
		}
		
		//예외가 발생하기 전에 레벨 변경이 있었던 사용자의 레벨이 처음 상태로 바뀌었나 확인
		checkLevelUpgraded(users.get(1), false);
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		
		if (upgraded)
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		else
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
	}
	
	static class TestUserService extends UserServiceImpl {
		private String id;
		
		//예외를 발생시킬 User 오브젝트의 id를 지정할 수 있게 만든다.
		private TestUserService(String id) {
			this.id = id;
		}
		
		@Override
		protected void upgradeLevel(User user) {
			if (user.getId().equals(this.id)) throw new TestUserServiceException();
			super.upgradeLevel(user);
		}
	}
	
	@SuppressWarnings("serial")
	static class TestUserServiceException extends RuntimeException {}
	
}