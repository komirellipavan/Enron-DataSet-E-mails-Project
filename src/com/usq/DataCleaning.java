package com.usq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	
	
	public static Message extractKey(Message msg,String messageMetaData) {
		from_index=0;
		HashMap<String, String> hmap = new HashMap<String, String>();
//		System.out.println("---------------key value pairs---------------");
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
		            	String[] s= arrOfStr[1].split(" ");
		                String val = s[2]+" "+s[3]+" "+s[4];
		            	m_date=val;
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
			 
			 msg.messageid=mess;
			 msg.to_user=u_to;
			 msg.from_user=u_from;
			 msg.subject=subject;
			 msg.date= msg.setDate(m_date);;
			 
//			 System.out.println(mess);
//			 System.out.println(m_date);
//			 System.out.println(u_from);
//			 System.out.println(u_to);
//			 System.out.println(subject);
		    }
		 
		 
		 return msg;
		 
		
	      
		
	}
	
	
	
	
	public static Message extractBody(Message msg,String messageMetaData) {
		
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
		
//		System.out.println(res);
		
		msg.body=res;
		
		return msg;
	
		//Remove HTML TAGS
//		String removeHTML = result.toString().replaceAll("\\<.*?\\>", "");
//		System.out.println(removeHTML);
		
		
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		
		String messageData;
		
		Message msg = new Message();
		
		
		List<Message> msgList = new ArrayList<Message>();
		
		
		connection = ConnectionManager.getConnection();
//		System.out.println(connection);
		connection.setAutoCommit(false);

		String sqlQuery = "SELECT * FROM raw_data LIMIT 10";
		PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sqlQuery);
		
		
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
//			System.out.println(rs.getString(2));
			messageData =rs.getString(3);
			msg=extractKey(msg,messageData);
			msg=extractBody(msg,messageData);
			
//			System.out.println(msg.toString());
			
			msgList.add(msg);
			
        }
		
		
//		todo**********************************************************
		
//		todo
		// write a code tobatch insert here
		// msgList contains list of message objects
		//batch insert from here
		
		for(Message m : msgList) {
			if(m.body!="")
			System.out.println(m.toString());
		}
		
		
		System.out.println("***********finish************");

	}

}
