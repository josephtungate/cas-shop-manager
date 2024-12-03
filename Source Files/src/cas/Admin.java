/**
 * Admin contains information about a system admin.
 * An admin has access to different system functionalities than other user subtypes.
 * @author Joseph Marcus Tungate
 */
package cas;

public class Admin extends User {
	/**
	 * Creates an Admin instance with all its attributes specified.
	 * @param id The admin's id number.
	 * @param username The admin's username.
	 * @param surname The admin's surname.
	 * @param houseNumber The admin's house number.
	 * @param city The admin's city.
	 * @param postcode The admin's postcode.
	 */
	public Admin(int id, String username, String surname, int houseNumber,
			String city, String postcode) {
		super(id, username, surname, houseNumber, city, postcode);
	}
}
