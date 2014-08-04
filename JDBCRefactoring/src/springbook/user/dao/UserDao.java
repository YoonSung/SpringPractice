package springbook.user.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.User;

public class UserDao {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
//	의존성 주입
//	public UserDao(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
	
//	DaoFactory방식 1. (생성자)
//	public UserDao() {
//		DaoFactory daoFactory = new DaoFactory();
//		this.connectionMaker = daoFactory.connectionMaker();
//	}
	
//	의존관계 검색을 이용하는 방식
//	public UserDao() {
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
//		context.close();
//	}
	
//DaoFactory방식 2. (수정자)	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
		this.dataSource = dataSource;
	}

	//왜 final이 아니여도 되는거지?? 1.8의 특징인가?!
	//public void add(User user) throws SQLException {
	public void add(final User user) throws SQLException {
		this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES (?, ?, ?)", user.getId(), user.getName(), user.getPassword());
	}
	
	public User get(String id) throws SQLException {
		return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", 
																			new Object[]{id}, 
																			new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				return user;
			}
			
		});
	}
	
	public void deleteAll() throws SQLException{
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException {
				return connection.prepareStatement("DELETE FROM users");
			}
		});
	}

	public int getCount() throws SQLException {
		return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
	}

	public List<User> getAll() {
		return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id", new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				
				return user;
			}
		});
	}
}
