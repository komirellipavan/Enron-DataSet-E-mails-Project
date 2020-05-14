package Data_Processor; 

import Repository.*;
import java.util.*;

public class TermDescription{
	
	private Hashtable table;
	private Hashtable ptable;
	private int termNumber;
	private int totalNumber;
	
	public TermDescription(){
		table = new Hashtable();
		ptable = new Hashtable();
		termNumber = 0;
		totalNumber = 0;
	}

	public void addTerm(String term){
		addTerm(term, "");
	}

	public void addTerm(String term, String stem){
	
		Vector stems = new Vector();
		
		if(!hasTerm(term)){
			this.table.put(term,new Integer(1));
			stems.add(stem);
			this.ptable.put(term,stems);
		}
			
		else{
			int freq = getFreq(term);
			stems = getPosition(term);
			
			removeTerm(term);
			
			freq++;
			this.table.put(term, new Integer(freq));		
			
			if(!stems.contains(stem)){
				stems.add(stem);
			}
			this.ptable.put(term,stems);		
		}
		this.termNumber++;
	}
	
	
	public Vector getTerms(){
		Vector terms=new Vector();
		Enumeration enu = this.table.keys();
		while(enu.hasMoreElements()){
			terms.add(enu.nextElement());
		}
		return terms;
	}
	
	public int getFreq(String term){
		int freq = 0;
		if(hasTerm(term)){
			Integer s = (Integer)(table.get(term));
			freq = s.intValue();
		}
		return freq;
	}
	
	public Vector getPosition(String term){
		Vector position = new Vector();
		if(this.ptable.containsKey(term))
			position = (Vector)(ptable.get(term));
		return position;
	}
	
	public int getTermNumber(){
		return this.termNumber;
	}
		
	public int getTotalNumber(){
		return this.totalNumber;
	}
	
	public void setTotalNumber(int n){
		this.totalNumber=n;
	}
	
	public boolean hasTerm(String term){
		if(this.table.containsKey(term)) 
			 return true;
		else return false;
	}
	
	private void removeTerm(String term){
		this.table.remove(term);
		this.ptable.remove(term);
		this.termNumber--;		
	}
	
	public void clearTable(){
		table.clear();
		ptable.clear();
	}
	

}