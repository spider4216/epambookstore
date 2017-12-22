package component.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import component.dao.book.IBookDAO;
import component.dao.book.MYSQLBookDAO;
import component.dao.book.exception.DaoBookException;
import component.dao.factory.exception.MysqlDaoException;
import component.dao.user.IUserDao;
import component.dao.user.MysqlUserDao;
import component.dao.user.exception.DaoUserException;

public class MYSQLDAOFactory extends DAOFactory {
	
	private static synchronized Connection createConnection() throws MysqlDaoException {
		ResourceBundle resource = ResourceBundle.getBundle("config.db");
		String url = resource.getString("url");
		String driver = resource.getString("driver");
		String user = resource.getString("user");
		String pass = resource.getString("password");
		
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new MysqlDaoException("Cannot create DB connection", e);
		}

		try {
			return DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			throw new MysqlDaoException("Problem with connection", e);
		}
	}

	public IBookDAO getBookDAO() throws DaoBookException {
		Connection con = null;
		try {
			con = createConnection();
		} catch (MysqlDaoException e) {
			throw new DaoBookException("Problem with create connection", e);
		}
		
		return new MYSQLBookDAO(con);
	}
	
	public IUserDao getUserDAO() throws DaoUserException {
		// TODO DRY
		Connection con = null;
		try {
			con = createConnection();
		} catch (MysqlDaoException e) {
			throw new DaoUserException("Problem with create connection", e);
		}
		
		return new MysqlUserDao(con);
	}

}
