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
import java.sql.ResultSet;
import com.mysql.jdbc.PreparedStatement;



public class DataCleaning {
	static Connection connection = null;
	ResultSet rs;
	String dd;
	
	
	public static String extractKey(String messageMetaData) {
		HashMap<String, String> hmap = new HashMap<String, String>();
		System.out.println("---------------key value pairs---------------");
		 try (Scanner scanner = new Scanner(messageMetaData)) {
		        while (scanner.hasNextLine()) {
		            String line = scanner.nextLine();
		            //System.out.println(line);
		            
		            
		            String[] arrOfStr = line.split(":", 2); 
		           
		            for (String a : arrOfStr) {
		                System.out.println(a); 
		                
		            }        
		            
		            if(line.isEmpty()) {
		            	break;
		            }
		            
		            
		        }
		    }
		 
		
	      
		
		return "dd";
	}
	
	public static String extractBody(String messageMetaData) {
		
		HashMap<String, String> hmap = new HashMap<String, String>();
		StringBuilder result = new StringBuilder();
		Boolean flag= false;
		
		System.out.println("---------------Body--------------");
		 try (Scanner scanner = new Scanner(messageMetaData)) {
		        while (scanner.hasNextLine()) {
		            String line = scanner.nextLine();
		            //System.out.println(line);
		            
		            
		            if(line.isEmpty()) {
		            	flag = true;
		        
		            }
		            
		            if(flag) {
		            	
		            	result.append(line); 
		            	result.append("\n");
				           
		            
		            }
		        }
		    }
		 
		System.out.println(result);
	      
		
		return result.toString();
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		
		String messageData;
		
		connection = ConnectionManager.getConnection();
		System.out.println(connection);
		connection.setAutoCommit(false);

		String sqlQuery = "SELECT * FROM raw_data LIMIT 2";
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
