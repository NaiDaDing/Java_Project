import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;

public class TestDB {
	public static void main(String[] args) throws SQLException {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
		    System.out.println("找不到驅動程式類別");
		}
		Connection conn = null;
		try {
			String url = "jdbc:mysql://localhost:3306/assignment4?user=root&password=122618071&&serverTimezone=UTC";
			conn = DriverManager.getConnection(url);
			if(!conn.isClosed())
		        System.out.println("資料庫連線成功");
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}finally {
			conn.close();
		}
		
	}
}
