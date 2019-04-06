import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class InvoiceList implements Serializable {
	  private static final long serialVersionUID = 1L;
	  private List<Invoice> invoices = new LinkedList<Invoice>();
	  private static InvoiceList invoicelist;
	  
	  private InvoiceList() {};
	  
	  public static InvoiceList instance() {
		    if (invoicelist == null) {
		      return (invoicelist = new InvoiceList());
		    } else {
		      return invoicelist;
		    }
		  }
		  
	  public boolean addInvoice(Invoice invoice) {
		  invoices.add(invoice);
		  return true;
	  }
	   
	  public Iterator<Invoice> getinvoices() {
		 return invoices.iterator();
	  }
	  
	  public Invoice searchInvoice(String invoiceID)
		{
			Iterator<Invoice> invoiceIterator = invoices.iterator();

			while (invoiceIterator.hasNext())
			{
				Invoice invoice = (Invoice)(invoiceIterator.next());
				if (invoice.getInvoiceID().equals(invoiceID))
				{
					return invoice;
				}
			}

			return null;
		}
	  
	  private void writeObject(java.io.ObjectOutputStream output) {
	    try {
	      output.defaultWriteObject();
	      output.writeObject(invoicelist);
	    } catch(IOException ioe) {
	      System.out.println(ioe);
	    }
	  }
	  
	  private void readObject(java.io.ObjectInputStream input) {
	    try {
	      if (invoicelist != null) {
	        return;
	      } else {
	        input.defaultReadObject();
	        if (invoicelist == null) {
	        	invoicelist = (InvoiceList) input.readObject();
	        } else {
	          input.readObject();
	        }
	      }
	    } catch(IOException ioe) {
	      System.out.println("in InvoiceList readObject \n" + ioe);
	    } catch(ClassNotFoundException cnfe) {
	      cnfe.printStackTrace();
	    }
	  }
	  
	  public String toString() {
	    return invoices.toString();
	  }
	  
	  
}
