/**
 * Manages the reading and writing of files containing data about
 * user accounts, stock, and records of system activity.
 * @author Joseph Marcus Tungate
 */
package cas;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Database {
	private final String stockFilePath;
	private final String userAccountsPath;
	private final String activityLogPath;
	private ArrayList<User> users;
	
	/**
	 * Creates a Database which reads from and writes to the files
	 * given by the provided file paths.
	 * @param stockFilePath File path of the stock file.
	 * @param userAccountsPath File path of the user accounts file.
	 * @param activityLogPath File path of the activity log file.
	 * @throws IOException Thrown if an IOException occurs when reading
	 * the files located at the given file paths. 
	 */
	public Database(String stockFilePath, String userAccountsPath,
			String activityLogPath) throws IOException {
		
		if(stockFilePath == null || userAccountsPath == null ||
				activityLogPath == null)
			throw new IllegalArgumentException("File path(s) is null");
		
		this.stockFilePath = stockFilePath;
		this.userAccountsPath = userAccountsPath;
		this.activityLogPath = activityLogPath;
		
		this.users = readUsers();
	}
	
	//Methods for handling the Stock file.
	/**
	 * Returns an Inventory object containing
	 * the products read from the stock file.
	 * @return the products reads from the stock file.
	 * @throws IOException Thrown if there is a problem reading the stock file.
	 */
	public Inventory getProducts() throws IOException {
		return readProducts();
	}
	
	/**
	 * Reads the stock file, interprets its contents as Product objects,
	 * and stores these Product objects in an Inventory object which it
	 * returns.
	 * @return An Inventory object containing Products read from the stock file. 
	 * @throws IOException Thrown if there is a problem reading the
	 * stock file.
	 */
	private Inventory readProducts() throws IOException{
		try(BufferedReader br = 
				new BufferedReader(new FileReader(this.stockFilePath))){
			
			Inventory products = new Inventory();
			String currentLine;
			
			//Keeps processing lines from the file until no lines are left.
			while((currentLine = br.readLine()) != null) {
				//If a line cannot be parsed, it should be skipped and the process should continue.
				try {
					products.add(productFromString(currentLine));
				}
				catch(IllegalArgumentException | NullPointerException e) {
					System.err.println("Line: " + currentLine + " could not be parsed as a product.\n"
							+ e.getMessage());
				}
			}
			
			return products;
		}
	}
	
	/**
	 * Writes the contents of the given Inventory object to
	 * the stock file, overwriting its existing contents. The 
	 * file will be written to in a way that makes it readable by
	 * Database objects.
	 * @throws IOException Thrown if the stock file cannot be written to.
	 */
	public void writeProducts(Inventory products) throws IOException{
		try(BufferedWriter br = new BufferedWriter(new FileWriter(stockFilePath))){
			
			for(int i = 0; i < products.size(); i++) {
				br.write(stringFromProduct(products.get(i)));
			}
		}
	}
	
	/**
	 * Returns a Product instance as specified by the given String.
	 * The String must be in database format.
	 * @param str The string from which to create a Product.
	 * @return The Product parsed from the String.
	 */
	private Product productFromString(String str) {
		final int BARCODE = 0;
		final int DEVICECLASS = 1;
		final int DEVICETYPE = 2;
		final int BRAND = 3;
		final int COLOUR = 4;
		final int CONNECTIVITY = 5;
		final int QUANTITY = 6;
		final int ORIGINALCOST = 7;
		final int RETAILPRICE = 8;
		final int ADDITIONALINFO = 9;
		
		try {
			if (str == null)
				throw new IllegalArgumentException();
			
			String[] attributes = str.split(", ");
			if (attributes.length != 10)
				throw new IllegalArgumentException("The given string, " + str + 
						" is in an invalid format");
			
			Product parsedProduct;
			
			//First, parse all data that isn't tied to a particular device class.
			int barcode = Integer.parseInt(attributes[BARCODE]);
			String brand = attributes[BRAND];
			String colour = attributes[COLOUR];
			
			boolean isWired;
			if(attributes[CONNECTIVITY].equals("wired"))
				isWired = true;
			else if(attributes[CONNECTIVITY].equals("wireless"))
				isWired = false;
			else
				throw new IllegalArgumentException(attributes[CONNECTIVITY] +
						" cannot be parsed as a boolean");
			
			int quantity = Integer.parseInt(attributes[QUANTITY]);
			BigDecimal originalCost = new BigDecimal(attributes[ORIGINALCOST]);
			BigDecimal retailPrice = new BigDecimal(attributes[RETAILPRICE]);
			
			//Now parse device class specific data and create the Product.
			if(attributes[DEVICECLASS].equals("keyboard")) {
				KeyboardType type = KeyboardType.valueOf(
						attributes[DEVICETYPE].toUpperCase());
				KeyboardLayout layout = KeyboardLayout.valueOf(
						attributes[ADDITIONALINFO].toUpperCase());
				
				parsedProduct = new Keyboard(barcode, brand, colour, isWired,
						type, layout, originalCost, retailPrice, quantity);
			}
			else if(attributes[DEVICECLASS].equals("mouse")) {
				MouseType type = MouseType.valueOf(
						attributes[DEVICETYPE].toUpperCase());
				int buttonCount = Integer.parseInt(attributes[ADDITIONALINFO]);
				
				parsedProduct = new Mouse(barcode, brand, colour, isWired,
						type, buttonCount, originalCost, retailPrice, quantity);
			}
			else
				throw new IllegalArgumentException("Device class cannot be parsed");
			
			return parsedProduct;
		}
		catch(NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Returns a correctly formatted String representation of the 
	 * given product. This String can be written to a stock file
	 * and then read by readProducts.
	 * @param product The product from which to create a string.
	 * @return A properly formatted String representation
	 * of the given product.
	 */
	private final String stringFromProduct(Product product) {
		String productStr;
		
		if(product == null)
			throw new IllegalArgumentException("Product argument is null");
		
		productStr = Integer.toString(product.getBarcode()) + ", ";
		
		if(product instanceof Keyboard) {
			productStr += "keyboard, " +
				((Keyboard)product).getType().toString().toLowerCase() +
				", ";
		}
		else if(product instanceof Mouse) {
			productStr += "mouse, " +
				((Mouse)product).getType().toString().toLowerCase() +
				", ";
		}
		
		productStr += product.getBrand() + ", " + 
				product.getColour() + ", ";
		
		productStr += product.isWired() ? "wired, " : "wireless, ";
		productStr += product.getQuantity() + ", " +
				product.getOriginalCost() + ", " +
				product.getRetailPrice() + ", ";
		
		if(product instanceof Keyboard) {
			productStr += ((Keyboard)product).getLayout().toString();
		}
		
		else if(product instanceof Mouse) {
			productStr += ((Mouse)product).getButtonCount();
		}
		
		productStr += "\n";
		
		return productStr;
	}

	//Methods for handling the UserAccounts file.
	/**
	 * Returns a reference to an ArrayList object containing Users
	 * read from the UserAccounts file. Modifying the returned
	 * ArrayList will affect the database's internal ArrayList.
	 * @return An ArrayList object containing Users read from the
	 * UserAccounts file.
	 */
	public ArrayList<User> getUsers() {
		return this.users;
	}
	
	/**
	 * Interprets the UserAccounts file as a series of User objects and
	 * stores these User objects in an ArrayList which becomes the value
	 * of the users attribute.
	 * @throws IOException Thrown if the UserAccounts file cannot be read.
	 */
	private final ArrayList<User> readUsers() throws IOException {
		ArrayList<User> users = new ArrayList<User>();
		
		try(BufferedReader br = 
				new BufferedReader(new FileReader(userAccountsPath))) {
			String currentLine;
			
			//Keep reading the file until no lines remain.
			while((currentLine = br.readLine()) != null) {
				users.add(userFromString(currentLine));
			}
			
			return users;
		}
		
	}
	
	/**
	 * Given a correctly formatted String, parses the String to create
	 * a User object.
	 * @param str The String from which to create the User.
	 * @return The User object as specified by the String.
	 */
	private final User userFromString(String str) {
		final int ID = 0;
		final int USERNAME = 1;
		final int SURNAME = 2;
		final int HOUSENO = 3;
		final int POSTCODE = 4;
		final int CITY = 5;
		final int ROLE = 6;
		
		User parsedUser;
		
		if(str == null)
			throw new IllegalArgumentException("Given String is null");
		
		String[] attributes = str.split(", ");
		if(attributes.length != 7)
			throw new IllegalArgumentException("String is not formatted properly");
		
		int id = Integer.parseInt(attributes[ID]);
		String username = attributes[USERNAME];
		String surname = attributes[SURNAME];
		int houseno = Integer.parseInt(attributes[HOUSENO]);
		String postcode = attributes[POSTCODE];
		String city = attributes[CITY];
		
		if(attributes[ROLE].equals("admin")){
			parsedUser = new Admin(id, username, surname, houseno, city, postcode);
		}
		else if(attributes[ROLE].equals("customer")) {
			parsedUser = new Customer(id, username, surname, houseno, city, postcode);
		}
		else {
			throw new IllegalArgumentException("Invalid role");
		}
		
		return parsedUser;
	}

	//Methods for handling the ActivityLog file
	/**
	 * Writes the given ActivityLog to the ActivityLog file.
	 * @param log The ActivityLog to be written to the ActivityLog file.
	 * @throws IOException Thrown if the ActivityLog file cannot be written to.
	 */
	public void writeActivityLog(ActivityLog log) throws IOException {
		try(BufferedWriter br =
				new BufferedWriter(new FileWriter(activityLogPath, true))){
			br.append(log.toString() + "\n");
		}
	}
}
