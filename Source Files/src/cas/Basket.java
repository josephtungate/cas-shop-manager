/**
 * A Basket contains a user curated list of BasketEntries
 * and methods for manipulating these BasketEntries.
 * @author Joseph Marcus Tungate.
 */

package cas;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Basket {
	private ArrayList<BasketEntry> contents;
	
	/**
	 * Creates an empty Basket.
	 */
	public Basket() {
		this.contents = new ArrayList<BasketEntry>();
	}
	
	/**
	 * Returns the BasketEntry at the specified index.
	 * @param index The position in the basket of the BasketEntry to be returned.
	 * @return the BasketEntry at the specified index.
	 */
	public BasketEntry get(int index) {
		if(index >= size() || index < 0)
			throw new IllegalArgumentException("Index is out of bounds.");
		else
			return this.contents.get(index);
	}

	/**
	 * Sets the element in contents at position index to the given BasketEntry.
	 * @param index The position of the element in contents to set to entry.
	 * @param entry The BasketEntry to add to contents.
	 */
	private void set(int index, BasketEntry entry) {
		if(index < 0 || index >= size())
			throw new IllegalArgumentException("Index is out of bounds.");
		if(entry == null)
			throw new IllegalArgumentException("Entry is null");
		
		this.contents.set(index, entry);
	}
	
	/**
	 * Returns the number of BasketEntry objects in the basket.
	 * @return the number of BasketEntry objects in the basket.
	 */
	public int size() {
		return this.contents.size();
	}
	
	/**
	 * Adds the given Product object to the basket as a BasketEntry.
	 * @param product The Product to add to the basket.
	 */
	public void add(Product product) {
		if(product == null)
			throw new IllegalArgumentException("Product is null.");
		
		/* If product is already represented in the basket by a BasketEntry,
		 * then increment that BasketEntry's quantity. Otherwise, add product
		 * to the basket as a new BasketEntry.
		 */
		for(int i = 0; i < size(); i++) {
			BasketEntry b = get(i);
			if(product.getBarcode() == b.getProduct().getBarcode()) {
				b.setQuantity(b.getQuantity() + 1);
				return;
			}
		}
		this.contents.add(new BasketEntry(product, 1));		
	}

	/**
	 * Decreases the quantity of the BasketEntry at the given index.
	 * If the quantity is 1, then the BasketEntry is removed from the basket.
	 * @param index The position of the BasketEntry in the basket that is
	 * to be 'removed'.
	 */
	public void remove(int index) {
		if(index >= size() || index < 0)
			throw new IllegalArgumentException("Index is out of bounds.");
		
		BasketEntry b = get(index);
		
		if(b.getQuantity() <= 1)
			contents.remove(index);
		else
			b.setQuantity(b.getQuantity() - 1);
	}
	
	/**
	 * Removes the BasketEntry at the given index from the basket.
	 * @param index The position of the BasketEntry to remove.
	 */
	public void removeAll(int index) {
		this.contents.remove(index);
	}
	
	/**
	 * Sorts the contents of the basket by their quantity in descending order.
	 */
	public void sortByQuantity() {
		//Sort by insertion sort.
		for(int i = 0; i < size() - 1; i++) {
			int indexOfMax = i;
			
			for(int j = i + 1; j < size(); j++) {
				if(get(indexOfMax).getQuantity() < get(j).getQuantity())
					indexOfMax = j;
			}
			
			BasketEntry temp = get(i);
			set(i, get(indexOfMax));
			set(indexOfMax, temp);
		}
	}
	
	/**
	 * Returns true if all members of the Basket exist in the given Inventory
	 * and their quantities do not exceed the quantity in stock.
	 * @param stock The Inventory against which the Basket is validated.
	 * @return true if the Basket is valid against stock. Returns false otherwise.
	 */
	public boolean validate(Inventory stock) {
		boolean isValid = true;
		
		for(int i = 0; i < size() && isValid; i++) {
			isValid = isValid && validateBasketEntry(get(i), stock);
		}
		
		return isValid;
	}
	
	/**
	 * Returns true if the Product stored in entry also exists in stock 
	 * and the quantity of said does not exceed the quantity in stock
	 * as specified by stock. 
	 * @param entry The BasketEntry to validate against stock.
	 * @param stock The Inventory to validate entry against.
	 * @return true if entry is valid. Returns false otherwise.
	 */
	private boolean validateBasketEntry(BasketEntry entry, Inventory stock) {
		boolean isValid = false;
		
		for(int i = 0; i < stock.size(); i++) {
			if(stock.get(i).getBarcode() == entry.getProduct().getBarcode()) {
				if(stock.get(i).getQuantity() >= entry.getQuantity()) {
					isValid = true;
					break;
				}
			}
		}
		
		return isValid;
	}

	/**
	 * Returns the sum of the retail prices of all items in the basket.
	 * @return the sum of the retail prices of all items in the basket.
	 */
	public BigDecimal getTotalPrice() {
		BigDecimal totalPrice = new BigDecimal(0);
		
		for(int i = 0; i < size(); i++) {
			//Retail price of Product * quantity of Product in basket.
			BigDecimal price = get(i).getProduct().getRetailPrice().multiply(new BigDecimal(get(i).getQuantity()));
			totalPrice = totalPrice.add(price);
		}
		
		return totalPrice;
	}

	/**
	 * Returns the BasketEntry which contains the Product with the given bar code.
	 * @param barcode The Product's bar code by which to search the Basket.
	 * @return the BasketEntry which contains the Product with the given bar code.
	 * Returns null if the Basket does not contain a Product with the given bar code.
	 */
	public BasketEntry getByBarcode(int barcode) {
		BasketEntry entry = null;
		
		for(int i = 0; i < size() && entry == null; i++) {
			if(get(i).getProduct().getBarcode() == barcode)
				entry = get(i);
		}
		
		return entry;
	}
}


