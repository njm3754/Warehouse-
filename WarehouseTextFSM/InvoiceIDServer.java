import java.io.IOException;
import java.io.ObjectInputStream;

public class InvoiceIDServer {
	private int idCounter;
	private static InvoiceIDServer invoice;

	private InvoiceIDServer() {
		idCounter = 1;
	}

	public static InvoiceIDServer instance() {
		if (invoice == null) {
			return (invoice = new InvoiceIDServer());
		} else {
			return invoice;
		}
	}

	public String getID() {
		return Integer.toString(idCounter++);
	}

	public String toString() {
		return ("IDServer" + idCounter);
	}

	public static void retrieve(ObjectInputStream input) {
		try {
			invoice = (InvoiceIDServer) input.readObject();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	private void writeObject(java.io.ObjectOutputStream output) throws IOException {
		try {
			output.defaultWriteObject();
			output.writeObject(invoice);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
		try {
			input.defaultReadObject();
			if (invoice == null) {
			invoice = (InvoiceIDServer) input.readObject();
			} else {
			input.readObject();
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
