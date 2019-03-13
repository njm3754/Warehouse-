public class WaitlistItem {
 
	private Client client;
	private Product product;
	private int quantity;
	
	public WaitlistItem(Client client, Product product, int quantity) {
		
		this.client = client;
		this.product = product; 
		this.quantity = quantity;
		
	}
	
	public Client getClient() {
		return client;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	public void decrementQuantity(int quantity) {
		this.quantity -= quantity;
	}
	
	public String toString() {
		return client.toString() + " " + product.toString() + " Quantity: " + quantity;
		
	}
}