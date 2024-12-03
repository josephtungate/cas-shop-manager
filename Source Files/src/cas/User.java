/**
 * Contains types of information common to all users of the system.
 * @author Joseph Marcus Tungate
 */
package cas;

public abstract class User {
	private final int id;
	private final String username;
	private final String surname;
	private final Address address;
	
	/**
	 * Creates a User instance with all its attributes specified.
	 * @param id The user's id.
	 * @param username The user's user name.
	 * @param surname The user's surname.
	 * @param houseNumber The user's house number.
	 * @param city The user's city.
	 * @param postcode The user's post code.
	 */
	public User(int id, String username, String surname, int houseNumber,
			String city, String postcode) {
		
		//Verify the arguments.
		if(!verifyId(id))
			throw new IllegalArgumentException("Invalid id");
		if(!verifyUsername(username))
			throw new IllegalArgumentException("Invalid username");
		if(!verifySurname(surname))
			throw new IllegalArgumentException("Invalid surname");
		
		this.id = id;
		this.username = username;
		this.surname = surname;
		address = new Address(houseNumber, city, postcode);
	}
	
	//Getters and setters.
	/**
	 * Returns the user's id.
	 * @return The user's id.
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Returns the user's user name.
	 * @return the user's user name.
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Returns the user's surname.
	 * @return the user's surname.
	 */
	public String getSurname() {
		return this.surname;
	}
	
	/**
	 * Returns the user's house number.
	 * @return The user's house number.
	 */
	public int getHouseNumber() {
		return this.address.getHouseNumber();
	}
	
	/**
	 * Returns the user's city.
	 * @return the user's city.
	 */
	public String getCity() {
		return this.address.getCity();
	}
	
	/**
	 * Returns the user's post code.
	 * @return the user's post code.
	 */
	public String getPostcode() {
		return this.address.getPostcode();
	}
	//End getters and setters.
	
	/**
	 * Returns a String representation of the user.
	 * @return a String representation of the user.
	 */
	@Override public String toString() {
		return "ID: " + getId() + ", " + 
			   "Username: " + getUsername() + ", " +
			   "Surname: " + getSurname() + ", " +
			   "House Number: " + getHouseNumber() + ", " +
			   "City: " + getCity() + ", " + 
			   "Postcode: " + getPostcode();
	}
	
	//Constructor verification methods.
	/**
	 * Returns <code>true</code> if the supplied int is a valid id.
	 * @param id The int to verify.
	 * @return <code>true</code> if the int is valid.
	 * <code>false</code> otherwise.
	 */
	public static boolean verifyId(int id) {
		return id > 100 && id < 999;
	}
	
	/**
	 * Returns <code>true</code> if the provided String is a valid user name.
	 * @param username The user name to be verified. 
	 * @return <code>true</code> if the user name is valid.
	 * <code>false</code> otherwise.
	 */
	public static boolean verifyUsername(String username) {
		return username != null && username.length() > 0;
	}

	/**
	 * Returns <code>true</code> if the provided String is a valid surname.
	 * @param surname The surname to be verified. 
	 * @return <code>true</code> if the surname is valid.
	 * <code>false</code> otherwise.
	 */
	public static boolean verifySurname(String surname) {
		return surname != null && surname.length() > 0;
	}
	//End constructor verification methods.
	
	/**
	 * Contains the address of a user.
	 * @author Joseph Marcus Tungate.
	 */
	private class Address{
		private final int houseNumber;
		private final String city;
		private final String postcode;
		
		/**
		 * Creates an Address instance with all its attributes specified.
		 * @param houseNumber The house number of the address.
		 * @param city The city of the address.
		 * @param postcode The post code of the address.
		 */
		public Address(int houseNumber, String city, String postcode) {
			
			//Verify the arguments.
			if(!verifyHouseNumber(houseNumber))
				throw new IllegalArgumentException("Invalid house number.");
			if(!verifyCity(city))
				throw new IllegalArgumentException("Invalid city.");
			if(!verifyPostcode(postcode))
				throw new IllegalArgumentException("Invalid postcode.");

			this.houseNumber = houseNumber;
			this.city = city;
			this.postcode = postcode;
		}
		
		//Getters and setters.
		/**
		 * Returns the house number of the address.
		 * @return the house number of the address.
		 */
		public int getHouseNumber() {
			return this.houseNumber;
		}
		
		/**
		 * Returns the city of the address.
		 * @return the city of the address.
		 */
		public String getCity() {
			return this.city;
		}
		
		/**
		 * Returns the post code of the address.
		 * @return the post code of the address.
		 */
		public String getPostcode() {
			return this.postcode;
		}
		//End getters and setters.
		
		//Constructor verification methods.
		/**
		 * Returns <code>true</code> if the provided int is a valid house number.
		 * A valid house number is an int value greater than 0.
		 * @param houseNumber the house number to be verified. 
		 * @return <code>true</code> if the house number is valid.
		 * <code>false</code> otherwise.
		 */
		public boolean verifyHouseNumber(int houseNumber) {
			return houseNumber > 0;
		}
		
		/**
		 * Returns <code>true</code> if the provided String is a valid post code.
		 * A valid post code is a String at least 4 characters long.
		 * @param postcode The post code to be verified. 
		 * @return <code>true</code> if the post code is valid.
		 * <code>false</code> otherwise.
		 */
		public boolean verifyPostcode(String postcode) {
			return postcode != null && postcode.length() >= 4;
		}
		
		/**
		 * Returns <code>true</code> if the provided String is a valid city.
		 * A valid city is a String with a length greater than 0.
		 * @param city The city to be verified. 
		 * @return <code>true</code> if the city is valid.
		 * <code>false</code> otherwise.
		 */
		public boolean verifyCity(String city) {
			return city != null && city.length() > 0;
		}
		//End constructor verification methods.
	}
}
