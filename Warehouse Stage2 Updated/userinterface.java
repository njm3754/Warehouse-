import java.util.*;
import java.text.*;
import java.io.*;
public class userinterface {
	private static userinterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Warehouse warehouse;
	private static final int EXIT = 0;
	private static final int ADD_CLIENT = 1;
	private static final int ADD_PRODUCTS = 2;
	private static final int ADD_MANUFACTURER = 3;
	private static final int ASSIGN_PRODUCT_TO_MANUFACTURER = 4;
	private static final int UNASSIGN_PRODUCT_TO_MANUFACTURER = 5;
	private static final int SHOW_CLIENTS = 6;
	private static final int SHOW_MANUFACTURERS = 7;
	private static final int SHOW_PRODUCTS = 8;
	private static final int SHOW_PRODUCT_SUPPLIERS = 9;
	private static final int SHOW_MANUFACTURER_PRODUCTS = 10;
	private static final int PLACE_ORDER = 11;
	private static final int ACCEPT_PAYMENT = 12;
	private static final int SHOW_OUTSTANDING_BALANCE_CLIENTS = 13;
	private static final int SHOW_PRODUCT_WAITLISTED_ORDERS = 14;
	private static final int SHOW_CLIENT_WAITLISTED_ORDERS = 15;
	private static final int RECEIVE_SHIPMENT = 16;
	private static final int SAVE = 17;
	private static final int RETRIEVE = 18;
	private static final int HELP = 19;

	private userinterface()
	{
		if (yesOrNo("Look for saved data and  use it?")) {
			retrieve();
		} else {
			warehouse = Warehouse.instance();
		}
	}

	public static userinterface instance()
	{
		if (userInterface == null) {
			return userInterface = new userinterface();
		} else {
			return userInterface;
		}
	}

