package com.usq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.ResultSet;
import com.mysql.jdbc.PreparedStatement;



public class DataCleaning {
	static Connection connection = null;
	ResultSet rs;
	String dd;
	static int from_index;
	
	
	public static void extractKey(String messageMetaData) {
		from_index=0;
		HashMap<String, String> hmap = new HashMap<String, String>();
		System.out.println("---------------key value pairs---------------");
		 try (Scanner scanner = new Scanner(messageMetaData)) {
			 String mess = "",u_to="",u_from="",subject="",m_date="";
		        
			 
			 while (scanner.hasNextLine()) {
		            String line = scanner.nextLine();
		            String[] arrOfStr = line.split(":", 2); 	            
		            
		            switch (arrOfStr[0]) {
		            case ("Message-ID"):
		              mess=arrOfStr[1];
		              break;
		            case ("Date"):
		            	m_date=arrOfStr[1];
		              break;
		            case ("From"):
		            	u_from=arrOfStr[1];
		              break;
		            case ("To"):
		            	u_to=arrOfStr[1];
		              break;
		            case ("Subject"):
		            	subject=arrOfStr[1];
		              break;
		            case ("X-FileName"):
//		            	this is to find the start index to grab themessage body
		            	from_index=arrOfStr[0].length()+arrOfStr[1].length()+2;
//		            	subject=arrOfStr[1];
		              break;
		            default:
//		              System.out.println("no cases");
		              break;
		          }
		           
		            if(line.isEmpty()) {
		            	break;
		            }
		            
		            
		        }
			 
			 System.out.println(mess);
			 System.out.println(m_date);
			 System.out.println(u_from);
			 System.out.println(u_to);
			 System.out.println(subject);
		    }
		 
		
	      
		
	}
	
	
	
	
	public static void extractBody(String messageMetaData) {
		
		HashMap<String, String> hmap = new HashMap<String, String>();
		StringBuilder result = new StringBuilder();
		Boolean flag= false;
		
		
		String reply_con=" -----Original Message-----|---------------------- Forwarded ";
		
		String str = messageMetaData;
		
		int len1 =str.indexOf("X-FileName:");
		
		int len2 =str.indexOf("["+reply_con+"]");
		
		String res="";
		
		//fromindex is the length of x filename to get from index of message body
		
		
		
		if(len2>0) {
			res = str.substring(len1+from_index, len2);
		}else {
			res = str.substring(len1+from_index);
		}
		
		System.out.println(res);
	
		//Remove HTML TAGS
//		String removeHTML = result.toString().replaceAll("\\<.*?\\>", "");
//		System.out.println(removeHTML);
		
		
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		
		String messageData;
		
		connection = ConnectionManager.getConnection();
		System.out.println(connection);
		connection.setAutoCommit(false);

		String sqlQuery = "SELECT * FROM raw_data LIMIT 10";
		PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sqlQuery);
		
		
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.println(rs.getString(2));
			messageData =rs.getString(3);
			extractKey(messageData);
			extractBody(messageData);
        }
		
		
		System.out.println("***********finish************");

	}

}
