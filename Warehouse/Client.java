/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.util.*;
import java.io.*;
/**
 *
 * @author jessc
 */
public class Client implements Serializable{
    private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String emailAddress;
  private String id;
  private static final String CLIENT_STRING = "C";
    
  public  Client (String name, String address, String emailAddress) {
    this.name = name;
    this.address = address;
    this.emailAddress = emailAddress;
    id = CLIENT_STRING + (ClientIdServer.instance()).getId();
  }

  public String getName() {
    return name;
  }
  public String getemailAddress() {
    return emailAddress;
  }
  public String getAddress() {
    return address;
  }
  public String getId() {
    return id;
  }
  public void setName(String newName) {
    name = newName;
  }
  public void setAddress(String newAddress) {
    address = newAddress;
  }
  public void setPhone(String newemailAddress) {
    emailAddress = newemailAddress;
  }
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  public String toString() {
    String string = "Member name " + name + " address " + address + " id " + id + " emailAddress " + emailAddress;
    return string;
  }
}
