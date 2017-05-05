package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.GenericType;

import com.tutorialspoint.*;

/**
 * Servlet implementation class ProcessServlet
 */
@WebServlet("/ProcessServlet")
public class ProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		RestConnectionClass restObj = new RestConnectionClass();
		
		if(request.getParameter("btn_login")!=null)
		{
			String username = request.getParameter("txt_usernameLogin");
			String password = request.getParameter("txt_passwordLogin");
			//restObj.testGetAllUsers();
			//User userObj = restObj.testGetUser(username, password);
			if(username.equals("eshu")&& password.equals("ishu"))
			{	
				//System.out.println("DB REST call successful!!");
				HttpSession sessionLogin = request.getSession();
				//sessionLogin.setAttribute("CurrentUser",userObj);
				request.getRequestDispatcher("/WEB-INF/addMemory.jsp").forward(request, response);
			}	
			else
			{
				request.getRequestDispatcher("./login.jsp").forward(request, response);
			}
		}		
		if(request.getParameter("btn_add")!=null)
		{
			request.getRequestDispatcher("/WEB-INF/addMemory.jsp").forward(request, response);
		}
		else if(request.getParameter("btn_editDelete")!=null)
		{
			request.getRequestDispatcher("/WEB-INF/deleteEdit.jsp").forward(request, response);
		}
		else if(request.getParameter("btn_search")!=null)
		{
			/*String keyword = request.getParameter("txt_searchKey");
		    String startDate = request.getParameter("txt_toSearchDate");
		    String endDate = request.getParameter("txt_fromSearchDate");
			User currentUser = (User)request.getSession().getAttribute("CurrentUser");
		    GenericType<List<MemoryDetails>> memList = restObj.testGetAllMemories(keyword, startDate, endDate,currentUser.getId());
			if(memList!=null)
			{			
				HttpSession sessionLogin = request.getSession();
				request.setAttribute("MemoryList",memList);
				request.getRequestDispatcher("/WEB-INF/searchMemory.jsp").forward(request, response);
			}
			else*/
			{
				request.getRequestDispatcher("/WEB-INF/searchMemory.jsp").forward(request, response);
			}				
		}		
		else if(request.getParameter("btn_logout")!=null)
		{
			request.getSession().removeAttribute("CurrentUser");
			request.getRequestDispatcher("./index.jsp").forward(request, response);			
		}
		else if(request.getParameter("btn_changePassword")!=null)
		{
			request.getRequestDispatcher("/WEB-INF/changeMemory.jsp").forward(request, response);			
		}
		else if(request.getParameter("btn_register")!=null)
		{
			String name = request.getParameter("txt_nameRegister");
			String username = request.getParameter("txt_usernameRegister");
			String password = request.getParameter("txt_passwordRegister");
			String confirmpass = request.getParameter("txt_confirmPasswordRegister");		
			String email = request.getParameter("txt_emailRegister");
			boolean addUserCheck = restObj.testAddUser(name, username, password, email);
			if(addUserCheck)
			{	
				
				System.out.println("DB REST call successful!!");
				User userObj = restObj.testGetUser(username, password);
				HttpSession sessionLogin = request.getSession();
				sessionLogin.setAttribute("CurrentUser",userObj);
				request.getRequestDispatcher("/WEB-INF/addMemory.jsp").forward(request, response);
			}	
			else
			{
				request.getRequestDispatcher("./register.jsp").forward(request, response);
			}
		}
		else if(request.getParameter("btn_addDetails")!=null)
		{
			User currentUser = (User)request.getSession().getAttribute("CurrentUser");
			String memodate = request.getParameter("txt_memoDate");
			String details = request.getParameter("txt_detailsAdd");
			String remarks = request.getParameter("txt_remarksAdd");
			String type = request.getParameter("list_addType");	
			if(!type.equals("TEXT")){
				details = request.getParameter("txt_addMemoryFileUpload");
			}
			int uid = currentUser.getId();
			boolean addCheck = restObj.testAddMemory(String.valueOf(uid), memodate, type, remarks, details);
			if(addCheck)
			{	
				request.setAttribute("AddResult","Successfully Added!!");
				HttpSession sessionLogin = request.getSession();
				request.getRequestDispatcher("/WEB-INF/addMemory.jsp").forward(request, response);
			}	
			else
			{
				request.setAttribute("AddResult","Added failed!!");
				request.getRequestDispatcher("/WEB-INF/addMemory.jsp").forward(request, response);
			}
		}
		else if(request.getParameter("btn_searchMemory")!=null)
		{
			String keyword = request.getParameter("txt_searchKey");
		    String startDate = request.getParameter("txt_toSearchDate");
		    String endDate = request.getParameter("txt_fromSearchDate");
			User currentUser = (User)request.getSession().getAttribute("CurrentUser");
		    GenericType<List<MemoryDetails>> memList = restObj.testGetAllMemories(keyword, startDate, endDate,currentUser.getId());
			if(memList!=null)
			{			
				HttpSession sessionLogin = request.getSession();
				request.setAttribute("MemoryList",memList);
				request.getRequestDispatcher("/WEB-INF/searchMemory.jsp").forward(request, response);
			}	
			else
			{
				request.setAttribute("MemorySearchResult","No Results Found!!");
				request.getRequestDispatcher("/WEB-INF/searchMemory.jsp").forward(request, response);
			}
		}
		else if(request.getParameter("btn_deleteMemory")!=null)
		{
			String memId = request.getParameter("txt_memId");
		    boolean delResult = restObj.testDeleteMemory(Integer.parseInt(memId));
			if(delResult==true)
			{			
				request.setAttribute("EDitDeleteResult","Deleted Successfully!");
				request.getRequestDispatcher("/WEB-INF/deleteEdit.jsp").forward(request, response);
			}	
			else
			{
				request.setAttribute("EDitDeleteResult","Delete Failed!");
				request.getRequestDispatcher("/WEB-INF/deleteEdit.jsp").forward(request, response);
			}
		}
		else if(request.getParameter("btn_editMemory")!=null)
		{
			String memId = request.getParameter("txt_memId");
		    String details = request.getParameter("txt_deleteMemory");
		    boolean editResult = restObj.testUpdateMemory(Integer.valueOf(memId), details);
			if(editResult==true)
			{			
				request.setAttribute("EDitDeleteResult","Edited Successfully!");
				request.getRequestDispatcher("/WEB-INF/deleteEdit.jsp").forward(request, response);
			}	
			else
			{
				request.setAttribute("EDitDeleteResult","Edit Failed!");
				request.getRequestDispatcher("/WEB-INF/deleteEdit.jsp").forward(request, response);
			}
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
