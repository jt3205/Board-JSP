package mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.UserService;

public class UserHandler implements URIHandler {
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
		 * cmds의 0:공백 1:user 2: join 또는 login 등등
		 */

		String view = null;
		String menu = null;
		if (cmds.length < 3) {
			menu = "login"; // 기본 값은 login으로
		} else {
			menu = cmds[2];
		}
		switch (menu) {
		case "join":
			view = joinUser(req, res);
			break;
		case "login":
			view = loginUser(req, res);
			break;
		}
		return view;
	}

	private String joinUser(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("POST")) { // 회원 가입 요청데이터
			req.setCharacterEncoding("UTF-8" );
			String id = req.getParameter("uid");
			String name = req.getParameter("uname");
			String pass1 = req.getParameter("pass1");
			String pass2 = req.getParameter("pass2");
			
			//여기에 비밀번호와 확인이 틀릴시에 작업할 내용이 들어가야 한다.(과제)
			
			if(id == null || id.isEmpty() || 
					name == null || name.isEmpty() || 
					pass1 == null || pass1.isEmpty() || 
					pass2 == null || pass2.isEmpty()){
				req.setAttribute("msg", "입력칸을 채워주세요");
				return "/WEB-INF/view/join.jsp"; 
			}
			
			if(!pass1.equals(pass2)) {
				req.setAttribute("msg", "비밀번호와 비밀번호 확인이 다릅니다.");
				return "/WEB-INF/view/join.jsp";
			}			
			//과제 끝.
			
			User user = new User();
			user.setId(id);
			user.setName(name);
			user.setPassword(pass1);
			System.out.println(user.getPassword());
			int result = UserService.getInstance().join(user);
			
			//여기서 result에 따라 다른 작업이 이루어지도록 해야한다.
			if(result < 0 ) { //오류 발생
				req.setAttribute("msg", "작업중 오류가 발생했습니다");
				return "/WEB-INF/view/join.jsp"; //오류메시지를 실어서 원래대로 돌려보냄
			} else if(result == 0){
				req.setAttribute("msg", "중복된 id가 있습니다");
				return "/WEB-INF/view/join.jsp"; //오류메시지를 실어서 원래대로 돌려보냄
			} 
			
			//여기까지 왔다면 정상적으로 가이되었으니 리다이렉트.
			res.sendRedirect("/");
			return null;
		} else { // 회원 가입 페이지 요청
			return "/WEB-INF/view/join.jsp";
		}
	}

	private String loginUser(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("POST")) { // 회원 가입 요청데이터
			req.setCharacterEncoding("UTF-8");
			//id와 비밀번호 받고
			String id = req.getParameter("uid");
			String pass = req.getParameter("pass1");
			
			User user = UserService.getInstance().getUser(id);
			if(user == null){ //해당 유저가 없거나 오류
				req.setAttribute("msg", "해당 유저는 존재하지 않습니다");
				return "/WEB-INF/view/login.jsp"; //오류메시지를 실어서 원래대로 돌려보냄
			}else if(!user.getPassword().equals(pass)){
				req.setAttribute("msg", "비밀번호가 올바르지 않습니다");
				return "/WEB-INF/view/login.jsp"; //오류메시지를 실어서 원래대로 돌려보냄
			}
			
			req.getSession().setAttribute("login", user); //로그인 처리
			
			res.sendRedirect("/");
			return null;
		} else { // 회원 가입 페이지 요청
			return "/WEB-INF/view/login.jsp";
		}
	}
}
