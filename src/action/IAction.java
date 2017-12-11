package action;

import java.util.HashMap;

public interface IAction {
	abstract public String run(HashMap<String, String> params);
}
