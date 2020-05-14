package Data_Processor;

import Repository.*;

public class StemChecker{

	public StemChecker(){
	}
	
	//stemChecker can perform for 
	//	- a paragraph which each term seperated by spaces only
	//	- a term at once
	public String checkStem(String st){
		String stems = "";
		
		String [] terms = st.split(" ");
		for(int i=0; i<terms.length; i++){
			stems = stems + stemming(terms[i]) + " ";
		}
		
		return stems;
	}
		
	private String stemming(String st){
		
		String stemmed = "";
      	char[] w = st.toCharArray();
      	Stemmer s = new Stemmer();

      	for(int i=0; i<w.length; i++){
         	s.add(Character.toLowerCase(w[i]));
      	}
      	s.stem();
	  	stemmed = s.toString();
	  	stemmed = stemmed.trim();
	  	return stemmed;
   }
   
   public static void main(String argv[]){
   	 String st = "ActionScript (Computer-program language) -- language Computer"; 
   	 st = st.replaceAll("\\("," \\( ");
   	 st = st.replaceAll("-"," - ");
   	 st = st.replaceAll("-  -","--");
   	 StemChecker sc = new StemChecker();
   	 st = sc.checkStem(st);
   	 System.out.println(st);
   }
      
 }