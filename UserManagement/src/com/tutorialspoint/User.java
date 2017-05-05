package com.tutorialspoint;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "user")
public class User implements Serializable {

   private static final long serialVersionUID = 1L;
   private int id;
   private String name;
   private String username;
   private String email;
   private String password;

   public User(){}

   public User(int id, String name, String username,String email,String password){
      this.id = id;
      this.name = name;
      this.username = username;
      this.email = email;
      this.password = password;
   }

   public int getId() {
      return id;
   }
   @XmlElement
   public void setId(int id) {
      this.id = id;
   }
   public String getName() {
      return name;
   }
   @XmlElement
      public void setName(String name) {
      this.name = name;
   }
   
   public String getUsername() {
      return username;
   }
   @XmlElement
   public void setUsername(String username) {
      this.username = username;
   }	

   
   public String getEmail() {
	      return email;
   }
   @XmlElement
   public void setEmail(String email) {
      this.email = email;;
   }	
	 	   
   public String getPassword() {
      return password;
   }
   @XmlElement
   public void setPassword(String password) {
      this.password = password;
   }	
  
   
   @Override
   public boolean equals(Object object){
      if(object == null){
         return false;
      }else if(!(object instanceof User)){
         return false;
      }else {
         User user = (User)object;
         if(id == user.getId()
            && name.equals(user.getName())
            && username.equals(user.getUsername())
            && email.equals(user.getEmail())
            && password.equals(user.getPassword())
         ){
            return true;
         }			
      }
      return false;
   }	
}