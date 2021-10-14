package com.record.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try
//	    {
//	      Date d1 = df.parse("2004-03-26 13:31:40");
//	      Date d2 = df.parse("2004-01-02 11:30:24");
//	      long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
//	      long days = diff / (1000 * 60 * 60 * 24);
//	 
//	      long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
//	      long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
//	      System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
//	    }catch (Exception e){
//	    	e.printStackTrace();
//	    }
		java.sql.Timestamp start_time = java.sql.Timestamp.valueOf("2006-06-06 06:06:06");
		java.sql.Timestamp end_time = java.sql.Timestamp.valueOf("2006-06-06 07:07:07");
		Long duration = (end_time.getTime()-start_time.getTime())/1000;
		int b = duration.intValue();
		System.out.println((end_time.getTime()-start_time.getTime())/1000);
		
	}

}
