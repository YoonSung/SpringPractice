package springbook.user.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springbook.user.domain.User;

public class UserDao {
	
	private ConnectionMaker connectionMaker;

//	의존성 주입
//	public UserDao(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
	
//	DaoFactory방식
	public UserDao() {
		DaoFactory daoFactory = new DaoFactory();
		this.connectionMaker = daoFactory.connectionMaker();
	}
	
//	의존관계 검색을 이용하는 방식
//	public UserDao() {
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
//		context.close();
//	}
	
	public void add(User user) throws SQLException, ClassNotFoundException {
		Connection connection = connectionMaker.makeConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
		preparedStatement.setString(1,  user.getId());
		preparedStatement.setString(2, user.getName());
		preparedStatement.setString(3, user.getPassword());
		
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		connection.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection connection = connectionMaker.makeConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
		
		preparedStatement.setString(1,  id);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		User user = new User();
		user.setId(resultSet.getString("id"));
		user.setName(resultSet.getString("name"));
		user.setPassword(resultSet.getString("password"));
		
		resultSet.close();
		preparedStatement.close();
		connection.close();
		
		return user;
	}
}
