package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//p531
//이 인터페이스는 모든 컨트롤러클래스(교재에서는~~Handler)에서
//무조건 구현하게 함으로써 모든 컨트롤러클래스에서 process()를 반드시 
//오버라이딩하도록 강제화한다.
public interface CommandController {
		
	public abstract String process(HttpServletRequest request,HttpServletResponse response)
	throws Exception;

}
// abstarct는 생략가능 리턴유형은 String 메서드명은 process


/* 인터페이스란?
 * -상수필드선언
 * :인터페이스는 상수 필드만 선언가능
 * -인터페이스에 선언된 필드는 모두 public static final: 자동적으로 컴파일
 * -상수명은 대문자로
 * 
 * -선언과 동시에 초기값 설정
 * static{} 블록작성불가 -static {} 으로 초기화불가
 * 
 * -추상메서드
 * [public abstract] 리턴유형 메서드명(매개변수);  선언까지만 하고 끝 {}를 쓸수없다. 
 * 구현은 구현클래스 구현객체가 대신한다.
 * 
 * */
 