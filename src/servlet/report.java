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


public class report extends HttpServlet {

  String sale1="SELECT r.product_ID , sum((i.price)*r.amount) amount_of_sale FROM product_info i,purchase_record r WHERE i.product_ID=r.product_ID ";
  String sale2="GROUP BY r.product_ID";	
  String cost1="SELECT r.product_ID , sum((i.cost)*r.amount) amount_of_cost,r.shipment_expense FROM product_info i,purchase_record r WHERE i.product_ID=r.product_ID ";
  String cost2="GROUP BY r.product_ID";
  String benefit_of_each_product1="SELECT r.product_ID , sum((i.price-i.cost)*r.amount) benefit,r.shipment_expense FROM product_info i,purchase_record r WHERE i.product_ID=r.product_ID ";
  String benefit_of_each_product2="GROUP BY r.product_ID";
  String benefit_of_all_products="SELECT sum((i.price-i.cost)*r.amount) FROM product_info i,purchase_record r WHERE i.product_ID=r.product_ID";
  String shipmentexpense="SELECT sum(shipment_expense) FROM purchase_record";


 //Register Userinfo=new Register();
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, java.io.IOException 
   {
    String year=request.getParameter("year"); 
	String month=request.getParameter("month");
	String order_date="order_date>'"+year+"-"+month+"-00' AND order_date<'"+year+"-"+month+"-32'";
    response.setContentType("text/html");
  // Here we use a PrintWriter to send text data
  // to the client who has requested the servlet
    PrintWriter out = response.getWriter();
    out.println("<html><head>");

    out.println("<title>Report Page</title></head><body>");

    out.println("<center>");
    out.println("<html>" +
    		"<head><title>REPORT</title></head><body>" +
    		"<center>" +
    		"<h2><blink>Welcome to the Universal Store online</blink></h2>" +
    		"<h2>Purchase report</h2>" +
    		"<form method='POST' action='report.html'>" +
    		"<table border='0'>" +
    		"<tr><td>" +
    		"Year</td>" +
    		"<td>Month</td>" +
    		"</tr><tr><td>" +
    		"<select name='year'>" +
    		"<option value='0'>ALL" +
    		"<option value='2012'>2012" +
    		"<option value='2011'>2011" +
    		"<option value='2010'>2010" +
    		"</select>" +
    		"</td></td><td>" +
    		"<select name='month'>" +
    		"<option value='01'>1" +
    		"<option value='02'>2" +
    		"<option value='03'>3" +
    		"<option value='04'>4" +
    		"<option value='05'>5" +
    		"<option value='06'>6" +
    		"<option value='07'>7" +
    		"<option value='08'>8" +
    		"<option value='09'>9" +
    		"<option value='10'>10" +
    		"<option value='11'>11" +
    		"<option value='12'>12" +
    		"</select>" +
    		"</td>" +
    		"<tr><td valign='top'>" +
    		"<input type='submit' value='GO!'>" +
    		"</td><td>" +
    		"</form>" +
    		"<form method='LINK' action='admin.html'>" +
    		"<INPUT type='submit' value='Back'>" +
    		"</form></td>" +
    		"</tr>" +
    		"</table>" +
    		"</center>" +
    		"</body>" +
    		"</html>");
    if(year==null)
    {year="-1";}
    else
    	{
        out.println("<table border='0'>");  	
    	if(year.equals("0"))
	{
     out.println("Overall Report");
     out.println("Amount of Sale");
	 updateUser(request,response,sale1+sale2);
     out.println("Amount of Cost");
	 updateUser(request,response,cost1+cost2);	 
     out.println("benefit of each product");
	 updateUser(request,response,benefit_of_each_product1+benefit_of_each_product2);
     out.println("benefit of all products");
	 updateUser(request,response,benefit_of_all_products);
     out.println("shipment expense");
	 updateUser(request,response,shipmentexpense);
	}
	else
	{
	     out.println("Report of"+year+"-"+month+"");
     out.println("Amount of Sale");
	 updateUser(request,response,sale1+" AND "+order_date+sale2);
     out.println("Amount of Cost");
	 updateUser(request,response,cost1+" AND "+order_date+cost2);	 
     out.println("benefit of each product");
	 updateUser(request,response,benefit_of_each_product1+" AND "+order_date+benefit_of_each_product2);
     out.println("benefit of all products");
	 updateUser(request,response,benefit_of_all_products+" AND "+order_date);
     out.println("shipment expense");
	 updateUser(request,response,shipmentexpense+" WHERE "+order_date);	 

	}
     out.println("<p><a href='admin.html'>Back</p>");
     out.println("</center>");

     out.println("</body></html>");
	
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

 public void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, java.io.IOException {doPost(request,response);}
 
}
