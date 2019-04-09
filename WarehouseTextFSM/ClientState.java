import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

public class ClientState extends WarehouseState {

    private static ClientState clientState;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private static final int EXIT = 0;
    private static final int SHOW_ACCOUNT = 1;
    private static final int SHOW_PRODUCTS = 2;
    private static final int PLACE_ORDER = 3;
    private static final int ACCEPT_PAYMENT = 4;
    private static final int SHOW_CLIENT_WAITLISTED_ORDERS = 5;
    private static final int CHECK_PRODUCT_PRICE = 6;
    private static final int HELP = 7;

    private ClientState() {
        super();
        warehouse = Warehouse.instance();
    }

    public static ClientState instance() {
        if (clientState == null) {
            return clientState = new ClientState();
        } else {
            return clientState;
        }
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
                int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
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
        System.out.println(SHOW_ACCOUNT + " to show a client account");
        System.out.println(SHOW_PRODUCTS + " to show a list of products");
        System.out.println(PLACE_ORDER + " to place an order");
        System.out.println(ACCEPT_PAYMENT + " to enter a payment");
        System.out.println(SHOW_CLIENT_WAITLISTED_ORDERS + " to show a list of waitlisted orders for a client");
        System.out.println(CHECK_PRODUCT_PRICE + " to check the price of a product");
        System.out.println(HELP + " for help");
    }

    public void showAccount() {
        Iterator clientList = warehouse.getClientList();
        while (clientList.hasNext()) {
            Client client = (Client) (clientList.next());
            if (client.getId().equals(context.getUser())) {
                System.out.println(client.toString());
            }
        }
    }

    public void placeOrder() {
        Order order = warehouse.createOrder(context.getUser());

        if (order != null) {
            //Populate Order
            do {
                String productID = getToken("Enter product ID");
                Product product = warehouse.searchProduct(productID);

                if (product != null) {
                    int quantity = getNumber("Enter quantity");
                    if (quantity > 0) {
                        order.addOrderItem(product, quantity);
                    }
                }
            } while (yesOrNo("Add more products to order?"));

            //Process Order
            if (!order.isEmpty()) {
                System.out.println("\nOrder placed.");

                Invoice invoice = warehouse.addOrder(order);
                String invoiceString = invoice.toString();
                if (invoiceString != "") {
                    System.out.println("\nProducts available to ship:\n");
                    System.out.println(invoiceString);
                }
            }
        }
    }

    public void showProducts() {
        Iterator productList = warehouse.getProductList();
        while (productList.hasNext()) {
            Product product = (Product) (productList.next());
            System.out.println(product.toString());
        }
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

    public void acceptPayment() {
        float paymentAmount = getFloat("Enter payment amount");
        float newAccountBalance = warehouse.makePayment(context.getUser(), paymentAmount);
        System.out.println("Payment accepted. New Account Balance: " + newAccountBalance);
    }

    public void showClientWaitlistedOrders() {
        Iterator clientWaitlist = warehouse.getClientWaitlist(context.getUser());
        if (clientWaitlist != null) {
            while (clientWaitlist.hasNext()) {
                WaitlistItem waitlistItem = (WaitlistItem) (clientWaitlist.next());
                System.out.println(waitlistItem.toString() + "\n");
            }
        }
    }

    public void checkProductPrice() {
        String productID = getToken("Enter product UPC");
        boolean found = false;

        Iterator productList = warehouse.getProductList();
        while (productList.hasNext()) {
            Product product = (Product) (productList.next());
            if (product.getProductID().equals(productID)) {
                found = true;
                System.out.println("The Product price is: $" + String.format("%.2f", product.getSalePrice()));
            }
        }
        if (!found) {
            System.out.println("Product could not be found.");
        }
    }

    public void exit() {
        if (context.getLogin() == 0||context.getLogin()==1){
        context.changeState(WarehouseContext.LOGOUT);
        }
        else 
        context.changeState(WarehouseContext.BACK);
    }

    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                case SHOW_ACCOUNT:
                    showAccount();
                    break;
                case SHOW_PRODUCTS:
                    showProducts();
                    break;
                case PLACE_ORDER:
                    placeOrder();
                    break;
                case ACCEPT_PAYMENT:
                    acceptPayment();
                    break;
                case SHOW_CLIENT_WAITLISTED_ORDERS:
                    showClientWaitlistedOrders();
                    break;
                case CHECK_PRODUCT_PRICE:
                    checkProductPrice();
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
