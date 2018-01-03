package com.epam.component.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.component.dao.user.exception.DaoUserException;
import com.epam.entity.User;

public class MysqlUserDao implements IUserDao {
	
	private Connection connection = null;
	
	public MysqlUserDao(Connection connection) {
		this.connection = connection;
	}
	
	public Integer insertUser(User entity) throws DaoUserException {
		String sqlInsert = "INSERT INTO user (username, password, first_name, last_name, gender) VALUES (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlInsert);
			pr.setString(1, entity.getUsername());
			pr.setString(2, entity.getPassword());
			pr.setString(3, entity.getFirstName());
			pr.setString(4, entity.getLastName());
			// TODO constant here
			pr.setInt(5, entity.getGender());			
			return pr.executeUpdate();
		} catch (SQLException e) {
			throw new DaoUserException("Cannot insert user", e);
		}
	}

	public Boolean deleteUser() throws DaoUserException {
		return null;
	}

	public User findOneById(Integer id) throws DaoUserException {
		return null;
	}
	
	public ResultSet findOneByUsername(String username) throws DaoUserException {
		String sqlFind = "SELECT * FROM user WHERE username = ?";

		ResultSet res = null;

		try {
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setString(1, username);
			res = pr.executeQuery();
			res.next();
						
			if (res.getRow() <= 0) {
				throw new DaoUserException("User not found");
			}

			return res;
		} catch (SQLException e) {
			throw new DaoUserException("Cannot find user", e);
		}
	}
	
	public Integer updateSissionIdByUsername(String username, String sessionId) throws DaoUserException {
		String sqlUpdate = "UPDATE user SET session_id = ? where username = ?";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlUpdate);
			pr.setString(1, sessionId);
			pr.setString(2, username);
			return pr.executeUpdate();
		} catch (SQLException e) {
			throw new DaoUserException("Cannot set session id for some reason", e);
		}
	}
	
	// TODO Interface
	public ResultSet findOneBySessionId(String sessionId) throws DaoUserException {
		String sqlSelect = "SELECT * FROM user WHERE session_id = ?";

		ResultSet res = null;

		try {
			PreparedStatement pr = connection.prepareStatement(sqlSelect);
			pr.setString(1, sessionId);
			res = pr.executeQuery();
			res.next();
						
			if (res.getRow() <= 0) {
				throw new DaoUserException("User not found");
			}

			return res;
		} catch (SQLException e) {
			throw new DaoUserException("Cannot find user by session id", e);
		}
	}
}
