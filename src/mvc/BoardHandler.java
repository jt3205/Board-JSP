package mvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.BoardVO;
import domain.UserVO;
import service.BoardService;

public class BoardHandler implements URIHandler {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// uri 파싱부분
		String command = req.getRequestURI();
		String cmds[] = null;
		if (command.indexOf(req.getContextPath()) == 0) {
			// 컨텍스트 패스만큼 잘라낸다.
			command = command.substring(req.getContextPath().length());
			cmds = command.split("/");
		}
		/*
		 * cmds의 0:공백 1:board 2: list 또는 write또는 mod
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
		case "view":
			view = viewBoard(req, res);
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
				req.setAttribute("msg", "로그인한 후에 올 수 있당께!");
				return "/WEB-INF/view/login.jsp";
			}
			String id = writer.getId();
			BoardVO vo = new BoardVO();
			vo.setTitle(title);
			vo.setContent(content);
			vo.setWriter(id);
			vo.setDate(new java.sql.Date(new java.util.Date().getTime()));
			
			BoardService.getInstance().write(vo);
			req.getSession().setAttribute("msg", "글 작성 완료");
			res.sendRedirect("/board/list");
			return null;
		}
		return "/WEB-INF/view/write.jsp";
	}

	private String modBoard(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "/WEB-INF/view/mod.jsp";
	}
	

	private String viewBoard(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String idParam = req.getParameter("id");
		int id = idParam == null ? 0 : Integer.parseInt(idParam);
		
		if(id > 0) {
			BoardVO data = BoardService.getInstance().read(id);
			if(data == null) {
				req.getSession().setAttribute("msg", "해당글은 존재하지 않습니다.");
				res.sendRedirect("/board/list");
				return null;
			}
			req.setAttribute("board", data);
		}
		return "/WEB-INF/view/view.jsp";
	}
}
