package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//요청처리관련 작업 중 null발생처리를 위한 클래스
//(여기에서는)모든 컨트롤러는 반드시 CommandHandler인터페이스를 구현해야 한다.
public class NullController implements CommandController {

	@Override
	public String process(HttpServletRequest request, 
			HttpServletResponse response)
	throws Exception {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

}

/*참고
특정 조건에서 HTTP 에러코드로 응답해야 하는 경우가 있다. 
예를 들어 사용자가 파라미터를 임의로 입력했을때 
없는 페이지인 것처럼 보인다던지 하는 경우인데, 
이 때는 HttpServletResponse의 메서드를 이용해
응답코드의 상태값을 임의로 변경하는 방식을 쓴다.

sendError
문법>void sendError( int code [, String message ] )
예문>response.sendError(404, "잘못된 접근입니다.");

setStatus
문법>void setStatus( int code [, String message ] )
예문>response.setStatus(404);
*/