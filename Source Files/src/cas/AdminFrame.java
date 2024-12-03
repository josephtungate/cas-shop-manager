/**
 * The frame from which an admin can navigate and interface with all
 * admin system functionalities.
 * @author Joseph Marcus Tungate
 */
package cas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class AdminFrame extends JFrame {

	private JPanel contentPane;
	private JTable tblProducts;
	private DefaultTableModel dtmProduct;
	
	private Database db;
	
	/**
	 * Creates the AdminFrame and initialises its fields and events.
	 * @param systemDatabase The Database object which is
	 * being used by the system/main frame.
	 */
	public AdminFrame(Database systemDatabase) {
		if(systemDatabase == null)
			throw new IllegalArgumentException("systemDatabase is null");
		
		this.db = systemDatabase;	
		
		setTitle("CASH - Admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane spTable = new JScrollPane();
		spTable.setBounds(10, 41, 864, 355);
		contentPane.add(spTable);
		
		tblProducts = new JTable();
		tblProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spTable.setViewportView(tblProducts);
		
		JButton btnAddProduct = new JButton("Add Product...");
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Launches the dialog for adding a new product to the system.
				new AddProductDialog(db);
				populateTable();
				
			}
		});
		btnAddProduct.setBounds(749, 407, 125, 23);
		contentPane.add(btnAddProduct);
		
		JLabel lblTable = new JLabel("Shop Inventory");
		lblTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTable.setLabelFor(tblProducts);
		lblTable.setBounds(10, 11, 125, 20);
		contentPane.add(lblTable);
		
		this.dtmProduct = new DefaultTableModel();
		dtmProduct.setColumnIdentifiers(new Object[] {"Barcode", "Name", "Type", "Brand", "Colour",
													  "Connectivity", "Quantity", "Original Cost",
													  "Retail Price", "Additional Information"});
		tblProducts.setModel(dtmProduct);
		populateTable();
		
		this.setVisible(true);
	}
	
	/**
	 * Populates tblProducts with the the products held in systemDatabase. 
	 */
	private void populateTable() {
		try {
			Inventory products = db.getProducts();
			products.sortByQuantity();
			dtmProduct.setRowCount(0);
		    
			for(int i = 0; i < products.size(); i++) {
				Product p = products.get(i);
				Object[] rowData;
				
				if(p instanceof Keyboard) {
					rowData = new Object[] {
							p.getBarcode(),
							"Keyboard",
							((Keyboard)p).getType(),
							p.getBrand(),
							p.getColour(),
							p.isWired() ? "wired" : "wireless",
							p.getQuantity(),
							"£" + p.getOriginalCost(),
							"£" + p.getRetailPrice(),
							((Keyboard)p).getLayout().toString() + " layout"
					};
					
				}
				else if(p instanceof Mouse) {
					rowData = new Object[] {
							p.getBarcode(),
							"Mouse",
							((Mouse)p).getType(),
							p.getBrand(),
							p.getColour(),
							p.isWired() ? "wired" : "wireless",
							p.getQuantity(),
							"£" + p.getOriginalCost(),
							"£" + p.getRetailPrice(),
							((Mouse)p).getButtonCount() + " buttons"
					};
					
				}
				//Ignore any unrecognised product subclasses.
				else {
					continue;
				}
				
				dtmProduct.addRow(rowData);
			}
		
		} catch(IOException ex) {
			showWarning(ex.getMessage());			
		}
	}
	
	/**
	 * A general use method for displaying a warning about the user's behaviour.
	 * @param message The message which describes the problem.
	 */
	void showWarning(String message) {
		JOptionPane.showMessageDialog(this, "A problem occurred while processing your request: " + message,
				  "Warning", JOptionPane.WARNING_MESSAGE);
	}
}
