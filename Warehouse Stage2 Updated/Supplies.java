import java.util.*;
import java.io.*;

public class Supplies implements Serializable {
	private static final long serialVersionUID = 1L;
	private Manufacturer manufacturer; 
	private Product product;
	private float price; 
	
	public Supplies(Manufacturer manufacturer, Product product, float price) {
		this.manufacturer = manufacturer;
		this.product = product;
		this.price = price;
	}
	
	public Manufacturer getManufacturer() {
		return manufacturer;
	}
	public Product getProduct() {
		return product;
	}
	public float getPrice() {
		return price;
	}
	
	public String toString() {
	    return  manufacturer.toString() +  "\n" + product.toString() + "\nPrice " + String.format("%.2f", price);
	  }
	  
}