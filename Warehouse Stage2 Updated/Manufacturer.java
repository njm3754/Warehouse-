import java.util.*;
import java.io.*;
public class Manufacturer implements Serializable  {
	private static final long serialVersionUID = 1L;
	private String manufacturerID;
	private String name;
	private String address;
	private static final String MANUFACTURER_STRING = "M";
	private List<Supplies> suppliedProducts = new LinkedList<Supplies>();

	public Manufacturer(String name, String address)
	{
		manufacturerID = MANUFACTURER_STRING + ManufacturerIDServer.instance().getID();
		this.name = name;
		this.address = address;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getManufacturerID()
	{
		return manufacturerID;
	}

	public String getName()
	{
		return name;
	}

	public String getAddress()
	{
		return address;
	}

	public Iterator getSuppliedProducts()
	{
		return suppliedProducts.iterator();
	}

	public boolean assignProduct(Supplies supplies)
	{
		return suppliedProducts.add(supplies);
	}

	public boolean unassignProduct(String productID, String manufacturerID)
	{
		Iterator suppliesIterator = suppliedProducts.iterator();

		while (suppliesIterator.hasNext())
		{
			Supplies supplies = (Supplies)(suppliesIterator.next());
			Manufacturer manufacturer = supplies.getManufacturer();
			Product product = supplies.getProduct();
			if (product.getProductID().equals(productID) && manufacturer.getManufacturerID().equals(manufacturerID))
			{
				return suppliedProducts.remove(supplies);
			}
		}

		return false; //Couldn't find Supplies object to unassign
	}

	public String toString() {
		return "Manufacturer ID: " + manufacturerID + " | Name: " + name + " | Address: " + address;
	}
}
