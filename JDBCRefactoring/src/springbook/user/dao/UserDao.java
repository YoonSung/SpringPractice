package springbook.user.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDao {
	
	private ConnectionMaker connectionMaker;
	
	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
	
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
