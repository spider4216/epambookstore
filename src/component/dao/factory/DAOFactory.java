package component.dao.factory;

import component.dao.book.IBookDAO;
import component.dao.book.exception.DaoBookException;
import component.dao.user.IUserDao;
import component.dao.user.exception.DaoUserException;

public abstract class DAOFactory {
	public static final int MYSQL = 1;
	
	public abstract IBookDAO getBookDAO() throws DaoBookException;
	
	public abstract IUserDao getUserDAO() throws DaoUserException;
	
	public static DAOFactory getDAOFactory(Integer whichFactory) {
		switch (whichFactory) {
			case MYSQL :
				return new MYSQLDAOFactory();
			default :
				return null;
		}
	}
}
