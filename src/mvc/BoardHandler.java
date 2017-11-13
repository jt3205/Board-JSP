package mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardHandler implements URIHandler {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//uri 파싱부분 
		String command = req.getRequestURI();
		String cmds[] = null;
		if(command.indexOf(req.getContextPath()) == 0){
			//컨텍스트 패스만큼 잘라낸다.
			command = command.substring(req.getContextPath().length());
			cmds = command.split("/");
		}
		/*
		 * cmds의 0:공백 1:board 2: list 또는 write또는 mod 
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
