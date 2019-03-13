
public class WaitListItem {
 
	private Client client;
	private OrderItem orderitem;
	
	public WaitListItem(Client client, OrderItem orderitem) {
		
		this.client = client;
		this.orderitem = orderitem; 
		
	}
	
	public Client getclient() {
		return client;
	}
	
	public OrderItem getorderitem() {
		return orderitem;
	}
	
	public String toString() {
		return client.toString() + " " + orderitem.toString();
		
	}
}
