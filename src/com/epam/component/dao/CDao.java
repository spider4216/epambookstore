package com.epam.component.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CDao {
	public void closeResources(PreparedStatement pr, ResultSet res, Connection con) throws SQLException {
		pr.close();
		res.close();
		con.close();
	}
}
