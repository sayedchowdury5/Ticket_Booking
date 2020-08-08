
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Font;

public class Register {

	private JFrame frame;
	private JTextField textField_email;             //Student Name TextField
	private JPasswordField passwordField;     		//Password TextField
	private JTextField textField_name;           	//Student ID TextField
	private JPasswordField passwordField_1;   		//Confirm Password TextField
	
	/*
	 * Default constructor. Used to call the function to initialize GUI
	 */
	public Register() {
		initRegisterGUI();
		frame.setVisible(true);
	}
	
	/*
	 * Function that is used to design the GUI
	 */
	public void initRegisterGUI() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 477, 327);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField_email = new JTextField();
		textField_email.setBounds(104, 119, 357, 20);
		frame.getContentPane().add(textField_email);
		textField_email.setColumns(10);
		
		JLabel lblUserId = new JLabel("Email:");
		lblUserId.setBounds(10, 122, 74, 14);
		frame.getContentPane().add(lblUserId);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 153, 74, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField_email.getText().equals("") || textField_name.getText().equals("") || passwordField.getText().equals("") || passwordField_1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, 
                            "Please fill in all the fields!", 
                            "Error", 
                            JOptionPane.WARNING_MESSAGE);
				}
				else {
					if (passwordField.getText().equals(passwordField_1.getText())) {
						if (registerUser(textField_email.getText(), passwordField.getText(), textField_name.getText())) {
							JOptionPane.showMessageDialog(null, 
				                    "Successfully registered your account!", 
				                    "Success", 
				                    JOptionPane.INFORMATION_MESSAGE);
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(null, 
				                    "User with that ID is already exists!", 
				                    "Error", 
				                    JOptionPane.WARNING_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, 
	                            "The password and confirm password are not the same!", 
	                            "Error", 
	                            JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnRegister.setBounds(10, 212, 451, 47);
		frame.getContentPane().add(btnRegister);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(104, 150, 357, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 91, 74, 14);
		frame.getContentPane().add(lblName);
		
		textField_name = new JTextField();
		textField_name.setBounds(104, 88, 357, 20);
		frame.getContentPane().add(textField_name);
		textField_name.setColumns(10);
		
		JLabel lblConfirmPassword = new JLabel("Confirm pass:");
		lblConfirmPassword.setBounds(10, 187, 84, 14);
		frame.getContentPane().add(lblConfirmPassword);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(104, 181, 357, 20);
		frame.getContentPane().add(passwordField_1);
		
		JLabel lblRegisterAccount = new JLabel("REGISTER ACCOUNT");
		lblRegisterAccount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRegisterAccount.setBounds(139, 26, 183, 32);
		frame.getContentPane().add(lblRegisterAccount);
	}
	
	/**
	 * Function to write the user's data inside the users database
	 * @param userEmail - User's email address
	 * @param userPass - User's password
	 * @param userFullName - User's full name
	 * @return true if the registration is success
	 */
	public boolean registerUser(String userEmail, String userPass, String userFullName) {
		FileOperation check = new FileOperation();
		if (!check.checkExists(userEmail, "users.txt")) {
			FileOperation register = new FileOperation();
			String details = userEmail + ";" + userPass + ";" + userFullName;
			if (register.writeInto(details, "users.txt"))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
