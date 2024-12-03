/**
 * Contains information about a CAS customer.
 * Customer's have access to system functionalities which other
 * User subclasses do not.
 * @author Joseph Marcus Tungate
 */
package cas;

import java.math.BigDecimal;

public class Customer extends User {
	private Basket basket;
	
	/**
	 * Creates a Customer instance and specifies its attributes.
	 * @param id The customer's id number.
	 * @param username The customer's username.
	 * @param surname The customer's surname.
	 * @param houseNumber The customer's house number.
	 * @param city The customer's city.
	 * @param postcode The customer's postcode.
	 */
	public Customer(int id, String username, String surname, int houseNumber,
			String city, String postcode) {
		super(id, username, surname, houseNumber, city, postcode);
		
		basket = new Basket();
	}
	
	/**
	 * Returns the product at the given index in the User's basket.
	 * @param index The position of the product in the basket.
	 * @return The product at the given index in the User's basket.
	 */
	public BasketEntry getFromBasket(int index) {
		return this.basket.get(index);
	}
	
	/**
	 * Adds the given product to the end of the customer's basket.
	 * @param product The product to add to the basket.
	 */
	public void addToBasket(Product product) {
		this.basket.add(product);
	}
	
	/**
	 * Returns the number of unique products in the customer's basket.
	 * @return the number of unique products in the customer's basket.
	 */
	public int basketSize() {
		return this.basket.size();
	}
	
	/**
	 * Removes one instance of the product at the given index from the basket.
	 * @param index The index of the product to remove from the basket.
	 */
	public void removeFromBasket(int index) {
		this.basket.remove(index);
	}
	
	/**
	 * Removes all copies of the product at the given index from the basket.
	 * @param index The position in the basket of the products to remove.
	 */
	public void removeAllFromBasket(int index) {
		this.basket.removeAll(index);
	}
	
	/**
	 * Empties the basket.
	 */
	public void emptyBasket() {
		this.basket = new Basket();
	}
	
	/**
	 * Sorts the products in the basket by quantity in basket.
	 */
	public void sortByQuantity() {
		this.basket.sortByQuantity();
	}
	
	/**
	 * Returns true if all members of this Customer's basket exist in the given Inventory
	 * and their quantities do not exceed the quantity in stock.
	 * @param stock The Inventory against which the basket is validated.
	 * @return true if the basket is valid against stock. Returns false otherwise.
	 */
	public boolean validateBasket(Inventory stock) {
		return basket.validate(stock);
	}
	
	/**
	 * Returns the sum of the retail prices of all items in the basket.
	 * @return the sum of the retail prices of all items in the basket.
	 */
	public BigDecimal getTotalPrice() {
		return basket.getTotalPrice();
	}
	
	/**
	 * Returns the BasketEntry which contains the Product with the given bar code.
	 * @param barcode The Product's bar code by which to search the basket.
	 * @return the BasketEntry which contains the Product with the given bar code.
	 */
	public BasketEntry getByBarcode(int barcode) {
		return this.basket.getByBarcode(barcode);
	}
}
