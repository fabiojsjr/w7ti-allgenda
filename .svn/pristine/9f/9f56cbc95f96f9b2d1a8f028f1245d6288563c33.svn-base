package com.tetrasoft.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.tetrasoft.app.adaptor.AbstractAdaptorMetaChar;

public class IPLocator {
	public void getClientLatLng(String ip){
		if(ip!=null && !ip.equals("")) {
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost("http://api.ipinfodb.com/v3/ip-city/?key=0e5aa7187c006be80764428390d2109f373509369eb30d4e950fbcc8a58bca08&ip=" + ip);
				HttpResponse response = httpClient.execute(postRequest);

				if (response.getStatusLine().getStatusCode() != 201) {
					/*
					 * throw new RuntimeException("Failed : HTTP error code : " +
					 * response.getStatusLine().getStatusCode());
					 */
					System.out.println("StatusCode: " + response.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader( (response.getEntity().getContent())));

				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}

				httpClient.getConnectionManager().shutdown();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getIPMap( String ip ) {
		String cidade = "";
		String pais   = "";
		String lat    = "";
		String lon    = "";
		
		try {
			String retorno = Utils.getWebpage("http://xml.utrace.de/?query=" + ip);
			lat     = retorno.substring( retorno.indexOf("<latitude>"), retorno.indexOf("</latitude>") );
			lon     = retorno.substring( retorno.indexOf("<longitude>"), retorno.indexOf("</longitude>") );
			cidade  = retorno.substring( retorno.indexOf("<region>"), retorno.indexOf("</region>") );
			pais    = retorno.substring( retorno.indexOf("<countrycode>"), retorno.indexOf("</countrycode>") );
			
			lat    = lat.replaceAll("<.*?>", "").trim();
			lon    = lon.replaceAll("<.*?>", "").trim();
			cidade = cidade.replaceAll("<.*?>", "").trim();
			pais   = pais.replaceAll("<.*?>", "").trim();
			
			if( cidade.equals("") || pais.equals("" ) ) {
				throw new Exception("");
			}
			
		} catch( Exception e ) {
			
			try { 
	//			retorno = Utils.getWebpage("http://www.ip-tracker.org/locator/ip-lookup.php?ip=" + ip);
	//			retorno = Utils.getWebpage("https://www.iplocation.net/?query=" + ip + "&submit=IP+Lookup");
				String retorno = Utils.getWebpage("http://" + ip + ".ipaddress.com");
					
				
				retorno = retorno.substring( retorno.indexOf("City:") );
				cidade  = retorno.substring( retorno.indexOf("<td>"), retorno.indexOf("</td>") );
					
				retorno = retorno.substring( retorno.indexOf("Country:") );
				pais    = retorno.substring( retorno.indexOf("<td>"), retorno.indexOf("</td>") );
					
	//			String coords = retorno.substring( retorno.indexOf("City Lat/Lon:"), retorno.indexOf("</td>") );
	//			lat     = retorno.substring( retorno.indexOf("<latitude>"), retorno.indexOf("</latitude>") );
	//			lon     = retorno.substring( retorno.indexOf("<longitude>"), retorno.indexOf("</longitude>") );
	//			lat    = lat.replaceAll("<.*?>", "").trim();
	//			lon    = lon.replaceAll("<.*?>", "").trim();
					
				cidade = cidade.replaceAll("<.*?>", "").trim();
				pais   = pais.replaceAll("<.*?>", "").trim();
					
				lat    = cidade.replaceAll(" ", "+");
				lon    = pais.replaceAll(" ", "+");
				
			} catch( Exception e2 ) {
				return "";
			}
			
		}

		String local = lat + "," + lon;
			
		return 
			AbstractAdaptorMetaChar.replaceMetaChar( 
					cidade + ", " + pais + "<br/><br/>" +
					"<img src='https://maps.googleapis.com/maps/api/staticmap?center=" + local +"&zoom=7&size=540x380&markers=color:red%7Clabel:%7C" + local + "' />"
			);
		
//			"<iframe width=\"425\" height=\"350\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" src=\"https://maps.google.com/maps?q=" + lat + "," + lon + "&z=6&output=embed\"></iframe>"
	}
	
	public static void main(String[] args) {
//		new IPLocator().getClientLatLng("184.89.137.49");
//		System.out.print( new IPLocator().getIPMap("184.89.137.49") );
		System.out.print( new IPLocator().getIPMap("199.87.224.41") );
	}
}
