package component.dao.user;

import java.util.ArrayList;

import component.dao.user.exception.DaoUserException;
import entity.User;

public interface IUserDao {
	public Integer insertUser(User entity) throws DaoUserException;
	
	public Boolean deleteUser() throws DaoUserException;
		
	public User findUser(Integer id) throws DaoUserException;
}