	public String getToken(String prompt)
	{
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

	private boolean yesOrNo(String prompt)
	{
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
			return true;
	}

	public int getNumber(String prompt)
	{
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

	public Calendar getDate(String prompt)
	{
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

	public int getCommand()
	{
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

	public void help()
	{
		System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ADD_CLIENT + " to add a client");
		System.out.println(ADD_PRODUCTS + " to add products");
		System.out.println(ADD_MANUFACTURER + " to add a manufacturer");
		System.out.println(ASSIGN_PRODUCT_TO_MANUFACTURER + " to assign a product to a manufacturer");
		System.out.println(UNASSIGN_PRODUCT_TO_MANUFACTURER + " to unassign a product to a manufacturer");
		System.out.println(SHOW_CLIENTS + " to show a list of clients");
		System.out.println(SHOW_MANUFACTURERS + " to show a list of manufacturers");
		System.out.println(SHOW_PRODUCTS + " to show a list of products");
		System.out.println(SHOW_PRODUCT_SUPPLIERS + " to show a list of a product's suppliers");
		System.out.println(SHOW_MANUFACTURER_PRODUCTS + " to show a list of a manufacturer's supplied products");
		System.out.println(PLACE_ORDER + " to place an order");
		System.out.println(ACCEPT_PAYMENT + " accept a client payment");
		System.out.println(SHOW_OUTSTANDING_BALANCE_CLIENTS + " to show a list of clients with an outstanding balance");
		System.out.println(SHOW_PRODUCT_WAITLISTED_ORDERS + " to show a list of waitlisted orders for a product");
		System.out.println(SHOW_CLIENT_WAITLISTED_ORDERS + " to show a list of waitlisted orders for a client");
		System.out.println(RECEIVE_SHIPMENT + " to receive a shipment");
		System.out.println(SAVE + " to save data");
		System.out.println(RETRIEVE + " to retrieve");
		System.out.println(HELP + " for help");
	}

	public void addClient()
	{
		String name = getToken("Enter client name");
		String address = getToken("Enter address");
		String emailAddress = getToken("Enter email address");
		Client result = warehouse.addClient(name, address, emailAddress);
		if (result == null) {
			System.out.println("Could not add client");
		}
		else
		{
			System.out.println(result);
		}
	}

	public void addProducts()
	{
		do {
			String productID = getToken("Enter product UPC");
			String name = getToken("Enter name");
			String description = getToken("Enter description");
			float salePrice = getFloat("Enter sale price");

			Product result = warehouse.addProduct(productID, name, description, salePrice);
			if (result != null) {
				System.out.println(result.toString());
			} else {
				System.out.println("Product could not be added");
			}
			if (!yesOrNo("Add more products?")) {
				break;
			}
		} while (true);
	}

	public void addManufacturer()
	{
		String name = getToken("Enter manufacturer name");
		String address = getToken("Enter address");
		Manufacturer result = warehouse.addManufacturer(name, address);
		if (result == null) {
			System.out.println("Could not add manufacturer");
		}
		else
		{
			System.out.println(result);
		}
	}

	public void assignProductToManufacturer()
	{
		String productID = getToken("Enter product UPC");
		String manufacturerID = getToken("Enter manufacturer ID");
		float price = getFloat("Enter price");
		Supplies result = warehouse.assignProductToManufacturer(productID, manufacturerID, price);
		if (result == null) {
			System.out.println("Could not assign product to manufacturer");
		}
		else
		{
			System.out.println(result);
		}
	}

	public void unassignProductFromManufacturer()
	{
		String productID = getToken("Enter product UPC");
		String manufacturerID = getToken("Enter manufacturer ID");
		boolean isUnassigned = warehouse.unassignProductFromManufacturer(productID, manufacturerID);
		if (!isUnassigned) {
			System.out.println("Could not unassign product from manufacturer");
		}
		else
		{
			System.out.println("Product succesfully unassigned from manufacturer");
		}		
	}

	public void showClients()
	{
		Iterator clientList = warehouse.getClientList();
		while (clientList.hasNext()){
			Client client = (Client)(clientList.next());
			System.out.println(client.toString());
		}
	}

	public void showManufacturers()
	{
		Iterator manufacturerList = warehouse.getManufacturerList();
		while (manufacturerList.hasNext()){
			Manufacturer manufacturer = (Manufacturer)(manufacturerList.next());
			System.out.println(manufacturer.toString());
		}
	}

	public void showProducts()
	{
		Iterator productList = warehouse.getProductList();
		while (productList.hasNext()){
			Product product = (Product)(productList.next());
			System.out.println(product.toString());
		}
	}

	public void showProductSuppliers()
	{
		String productID = getToken("Enter product ID");
		Iterator productSupplierList = warehouse.getProductSupplierList(productID);
		if (productSupplierList != null)
		{
			while (productSupplierList.hasNext()){
				Supplies supplies = (Supplies)(productSupplierList.next());
				System.out.println(supplies.toString());
			}	
		}
		else
		{
			System.out.println("Product not found");
		}
	}

	public void showManufacturerProducts()
	{
		String manufacturerID = getToken("Enter manufacturer ID");
		Iterator manufacturerProductList = warehouse.getManufacturerProductList(manufacturerID);
		if (manufacturerProductList != null)
		{
			while (manufacturerProductList.hasNext()){
				Supplies supplies = (Supplies)(manufacturerProductList.next());
				System.out.println(supplies.toString());
			}	
		}
		else
		{
			System.out.println("Manufacturer not found");
		}
	}

	public void placeOrder()
	{
		String clientID = getToken("Enter client ID");
		Order order = warehouse.createOrder(clientID);

		if (order != null)
		{
			//Populate Order
			do
			{
				String productID = getToken("Enter product ID");
				Product product = warehouse.searchProduct(productID);

				if (product != null)
				{
					int quantity = getNumber("Enter quantity");
					if (quantity > 0)
					{
						order.addOrderItem(product, quantity);
					}
				}
			}
			while (yesOrNo("Add more products to order?"));

			//Process Order
			if (!order.isEmpty())
			{
				System.out.println("Order placed.");
				Invoice invoice = warehouse.addOrder(order);
				System.out.println(invoice.toString());
			}
		}
	}

	public void acceptPayment()
	{
		String clientID = getToken("Enter client ID");
		float paymentAmount = getFloat("Enter payment amount");
		float newAccountBalance = warehouse.makePayment(clientID, paymentAmount);
		System.out.println("Payment accepted. New Account Balance: " + newAccountBalance);
	}

	public void showOutstandingBalanceClients()
	{
		Iterator clientList = warehouse.getOutstandingBalanceClientsList();
		while (clientList.hasNext()){
			Client client = (Client)(clientList.next());
			float accountBalance = client.getAccountBalance();
			System.out.println(client.toString());
		}
	}

	public void showProductWaitlistedOrders()
	{
		String productID = getToken("Enter product ID");
		Iterator productWaitlist = warehouse.getProductWaitlist(productID);
		if (productWaitlist != null)
		{
			while (productWaitlist.hasNext()){
				WaitlistItem waitlistItem = (WaitlistItem)(productWaitlist.next());
				System.out.println(waitlistItem.toString() + "\n");
			}	
		}
	}

	public void showClientWaitlistedOrders()
	{
		String clientID = getToken("Enter client ID");
		Iterator clientWaitlist = warehouse.getClientWaitlist(clientID);
		if (clientWaitlist != null)
		{
			while (clientWaitlist.hasNext()){
				WaitlistItem waitlistItem = (WaitlistItem)(clientWaitlist.next());
				System.out.println(waitlistItem.toString() + "\n");
			}	
		}
	}

	public void receiveShipment()
	{
		String productID = getToken("Enter product ID");
		int quantity = getNumber("Enter quantity");
		Iterator invoices = warehouse.receiveShipment(productID, quantity);

		if (invoices != null)
		{
			System.out.println("Shipment received.");
			while (invoices.hasNext())
			{
				System.out.println("Wait listed order filled: ");
				Invoice invoice = (Invoice)(invoices.next());
				System.out.println("\n" + invoice.toString() + "\n");
			}	
		}
		else
		{
			System.out.println("A problem occurred. Shipment was not received.");
		}
	}

	private void save()
	{
		if (warehouse.save()) {
			System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
		} else {
			System.out.println(" There has been an error in saving \n" );
		}
	}

	private void retrieve()
	{
		try {
			Warehouse tempwarehouse = warehouse.retrieve();
			if (tempwarehouse != null) {
				System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n" );
				warehouse = tempwarehouse;
			} else {
				System.out.println("File doesnt exist; creating new warehouse" );
				warehouse = warehouse.instance();
			}
		} catch(Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
				case ADD_CLIENT:
					addClient();
					break;
				case ADD_PRODUCTS:
					addProducts();
					break;
				case ADD_MANUFACTURER:
					addManufacturer();
					break;
				case ASSIGN_PRODUCT_TO_MANUFACTURER:
					assignProductToManufacturer();
					break;
				case UNASSIGN_PRODUCT_TO_MANUFACTURER:
					unassignProductFromManufacturer();
					break;
				case SHOW_CLIENTS:
					showClients();
					break;
				case SHOW_MANUFACTURERS:
					showManufacturers();
					break;
				case SHOW_PRODUCTS:
					showProducts();
					break;
				case SHOW_PRODUCT_SUPPLIERS: 
					showProductSuppliers();
					break;
				case SHOW_MANUFACTURER_PRODUCTS:
					showManufacturerProducts();
					break;
				case PLACE_ORDER:
					placeOrder();
					break;
				case ACCEPT_PAYMENT:
					acceptPayment();
					break;
				case SHOW_OUTSTANDING_BALANCE_CLIENTS:
					showOutstandingBalanceClients();
					break;
				case SHOW_PRODUCT_WAITLISTED_ORDERS:
					showProductWaitlistedOrders();
					break;
				case SHOW_CLIENT_WAITLISTED_ORDERS:
					showClientWaitlistedOrders();
					break;
				case RECEIVE_SHIPMENT:
					receiveShipment();
					break;
				case SAVE:
					save();
					break;
				case RETRIEVE:
					retrieve();
					break; 		
				case HELP:
					help();
					break;
				}
		}
	}

	public static void main(String[] s) {
		userinterface.instance().process();
	}
}
