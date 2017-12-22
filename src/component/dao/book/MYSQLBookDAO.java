package component.dao.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import component.dao.book.exception.DaoBookException;
import entity.Book;

public class MYSQLBookDAO implements IBookDAO {
	
	private Connection connection = null;
	
	private Book entity = null;	

	public MYSQLBookDAO(Connection connection) {
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
			pr.setInt(6, entity.getPages());
			
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

	public ArrayList<Book> findBooks() {
		return null;
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
			book.setPages(rs.getInt("pages"));
			
			return book;
		} catch (SQLException e) {
			throw new DaoBookException("Cannot find book", e);
		}
	}

}
