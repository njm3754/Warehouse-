
public class Supplies {
	
	private Manufacturer manufacturer; 
	private Product product;
	private float price; 
	
	private Supplies(Manufacturer manufacturer, Product product, float price) {
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
	    return  manufacturer.toString() +  product.toString() + " Price " + price;
	  }
	  
}
