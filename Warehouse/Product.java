import java.util.*;
import java.lang.*;
import java.io.*;

public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String ID;
  private String name;
  private double price;
  private int qty;

  public Product(String ID, String name) {
    this.ID = ID;
	price = -1;
	qty = 0;
	this.name = name;
  }

  public void setPrice(double price) {
    this.price = price;
  }
	
  public double getPrice() {
    return price;
  }
	
  public String getProductID() {
    return ID;
  }
	
  public void setQuantity(int qty) {
    this.qty = qty;
  }
  
  public String toString() {
      return "Product ID " + ID + " name " + name + " quantity " + qty;
  }
}
