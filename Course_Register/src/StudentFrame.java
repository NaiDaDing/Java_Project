import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class StudentFrame extends JFrame {

	private final int FRAME_WIDTH = 360;
	private final int FRAME_HEIGHT = 180;
	private final int FIELD_WIDTH = 10;
	private JRadioButton addButton, deleteButton;
	private JLabel studentIDLabel, studentNameLabel;
	private JTextField studentIDField, studentNameField;
	private JButton submitButton, resetButton;
	
	public StudentFrame() {
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		createPanel();
	}
	
	public JPanel createRadioButton() {
		JPanel panel = new JPanel();
		ButtonGroup group = new ButtonGroup();
		createAddButton();
		createDeleteButton();
		group.add(addButton);
		group.add(deleteButton);
		addButton.setSelected(true);
		panel.add(addButton);
		panel.add(deleteButton);
		return panel;
	}
	
	public void createAddButton() {
		addButton = new JRadioButton("Add");
		class AddListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				enableFields();
			}
			
		}
		ActionListener addListener = new AddListener();
		addButton.addActionListener(addListener);
	}
	
	public void createDeleteButton() {
		deleteButton = new JRadioButton("Delete");
		class DelListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				disableFeilds();
			}
			
		}
		ActionListener delListener = new DelListener();
		deleteButton.addActionListener(delListener);
	}
	
	public void enableFields() {
		studentIDField.setEditable(true);
		studentNameField.setEditable(true);
	}
	
	public void disableFeilds() {
		studentIDField.setEditable(true);
		studentNameField.setEditable(false);
	}
	
	public void createTextField() {
		studentIDLabel = new JLabel("Student ID:");
		studentNameLabel = new JLabel("Student Name:");
		studentIDField = new JTextField(FIELD_WIDTH);
		studentNameField = new JTextField(FIELD_WIDTH);
	}
	
	public void createButton() {
		createSubmitBtn();
		createResetBtn();
	}
	
	public void createSubmitBtn() {
		submitButton = new JButton("Submit");
		class SubListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(addButton.isSelected()) {
						getRegisterFromRegisterFrame().addStudent(studentIDField.getText(), studentNameField.getText());
					}
					else {
						getRegisterFromRegisterFrame().removeStudent(studentIDField.getText());
					}
				}catch(SQLException ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage());
				}
			}
		}
		ActionListener subListener = new SubListener();
		submitButton.addActionListener(subListener);
	}
	
	public void createResetBtn() {
		resetButton = new JButton("Reset");
		class ReListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				studentIDField.setText("");
				studentNameField.setText("");
			}
		}
		ActionListener reListener = new ReListener();
		resetButton.addActionListener(reListener);
	}
	
	private Register getRegisterFromRegisterFrame() {
		for(Frame frame: JFrame.getFrames()) {
			if(frame.getTitle().equals("Course Register")) {
				RegisterFrame registerFrame = (RegisterFrame) frame;
				return registerFrame.getRegister();
			}
		}
		return null;
	}

	public void createPanel() {
		createRadioButton();
		createTextField();
		createButton();
		JPanel totalPanel = new JPanel();
		JPanel panel1 = createRadioButton();
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(2,2));
		panel2.add(studentIDLabel);
		panel2.add(studentIDField);
		panel2.add(studentNameLabel);
		panel2.add(studentNameField);
		add(panel2);
		JPanel panel3 = new JPanel();
		panel3.add(submitButton);
		panel3.add(resetButton);
		totalPanel.add(panel1);
		totalPanel.add(panel2);
		totalPanel.add(panel3);
		add(totalPanel);
	}
}
