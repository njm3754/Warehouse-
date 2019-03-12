import java.util.*;
import java.io.*;
public class ManufacturerList implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Manufacturer> manufacturers = new LinkedList<Manufacturer>();
	private static ManufacturerList ManufacturerList;

	private ManufacturerList() { }

	public static ManufacturerList instance() {
		if (ManufacturerList == null) {
			return (ManufacturerList = new ManufacturerList());
		} else {
			return ManufacturerList;
		}
	}

	public boolean insertManufacturer(Manufacturer manufacturer) {
		return manufacturers.add(manufacturer);
	}

	public Manufacturer searchManufacturer(String manufacturerID)
	{
		Iterator<Manufacturer> manufacturerIterator = manufacturers.iterator();

		while (manufacturerIterator.hasNext())
		{
			Manufacturer manufacturer = (Manufacturer)(manufacturerIterator.next());
			if (manufacturer.getManufacturerID().equals(manufacturerID))
			{
				return manufacturer;
			}
		}

		return null;
	}

	public Iterator<Manufacturer> getManufacturerList(){
		 return manufacturers.iterator();
	}
	
	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(ManufacturerList);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void readObject(java.io.ObjectInputStream input) {
		try {
			if (ManufacturerList != null) {
				return;
			} else {
				input.defaultReadObject();
				if (ManufacturerList == null) {
					ManufacturerList = (ManufacturerList) input.readObject();
				} else {
					input.readObject();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

	public String toString() {
		return manufacturers.toString();
	}
}