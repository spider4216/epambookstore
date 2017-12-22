package component.dao.user.exception;

public class DaoUserException extends Exception {
	public DaoUserException(String msg, Exception reason) {
		super(msg, reason);
	}
}
