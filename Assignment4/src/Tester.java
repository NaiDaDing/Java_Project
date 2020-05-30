import java.sql.SQLException;


public class Tester {

	public static void main(String[] args) {
		try {
			Register register = new Register();
			register.addStudent("108356001", "unknow");
			register.addCourse("it-777", "IT", 3);
			Student student = register.findStudent("108356001");
			Course course = register.findCourse("it-777");
			register.enrollCourse("108356001", "it-777");
			System.out.println(student.info());
			register.updateGrade("108356001", "it-777", 99);
			register.dropCourse("108356001", "it-777");
			System.out.println(student.info());
			register.removeStudent("108356001");
			register.removeCourse("it-777");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
  }
}