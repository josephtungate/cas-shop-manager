/**
 * The frame from which a customer can navigate and interface with all
 * customer system functionalities.
 * @author Joseph Marcus Tungate
 */
package cas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class CustomerFrame extends JFrame {

	private JPanel contentPane;
	private JTable tblBrowse;
	private JTextField txtSearch;
	private JTable tblBasket;
	private DefaultTableModel dtmProduct;
	private DefaultTableModel dtmBasket;
	
	private Database db;
	private Inventory products;
	private Customer systemUser;

	/**
	 * Creates the CustomerFrame, initialises its fields and events.
	 * @param systemDatabase The Database object which is
	 * being used by the system/main frame.
	 * @param systemUser The Customer which is using the system.
	 */
	public CustomerFrame(Database systemDatabase, Customer systemUser) {
		if(systemUser == null)
			throw new IllegalArgumentException("systemUser is null.");
		if(systemDatabase == null)
			throw new IllegalArgumentException("systemDatabase is null.");
		
		this.systemUser = systemUser;
		this.db = systemDatabase;
		
		setTitle("CASH - Customer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 884, 441);
		contentPane.add(tabbedPane);
		
		JPanel pnlBrowse = new JPanel();
		tabbedPane.addTab("Browse", null, pnlBrowse, null);
		pnlBrowse.setLayout(null);
		
		JScrollPane scrlpBrowse = new JScrollPane();
		scrlpBrowse.setBounds(10, 36, 859, 332);
		pnlBrowse.add(scrlpBrowse);
		
		tblBrowse = new JTable();
		tblBrowse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblBrowse.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrlpBrowse.setViewportView(tblBrowse);
		
		JLabel lblSearch = new JLabel("Search by Brand:");
		lblSearch.setBounds(10, 11, 99, 14);
		pnlBrowse.add(lblSearch);
		
		txtSearch = new JTextField();
		lblSearch.setLabelFor(txtSearch);
		txtSearch.setBounds(119, 8, 531, 20);
		pnlBrowse.add(txtSearch);
		txtSearch.setColumns(10);
		
		JCheckBox chckbxUkLayout = new JCheckBox("Only UK Layout?");
		chckbxUkLayout.setBounds(656, 7, 118, 23);
		pnlBrowse.add(chckbxUkLayout);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					/* Searches the shop's inventory for Products which match
					 * the given brand and filters them by layout. Then updates
					 * tblBrowse to show the result of the search.
					 */
					String search = txtSearch.getText().trim();
					boolean onlyUKLayout = chckbxUkLayout.isSelected();
					
					products = db.getProducts();
					products.filter(search, onlyUKLayout);
					populateTable(products, dtmProduct);
				} catch (Exception ex) {
					showWarning(ex.getMessage());
				}
			}
		});
		btnSearch.setBounds(780, 7, 89, 23);
		pnlBrowse.add(btnSearch);
		
		JButton btnReset = new JButton("Reset Results");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Resets tblBrowse to show all Products in the shop's inventory.
				resetResults();
			}
		});
		btnReset.setBounds(10, 379, 130, 23);
		pnlBrowse.add(btnReset);
		
		JButton btnAddToBasket = new JButton("Add To Basket");
		btnAddToBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* Adds the Product selected by the user via the tblBrowse to
				 * the user's basket. If the user already has as many of a given
				 * item in their basket as the shop has in stock, then the user
				 * is notified and the operation is cancelled.
				 */
				int index = tblBrowse.getSelectedRow();
				if(index != -1) {
					Product p = products.get(index);
					BasketEntry b = systemUser.getByBarcode(p.getBarcode());
					if(b != null) {
						if(p.getQuantity() > b.getQuantity())
							systemUser.addToBasket(p);
						else
							showWarning("Your basket contains all copies of this product.");
					} else
						systemUser.addToBasket(p);
				} else 
					showWarning("No product was selected.");
				
				populateTable(systemUser, dtmBasket);
			}
		});
		btnAddToBasket.setBounds(739, 379, 130, 23);
		pnlBrowse.add(btnAddToBasket);
		
		JPanel pnlBasket = new JPanel();
		tabbedPane.addTab("My Basket", null, pnlBasket, null);
		pnlBasket.setLayout(null);
		
		JScrollPane scrlpBasket = new JScrollPane();
		scrlpBasket.setBounds(10, 11, 859, 357);
		pnlBasket.add(scrlpBasket);
		
		tblBasket = new JTable();
		scrlpBasket.setViewportView(tblBasket);
		
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* Launches a PaymentDialog where the user can 
				 * checkout their basket.				 * 
				 */
				try {
					if(systemUser.basketSize() > 0) {
						if(systemUser.validateBasket(db.getProducts())) {
							new PaymentDialog(systemUser, db);
							populateTable(systemUser, dtmBasket);
							resetResults();
						} else {
							showWarning("Your basket contains more copies of a product than we have in stock.");
						}
					} else {
						showWarning("Your basket is empty.");
					}
			
				} catch (IOException ex) {
					showWarning(ex.getMessage());
				}
			}
		});
		btnCheckout.setBounds(780, 379, 89, 23);
		pnlBasket.add(btnCheckout);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cancels the customer's basket and logs this as an activity.
				try {
					logAllItems("cancelled");
					systemUser.emptyBasket();
					populateTable(systemUser, dtmBasket);
					
				} catch (IOException ex) {
					showWarning(ex.getMessage());
				}
			}
		});
		btnCancel.setBounds(109, 379, 89, 23);
		pnlBasket.add(btnCancel);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Saves the customer's basket and logs this as an activity.
				try {
					logAllItems("saved");
					systemUser.emptyBasket();
					populateTable(systemUser, dtmBasket);
					
				} catch (IOException ex) {
					showWarning(ex.getMessage());
				}
				
			}
		});
		btnSave.setBounds(10, 379, 89, 23);
		pnlBasket.add(btnSave);
		
		this.dtmProduct = new DefaultTableModel();
		dtmProduct.setColumnIdentifiers(new Object[] {"Barcode", "Name", "Type", "Brand", "Colour",
													  "Connectivity", "Quantity", "Retail Price", "Additional Information"});
		tblBrowse.setModel(dtmProduct);
		
		this.dtmBasket = new DefaultTableModel();
		dtmBasket.setColumnIdentifiers(new Object[] {"Barcode", "Name", "Type", "Brand", "Colour",
				  "Connectivity", "Quantity in Stock", "Retail Price", "Additional Information", "Quantity in Basket"});
		tblBasket.setModel(dtmBasket);
		
		resetResults();
		populateTable(systemUser, dtmBasket);
		setVisible(true);
	}
	
	/**
	 * Populates the provided DefaultTableModel with the the products held in the given Inventory.
	 * @param products An Inventory containing the products which are to populate dtm.
	 * @param dtm The DefaultTableModel to be populated. 
	 */
	private void populateTable(Inventory products, DefaultTableModel dtm) {
		products.sortByQuantity();
		dtm.setRowCount(0);
	    
		for(int i = 0; i < products.size(); i++) {
				Product p = products.get(i);
				Object[] rowData;
				
				if(p.getQuantity() > 0) {					
					if(p instanceof Keyboard) {
						rowData = new Object[] {
								p.getBarcode(),
								"Keyboard",
								((Keyboard)p).getType(),
								p.getBrand(),
								p.getColour(),
								p.isWired() ? "wired" : "wireless",
								p.getQuantity(),
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
								"£" + p.getRetailPrice(),
								((Mouse)p).getButtonCount() + " buttons"
						};
						
					}
					else {
						continue;
					}
					dtm.addRow(rowData);
			}
		}
	}

	/**
	 * Populates the provided DefaultTableModel with the the products held in the basket of the given Customer.
	 * @param basketHolder An Customer whose basket is to populate dtm.
	 * @param dtm The DefaultTableModel to be populated. 
	 */
	private void populateTable(Customer basketHolder, DefaultTableModel dtm) {
		basketHolder.sortByQuantity();
		dtm.setRowCount(0);
	    
		for(int i = 0; i < basketHolder.basketSize(); i++) {
			Product p = basketHolder.getFromBasket(i).getProduct();
			Object[] rowData;
			
			if(p.getQuantity() > 0) {
				if(p instanceof Keyboard) {
					
					rowData = new Object[] {
							p.getBarcode(),
							"Keyboard",
							((Keyboard)p).getType(),
							p.getBrand(),
							p.getColour(),
							p.isWired() ? "wired" : "wireless",
							p.getQuantity(),
							"£" + p.getRetailPrice(),
							((Keyboard)p).getLayout().toString() + " layout",
							basketHolder.getFromBasket(i).getQuantity()
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
							"£" + p.getRetailPrice(),
							((Mouse)p).getButtonCount() + " buttons",
							basketHolder.getFromBasket(i).getQuantity()
					};
					
				}
				else {
					continue;
				}
				
				dtm.addRow(rowData);
			}
		}
	}

	/**
	 * Populates tblBrowse with the the products held in systemDatabase. 
	 */
	private void resetResults() {
		try {
			products = db.getProducts();
			populateTable(products, dtmProduct);
		} catch(IOException ex) {
			showWarning(ex.getMessage());
		}
		
	}
	
	/**
	 * A general use method for displaying a warning about the user's behaviour.
	 * @param message The message which describes the problem.
	 */
	private void showWarning(String message) {
		JOptionPane.showMessageDialog(this, "There was a problem processing your request: " + message,
				  "Warning", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Writes ActivtyLogs to the system's database for every item in the Customer's basket.
	 * @param status The status of the ActivityLogs being written to the system's database.
	 * @throws IOException Thrown if a problem occurs whilst writing to the database.
	 */
	private void logAllItems(String status) throws IOException {	
		for (int i = 0; i < systemUser.basketSize(); i++) {
			BasketEntry b = systemUser.getFromBasket(i);
			db.writeActivityLog(new ActivityLog(systemUser, b.getProduct(), b.getQuantity(), status));
		}
	}
}
