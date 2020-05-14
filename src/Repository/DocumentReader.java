package Repository;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.sql.*;
import Repository.*;

public class DocumentReader {
	
	public DocumentReader(){
	}
	
	/**
	 * 
	 * @param path referring to a directory containing documents (MUST 2 levels)
	 * @return Hashtable <docID, doc_content>
	 */
	public Hashtable readDirectory(String path){		
		Hashtable docs = new Hashtable();	
		try { 
		    File dir = new File(path);
		    if(dir.isDirectory()){
		    	String[] subDir = dir.list();
				if (subDir != null) {
					for (int i=0; i<subDir.length; i++) {
						String filename = subDir[i];
					    if(!filename.equals(".DS_Store")){
					    	String st = readFile(path + filename);
					    	docs.put(filename, st);
					    }
					}
				}
		    }else{
		    	System.out.println("Not a directory");
		    }
			
		} 
		catch (Exception e) { 
			System.err.println(e); 
		}
		
		return docs;
	}
	
	/**
	 * without specification of size to retrieve all records
	 * @param path the location where the file sites in
	 * @return s String contained in the file
	 */
	public String readFile(String path){				
		String s = "";
		try { 
			FileInputStream fstream = new FileInputStream(path); 
			DataInputStream in = new DataInputStream(fstream); 
	
			while (in.available() !=0) {
				s += in.readLine() + "\n";											
			} 
			in.close(); 
		} 
		catch (Exception e) { 
			System.err.println(e); 
		}
		
		return s;

	}
	
	/**
	 * without the specification of size to retrieve a number of top records
	 * 	overriding the readFile(String path) method
	 * @param path the location where the file sites in
	 * @param size the number of records to retrieve 
	 * @return s String contained in the file
	 */
	public String readFile(String path, int size){				
		String s = "";
		int count = 0;
		try { 
			FileInputStream fstream = new FileInputStream(path); 
			DataInputStream in = new DataInputStream(fstream); 
	
			while (in.available() !=0 && count<size) {
				s += in.readLine() + "\n";
				count++;
			} 
			in.close(); 
		} 
		catch (Exception e) { 
			System.err.println(e); 
		}
		
		return s;

	}
	
	public static void main(String args[]){
		DocumentReader d = new DocumentReader();		
		Hashtable table = d.readDirectory(FILENAMES.TRAININGSET.toString() + "/stems/stem101/");
		Enumeration e = table.keys();
		while(e.hasMoreElements()){
			String id = e.nextElement().toString();
			String content = table.get(id).toString();
			System.out.println(id + "\n" + content + "\n");
		}
	} 
}
