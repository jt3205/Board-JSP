package mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.BoardVO;
import domain.UserVO;
import service.BoardService;

public class BoardHandler implements URIHandler {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// uri �Ľ̺κ�
		String command = req.getRequestURI();
		String cmds[] = null;
		if (command.indexOf(req.getContextPath()) == 0) {
			// ���ؽ�Ʈ �н���ŭ �߶󳽴�.
			command = command.substring(req.getContextPath().length());
			cmds = command.split("/");
		}
		/*
		 * cmds�� 0:���� 1:board 2: list �Ǵ� write�Ǵ� mod
		 */

		String view = null;
		String menu = null;
		if (cmds.length < 3) {
			menu = "list";
		} else {
			menu = cmds[2];
		}
		switch (menu) {
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
		String startParam = req.getParameter("start");
		String cntParam = req.getParameter("cnt");
		int start, cnt;
		
		start = startParam == null ? 0 : Integer.parseInt(startParam);
		cnt = cntParam == null ? 5 : Integer.parseInt(cntParam);
		
		List<BoardVO> vo = BoardService.getInstance().getList(start, cnt);
		
		req.setAttribute("boardList", vo);
		
		return "/WEB-INF/view/list.jsp";
	}

	private String writeBoard(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("POST")) {
			req.setCharacterEncoding("UTF-8");
			
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			System.out.println(req.getSession());
			UserVO writer = (UserVO) req.getSession().getAttribute("login");
			
			if(writer == null) {
				req.setAttribute("msg", "�α����� �Ŀ� �� �� �ִ粲!");
				return "/WEB-INF/view/login.jsp";
			}
			String id = writer.getId();
			BoardVO vo = new BoardVO();
			vo.setTitle(title);
			vo.setContent(content);
			vo.setWriter(id);
			vo.setDate(new java.sql.Date(new java.util.Date().getTime()));
			
			BoardService.getInstance().write(vo);
			req.getSession().setAttribute("msg", "�� �ۼ� �Ϸ�");
			res.sendRedirect("/board/list");
			return null;
		}
		return "/WEB-INF/view/write.jsp";
	}

	private String modBoard(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "/WEB-INF/view/mod.jsp";
	}
}
