package com.db;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;
import java.util.Vector;

import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.swing.table.DefaultTableModel;

import com.tutorialspoint.MemoryDetails;
import com.tutorialspoint.User;


public class DBConnection 
{
	private static Connection connection;
    private static Statement statement;
    public static boolean connectedToDatabase = false;
    private int ActiveMissionID;
    public static boolean isNewMission;
    private static String Driver = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://127.0.0.1:3306/personaldairy";
    private static String Username = "root";
    private static String Password = "SqlRoot3!";
            
	
	/*public DBConnection(String driver, String url, String username, String password) {
        Driver = driver;
        URL = url;
        Username = username;
        Password = password;
        
    }*/

    public synchronized void makeDatabaseConnection() {
        try {
            Class.forName(Driver);
            connection = DriverManager.getConnection(URL, Username, Password);
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            connectedToDatabase = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized void closeDatabaseConnection() {
        if (connectedToDatabase) {
            try {
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                connectedToDatabase = false;
            }
        }
    }
    
    
    /*Get Id for new User*/
    public synchronized int getNewUserId() {
        String SQLQuery = "SELECT MAX(id) FROM regusers";
        int id = -1;
        try {
            makeDatabaseConnection();
            ResultSet rs = statement.executeQuery(SQLQuery);
            if (rs!=null) {
                id = rs.getInt(1)+1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }    
        return id;
    }
     
    /*Get Id for new entry of memory*/
    public synchronized int getNewMemoryId() {
        String SQLQuery = "SELECT MAX(id) FROM memories";
        int id = -1;
        try {
            makeDatabaseConnection();
            ResultSet rs = statement.executeQuery(SQLQuery);
            if (rs!=null) {
                id = rs.getInt(1)+1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }    
        return id;
    }
   
     
    /*List all users*/
    public synchronized List<User> getAllUsers() {
        String SQLQuery = "SELECT* FROM regusers";
        ArrayList<User> userList = new ArrayList<User>();
        User userObj = new User();
        try {
            makeDatabaseConnection();
            ResultSet rs = statement.executeQuery(SQLQuery);
            if (rs!=null) {
                while(rs.next()){
                	userObj = new User();
                	userObj.setId(rs.getInt(1));
                	userObj.setName(rs.getString(2));
                	userObj.setUsername(rs.getString(3));
                	userObj.setEmail(rs.getString(4));
                	userObj.setPassword(rs.getString(5));
                	userList.add(userObj);
                	System.out.println(rs.getString(1)+":"+rs.getString(2)+":"+rs.getString(3)+":"+rs.getString(4)+":"+rs.getString(5));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }    
        return userList;
    }
     
    /*Insert new user into DB with register operation*/
    public synchronized boolean insertNewUser(User userObj) {
    	
    	int id=getNewUserId();
    	userObj.setId(id);
    	
        String SQLQuery = "insert into regusers values("+userObj.getId()+",\'"+userObj.getName()+"\',\'"+userObj.getUsername()+"\',\'"+userObj.getEmail()+"\',\'"+userObj.getPassword()+"\')";
        boolean res=false;
        try {
            makeDatabaseConnection();
            
            if (statement.executeUpdate(SQLQuery) > 0) {
                res = true;
                System.out.println("Inserted successfully");
            }           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }    
        return res;
    }
    
    /*Delete user from DB with DeleteUser operation*/
    public synchronized boolean deleteUser(int id) {
    	
    	String SQLQuery = "delete from regusers where id= "+id;
        System.out.println(SQLQuery);
        boolean res=false;
        try {
            makeDatabaseConnection();
            
            if (statement.executeUpdate(SQLQuery) > 0) {
                res = true;
                System.out.println("Deleted successfully from users!");
            }           
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }      
        return res;
    }
    
    /*Update user into DB with UpdateUser operation*/
    public synchronized boolean updateUser(User userObj) {
    	    	
        String SQLQuery = "update regusers set password=\'"+userObj.getPassword()+"\' where id="+userObj.getId();
        System.out.println(SQLQuery);
        boolean res=false;
        try {
            makeDatabaseConnection();
            
            if (statement.executeUpdate(SQLQuery) > 0) {
                res = true;
                System.out.println("Updated successfully in users!");
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }      
        return res;
    }
    
    
    
    
    /* List all memories*/
    public synchronized List<MemoryDetails> getAllMemories(String keyword,String startDate,String endDate,int uid) {
        String SQLQuery = "SELECT* FROM memories where uid="+uid;
        
        if(startDate!=null & endDate!=null && !startDate.equals("")&& !endDate.equals(""))
        {
        	SQLQuery = "SELECT* FROM memories where uid="+uid+" and memodate between '"+startDate+"' and '"+endDate+"'";
        	 
        }
        if(keyword!=null && !keyword.equals(""))
        {
        	SQLQuery = SQLQuery + " and details = %"+keyword+"%";
        }
        
        System.out.println("SQL Query for memory search:"+SQLQuery);
        
        ArrayList<MemoryDetails> memList = new ArrayList<MemoryDetails>();
        MemoryDetails memObj = new MemoryDetails();
        try {
            makeDatabaseConnection();
            ResultSet rs = statement.executeQuery(SQLQuery);
            if (rs!=null) {
                while(rs.next()){
                	memObj = new MemoryDetails();
                	memObj.setId(rs.getInt(1));
                	memObj.setUid(rs.getInt(2));
                	memObj.setMemoDate(rs.getString(3));
                	memObj.setType(rs.getString(4));
                	memObj.setRemarks(rs.getString(5));
                	memObj.setDetails(rs.getString(6));
                	memList.add(memObj);
                	System.out.println(rs.getString(1)+":"+rs.getString(2)+":"+rs.getString(3)+":"+rs.getString(4)+":"+rs.getString(5));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }    
        return memList;
    }    
    
    /*Insert new memory into DB with AddMemory operation*/
    public synchronized boolean insertNewMemory(MemoryDetails memObj) {
    	
    	int id=getNewMemoryId();
    	int uid = memObj.getUid();
    	String memoDate=memObj.getMemoDate();
    	String type=memObj.getType();
    	String remarks=memObj.getRemarks();
    	String details = memObj.getDetails();
    	
        String SQLQuery = "insert into memories values("+id+","+uid+",\'"+memoDate+"\',\'"+type+"\',\'"+remarks+"\',\'"+details+"\')";
        System.out.println(SQLQuery);
        boolean res=false;
        try {
            makeDatabaseConnection();
            
            if (statement.executeUpdate(SQLQuery) > 0) {
                res = true;
                System.out.println("Inserted successfully into memories!");
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }      
        return res;
    }
    
    /*Delete memory from DB with DeleteMemory operation*/
    public synchronized boolean deleteMemory(int id) {
    	
    	String SQLQuery = "delete from memories where id= "+id;
        System.out.println(SQLQuery);
        boolean res=false;
        try {
            makeDatabaseConnection();
            
            if (statement.executeUpdate(SQLQuery) > 0) {
                res = true;
                System.out.println("Deleted successfully from memories!");
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }      
        return res;
    }
    
    /*Update memory into DB with UpdateMemory operation*/
    public synchronized boolean updateMemory(MemoryDetails memObj) {
    	    	
        String SQLQuery = "update memories set remarks=\'"+memObj.getRemarks()+"\',details=\'"+memObj.getRemarks()+"\' where id="+memObj.getId();
        System.out.println(SQLQuery);
        boolean res=false;
        try {
            makeDatabaseConnection();
            
            if (statement.executeUpdate(SQLQuery) > 0) {
                res = true;
                System.out.println("Updated successfully in memories!");
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }      
        return res;
    }
       
    /*public static void main(String n[])
    {
    	DBConnection db=new DBConnection();
        db.makeDatabaseConnection();
    	if(db.connectedToDatabase)
    	{
    		System.out.println("Connected to DB");
    		//db.insertNewUser(1,"ruchi", "ruchi.arora", "ruchi.arora@gmail.com", "ruchi");
    		//db.insertNewUser(2,"Pooja", "pooja.soni", "pooja.soni@gmail.com", "pooja");
    		//db.insertNewUser(3,"amit", "amit.agarwal", "amit.agarwal@gmail.com", "amit");
    		db.getAllUsers();
    	}
    	
    }*/
}
