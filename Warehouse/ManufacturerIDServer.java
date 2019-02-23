import java.io.*;
public class ManufacturerIDServer implements Serializable {
	private int idCounter;
	private static ManufacturerIDServer server;

	private ManufacturerIDServer() {
		idCounter = 1;
	}

	public static ManufacturerIDServer instance() {
		if (server == null) {
			return (server = new ManufacturerIDServer());
		} else {
			return server;
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
			server = (ManufacturerIDServer) input.readObject();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	private void writeObject(java.io.ObjectOutputStream output) throws IOException {
		try {
			output.defaultWriteObject();
			output.writeObject(server);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
		try {
			input.defaultReadObject();
			if (server == null) {
			server = (ManufacturerIDServer) input.readObject();
			} else {
			input.readObject();
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}