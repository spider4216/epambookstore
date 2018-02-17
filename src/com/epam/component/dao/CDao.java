package com.epam.component.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * System Dao Super Class
 * This class was declared as abstract because it prevent
 * get instance of this class. CDao have to be extended by child
 * DAO classes
 * 
 * @author Yuriy Sirotenko
 */
public abstract class CDao {
	public final void closeResources(PreparedStatement pr, ResultSet res) throws SQLException {
		pr.close();
		res.close();
	}
	
	public final void closeResources(Statement st, ResultSet res) throws SQLException {
		st.close();
		res.close();
	}
	
	public final void closeResources(PreparedStatement pr) throws SQLException {
		pr.close();
	}	
}
