package com.tetrasoft.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import com.tetrasoft.app.util.RegularExp;

public class Utils {
	public static String[] EMAIL_RENATO 	= new String[]{"fabiojsjr@gmail.com"};
	public static String[] EMAILS_ADMIN 	= new String[]{"fabiojsjr@gmail.com",};
	public static String[] EMAILS_ADMIN_TOP = new String[]{"fabiojsjr@gmail.com",};
	public static String[] EMAILS_ADMIN_FL  = new String[]{"fabiojsjr@gmail.com",};
	public static String[] EMAILS_ADMIN_NJ  = new String[]{"fabiojsjr@gmail.com",};

	public static String getLinkLocalIP( String ip ) {
		return ip + "<br/><br/><iframe src='ipLocator.jsp?ip=" + ip + "' width='400' height='400' scrolling='no' marginheight='0' marginwidth='0' frameborder='0'></iframe>";
	}

	public static String getWebpage( String url ) {
		try {
			URL ulX = new URL( url );
			HttpURLConnection connection = (HttpURLConnection)ulX.openConnection();

			connection.connect();

			InputStream in = connection.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			int i;
			byte[] buf = new byte[8192];
			while(( i = in.read( buf )) > 0 ) {
				out.write( buf, 0, i );
			}
			in.close();
			out.flush();
			connection.disconnect();

			return out.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}		
	}

	public static double great_circle_distance( double $fromlat, double $tolat, double $fromlong, double $tolong ) {
		// Convert all the degrees to radians
		$fromlat  = deg_to_rad($fromlat);
		$fromlong = deg_to_rad($fromlong);
		$tolat    = deg_to_rad($tolat);
		$tolong   = deg_to_rad($tolong);

		// Find the deltas
		double $delta_lat = $tolat  - $fromlat;
		double $delta_lon = $tolong - $fromlong;

		// Find the Great Circle distance
		double $temp = Math.pow( Math.sin($delta_lat/2.0),2) + Math.cos($fromlat) * Math.cos($tolat) * Math.pow( Math.sin($delta_lon/2.0), 2);

		double $EARTH_RADIUS = 3956;

		double $distance = $EARTH_RADIUS * 2 * Math.atan2( Math.sqrt($temp), Math.sqrt(1-$temp) );

		return $distance;
	}

	private static double deg_to_rad(double $deg) {
		double $radians = 0.0;

		$radians = $deg * Math.PI / 180.0;

		return($radians);
	}

