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
	
	public User findOneByUsername(String username) throws DaoUserException {
		String sqlInsert = "SELECT * FROM user WHERE username = ?";

		User entity = new User();
		ResultSet res = null;

		try {
			PreparedStatement pr = connection.prepareStatement(sqlInsert);
			pr.setString(1, username);
			res = pr.executeQuery();
			res.next();
			
			if (res.wasNull()) {
				throw new DaoUserException("User not found");
			}

			entity.setId(res.getInt("id"));
			entity.setUsername(res.getString("username"));
			entity.setPassword(res.getString("password"));
			entity.setFirstName(res.getString("first_name"));
			entity.setLastName(res.getString("last_name"));
			entity.setGender(res.getInt("gender"));

			return entity;
		} catch (SQLException e) {
			throw new DaoUserException("Cannot insert user", e);
		}
	}
}
