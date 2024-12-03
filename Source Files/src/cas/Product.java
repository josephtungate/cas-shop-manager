/**
*A Product contains basic information about an item of stock.
*@author Joseph Marcus Tungate
*/

package cas;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Product {
	private final int barcode;
	private final String brand;
	private final String colour;
	private final boolean isWired;
	private final BigDecimal originalCost;
	private final BigDecimal retailPrice;
	private int quantity;
	
	/**
	 * Creates a Product instance with all its attributes specified.
	 * @param barcode The product's bar code.
	 * @param brand The product's brand.
	 * @param colour The product's colour.
	 * @param isWired Whether the product is wired.
	 * @param originalCost The price at which the product was purchased.
	 * @param retailPrice The price at which the product is sold.
	 * @param quantity The quantity of the product in stock.
	 */
	public Product(int barcode, String brand, String colour, boolean isWired,
			BigDecimal originalCost, BigDecimal retailPrice, int quantity){
		
		
		//Verify the arguments.
		if(!verifyBarcode(barcode))
			throw new IllegalArgumentException("Invalid barcode.");
		if(!verifyBrand(brand)) 
			throw new IllegalArgumentException("Invalid brand.");
		if(!verifyColour(colour))
			throw new IllegalArgumentException("Invalid colour.");
		if(!verifyPrice(originalCost))
			throw new IllegalArgumentException("Invalid original cost.");
		if(!verifyPrice(retailPrice))
			throw new IllegalArgumentException("Invalid retail price.");
		if(!verifyQuantity(quantity))
			throw new IllegalArgumentException("Invalid quantity.");
		
		//Ensures the prices are to two decimal places.
		originalCost = originalCost.setScale(2, RoundingMode.HALF_UP);
		retailPrice = retailPrice.setScale(2, RoundingMode.HALF_UP);
		
		this.barcode = barcode;
		this.brand = brand;
		this.colour = colour;
		this.isWired = isWired;
		this.originalCost = originalCost;
		this.retailPrice = retailPrice;
		this.quantity = quantity;
	}
	
	//Getters and setters.
	/**
	 * Returns the product's bar code.
	 * @return The bar code of the product.
	 */
	public int getBarcode() {
		return this.barcode;
	}
	
	/**
	 * Returns the product's brand.
	 * @return The brand of the product.
	 */
	public String getBrand() {
		return this.brand;
	}
	
	/**
	 * Returns the product's colour.
	 * @return The colour of the product.
	 */
	public String getColour() {
		return this.colour;
	}
	
	/**
	 * Determines whether the product is wired.
	 * @return <code>true</code> if the product is wired.
	 */
	public boolean isWired() {
		return this.isWired;
	}
	
	/**
	 * Returns the price at which the product was bought.
	 * @return the product's original price.
	 */
	public BigDecimal getOriginalCost() {
		return this.originalCost;
	}
	
	/**
	 * Returns the price at which the product is sold.
	 * @return the product's retail price.
	 */
	public BigDecimal getRetailPrice() {
		return this.retailPrice;
	}
	
	/**
	 * Returns the quantity of this product which is in stock.
	 * @return the quantity of this product which is in stock.
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Updates the quantity of this product which is in stock.
	 * @param quantity The amount of this product which is in stock.
	 */
	public void setQuantity(int quantity) {
		if(!verifyQuantity(quantity))
			throw new IllegalArgumentException("Invalid quantity");
		
		this.quantity = quantity;
	}
	//End getters and setters.
	
	/**
	 * Returns a String representation of the product.
	 * @param showOriginalCost if <code>true</code> then 
	 * the product's original cost will be included in the String.
	 * @return A String representation of the product.
	 */
	public String toString(boolean showOriginalCost) {
		String str = "Bar code: " + getBarcode() + ", " + 
					 "Brand: " + getBrand() + ", " +
					 "Colour: " + getColour() + ", " + 
				     "Connection: " + 
					 (isWired() ? "wired" : "wireless") + ", " +
					 (showOriginalCost ? "Original Cost: " + getOriginalCost() + ", " : "") +
					 "Retail Price: " + getRetailPrice() + ", " +
					 "Quantity: " + getQuantity();
		
		return str;
	}
	
	/**
	 * Returns a string representation of the product omitting the product's
	 * original cost.
	 * @return A String representation of the product omitting the product's#
	 * original price.
	 */
	@Override
	public String toString() {
		return this.toString(false);
	}
	
	//Methods for verifying constructor arguments.
	/**
	 * Returns <code>true</code> if the provided int is a valid bar code.
	 * A valid bar code is an int value between 100,000 and 999,999 inclusive.
	 * @param barcode The bar code to be verified. 
	 * @return <code>true</code> if the bar code is valid.
	 * <code>false</code> otherwise.
	 */
	public static boolean verifyBarcode(int barcode) {
		//checks whether the bar code is a six digit number.
		return barcode >= 100_000 && barcode <= 999_999;
	}
	
	/**
	 * Returns <code>true</code> if the provided String is a valid brand.
	 * A valid brand is a non-empty String.
	 * @param brand The brand to be verified.
	 * @return <code>true</code> if the brand is valid.
	 * <code>false</code> otherwise.
	 */
	public static boolean verifyBrand(String brand) {
		return brand != null && brand.length() > 0;
	}
	
	/**
	 * Returns <code>true</code> if the provided String is a valid colour.
	 * A valid colour is a non-empty String.
	 * @param colour The colour to be verified.
	 * @return <code>true</code> if the colour is valid.
	 * <code>false</code> otherwise.
	 */
	public static boolean verifyColour(String colour) {
		return colour != null && colour.length() > 0;
	}
	
	/**
	 * Returns <code>true</code> if the provided BigDecimal is a valid price.
	 * A valid price is a BigDecimal greater than or equal to 0.
	 * @param price The price to be verified.
	 * @return <code>true</code> if the price is valid.
	 * <code>false</code> otherwise.
	 */
	public static boolean verifyPrice(BigDecimal price) {
		return price != null && price.compareTo(BigDecimal.valueOf(0)) >= 0;
	}
	
	/**
	 * Returns <code>true</code> if the provided int is a valid quantity.
	 * A valid quantity is an int greater than or equal to 0.
	 * @param quantity The quantity to be verified.
	 * @return <code>true</code> if the price is valid.
	 * <code>false</code> otherwise.
	 */
	public static boolean verifyQuantity(int quantity) {
		return quantity >= 0;
	}
	//End methods for verifying constructor arguments.
}
