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
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class comparision extends HttpServlet {
	
 int Num=UserForm.TypeNum;
 String ID=UserForm.ID;
 String searchProductforcustomer="SELECT product_info.product_ID,view, product_info.name, product_type.name,date_of_manufacture,warranty_time,amount,price,product_info.description FROM product_info,view,product_type WHERE product_info.view_ID=view.view_ID AND product_info.type_ID=product_type.product_type_ID";
 
 //Register Userinfo=new Register();
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, java.io.IOException {
   
     response.setContentType("text/html");
  // Here we use a PrintWriter to send text data
  // to the client who has requested the servlet
  PrintWriter out = response.getWriter();
  
  out.println("<html><head>");
  out.println("<title>Comparision</title></head><body>");
  out.println("<center>");
  out.println("<h2>Welcome to the Universal Store online"+ID+"</h2>");
  // Here we set the value for method to post, so that
  // the servlet service method calls doPost in the
  // response to this form submit
  out.println("<table border='0'><tr><td valign='top'>");
  out.println("<form method='POST' + action='comparision.html'>");
  out.println("<table border='0'>"+
	"<tr><td>ID1</td><td bgcolor='red' ><input type='text' size='20' name='ID1'" +
	"</td></tr>"+"<tr><td>ID2</td><td bgcolor='red' ><input type='text' size='20' name='ID2'" +
	"</td></tr>");
  
  out.println("<tr><td valign='top'>");
  out.println("<input type='submit' value='compare!!!'></td></form>");
  if(Num==0)//customer
  out.println("</td></tr><tr><td><a href='customer.html'>Back</td></tr></table>");
  if(Num==2)//admin    
  out.println("</td></tr><tr><td><a href='admin.html'>Back</td></tr></table>");
  
	  String ID1=request.getParameter("ID1");
	  String ID2=request.getParameter("ID2");
  if(request.getParameter("ID1")!=null)
	{	  
	String Product=" AND (product_info.product_ID="+ID1+" OR product_info.product_ID="+ID2+")";
     updateUser(request,response,searchProductforcustomer+Product);                                                                       
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
