
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class Payment {

	private String userEmail;
	private String ticketLoc;
	private String ticketDay;
	private String ticketMonth;
	private String ticketYear;
	private String ticketType;
	private String ticketTotal;

	private JFrame frmPayment;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblTotalRm;
	private JButton btnCheckout;

	/**
	 * Create the application.
	 */
	public Payment(String email, String loc, String day, String month, String year, String type, String total) {
		this.userEmail = email;
		this.ticketLoc = loc;
		this.ticketDay = day;
		this.ticketMonth = month;
		this.ticketYear = year;
		this.ticketType = type;
		this.ticketTotal = total;
		initialize();
		frmPayment.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPayment = new JFrame();
		frmPayment.setTitle("Payment");
		frmPayment.setBounds(100, 100, 450, 335);
		frmPayment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPayment.getContentPane().setLayout(null);

		JLabel lblPayUsingVisa = new JLabel("Pay using Visa:");
		lblPayUsingVisa.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPayUsingVisa.setBounds(10, 11, 128, 35);
		frmPayment.getContentPane().add(lblPayUsingVisa);

		JLabel lblCardNumber = new JLabel("Card Number:");
		lblCardNumber.setBounds(10, 57, 93, 14);
		frmPayment.getContentPane().add(lblCardNumber);

		textField = new JTextField();
		textField.setBounds(10, 82, 414, 20);
		frmPayment.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblExpirationDate = new JLabel("Expiration Date:");
		lblExpirationDate.setBounds(10, 113, 93, 14);
		frmPayment.getContentPane().add(lblExpirationDate);

		textField_1 = new JTextField();
		textField_1.setToolTipText("");
		textField_1.setColumns(10);
		textField_1.setBounds(10, 138, 414, 20);
		frmPayment.getContentPane().add(textField_1);

		JLabel lblCardHolderName = new JLabel("Card Holder Name:");
		lblCardHolderName.setBounds(10, 169, 128, 14);
		frmPayment.getContentPane().add(lblCardHolderName);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(10, 194, 414, 20);
		frmPayment.getContentPane().add(textField_2);

		lblTotalRm = new JLabel("Total: RM " + this.ticketTotal);
		lblTotalRm.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotalRm.setBounds(10, 245, 315, 40);
		frmPayment.getContentPane().add(lblTotalRm);

		btnCheckout = new JButton("Checkout");
		btnCheckout.setBounds(335, 245, 89, 40);
		frmPayment.getContentPane().add(btnCheckout);

		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("") || textField.getText().equals("") || textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, 
							"Please fill in all the text fields!", 
							"Error", 
							JOptionPane.WARNING_MESSAGE);
				} else {
					FileOperation file = new FileOperation();
					String string = userEmail + ";" + ticketLoc + ";" + ticketDay + ";" + ticketMonth + ";" + ticketYear + ";" + ticketType + ";" + ticketTotal;
					String string2 = ticketLoc + ";" + ticketDay + ";" + ticketMonth + ";" + ticketYear + ";" + ticketType + ";" + ticketTotal;
					if (file.writeInto(string, "admin-record.txt") && file.writeInto(string2, userEmail + "-record.txt")) {
						JOptionPane.showMessageDialog(null, 
								"Payment successful!", 
								"Success", 
								JOptionPane.INFORMATION_MESSAGE);
						frmPayment.dispose();
					} else {
						JOptionPane.showMessageDialog(null, 
								"Payment failed!", 
								"Error", 
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
	}

}
