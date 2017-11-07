package mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface URIHandler {
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
