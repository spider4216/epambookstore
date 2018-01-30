package com.epam.component.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.epam.component.dao.exception.ConnectionPoolException;

/**
 * This realisation of connection pool was got from
 * manual which I get in Java Lab. But it was refactored
 * by mine a little bit, e.g I added single connection
 * feature for transaction use
 * 
 * @author Yuriy Sirotenko
 */
public class ConnectionPool {
	private static ConnectionPool instance = null;
	
	private ArrayList<Connection> freeConnection = new ArrayList<>();
	
	private String url;
	
	private String driver;
	
	private String user;
	
	private String pass;
	
	private Integer maxConnection;
	
	/**
	 * Need for transaction
	 */
	private Boolean useSingleConnection = false;
	
	private Connection singleConnection = null;

	public ConnectionPool() throws ConnectionPoolException {
		ResourceBundle resource = ResourceBundle.getBundle("com.epam.config.db");
		url = resource.getString("url");
		driver = resource.getString("driver");
		user = resource.getString("user");
		pass = resource.getString("password");
		maxConnection = Integer.parseInt(resource.getString("max_connection"));
		
		loadDriver();
	}
	
	private void loadDriver() throws ConnectionPoolException {
		try {
			Class.forName(driver).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new ConnectionPoolException("Cannot create DB connection", e);
		}
	}
	
	public synchronized static ConnectionPool getInstance() throws ConnectionPoolException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		
		return instance;
	}
	
	public synchronized Connection getConnection() throws ConnectionPoolException {
		if (useSingleConnection == true) {
			if (singleConnection == null) {
				singleConnection = newConnection();
			}
			
			return singleConnection;
		}
		
		Connection con = null;
		
		if (!freeConnection.isEmpty()) {
			con = (Connection) freeConnection.get(freeConnection.size() - 1);
			freeConnection.remove(con);
			
			try {
				if (con.isClosed()) {
					con = getConnection();
				}
			} catch (SQLException e) {
				con = getConnection();
			}
		} else {
			con = newConnection();
		}
		
		return con;
	}
	
	private Connection newConnection() throws ConnectionPoolException {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			throw new ConnectionPoolException("cannot create new connection", e);
		}
		
		return con;
	}
	
	public synchronized void freeConnection(Connection con) {
		if (useSingleConnection != true && (con != null) && (freeConnection.size() <= maxConnection)) {
			freeConnection.add(con);
		}
	}
	
	public void useOneConnection(Boolean val) {
		useSingleConnection = val;
	}
}
