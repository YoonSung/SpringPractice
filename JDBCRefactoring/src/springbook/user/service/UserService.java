package springbook.user.service;

import java.util.List;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserService {
	
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		
		userDao.add(user);
	}
	
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (canUpgradeLevel(user)) {
				try {
					upgradeLevel(user);
					userDao.update(user);
				} catch (IllegalArgumentException e) {
					continue;
				}
			}
		}
	}

	protected void upgradeLevel(User user) {
		user.upgradeLevel();
	}

	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		
		switch (currentLevel) {
			case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
			case SILVER: return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
			case GOLD: return false;
			default: throw new IllegalArgumentException("Unknown Level : "+currentLevel);
		}
	}
}
