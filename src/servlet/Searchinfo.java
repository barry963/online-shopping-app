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
public class Searchinfo extends HttpServlet {
 // Here we define a directory for user information.
    UserForm User=new UserForm();
 int Num=UserForm.TypeNum;
 String ID=User.ID;
 String searchCustomerinfo="SELECT name,gender,age,email,password,address,district,postal_code,city,country,phone FROM customer_info,address,city,country WHERE email='"+ID+"' AND address.address_ID=(SELECT address_ID FROM customer_info WHERE email='"+ID+"') AND city=(SELECT city FROM city where city_ID=(SELECT city_ID FROM address WHERE address_ID=(SELECT address_ID FROM customer_info WHERE email='"+ID+"'))) AND country = (SELECT country FROM country WHERE country_ID=(SELECT country_ID FROM city where city_ID=(SELECT city_ID FROM address WHERE address_ID=(SELECT address_ID FROM customer_info WHERE email='"+ID+"'))))";  
 String searchSupplierinfo="SELECT name,email,password,address,district,postal_code,city,country,phone FROM supplier_info,address,city,country WHERE email='"+ID+"' AND address.address_ID=(SELECT address_ID FROM supplier_info WHERE email='"+ID+"') AND city=(SELECT city FROM city where city_ID=(SELECT city_ID FROM address WHERE address_ID=(SELECT address_ID FROM supplier_info WHERE email='"+ID+"'))) AND country = (SELECT country FROM country WHERE country_ID=(SELECT country_ID FROM city where city_ID=(SELECT city_ID FROM address WHERE address_ID=(SELECT address_ID FROM supplier_info WHERE email='"+ID+"'))))";  
 String searchProductforcustomer="SELECT product_info.product_ID,view, product_info.name, product_type.name type_name,date_of_manufacture,warranty_time,amount,price,product_info.description FROM product_info,view,product_type WHERE product_info.view_ID=view.view_ID AND product_info.type_ID=product_type.product_type_ID";
 String searchProductforadmin="SELECT product_info.product_ID,view, product_info.name, product_type.name type_name,date_of_manufacture,warranty_time,amount,cost,price,product_info.description FROM product_info,view,product_type WHERE product_info.view_ID=view.view_ID AND product_info.type_ID=product_type.product_type_ID";
 String searchCustomerforadmin="SELECT customer_info.customer_ID,name,gender,age,email,password,address,district,postal_code,city,country,phone FROM customer_info,address,city,country WHERE address.address_ID=customer_info.address_ID  AND address.city_ID=city.city_ID AND city.country_ID=country.country_ID";
 String searchSupplierforadmin="SELECT supplier_info.supplier_ID,name,email,password,address,district,postal_code,city,country,phone FROM supplier_info,address,city,country WHERE address.address_ID=supplier_info.address_ID  AND address.city_ID=city.city_ID AND city.country_ID=country.country_ID";
 String searchView="SELECT view_ID,view,name,description FROM view";
 String searchType="SELECT * FROM product_type";
 String searchPurchase="SELECT * FROM purchase_record WHERE shipment_expense!=0";
 String searchOrder="SELECT * FROM purchase_record WHERE shipment_expense=0";
 
