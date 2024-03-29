package com.usq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.Date;
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
import Data_Processor.*;


public class DataCleaning {
	static Connection connection = null;
	ResultSet rs;
	String dd;
	static int from_index;
	
	
	public static Message extractKey(Message msg,String messageMetaData) {
		from_index=0;
		

		 try (Scanner scanner = new Scanner(messageMetaData)) {
			 String mess = "",u_to="",u_from="",subject="",m_date="";
		        
			 
			 while (scanner.hasNextLine()) {
		            String line = scanner.nextLine();
		            String[] arrOfStr = line.split(":", 2); 	            
		            
		            switch (arrOfStr[0]) {
		            case ("Message-ID"):
		              mess=arrOfStr[1].replaceAll(">", "");
		              mess=mess.replaceAll("<", "");
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
//		            	this is to find the start index to grab the message body
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
			 
	//		 System.out.println(mess);
//    		 System.out.println(m_date);
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
		
		
		
		
		String str = messageMetaData;
		
		int len1 =str.indexOf("X-FileName:");
		
		int len2 =str.indexOf("----------------------");
		
		String res="";
		
		//from_index is the length of x filename to get from index of message body
		
		//System.out.println(len2);
		
		if(len2>0) {
			
			res = str.substring(len1+from_index, len2);
			if(!res.matches("[a-zA-Z]+")){
				res="";
			}
			
			
		}else {
			res = str.substring(len1+from_index);
		}
		
		//System.out.println(res);
		
		
		msg.raw_msg=res;
		
		
		
		//Remove HTML TAGS
		String removeHTML = res.replaceAll("\\<.*?\\>", "");
		//System.out.println(removeHTML);
		
		//Remove http and https links
		String removeHyperLinks = removeHTML.replaceAll("(http|https):\\/\\/(.*)", "");
		//System.out.println(removeHyperLinks);
		
		//Remove all special characters except alphabets and line break.Replace with break [^A-Za-z'\\n]
		String removeSpecailChars = removeHyperLinks.replaceAll("[^A-Za-z]", " "); 
		//System.out.println(removeSpecailChars);
		
		//Remove more than one space
		String removeSpace = removeSpecailChars.replaceAll("\\s\\s+", " ");
		//System.out.println(removeSpace);
		
		//Remove stop words
		StopWordChecker stopWord = new StopWordChecker();
		String finalStopWord = stopWord.removeStopword(removeSpace);
		//System.out.println(finalStopWord);
		
		
		//Apply Stemming
		
		StemChecker stemming = new StemChecker();
		String StemStr = stemming.checkStem(finalStopWord);
		
		
//		System.out.println(res);
//		System.out.println("==>"+StemStr);
		
		
		
		msg.body=StemStr.trim();
		return msg;
	

		
	}
	

	
	
	public static void main(String[] args) throws IOException, SQLException {
		
		String messageData;
		
		Message msg = new Message();
		
		
		List<Message> msgList = new ArrayList<Message>();
		
		
		connection = ConnectionManager.getConnection();
//		System.out.println(connection);
		connection.setAutoCommit(false);

		String sqlQuery = "SELECT * FROM raw_data";
		PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(sqlQuery);
		
		
		
		ResultSet rs = pstmt.executeQuery();
		
		
		String sqlQuery1 = "insert into message_data (id,msg_id,message) values (?,?,?)";
		PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement(sqlQuery1);
		
		
		String sqlQuery2 = "insert into message_timeline (id,msg_date) values (?,?)";
		PreparedStatement pstmt2 = (PreparedStatement) connection.prepareStatement(sqlQuery2);
		
		String sqlQuery3 = "insert into message_raw (id,raw_msg) values (?,?)";
		PreparedStatement pstmt3 = (PreparedStatement) connection.prepareStatement(sqlQuery3);
		
		int counter=0,totalmessage=0;;
		
		
		while (rs.next()) {
//			System.out.println(rs.getString(2));
			messageData =rs.getString(3);
			msg=extractKey(msg,messageData);
			msg=extractBody(msg,messageData);
			totalmessage++;
			
			if(!msg.body.isEmpty()) {	
				counter++;
//				System.out.println("records inserted "+counter);
				// add raw msg
				pstmt1.setInt(1,counter);
				pstmt1.setString(2, msg.messageid);
				pstmt1.setString(3, msg.body);
				pstmt1.addBatch();
				
//				System.out.println("records inserted "+counter);
				//add date
				pstmt2.setInt(1, counter);
				try {
					if(msg.date!=null) {
						Date msgdate=new java.sql.Date(msg.date.getTime());
						pstmt2.setDate(2, msgdate);
					}else {
						pstmt2.setDate(2, null);
					}
					
					
				}catch(Exception e) {
					pstmt2.setDate(2, null);
				}

				pstmt2.addBatch();
				
//				System.out.println("records inserted "+counter);
				//add processed message
				pstmt3.setInt(1, counter);
				pstmt3.setString(2, msg.body);
				pstmt3.addBatch();
				
//				System.out.println(msg.from_user);
//				System.out.println(msg.to_user);
				
//				System.out.println("=====================\n\n\n");

				
				
				if(counter%500==0) {	
					
					
						
						pstmt1.executeBatch();
						pstmt2.executeBatch();
						pstmt3.executeBatch();
						
						connection.commit();
//						msgList.clear();
						System.out.println("records inserted = "+counter);
					}

				}
			
			
			}
		
		
//		todo**********************************************************
		
//		todo
		// write a code tobatch insert here
		// msgList contains list of message objects
		//batch insert from here
		
		for(Message m : msgList) {
			//if(m.body!="")
			//System.out.println(m.toString());
			//System.out.println(m.toString());
		}
		
		pstmt1.executeBatch();
		pstmt2.executeBatch();
		pstmt3.executeBatch();
		
		connection.commit();
		
		System.out.println("total empty message = "+ (totalmessage-counter));
		
		
		connection.close();
		
		System.out.println("***********finish************");

	}
	

	

}
