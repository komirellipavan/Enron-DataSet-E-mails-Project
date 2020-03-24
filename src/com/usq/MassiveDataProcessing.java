package com.usq;

//public class MassiveDataProcessing {
//
//}


import java.sql.*;  
class MassiveDataProcessing{  
public static void main(String args[]){  
try{  
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3306/enron_email","root","");  
//here sonoo is database name, root is username and password  
Statement stmt=con.createStatement();  



String sql ="INSERT INTO raw_data " + "VALUES (1,'Simpson', '2001')";
stmt.execute(sql);


ResultSet rs=stmt.executeQuery("select * from raw_data");  
while(rs.next())  
System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
con.close();  
}catch(Exception e){ System.out.println(e);}  
}  
}