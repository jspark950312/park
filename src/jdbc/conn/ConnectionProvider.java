package jdbc.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//p586
//web.xml문서에서 설정한 poolName=board로 지정한 board를
//풀 이름으로 사용하여 Conncetion을 제공하는 클래스이다.
//Connection을 필요로하는 외부에서 Conncetion 참조변수=ConnectionProvider.getConnection(); 
public class ConnectionProvider {
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:apache:commons:dbcp:erpdb");
	}//dpcp:poolName 으로 끝남
	
}
