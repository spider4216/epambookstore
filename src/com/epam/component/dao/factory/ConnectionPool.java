package com.epam.component.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.epam.component.dao.exception.ConnectionPoolException;

public class ConnectionPool {
	private static ConnectionPool instance;
	
	private ArrayList<Connection> freeConnection = new ArrayList<>();
	
	private String url;
	
	private String driver;
	
	private String user;
	
	private String pass;
	
	private Integer maxConnection;

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
		if ((con != null) && (freeConnection.size() <= maxConnection)) {
			freeConnection.add(con);
		}
	}
	
	public synchronized void release() throws ConnectionPoolException {
		Iterator allConnections = freeConnection.iterator();
		
		while (allConnections.hasNext()) {
			Connection con = (Connection) allConnections.next();
			
			try {
				con.close();
			} catch (SQLException e) {
				throw new ConnectionPoolException("Cannot close connection", e);
			}
		}
		
		freeConnection.clear();
	}
	
	public Integer getConnectionCount() {
		return freeConnection.size();
	}
}