	public static void main(String[] args) {
//		String html = Utils.getWebpage("http://" + ConfigEntity.CONFIG.get("url") + "/allgenda/inventarioPrint.jsp?idQuote=1461177876322&nocache=" + System.currentTimeMillis());
//		String html = Utils.getWebpage("http://localhost:8080/allgenda/doc/email.html?nocache=" + System.currentTimeMillis());

		try {
			System.out.println( great_circle_distance( 28.4775, 39.1256, 81.6145, 106.7972) );
			
//			SendMailSparkpost.send("*** TESTE *** " + System.currentTimeMillis(),"allgenda@allgenda.org.br", "fabiojsjr@gmail.com", "text/html", new StringBuffer(html));
//			SendMailSparkpost.send("*** TESTE *** Inventério feito pelo cliente *** TESTE ***","allgenda@allgenda.org.br", "rodrigo@allgenda.org.br", "text/html", new StringBuffer(html));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String corrigirIframeMCE(String str) {
//		<img class="mceItemMedia mceItemIframe" src="http://localhost:8080/allgenda/js/tinymce/themes/advanced/img/trans.gif" alt="" width="560" height="315" data-mce-json="{'video':{},'params':{'src':'https://www.youtube.com/embed/uuL4xBv2bds'},'hspace':null,'vspace':null,'align':null,'bgcolor':null}" data-mce-src="js/tinymce/themes/advanced/img/trans.gif">
//		<iframe src="https://www.youtube.com/embed/uuL4xBv2bds" width="560" height="315"></iframe>
		
		Pattern patImagem = Pattern.compile("<img class=.mceItemMedia.*?>",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		Pattern patW    = Pattern.compile("width=\".*?\"",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		Pattern patH   = Pattern.compile("height=\".*?\"",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		Pattern patURL = Pattern.compile("src':'.*?'",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		
		String imagem = RegularExp.get( patImagem, str );
		String w      = RegularExp.get( patW, imagem );
		String h      = RegularExp.get( patH, imagem );
		String url    = RegularExp.get( patURL, imagem );
		
		if( w   != null ) w   = w.replaceAll("width=", "").replaceAll("\"", "").trim();
		if( h   != null ) h   = h.replaceAll("height=", "").replaceAll("\"", "").trim();
		if( url != null ) url = url.replaceAll("src':'", "").replaceAll("'", "").trim();
		
		String frame = "<center><iframe src=\"" + url + "\" width=\"" + w + "\" height=\"" + h + "\" frameborder=\"0\"></iframe></center>";
		
		str = str.replace( imagem, frame );
		
		return str;
	}

	public static String createVideoMCE(String descricao) {
		String  expressao = "\\[http.*?\\]";
		Pattern patURL    = Pattern.compile(expressao,Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		
		int protection = 0;
		String url = RegularExp.get( patURL, descricao );
		while( url != null && protection < 20 ) {
			
			String url2 = url.replaceAll("\\[", "").replaceAll("\\]", "");
			
			if( url.contains("youtube") ) {
				String p = url2.substring( url2.indexOf("v=")+2, url2.length() );
				url2 = "https://www.youtube.com/embed/" + p;
				
				descricao = descricao.replaceFirst( expressao, "<center><iframe width='640' height='480' src='" + url2 + "' frameborder='0' allowfullscreen></iframe></center>");
				
			} else if( url.contains("youtu.be") ) {  // https://youtu.be/a8pxIxvzioo

				String p = url2.substring( url2.lastIndexOf("/") + 1 );
				url2 = "https://www.youtube.com/embed/" + p;
				
				descricao = descricao.replaceFirst( expressao, "<center><iframe width='640' height='480' src='" + url2 + "' frameborder='0' allowfullscreen></iframe></center>");
				
			} else { // outros tipos de védeos
				url2 = url2.replaceAll("%20", " ");
				
				String video = "<center><video width='640' height='480'><source src='" + url2 + "' type='video/mp4'>Seu navegador não suporta a exibiééo de védeos MP4</video></center>";						
				descricao = descricao.replaceFirst( expressao, video );
			}
			
			url = RegularExp.get( patURL, descricao );
			protection++;
		}
		
		descricao = descricao.replaceAll("<center><center>", "<center>");
		descricao = descricao.replaceAll("</center></center>", "</center>");
		
		return descricao;
	}

	/*
	public static String corrigirVideoMCE(String str) {
		Pattern patImagem = Pattern.compile("<img class=.mceItemMedia.*?>",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		Pattern patW    = Pattern.compile("width=\".*?\"",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		Pattern patH   = Pattern.compile("height=\".*?\"",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		Pattern patURL = Pattern.compile("src':'.*?'",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		
		String imagem = RegularExp.get( patImagem, str );
		String w      = RegularExp.get( patW, imagem );
		String h      = RegularExp.get( patH, imagem );
		String url    = RegularExp.get( patURL, imagem );
		
		if( w   != null ) w   = w.replaceAll("width=", "").replaceAll("\"", "").trim();
		if( h   != null ) h   = h.replaceAll("height=", "").replaceAll("\"", "").trim();
		if( url != null ) url = url.replaceAll("src':'", "").replaceAll("'", "").trim();
		
		String frame = "<center><iframe src=\"" + url + "\" width=\"" + w + "\" height=\"" + h + "\" frameborder=\"0\"></iframe></center>";
		
		str = str.replace( imagem, frame );
		
		return str;
	}
	*/
}
