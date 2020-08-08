
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Main {

	private int userType;
	private String userEmail;
	private String total;
	private JFrame frmRailwayTicketBooking;
	private JTabbedPane tabbedPane;
	private JComboBox comboBox_location;
	private JComboBox comboBox_ticket;
	private JTextField textField_name;
	private JTextField textField_email;
	private JTextField textField_pass;
	private JTextField textField_premClass;
	private JTextField textField_supClass;
	private JTextField textField_ecoClass;
	private JTextField textField_premNClass;
	private JTextField textField_supNClass;
	private JTextField textField_ecoNClass;
	private JLabel lblPrice;
	private JPanel genReport_panel;
	private float[] ticket = new float[6];

	/**
	 * Default Constructor. Used to set the usertype and call the function to initialize GUI
	 * @param email - user Email
	 * @param type - user type
	 */
	public Main(String email, int type) {
		this.userEmail = email;
		this.userType = type;
		initialize();
		frmRailwayTicketBooking.setVisible(true);
	}
	
	public void setProfile() {
		try {
			File file = new File("users.txt");
			Scanner scan = new Scanner(file);

			while (scan.hasNext()) {
				String[] tokens = scan.nextLine().split(";");
				if (tokens[0].equals(userEmail)) {
					textField_name.setText(tokens[2]);
					textField_email.setText(tokens[0]);
					textField_pass.setText(tokens[1]);
					break;
				}
			}
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getPrice() {
		try {
			File file = new File("ticketprice.txt");
			Scanner scan = new Scanner(file);

			while (scan.hasNext()) {
				String[] tokens = scan.nextLine().split(";");
				lblPrice.setText("Total: RM " + tokens[comboBox_ticket.getSelectedIndex()]);
				this.total = tokens[comboBox_ticket.getSelectedIndex()];
			}
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to read the price of tickets
	 */
	public void readPrice() {
		try {
			File file = new File("ticketprice.txt");
			Scanner scan = new Scanner(file);

			while (scan.hasNext()) {
				String[] tokens = scan.nextLine().split(";");
				for (int i=0; i<6; i++)
					ticket[i] = Float.parseFloat(tokens[i]);
			}
			textField_premClass.setText(Float.toString(ticket[0]));
			textField_supClass.setText(Float.toString(ticket[1]));
			textField_ecoClass.setText(Float.toString(ticket[2]));
			textField_premNClass.setText(Float.toString(ticket[3]));
			textField_supNClass.setText(Float.toString(ticket[4]));
			textField_ecoNClass.setText(Float.toString(ticket[5]));
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to save the price of tickets
	 */
	public void savePrice() {
		FileOperation file = new FileOperation();
		String data = textField_premClass.getText() + ";" + 
				textField_supClass.getText() + ";" + 
				textField_ecoClass.getText() + ";" + 
				textField_premNClass.getText() + ";" + 
				textField_supNClass.getText() + ";" + 
				textField_ecoNClass.getText();
		try {
			file.removeNthLine("ticketprice.txt", 0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (file.writeInto(data, "ticketprice.txt")) {
			JOptionPane.showMessageDialog(null, 
					"Successfully update the price!", 
					"Success", 
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, 
					"Error updating ticket price!", 
					"Error", 
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/*
	 * Function to design and initialize GUI
	 */
	private void initialize() {
		frmRailwayTicketBooking = new JFrame();
		frmRailwayTicketBooking.setTitle("Railway Ticket Booking System");
		frmRailwayTicketBooking.setBounds(100, 100, 580, 442);
		frmRailwayTicketBooking.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRailwayTicketBooking.getContentPane().setLayout(null);
		frmRailwayTicketBooking.setLocationRelativeTo(null);
		frmRailwayTicketBooking.setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 574, 21);
		frmRailwayTicketBooking.getContentPane().add(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		JMenuItem mntmViewTicket = new JMenuItem("View Ticket");
		mntmViewTicket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewTicket(userEmail);
			}
		});

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmLogOut);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 55, 557, 347);
		frmRailwayTicketBooking.getContentPane().add(tabbedPane);

		JPanel bookTicket_panel = new JPanel();
		tabbedPane.addTab("Book Ticket", null, bookTicket_panel, null);
		bookTicket_panel.setLayout(null);

		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(10, 11, 60, 14);
		bookTicket_panel.add(lblLocation);

		comboBox_location = new JComboBox();
		comboBox_location.setModel(new DefaultComboBoxModel(new String[] {"Sibu", "Miri", "Bintulu", "Limbang", "Sematan", "Serian"}));
		comboBox_location.setBounds(107, 8, 350, 20);
		bookTicket_panel.add(comboBox_location);

		JButton btn_loc = new JButton("Select");
		btn_loc.setBounds(467, 7, 75, 23);
		bookTicket_panel.add(btn_loc);

		JLabel lblDay = new JLabel("Day: ");
		lblDay.setVisible(false);
		lblDay.setBounds(10, 40, 60, 14);
		bookTicket_panel.add(lblDay);

		JComboBox comboBox_day = new JComboBox();
		comboBox_day.setVisible(false);
		comboBox_day.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBox_day.setBounds(107, 37, 48, 20);
		bookTicket_panel.add(comboBox_day);

		JButton btn_Date = new JButton("Select");
		btn_Date.setVisible(false);
		btn_Date.setBounds(467, 36, 75, 23);
		bookTicket_panel.add(btn_Date);

		JLabel lblMonth = new JLabel("Month:");
		lblMonth.setVisible(false);
		lblMonth.setBounds(165, 40, 60, 14);
		bookTicket_panel.add(lblMonth);

		JComboBox comboBox_month = new JComboBox();
		comboBox_month.setVisible(false);
		comboBox_month.setModel(new DefaultComboBoxModel(new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "November", "December"}));
		comboBox_month.setBounds(212, 39, 113, 20);
		bookTicket_panel.add(comboBox_month);

		JLabel lblYear = new JLabel("Year:");
		lblYear.setVisible(false);
		lblYear.setBounds(335, 40, 37, 14);
		bookTicket_panel.add(lblYear);

		JComboBox comboBox_year = new JComboBox();
		comboBox_year.setVisible(false);
		comboBox_year.setModel(new DefaultComboBoxModel(new String[] {"2018", "2019", "2020", "2021"}));
		comboBox_year.setBounds(382, 39, 75, 20);
		bookTicket_panel.add(comboBox_year);

		JLabel lblTicketType = new JLabel("Ticket Type:");
		lblTicketType.setVisible(false);
		lblTicketType.setBounds(10, 74, 87, 14);
		bookTicket_panel.add(lblTicketType);

		comboBox_ticket = new JComboBox();
		comboBox_ticket.setVisible(false);
		comboBox_ticket.setModel(new DefaultComboBoxModel(new String[] {"Premier Class", "Superior Class", "Economy Class", "Premier Night Class", "Superior Night Class", "Economy Night Class"}));
		comboBox_ticket.setBounds(107, 71, 350, 20);
		bookTicket_panel.add(comboBox_ticket);

		JButton btn_ticket = new JButton("Select");
		btn_ticket.setVisible(false);
		btn_ticket.setBounds(467, 70, 75, 23);
		bookTicket_panel.add(btn_ticket);

		lblPrice = new JLabel("Price: RM");
		lblPrice.setVisible(false);
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPrice.setBounds(10, 261, 127, 47);
		bookTicket_panel.add(lblPrice);

		JButton btnBook = new JButton("Book");
		btnBook.setEnabled(false);
		btnBook.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBook.setBounds(439, 271, 103, 37);
		bookTicket_panel.add(btnBook);

		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnReset.setBounds(326, 271, 103, 37);
		bookTicket_panel.add(btnReset);

		JPanel profile_panel = new JPanel();
		tabbedPane.addTab("Profile", null, profile_panel, null);
		profile_panel.setLayout(null);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 14, 81, 14);
		profile_panel.add(lblName);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 72, 81, 14);
		profile_panel.add(lblEmail);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 130, 81, 14);
		profile_panel.add(lblPassword);

		JButton btnSaveProf = new JButton("Save");
		btnSaveProf.setBounds(427, 264, 115, 44);
		profile_panel.add(btnSaveProf);

		textField_name = new JTextField();
		textField_name.setBounds(10, 39, 532, 20);
		profile_panel.add(textField_name);
		textField_name.setColumns(10);

		textField_email = new JTextField();
		textField_email.setBounds(10, 99, 532, 20);
		profile_panel.add(textField_email);
		textField_email.setColumns(10);

		textField_pass = new JTextField();
		textField_pass.setBounds(10, 154, 532, 20);
		profile_panel.add(textField_pass);
		textField_pass.setColumns(10);

		genReport_panel = new JPanel();
		tabbedPane.addTab("Generate Report", null, genReport_panel, null);

		JPanel editPrice_panel = new JPanel();
		tabbedPane.addTab("Edit Price", null, editPrice_panel, null);
		editPrice_panel.setLayout(null);

		JLabel lblTicketType_1 = new JLabel("Premium Class:");
		lblTicketType_1.setBounds(113, 35, 180, 14);
		editPrice_panel.add(lblTicketType_1);

		textField_premClass = new JTextField();
		textField_premClass.setBounds(303, 32, 112, 20);
		editPrice_panel.add(textField_premClass);
		textField_premClass.setColumns(10);

		JLabel lblSuperiorClass = new JLabel("Superior Class:");
		lblSuperiorClass.setBounds(113, 63, 180, 14);
		editPrice_panel.add(lblSuperiorClass);

		textField_supClass = new JTextField();
		textField_supClass.setColumns(10);
		textField_supClass.setBounds(303, 60, 112, 20);
		editPrice_panel.add(textField_supClass);

		JLabel lblEconomyClass = new JLabel("Economy Class:");
		lblEconomyClass.setBounds(113, 91, 180, 14);
		editPrice_panel.add(lblEconomyClass);

		textField_ecoClass = new JTextField();
		textField_ecoClass.setColumns(10);
		textField_ecoClass.setBounds(303, 88, 112, 20);
		editPrice_panel.add(textField_ecoClass);

		JLabel lblPremiumNightClass = new JLabel("Premium Night Class:");
		lblPremiumNightClass.setBounds(113, 122, 180, 14);
		editPrice_panel.add(lblPremiumNightClass);

		textField_premNClass = new JTextField();
		textField_premNClass.setColumns(10);
		textField_premNClass.setBounds(303, 119, 112, 20);
		editPrice_panel.add(textField_premNClass);

		JLabel lblSuperiorNightClass = new JLabel("Superior Night Class:");
		lblSuperiorNightClass.setBounds(113, 150, 180, 14);
		editPrice_panel.add(lblSuperiorNightClass);

		textField_supNClass = new JTextField();
		textField_supNClass.setColumns(10);
		textField_supNClass.setBounds(303, 147, 112, 20);
		editPrice_panel.add(textField_supNClass);

		JLabel lblEconomyNightClass = new JLabel("Economy Night Class:");
		lblEconomyNightClass.setBounds(113, 178, 180, 14);
		editPrice_panel.add(lblEconomyNightClass);

		textField_ecoNClass = new JTextField();
		textField_ecoNClass.setColumns(10);
		textField_ecoNClass.setBounds(303, 175, 112, 20);
		editPrice_panel.add(textField_ecoNClass);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(326, 223, 89, 23);
		editPrice_panel.add(btnSave);

		JLabel lblWelcome = new JLabel("Welcome, ");
		lblWelcome.setBounds(10, 23, 554, 21);
		frmRailwayTicketBooking.getContentPane().add(lblWelcome);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePrice();
			}
		});

		btn_loc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_location.setEnabled(false);
				btn_loc.setEnabled(false);
				lblDay.setVisible(true);
				comboBox_day.setVisible(true);
				lblMonth.setVisible(true);
				comboBox_month.setVisible(true);
				lblYear.setVisible(true);
				comboBox_year.setVisible(true);
				btn_Date.setVisible(true);
			}
		});

		btn_Date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_day.setEnabled(false);
				comboBox_month.setEnabled(false);
				comboBox_year.setEnabled(false);
				btn_Date.setEnabled(false);
				lblTicketType.setVisible(true);
				comboBox_ticket.setVisible(true);
				btn_ticket.setVisible(true);
			}
		});

		btn_ticket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_ticket.setEnabled(false);
				btn_ticket.setEnabled(false);
				getPrice();
				lblPrice.setVisible(true);
				btnBook.setEnabled(true);
			}
		});

		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_location.setEnabled(true);
				btn_loc.setEnabled(true);
				comboBox_day.setEnabled(true);
				comboBox_month.setEnabled(true);
				comboBox_year.setEnabled(true);
				btn_Date.setEnabled(true);
				comboBox_ticket.setEnabled(true);
				btn_ticket.setEnabled(true);
				btnBook.setEnabled(false);
				
				lblDay.setVisible(false);
				comboBox_day.setVisible(false);
				lblMonth.setVisible(false);
				comboBox_month.setVisible(false);
				lblYear.setVisible(false);
				comboBox_year.setVisible(false);
				btn_Date.setVisible(false);
				lblTicketType.setVisible(false);
				comboBox_ticket.setVisible(false);
				btn_ticket.setVisible(false);
				lblPrice.setVisible(false);
			}
		});
		
		btnSaveProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileOperation file = new FileOperation();
				String string = textField_email.getText() + ";" + textField_pass.getText() + ";" + textField_name.getText();
				int del_line = file.findLine(userEmail, "users.txt");
				try {
					file.removeNthLine("users.txt", del_line);
					if (file.writeInto(string, "users.txt")) {
						JOptionPane.showMessageDialog(null, 
								"Successfully update your profile!", 
								"Success", 
								JOptionPane.INFORMATION_MESSAGE);
						lblWelcome.setText("Welcome, " + textField_email.getText());
					} else {
						JOptionPane.showMessageDialog(null, 
								"Error updating profile!", 
								"Error", 
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Payment(userEmail, comboBox_location.getSelectedItem().toString(), comboBox_day.getSelectedItem().toString(), 
						comboBox_month.getSelectedItem().toString(), comboBox_year.getSelectedItem().toString(),
						comboBox_ticket.getSelectedItem().toString(), total);
			}
		});
		
		if (userType == 0) {
			tabbedPane.remove(bookTicket_panel);
			tabbedPane.remove(profile_panel);
			lblWelcome.setText("Welcome, Admin");
			genReport();
			readPrice();
		} else {
			tabbedPane.remove(genReport_panel);
			tabbedPane.remove(editPrice_panel);
			lblWelcome.setText("Welcome, " + userEmail);
			setProfile();
			mnNewMenu.add(mntmViewTicket);
		}
	}
	
	public void genReport() {
		try {
            genReport_panel.setLayout(new BorderLayout());
            JTable table = new JTable();

            String readLine = null;

            TicketTableModel tableModel = new TicketTableModel();
            File file = new File("admin-record.txt");

            FileReader reader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(reader);

            List<Ticket> ticketList = new ArrayList<Ticket>();
            while((readLine = bufReader.readLine()) != null) {
                String[] splitData = readLine.split(";");

                Ticket t = new Ticket();
                t.setUserEmail(splitData[0]);
                t.setLocation(splitData[1]);
                t.setDay(splitData[2]);
                t.setMonth(splitData[3]);
                t.setYear(splitData[4]);
                t.setType(splitData[5]);
                t.setTotal(splitData[6]);
                ticketList.add(t);
            }
            reader.close();
            bufReader.close();

            tableModel.setList(ticketList);
            table.setModel(tableModel);
            
            genReport_panel.add(new JScrollPane(table));

        } catch(IOException ex) {}
	}
	
	class Ticket {

		private String userEmail;
        private String location;
        private String day;
        private String month;
        private String year;
        private String type;
        private String total;
        
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public String getUserEmail() {
			return userEmail;
		}
		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}
    }

    class TicketTableModel extends AbstractTableModel {
    	
		private static final long serialVersionUID = 1L;
		private List<Ticket> list = new ArrayList<Ticket>();
        private String[] columnNames = {"Email","Location", "Date", "Ticket Type","Total"};

        public void setList(List<Ticket> list) {
            this.list = list;
            fireTableDataChanged();
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public int getRowCount() {
            return list.size();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
            case 0:
            	return list.get(rowIndex).getUserEmail();
            case 1:
                return list.get(rowIndex).getLocation();
            case 2:
                return list.get(rowIndex).getDay() + " " + list.get(rowIndex).getMonth() + " " + list.get(rowIndex).getYear();
            case 3:
            	return list.get(rowIndex).getType();
            case 4:
            	return list.get(rowIndex).getTotal();
            default:
                return null;
            }
        }
    }
}
