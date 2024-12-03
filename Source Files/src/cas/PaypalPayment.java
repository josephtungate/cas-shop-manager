/**
 * Stores immutable information about, and methods for, executing a PayPal payment
 * for a given Product and User.
 * @author Joseph Marcus Tungate
 */
package cas;

import java.io.IOException;

public class PaypalPayment implements PaymentMethod{
	private Database db;
	private Customer accountHolder;
	private Product item;
	private int itemQuantity;
	private String accountEmail;
	
	
	/**
	 * Creates a PaypalPayment instance from the given parameters.
	 * @param systemDatabase The Database object involved in this transaction.
	 * @param accountHolder The Customer who holds the PayPal account involved in this transaction.
	 * @param item The Product being purchased by the accountHolder.
	 * @param itemQuantity The quantity of item being purchased by the accountHolder.
	 * @param accountEmail The email address of accountHolder's PayPal account.
	 */
	public PaypalPayment(Database systemDatabase, Customer accountHolder, Product item, int itemQuantity, String accountEmail) {
		if(systemDatabase == null)
			throw new IllegalArgumentException("systemDatabase is null.");		
		if(accountHolder == null)
			throw new IllegalArgumentException("accountHolder is null.");
		if(item == null)
			throw new IllegalArgumentException("item is null.");
		if(itemQuantity < 0 || itemQuantity > item.getQuantity())
			throw new IllegalArgumentException("Invalid item quantity.");
		if(!validateEmail(accountEmail)) {
			throw new IllegalArgumentException("Invalid account email.");
		}
		
		this.db = systemDatabase;
		this.accountHolder = accountHolder;
		this.item = item;
		this.itemQuantity = itemQuantity;
		this.accountEmail = accountEmail;
	}
	
	/**
	 * Attempts to carry out the transaction described by this object.
	 * Returns true if the transaction was successful, false otherwise.
	 * @returns true if the transaction was successful. Returns false otherwise.
	 */
	@Override
	public boolean processPayment(){
		try {
		//Updates database to reflect purchase.
		Inventory products = db.getProducts();
		Product product = products.getByBarcode(this.item.getBarcode());
		product.setQuantity(product.getQuantity() - this.itemQuantity);
		db.writeProducts(products);
		//Write activity log.
		db.writeActivityLog(new ActivityLog(accountHolder, product, itemQuantity, "purchased", "PayPal"));
		return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Returns true if the given String represents a valid email address.
	 * A valid email address contains exactly one '@' character which neither
	 * starts nor ends the String and at least one '.' character which appears
	 * after the character following the '@' character and does not end the String,
	 * e.g. 'joe@domain.com'.
	 * @param email The email to validate.
	 * @return true if the given String represents a valid email address. Returns false otherwise.
	 */
	public static boolean validateEmail(String email) {
		boolean isValid = false;
		
		if(email != null &&
		  (email.indexOf('@') == email.lastIndexOf('@')) && 
		   email.indexOf('@') > 0 &&
		   email.lastIndexOf('.') > email.lastIndexOf('@') + 1 &&
		   email.length() - 1 > email.lastIndexOf('.'))
			isValid = true;
		
		return isValid;
	}
}
