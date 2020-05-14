package Data_Processor;

import Repository.*;

import java.util.*;

public class TermParser{
	
	StopWordChecker checker;
	
	public TermParser(){
		checker = new StopWordChecker();
	}
	
	public TermDescription getTermDescriptions(String [] contents){
		
		//String [] contents = splitString(regex, content);
		TermDescription td = new TermDescription();
		td.setTotalNumber(contents.length);
		
		for(int position=0; position<contents.length; position++){
			if((!contents[position].equals("")) 
				&& (!checker.checkWord(FILENAMES.STOPWORDS, contents[position])))
				td.addTerm(contents[position]);			
		}
		return td;
	}
	
	public String parseString(String content){
		
		String st = "";
		String [] contents = splitString(regex, content);
		for(int position=0; position<contents.length; position++){
			if((!contents[position].equals("")) 
				&& (!checker.checkWord(FILENAMES.STOPWORDS, contents[position]))
				&& !contents[position].contains("0")
				&& !contents[position].contains("1")
				&& !contents[position].contains("2")
				&& !contents[position].contains("3")
				&& !contents[position].contains("4")
				&& !contents[position].contains("5")
				&& !contents[position].contains("6")
				&& !contents[position].contains("7")
				&& !contents[position].contains("8")
				&& !contents[position].contains("9")){
					st = st + contents[position] + " ";	
			}
		}
		
		return st;
	}
	
	public String [] splitString(String st){
		return splitString(regex, st);
	}
	
	public String [] splitString(char[] c, String st){
		st = st.toLowerCase();
		for(int j=0; j<c.length; j++){
			st = st.replace(c[j], ' ');
		}
		
		String [] contents = st.split(" ");
		return contents;
	}
	
	public final char [] regex = {'\\', '\t', '\n', '\r', '\f', '!', '@', 
			'%', '^', '*', '(', ')', '-', '_', '=', '~', '`', '\'',
			',', '?', '/', '<', '>', '\f', ':', ';', '"', '|', 
			'[', ']', '{', '}', '&', '.', '#', '$', '+'
		};
		


}