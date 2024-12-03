/**
 * MainFrame contains the main method for running the CAS System, CASH.
 * @author Joseph Marcus Tungate
 */

package cas;

import java.io.IOException;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;;

public class MainFrame extends JFrame {
	
	private JPanel contentPane;
	private JTable jtUsernames;
	private DefaultTableModel dtmUser;
	
	private Database db;
	private ArrayList<User> systemUsers;
	private User currentUser;
	
	//Launch the application. 
	public static void main(String[] args) {
		//File paths for the database.
		String userAccountsPath = "src/cas/UserAccounts.txt";
		String stockPath = "src/cas/Stock.txt";
		String activityLogPath = "src/cas/ActivityLog.txt";
		
		//Runs the CAS System.
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(stockPath, userAccountsPath, activityLogPath);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Creates a main frame from which the CAS System is run.
	 * @param stockPath The file path of stock.txt.
	 * @param userAccountsPath The file path of UserAccounts.txt.
	 * @param activityLogPath The file path of ActivityLog.txt.
	 */
	public MainFrame(String stockPath, String userAccountsPath, String activityLogPath) {
		setTitle("CASH - Login");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 414, 151);
		contentPane.add(scrollPane);
		
		jtUsernames = new JTable();
		jtUsernames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(jtUsernames);
		
		dtmUser = new DefaultTableModel();
		dtmUser.setColumnIdentifiers(new Object[] {"Username"});
		jtUsernames.setModel(dtmUser);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogin.setBounds(335, 227, 89, 23);
		contentPane.add(btnLogin);
		
		JLabel lblNewLabel = new JLabel("Welcome to CASH - Computer Accessories Shop Helper.");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 7, 414, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Select your username:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 41, 202, 14);
		contentPane.add(lblNewLabel_1);
		
		//Try accessing the database. Terminate execution if it fails.
		try{
			db = new Database(stockPath, userAccountsPath, activityLogPath);
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(this, "There was an error accessing the database. Execution will now terminate.",
										  "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		
		//Read the list of Users from the database and display them in the table.
		this.systemUsers = db.getUsers();
		
		for(User u : systemUsers) {
			Object[] rowData = new Object[] {u.getUsername()};
			dtmUser.addRow(rowData);			
		}
		
	}
	
	/**
	 * Launches the appropriate frame depending on the currently selected user in jtUsernames.
	 */
	private void login() {
		int selectedRow = this.jtUsernames.getSelectedRow();
		
		if(selectedRow != -1) {
			this.currentUser = this.systemUsers.get(selectedRow);
			
			try {	
				if(this.currentUser instanceof Admin) {
					new AdminFrame(db);
					this.setVisible(false);
				}
				else if(this.currentUser instanceof Customer) {
					new CustomerFrame(db, (Customer)this.currentUser);
					this.setVisible(false);
				}
				
				else
					throw new Exception("Selected user has undefined role.");
			}
			catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
}
