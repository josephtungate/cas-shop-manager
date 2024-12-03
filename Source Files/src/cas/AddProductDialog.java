/**
 * A JDialog which allows the user to add a new product
 * to the system.
 * @author Joseph Marcus Tungate
 */

package cas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

public class AddProductDialog extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTextField txtBarcode;
	private JTextField txtBrand;
	private JTextField txtColour;
	private JTextField txtQuantity;
	private JTextField txtOriginalCost;
	private JTextField txtRetailPrice;
	private JTextField txtButtonCount;
	
	private Database db;

	/**
	 * Creates the AddProductDialog, initialises its
	 * fields and events.
	 * @param systemDatabase The Database object which is
	 * being used by the system/main frame.
	 */
	public AddProductDialog(Database systemDatabase) {
		if(systemDatabase == null)
			throw new IllegalArgumentException("systemDatabase is null");
		
		this.db = systemDatabase;		
		
		setTitle("CASH - Add Product");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setAlwaysOnTop(true);
		setBounds(100, 100, 615, 363);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblBarcode = new JLabel("Barcode:");
		lblBarcode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBarcode.setBounds(10, 11, 89, 24);
		contentPanel.add(lblBarcode);
		
		JLabel lblBrand = new JLabel("Brand:");
		lblBrand.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBrand.setBounds(10, 46, 89, 24);
		contentPanel.add(lblBrand);
		
		JLabel lblColour = new JLabel("Colour:");
		lblColour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblColour.setBounds(10, 81, 89, 24);
		contentPanel.add(lblColour);
		
		JLabel lblConnectivity = new JLabel("Connectivity:");
		lblConnectivity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConnectivity.setBounds(10, 116, 89, 24);
		contentPanel.add(lblConnectivity);
		
		ButtonGroup grpConnectivity = new ButtonGroup();
		
		JRadioButton rdbtnWired = new JRadioButton("Wired");
		rdbtnWired.setSelected(true);
		rdbtnWired.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnWired.setBounds(109, 117, 65, 24);
		contentPanel.add(rdbtnWired);
		grpConnectivity.add(rdbtnWired);
		
		JRadioButton rdbtnWireless = new JRadioButton("Wireless");
		rdbtnWireless.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnWireless.setBounds(190, 117, 69, 24);
		contentPanel.add(rdbtnWireless);
		grpConnectivity.add(rdbtnWireless);
		
		JLabel lblQuantity = new JLabel("Quantity in Stock:");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantity.setBounds(10, 151, 111, 24);
		contentPanel.add(lblQuantity);
		
		JLabel lblOriginalCost = new JLabel("Original Cost:");
		lblOriginalCost.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOriginalCost.setBounds(10, 186, 89, 24);
		contentPanel.add(lblOriginalCost);
		
		JLabel lblRetailPrice = new JLabel("Retail Price:");
		lblRetailPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRetailPrice.setBounds(10, 221, 89, 24);
		contentPanel.add(lblRetailPrice);
		
		txtBarcode = new JTextField();
		lblBarcode.setLabelFor(txtBarcode);
		txtBarcode.setBounds(109, 15, 150, 20);
		contentPanel.add(txtBarcode);
		txtBarcode.setColumns(10);
		
		txtBrand = new JTextField();
		lblBrand.setLabelFor(txtBrand);
		txtBrand.setBounds(109, 50, 150, 20);
		contentPanel.add(txtBrand);
		txtBrand.setColumns(10);
		
		txtColour = new JTextField();
		lblColour.setLabelFor(txtColour);
		txtColour.setBounds(109, 85, 150, 20);
		contentPanel.add(txtColour);
		txtColour.setColumns(10);
		
		txtQuantity = new JTextField();
		lblQuantity.setLabelFor(txtQuantity);
		txtQuantity.setBounds(131, 155, 128, 20);
		contentPanel.add(txtQuantity);
		txtQuantity.setColumns(10);
		
		txtOriginalCost = new JTextField();
		lblOriginalCost.setLabelFor(txtOriginalCost);
		txtOriginalCost.setBounds(109, 190, 150, 20);
		contentPanel.add(txtOriginalCost);
		txtOriginalCost.setColumns(10);
		
		txtRetailPrice = new JTextField();
		lblRetailPrice.setLabelFor(txtRetailPrice);
		txtRetailPrice.setBounds(109, 225, 150, 20);
		contentPanel.add(txtRetailPrice);
		txtRetailPrice.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(320, 11, 65, 24);
		contentPanel.add(lblName);
		
		ButtonGroup grpName = new ButtonGroup();
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(307, 46, 278, 112);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblMouseType = new JLabel("Type:");
		lblMouseType.setBounds(10, 42, 89, 24);
		panel.add(lblMouseType);
		lblMouseType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblButtonCount = new JLabel("Number of Buttons:");
		lblButtonCount.setBounds(10, 77, 127, 24);
		panel.add(lblButtonCount);
		lblButtonCount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JComboBox cbxMouseType = new JComboBox();
		lblMouseType.setLabelFor(cbxMouseType);
		cbxMouseType.setBounds(146, 45, 120, 22);
		panel.add(cbxMouseType);
		cbxMouseType.setModel(new DefaultComboBoxModel(MouseType.values()));
		
		txtButtonCount = new JTextField();
		lblButtonCount.setLabelFor(txtButtonCount);
		txtButtonCount.setBounds(147, 81, 119, 20);
		panel.add(txtButtonCount);
		txtButtonCount.setColumns(10);
		
		JLabel lblMouse = new JLabel("Mouse");
		lblMouse.setLabelFor(panel);
		lblMouse.setBounds(10, 11, 89, 24);
		panel.add(lblMouse);
		lblMouse.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(307, 166, 278, 112);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblKeyboard = new JLabel("Keyboard");
		lblKeyboard.setEnabled(false);
		lblKeyboard.setLabelFor(panel_1);
		lblKeyboard.setBounds(10, 11, 89, 24);
		panel_1.add(lblKeyboard);
		lblKeyboard.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblKeyboardLayout = new JLabel("Layout:");
		lblKeyboardLayout.setEnabled(false);
		lblKeyboardLayout.setBounds(10, 77, 89, 24);
		panel_1.add(lblKeyboardLayout);
		lblKeyboardLayout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblKeyboardType = new JLabel("Type:");
		lblKeyboardType.setEnabled(false);
		lblKeyboardType.setBounds(10, 42, 89, 24);
		panel_1.add(lblKeyboardType);
		lblKeyboardType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JComboBox cbxKeyboardType = new JComboBox();
		cbxKeyboardType.setEnabled(false);
		lblKeyboardType.setLabelFor(cbxKeyboardType);
		cbxKeyboardType.setBounds(146, 45, 120, 22);
		panel_1.add(cbxKeyboardType);
		cbxKeyboardType.setModel(new DefaultComboBoxModel(KeyboardType.values()));
		
		JComboBox cbxLayout = new JComboBox();
		cbxLayout.setEnabled(false);
		lblKeyboardLayout.setLabelFor(cbxLayout);
		cbxLayout.setBounds(146, 80, 120, 22);
		panel_1.add(cbxLayout);
		cbxLayout.setModel(new DefaultComboBoxModel(KeyboardLayout.values()));
		
		JRadioButton rdbtnMouse = new JRadioButton("Mouse");
		rdbtnMouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			/* Enables the widgets for entering mouse data.
			 * Disables the widgets for entering keyboard data.
			 */
				lblMouse.setEnabled(true);
				lblMouseType.setEnabled(true);
				cbxMouseType.setEnabled(true);
				lblButtonCount.setEnabled(true);
				txtButtonCount.setEnabled(true);
				
				lblKeyboard.setEnabled(false);
				lblKeyboardType.setEnabled(false);
				cbxKeyboardType.setEnabled(false);
				lblKeyboardLayout.setEnabled(false);
				cbxLayout.setEnabled(false);
			}
		});
		rdbtnMouse.setSelected(true);
		rdbtnMouse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnMouse.setBounds(391, 13, 65, 24);
		contentPanel.add(rdbtnMouse);
		
		JRadioButton rdbtnKeyboard = new JRadioButton("Keyboard");
		rdbtnKeyboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			/* Disables the widgets for entering mouse data.
			 * Enables the widgets for entering keyboard data.
			 */
				lblMouse.setEnabled(false);
				lblMouseType.setEnabled(false);
				cbxMouseType.setEnabled(false);
				lblButtonCount.setEnabled(false);
				txtButtonCount.setEnabled(false);
				
				lblKeyboard.setEnabled(true);
				lblKeyboardType.setEnabled(true);
				cbxKeyboardType.setEnabled(true);
				lblKeyboardLayout.setEnabled(true);
				cbxLayout.setEnabled(true);
			}
		});
		rdbtnKeyboard.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnKeyboard.setBounds(458, 13, 77, 23);
		contentPanel.add(rdbtnKeyboard);
		
		grpName.add(rdbtnMouse);
		grpName.add(rdbtnKeyboard);
	
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 /*	Processes the form data as input by the user.
			  *	If the form is valid, the product is added
			  * to the database and the dialog closes.
			  * If the form is invalid, the user is warned by a dialog.
			  */
				try {					
					//Parse Product from form input.
					Product formProduct;
					int barcode = Integer.parseInt(txtBarcode.getText());
					String brand = txtBrand.getText();
					String colour = txtColour.getText();
					boolean isWired;
					
					if(rdbtnWired.isSelected())
						isWired = true;
					else
						isWired = false;
					
					int quantity = Integer.parseInt(txtQuantity.getText());
					BigDecimal originalCost = new BigDecimal(txtOriginalCost.getText());
					BigDecimal retailPrice = new BigDecimal(txtRetailPrice.getText());
					boolean isMouse = rdbtnMouse.isSelected();
					
					if(isMouse) {
						MouseType type = (MouseType)cbxMouseType.getSelectedItem();
						int buttons = Integer.parseInt(txtButtonCount.getText());
						formProduct = new Mouse(barcode, brand, colour, isWired, type, buttons, originalCost, retailPrice, quantity);
						
					} else {
						KeyboardType type = (KeyboardType)cbxKeyboardType.getSelectedItem();
						KeyboardLayout layout = (KeyboardLayout)cbxLayout.getSelectedItem();
						formProduct = new Keyboard(barcode, brand, colour, isWired, type, layout, originalCost, retailPrice, quantity);
					}
					
					//If correctly parsed, add Product to Database.
					Inventory products = db.getProducts();
					if(!products.add(formProduct))
						throw new Exception("A product with this barcode already exists.");
					
					db.writeProducts(products);	
					dispose();
				
				} catch(NumberFormatException ex) {
					showWarning("A number you entered is invalid.");
				} catch(Exception ex) {
					showWarning(ex.getMessage());
				}
			}
		});
		
		buttonPane.add(btnSubmit);
		getRootPane().setDefaultButton(btnSubmit);
	
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Disposes the form when the user clicks 'Cancel'.
				dispose();
			}
		});
		buttonPane.add(btnCancel);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * A general use method for displaying a warning about the user's form input.
	 * @param message The message which describes the problem.
	 */
	private void showWarning(String message) {
		JOptionPane.showMessageDialog(this, "There was a problem processing your request: " + message,
									  "Warning", JOptionPane.WARNING_MESSAGE);
	}
}
