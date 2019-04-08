import java.util.*;
import java.text.*;
import java.io.*;
public class ManagerState extends WarehouseState {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	private Security security;
	private static ManagerState instance;
	private static final int EXIT = 0;
	private static final int MODIFY_PRODUCT_SALE_PRICE = 1;
	private static final int ADD_MANUFACTURER = 2;
	private static final int CONNECT_PRODUCT_TO_MANUFACTURER = 3;
	private static final int CLERK_MENU = 4;
	private static final int HELP = 5;

	private ManagerState() {
		super();
		warehouse = Warehouse.instance();
		security = Security.instance();
	}

	public static ManagerState instance() {
		if (instance == null) {
			instance = new ManagerState();
		}
		return instance;
	}

	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer num = Integer.valueOf(item);
				return num.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	public float getFloat(String prompt)
	{
		do {
			try {
				String item = getToken(prompt);
				Float num = Float.valueOf(item);
				return num.floatValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(df.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command: " + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}

	public void help() {
		System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(MODIFY_PRODUCT_SALE_PRICE + " to modify the sale price of a product");
		System.out.println(ADD_MANUFACTURER + " to add a manufacturer");
		System.out.println(CONNECT_PRODUCT_TO_MANUFACTURER + " to connect a product to a manufacturer");
		System.out.println(CLERK_MENU + " to switch to the sales clerk menu");
		System.out.println(HELP + " for help");
	}

	public void modifyProductSalePrice() {
		String productID = getToken("Enter product UPC");
		float newPrice = getFloat("Enter new sale price");

		Product product = warehouse.searchProduct(productID);
		if (product != null) {
			if (confirmOperation())
			{
				product.setSalePrice(newPrice);
				System.out.println("\nProduct sale price successfully modified \n" + product.toString());	
			}
			else
			{
				System.out.println("\nPassword could not be verified, operation aborted.");
			}
		}
		else
		{
			System.out.println("\nProduct not found.");
		}	
	}

	public void addManufacturer()
	{
		String name = getToken("Enter manufacturer name");
		String address = getToken("Enter address");
		
		if (confirmOperation())
		{
			System.out.println("\nPassword verified, operation confirmed.");

			Manufacturer result = warehouse.addManufacturer(name, address);
			if (result == null) {
				System.out.println("Could not add manufacturer");
			}
			else
			{
				System.out.println(result);
			}
		}
		else
		{
			System.out.println("\nPassword could not be verified, operation aborted.");
		}
	}

	public void connectProductToManufacturer()
	{
		String productID = getToken("Enter product UPC");
		String manufacturerID = getToken("Enter manufacturer ID");
		float price = getFloat("Enter price");

		if (confirmOperation())
		{
			System.out.println("\nPassword verified, operation confirmed.");

			Supplies result = warehouse.assignProductToManufacturer(productID, manufacturerID, price);
			if (result == null) {
				System.out.println("Could not assign product to manufacturer");
			}
			else
			{
				System.out.println(result);
			}
		}
		else
		{
			System.out.println("\nPassword could not be verified, operation aborted.");
		}
	}

	public void clerkMenu()
	{
		if (confirmOperation())
		{
			context.changeState(WarehouseContext.SALESCLERK_MENU);
		}
		else
		{
			System.out.println("Password could not be verified, operation aborted.");
		}
	}

	private boolean confirmOperation()
	{
		return security.verifyPswd(getToken("\nEnter password to confirm operation"));
	}

	public void exit()
	{
		context.changeState(WarehouseContext.BACK);
	}

	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
				case MODIFY_PRODUCT_SALE_PRICE:
					modifyProductSalePrice();
					break;
				case ADD_MANUFACTURER:
					addManufacturer();
					break;
				case CONNECT_PRODUCT_TO_MANUFACTURER:
					connectProductToManufacturer();
					break;
				case CLERK_MENU:
					clerkMenu();
					break;
				case HELP:
					help();
					break;
			}
		}
		exit();
	}

	public void run() {
		process();
	}
}
