import java.util.*;
import java.io.*;
public class Order implements Serializable  {
	private static final long serialVersionUID = 1L;
	private String orderID;
	private float orderTotal;
	private Client client;
	private static final String ORDER_STRING = "O";
	private List<OrderItem> orderItems = new LinkedList<OrderItem>();

	public Order(Client client) 
	{
		orderID = ORDER_STRING + OrderIDServer.instance().getID();
		this.client = client;
		orderTotal = 0;
	}

	public String getOrderID()
	{
		return orderID;
	}

	public float getOrderTotal()
	{
		return orderTotal;
	}

	public Client getClient()
	{
		return client;
	}

	public Iterator getOrderItems()
	{
		return orderItems.iterator();
	}

	public OrderItem addOrderItem(Product product, int quantity)
	{
		float salePrice = product.getSalePrice();
		OrderItem orderItem = new OrderItem(product, quantity, salePrice);
		orderItems.add(orderItem);

		System.out.println("Sale price: " + salePrice + " quantity: " + quantity);
		orderTotal += salePrice * quantity;

		return orderItem;
	}

	public boolean isEmpty()
	{
		return orderItems.size() == 0;
	}

	public String toString() {
		return "Order ID: " + orderID + "\n" + orderItems.toString();
	}
}
