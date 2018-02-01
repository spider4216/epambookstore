package com.epam.component.dao;

import java.sql.Connection;
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
	public final void closeResources(PreparedStatement pr, ResultSet res, Connection con) throws SQLException {
		pr.close();
		closePartResources(res, con);
	}
	
	public final void closeResources(Statement st, ResultSet res, Connection con) throws SQLException {
		st.close();
		closePartResources(res, con);
	}
	
	public final void closeResources(PreparedStatement pr, Connection con) throws SQLException {
		pr.close();
		con.close();
	}
	
	private void closePartResources(ResultSet res, Connection con) throws SQLException {
		res.close();
		con.close();
	}
}
