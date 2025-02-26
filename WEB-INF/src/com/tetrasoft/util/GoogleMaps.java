package com.tetrasoft.util;

import java.util.regex.Pattern;

import com.tetrasoft.app.util.RegularExp;


public class GoogleMaps {
	private static String API_KEY = "AIzaSyAguZAspGzPFzMKBDkB5yiXwmGZkpHvcVQ";
	
	// https://maps.googleapis.com/maps/api/directions/json?origin=34787&destination=33178&key=AIzaSyAguZAspGzPFzMKBDkB5yiXwmGZkpHvcVQ
	
	public double getDistance( String origin, String destination ) {
		try {
			String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&key=" + API_KEY;
			Pattern pat1 = Pattern.compile("\"distance\".*?}",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
			Pattern pat2 = Pattern.compile("\"text\".*?\",",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
			
			String response = Utils.getWebpage(url);
			String distance = RegularExp.get( pat1, response );
			distance = RegularExp.get( pat2, distance );
			distance = distance.replaceAll("text", "").replaceAll("\"", "").replaceAll(":", "").replaceAll(",", "").replaceAll("mi", "").trim();		
			
			return Double.parseDouble(distance);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public static void main(String[] args) {
		System.out.println( new GoogleMaps().getDistance("Orlando", "07094") );
	}
}
