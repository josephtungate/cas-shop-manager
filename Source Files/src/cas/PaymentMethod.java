/**
 * A payment method processes a transaction.
 * @author Joseph Marcus Tungate
 */

package cas;

public interface PaymentMethod {
	/**
	 * Processes a transaction within the system.
	 * Typically used for processing a transaction where the user purchases a product.
	 * @return true if the transaction is successful. Returns false otherwise.
	 */
	public boolean processPayment();
}
