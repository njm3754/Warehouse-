
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
import warehouse.ClientList;
import warehouse.ClientIdServer;
import warehouse.Client;
import warehouse.Supplies;
import warehouse.Product;
import warehouse.ProductList;
import warehouse.Manufacturer;
import warehouse.ManufacturerList;


public class Warehouse implements Serializable{
  private ProductList productList;
  private ManufacturerList manufacturerList;
  private ClientList clientList;
  private static Warehouse warehouse;
  
  private Warehouse() {
    
    clientList = ClientList.instance();
    productList = ProductList.instance();
    manufacturerList = ManufacturerList.intance();
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
   
   public Iterator getClients() {
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
       Supplies supplies = new Supplies(productID, manufacturerID, price);
       boolean flag1, flag2;
       
       p1 = searchProduct(productID);
       m1 = searchManufacturer(manufacturerID);
       
       flag1 = p1.unassignProduct(supplies);
       flag2 = m1.unassignManufacturer(supplies);
       
       if (flag1 && flag2){
           return supplies;
       }
       else 
           return null;
   }
   
   
   public boolean unassignProductFromManufacturer(String productID, String manufacturerID) {
       manufacturer m1;
       product p1;
       boolean flag1, flag2;
       
       p1 = searchProduct(productID);
       m1 = searchManufacturer(manufacturerID);
       
       
       flag1 = p1.unassignProduct(productID, manufacturerID);
       flag2 = m1.unassignManufacturer(productID, manufacturerID);
       
       return (flag1 && flag2);
   }
   
   public Manufacturer addManufacturer (String name, String address) {
    Manufacturer manufacturer = new Manufacturer(name, address);
    if (manufacturerList.insertManufacturer(manufacturer)) {
      return (manufacturer);
    }
    return null;
  }
   
   public Iterator getManufacturer() {
      return manufacturerList.getManufacturer();
  }
   
   private Product addProduct(String name, String description, String ID) {
    Product product = new Product(name, description, ID);
    if (productList.insertProduct(product)) {
      return (product);
    }
    return null;
  }
   
   public Iterator getProduct() {
      return productList.getProduct();
  }
   
   public Iterator getProductSupplierList(String productID){
       product p1;
       
       p1 = searchProduct(productID);
       return(p1.getSuppliers());
   }
   
   public Iterator getManufacturerProductList(String manufacturerID){
       manufacturer m1;
       
       m1 = searchManufacturer(manufacturerID);
       return(m1.getSuppliedProducts());
   }
   
   
public static  boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("WarehouseData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(warehouse);
      output.writeObject(ClientIdServer.instance());
      output.writeObject(ManufacturerIdServer.instance());
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
    return productList + "\n" manufacturerList + "\n" + clientList;
  }
}
