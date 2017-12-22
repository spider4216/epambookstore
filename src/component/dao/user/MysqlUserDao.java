package component.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import component.dao.user.exception.DaoUserException;
import entity.User;

public class MysqlUserDao implements IUserDao {
	
	private Connection connection = null;
	
	private User entity = null;	

	public MysqlUserDao(Connection connection) {
		this.connection = connection;
	}
	
	public void setUserEntity(User entity) {
		this.entity = entity;
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

	public User findUser(Integer id) throws DaoUserException {
		return null;
	}
	
}
