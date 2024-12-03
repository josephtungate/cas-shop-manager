/**
 * A dialog for collecting payment information for, and then processing,
 * the checkout of a customer's basket.
 * @author Joseph Marcus Tungate
 */
package cas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;

public class PaymentDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtEmail;
	private JTextField txtCardNumber;
	private JTextField txtSecurityCode;
	
	private Customer systemUser;
	private Database db;

	/**
	 * Creates a PaymentDialog.
	 * @param systemUser The Customer whose basket is to be processed.
	 * @param systemDatabase The database object being used by the system.
	 */
	public PaymentDialog(Customer systemUser, Database systemDatabase) {
		if(systemUser == null)
			throw new IllegalArgumentException("systemUser is null.");
		if(systemDatabase == null)
			throw new IllegalArgumentException("systemDatabase is null.");
		this.systemUser = systemUser;
		this.db = systemDatabase;
		
		setTitle("CASH - Payment");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 335, 345);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblForTotalPrice = new JLabel("Total Price:");
		lblForTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblForTotalPrice.setBounds(10, 11, 68, 22);
		contentPanel.add(lblForTotalPrice);
		
		JLabel lblTotalPrice = new JLabel("£" + systemUser.getTotalPrice().toString());
		lblForTotalPrice.setLabelFor(lblTotalPrice);
		lblTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalPrice.setBounds(88, 11, 80, 22);
		contentPanel.add(lblTotalPrice);
		
		JLabel lblPaymentMethod = new JLabel("Select Payment Method:");
		lblPaymentMethod.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPaymentMethod.setBounds(10, 44, 150, 22);
		contentPanel.add(lblPaymentMethod);
		
		ButtonGroup grpPaymentMethod = new ButtonGroup();
		
		JPanel pnlPaypal = new JPanel();
		pnlPaypal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlPaypal.setBounds(10, 74, 299, 75);
		contentPanel.add(pnlPaypal);
		pnlPaypal.setLayout(null);
		
		JLabel lblPaypal = new JLabel("Paypal");
		lblPaypal.setLabelFor(pnlPaypal);
		lblPaypal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPaypal.setBounds(10, 11, 74, 17);
		pnlPaypal.add(lblPaypal);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(10, 39, 42, 17);
		pnlPaypal.add(lblEmail);
		
		txtEmail = new JTextField();
		lblEmail.setLabelFor(txtEmail);
		txtEmail.setBounds(62, 39, 227, 20);
		pnlPaypal.add(txtEmail);
		txtEmail.setColumns(10);
		
		JPanel pnlCreditCard = new JPanel();
		pnlCreditCard.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlCreditCard.setBounds(10, 160, 299, 101);
		contentPanel.add(pnlCreditCard);
		pnlCreditCard.setLayout(null);
		
		JLabel lblCreditCard = new JLabel("Credit Card");
		lblCreditCard.setEnabled(false);
		lblCreditCard.setLabelFor(pnlCreditCard);
		lblCreditCard.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCreditCard.setBounds(10, 11, 89, 17);
		pnlCreditCard.add(lblCreditCard);
		
		JLabel lblCardNumber = new JLabel("Number:");
		lblCardNumber.setEnabled(false);
		lblCardNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCardNumber.setBounds(10, 39, 54, 17);
		pnlCreditCard.add(lblCardNumber);
		
		txtCardNumber = new JTextField();
		txtCardNumber.setEnabled(false);
		lblCardNumber.setLabelFor(txtCardNumber);
		txtCardNumber.setBounds(74, 39, 215, 20);
		pnlCreditCard.add(txtCardNumber);
		txtCardNumber.setColumns(10);
		
		JLabel lblSecurityCode = new JLabel("Security Code:");
		lblSecurityCode.setEnabled(false);
		lblSecurityCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSecurityCode.setBounds(10, 67, 91, 23);
		pnlCreditCard.add(lblSecurityCode);
		
		txtSecurityCode = new JTextField();
		txtSecurityCode.setEnabled(false);
		txtSecurityCode.setBounds(111, 70, 178, 20);
		pnlCreditCard.add(txtSecurityCode);
		txtSecurityCode.setColumns(10);
		
		JRadioButton rdbtnCreditCard = new JRadioButton("Credit Card");
		rdbtnCreditCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* Enables widgets for inputting credit card information and
				 * disables widgets for inputting PayPal information.
				 */
				lblPaypal.setEnabled(false);
				lblCreditCard.setEnabled(true);
				lblEmail.setEnabled(false);
				txtEmail.setEnabled(false);
				lblCardNumber.setEnabled(true);
				txtCardNumber.setEnabled(true);
				lblSecurityCode.setEnabled(true);
				txtSecurityCode.setEnabled(true);
			}
		});
		rdbtnCreditCard.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnCreditCard.setBounds(227, 46, 86, 23);
		contentPanel.add(rdbtnCreditCard);
		grpPaymentMethod.add(rdbtnCreditCard);
		
		JRadioButton rdbtnPaypal = new JRadioButton("Paypal");
		rdbtnPaypal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* Enables widgets for inputting PayPal information and
				 * disables widgets for inputting credit card information.
				 */
				lblPaypal.setEnabled(true);
				lblCreditCard.setEnabled(false);
				lblEmail.setEnabled(true);
				txtEmail.setEnabled(true);
				lblCardNumber.setEnabled(false);
				txtCardNumber.setEnabled(false);
				lblSecurityCode.setEnabled(false);
				txtSecurityCode.setEnabled(false);
			}
		});
		rdbtnPaypal.setSelected(true);
		rdbtnPaypal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnPaypal.setBounds(166, 46, 59, 23);
		contentPanel.add(rdbtnPaypal);
		grpPaymentMethod.add(rdbtnPaypal);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnSubmit = new JButton("Submit");
				btnSubmit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							/* Creates and executes a PaymentMethod for each BasketEntry in systemUser's basket,
							 * removing each BasketEntry from the basket once it has been processed.
							 * Then display's the total price and payment method as a dialog and disposes of
							 * this PaymentDialog.
							 */
							BigDecimal totalPrice = systemUser.getTotalPrice();
							boolean usingPaypal = rdbtnPaypal.isSelected();
							int basketSize = systemUser.basketSize();
							
							for(int i = 0; i < basketSize; i++) {
								BasketEntry item = systemUser.getFromBasket(0);
								PaymentMethod payment;
								
								if(usingPaypal)
									payment = new PaypalPayment(db, systemUser, item.getProduct(),
																item.getQuantity(), txtEmail.getText());
								else {
									payment = new CreditCardPayment(db, systemUser, item.getProduct(),
																	item.getQuantity(), txtCardNumber.getText(),
																	txtSecurityCode.getText());
								}
								payment.processPayment();
								systemUser.removeAllFromBasket(0);
							}
							
							if(usingPaypal)
								showDialog("£" + totalPrice.toString() + " paid using PayPal");
							else
								showDialog("£" + totalPrice.toString() + " paid using Credit Card");
							
							dispose();
							
						} catch (Exception ex) {
							showWarning(ex.getMessage());
						}
					}
				});
				buttonPane.add(btnSubmit);
				getRootPane().setDefaultButton(btnSubmit);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnCancel);
			}
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * A general use method for displaying a warning about the user's actions.
	 * @param message The message which describes the problem.
	 */
	private void showWarning(String message) {
		JOptionPane.showMessageDialog(this, "There was a problem processing your request: " + message,
									  "Warning", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * A general use method for displaying a message to the user.
	 * @param message The message to be displayed to the user.
	 */
	private void showDialog(String message) {
		JOptionPane.showMessageDialog(this, message,
				  "CASH - Payment", JOptionPane.INFORMATION_MESSAGE);
		
	}
}
