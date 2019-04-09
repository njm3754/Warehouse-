import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

public class ClerkState extends WarehouseState {

    private static Warehouse warehouse;
    private static ClerkState instance;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final int EXIT = 0;
    private static final int ADD_CLIENT = 1;
    private static final int ADD_PRODUCTS = 2;
    private static final int SHOW_CLIENTS = 3;
    private static final int SHOW_MANUFACTURERS = 4;
    private static final int SHOW_PRODUCTS = 5;
    private static final int SHOW_PRODUCT_SUPPLIERS = 6;
    private static final int SHOW_MANUFACTURER_PRODUCTS = 7;
    private static final int SHOW_OUTSTANDING_BALANCE_CLIENTS = 8;
    private static final int SHOW_PRODUCT_WAITLISTED_ORDERS = 9;
    private static final int SHOW_CLIENT_WAITLISTED_ORDERS = 10;
    private static final int RECEIVE_SHIPMENT = 11;
    private static final int CLIENT_MENU = 12;
    private static final int HELP = 13;

    private ClerkState() {
        super();
        warehouse = Warehouse.instance();
    }

    public static ClerkState instance() {
        if (instance == null) {
            instance = new ClerkState();
        }
        return instance;
    }

    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
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

    public float getFloat(String prompt) {
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
        System.out.println(ADD_CLIENT + " to add a client");
        System.out.println(ADD_PRODUCTS + " to add products");
        System.out.println(SHOW_CLIENTS + " to show a list of clients");
        System.out.println(SHOW_MANUFACTURERS + " to show a list of manufacturers");
        System.out.println(SHOW_PRODUCTS + " to show a list of products");
        System.out.println(SHOW_PRODUCT_SUPPLIERS + " to show a list of a product's suppliers");
        System.out.println(SHOW_MANUFACTURER_PRODUCTS + " to show a list of a manufacturer's supplied products");
        System.out.println(SHOW_OUTSTANDING_BALANCE_CLIENTS + " to show a list of clients with an outstanding balance");
        System.out.println(SHOW_PRODUCT_WAITLISTED_ORDERS + " to show a list of waitlisted orders for a product");
        System.out.println(SHOW_CLIENT_WAITLISTED_ORDERS + " to show a list of waitlisted orders for a client");
        System.out.println(RECEIVE_SHIPMENT + " to receive a shipment");
        System.out.println(CLIENT_MENU + " to go to the client menu");
        System.out.println(HELP + " for help");
    }

    public void addClient() {
        String name = getToken("Enter client name");
        String address = getToken("Enter address");
        String emailAddress = getToken("Enter email address");
        Client result = warehouse.addClient(name, address, emailAddress);
        if (result == null) {
            System.out.println("Could not add client");
        } else {
            System.out.println(result);
        }
    }

    public void addProducts() {
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

    public void showClients() {
        Iterator clientList = warehouse.getClientList();
        while (clientList.hasNext()) {
            Client client = (Client) (clientList.next());
            System.out.println(client.toString());
        }
    }

    public void showManufacturers() {
        Iterator manufacturerList = warehouse.getManufacturerList();
        while (manufacturerList.hasNext()) {
            Manufacturer manufacturer = (Manufacturer) (manufacturerList.next());
            System.out.println(manufacturer.toString());
        }
    }

    public void showProducts() {
        Iterator productList = warehouse.getProductList();
        while (productList.hasNext()) {
            Product product = (Product) (productList.next());
            System.out.println(product.toString());
        }
    }

    public void showProductSuppliers() {
        String productID = getToken("Enter product ID");
        Iterator productSupplierList = warehouse.getProductSupplierList(productID);
        if (productSupplierList != null) {
            while (productSupplierList.hasNext()) {
                Supplies supplies = (Supplies) (productSupplierList.next());
                System.out.println(supplies.toString());
            }
        } else {
            System.out.println("Product not found");
        }
    }

    public void showManufacturerProducts() {
        String manufacturerID = getToken("Enter manufacturer ID");
        Iterator manufacturerProductList = warehouse.getManufacturerProductList(manufacturerID);
        if (manufacturerProductList != null) {
            while (manufacturerProductList.hasNext()) {
                Supplies supplies = (Supplies) (manufacturerProductList.next());
                System.out.println(supplies.toString());
            }
        } else {
            System.out.println("Manufacturer not found");
        }
    }

    public void acceptPayment() {
        String clientID = getToken("Enter client ID");
        float paymentAmount = getFloat("Enter payment amount");
        float newAccountBalance = warehouse.makePayment(clientID, paymentAmount);
        System.out.println("Payment accepted. New Account Balance: " + newAccountBalance);
    }

    public void showOutstandingBalanceClients() {
        Iterator clientList = warehouse.getOutstandingBalanceClientsList();
        while (clientList.hasNext()) {
            Client client = (Client) (clientList.next());
            float accountBalance = client.getAccountBalance();
            System.out.println(client.toString());
        }
    }

    public void showProductWaitlistedOrders() {
        String productID = getToken("Enter product ID");
        Iterator productWaitlist = warehouse.getProductWaitlist(productID);
        if (productWaitlist != null) {
            while (productWaitlist.hasNext()) {
                WaitlistItem waitlistItem = (WaitlistItem) (productWaitlist.next());
                System.out.println(waitlistItem.toString() + "\n");
            }
        }
    }

    public void showClientWaitlistedOrders() {
        String clientID = getToken("Enter client ID");
        Iterator clientWaitlist = warehouse.getClientWaitlist(clientID);
        if (clientWaitlist != null) {
            while (clientWaitlist.hasNext()) {
                WaitlistItem waitlistItem = (WaitlistItem) (clientWaitlist.next());
                System.out.println(waitlistItem.toString() + "\n");
            }
        }
    }

    public void receiveShipment() {
        String productID = getToken("Enter product ID");
        int quantity = getNumber("Enter quantity");
        Iterator invoices = warehouse.receiveShipment(productID, quantity);

        if (invoices != null) {
            System.out.println("\nShipment received.");
            while (invoices.hasNext()) {
                System.out.println("\nWait listed order filled: ");
                Invoice invoice = (Invoice) (invoices.next());
                System.out.println("\n" + invoice.toString() + "\n");
            }
        } else {
            System.out.println("A problem occurred. Shipment was not received.");
        }
    }

    public void clientMenu() {
        String clientID = getToken("Enter client ID");
        if (findClient(clientID)) {
            context.setUser(clientID);
            context.changeState(WarehouseContext.CLIENT_MENU);
            
        } else {
            System.out.println("Client ID could not be verified, operation aborted.");
        }
    }

    private boolean findClient(String clientID) {
        Iterator clientList = warehouse.getClientList();
        while (clientList.hasNext()) {
            Client client = (Client) (clientList.next());
            if (client.getId().equals(clientID)) {
                return true;
            }
        }
        return false;
    }


    public void exit() {
        if (context.getLogin() == 0) {
            context.changeState(WarehouseContext.LOGOUT);
        } else
            context.changeState(WarehouseContext.BACK);
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
                case CLIENT_MENU:
                    clientMenu();
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
