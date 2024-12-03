/**
 * A Mouse contains all the information about a mouse product.
 * @author Joseph Marcus Tungate
 */

package cas;
import java.math.BigDecimal;

public class Mouse extends Product {
	private final MouseType type;
	private final int buttonCount;
	
	/**
	 * Creates a Mouse instance with all its attributes specified.
	 * @param barcode The product's bar code.
	 * @param brand The product's brand.
	 * @param colour The product's colour.
	 * @param isWired Whether the product is wired.
	 * @param type The type of the mouse.
	 * @param buttonCount The number of buttons on the mouse.
	 * @param originalCost The price at which the product was purchased.
	 * @param retailPrice The price at which the product is sold.
	 * @param quantity The quantity of the product in stock.
	 */
	public Mouse(int barcode, String brand, String colour, boolean isWired,
			MouseType type, int buttonCount, BigDecimal originalCost,
			BigDecimal retailPrice, int quantity) {
		super(barcode, brand, colour, isWired, originalCost, retailPrice, quantity);
		
		if(!verifyButtonCount(buttonCount))
			throw new IllegalArgumentException("Invalid button count");
		
		if(!verifyType(type))
			throw new IllegalArgumentException("Invalid MouseType");
		
		this.type = type;
		this.buttonCount = buttonCount;
	}
	
	
	//Getters and setters.
	/**
	 * Returns the type of the mouse.
	 * @return The type of the mouse.
	 */
	public MouseType getType() {
		return this.type;
	}
	
	/**
	 * Return the number of buttons on the mouse.
	 * @return The number of buttons on the mouse.
	 */
	public int getButtonCount() {
		return this.buttonCount;
	}
	//End getters and setters.
	
	/**
	 * Returns a String representing the mouse.
	 * @param showOriginalCost If <code>true</code> then the original cost
	 * of the mouse is included in the String. Otherwise it is omitted.
	 * @return A String representing the mouse.
	 */
	@Override 
	public String toString(boolean showOriginalCost) {
		String str = "Bar code: " + getBarcode() + ", " + 
				 "Brand: " + getBrand() + ", " +
				 "Colour: " + getColour() + ", " + 
			     "Connection: " + 
				 (isWired() ? "wired" : "wireless") + ", " +
				 (showOriginalCost ? "Original Cost: " + getOriginalCost() + ", " : "") +
				 "Type: " + getType() + ", " +
				 "Button Count: " + getButtonCount() + ", " +
				 "Retail Price: " + getRetailPrice() + ", " +
				 "Quantity: " + getQuantity();
	
		return str;
	}
	
	/**
	 * Returns a String representing the mouse, omitting the mouse's 
	 * original cost.
	 * @return A String representing the mouse.
	 */
	@Override public String toString() {
		return toString(false);
	}
	
	//Methods for verifying constructor arguments.
	/**
	 * Returns <code>true</code> if the provided int is a valid button count.
	 * A valid button count is greater than zero.
	 * @param buttonCount The button count to be validated.
	 * @return <code>true</code> if the button count is valid.
	 * <code>false</code> otherwise.
	 */
	public boolean verifyButtonCount(int buttonCount) {
		return buttonCount > 0;
	}
	
	/**
	 * Returns <code>true</code> if the provided MouseType is a valid type.
	 * @param type The type to validated.
	 * @return <code>true</code> if the type is valid.
	 * <code>false</code> otherwise.
	 */
	public boolean verifyType(MouseType type) {
		return type != null;
	}
	//End methods for verifying constructor arguments.
}
