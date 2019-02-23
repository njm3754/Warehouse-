/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jessc
 */
public class ClientList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List <Client> clients = new LinkedList<Client>();
  private static ClientList clientList;
  private ClientList() {
  }
  public static ClientList instance() {
    if (clientList == null) {
      return (clientList = new ClientList());
    } else {
      return clientList;
    }
  }
  
  public boolean insertClient(Client client) {
    clients.add(client);
    return true;
  }

  public Iterator getClients(){
     return clients.iterator();
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(clientList);
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }
  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (clientList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (clientList == null) {
          clientList = (ClientList) input.readObject();
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
    return clients.toString();
  }

public Client searchClient(String clientID) {
    Iterator clients = clientList.getClients();  
      
      while (clients.hasNext()){
	  Client client = (Client)(clients.next());
          if (client.getId().equals(clientID)){
          return client;
          } 
      }
      return null;
  }
}