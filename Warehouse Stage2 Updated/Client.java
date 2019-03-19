import java.util.*;
import java.io.*;

public class Client implements Serializable{
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String emailAddress;
  private String id;
  private Account account;
  private List<Transaction> transactions = new LinkedList<Transaction>();
  private List<Order> orders = new LinkedList<Order>();
  private List<Invoice> invoices = new LinkedList<Invoice>();
  private List<WaitlistItem> orderWaitlist = new LinkedList<WaitlistItem>();
  
  private static final String CLIENT_STRING = "C";
    
  public  Client (String name, String address, String emailAddress) {
    this.name = name;
    this.address = address;
    this.emailAddress = emailAddress;
    id = CLIENT_STRING + (ClientIdServer.instance()).getId();
	account = new Account();
  }

  public String getName() {
    return name;
  }
  
  public String getemailAddress() {
    return emailAddress;
  }
  
  public String getAddress() {
    return address;
  }
  
  public String getId() {
    return id;
  }
  
  public void setName(String newName) {
    name = newName;
  }
  
  public void setAddress(String newAddress) {
    address = newAddress;
  }
  
  public void setPhone(String newemailAddress) {
    emailAddress = newemailAddress;
  }
  
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  
  public String toString() {
    String string = "Client ID: " + id + "\n Name: " + name + "\n Address: " + address + "\n Email Address: " + emailAddress + "\n Account Balance: " + account.getBalance();
    return string;
  }
  
  public boolean addTransactionToList(Transaction transaction){
      return transactions.add(transaction);
  }
  
  public Transaction createTransaction(String description, float dollarAmount){
      Transaction transaction = new Transaction(description, dollarAmount);
      return transaction;
  }  
  
  public Iterator getTransactions()
  {
      return transactions.iterator();
  }
  
  public Iterator getOrders()
  {
      return orders.iterator();
  }
  
  public boolean addOrderToList(Order order){
      return orders.add(order);
  }
  
  public Iterator getInvoices()
  {
      return invoices.iterator();
  }
  
  public Invoice createInvoice(Client client){
      Invoice invoice = new Invoice(client);
      return invoice;
  }
 
  public Iterator getOrderWaitlist()
  {
      return orderWaitlist.iterator();
  }
  
  public WaitlistItem createWaitlistItem(Client client, Product product, int quantityUnavailable){
     WaitlistItem waitlistItem = new WaitlistItem(client, product, quantityUnavailable); 
     return waitlistItem;
  }
  
  public boolean addWaitlistItem(WaitlistItem waitlistItem){
      return orderWaitlist.add(waitlistItem);
  }
  
  public boolean removeWaitlistItem(WaitlistItem waitlistItem){
      return orderWaitlist.remove(waitlistItem);
  }
  
  public boolean hasOutstandingBalance(){
      return account.hasOutstandingBalance();
  }
  
  public boolean getInformation(OrderItem orderItem, Invoice invoice, Client client){
      Product product = orderItem.getProduct();
      float salePrice = product.getSalePrice();
      int quantity = orderItem.getQuantity();
      int stock = product.getStockCount();
	  int quantityAvailable = quantityAvailable(quantity,stock);
      OrderItem orderItem2;
      
	  if (quantityAvailable > 0)
	  {
		orderItem2 = createOrderItem(product, quantityAvailable, salePrice);
		addOrderItem(orderItem2, invoice);  
	  }
      
      if (quantity > stock){
          WaitlistItem waitlistItem = createWaitlistItem(client, product,(quantity-stock));
          addWaitlistItem(waitlistItem);
		  product.addWaitlistItem(waitlistItem);
        }
            
      return true;
  }
  
 private int quantityAvailable(int quantity, int stock){
     if (quantity <= stock)
     return quantity;
     else 
         return stock;
 }
 
 private OrderItem createOrderItem(Product product, int quantity, float salePrice){
     OrderItem orderItem = new OrderItem(product, quantity, salePrice);
     return orderItem;
 } 
 
 private boolean addOrderItem(OrderItem orderItem, Invoice invoice){
     return invoice.addOrderItem(orderItem);
 }
  
public Iterator getOrderItems(Order order){
    return order.getOrderItems();
}

 public Invoice populateInvoice(Order order, Invoice invoice, Client client){
    Iterator orderItems = order.getOrderItems();
    while (orderItems.hasNext()) {
        OrderItem orderItem = (OrderItem)(orderItems.next());
        getInformation(orderItem,invoice,client);
    }
    return invoice;
}

 public float getOrderTotal(Order order){
     return order.getOrderTotal();
 }
 
 public float chargeAccount(float orderTotal){
     return account.chargeAccount(orderTotal);
 }
 
 public float makePayment(float paymentAmount){
     return account.payment(paymentAmount);
 }

 public Invoice addOrder(Order order){
     Client client = order.getClient();
     Invoice invoice = createInvoice(client);
     addOrderToList(order);
     
     invoice = populateInvoice(order, invoice, client);
	 chargeAccount(order.getOrderTotal());
     
     String description = order.toString();
     Transaction transaction = createTransaction( description, getOrderTotal(order));
     addTransactionToList(transaction);
     
     return invoice;
 }
 
 public float getAccountBalance()
 {
     return account.getBalance();
 }
}
