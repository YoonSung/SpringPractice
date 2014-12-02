package springbook.user.service;

import java.sql.SQLException;

import springbook.user.domain.User;

public interface UserService {

	public abstract void add(User user);

	public abstract void upgradeLevels() throws SQLException;

}