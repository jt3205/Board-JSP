package mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Board;
import domain.Pager;
import domain.User;
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
		 * cmds의 0:공백 1:board 2: list,write 또는 del 등등
		 */

		String view = null;
		String menu = null;
		if (cmds.length < 3) {
			menu = "list"; // 기본 값은 list으로
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
			//과제view
		case "view":
			view = viewBoard(req,res);
			break;
			//과제 view 종료
		case "del":
			view = delBoard(req, res);
			break;
		}

		return view;
		
	}
	
	//과제 del 함수
	private String delBoard(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String idParam = req.getParameter("id");
		int id = idParam == null ? 0 : Integer.parseInt(idParam);
		
		if(id > 0){
			Board data = BoardService.getInstance().read(id);
			if(data == null){
				req.getSession().setAttribute("msg", "해당 글은 존재하지 않습니다");
				res.sendRedirect("/board/list");
				return null;
			}else {
				//여기에 삭제 관련 루틴 작성.
				User writer = (User)req.getSession().getAttribute("login");
				
				if(writer == null){
					req.setAttribute("msg", "로그인 후 삭제할 수 있습니다.");
					return "/WEB-INF/view/login.jsp"; //오류메시지를 실어서 로그인 창으로 보냄.
				}
				
				if(!writer.getId().equals(data.getWriter())){
					req.getSession().setAttribute("msg", "글을 삭제할 권한이 없습니다.");
					res.sendRedirect("/board/list");
					return null;
				}
				
				if(BoardService.getInstance().delete(id) < 0){
					req.getSession().setAttribute("msg", "글 삭제시 오류가 발생했습니다.");
					res.sendRedirect("/board/list");
					return null;
				}
				
				req.getSession().setAttribute("msg", "성공적으로 삭제되었습니다.");
				res.sendRedirect("/board/list");
				return null;
			}
		}else{
			req.getSession().setAttribute("msg", "해당 글은 존재하지 않습니다");
			res.sendRedirect("/board/list");
			return null;
		}
		
	}
	
	
	
	//과제 view 관련 함수
	private String viewBoard(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String idParam = req.getParameter("id");
		int id = idParam == null ? 0 : Integer.parseInt(idParam);
		
		if(id > 0){
			Board data = BoardService.getInstance().read(id);
			if(data == null){
				req.getSession().setAttribute("msg", "해당 글은 존재하지 않습니다");
				res.sendRedirect("/board/list");
				return null;
			}else {
				req.setAttribute("board", data); //데이터가 존재하면 데이터를 실어서 보냄.
				return "/WEB-INF/view/view.jsp";
			}
		}else{
			req.getSession().setAttribute("msg", "해당 글은 존재하지 않습니다");
			res.sendRedirect("/board/list");
			return null;
		}
	}
	
	private String listBoard(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//페이징 과제 처리를 위해 page를 받도록 변경
		String pageParam = req.getParameter("page");
		int page;
		page = pageParam == null ? 1 : Integer.parseInt(pageParam);
		
		//여기서부터 페이지네이션 관련 코드(과제)
		Pager pager = new Pager();
		int totalCnt = BoardService.getInstance().getTotalCnt();
		pager.setTotalCnt(totalCnt); //전체 페이지수 설정
		pager.setNowPage(page);
		pager.calc();
		
		//pager 객체의 값에 따라 start와 end지정
		List<Board> list = BoardService.getInstance().getList((page -1) * pager.getPerPageNum() , pager.getPerPageNum());
		
		req.setAttribute("boardList", list);
		req.setAttribute("pager", pager); //페이저 객체 실어서 보내줌.
		req.setAttribute("title", "글 목록 보기");
		//과제 종료
		
		return "/WEB-INF/view/index.jsp";
	}
	
	private String writeBoard(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("POST")){
			req.setCharacterEncoding("UTF-8");
			
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			User writer = (User)req.getSession().getAttribute("login");
			
			if(writer == null){
				req.setAttribute("msg", "로그인 후 작성할 수 있습니다.");
				return "/WEB-INF/view/login.jsp"; //오류메시지를 실어서 로그인 창으로 보냄.
			}
			
			String id = writer.getId();
			
			Board board = new Board();
			board.setTitle(title);
			board.setContent(content);
			board.setDate(new java.sql.Date(new java.util.Date().getTime()));
			board.setWriter(id);
			
			
			BoardService.getInstance().write(board);
			res.sendRedirect("/board/list"); //리스트로 리다이렉트 시켜주고.
			
			return null;
		}
		return "/WEB-INF/view/write.jsp";
	}

}
