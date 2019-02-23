
/*  
    Jessica Johnson
    CSCI 430
    24 February 2019
*/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;


public class Warehouse implements Serializable{
  private ProductList productList;
  private ManufacturerList manufacturerList;
  private ClientList clientList;
  private static Warehouse warehouse;
  
  private Warehouse() {
    
    clientList = ClientList.instance();
    productList = ProductList.instance();
    manufacturerList = ManufacturerList.instance();
  }
  
  public static Warehouse instance() {
    if (warehouse == null) {
      ClientIdServer.instance(); // instantiate all singletons
      ManufacturerIDServer.instance();
      return (warehouse = new Warehouse());
    } else {
      return warehouse;
    }
  }
  
   public Client addClient(String name, String address, String emailAddress) {
    Client client = new Client(name, address, emailAddress);
    if (clientList.insertClient(client)) {
      return (client);
    }
    return null;
  }
   
   public Iterator getClientList() {
      return clientList.getClients();
  }
   public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      ClientIdServer.retrieve(input);
      ManufacturerIDServer.retrieve(input);
      return warehouse;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return null;
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      return null;
    }
  }
   
   public Supplies assignProductToManufacturer(String productID, String manufacturerID, float price){
       boolean flag1 = false, flag2 = false;
       
       Product p1 = productList.searchProduct(productID);
       Manufacturer m1 = manufacturerList.searchManufacturer(manufacturerID);
	   Supplies supplies = new Supplies(m1, p1, price);
       
	   if (p1 != null && m1 != null)
	   {
			flag1 = p1.assignManufacturer(supplies);
			flag2 = m1.assignProduct(supplies);
	   }
       
       if (flag1 && flag2){
           return supplies;
       }
       else 
           return null;
   }
   
   
   public boolean unassignProductFromManufacturer(String productID, String manufacturerID) {
       Manufacturer m1;
       Product p1;
       boolean flag1 = false, flag2 = false;
       
       p1 = productList.searchProduct(productID);
       m1 = manufacturerList.searchManufacturer(manufacturerID);       
       
	   if (p1 != null && m1 != null)
	   {
			flag1 = p1.unassignManufacturer(productID, manufacturerID);
			flag2 = m1.unassignProduct(productID, manufacturerID);
	   }
       
       return (flag1 && flag2);
   }
   
   public Manufacturer addManufacturer (String name, String address) {
    Manufacturer manufacturer = new Manufacturer(name, address);
    if (manufacturerList.insertManufacturer(manufacturer)) {
      return (manufacturer);
    }
    return null;
  }
   
   public Iterator getManufacturerList() {
      return manufacturerList.getManufacturerList();
  }
   
   public Product addProduct(String name, String description, String ID) {
    Product product = new Product(name, description, ID);
    if (productList.insertProduct(product)) {
      return (product);
    }
    return null;
  }
   
   public Iterator getProductList() {
      return productList.getProducts();
  }
   
   public Iterator getProductSupplierList(String productID){
       Product p1 = productList.searchProduct(productID);
	   if (p1 != null)
	   {
			return(p1.getSuppliers());   
	   }
	   else
	   {
		   return null;
	   }
   }
   
   public Iterator getManufacturerProductList(String manufacturerID){
       Manufacturer m1 = manufacturerList.searchManufacturer(manufacturerID);
       if (m1 != null)
	   {
		   return(m1.getSuppliedProducts());
	   }
	   else
	   {
		   return null;
	   }
   }
   
   
	public static  boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("WarehouseData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(warehouse);
      output.writeObject(ClientIdServer.instance());
      output.writeObject(ManufacturerIDServer.instance());
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }

  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(warehouse);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  
  private void readObject(java.io.ObjectInputStream input) {
    try {
      input.defaultReadObject();
      if (warehouse == null) {
        warehouse = (Warehouse) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
   
    public String toString() {
    return productList + "\n" + manufacturerList + "\n" + clientList;
  }
}
