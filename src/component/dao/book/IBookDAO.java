package component.dao.book;

import java.util.ArrayList;

import component.dao.book.exception.DaoBookException;
import entity.Book;

public interface IBookDAO {
	public Integer insertBook() throws DaoBookException;
	
	public Boolean deleteBook() throws DaoBookException;
	
	public ArrayList<Book> findBooks();
	
	public Book findBook(Integer id) throws DaoBookException;
}
