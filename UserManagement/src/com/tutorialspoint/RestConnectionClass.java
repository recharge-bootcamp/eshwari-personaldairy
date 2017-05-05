package com.tutorialspoint;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.tutorialspoint.User;

public class RestConnectionClass 
{
	private Client client;
	private String REST_SERVICE_URL = "http://localhost:8080/UserManagement/rest/UserService/users";
	private String REST_SERVICE_URL_MEMORY = "http://localhost:8080/UserManagement/rest/MemoryService/memory";
	private static final String SUCCESS_RESULT="<result>success</result>";
	private static final String PASS = "pass";
	private static final String FAIL = "fail";
	
	public void RestConnectionClass()
	{
		init();
	}	
	
	   private void init(){
	      this.client = ClientBuilder.newClient();
	   }
	
	   //Test: loginCheck
	   public User testGetUser(String username,String password){
		  this.client = ClientBuilder.newClient();
		  
	      User sampleUser = new User();
	      sampleUser.setUsername(username);
	      //System.out.println(REST_SERVICE_URL+"  "+client);
	      User user = client
	         .target(REST_SERVICE_URL)
	         .path("/{username}")
	         .resolveTemplate("username", username)
	         .request(MediaType.APPLICATION_XML)
	         .get(User.class);
	      String result = FAIL;
	      if(sampleUser != null && sampleUser.getPassword().equals(password)){
	         result = PASS;	         
	      }
	      System.out.println("Test case name: testGetUser, Result: " + result );
	      return user;
	   }
	
	   
	 //Test: Get list of all users;Sample method to test REST call
	 //Test: Check if list is not empty
	 public void testGetAllUsers(){
		 this.client = ClientBuilder.newClient();
	      GenericType<List<User>> list = new GenericType<List<User>>() {};
	      List<User> users = client
	         .target(REST_SERVICE_URL)
	         .request(MediaType.APPLICATION_XML)
	         .get(list);
	      String result = PASS;
	      if(users.isEmpty()){
	         result = FAIL;
	      }
	      System.out.println("Test case name: testGetAllUsers, Result: " +result);
	   }
	 
	 
	  //Test: Add User with given details
	   //Test: Check if result is success XML.
	   public boolean testAddUser(String name,String username,String password,String email){
	      boolean useradded = true;
		  Form form = new Form();
	      form.param("id", "-1");
	      form.param("name", name);
	      form.param("username", username);
	      form.param("email", email);
	      form.param("password", "password");	      
	      
	      System.out.println(client
	        .target(REST_SERVICE_URL)
	        .request(MediaType.APPLICATION_XML)
	        .post(Entity.entity(form,
	           MediaType.APPLICATION_FORM_URLENCODED_TYPE),
	           String.class)
	  );
	      String callResult = client
	         .target(REST_SERVICE_URL)
	         .request(MediaType.APPLICATION_XML)
	         .post(Entity.entity(form,
	            MediaType.APPLICATION_FORM_URLENCODED_TYPE),
	            String.class);
	   
	      String result = PASS;
	      
	      if(!SUCCESS_RESULT.equals(callResult)){
	         result = FAIL;
	         useradded = false;
	      }
	      
	      return useradded;
	   }
	   
	 
	 //Test: Add Memory for User id
	      //Test: Check if result is success XML.
	      public boolean testAddMemory(String uid,String memodate,String type,String remarks,String details){
	    	  boolean addedCheck = true;
	         Form form = new Form();
	         form.param("id", "-1");
	         form.param("uid", uid);
	         form.param("memodate", memodate);
	         form.param("type", type);
	         form.param("remarks", remarks);
	         form.param("details", details);
	         
	         System.out.println(client
	           .target(REST_SERVICE_URL_MEMORY)
	           .request(MediaType.APPLICATION_XML)
	           .post(Entity.entity(form,
	              MediaType.APPLICATION_FORM_URLENCODED_TYPE),
	              String.class)
	     );
	         String callResult = client
	            .target(REST_SERVICE_URL_MEMORY)
	            .request(MediaType.APPLICATION_XML)
	            .post(Entity.entity(form,
	               MediaType.APPLICATION_FORM_URLENCODED_TYPE),
	               String.class);
	      
	         String result = PASS;
	         if(!SUCCESS_RESULT.equals(callResult)){
	            result = FAIL;
	            addedCheck = false;
	         }
	      
	      
	      System.out.println("Test case name: testAddMemory, Result: " + result );
	      return addedCheck;
	   }
	 
	      
	    //Test: Get list of all memories based on filter condition
	    //Test: Check if list is not empty
	     public GenericType<List<MemoryDetails>> testGetAllMemories(String keyword,String startDate,String endDate,int uid){
	         GenericType<List<MemoryDetails>> memList = new GenericType<List<MemoryDetails>>() {};
	         List<MemoryDetails> memories = client
	            .target(REST_SERVICE_URL_MEMORY)
	            .request(MediaType.APPLICATION_XML)
	            .get(memList);
	         String result = PASS;
	         if(memories.isEmpty()){
	            result = FAIL;
	         }
	        return memList;
	      }

	   //Test: Delete memory of given id
	     //Test: Check if result is success XML.
	    public boolean testDeleteMemory(int memId){
	    	boolean delResult = true;
	        String callResult = client
	           .target(REST_SERVICE_URL_MEMORY)
	           .path("/{memoryid}")
	           .resolveTemplate("memoryid", memId)
	           .request(MediaType.APPLICATION_XML)
	           .delete(String.class);

	        String result = PASS;
	        if(!SUCCESS_RESULT.equals(callResult)){
	           result = FAIL;
	           delResult = false;
	        }

	        System.out.println("Test case name: testDeleteMemory, Result: " + result );
	        return delResult;
	     }

	    
	    //Test: Update memory of given id
	    //Test: Check if result is success XML.
	    public boolean testUpdateMemory(int memId,String details){
	       boolean updateResult = true;	
	       Form form = new Form();
	       form.param("id", String.valueOf(memId));
	       form.param("details", details);

	       String callResult = client
	          .target(REST_SERVICE_URL_MEMORY)
	          .request(MediaType.APPLICATION_XML)
	          .put(Entity.entity(form,
	             MediaType.APPLICATION_FORM_URLENCODED_TYPE),
	             String.class);
	       String result = PASS;
	       if(!SUCCESS_RESULT.equals(callResult)){
	          result = FAIL;
	          updateResult = false;
	       }

	       System.out.println("Test case name: testUpdateMemory, Result: " + result );
	       return updateResult;
	    }
	    
	    
	    
	    
	    
	 /*public static void main(String n[])
	 {
		 RestConnectionClass rc= new RestConnectionClass();
		 rc.testGetAllUsers();
		 
	 }*/
	   
}
