package com.tutorialspoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.db.DBConnection;

public class UserDao {
   DBConnection db = new DBConnection();
   public List<User> getAllUsers(){
      List<User> userList = null;
      
      userList = db.getAllUsers();
      return userList;
   }

   public User getUser(int id){
      List<User> users = getAllUsers();

      for(User user: users){
         if(user.getId() == id){
            return user;
         }
      }
      return null;
   }

   public int addUser(User pUser){
      
      boolean result = db.insertNewUser(pUser);
      if(result==true)         
         return 1;
      else
         return 0;
   }

   public int updateUser(User pUser){
      boolean result = db.updateUser(pUser);
      if(result==true)
    	  return 1;
      else
          return 0;
   }

   public int deleteUser(int id){
	   boolean result = db.deleteUser(id);
	      if(result==true)
	    	  return 1;
	      else
	          return 0;
   }

   private void saveUserList(List<User> userList){
      try {
         File file = new File("list.dat");
         FileOutputStream fos;

         fos = new FileOutputStream(file);

         ObjectOutputStream oos = new ObjectOutputStream(fos);		
         oos.writeObject(userList);
         oos.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}