/**
 * A Keyboard contains all the information about a keyboard product.
 * @author Joseph Marcus Tungate
 */
package cas;
import java.math.BigDecimal;

public class Keyboard extends Product {

	private final KeyboardType type;
	private final KeyboardLayout layout;
	
	/**
	 * Creates a Keyboard instance with all its attributes specified.
	 * @param barcode The product's bar code.
	 * @param brand The product's brand.
	 * @param colour The product's colour.
	 * @param isWired Whether the product is wired.
	 * @param type The type of the keyboard.
	 * @param layout The layout of the keyboard.
	 * @param originalCost The price at which the product was purchased.
	 * @param retailPrice The price at which the product is sold.
	 * @param quantity The quantity of the product in stock.
	 */
	public Keyboard(int barcode, String brand, String colour, boolean isWired,
			KeyboardType type, KeyboardLayout layout, BigDecimal originalCost,
			BigDecimal retailPrice, int quantity) {
		super(barcode, brand, colour, isWired, originalCost, retailPrice, quantity);
		
		if(!verifyType(type))
			throw new IllegalArgumentException("Invalid type");
		if(!verifyLayout(layout))
			throw new IllegalArgumentException("Invalid layout");
		
		this.type = type;
		this.layout = layout;
	}
	
	//Getters and setters
	/**
	 * Returns the keyboard's type.
	 * @return The type of the keyboard.
	 */
	public KeyboardType getType() {
		return this.type;
	}
	
	/**
	 * Returns the keyboard's layout.
	 * @return The layout of the keyboard.
	 */
	public KeyboardLayout getLayout() {
		return this.layout;
	}
	//End getters and setters
	
	/**
	 * Returns a String representation of the keyboard.
	 * @param showOriginalCost if <code>true</code> then 
	 * the product's original cost will be included in the String.
	 * @return A String representation of the product.
	 */
	@Override
	public String toString(boolean showOriginalCost) {
		String str = "Bar code: " + getBarcode() + ", " + 
				 "Brand: " + getBrand() + ", " +
				 "Colour: " + getColour() + ", " + 
			     "Connection: " + 
				 (isWired() ? "wired" : "wireless") + ", " +
			     "Type: " + getType() + ", " +
				 "Layout: " + getLayout() + ", " +
				 (showOriginalCost ? "Original Cost: " + getOriginalCost() + ", " : "") +
				 "Retail Price: " + getRetailPrice() + ", " +
				 "Quantity: " + getQuantity();
	
	return str;	
	}
	
	/**
	 * Returns a string representation of the keyboard omitting the product's
	 * original price.
	 * @return A String representation of the keyboard omitting the product's
	 * original price.
	 */
	@Override
	public String toString() {
		return this.toString(false);
	}
	
	//Constructor verification methods.
	/**
	 * Returns <code>true</code> if the type is valid.
	 * @param type The type to be verified.
	 * @return <code>true</code> if the type is valid.
	 * <code>false</code> otherwise.
	 */
	public static boolean verifyType(KeyboardType type) {
		return type != null;
	}
	/**
	 * Returns <code>true</code> if the layout is valid.
	 * @param layout The KeyboardLayout to be verified.
	 * @return <code>true</code> if the layout is valid.
	 * <code>false</code> otherwise.
	 */
	public static boolean verifyLayout(KeyboardLayout layout) {
		return layout != null;
	}
	//End constructor verification methods.
}
