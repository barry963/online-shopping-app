package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

public class Register extends HttpServlet {
    public class User
    {
        String Name="none";
        String Gender="none";//Only for customer
        int Age=0;//Only for customer
        String Email="none";
        String Address="none";
        String District="none";
        String Postcode="none";
        String Phone="none";
        int City=0;
        int Country=0;
        String Password="none";
        public User(){}
        
public void Set(String n, String gender, int age, String email,
String address, String district, String postcode, String phone,
int city, int country, String password) {
Name = n;
Gender = gender;
Age = age;
Email = email;
Address = address;
District = district;
Postcode = postcode;
Phone = phone;
City = city;
Country = country;
Password = password;
}

public String getName() {
return Name;
}
public void setName(String name) {
Name = name;
}
public String getGender() {
return Gender;
}
public void setGender(String gender) {
Gender = gender;
}
public int getAge() {
return Age;
}
public void setAge(int age) {
Age = age;
}
public String getEmail() {
return Email;
}
public void setEmail(String email) {
Email = email;
}
public String getAddress() {
return Address;
}
public void setAddress(String address) {
Address = address;
}
public String getDistrict() {
return District;
}
public void setDistrict(String district) {
District = district;
}
public String getPostcode() {
return Postcode;
}
public void setPostcode(String postcode) {
Postcode = postcode;
}
public String getPhone() {
return Phone;
}
public void setPhone(String phone) {
Phone = phone;
}
public int getCity() {
return City;
}
public void setCity(int city) {
City = city;
}
public int getCountry() {
return Country;
}
public void setCountry(int country) {
Country = country;
}
public String getPassword() {
return Password;
}
public void setPassword(String password) {
Password = password;
}    
    }
     int TypeNum;     
     User user=new User();
     
public int IDgenerator(HttpServletRequest request, HttpServletResponse response,String Type)
throws ServletException, java.io.IOException 
{
    int UserID=0;
    String query;
    String tablename;
    if(Type.equals("customer")||Type.equals("supplier"))
        tablename=Type+"_info";
    else
    tablename=Type;
    
    String IDcolumn=Type+"_ID";
    query="SELECT "+IDcolumn+" FROM "+tablename;

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
    
    while((resultSet.next())&&(!resultSet.last()));
    UserID=Integer.parseInt(resultSet.getObject(1).toString())+1;

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

 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, java.io.IOException {
     String Type=request.getParameter("type");
     //TypeNum=checkNum(Type);
TypeNum=Integer.parseInt(Type);
     if(Type==null)
     {}
     else
     {
     if(Type.equals("0"))//Customer
     {
         String Gender="MALE";
         if(request.getParameter("cgender").equals("1"))
             Gender="MALE";
         else
             Gender="FEMALE";
         user.Set(request.getParameter("cname"),Gender,Integer.parseInt(request.getParameter("cage")),request.getParameter("cemail"),request.getParameter("caddress"),request.getParameter("cdistrict"),request.getParameter("cpostcode"),request.getParameter("cphone"),Integer.parseInt(request.getParameter("ccity")),Integer.parseInt(request.getParameter("ccountry")),request.getParameter("cpassword"));
     }
     if(Type.equals("1"))//Supplier         
     {
         user.Set(request.getParameter("sname"),"none",0,request.getParameter("semail"),request.getParameter("saddress"),request.getParameter("sdistrict"),request.getParameter("spostcode"),request.getParameter("sphone"),Integer.parseInt(request.getParameter("scity")),Integer.parseInt(request.getParameter("scountry")),request.getParameter("spassword"));
     }
     }
  

  updateUser(request,response);
  response.setContentType("text/html");

  // Here we use a PrintWriter to send text data

  // to the client who has requested the servlet

  PrintWriter out = response.getWriter();

  // Here we start assembling the HTML content

  out.println("<html><head>");

  out.println("<title>Login Page</title></head><body>");

  out.println("<center>");

  out.println("<h2>Welcome to the Universal Store online</h2>" + 
          "<h2>Thanks for your registration</h2>");

  out.println("<table border='0'><tr><td valign='top'>");


  out.println("Type</td><td>"+TypeNum+"(0:customer||1:supplier)</td>");

  out.println("<tr><td valign='top'>Your ID</td>");
  out.println("<td>"+ user.getEmail() + "</td></tr>");

  out.println("<tr><td valign='top'>Your password</td>");
  out.println("<td>"+ user.getPassword() + "</td></tr>");
   
  out.println("<tr><td valign='top'>");

  out.println("<a href='index.html'>Login</a></td></tr></table>");

  out.println("</center>");

  out.println("</body></html>");
 
  }
 /*
 private int checkNum(String Num)
 {
     if(Num==null)
         return 0;
     if(Num.equals("0"))
         return 0;
     if(Num.equals("1"))
         return 1;
     if(Num.equals("2"))
         return 2;
     return 0;
 }
 */
 
 public void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
 {

        String InsertUser=null;
        String InsertAddress=null;
        int UserID=0;
        int addressID=0;
    addressID=IDgenerator(request,response,"address");
   
        if(TypeNum==0)
        {
        UserID=IDgenerator(request,response,"customer");        
            InsertUser="INSERT customer_info(customer_ID,name,gender,age,email,address_ID,password) VALUES("+UserID+",'"+user.getName()+"','"+user.getGender()+"',"
            +user.getAge()+",'"+user.getEmail()+"',"+addressID+",'"+user.getPassword()+"')";
        }
        else 
            if(TypeNum==1)
            {
            UserID=IDgenerator(request,response,"supplier");        
                InsertUser="INSERT supplier_info(supplier_ID,name,email,address_ID,password) VALUES("+UserID+",'"+user.getName()
            +"','"+user.getEmail()+"',"+addressID+",'"+user.getPassword()+"')";
            }
        InsertAddress="INSERT address(address_ID,address,district,city_ID,postal_code,phone) VALUES("+addressID+",'"+user.getAddress()+"','"
                +user.getDistrict()+"',"+user.getCity()+",'"+user.getPostcode()+"','"+user.getPhone()+"')";

        /*
        * Here we initialize tools for making the database connection
        * and reading from the database
        */
        Connection conn=null;
        Statement stmt=null;
        
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        
        //ResultSet resultSet=null;
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
        stmt.executeUpdate(InsertUser);
        stmt.executeUpdate(InsertAddress);
        //Here we get the metadata of the query results

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
 
/*
 private String checkType(String Num)
 {
     if(Num==null)
         return "Customer";
     if(Num.equals("0"))
         return "Customer";
     if(Num.equals("1"))
         return "Supplier";
     return "Customer";
 }
 */
 
 public void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, java.io.IOException {doPost(request,response);}
 
}
