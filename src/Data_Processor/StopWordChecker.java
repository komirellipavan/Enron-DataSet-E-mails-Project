package Data_Processor;

import Repository.*;
import java.io.*; 
import java.util.*;

public  class StopWordChecker { 
	
	private FILENAMES fn;
	
	public StopWordChecker(){
		fn = FILENAMES.STOPWORDS;
		stopwords = new Vector();
	 	reservedwords = new Vector();
	 	traceWords();
	}
	
	
	public String removeStopword(String st){ 
		String stemmed_st = "";
		String [] sts = st.split(" ");
		for(int i=0; i<sts.length; i++){
			if(!wordChecking(FILENAMES.STOPWORDS.toString(), sts[i])){
				if(i==sts.length-1)
					stemmed_st = stemmed_st + sts[i];
				else
					stemmed_st = stemmed_st + sts[i] + " ";
			}				
		}		
		return stemmed_st;
	}
	
	public boolean checkWord(FILENAMES fn, String st){ 
		return wordChecking(fn.toString(), st);
	}
	
	private boolean wordChecking(String fn, String st){
		
		st = st.replaceAll("[\\.|+]", "");
		
		
		boolean flag = false;
		
		if(fn.equals(FILENAMES.STOPWORDS.toString())){
			if(stopwords.contains(st.toLowerCase())) {
				//System.out.println(st);
				flag = true;
			}
		}			
		else if(fn.equals(FILENAMES.RESERVEDWORDS.toString())){
			if(reservedwords.contains(st.toLowerCase()))
				flag = true;
		}
					
		return flag; 
	}
	
	public boolean isStopword(String st){
		
		if(stopwords.contains(st)) return true;
		else return false;
	}
	
	public boolean isReservedword(String st){
		if(reservedwords.contains(st)) return true;
		else return false;
	}
	
	
	private void traceWords(){
		try { 
			FileInputStream fstream = new FileInputStream(FILENAMES.STOPWORDS.toString()); 
			DataInputStream in = new DataInputStream(fstream); 
			
			while (in.available() !=0) {
				String word = in.readLine();
				stopwords.add(word);			

			} 
			in.close(); 
			
			fstream = new FileInputStream(FILENAMES.RESERVEDWORDS.toString()); 
			in = new DataInputStream(fstream); 
	
			while (in.available() !=0) {
				String word = in.readLine();
				reservedwords.add(word);			
			} 
			in.close(); 
		} 
		catch (Exception e) { 
			System.err.println("File input error "+ e); 
		}
	}
	
	private Vector stopwords;
	private Vector reservedwords;
} 

