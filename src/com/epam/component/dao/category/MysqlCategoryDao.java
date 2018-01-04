package com.epam.component.dao.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.category.exception.DaoCategoryException;

public class MysqlCategoryDao implements ICategoryDao {
	private Connection connection = null;
	
	public MysqlCategoryDao(Connection connection) {
		this.connection = connection;
	}

	public ResultSet findAll() throws DaoCategoryException {
		String sqlFind = "SELECT * FROM category";		
		try {
			Statement pr = connection.createStatement();
			
			ResultSet rs = pr.executeQuery(sqlFind);
			
			return rs;
		} catch (SQLException e) {
			throw new DaoCategoryException("Cannot find categories", e);
		}
	}
	
	public ResultSet findOneById(Integer id) throws DaoCategoryException {
		String sqlFind = "SELECT * FROM category WHERE id = ?";		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			ResultSet rs = pr.executeQuery();
			rs.next();
			
			return rs;
		} catch (SQLException e) {
			throw new DaoCategoryException("Cannot find category by id", e);
		}
	}
}
