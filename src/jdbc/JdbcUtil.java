package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//p434
// DB작업관련 객체들의 close()기능제공, 트랜잭션의 rollback()제공
public class JdbcUtil {
	
	public static void close(ResultSet rs) {
		if(rs !=null) {
			try {rs.close();
				
			}catch(SQLException e) {
				
			}
			
		}
		
	}//close()끝
	
	public static void close(Statement stmt) {
		if(stmt!=null) {
			try {
				stmt.close();      
			}catch(SQLException e) {
				
			}
			
		}
		
	}//close()끝
	
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				
			}
			
		}
		
	}//close()끝
	
	public static void rollback(Connection conn) {
		if(conn != null) {
			try {
				conn.rollback();
			}catch(SQLException e) {
				
			}
			
		}
		
	}//rollback() 끝

}
