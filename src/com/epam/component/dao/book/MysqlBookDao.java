package com.epam.component.dao.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.epam.component.dao.book.exception.DaoBookException;
import com.epam.entity.Book;

public class MysqlBookDao implements IBookDao {
	
	private Connection connection = null;
	
	private Book entity = null;	

	public MysqlBookDao(Connection connection) {
		this.connection = connection;
	}
	
	public void setBookEntity(Book entity) {
		this.entity = entity;
	}

	public Integer insertBook() throws DaoBookException {
		String sqlInsert = "INSERT INTO books (name, price, author, description, isbn, pages) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlInsert);
			pr.setString(1, entity.getName());
			pr.setDouble(2, entity.getPrice());
			pr.setString(3, entity.getAuthor());
			pr.setString(4, entity.getDescription());
			pr.setString(5, entity.getIsbn());
			pr.setInt(6, entity.getPage());
			
			return pr.executeUpdate();
		} catch (SQLException e) {
			throw new DaoBookException("Cannot insert book", e);
		}
	}

	// TODO not checking
	public Boolean deleteBook() throws DaoBookException {
		String sqlDelete = "DELETE FROM books WHERE id = ?";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlDelete);
			pr.setInt(1, entity.getId());
			
			pr.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new DaoBookException("Cannot delete book", e);
		}
	}

	public ArrayList<Book> findBooks() throws DaoBookException {
		String sqlFind = "SELECT * FROM books";
		ArrayList<Book> booksCollection = new ArrayList<>();
		
		try {
			Statement pr = connection.createStatement();
			
			ResultSet rs = pr.executeQuery(sqlFind);
			
			while (rs.next()) {
				Book book = new Book();
				
				// TODO вынести в другое место. Сделать как билдер. Но вот куда?
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setPrice(rs.getDouble("price"));
				book.setAuthor(rs.getString("author"));
				book.setDescription(rs.getString("description"));
				book.setIsbn(rs.getString("isbn"));
				book.setPage(rs.getInt("page"));
				booksCollection.add(book);
			}
			
		} catch (SQLException e) {
			throw new DaoBookException("Cannot find book", e);
		}
			
		return booksCollection;
	}
	
	public Book findBook(Integer id) throws DaoBookException {
		String sqlFind = "SELECT * FROM books WHERE id = ?";
		
		try {
			PreparedStatement pr = connection.prepareStatement(sqlFind);
			pr.setInt(1, id);
			
			ResultSet rs = pr.executeQuery();
			rs.next();
			Book book = new Book();
			
			// TODO вынести в другое место. Сделать как билдер. Но вот куда?
			book.setId(rs.getInt("id"));
			book.setName(rs.getString("name"));
			book.setPrice(rs.getDouble("price"));
			book.setAuthor(rs.getString("author"));
			book.setDescription(rs.getString("description"));
			book.setIsbn(rs.getString("isbn"));
			book.setPage(rs.getInt("page"));
			
			return book;
		} catch (SQLException e) {
			throw new DaoBookException("Cannot find book", e);
		}
	}
}
