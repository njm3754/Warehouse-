import java.util.*;
import java.io.*;

public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String ID;
  private String name;
  private String description;
  private float saleprice; 
  private int stockCount = 0; 
  private List<Supplies> assignedManufacturers = new LinkedList<Supplies>();
  private List<WaitlistItem> productWaitlist = new LinkedList<WaitlistItem>();

  public Product(String ID, String name, String description, float saleprice) {
    this.ID = ID;
	this.name = name;
	this.description = description;
	this.saleprice = saleprice;
  }
	
  public String getProductID() {
    return ID;
  }

  public String getName() {
    return name;
  }
	
  public String getDescription() {
    return description;
  }
  public float getSalePrice() {
	  return saleprice;
  }
  
  public Iterator<WaitlistItem> getProductWaitlist()
  {
	  return productWaitlist.iterator();
  }
  
  public boolean addWaitlistItem(WaitlistItem waitlistItem)
  {
	  return productWaitlist.add(waitlistItem);
  }

  public int removeStock(int quantity)
  {
    if (stockCount - quantity > 0) {
      stockCount -= quantity;
    }
    else {
      stockCount = 0;
    }

    return stockCount;
  }
  
  public Iterator  addStock(int stockCount1) {
      Iterator<WaitlistItem> WaitlistedItems = getProductWaitlist(); 
      WaitlistItem waitlistitem;
      List<Invoice> listinvoices = new LinkedList<Invoice>();
      
      while(stockCount1!=0 && WaitlistedItems.hasNext() ) {
    	  waitlistitem = WaitlistedItems.next();
    	  int x = waitlistitem.getQuantity();
    	  
    	
    	  if(x <= stockCount1) {
    		  stockCount1 = stockCount1 - x;
    		  Invoice invoice = new Invoice(waitlistitem.getClient());
    		  OrderItem orderitem = new OrderItem(this, x, saleprice);
    		  invoice.addOrderItem(orderitem);
    		  listinvoices.add(invoice);
    		  WaitlistedItems.remove();
    		  waitlistitem.getClient().removeWaitlistItem(waitlistitem);
    		  
    		  

    	  }
    	  else {
    		  /*Copy of Object gets changed not the actual object*/
    		  waitlistitem.decrementQuantity(stockCount1);
    		  Invoice invoice = new Invoice(waitlistitem.getClient());
    		  OrderItem orderitem = new OrderItem(this, stockCount1, saleprice);
    		  invoice.addOrderItem(orderitem);
    		  listinvoices.add(invoice);
    		  stockCount1 = 0;

    	  	}


      }
      stockCount = stockCount + stockCount1;
      return listinvoices.iterator();
  }
  public Iterator getSuppliers()
  {
	  return assignedManufacturers.iterator();
  }
  
  public int getStockCount()
  {
	  return stockCount;
  }
	
  public boolean assignManufacturer(Supplies supplies) {
	  return assignedManufacturers.add(supplies);	 
  }
  public boolean unassignManufacturer(String productID, String manufacturerID) {
	  Iterator suppliesIterator = assignedManufacturers.iterator();

		while (suppliesIterator.hasNext())
		{
			Supplies supplies = (Supplies)(suppliesIterator.next());
			Manufacturer manufacturer = supplies.getManufacturer();
			Product product = supplies.getProduct();
			if (product.getProductID().equals(productID) && manufacturer.getManufacturerID().equals(manufacturerID))
			{
				return assignedManufacturers.remove(supplies);
			}
		}

		return false;
	 
  }
  
  public String toString() {
      return "Product ID: " + ID + "\n Name: " + name + "\n Description: " + description + "\n In Stock: " + stockCount + "\n Sale Price: " + String.format("%.2f", saleprice);
  }


}