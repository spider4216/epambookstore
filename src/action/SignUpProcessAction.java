package action;

import java.util.HashMap;

public class SignUpProcessAction implements IAction {

	public Object run(HashMap<String, String> params) {
		System.out.println(params);
		return null;
	}

}
