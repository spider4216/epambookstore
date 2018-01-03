package com.epam.component.dao.category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.component.dao.category.exception.DaoCategoryException;
import com.epam.entity.CategoryEntity;

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
}
