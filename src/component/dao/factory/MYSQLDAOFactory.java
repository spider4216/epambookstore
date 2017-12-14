package component.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import component.dao.book.IBookDAO;
import component.dao.book.MYSQLBookDAO;

public class MYSQLDAOFactory extends DAOFactory {
	
	private static synchronized Connection createConnection() throws SQLException {
		ResourceBundle resource = ResourceBundle.getBundle("com.epam.bookshop.config.db");
		String url = resource.getString("url");
		String driver = resource.getString("driver");
		String user = resource.getString("user");
		String pass = resource.getString("password");
		
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException e) {
			throw new SQLException("Драйвер не загружен!");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return DriverManager.getConnection(url, user, pass);
	}

	public IBookDAO getBookDAO() {
		Connection con = null;
		try {
			con = createConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new MYSQLBookDAO(con);
	}

}
