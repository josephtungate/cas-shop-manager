/**
 * An Inventory contains a sequence of products as well as methods for manipulating this sequence.
 * @author Joseph Marcus Tungate
 */
package cas;
import java.util.ArrayList;

public class Inventory {
	private ArrayList<Product> products;
	
	/**
	 * Creates an empty inventory.
	 */
	public Inventory() {
		products = new ArrayList<Product>();
	}
	
	/**
	 * Returns the product at the given index.
	 * @param index The position of the product within the sequence.
	 * @return The product at the given index.
	 */
	public Product get(int index) {
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException(index + "is out of bounds.");
		
		return this.products.get(index);
	}
	
	/**
	 * Replaces the product at the given index with the given product.
	 * @param index The position of the product within the sequence.
	 * @param product The new product.
	 */
	private void set(int index, Product product) {
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException(index + "is out of bounds.");
		this.products.set(index, product);
	}
	
	/**
	 * Adds the product to the end of the inventory.
	 * @param product The product to add to the inventory.
	 */
	public boolean add(Product product) {
		boolean isAdded = false;
		
		if(product == null)
			throw new IllegalArgumentException("null is not a valid Product");
		if(getByBarcode(product.getBarcode()) == null) {
			this.products.add(product);
			isAdded = true;
		}
		
		return isAdded;
	}
	
	/**
	 * Returns the number of products in the inventory.
	 * @return The number of products in the inventory.
	 */
	public int size() {
		return this.products.size();
	}
	
	/**
	 * Removes the product at the given index from the inventory.
	 * @param index The index of the item to remove.
	 */
	public final void remove(int index) {
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException(index + "is out of bounds.");
		this.products.remove(index);
	}
	/**
	 * Filters the Inventory such that it contains only products of a given
	 * brand and keyboard layout.
	 * @param productBrand The brand of products to keep in the Inventory.
	 * @param onlyUKLayout If true, then any Keyboards in the filtered
	 * Inventory will have a UK layout. This parameter does not affect other Product
	 * subclasses.
	 */
	
	public void filter(String productBrand, boolean onlyUKLayout) {
		if(productBrand == null) {
			throw new IllegalArgumentException("Product brand is invalid.");
		} else {
			ArrayList<Product> filteredProducts = new ArrayList<Product>();
			
			for(int i = 0; i < size(); i++) {
				Product p = get(i);
				
				if(productBrand.length() == 0 || p.getBrand().contentEquals(productBrand)) {
					if(onlyUKLayout && p instanceof Keyboard) {
						if(((Keyboard)p).getLayout() == KeyboardLayout.UK)
							filteredProducts.add(p);
					}else {
						filteredProducts.add(p);
					}
				}
			}
			this.products = filteredProducts;
		}
	}
	
	/**
	 * Sorts the products in this Inventory by the quantity in stock of each product
	 * in descending order.
	 */
	public void sortByQuantity() {
		//Sort by insertion sort.
		for(int i = 0; i < size() - 1; i++) {
			int indexOfMax = i;
			
			for(int j = i + 1; j < size(); j++) {
				if(get(indexOfMax).getQuantity() < get(j).getQuantity())
					indexOfMax = j;
			}
			
			Product temp = get(i);
			set(i, get(indexOfMax));
			set(indexOfMax, temp);
		}
	}
	
	/**
	 * Sorts the products in this Inventory by their barcode in descending order.
	 */
	public void sortByBarcode() {
		//Sort by insertion sort.
		for(int i = 0; i < size() - 1; i++) {
			int indexOfMax = i;
			
			for(int j = i + 1; j < size(); j++) {
				if(get(indexOfMax).getBarcode() < get(j).getBarcode())
					indexOfMax = j;
			}
			
			Product temp = get(i);
			set(i, get(indexOfMax));
			set(indexOfMax, temp);
		}
	}
	
	/**
	 * Searches the Inventory for a Product with the given barcode and returns that Product
	 * if it exists. Returns null if it cannot find the Product.
	 * @param barcode The barcode of the Product which is to be returned.
	 * @return The Product in this Inventory that has the given barcode.
	 * Returns null if such a Product doesn't exist.
	 */
	public Product getByBarcode(int barcode) {
		Product p = null;
		
		for(int i = 0; i < size(); i++) {
			if(get(i).getBarcode() == barcode) {
				p = get(i);
				break;
			}
		}
		
		return p;
	}
}