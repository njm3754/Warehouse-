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
	
  public boolean assignManufacturer(Supplies supplies) {
	  return assignedManufacturers.add(supplies);	 
  }
 public boolean unassignManufacturer(Supplies supplies) {
	  return assignedManufacturers.remove(supplies);
	 
  }
  public String toString() {
      return "Product ID " + ID + " Name " + name + " Description " + description;
  }
}
