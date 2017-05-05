package com.tutorialspoint;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class WebServiceTester  {

   private Client client;
   private String REST_SERVICE_URL = "http://localhost:8080/UserManagement/rest/UserService/users";
   private String REST_SERVICE_URL_MEMORY = "http://localhost:8080/UserManagement/rest/MemoryService/memory";
   private static final String SUCCESS_RESULT="<result>success</result>";
   private static final String PASS = "pass";
   private static final String FAIL = "fail";

   private void init(){
      this.client = ClientBuilder.newClient();
   }

   public static void main(String[] args){
      WebServiceTester tester = new WebServiceTester();
      //initialize the tester
      tester.init();
      //test get all users Web Service Method
      tester.testGetAllUsers();
      //test get user Web Service Method 
      tester.testGetUser();
    //test get user Web Service Method 
      tester.testGetAllMemories();
      //test get user Web Service Method 
      tester.testAddMemory();
      //test get user Web Service Method 
      tester.testUpdateMemory();
      //test get user Web Service Method 
      tester.testDeleteMemory();
      
      //test update user Web Service Method
      tester.testUpdateUser();
      //test add user Web Service Method
      tester.testAddUser();
      //test delete user Web Service Method
      tester.testDeleteUser();
   }
   //Test: Get list of all users
   //Test: Check if list is not empty
   private void testGetAllUsers(){
      GenericType<List<User>> list = new GenericType<List<User>>() {};
      List<User> users = client
         .target(REST_SERVICE_URL)
         .request(MediaType.APPLICATION_XML)
         .get(list);
      String result = PASS;
      if(users.isEmpty()){
         result = FAIL;
      }
      System.out.println("Test case name: testGetAllUsers, Result: "+result);
   }
   //Test: Get User of id 1
   //Test: Check if user is same as sample user
   private void testGetUser(){
      User sampleUser = new User();
      sampleUser.setId(1);

      User user = client
         .target(REST_SERVICE_URL)
         .path("/{userid}")
         .resolveTemplate("userid", 1)
         .request(MediaType.APPLICATION_XML)
         .get(User.class);
      String result = FAIL;
      if(sampleUser != null && sampleUser.getId() == user.getId()){
         result = PASS;
      }
      System.out.println("Test case name: testGetUser, Result: " + result );
   }
   //Test: Update User of id 1
   //Test: Check if result is success XML.
   private void testUpdateUser(){
      Form form = new Form();
      form.param("id", "1");
      form.param("name", "suresh");
      form.param("profession", "clerk");

      String callResult = client
         .target(REST_SERVICE_URL)
         .request(MediaType.APPLICATION_XML)
         .put(Entity.entity(form,
            MediaType.APPLICATION_FORM_URLENCODED_TYPE),
            String.class);
      String result = PASS;
      if(!SUCCESS_RESULT.equals(callResult)){
         result = FAIL;
      }

      System.out.println("Test case name: testUpdateUser, Result: " + result );
   }
   //Test: Add User of id 2
   //Test: Check if result is success XML.
   private void testAddUser(){
      Form form = new Form();
      form.param("id", "2");
      form.param("name", "naresh");
      form.param("profession", "clerk");
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
      }
   }
   
   //Test: Get list of all memories
   //Test: Check if list is not empty
   private void testGetAllMemories(){
      GenericType<List<MemoryDetails>> memList = new GenericType<List<MemoryDetails>>() {};
      List<MemoryDetails> memories = client
         .target(REST_SERVICE_URL_MEMORY)
         .request(MediaType.APPLICATION_XML)
         .get(memList);
      String result = PASS;
      if(memories.isEmpty()){
         result = FAIL;
      }
     // System.out.println("Test case name: testGetAllUsers, Result: " +memories.get(0).getDetails());
   }

    //Test: Add Memory for User id
      //Test: Check if result is success XML.
      private void testAddMemory(){
         Form form = new Form();
         form.param("id", "1");
         form.param("uid", "2");
         form.param("memodate", "2016-09-02 07:14:00");
         form.param("type", "TEXT");
         form.param("remarks", "nil");
         form.param("details", "Kushi came to my life on this day!");
         
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
         }
      
      
      System.out.println("Test case name: testAddMemory, Result: " + result );
   }
   
 //Test: Update memory of id 1
   //Test: Check if result is success XML.
   private void testUpdateMemory(){
      Form form = new Form();
      form.param("id", "1");
      form.param("uid", "1");
      form.param("memodate", "2017-09-02 07:14:00");
      form.param("type", "TEXT");
      form.param("remarks", "updating remarks");
      form.param("details", "I updated kushi details");

      String callResult = client
         .target(REST_SERVICE_URL_MEMORY)
         .request(MediaType.APPLICATION_XML)
         .put(Entity.entity(form,
            MediaType.APPLICATION_FORM_URLENCODED_TYPE),
            String.class);
      String result = PASS;
      if(!SUCCESS_RESULT.equals(callResult)){
         result = FAIL;
      }

      System.out.println("Test case name: testUpdateMemory, Result: " + result );
   }
   
   
   
   //Test: Delete memory of id 2
   //Test: Check if result is success XML.
   private void testDeleteMemory(){
      String callResult = client
         .target(REST_SERVICE_URL_MEMORY)
         .path("/{memoryid}")
         .resolveTemplate("memoryid", 2)
         .request(MediaType.APPLICATION_XML)
         .delete(String.class);

      String result = PASS;
      if(!SUCCESS_RESULT.equals(callResult)){
         result = FAIL;
      }

      System.out.println("Test case name: testDeleteMemory, Result: " + result );
   }

   
   //Test: Delete User of id 2
   //Test: Check if result is success XML.
   private void testDeleteUser(){
      String callResult = client
         .target(REST_SERVICE_URL)
         .path("/{userid}")
         .resolveTemplate("userid", 2)
         .request(MediaType.APPLICATION_XML)
         .delete(String.class);

      String result = PASS;
      if(!SUCCESS_RESULT.equals(callResult)){
         result = FAIL;
      }

      System.out.println("Test case name: testDeleteUser, Result: " + result );
   }

}