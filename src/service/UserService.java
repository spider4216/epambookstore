package service;

import component.dao.factory.DAOFactory;
import component.dao.user.MysqlUserDao;
import component.dao.user.exception.DaoUserException;
import entity.User;

public class UserService {
	private MysqlUserDao userDao;
	
	public UserService(User entity) throws DaoUserException {
		DAOFactory MYSQLFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		userDao = (MysqlUserDao)MYSQLFactory.getUserDAO();
		userDao.setUserEntity(entity);
	}

	public Boolean insert(User entity) throws DaoUserException {
		Integer res = userDao.insertUser(entity);
		
		if (res <= 0) {
			return false;
		}
		
		return true;
	}
}
