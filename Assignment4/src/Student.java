import java.sql.*;

public class Student {

	private String studentID;
	private String studentName;
	private int currentCredits;
	private int maxCredits;
	private String url = "jdbc:mysql://localhost:3306/assignment4?user=root&password=122618071&&serverTimezone=UTC";
	
	public Student(String studentID, String name, int credit, int max) {
		this.studentID = studentID;
		this.studentName = name;
		this.currentCredits = credit;
		this.maxCredits = max;
	}

	public String getStudentID() {
		return studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public int getCurrentCredits() {
		return currentCredits;
	}

	public int getMaxCredits() {
		return maxCredits;
	}
	
	public void setCurrentCredits(int currentCredits) throws SQLException{
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection(url);
			String query = "UPDATE student SET Student_current_credits = ? WHERE Student_id = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,currentCredits);
			pst.setString(2, getStudentID());
			pst.executeUpdate();
			this.currentCredits = currentCredits;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			pst.close();
			conn.close();
		}
	}
	
	
	public String info() throws SQLException{
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		String resultString = "";
		String query1 = "SELECT * from student where Student_id = ?" ;
		String query2 = "SELECT Course_id FROM enroll WHERE Student_id=?";
		try {
			conn = DriverManager.getConnection(url);
			stat = conn.prepareStatement(query1);
			stat.setString(1, getStudentID());
			result = stat.executeQuery();
			while(result.next()) {
				resultString += String.format("Student ID: %s\nStuednt Name: %s\nCredits: %d/%d\nEnrolled courses:\n",
						result.getString("Student_id"),result.getString("Student_name"),result.getInt("Student_current_credits"),
						result.getInt("Student_max_credits"));
			}
			stat = conn.prepareStatement(query2);
			stat.setString(1, getStudentID());
			result = stat.executeQuery();
			while(result.next()) {
				resultString += String.format("%s\n",result.getString("Course_id"));
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			conn.close();
			result.close();
			stat.close();
			return resultString;
		}
		
	}
}
