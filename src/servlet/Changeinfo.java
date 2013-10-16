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
public class Changeinfo extends HttpServlet {
	
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
	public class Product
	{
		String name="none";
		String date_of_manufacture="0000-00-00";
		int warranty_time=0;
		int amount=0;
		double price=0;
		double cost=0;
		String description="none";
        
		public Product()
		{}
		public void set(String n,String d,int w,int a, double p,double c, String de)
		{
			name=n;date_of_manufacture=d;warranty_time=w;amount=a;price=p;cost=c;description=de;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDate_of_manfacture() {
			return date_of_manufacture;
		}
		public void setDate_of_manfacture(String date_of_manfacture) {
			this.date_of_manufacture = date_of_manfacture;
		}
		public int getWarranty_time() {
			return warranty_time;
		}
		public void setWarranty_time(int warranty_time) {
			this.warranty_time = warranty_time;
		}
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public double getCost() {
			return cost;
		}
		public void setCost(double cost) {
			this.cost = cost;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
	}
	public class view
	{
		String name;
		String description;
		public view(){}
		public void set(String n,String d)
		{
			name=n;
			description=d;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			description = description;
		}
		
	}
	
 // Here we define a directory for user information.
    User temp=new User();
	Product tempProduct=new Product();
	view tempView=new view();
	String tempProductType;
	String tempChooseEmail;
	int tempID;
    UserForm User=new UserForm();

 int Num=UserForm.TypeNum;
 String ID=User.ID;
 String searchCustomerinfo="SELECT name,age,email,password,address,district,postal_code,phone FROM customer_info,address,city,country WHERE email='"+ID+"' AND address.address_ID=(SELECT address_ID FROM customer_info WHERE email='"+ID+"') AND city=(SELECT city FROM city where city_ID=(SELECT city_ID FROM address WHERE address_ID=(SELECT address_ID FROM customer_info WHERE email='"+ID+"'))) AND country = (SELECT country FROM country WHERE country_ID=(SELECT country_ID FROM city where city_ID=(SELECT city_ID FROM address WHERE address_ID=(SELECT address_ID FROM customer_info WHERE email='"+ID+"'))))";  
 String searchSupplierinfo="SELECT name,email,password,address,district,postal_code,phone FROM supplier_info,address,city,country WHERE email='"+ID+"' AND address.address_ID=(SELECT address_ID FROM supplier_info WHERE email='"+ID+"') AND city=(SELECT city FROM city where city_ID=(SELECT city_ID FROM address WHERE address_ID=(SELECT address_ID FROM supplier_info WHERE email='"+ID+"'))) AND country = (SELECT country FROM country WHERE country_ID=(SELECT country_ID FROM city where city_ID=(SELECT city_ID FROM address WHERE address_ID=(SELECT address_ID FROM supplier_info WHERE email='"+ID+"'))))";  
 String searchProductforadmin="SELECT view, product_info.name, product_type.name AS type,date_of_manufacture,warranty_time,amount,cost,price,product_info.description FROM product_info,view,product_type WHERE product_info.view_ID=view.view_ID AND product_info.type_ID=product_type.product_type_ID";
 //Register Userinfo=new Register();
 public void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, java.io.IOException {
   
     response.setContentType("text/html");
  // Here we use a PrintWriter to send text data
  // to the client who has requested the servlet
  PrintWriter out = response.getWriter();
  
     if(Num==2)
{
    	 
out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>" +
		"<html xmlns='http://www.w3.org/1999/xhtml'>"+
			"<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />" +
					"<script  type='text/javascript'>" +
					"var passwordflag=0;");
out.println("function check(arg)" +
		"{" +
		"if(arg=='-1')" +
		"{" +
		"document.getElementById('Order').style.display='block';" +
		"document.getElementById('Customer').style.display='none';" +
		"document.getElementById('Supplier').style.display='none';" +
		"document.getElementById('Product').style.display='none';" +
		"document.getElementById('Product_type').style.display='none';" +
		"document.getElementById('View').style.display='none';" +
		"}"+
		"if(arg=='0')" +
		"{" +
		"document.getElementById('Order').style.display='none';" +
		"document.getElementById('Customer').style.display='block';" +
		"document.getElementById('Supplier').style.display='none';" +
		"document.getElementById('Product').style.display='none';" +
		"document.getElementById('Product_type').style.display='none';" +
		"document.getElementById('View').style.display='none';" +
		"}"+
		"if(arg=='1')" +
		"{" +
		"document.getElementById('Order').style.display='none';" +
		"document.getElementById('Customer').style.display='none';" +
		"document.getElementById('Supplier').style.display='block';" +
		"document.getElementById('Product').style.display='none';" +
		"document.getElementById('Product_type').style.display='none';" +
		"document.getElementById('View').style.display='none';" +
		"}"+
		"if(arg=='2')" +
		"{" +
		"document.getElementById('Order').style.display='none';" +
		"document.getElementById('Customer').style.display='none';" +
		"document.getElementById('Supplier').style.display='none';" +
		"document.getElementById('Product').style.display='block';" +
		"document.getElementById('Product_type').style.display='none';" +
		"document.getElementById('View').style.display='none';" +
		"}"+
		"if(arg=='3')" +
		"{" +
		"document.getElementById('Order').style.display='none';" +
		"document.getElementById('Customer').style.display='none';" +
		"document.getElementById('Supplier').style.display='none';" +
		"document.getElementById('Product').style.display='none';" +
		"document.getElementById('Product_type').style.display='block';" +
		"document.getElementById('View').style.display='none';" +
		"}"+
		"if(arg=='4')" +
		"{" +
		"document.getElementById('Order').style.display='none';" +
		"document.getElementById('Customer').style.display='none';" +
		"document.getElementById('Supplier').style.display='none';" +
		"document.getElementById('Product').style.display='none';" +
		"document.getElementById('Product_type').style.display='none';" +
		"document.getElementById('View').style.display='block';" +
		"}"+
		"}" +
		"</script>" +
		"<title>Change</title>" +
		"<h2>Welcome to the change information page!<h2>" +
		"<h2>Please enter the information</h2>" +
		"</head>" +
		"<body>" +
		"<form action='change.html' method='POST'>" +
		"<table border='0'><tr><td>Type</td><td>" +
		"<select name='type' id='select' onchange='check(this.options[selectedIndex].value)'>" +
		"<option value='-1'>Order</option>" +		
		"<option value='0'>Customer</option>" +
		"<option value='1'>Supplier</option>" +
		"<option value='2'>Product</option>" +
		"<option value='3'>Product Type</option>" +
		"<option value='4'>Product View</option>" +
		"</select>" +
		"</td></tr></table>" +
		
		"<div id='Order' style='display:block'>" +
		"<table border='0'>" +"<tr><td>Purchase ID</td><td bgcolor='red' ><input type='text' size='20' name='purchaseID'" +
			"</td></tr>"+
		"<tr><td>arrive_date</td><td bgcolor='red' ><input type='text' id='arrive_date' size='20' name='arrive_date'" +
		"</td></tr>" +
		"<tr><td>shipment expense</td>" +
		"<td bgcolor='red' ><input type='text' id='shipment_expense' size='20' name='shipment_expense' >" +
		"</td></tr>" +
		"</table>" +
		"</div>" +		
		
		
		
		"<div id='Customer' style='display:none'>" +
		"<table border='0'>" +"<tr><td>Enter the customer email</td><td bgcolor='red' ><input type='text' size='20' name='choosecemail'" +
			"</td></tr>"+
		"<tr><td>Your Name</td><td bgcolor='red' ><input type='text' id='cname' size='20' name='cname'" +
		"</td></tr>" +
		"<tr><td>Age</td>" +
		"<td bgcolor='red' ><input type='text' id='cage' size='20' name='cage' >" +
		"</td></tr>" +
		"<tr><td>Email</td>" +
		"<td bgcolor='red' ><input type='text' id='cemail' size='20' name='cemail' >" +
		"</td></tr>" +
		"<tr><td>Address</td>" +
		"<td bgcolor='red' ><input type='text' id='caddress' size='20' name='caddress' >" +
		"</td></tr>" +
		"<tr><td>District</td>" +
		"<td bgcolor='red' ><input type='text' id='cdistrict' size='20' name='cdistrict' >" +
		"</td></tr>" +
		"<tr><td>Postcode</td>" +
		"<td bgcolor='red' ><input type='text' id='cpostcode' size='20' name='cpostcode' >" +
		"</td></tr>" +
		"<tr><td>Phone</td>" +
		"<td bgcolor='red' ><input type='text' id='cphone' size='20' name='cphone' >" +
		"</td></tr>" +
		"<tr><td>Password</td>" +
		"<td bgcolor='red' ><input type='password'  id='cpassword' size='20' name='cpassword' >" +
		"</td></tr>" +
		"</table>" +
		"</div>" +		
		
		
		"<div id='Supplier' style='display:none'>" +
		"<h2>Supplier</h2>" +
		"<table border='0'>" +
		 "<tr><td>Enter the supplier email</td><td bgcolor='red' ><input type='text' size='20' name='choosesemail'" +
			"</td></tr>"+
		"<tr><td>Your Name</td><td bgcolor='red'  ><input type='text' id='sname' size='20' name='sname' >" +
		"</td></tr>" +
		"<tr><td>Email</td>" +
		"<td bgcolor='red'  ><input type='text' id='semail' size='20' name='semail' >" +
		"</td></tr>" +
		"<tr><td>Address</td>" +
		"<td bgcolor='red' ><input type='text' id='saddress' size='20' name='saddress' >" +
		"</td></tr>" +
		"<tr><td>District</td>" +
		"<td bgcolor='red' ><input type='text' id='sdistrict' size='20' name='sdistrict' >" +
		"</td></tr>" +
		"<tr><td>Postcode</td>" +
		"<td bgcolor='red' ><input type='text' id='spostcode' size='20' name='spostcode' >" +
		"</td></tr>" +
		"<tr><td>Phone</td>" +
		"<td bgcolor='red'  ><input type='text' id='sphone' size='20' name='sphone' >" +
		"</td></tr>" +
		"<tr><td>Password</td>" +
		"<td bgcolor='red'  ><input type='password' id='spassword' size='20' name='spassword' >" +
		"</td></tr>" +
		"</table>" +
		"</div>" +
		
		"<div id='Product' style='display:none'>" +
		"<h2>Product</h2>" +
		"<table border='0'>" +
		 "<tr><td>Enter the product ID</td><td bgcolor='red' ><input type='text' size='20' name='choosepID'" +
			"</td></tr>"+
		"<tr><td>Name</td><td bgcolor='red'  ><input type='text' id='product_name' size='20' name='pname' >" +
		"</td></tr>" +
		"<tr><td>date of manufacture</td>" +
		"<td bgcolor='red' ><input type='text' size='20' name='date_of_manufacture' >" +
		"</td></tr>" +
		"<tr><td>warranty time</td>" +
		"<td bgcolor='red' ><input type='text' size='20' name='warranty_time' >" +
		"</td></tr>" +
		"<tr><td>Amount</td>" +
		"<td bgcolor='red' ><input type='text' size='20' name='amount' >" +
		"</td></tr>" +
		"<tr><td>Price</td>" +
		"<td bgcolor='red'  ><input type='text' size='20' name='price' >" +
		"</td></tr>" +
		"<tr><td>Cost</td>" +
		"<td bgcolor='red'>" +
		"<input type='text' size='20' name='cost' >" +
		"</td>" +
		"</tr>" +
		"<tr><td>Description</td>" +
		"<td>" +
		"<input type='text' size='20' name='product_description' >" +
		"</td>" +
		"</tr>" +
		"</table>" +
		"</div>" +
		
		
		"<div id='Product_type' style='display:none'>" +
		"<h2>Product Type</h2>" +
		"<table border='0'>" +
		 "<tr><td>Enter the product type ID</td><td bgcolor='red' ><input type='text' size='20' name='chooseptID'" +
			"</td></tr>"+
		"<tr><td>Type name</td><td bgcolor='red'  ><input type='text' size='20' name='typename' >" +
		"</td></tr>" +
		"</table>" +
		"</div>" +
		
		"<div id='View' style='display:none'>" +
		"<h2>Product View</h2>" +
		"<table border='0'>" +
		 "<tr><td>Enter the product view ID</td><td bgcolor='red' ><input type='text' size='20' name='choosepvID'" +
			"</td></tr>"+
		"<tr><td>Name</td><td bgcolor='red'  ><input type='text' size='20' name='view_name' >" +
		"</td></tr>" +
		"<tr><td>Description</td>" +
		"<td bgcolor='red'  ><input type='text' size='20' name='view_description' >" +
		"</td></tr>" +
		"</table>" +
		"</div>" +
		
		"<div>" +
		"<table border='0'>" +
		"<tr>" +
		"<td valign='top'>" +
		"<input type='submit' value='Submit'>" +
		"</td><td><p><a href='admin.html'>Back</p></td>" +
		"</tr>" +
		"</table>" +
		"</div>" +
		
		"</form>" +
		"</body>" +
		"</html>");
	String Type=request.getParameter("type");
	if(Type==null)
	{}
	else
		if(Type.equals("-1"))//Order
		{
			String purchaseID=request.getParameter("purchaseID");
			String arrive_date=request.getParameter("arrive_date");
			int shipment_expense=Integer.parseInt(request.getParameter("shipment_expense"));
			String changepurchaserecord="UPDATE purchase_record SET arrive_date='"+arrive_date+"', shipment_expense="+shipment_expense+" WHERE purchase_ID="+purchaseID+" AND shipment_expense=0";
			InsertUserInfo(request,response,changepurchaserecord);
			response.reset();
		     response.setContentType("text/html");
		     // Here we use a PrintWriter to send text data
		     // to the client who has requested the servlet
		     PrintWriter out1 = response.getWriter();
		     out1.println("<p>Successful change!!!</p><p><a href='admin.html'>Back</p>");
		}		
		else
			if(Type.equals("0"))//Customer
		{
			String tempID=request.getParameter("choosecemail");
			temp.Set(request.getParameter("cname"), "none", Integer.parseInt(request.getParameter("cage")), request.getParameter("cemail"), request.getParameter("caddress"), request.getParameter("cdistrict"), request.getParameter("cpostcode"), request.getParameter("cphone"), 0, 0, request.getParameter("cpassword"));
			String queryinfo="UPDATE customer_info SET name='"+temp.getName()+"', age="+temp.getAge()+", email='"+temp.getEmail()+"', password='"+temp.getPassword()+"' WHERE email='"+tempID+"'";
			String queryaddress="UPDATE address SET address='"+temp.getAddress()+"',district='"+temp.getDistrict()+"',postal_code='"+temp.getPostcode()+"',phone='"+temp.getPhone()+"'WHERE address_ID=(SELECT address_ID FROM customer_info WHERE email='"+tempID+"')";
			InsertUserInfo(request,response,queryinfo);
			InsertUserInfo(request,response,queryaddress);
			response.reset();
		     response.setContentType("text/html");
		     // Here we use a PrintWriter to send text data
		     // to the client who has requested the servlet
		     PrintWriter out1 = response.getWriter();
		     out1.println("<p>Successful change!!!</p><p><a href='admin.html'>Back</p>");
		}
	else
		if(Type.equals("1"))//supplier
		{
			String tempID=request.getParameter("choosesemail");
			temp.Set(request.getParameter("sname"), "none", 0, request.getParameter("semail"), request.getParameter("saddress"), request.getParameter("sdistrict"), request.getParameter("spostcode"), request.getParameter("sphone"), 0, 0, request.getParameter("spassword"));		
			String queryinfo="UPDATE supplier_info SET name='"+temp.getName()+"', email='"+temp.getEmail()+"', password='"+temp.getPassword()+"' WHERE email='"+tempID+"'";
			String queryaddress="UPDATE address SET address='"+temp.getAddress()+"',district='"+temp.getDistrict()+"',postal_code='"+temp.getPostcode()+"',phone='"+temp.getPhone()+"'WHERE address_ID=(SELECT address_ID FROM supplier_info WHERE email='"+tempID+"')";
			InsertUserInfo(request,response,queryinfo);
			InsertUserInfo(request,response,queryaddress);	
			response.reset();
		     response.setContentType("text/html");
		     // Here we use a PrintWriter to send text data
		     // to the client who has requested the servlet
		     PrintWriter out1 = response.getWriter();
		     out1.println("<p>Successful change!!!</p><p><a href='admin.html'>Back</p>");

			
		}
	else
		if(Type.equals("2"))//product
		{
			tempID=Integer.parseInt(request.getParameter("choosepID"));
			tempProduct.set(request.getParameter("pname"), request.getParameter("date_of_manufacture"), Integer.parseInt(request.getParameter("warranty_time")),Integer.parseInt(request.getParameter("amount")), Integer.parseInt(request.getParameter("price")), Integer.parseInt(request.getParameter("cost")), request.getParameter("product_description"));
			String queryinfo="UPDATE product_info SET name='"+tempProduct.getName()+"',date_of_manufacture='"+tempProduct.getDate_of_manfacture()+"',warranty_time="+tempProduct.getWarranty_time()+",amount="+tempProduct.getAmount()+",price="+tempProduct.getPrice()+",cost="+tempProduct.getCost()+",description='"+tempProduct.getDescription()+"' WHERE product_ID="+tempID;
			InsertUserInfo(request,response,queryinfo);
			response.reset();
		     response.setContentType("text/html");
		     // Here we use a PrintWriter to send text data
		     // to the client who has requested the servlet
		     PrintWriter out1 = response.getWriter();
		     out1.println("<p>Successful change!!!</p><p><a href='admin.html'>Back</p>"+"UPDATE product_info SET name='"+tempProduct.getName()+"',date_of_manufacture='"+tempProduct.getDate_of_manfacture()+"',warranty_time="+tempProduct.getWarranty_time()+",amount="+tempProduct.getAmount()+",price="+tempProduct.getPrice()+",cost="+tempProduct.getCost()+",product_description='"+tempProduct.getDescription()+"' WHERE product_ID="+tempID
);

		}
	else
		if(Type.equals("3"))//product type
		{
			tempID=Integer.parseInt(request.getParameter("chooseptID"));
			tempProductType=request.getParameter("typename");
			String queryinfo="UPDATE product_type SET name='"+tempProductType+"' WHERE product_type_ID="+tempID;
			InsertUserInfo(request,response,queryinfo);
			response.reset();
		     response.setContentType("text/html");
		     // Here we use a PrintWriter to send text data
		     // to the client who has requested the servlet
		     PrintWriter out1 = response.getWriter();
		     out1.println("<p>Successful change!!!</p><p><a href='admin.html'>Back</p>");

		}
	else
		if(Type.equals("4"))//view
		{
			tempID=Integer.parseInt(request.getParameter("choosepvID"));
			tempView.set(request.getParameter("view_name"), request.getParameter("view_description"));
			String queryinfo="UPDATE view SET name='"+tempView.getName()+"',description='"+tempView.getDescription()+"' WHERE view_ID="+tempID;
			InsertUserInfo(request,response,queryinfo);
			response.reset();
		     response.setContentType("text/html");
		     // Here we use a PrintWriter to send text data
		     // to the client who has requested the servlet
		     PrintWriter out1 = response.getWriter();
		     out1.println("<p>Successful change!!!</p><p><a href='admin.html'>Back</p>"+tempID+tempView.getName()+tempView.getDescription());
		}
			
			
  }
  else if(Num==0)//customer
{
  out.println("<html><head>");
  out.println("<title>Change the info for Customer</title></head><body>");
  out.println("<center>");
  out.println("<h2>Welcome to the Universal Store online"+ID+"</h2>");
  // Here we set the value for method to post, so that
  // the servlet service method calls doPost in the
  // response to this form submit
  updateUser(request,response,searchCustomerinfo);
  out.println("<table border='0'><tr><td><a href='customer.html'>Back</td></tr><tr><td valign='top'>");
  out.println("<form method='POST' + action='change.html'>");
  out.println("<table border='0'>"+
	"<tr><td>Your Name</td><td bgcolor='red' ><input type='text' id='cname' size='20' name='cname'" +
	"</td></tr>" +
	"<tr><td>Age</td>" +
	"<td bgcolor='red' ><input type='text' id='cage' size='20' name='cage' >" +
	"</td></tr>" +
	"<tr><td>Email</td>" +
	"<td bgcolor='red' ><input type='text' id='cemail' size='20' name='cemail' >" +
	"</td></tr>" +
	"<tr><td>Address</td>" +
	"<td bgcolor='red' ><input type='text' id='caddress' size='20' name='caddress' >" +
	"</td></tr>" +
	"<tr><td>District</td>" +
	"<td bgcolor='red' ><input type='text' id='cdistrict' size='20' name='cdistrict' >" +
	"</td></tr>" +
	"<tr><td>Postcode</td>" +
	"<td bgcolor='red' ><input type='text' id='cpostcode' size='20' name='cpostcode' >" +
	"</td></tr>" +
	"<tr><td>Phone</td>" +
	"<td bgcolor='red' ><input type='text' id='cphone' size='20' name='cphone' >" +
	"</td></tr>" +
	"<tr><td>Password</td>" +
	"<td bgcolor='red' ><input type='password'  id='cpassword' size='20' name='cpassword' >" +
	"</td></tr>");
  out.println("<tr><td valign='top'>");
  out.println("<input type='submit' value='Submit'></td></form>");
  out.println("</td></tr></table>");
  if(request.getParameter("cname")!=null)
	{
	  temp.Set(request.getParameter("cname"), "none", Integer.parseInt(request.getParameter("cage")), request.getParameter("cemail"), request.getParameter("caddress"), request.getParameter("cdistrict"), request.getParameter("cpostcode"), request.getParameter("cphone"), 0, 0, request.getParameter("cpassword"));
	String queryinfo="UPDATE customer_info SET name='"+temp.getName()+"', age="+temp.getAge()+", email='"+temp.getEmail()+"', password='"+temp.getPassword()+"' WHERE email='"+ID+"'";
	String queryaddress="UPDATE address SET address='"+temp.getAddress()+"',district='"+temp.getDistrict()+"',postal_code='"+temp.getPostcode()+"',phone='"+temp.getPhone()+"'WHERE address_ID=(SELECT address_ID FROM customer_info WHERE email='"+ID+"')";
	InsertUserInfo(request,response,queryinfo);
	InsertUserInfo(request,response,queryaddress);
	response.reset();
     response.setContentType("text/html");
     // Here we use a PrintWriter to send text data
     // to the client who has requested the servlet
     PrintWriter out1 = response.getWriter();
     out1.println("<p>Successful change!!!</p><p><a href='customer.html'>Back</p>");
	}
  }
  else if(Num==1)//supplier
  {
	  out.println("<html><head>");
	  out.println("<title>Change the info for Supplier</title></head><body>");
	  out.println("<center>");
	  out.println("<h2>Welcome to the Universal Store online"+ID+"</h2>");
	  // Here we set the value for method to post, so that
	  // the servlet service method calls doPost in the
	  // response to this form submit
	  updateUser(request,response,searchSupplierinfo);
	  out.println("<table border='0'><tr><td valign='top'>");
	  out.println("<form method='POST' + action='change.html'>");
	  out.println("<table border='0'>"+
		"<tr><td>Your Name</td><td bgcolor='red' ><input type='text' id='cname' size='20' name='cname'" +
		"</td></tr>" +
		"<tr><td>Email</td>" +
		"<td bgcolor='red' ><input type='text' id='cemail' size='20' name='cemail' >" +
		"</td></tr>" +
		"<tr><td>Address</td>" +
		"<td bgcolor='red' ><input type='text' id='caddress' size='20' name='caddress' >" +
		"</td></tr>" +
		"<tr><td>District</td>" +
		"<td bgcolor='red' ><input type='text' id='cdistrict' size='20' name='cdistrict' >" +
		"</td></tr>" +
		"<tr><td>Postcode</td>" +
		"<td bgcolor='red' ><input type='text' id='cpostcode' size='20' name='cpostcode' >" +
		"</td></tr>" +
		"<tr><td>Phone</td>" +
		"<td bgcolor='red' ><input type='text' id='cphone' size='20' name='cphone' >" +
		"</td></tr>" +
		"<tr><td>Password</td>" +
		"<td bgcolor='red' ><input type='password'  id='cpassword' size='20' name='cpassword' >" +
		"</td></tr>");
	  out.println("<tr><td valign='top'>");
	  out.println("<input type='submit' value='Submit'></td></form>");
	  out.println("</td></tr><tr><td><a href='supplier.html'>Back</td></tr></table>");
	  if(request.getParameter("cname")!=null)
		{
		  temp.Set(request.getParameter("cname"), "none", 0, request.getParameter("cemail"), request.getParameter("caddress"), request.getParameter("cdistrict"), request.getParameter("cpostcode"), request.getParameter("cphone"), 0, 0, request.getParameter("cpassword"));
		String queryinfo="UPDATE supplier_info SET name='"+temp.getName()+", email='"+temp.getEmail()+"', password='"+temp.getPassword()+"' WHERE email='"+ID+"'";
		String queryaddress="UPDATE address SET address='"+temp.getAddress()+"',district='"+temp.getDistrict()+"',postal_code='"+temp.getPostcode()+"',phone='"+temp.getPhone()+"'WHERE address_ID=(SELECT address_ID FROM customer_info WHERE email='"+ID+"')";
		InsertUserInfo(request,response,queryinfo);
		InsertUserInfo(request,response,queryaddress);
		response.reset();
	     response.setContentType("text/html");
	     // Here we use a PrintWriter to send text data
	     // to the client who has requested the servlet
	     PrintWriter out1 = response.getWriter();
	     out1.println("<p>Successful change!!!</p><p><a href='supplier.html'>Back</p>");
		}
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
 
 public void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, java.io.IOException {doPost(request,response);}
 
}
