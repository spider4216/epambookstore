package action;

import java.util.HashMap;

import entity.Book;
import service.BookService;

public class SortAction implements IAction {
	public Object run(HashMap<String, String> params) {
		BookService service = new BookService(new Book());
		
		Book book = null;
		try {
			book = service.findById(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "My First Sort Action And First Science Method " + book.getName();
	}
}
