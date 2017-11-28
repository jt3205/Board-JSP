package mvc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class URIRouter extends HttpServlet{
	private Map<String, URIHandler> uriHandlerMap = new HashMap();
	
	public void init() throws ServletException {
		//초기 설정 파일의 위치를 configFile변수에 담는다.
		String configFile = getInitParameter("configFile");
		Properties prop = new Properties();
		String configFilePath = getServletContext().getRealPath(configFile);
		//설정 파일을 파일리더기를 통해 읽어주고 Properties 객체에 담아준다.
		//담겨진 프로퍼티는 hello=mvc.HelloHandler을 읽었다면 key:hello, property:mvc.HelloHandler로
		//지정된다.
		try{
			FileReader fs = new FileReader(configFilePath);
			prop.load(fs);
		} catch (IOException e) {
			throw new ServletException(e);
		}
		
		//프로퍼티 객체에 읽어들여진 값들을 이터레이터로 반복하면서 uriHandlerMap에 넣어준다.
		Iterator keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()){
			String command = (String) keyIter.next();
			String handlerClassName = prop.getProperty(command);
			//예를 들어 위의 두줄은 환경설정 파일에서 처음 일은 키가. hello 라면
			//hello가 commad로 들어가고 getProperty매서드에 의해  
			//handlerClassName에는 mvc.HelloHandler 이 들어간다.
			try{
				//가져온 클래스를 로딩해주고.
				Class<?> handlerClass = Class.forName(handlerClassName);
				//해당 클래스의 인스턴스를 받아온다.
				URIHandler handlerInstance = (URIHandler) handlerClass.newInstance();
				//그리고 만들어진 인스턴스와 커맨드를 맵에다가 집어넣는다.
				uriHandlerMap.put(command, handlerInstance);
			}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){
				throw new ServletException(e);
			}
		}
		/*
		 * while문이 종료된 후에는  uriHandlerMap에는 uri값과 그에 매칭되는 클래스의 인스턴스가 들어가 있다.
		 */
	}
	
	private void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//cmd 파라메터 값을 받아서 command에 넣어준다.
		
		//요청한 URI를 알아내고
		String command = req.getRequestURI();
		if(command.indexOf(req.getContextPath()) == 0){
			//컨텍스트 패스만큼 잘라낸다.
			command = command.substring(req.getContextPath().length());
			
			int slashIndex = command.indexOf('/', 1);
			if(slashIndex >= 0){
				command = command.substring(0, slashIndex);
			}

		}

		//해당 cmd 값이 맵에 존재하는 지 검사해서
		URIHandler handler = uriHandlerMap.get(command);
		if(handler == null){
			//없다면 널핸들러로 연결해주고.
			handler = new NullHandler();
		}
		//있다면 해당 핸들러의 process를 실행후 나오는 viewPage를 보도록 dispatch해준다.
		String viewPage = null;
		try {
			viewPage = handler.process(req,  res);
		} catch (Exception e){
			throw new ServletException(e);
		}
		
		//문제 없이 viewPage가 넘어왔다면 해당 뷰페이지로 디스패치
		if(viewPage != null){
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req,res);
		}
	}
	
	//get이든 post든 전부 process로 넘긴다.
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}
}

