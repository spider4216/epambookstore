package action;

import java.util.HashMap;

public interface IAction {
	abstract public Object run(HashMap<String, String> params) throws Exception;
}
