/**
 * Holds information about a system activity which occurred during runtime,
 * such as the user checking out, cancelling, and saving their basket.
 * @author Joseph Marcus Tungate
 */
package cas;

import java.util.Date;
import java.text.SimpleDateFormat;

public class ActivityLog{
	
	private final String log;
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	
	/**
	 * Creates an ActivityLog instance logging a system activity as described
	 * by the arguments.
	 * @param user The User involved in the activity.
	 * @param product The Product involved in the activity.
	 * @param quantity The amount of the specified Product involved
	 * in the activity.
	 * @param status A description of the activity.
	 */
	public ActivityLog(User user, Product product, int quantity, String status) {
		this.log = user.getId() + ", " + 
				   user.getPostcode() + ", " +
				   product.getBarcode() + ", " +
				   product.getRetailPrice() + ", " +
				   quantity + ", " + 
				   status + ", " + 
				   formatter.format(new Date());
	}
	
	/**
	 * Creates an ActivityLog instance logging an activity as described
	 * by the arguments.
	 * @param user The User involved in the activity.
	 * @param product The Product involved in the activity.
	 * @param quantity The amount of the specified Product involved
	 * in the activity.
	 * @param status A description of the activity.
	 * @param paymentType The payment type involved in the activity.
	 */
	public ActivityLog(User user, Product product, int quantity, String status, String paymentType) {
		this.log = user.getId() + ", " + 
				   user.getPostcode() + ", " +
				   product.getBarcode() + ", " + 
				   product.getRetailPrice() + ", " +
				   quantity + ", " + 
				   status + ", " + 
				   paymentType + ", " + 
				   formatter.format(new Date());
	}
	
	/**
	 * Returns a String representation of the ActivityLog.
	 * Can be used for writing an ActivityLog to a file.
	 * @return A String representation of the ActivityLog.
	 */
	public String toString() {
		return this.log;
	}
}