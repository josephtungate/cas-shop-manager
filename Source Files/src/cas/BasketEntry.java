/**
 * Contains a Product and the quantity of said Product in the user's basket.
 * @author Joseph Marcus Tungate
 */

package cas;

public class BasketEntry {
	private Product product;
	private int quantity;
	
	/**
	 * Creates a BasketEntry for the given Product and quantity.
	 * @param product The Product to be stored in this BasketEntry.
	 * @param quantity The quantity of the product stored in this BasketEntry.
	 */
	public BasketEntry(Product product, int quantity) {
		if(product == null)
			throw new IllegalArgumentException("Product is null.");
		if(quantity < 0) 
			throw new IllegalArgumentException("Quantity is negative.");
		
		this.product = product;
		this.quantity = quantity;
	}
	
	/**
	 * Returns the Product stored by this BasketEntry.
	 * @return the Product stored by this BasketEntry.
	 */
	public Product getProduct() {
		return this.product;
	}
	
	/**
	 * Returns the quantity stored by this BasketEntry.
	 * @return the quantity stored by this BasketEntry.
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Sets the quantity stored by this BasketEntry.
	 * @param quantity The quantity to be stored by this BasketEntry.
	 */
	public void setQuantity(int quantity) {
		if(quantity < 0)
			throw new IllegalArgumentException("Quantity is negative");
		
		this.quantity = quantity;
	}
}
