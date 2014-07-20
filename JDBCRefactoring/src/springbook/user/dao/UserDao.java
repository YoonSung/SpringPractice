package springbook.user.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;

public class UserDao {
	
	private DataSource dataSource;

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
		this.dataSource = dataSource;
	}
	
	public void add(User user) throws SQLException {
		Connection connection = dataSource.getConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
		preparedStatement.setString(1,  user.getId());
		preparedStatement.setString(2, user.getName());
		preparedStatement.setString(3, user.getPassword());
		
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		connection.close();
	}
	
	public User get(String id) throws SQLException {
		Connection connection = dataSource.getConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
		
		preparedStatement.setString(1,  id);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		User user = null;
		if (resultSet.next()){
			user = new User();
			user.setId(resultSet.getString("id"));
			user.setName(resultSet.getString("name"));
			user.setPassword(resultSet.getString("password"));
			
		}
		
		resultSet.close();
		preparedStatement.close();
		connection.close();
		
		if (user == null) throw new EmptyResultDataAccessException(1);
		return user;
	}
	
	public void deleteAll() throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users");
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		connection.close();
	}
	
	public int getCount() throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM users");
		
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		int count = resultSet.getInt(1);
		
		resultSet.close();
		preparedStatement.close();
		connection.close();
		
		return count;
	}
}
