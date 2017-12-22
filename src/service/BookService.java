package service;

import component.dao.book.MYSQLBookDAO;
import component.dao.book.exception.DaoBookException;
import component.dao.factory.DAOFactory;
import entity.Book;

/**
 * Service for Book logic
 * 
 * @author Yuriy Sirotenko
 */
public class BookService {
	private MYSQLBookDAO bookDAO;
	
	public BookService(Book entity) throws DaoBookException {
		DAOFactory MYSQLFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		bookDAO = (MYSQLBookDAO)MYSQLFactory.getBookDAO();
		bookDAO.setBookEntity(entity);
	}
	
	public Boolean insert(Book entity) throws DaoBookException {
		Integer res = bookDAO.insertBook();
		
		if (res <= 0) {
			return false;
		}
		
		return true;
	}
	
	public Boolean delete(Book entity) throws DaoBookException {
		bookDAO.setBookEntity(entity);
		
		return bookDAO.deleteBook();
	}
	
	public Book findById(Integer id) throws DaoBookException {
		return bookDAO.findBook(id);
	}
}
