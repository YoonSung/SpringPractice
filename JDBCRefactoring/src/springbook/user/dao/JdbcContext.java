package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
	
	DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		System.out.println("test");
		this.dataSource = dataSource;
	}
	
	public void workWithStatement(StatementStrategy stmt) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = stmt.makePreparedStatement(connection);
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
				}
			}
			
			if (connection !=null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public void executeSql(String sql) throws SQLException {
		workWithStatement(new StatementStrategy() {
			@Override
			public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql);
				return ps;
			}
		});
	}
}
