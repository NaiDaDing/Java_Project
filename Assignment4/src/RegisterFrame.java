import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RegisterFrame extends JFrame {

	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 450;
	private final int FIELD_WIDTH = 10;
	private final int AREA_WIDTH = 30;
	private final int AREA_HEIGHT = 10;
	private Register register;
	private JPanel panel;
	private JLabel studentIDLabel, courseIDLabel, gradeLabel;
	private JTextField studentIDField, courseIDField, gradeField;
	private JButton studentInfoButton, courseInfoButton, enrollButton, dropButton, updateButton;
	private JScrollPane scrollPane;
	private JTextArea outputTextArea;
	private JMenuBar menuBar;
	
	public RegisterFrame() {
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		setTitle("Course Register");
		register = new Register();
		createMenuBar();
		createStudentIDComp();
		createCourseIDComp();
		createGradeComp();
		createEnrollBtn();
		createDropBtn();
		createOutputArea();
		createPanel();
	}
	
	public Register getRegister() {
		return register;
	}
	
	public void createStudentIDComp() {
		studentIDLabel = new JLabel("Student ID:");
		studentIDField = new JTextField(FIELD_WIDTH);
		studentInfoButton = new JButton("Student INFO");
		class StudentActionListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(studentIDField.getText().length()!=9) {
						JOptionPane.showMessageDialog(null,"Student ID length should be 9. ");
					}
					else {
						String info = register.findStudent(studentIDField.getText()).info();
						if(info==null)
							outputTextArea.append("\nFalse");
						else
							outputTextArea.append("\n"+info);
					}
				}catch(SQLException ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage());
				}
				catch(NullPointerException ex) {
					JOptionPane.showMessageDialog(null,"No such student!");
				}
			}
		}
		ActionListener sal = new StudentActionListener();
		studentInfoButton.addActionListener(sal);
	}
	
	public void createCourseIDComp() {
		courseIDLabel = new JLabel("Course ID:");
		courseIDField = new JTextField(FIELD_WIDTH);
		courseInfoButton = new JButton("Course INFO");
		class CourseActionListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(courseIDField.getText().length()!=7) {
						JOptionPane.showMessageDialog(null,"Course ID length should be 7. ");
					}
					else {
						String info = register.findCourse(courseIDField.getText()).toString();
						if(info==null)
							outputTextArea.append("\nFalse");
						else
							outputTextArea.append("\n"+info);
					}
				}catch(SQLException ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage());
				}
				catch(NullPointerException ex) {
					JOptionPane.showMessageDialog(null,"No such course!");
				}
			}
			
		}
		ActionListener cal = new CourseActionListener();
		courseInfoButton.addActionListener(cal);
	}
	
	public void createGradeComp() {
		gradeLabel = new JLabel("Grade:");
		gradeField = new JTextField(FIELD_WIDTH);
		updateButton = new JButton("Update grade");
		class CourseActionListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int grade = Integer.parseInt(gradeField.getText());
					String student = studentIDField.getText();
					String course = courseIDField.getText();
					try {
						if(register.findStudent(student)!=null && register.findCourse(course)!=null) {
							outputTextArea.append(String.format("\nUpdate %s\'s %s successfully!",student,course));
						}else {
							outputTextArea.append("\nFail to update!");
						}
					}catch(SQLException ex) {
						JOptionPane.showMessageDialog(null,ex.getMessage());
					}
				}catch(InputMismatchException ex) {
					JOptionPane.showMessageDialog(null,"Grade must be integer. ");
				}
			}
			
		}
		ActionListener cal = new CourseActionListener();
		updateButton.addActionListener(cal);
	}
	
	public void createEnrollBtn() {
		enrollButton = new JButton("Enroll");
		class enrollActionListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String student = studentIDField.getText();
				String course = courseIDField.getText();
				try {
					if(student.length()!=9 || course.length()!=7) {
						JOptionPane.showMessageDialog(null,"Course\r\n" + 
								"/Student ID length should be 7\\9.");
					}
					else {
						boolean success = register.enrollCourse(student, course);
						if(success) {
							outputTextArea.append(String.format("\n%s enrolled in %s",student,course));
						}
						else {
							outputTextArea.append("\nFalse");
						}
					}
				}catch(SQLException ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage());
				}
			}
		}
		ActionListener eal = new enrollActionListener();
		enrollButton.addActionListener(eal);
	}
	
	public void createDropBtn() {
		dropButton = new JButton("Drop");
		class dropActionListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String student = studentIDField.getText();
				String course = courseIDField.getText();
				try {
					if(student.length()!=9 || course.length()!=7) {
						JOptionPane.showMessageDialog(null,"Course\r\n" + 
								"/Student ID length should be 7\\9.");
					}
					else {
						boolean success= register.dropCourse(student,course);
						if(success) {
							outputTextArea.append(String.format("\n%s dropped out %s",student,course));
						}
						else {
							outputTextArea.append("\nFalse");
						}
					}
				}catch(SQLException ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage());
				}
			}
		}
		ActionListener dal = new dropActionListener();
		dropButton.addActionListener(dal);
	}
	
	public void createOutputArea() {
		outputTextArea = new JTextArea(AREA_HEIGHT,AREA_WIDTH);
		scrollPane = new JScrollPane(outputTextArea);
		outputTextArea.setLineWrap(true);
	}
	
	public void createMenuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(createStudent());
		fileMenu.add(createCourse());
		fileMenu.add(createExit());
		menuBar.add(fileMenu);
	}
	
	public JMenuItem createStudent() {
		JMenuItem studentItem = new JMenuItem("Student");
		class SListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				StudentFrame studentFrame = new StudentFrame();
				studentFrame.setTitle("Manage Students");
				studentFrame.setVisible(true);
				studentFrame.setLocation(350,0);
			}
		}
		ActionListener sListener = new SListener();
		studentItem.addActionListener(sListener);
		return studentItem;
	}
	
	public JMenuItem createCourse() {
		JMenuItem courseItem = new JMenuItem("Course");
		class CListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				CourseFrame courseFrame = new CourseFrame();
				courseFrame.setTitle("Manage Courses");
				courseFrame.setVisible(true);
				courseFrame.setLocation(350,0);
			}
		}
		ActionListener cListener = new CListener();
		courseItem.addActionListener(cListener);
		return courseItem;
	}
	
	public JMenuItem createExit() {
		JMenuItem exitItem = new JMenuItem("Exit");
		class EListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		ActionListener eListener = new EListener();
		exitItem.addActionListener(eListener);
		return exitItem;
	}
	
	public void createPanel() {
		JPanel totalPanel = new JPanel();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 3));
		panel.add(studentIDLabel);
		panel.add(studentIDField);
		panel.add(studentInfoButton);
		panel.add(courseIDLabel);
		panel.add(courseIDField);
		panel.add(courseInfoButton);
		panel.add(gradeLabel);
		panel.add(gradeField);
		panel.add(updateButton);
		totalPanel.add(panel);
		totalPanel.add(enrollButton);
		totalPanel.add(dropButton);
		totalPanel.add(scrollPane);
		add(totalPanel);
	}
}
