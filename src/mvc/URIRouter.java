package mvc;

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

public class URIRouter extends HttpServlet {
	private Map<String, URIHandler> uriHandlerMap = new HashMap();

	@Override
	public void init() throws ServletException {
		String configFile = getInitParameter("configFile");
		Properties prop = new Properties();
		String configFilePath = getServletContext().getRealPath(configFile);

		try {
			FileReader fs = new FileReader(configFilePath);
			prop.load(fs);
		} catch (IOException e) {
			throw new ServletException(e);
		}

		Iterator keyIter = prop.keySet().iterator();
		while (keyIter.hasNext()) {
			String command = (String) keyIter.next();
			String handlerClassName = prop.getProperty(command);

			try {
				Class<?> handlerClass = Class.forName(handlerClassName);
				URIHandler handlerInstance = (URIHandler) handlerClass.newInstance();
				uriHandlerMap.put(command, handlerInstance);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
		
		System.out.println(uriHandlerMap.toString());
	}

	private void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String command = req.getRequestURI();
		if (command.indexOf(req.getContextPath()) == 0) {
			// 컨텍스트 패스만큼 잘라낸다.
			command = command.substring(req.getContextPath().length());
			int slashIndex = command.indexOf('/', 1);
			if (slashIndex >= 0) {
				command = command.substring(0, slashIndex);
			}
		}
		// 만약 http://localhost:8080/TestJSP/main/a 를 요청했다면
		// RequestURI 는 /TestJSP/main/a 이고
		// 여기서 contextPath를 잘라내면 /main/a가 남게 된다.
		URIHandler handler = uriHandlerMap.get(command);
		if (handler == null) {
			// 없다면 널핸들러로 연결해주고.
			handler = new NullHandler();
		}
		// 있다면 해당 핸들러의 process를 실행후 나오는 viewPage를 보도록 dispatch해준다.
		String viewPage = null;
		try {
			viewPage = handler.process(req, res);
		} catch (Exception e) {
			throw new ServletException(e);
		}

		// 문제 없이 viewPage가 넘어왔다면 해당 뷰페이지로 디스패치
		if (viewPage != null) {
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}
}
