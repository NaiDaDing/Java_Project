import java.sql.*;

public class Register {

	private String url = "jdbc:mysql://localhost:3306/assignment4?user=root&password=122618071&&serverTimezone=UTC";
	
	public Register() {}
	
	public void addStudent(String id, String name) throws SQLException{
		Connection conn = null;
		PreparedStatement stat = null;
		String query = "insert into student values(?,?,null,25)";
		try {
		    conn = DriverManager.getConnection(url);
		    stat = conn.prepareStatement(query);
		    stat.setString(1, id);
		    stat.setString(2, name);
		    stat.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			stat.close();
			conn.close();
		}
	}
	
	public void addCourse(String id, String name, int credits) throws SQLException{
		Connection conn = null;
		PreparedStatement stat = null;
		String query = "insert into course values(?,?,?)";
		try {
		    conn = DriverManager.getConnection(url);
		    stat = conn.prepareStatement(query);
		    stat.setString(1, id);
		    stat.setString(2, name);
		    stat.setInt(3, credits);
		    stat.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			stat.close();
			conn.close();
		}
	}
	
	public Student findStudent(String studentID) throws SQLException{
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		String query = "SELECT * from student where student_id = ?";
		String id = "",name = "";
		int credit = 0,max = 0;
		String resultString = "";
		try {
			conn = DriverManager.getConnection(url);
			stat = conn.prepareStatement(query);
			stat.setString(1,studentID);
			result = stat.executeQuery();
			while(result.next()) {
				id = result.getString(1);
				name = result.getString(2);
				credit = result.getInt(3);
				max = result.getInt(4);
				resultString += id;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			stat.close();
			conn.close();
			if(resultString!="")
				return new Student(id,name,credit,max);
			else {
				return null;
			}
		}
	}
	
	public Course findCourse(String courseID) throws SQLException{
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		String query = "SELECT * from course where course_id = ?";
		String id = "",name = "";
		int credit = 0;
		String resultString = "";
		try {
			conn = DriverManager.getConnection(url);
			stat = conn.prepareStatement(query);
			stat.setString(1,courseID);
			result = stat.executeQuery();
			while(result.next()) {
				id = result.getString(1);
				name = result.getString(2);
				credit = result.getInt(3);
				resultString += id;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			stat.close();
			conn.close();
			if(resultString!="")
				return new Course(id,name,credit);
			else 
				return null;
		}
	}
	
	public boolean enrollCourse(String studentID, String courseID) throws SQLException{
		boolean flag = false;
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		String checkQuery = "select course_id from enroll where student_id = ? and course_id = ?";
		String update = "insert into enroll values(?,?,0)";
		String test = "";
		Student student = findStudent(studentID);
		Course course = findCourse(courseID);
		try {
			conn = DriverManager.getConnection(url);
			stat = conn.prepareStatement(checkQuery);
			stat.setString(1, student.getStudentID());
			stat.setString(2, course.getCourseID());
			result = stat.executeQuery();
			if(student!=null && course!=null) {
				while(result.next()) {
					test += result.getString(1);
				}
				if(student.getCurrentCredits()+course.getCredits()<=student.getMaxCredits() && !test.equals(courseID)) {
					student.setCurrentCredits(student.getCurrentCredits()+course.getCredits());
					stat = conn.prepareStatement(update);
					stat.setString(1, studentID);
					stat.setString(2, courseID);
					stat.executeUpdate();
					flag = true;
				}
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			conn.close();
			stat.close();
			result.close();
			return flag;
		}
	}
	
	public boolean dropCourse(String studentID, String courseID) throws SQLException{
		boolean flag = false;
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		String checkQuery = "select course_id from enroll where student_id = ? and course_id = ?";
		String drop = "delete from enroll where course_id = ?";
		String test = "";
		Student student = findStudent(studentID);
		Course course = findCourse(courseID);
		try {
			conn = DriverManager.getConnection(url);
			stat = conn.prepareStatement(checkQuery);
			stat.setString(1, student.getStudentID());
			stat.setString(2, course.getCourseID());
			result = stat.executeQuery();
			if(student!=null && course!=null) {
				while(result.next()) {
					test += result.getString(1);
				}
				if(test.equals(courseID)) {
					student.setCurrentCredits(student.getCurrentCredits()-course.getCredits());
					stat = conn.prepareStatement(drop);
					stat.setString(1, courseID);
					stat.executeUpdate();
					flag = true;
				}
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			conn.close();
			stat.close();
			result.close();
			return flag;
		}
	}
	
	public void removeStudent(String studentID) throws SQLException{
		Connection conn = null;
		PreparedStatement stat = null;
		String query = "delete from student where Student_id=?";
		try {
		    conn = DriverManager.getConnection(url);
		    stat = conn.prepareStatement(query);
		    stat.setString(1, studentID);
		    stat.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			stat.close();
			conn.close();
		}
	}
	
	public void removeCourse(String courseID) throws SQLException{
		Connection conn = null;
		PreparedStatement stat = null;
		String query = "delete from course where Course_id=?";
		try {
		    conn = DriverManager.getConnection(url);
		    stat = conn.prepareStatement(query);
		    stat.setString(1, courseID);
		    stat.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			stat.close();
			conn.close();
		}
	}
	
	public boolean updateGrade(String studentID, String courseID, int grade) throws SQLException{
		Connection conn = null;
		PreparedStatement stat = null;
		boolean flag = true;
		String query = "update enroll set Grade = ? where Course_id = ? and Student_id = ?";
		try {
		    conn = DriverManager.getConnection(url);
		    stat = conn.prepareStatement(query);
		    stat.setInt(1, grade);
		    stat.setString(2, courseID);
		    stat.setString(3, studentID);
		    stat.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			flag = false;
		}finally {
			stat.close();
			conn.close();
			return flag;
		}
	}
}
