
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class ViewTicket {

	private String userEmail;
	
    public ViewTicket(String email) {
    	this.userEmail = email;
    	createUI();
    }
    
    private void createUI() {

        try {
            JFrame frame = new JFrame();
            frame.setLayout(new BorderLayout());
            JTable table = new JTable();

            String readLine = null;

            TicketTableModel tableModel = new TicketTableModel();
            File file = new File(userEmail + "-record.txt");

            FileReader reader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(reader);

            List<Ticket> ticketList = new ArrayList<Ticket>();
            while((readLine = bufReader.readLine()) != null) {
                String[] splitData = readLine.split(";");

                Ticket t = new Ticket();
                t.setLocation(splitData[0]);
                t.setDay(splitData[1]);
                t.setMonth(splitData[2]);
                t.setYear(splitData[3]);
                t.setType(splitData[4]);
                t.setTotal(splitData[5]);
                ticketList.add(t);
            }
            reader.close();
            bufReader.close();
            //scan.close();

            tableModel.setList(ticketList);
            table.setModel(tableModel);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		frame.setLocationRelativeTo(null);
            frame.add(new JScrollPane(table));
            frame.setTitle("Purchase History");
            frame.pack();
            frame.setVisible(true);

        } catch(IOException ex) {}
    }

    class Ticket {

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
    }

    class TicketTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private List<Ticket> list = new ArrayList<Ticket>();
        private String[] columnNames = {"Location", "Date", "Ticket Type","Total"};

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
                return list.get(rowIndex).getLocation();
            case 1:
                return list.get(rowIndex).getDay() + " " + list.get(rowIndex).getMonth() + " " + list.get(rowIndex).getYear();
            case 2:
            	return list.get(rowIndex).getType();
            case 3:
            	return list.get(rowIndex).getTotal();
            default:
                return null;
            }
        }
    }
}