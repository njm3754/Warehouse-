import java.util.*;
import java.io.*;
import java.io.Serializable;

public class Transaction implements Serializable{
    private String description;
    private float dollarAmount;
    private Date date;
    private String id;
    private static final String TRANSACTION_STRING = "T";
    
    public  Transaction (String description, float dollarAmount) {
    this.description = description;
    this.dollarAmount = dollarAmount;
    this.date = new Date();
    id = TRANSACTION_STRING + (TransactionIDServer.instance()).getId();
  }
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setDollarAmount(float dollarAmount){
        this.dollarAmount = dollarAmount;
    }
        
    public String getDescription(){
        return this.description;
    }
    
    public float getDollarAmount(){
        return this.dollarAmount;
    }
    
    public Date getDate(){
        return this.date;
    }
    
    public String getID(){
        return this.id;
    }
}
