
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;

public class Login {

	private JFrame frame;
	private JTextField textField_email;
	private JPasswordField passwordField;

	/*
	 * Default Constructor. Used to run the function of initializing GUI
	 */
	public Login() {
		initLoginGUI();
	}

	/*
	 * The main function of the whole class. This is where the program will starts.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Function to design and initialize GUI
	 */
	public void initLoginGUI() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 477, 327);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField_email = new JTextField();
		textField_email.setBounds(200, 123, 218, 20);
		frame.getContentPane().add(textField_email);
		textField_email.setColumns(10);

		JLabel lblUserId = new JLabel("Email:");
		lblUserId.setBounds(78, 126, 74, 14);
		frame.getContentPane().add(lblUserId);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(78, 157, 74, 14);
		frame.getContentPane().add(lblPassword);

		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(10, 206, 223, 41);
		frame.getContentPane().add(btnRegister);

		passwordField = new JPasswordField();
		passwordField.setBounds(200, 154, 218, 20);
		frame.getContentPane().add(passwordField);

		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setBounds(243, 206, 218, 41);
		frame.getContentPane().add(btnLogIn);

		JLabel lblWelcomeToKuching = new JLabel("WELCOME TO KUCHING");
		lblWelcomeToKuching.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblWelcomeToKuching.setBounds(126, 11, 240, 50);
		frame.getContentPane().add(lblWelcomeToKuching);

		JLabel lblRailwayBookingSystem = new JLabel("RAILWAY BOOKING SYSTEM");
		lblRailwayBookingSystem.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRailwayBookingSystem.setBounds(110, 54, 294, 50);
		frame.getContentPane().add(lblRailwayBookingSystem);

		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileOperation userLogin = new FileOperation();
				if (userLogin.checkCredential(textField_email.getText(), passwordField.getText())) {
					JOptionPane.showMessageDialog(null, 
							"Successfully Logged In!", 
							"Success", 
							JOptionPane.INFORMATION_MESSAGE);
					if (textField_email.getText().matches("admin") && passwordField.getText().matches("admin"))
						new Main(textField_email.getText(), 0);
					else
						new Main(textField_email.getText(), 1);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, 
							"Login failed!",
							"Error", 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Register();
			}
		});
	}
}
