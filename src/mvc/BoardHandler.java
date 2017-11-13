package mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardHandler implements URIHandler {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//uri �Ľ̺κ� 
		String command = req.getRequestURI();
		String cmds[] = null;
		if(command.indexOf(req.getContextPath()) == 0){
			//���ؽ�Ʈ �н���ŭ �߶󳽴�.
			command = command.substring(req.getContextPath().length());
			cmds = command.split("/");
		}
		/*
		 * cmds�� 0:���� 1:board 2: list �Ǵ� write�Ǵ� mod 
		 */

		String view = null;
		String menu = null;
		if(cmds.length < 3){
			menu = "list";
		}else {
			menu = cmds[2];
		}
		switch(menu){
			case "list":
				view = listBoard(req, res);
				break;
			case "write":
				view = writeBoard(req, res);
				break;
			case "mod":
				view = modBoard(req, res);
				break;
		}
		return view;
	}
	
	private String listBoard(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "/WEB-INF/view/list.jsp";
	}
	
	private String writeBoard(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "/WEB-INF/view/write.jsp";
	}

	private String modBoard(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "/WEB-INF/view/mod.jsp";
	}
}
