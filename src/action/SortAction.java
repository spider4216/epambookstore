package action;

import java.util.HashMap;

import component.dao.book.exception.DaoBookException;
import entity.Book;
import service.BookService;

public class SortAction implements IAction {
	public Object run(HashMap<String, String> params) throws DaoBookException {
		BookService service = new BookService(new Book());
		
		Book book = null;
		book = service.findById(3);
		
		return "My First Sort Action And First Science Method " + book.getName();
	}
}
