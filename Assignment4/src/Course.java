
public class Course {

	private String courseID;
	private String courseName;
	private int credits;
	public Course(String id,String name,int credits) {
		courseID = id;
		courseName = name;
		this.credits = credits;
	}
	public String getCourseID() {
		return courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public int getCredits() {
		return credits;
	}
	public String toString() {
		return String.format("[CourseID: %s, CourseName: %s, Credits: %d]\n",courseID,courseName,credits);
	}
}
