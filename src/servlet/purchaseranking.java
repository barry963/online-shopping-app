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
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class purchaseranking extends HttpServlet {
 // Here we define a directory for user information.
    UserForm User=new UserForm();
 int Num=UserForm.TypeNum;
 List<String> IDlist=new ArrayList<String>();
 
 String getrankingID="SELECT product_ID FROM purchase_record GROUP BY product_ID ORDER BY sum(amount) DESC";
 String searchProductforcustomer="SELECT product_info.product_ID,view, product_info.name, product_type.name type_name,date_of_manufacture,warranty_time,price,product_info.description,sum(purchase_record.amount) FROM product_info,view,product_type,purchase_record WHERE product_info.view_ID=view.view_ID AND product_info.type_ID=product_type.product_type_ID AND product_info.product_ID=purchase_record.product_ID AND product_info.product_ID=";
 String searchProductforadmin="SELECT product_info.product_ID,view, product_info.name, product_type.name type_name,date_of_manufacture,warranty_time,cost,price,product_info.description,sum(purchase_record.amount) FROM product_info,view,product_type,purchase_record WHERE product_info.view_ID=view.view_ID AND product_info.type_ID=product_type.product_type_ID AND product_info.product_ID=purchase_record.product_ID AND product_info.product_ID=";

 //Register Userinfo=new Register();
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, java.io.IOException {
   
     response.setContentType("text/html");
  // Here we use a PrintWriter to send text data
  // to the client who has requested the servlet
  PrintWriter out = response.getWriter();
  updateUser(request,response,getrankingID);
  Iterator it = IDlist.iterator();
  if(Num==2)
  {
    while(it.hasNext())
	{
	updateUser(request,response,searchProductforadmin+Integer.parseInt((String)it.next()));
	}
     out.println("<p><a href='admin.html'>Back</p>");
  }
  else if(Num==0)
{
    while(it.hasNext())
	{
	updateUser(request,response,searchProductforcustomer+Integer.parseInt((String)it.next()));
	}
     out.println("<p><a href='customer.html'>Back</p>");
  }
  else if(Num==1)
  {
      while(it.hasNext())
  	{
  	updateUser(request,response,searchProductforadmin+Integer.parseInt((String)it.next()));
  	}
       out.println("<tr><a href='supplier.html'>Back</tr>");
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
        if(!updateUserinfo.contains("view"))
        {
      //Here we print column names in table header cells
      //Pay attention that the column index starts with 1
      while(resultSetuser.next())
        	{
                //Here we print the value of each column

				IDlist.add(resultSetuser.getObject(1).toString());

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
