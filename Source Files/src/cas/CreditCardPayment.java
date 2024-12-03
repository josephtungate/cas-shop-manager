/**
 * Stores immutable information about, and methods for, executing a credit card payment
 * for a given Product and User.
 * @author Joseph Marcus Tungate
 */

package cas;

public class CreditCardPayment implements PaymentMethod{
	private Database db;
	private Customer cardHolder;
	private Product item;
	private int itemQuantity;
	private String cardNumber;
	private String securityCode;
	
	/**
	 * Creates a CreditCardPayment instance from the given parameters.
	 * @param systemDatabase The Database object to be involved in the payment.
	 * @param cardHolder The Customer who is purchasing item.
	 * @param item The Product being purchased by cardHolder.
	 * @param itemQuantity The quantity of item involved in the payment.
	 * @param cardNumber The card number of the card held by cardHolder.
	 * @param securityCode The security code of the card held by cardHolder
	 */
	public CreditCardPayment(Database systemDatabase, Customer cardHolder, Product item, int itemQuantity, String cardNumber, String securityCode) {
		if(systemDatabase == null)
			throw new IllegalArgumentException("systemDatabase is null.");		
		if(cardHolder == null)
			throw new IllegalArgumentException("cardHolder is null.");
		if(item == null)
			throw new IllegalArgumentException("item is null.");
		if(itemQuantity < 0 || itemQuantity > item.getQuantity())
			throw new IllegalArgumentException("Invalid item quantity.");
		if(!validateCardNumber(cardNumber)) {
			throw new IllegalArgumentException("Invalid card number.");
		}
		if(!validateSecurityCode(securityCode)) {
			throw new IllegalArgumentException("Invalid security code.");
		}
		
		this.db = systemDatabase;
		this.cardHolder = cardHolder;
		this.item = item;
		this.itemQuantity = itemQuantity;
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
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
		db.writeActivityLog(new ActivityLog(cardHolder, product, itemQuantity, "purchased", "Credit Card"));
		return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Returns true if the given card number is a valid card number.
	 * @param cardNumber The card number to validate.
	 * @return true if cardNumber is valid. Returns false otherwise.
	 */
	public static boolean validateCardNumber(String cardNumber) {
		boolean isValid = false;
		
		if(cardNumber.length() == 16 && isStringNumeric(cardNumber))
			isValid = true;
		
		return isValid;
	}
	
	/**
	 * Returns true if the given security code is a valid card number.
	 * @param securityCode The security code to validate.
	 * @return true if securityCode is valid. Returns false otherwise.
	 */
	public static boolean validateSecurityCode(String securityCode) {
		boolean isValid = false;
		
		if(securityCode.length() == 3 && isStringNumeric(securityCode))
			isValid = true;
		
		return isValid;
	}
	
	/**
	 * Returns true if the given String only contains digits.
	 * @param str The String that will be checked for numeric content.
	 * @return true if the given String only contains digits.
	 */
	public static boolean isStringNumeric(String str) {
		boolean isNumeric = true;
		
		for(char digit : str.toCharArray()) {
			if(!Character.isDigit(digit)) {
				isNumeric = false;
				break;
			}
		}		
		return isNumeric;
	}

}
