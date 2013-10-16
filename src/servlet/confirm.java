//This is the content of servlet.UserForm.java file:
package servlet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.Integer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class confirm extends HttpServlet {
	  String ID=UserForm.ID;
 // Here we define a directory for user information.
 //Register Userinfo=new Register();
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, java.io.IOException {
   
     response.setContentType("text/html");
  // Here we use a PrintWriter to send text data
  // to the client who has requested the servlet
     PrintWriter out = response.getWriter();
            int customer_ID=IDget(request,response,"customer");
            
			String purchaseID=request.getParameter("purchaseID");
			String arrive_date=request.getParameter("arrive_date");
			String changepurchaserecord="UPDATE purchase_record SET arrive_date='"+arrive_date+"' WHERE purchase_ID="+purchaseID+" AND shipment_expense=0 AND customer_ID="+customer_ID;
			if(purchaseID!=null)
			{
			InsertUserInfo(request,response,changepurchaserecord);
			response.reset();
		     response.setContentType("text/html");
		     // Here we use a PrintWriter to send text data
		     // to the client who has requested the servlet
		     PrintWriter out1 = response.getWriter();
		     out.println("<p>Successful change!!!</p><p><a href='customer.html'>Back</p>");				
			}
     
 }
 public void InsertUserInfo(HttpServletRequest request, HttpServletResponse response,String updateUserinfo) throws ServletException, IOException
 {
        /*
        * Here we initialize tools for making the database connection
        * and reading from the database
        */

        Connection conn=null;
        Statement stmt=null;
    
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        
        ResultSet resultSetuser=null;        
        ResultSetMetaData resultSetuserMeatData=null;
        try {
        //Here we load the database driver for Oracle database
        //Class.forName("oracle.jdbc.driver.OracleDriver");
        //For mySQL database the above code would look like this:
        Class.forName("com.mysql.jdbc.Driver");
        //For mySQL database the above code would look like
        //something this.
        //Notice that here we are accessing mg_db database
         String url="jdbc:mysql://mysql.cc.puv.fi:3306/e1100587_project";
        //Here we create a connection to the database
        conn=DriverManager.getConnection(url, "e1100587", "vxnB9ws3N5M7");
        //Here we create the statement object for executing SQL commands.
        stmt=conn.createStatement();
        //Here we execute the SQL query and save the results to a ResultSet object
        stmt.executeUpdate(updateUserinfo);
        
        }catch(Exception e){
            /*---------------*/
            out.println("<tr>"+ e.getMessage());
        }finally {
        try {
        //Here we close all open streams
        if(stmt !=null)
        stmt.close();
        if(conn!=null)
        conn.close();
        }catch(SQLException sqlexc){
            /*----------------*/
            out.println("EXception occurred while closing streams!");
        }
        }
        }
 public void updateUser(HttpServletRequest request, HttpServletResponse response,String updateUserinfo) throws ServletException, IOException
 {
        /*
        * Here we initialize tools for making the database connection
        * and reading from the database
        */

        Connection conn=null;
        Statement stmt=null;
 	    String separator = System.getProperty("file.separator");       
        String imageDir = getServletContext().getRealPath(separator) + "images" + separator;
        
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        
        ResultSet resultSetuser=null;        
        ResultSetMetaData resultSetuserMeatData=null;
        try {
        //Here we load the database driver for Oracle database
        //Class.forName("oracle.jdbc.driver.OracleDriver");
        //For mySQL database the above code would look like this:
        Class.forName("com.mysql.jdbc.Driver");
        //For mySQL database the above code would look like
        //something this.
        //Notice that here we are accessing mg_db database
         String url="jdbc:mysql://mysql.cc.puv.fi:3306/e1100587_project";
        //Here we create a connection to the database
        conn=DriverManager.getConnection(url, "e1100587", "vxnB9ws3N5M7");
        //Here we create the statement object for executing SQL commands.
        stmt=conn.createStatement();
        //Here we execute the SQL query and save the results to a ResultSet object
        resultSetuser=stmt.executeQuery(updateUserinfo);    
        resultSetuserMeatData=resultSetuser.getMetaData();    
        int usercolumn=resultSetuserMeatData.getColumnCount();
        if(!updateUserinfo.contains("view"))
        {
        	out.println("<table border='1'><tr>");
      //Here we print column names in table header cells
      //Pay attention that the column index starts with 1
      for(int i=1; i<=usercolumn; i++) {
      out.println("<th> "+resultSetuserMeatData.getColumnName(i)+ "</th>");
      }
      out.println("</tr>");      
      while(resultSetuser.next())
        	{
    	  		out.println("<tr>");
                //Here we print the value of each column
    	  		for(int i=1; i<=usercolumn; i++) 
    	  		{
                	if (resultSetuser.getObject(i)!=null)
                	out.println("<td>" + resultSetuser.getObject(i).toString()+"</td>");
                	else
                		out.println("<td>---</td>");
            	}
                 out.println("</tr>");
        	}

        }
        else
        {
            File destinationFile =null;
            FileOutputStream fos=null;
        	out.println("<table border='1'><tr>");
            //Here we print column names in table header cells
            //Pay attention that the column index starts with 1
            for(int i=1; i<=usercolumn; i++) {
            out.println("<th> "+resultSetuserMeatData.getColumnName(i)+ "</th>");
            }
            out.println("</tr>");      
            while(resultSetuser.next())
              	{
          	  		out.println("<tr>");
                      //Here we print the value of each column
          	  		for(int i=1; i<=usercolumn; i++) 
          	  		{
                      	if (resultSetuser.getObject(i)!=null)
                      	{
                      		out.println("<td>" + resultSetuser.getObject(i).toString()+"</td>");                      	
                      	}
                      	else
                      		out.println("<td>---</td>");
                  	}
                       out.println("</tr>");
              	}
        }
        
        }catch(Exception e){
            /*---------------*/
            out.println("<tr>"+ e.getMessage());
        }finally {
        try {
        //Here we close all open streams
        if(stmt !=null)
        stmt.close();
        if(conn!=null)
        conn.close();
        }catch(SQLException sqlexc){
            /*----------------*/
            out.println("EXception occurred while closing streams!");
        }
        }
        }
 public int IDget(HttpServletRequest request, HttpServletResponse response,String Type)
 throws ServletException, java.io.IOException 
 {
     int UserID=0;
     String query;
     String tablename;

    if(Type.equals("customer"))
     {
    	 tablename="customer_info";
     }
     else
         tablename=Type;
     
     String IDcolumn=Type+"_ID";
     query="SELECT "+IDcolumn+" FROM "+tablename+" WHERE email='"+ID+"'";

     /*
     * Here we initialize tools for making the database connection
     * and reading from the database
     */
     Connection conn=null;
     Statement stmt=null;
     
     response.setContentType("text/html");   
     PrintWriter out = response.getWriter();
     
     ResultSet resultSet=null;
     //ResultSetMetaData resultSetMeatData=null;


     try {
     //Here we load the database driver for Oracle database
     //Class.forName("oracle.jdbc.driver.OracleDriver");

     //For mySQL database the above code would look like this:
     Class.forName("com.mysql.jdbc.Driver");


     //Here we set the JDBC URL for the Oracle database
     //String url="jdbc:oracle:thin:@db.cc.puv.fi:1521:ora817";

     //For mySQL database the above code would look like
     //something this.
     //Notice that here we are accessing mg_db database
      String url="jdbc:mysql://mysql.cc.puv.fi:3306/e1100587_project";


     //Here we create a connection to the database
     conn=DriverManager.getConnection(url, "e1100587", "vxnB9ws3N5M7");

     //Here we create the statement object for executing SQL commands.
     stmt=conn.createStatement();

     //Here we execute the SQL query and save the results to a ResultSet object
     resultSet=stmt.executeQuery(query);

     //Here we get the metadata of the query results
     //resultSetMeatData=resultSet.getMetaData();
     
     resultSet.next();
     UserID=Integer.parseInt(resultSet.getObject(1).toString());

     return UserID;
     
     }catch(Exception e){
         /*---------------*/
         out.println("<tr>"+ e.getMessage());
     }finally {
     try {
     //Here we close all open streams
     if(stmt !=null)
     stmt.close();

     if(conn!=null)
     conn.close();

     }catch(SQLException sqlexc){
         /*----------------*/
         out.println("EXception occurred while closing streams!");
     }

     }
 return 0;
 }
 public void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, java.io.IOException {doPost(request,response);}
 
}
