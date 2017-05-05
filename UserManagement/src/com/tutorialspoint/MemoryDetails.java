package com.tutorialspoint;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "memory")
public class MemoryDetails implements Serializable {

   private static final long serialVersionUID = 1L;
   private int id;
   private int uid;
   private String memodate;
   private String type;
   private String remarks;
   private String details;

   public MemoryDetails(){}

   public MemoryDetails(int id,int uid, String memodate, String type,String remarks,String details){
      this.id = id;
      this.uid = uid;
      this.memodate = memodate;
      this.type = type;
      this.remarks = remarks;
      this.details = details;
   }

   public int getId() {
      return id;
   }
   @XmlElement
   public void setId(int id) {
      this.id = id;
   }
   public int getUid() {
      return this.uid;
   }
   @XmlElement
      public void setUid(int uid) {
      this.uid = uid;
   }
   
   public String getMemoDate() {
      return this.memodate;
   }
   @XmlElement
   public void setMemoDate(String memodate) {
      this.memodate = memodate;
   }	
   
   public String getType() {
	      return this.type;
   }
   @XmlElement
   public void setType(String type) {
      this.type = type;;
   }	
	 	   
   public String getRemarks() {
      return this.remarks;
   }
   @XmlElement
   public void setRemarks(String remarks) {
      this.remarks = remarks;
   }	
  
   public String getDetails() {
	      return this.details;
	   }
	   @XmlElement
	   public void setDetails(String details) {
	      this.details = details;
	   }	
	  
   
   
   @Override
   public boolean equals(Object object){
      if(object == null){
         return false;
      }else if(!(object instanceof MemoryDetails)){
         return false;
      }else {
         MemoryDetails mem = (MemoryDetails)object;
         if(id == mem.getId()
            && uid == mem.getUid()
            && memodate.equals(mem.getMemoDate())
            && type.equals(mem.getType())
            && remarks.equals(mem.getRemarks())
            && details.equals(mem.getDetails())
         ){
            return true;
         }			
      }
      return false;
   }	
}