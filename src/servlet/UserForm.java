//This is the content of servlet.UserForm.java file:
package servlet;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserForm extends HttpServlet {

 // Here we define a directory for user information.
 public static String ID = null; 
 public static int TypeNum=0;
 
 String Password = null,Type ="0";
 String Username=null;
 private static final long serialVersionUID = 1L;
 
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, java.io.IOException {

  // Here we set the MIME type of the response, "text/html"

  Type = request.getParameter("type"); 	 
  ID = request.getParameter("ID");
  Password = request.getParameter("password");
  
  TypeNum=checkNum(Type);
	 
  String[] TypeName=new String[3]; 
  TypeName[0]=new String("Customer");
  TypeName[1]=new String("Supplier");
  TypeName[2]=new String("Owner");
  
  boolean idCheck = checkParameter(ID)&&(ID.indexOf("@") != -1);
  boolean pCheck = checkParameter(Password);  
  
  response.setContentType("text/html");

  // Here we use a PrintWriter to send text data

  // to the client who has requested the servlet

  PrintWriter out = response.getWriter();

  // Here we start assembling the HTML content

  out.println("<html><head>");

  out.println("<title>Login Page</title></head><body>");

  out.println("<center>");

  out.println("<h2>Welcome to the Universal Store online</h2>" +
  		"<h2>Please submit your information to login</h2>");

  // Here we set the value for method to post, so that

  // the servlet service method calls doPost in the

  // response to this form submit
  out.println("<table border='0'><tr><td valign='top'>");
  out.println("<form method='POST' + action='index.html'>");


  out.println("Type</td><td>"+
  		"<select name='type'>");
  for(int i=0;i<3;i++)
  {
  		out.println("<option value='"+i+"'");
  		if(i==TypeNum)
  			out.println(" selected='selected'");
  		out.println(">"+TypeName[i]);
  }
  out.println("</select>"+
        "</td></tr><tr><td valign='top'>");

  out.println("Your ID</td>");

  if (idCheck)
   out.println("<td valign='top'><input type='text' name='ID' value='"
     + ID + "'  size='20'>");
  else
   out.println("<td valign='top' bgcolor='red'><input type='text' name='ID' size='20'>");

  out.println("</td></tr><tr><td valign='top'>");

  out.println("Password: </td>");

  if (pCheck)
   out.println("<td valign='top'><input type='password' name='password' value='"
     + Password + "' size='20'>");
  else
   out.println("<td valign='top' bgcolor='red'><input type='password' name='password' size='20'>");

  //out.println("</td></tr><tr><td valign='top'>");



  out.println("</td></tr><tr><td valign='top'>");

  out.println("<input type='submit' value='Login'></td></form>");
  out.println("<td><form method='LINK' action='register.html'><input type='submit' value='Register'>");

  out.println("</form></td></tr></table>");

  out.println("</center>");

  out.println("</body></html>");
  

  if (idCheck && pCheck&&checkUser(request,response))//After checking the format, then check the ID and the password
   {

	  response.reset();

	  // Here we set the MIME type of the response, "text/html"
	  response.setContentType("text/html");

	  // Here we use a PrintWriter to send text data to the client

	  out = response.getWriter();

	  // Here we start assembling the HTML content

	  out.println("<html><head>");

	  out.println("<title>Welcome</title></head><body>");

	  out.println("<center>");
	  
    	  if(TypeNum==0)//
    	  {
        	  out.println("<p>Welcome to our Store "+Username+TypeNum+"</p>");
    		  out.println("<br><br><a href='customer.html'>Forward</a>");
    	  }
    	  if(TypeNum==1)
    	  {
        	  out.println("<p>Welcome to our Store "+Username+"</p>");
    		  out.println("<br><br><a href='supplier.html'>Forward</a>");
    	  }
    	  if(TypeNum==2)
    	  {
    	      out.println("<p>Welcome to our Store administor</p><p>"+ID+"</p>");    	  
    		  out.println("<br><br><a href='admin.html'>Forward</a>");
    	  }

	  out.println("</center>");
	  out.println("</body></html>");
   }
  }

 private boolean checkParameter(String parameter) 
 {
	  if (parameter != null && !parameter.equals("")
	    && !parameter.equals("null"))
	   return true;
	  return false;

	 }

 private int checkNum(String Num)
 {
	 if(Num==null)
	 {return -1;}
	 if(Num.equals("0"))
		 return 0;
	 if(Num.equals("1"))
		 return 1;
	 if(Num.equals("2"))
		 return 2;
	 return 0;
 }
 public boolean checkUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{


	String tableName ="customer_info";
	if(TypeNum==0)
		tableName="customer_info";
	else if(TypeNum==1)
		tableName="supplier_info";
	else if(TypeNum==2)
	{
		if(ID.equals("admin@store.com")&&Password.equals("admin"))
			return true;
		else return false;
	}

	String query="select name from " + tableName+ " where email='"+ID+"' AND password='"+Password+"'";

	/*
	* Here we initialize tools for making the database connection
	* and reading from the database
	*/
	Connection conn=null;
	Statement stmt=null;
	
	response.setContentType("text/html");
	PrintWriter out=response.getWriter();
	
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
	Username=resultSet.getObject(1).toString();
	if (Username!=null)
		return true;
	else
		return false;

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
	return false;

	}
 public void doGet(HttpServletRequest request, HttpServletResponse response)
		   throws ServletException, java.io.IOException {doPost(request,response);}
 
}