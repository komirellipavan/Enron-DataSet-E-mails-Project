package com.usq;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message {
	String messageid;
	String to_user;
	String from_user;
	
	String subject;
	
	String raw_msg;
	
	String body;
	
	Date date;
	
	public Date getDate() {
		return date;
	}



	public Date setDate(String s_date) {
		String string = s_date; //Thu, 24 Jan 2002
		DateFormat format = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.date = date;
		
		return this.date;
	}



	public Message() {
		
	}
	
	
	
	public Message(String id,String to,String from, String subject,String body,String raw,String s_date) {
		this.messageid=id;
		this.to_user=to;
		this.from_user=from;
		this.subject=subject;
		this.body=body;
		this.raw_msg=raw;
		
		String string = s_date; //Thu, 24 Jan 2002
		DateFormat format = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.date=date;
	}
	
	public String toString() {
		
		return this.messageid+""+this.body.substring(1,10);
		
	}
	
	
	
	

}
