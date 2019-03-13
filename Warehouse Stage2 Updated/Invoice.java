import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Invoice implements Serializable{

	  private static final long serialVersionUID = 1L;
	  private List<OrderItem> orderitems = new LinkedList<OrderItem>();
	  private Client client;
	  private float totalPrice = 0;
	  private Date date;
	  private String id;
	  private static final String Invoice_String = "I";

	
	
	  public Invoice (Client client) {
		  this.client = client;
		  date = new Date();
		  id = Invoice_String + InvoiceIDServer.instance().getID();
	  }
	  
	  public boolean addOrderItem (OrderItem orderitem) {
		  orderitems.add(orderitem);
		  totalPrice = totalPrice + orderitem.getSalePrice();
		  return true;
	  }
	  
	  public float getTotalPrice() {
		  return totalPrice;
	  }
	  
	  public Date getDate() {
		  return date;
	  }
	  
	  public String getInvoiceID() {
			return id;
		}
	  
	  public String toString() {
		  return "Client: " + client + "\n Invoice ID: " + id +  "\nTotal Price: " + totalPrice + "\nDate: "+ date;
	  }


	  
}
