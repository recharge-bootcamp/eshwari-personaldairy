package com.tutorialspoint;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/MemoryService")
public class MemoryService {

	MemoryDao memoryDao = new MemoryDao();
	   private static final String SUCCESS_RESULT="<result>success</result>";
	   private static final String FAILURE_RESULT="<result>failure</result>";


	   @POST
	   @Path("/memory")
	   @Produces(MediaType.APPLICATION_XML)
	   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	   public String createMemory(@FormParam("id") int id,
	      @FormParam("uid") int uid,
	      @FormParam("memodate") String memodate,
	      @FormParam("type") String type,
	      @FormParam("remarks") String remarks,
	      @FormParam("details") String details,
	      
	      @Context HttpServletResponse servletResponse) throws IOException{
	      MemoryDetails memoObj = new MemoryDetails(id,uid,memodate,type,remarks,details);
	      int result = memoryDao.addMemory(memoObj);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      else
	    	  
	      return FAILURE_RESULT;
	   }
	   
	   @GET
	   @Path("/memory")
	   @Produces(MediaType.APPLICATION_XML)
	   public List<MemoryDetails> getAllMemories(@FormParam("uid") int uid,
			      @FormParam("keyword") String keyword,
			      @FormParam("startdate") String startdate,
			      @FormParam("enddate") String enddate,
			      			      
			      @Context HttpServletResponse servletResponse) throws IOException{
		   				
		   				return memoryDao.getAllMemories(keyword,startdate,enddate,uid);
	   }

	
	   
	   @PUT
	   @Path("/memory")
	   @Produces(MediaType.APPLICATION_XML)
	   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	   public String updateMemory(@FormParam("id") int id,
	      @FormParam("uid") int uid,
	      @FormParam("memodate") String memodate,
	      @FormParam("type") String type,
	      @FormParam("remarks") String remarks,
	      @FormParam("details") String details,
	      
	      @Context HttpServletResponse servletResponse) throws IOException{
		  MemoryDetails memObj = new MemoryDetails(id, uid,memodate,type,remarks,details);
	      int result = memoryDao.updateMemory(memObj);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }

	   @DELETE
	   @Path("/memory/{memoryid}")
	   @Produces(MediaType.APPLICATION_XML)
	   public String deleteMemory(@PathParam("memoryid") int memoryid){
	      int result = memoryDao.deleteMemory(memoryid);
	      if(result == 1){
	         return SUCCESS_RESULT;
	      }
	      return FAILURE_RESULT;
	   }

	   @OPTIONS
	   @Path("/memory")
	   @Produces(MediaType.APPLICATION_XML)
	   public String getSupportedOperations(){
	      return "<operations>GET, PUT, POST, DELETE</operations>";
	   }  
	
}
