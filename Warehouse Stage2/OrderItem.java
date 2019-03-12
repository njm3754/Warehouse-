import java.io.*;
public class OrderItem implements Serializable  {
	private static final long serialVersionUID = 1L;
	private Product product;
	private int quantity;
	private float salePrice;

	public OrderItem(Product product, int quantity, float salePrice)
	{
		this.product = product;
		this.quantity = quantity;
		this.salePrice = salePrice;
	}

	public Product getProduct()
	{
		return product;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public float getSalePrice()
	{
		return salePrice;
	}

	public String toString() {
		return product.toString() + " | Quantity: " + quantity + " | Sale Price: " + salePrice;
	}
}