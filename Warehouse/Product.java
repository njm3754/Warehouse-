import java.util.*;
import java.lang.*;
import java.io.*;

public class Product implements Serializable {
  private static final long serialVersionUID = 1L;
  private String ID;
  private String name;
  private String description; 
  private List<Supplies> assignedManufacturers = new LinkedList<Supplies>();

  public Product(String ID, String name, String description) {
    this.ID = ID;
	this.name = name;
	this.description = description;
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
  
  public Iterator getSuppliers()
  {
	  return assignedManufacturers.iterator();
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
      return "Product ID " + ID + " Name " + name + " Description " + description;
  }
}
