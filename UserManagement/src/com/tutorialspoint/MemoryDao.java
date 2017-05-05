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

public class MemoryDao {
	
   public List<MemoryDetails> getAllMemories(String keyword,String startDate,String endDate,int uid){
      List<MemoryDetails> memList = null;
      DBConnection db = new DBConnection();
      try {               
    	  memList = db.getAllMemories(keyword,startDate,endDate,uid);  
    	  
      } catch (Exception e) {
         e.printStackTrace();
      } 	
      return memList;
   }

   public MemoryDetails getMemory(int id){
      MemoryDetails memObj = new MemoryDetails(); 
      //memObj = db.
      return memObj;
   }

   public int addMemory(MemoryDetails memObj){
      //check the meaning of return value 0.   
	  System.out.println("Adding memory!!");
	  DBConnection db = new DBConnection();
	  boolean res = db.insertNewMemory(memObj);
	  if(res==true)
	  return 1;
	  else
      return 0;
   }

   public int updateMemory(MemoryDetails memObj){
	 //check the meaning of return value 0. 
		  System.out.println("Updating memory!!");
		  DBConnection db = new DBConnection();
		  boolean res = db.updateMemory(memObj);
		  if(res==true)
		  return 1;
		  else
	      return 0;
      
   }

   public int deleteMemory(int id)
   {
	 //check the meaning of return value 0.   
		  System.out.println("Deleting memory!!");
		  DBConnection db = new DBConnection();
		  boolean res = db.deleteMemory(id);
		  if(res==true)
		  return 1;
		  else
	      return 0;
   }  
}