 //Register Userinfo=new Register();
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, java.io.IOException {
   
     response.setContentType("text/html");
  // Here we use a PrintWriter to send text data
  // to the client who has requested the servlet
  PrintWriter out = response.getWriter();
  
     if(Num==2)
{
  // Here we set the MIME type of the response, "text/html"
  
  // Here we start assembling the HTML content
  String SearchType = request.getParameter("type");     	 
  out.println("<html><head>");
  out.println("<title>Search Page for admin</title></head><body>");
  out.println("<center>");
  out.println("<h2>Welcome to the Universal Store online"+ID+"</h2>");
  // Here we set the value for method to post, so that
  // the servlet service method calls doPost in the
  // response to this form submit
  out.println("<table border='0'><tr><td valign='top'>");
  out.println("<form method='POST' + action='search.html'>");
  out.println("Type</td><td>");
  out.println("<select name='type'>");
  out.println("<option value='product'>Product");
  out.println("<option value='supplier'>Supplier");
  out.println("<option value='customer'>Customer"); 
  out.println("<option value='product_view'>Product view"); 
  out.println("<option value='product_type'>Product type"); 
  out.println("<option value='purchase_history'>purchase history"); 
  out.println("<option value='order_history'>order history"); 
  out.println("</select></td></tr>");
  out.println("<tr><td valign='top'>");
  out.println("<input type='submit' value='Search'></td></form>");
  out.println("</td></tr><tr><td><a href='admin.html'>Back</td></tr></table>");
  if(SearchType==null)
  	{}
  	else
  	{
  		if(SearchType.equals("product"))
  			{updateUser(request,response,searchProductforadmin);}
  		if(SearchType.equals("supplier"))
  			{updateUser(request,response,searchSupplierforadmin);}
  		if(SearchType.equals("customer"))
  			{updateUser(request,response,searchCustomerforadmin);}
  		if(SearchType.equals("product_view"))
			{updateUser(request,response,searchView);}
  		if(SearchType.equals("product_type"))
			{updateUser(request,response,searchType);}
  		if(SearchType.equals("purchase_history"))
		{updateUser(request,response,searchPurchase);}
  		if(SearchType.equals("order_history"))
		{updateUser(request,response,searchOrder);}
  	}
  }
  else if(Num==0)
{
	  String SearchType = request.getParameter("type");  
    out.println("<html><head>");
  out.println("<title>Search Page for customer</title></head><body>");
  out.println("<center>");
  out.println("<h2>Welcome to the Universal Store online"+ID+"</h2>");
  // Here we set the value for method to post, so that
  // the servlet service method calls doPost in the
  // response to this form submit
  out.println("<table border='0'><tr><td valign='top'>");
  out.println("<form method='POST' + action='search.html'>");
  out.println("Type</td><td>");
  out.println("<select name='type'>");
  out.println("<option value='product'>Product");
  out.println("<option value='personal'>Personal"); 
  out.println("</select></td></tr>");
  out.println("<tr><td valign='top'>");
  out.println("<input type='submit' value='Search'></td></form>");
  out.println("</td></tr><tr><td><a href='customer.html'>Back</td></tr></table>");
  if(SearchType==null)
  	{}
  		else
  		{
  			if(SearchType.equals("product"))
  				{updateUser(request,response,searchProductforcustomer);}
  			if(SearchType.equals("personal"))
  				{updateUser(request,response,searchCustomerinfo);}
  		}
  }
  else if(Num==1)
  {
	  String SearchType = request.getParameter("type"); 
      out.println("<html><head>");
  out.println("<title>Search Page for supplier</title></head><body>");
  out.println("<center>");
  out.println("<h2>Welcome to the Universal Store online"+ID+"</h2>");
  // Here we set the value for method to post, so that
  // the servlet service method calls doPost in the
  // response to this form submit
  out.println("<table border='0'><tr><td valign='top'>");
  out.println("<form method='POST' + action='search.html'>");
  out.println("Type</td><td>");
  out.println("<select name='type'>");
  out.println("<option value='product'>Product");
  out.println("<option value='personal'>Personal"); 
  out.println("</select></td></tr>");
  out.println("<tr><td valign='top'>");
  out.println("<input type='submit' value='Search'></td></form>");
  out.println("</td></tr><tr><td><a href='supplier.html'>Back</td></tr></table>"); 
  if(SearchType==null)
  	{}
  else
  	{
	  if(SearchType.equals("product"))
	  	{updateUser(request,response,searchProductforadmin);}
	  if(SearchType.equals("personal"))
	  	{updateUser(request,response,searchSupplierinfo);}
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
      		out.println("</center>");
      		out.println("</body></html>");
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
                      		if(i==2)
                      		{

                      		    //Here we create a File object, which refers to
                      		    //the name read from the first column of the table
                      		    destinationFile = new File(imageDir + resultSetuser.getObject(1).toString());

                      		    //Here we prepare a FileOutputStream to write to the
                      		    //destination file.
                      		    fos = new FileOutputStream(destinationFile);



                      		    //Here we initialize the inputStream by reading from
                      		    //the second column of the table
                      		    InputStream is = resultSetuser.getBinaryStream(i);
                      		    byte[] imageBuffer = new byte[is.available()];

                      		    //Here we read the image data from the database to
                      		    //the memory area.
                      		    is.read(imageBuffer);

                      		    //Here write the image data from memory to the
                      		    //file.
                      		    fos.write(imageBuffer);

                      		    //Here we close the output and input streams.
                      		    fos.close();
                      		  out.println("<td>" + "<img src='" + "images" + separator+resultSetuser.getObject(1).toString()+"' alt='Error' width='200' height='150'/>"+"</td>");
                      		}
                      		else
                      			out.println("<td>" + resultSetuser.getObject(i).toString()+"</td>");
                      	
                      	}
                      	else
                      		out.println("<td>---</td>");
                  	}
                       out.println("</tr>");
              	}
            		out.println("</center>");
            		out.println("</body></html>");
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
