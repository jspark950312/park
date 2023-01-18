package mvc.controller;

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

import mvc.command.CommandController;
import mvc.command.NullController;


//요청 URI를 명령어로 사용하기위한 클래스

public class ControllerUsingURI extends HttpServlet {
    //필드
    // <커맨드, 핸들러인스턴스> 매핑 정보 저장
    private Map<String, CommandController> commandHandlerMap = 
    		new HashMap<>();

    //메서드
    //init()는 서블릿을 처음 메모리에 올릴때 실행되어, 서블릿을 초기화하며 처음에 한번만 실행. 
    public void init() throws ServletException {
		// web.xml문서 설정부분에서   /WEB-INF/commandHandlerURI.properties를 가져와
		// String타입  configFile변수에 저장
        String configFile = getInitParameter("configFile");
        Properties prop = new Properties();//Properties객체
        String configFilePath = getServletContext().getRealPath(configFile);
        
        //실행동작할 수 있는 파일로 만든다
        try (FileReader fis = new FileReader(configFilePath)) {
            prop.load(fis);
        } catch (IOException e) {
            throw new ServletException(e);
        }
        //key 목록가져온다. properties에 담겨있는 키 하나씩 꺼내어 String타입의 변수안에 넣는다.
        //꺼내온 키는 
        Iterator keyIter = prop.keySet().iterator();
        while (keyIter.hasNext()) {
            String command = (String) keyIter.next();
            String handlerClassName = prop.getProperty(command);
            try {
                //동작할 수 있는 파일에서 특정 파일을 찾는다.다시 동작시키기위해 객체(newInstance)로 만들어주고
            	//commandHandlerMap에 담아준다.
            	//결론은 properties에 있는 String으로 이루어진 key 와 value를 찾어서 꺼내고 동작할 수 있는 파일목록으로 만든다.
            	//이렇게 미리 만들어놓고 클라이언트가 요청할 때 동작
            	Class<?> handlerClass = Class.forName(handlerClassName);
                CommandController handlerInstance = 
                        (CommandController) handlerClass.newInstance();
                //맵에 값을 저장 Map참조변수명.put(key,value): Map에 key,value를 저장
                commandHandlerMap.put(command, handlerInstance);
            } catch (ClassNotFoundException | InstantiationException 
            		| IllegalAccessException e) {
                throw new ServletException(e);
            }
        }
    }

    //doGet()는 get방식으로 요청시 호출되는 메서드
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    //doPost()는 post방식으로 요청시 호출되는 메서드
    protected void doPost(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /*URI를 명령어로 사용하려먼 컨트롤러 서블릿의 process()에서 인식할 수 있어야 한다.*/
    private void process(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException {
		
		//요청URI에서 request.getContextPath()부분을 제거하여 요청URI만 사용하기
		String command = request.getRequestURI();// /컨텍스트패스/~~~
		if (command.indexOf(request.getContextPath()) == 0) {//요청URI에서 request.getContextPath()부분찾기
			command = command.substring(request.getContextPath().length());
		}
		
		//담당컨트롤러를 가져오기
		//위에서 선언했던 Map<String, CommandHandler>, commandHanlerMap에서
		//키값에 해당하는 command를 이용하여
		//값에 해당하는 CommandHandler를 Map에서 꺼낸다.
        CommandController handler = commandHandlerMap.get(command);
        if (handler == null) {
            handler = new NullController();
        }
        String viewPage = null;
        try {
        	//(가져온) 담당컨트롤러의 process()메서드를 호출해라
        	//(여기에서는)모든 컨트롤러는 반드시 CommandHandler인터페이스를 구현하고 있다.
        	//모든 컨트롤러의 process() 리턴하는 String타입의 view(jsp문서)를
        	//String타입 viewPage에 저장
            viewPage = handler.process(request, response);
        } catch (Throwable e) {
            throw new ServletException(e);
        }
        if (viewPage != null) {//viewPage가 존재하면
        	//해당  viewPage로 페이지를 이동해라-> 브라우저에 출력해라
        	RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
	        dispatcher.forward(request, response);
        }
    }
}

