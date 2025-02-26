package com.tetrasoft.util.ajax;

import com.technique.engine.web.CommandWrapper;
import com.technique.engine.xml.XMLData;
import com.tetrasoft.app.sender.SenderInterface;

public class AjaxUtil {
	public static String formatXML( String xml ) {
		xml = xml.replaceAll("\r", ""); // quebra de linha
		xml = xml.replaceAll("\n", ""); // quebra de linha
		xml = xml.replaceAll("	", " "); // tabs
		for( int i = 0; i < 5; i++ )
			xml = xml.replaceAll("  ", " "); // espaço
		
		int inicio = xml.indexOf("<document>");

		String novo = xml.substring(0, inicio);
		xml = xml.substring( inicio, xml.length() );
		inicio = -1;
		
		String tag = "";
		while( true ) {
			inicio = xml.indexOf("<", inicio);
			
			int espaco = xml.indexOf(" ", inicio);
			int fim    = xml.indexOf(">", inicio);
			
			if( espaco < 0 ) {
				novo += xml;
				break;
			} else if( espaco > fim ) {
				inicio = fim;
				novo += xml.substring( 0, inicio+1 ); 
				xml = xml.substring(inicio+1, xml.length());
				
				if( xml.startsWith("<![CDATA[") ) {
					novo += xml.substring(0, xml.indexOf("]]>")+3 );
					xml = xml.substring(xml.indexOf("]]>")+3, xml.length());
				}
				
				inicio = -2;
			} else {
				tag   = xml.substring(inicio+1, espaco);
				novo += "<" + tag + ">"; 
				
				String interno = xml.substring(espaco, fim);
				interno = interno.replaceAll("command=", "command!"); // exceção
				interno = interno.replaceAll("=\"", ">");
				interno = interno.replaceAll("\"",  "<");
				interno = interno.replaceAll("command!", "command="); // exceção
				
				try {
					String child = "";
					while( true ) {
						child = interno.substring(0, interno.indexOf(">") ).trim();
						novo += "<" + child + ">";
						
						novo += interno.substring( interno.indexOf(">")+1, interno.indexOf("<") );
						novo += "</" + child + ">";
						interno = interno.substring( interno.indexOf("<")+1, interno.length() );
					}
				} catch (Exception e) {
				}
				
				novo += "</" + tag + ">";
				
				xml = xml.trim();
				xml = xml.substring( xml.indexOf("<", 1), xml.length() );
				inicio = -2;
			}

			inicio++;
			if( fim < 0 ) break;
		}
		
		return novo;
	}
	
	public static void main( String[] args ) {
        try {
			CommandWrapper wrapper = new CommandWrapper(SenderInterface.SID);
			XMLData data = wrapper.getXMLData();

//			ClienteEntity cliente = new ClienteEntity();
//			cliente.prepareUpdate(data, "3762", "");
			
			String xml = data.getXML();
			xml = formatXML( xml );
			System.out.println(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
