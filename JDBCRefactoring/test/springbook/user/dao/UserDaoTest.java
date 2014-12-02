package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserDaoTest {
	
	private UserDaoJdbc dao;
	private DataSource dataSource;
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
		dao = new UserDaoJdbc();
		dataSource = new SingleConnectionDataSource(
				"jdbc:mysql://54.64.59.174:3306/springPractice?useUnicode=true",
				"yoon",
				"spring",
				true
		);
		dao.setDataSource(dataSource);
		JdbcContext jdbcContext = new JdbcContext();
		jdbcContext.setDataSource(dataSource);
		
		user1 = new User("lvev99251", "JungYoonSung1", "yoonsung1", Level.BASIC, 1, 0, "testEmail");
		user2 = new User("lvev99252", "JungYoonSung2", "yoonsung2", Level.SILVER, 55, 10, "testEmail");
		user3 = new User("lvev99253", "JungYoonSung3", "yoonsung3", Level.GOLD, 100, 40, "testEmail");
	}
	
	@Test
	public void update() throws Exception {
		dao.deleteAll();
		
		dao.add(user1);
		
		user1.setName("updateName");
		user1.setPassword("updatePassword");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(999);
		user1.setEmail("testEmail");
		dao.update(user1);
		
		User updatedUser = dao.get(user1.getId());
		checkSameUser(user1, updatedUser);
	}
	
	@Test
	public void addAndGet() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		
		assertThat(dao.getCount(), is(1));
		
		User selectUser = dao.get(user1.getId());
		assertThat(user1.getName(), is(selectUser.getName()));
		assertThat(user1.getPassword(), is(selectUser.getPassword()));
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
	
	@Test
	public void getAll() throws SQLException {
		dao.deleteAll();
		
		List<User> user0 = dao.getAll();
		assertThat(user0.size(), is(0));
		
		dao.add(user1);
		List<User> users1 = dao.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, users1.get(0));
		
		dao.add(user2);
		List<User> users2 = dao.getAll();
		assertThat(users2.size(), is(2));
		checkSameUser(user1, users2.get(0));
		checkSameUser(user2, users2.get(1));
		
		dao.add(user3);
		List<User> users3 = dao.getAll();
		assertThat(users3.size(), is(3));
		checkSameUser(user1, users3.get(0));
		checkSameUser(user2, users3.get(1));
		checkSameUser(user3, users3.get(2));
	}

	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
		assertThat(user1.getLevel(), is(user2.getLevel()));
		assertThat(user1.getLogin(), is(user2.getLogin()));
		assertThat(user1.getRecommend(), is(user2.getRecommend()));
	}
	
	@Test(expected=DataAccessException.class)
	public void duplicateKey() {
		dao.deleteAll();
		dao.add(user1);
		dao.add(user1);
	}
	
	/*
	@Test
	public void sqlExceptionTranslate() {
		dao.deleteAll();

		try {
			dao.add(user1);
			dao.add(user2);
		} catch (DuplicateKeyException ex) {
			SQLException sqlEx = (SQLException)ex.getRootCause();
			SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
			
			assertThat(set.translate(null, null, sqlEx), 
					is(DuplicateKeyException.class));
		}
	}
	*/
}
