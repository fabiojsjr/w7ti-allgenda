package com.tetrasoft.app.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotUtil {
	private static Pattern patInput = Pattern.compile("<input.*?>",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
	private static Pattern patValue = Pattern.compile("value=.*?[ >]",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
	private static Pattern patName = Pattern.compile("name=.*?[ >]",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
	
	void print(Object msg) {
		System.out.println(msg);
	}

	private static String getPat(Pattern p, String content) {
        try {
            Matcher m = p.matcher(content);
            if (m.find())
                return m.group();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

	/*
	public static void tratarForm(PostMethod post, Map<String,String> namepair) {
		int i = 0;
		NameValuePair[] postvars = new NameValuePair[namepair.size()];
		Iterator<String> it = namepair.keySet().iterator();
		while( it.hasNext() ) {
			String chave = (String)it.next();
			postvars[i] = new NameValuePair( chave, namepair.get(chave) );
			i++;
		}
		post.setRequestBody( postvars );
	}
	*/

	public static Map<String,String> tratarForm(String body, String form) {
		int iniForm=0;
		if (form.length()>0) {
			iniForm=body.toLowerCase().indexOf(form.toLowerCase());
			if (iniForm==-1) {
				iniForm=body.toLowerCase().indexOf("<form");
			} else {
				int vtemp=-1;
				wh:while (vtemp==-1) { 
					vtemp = body.toLowerCase().indexOf("<form",iniForm);
					iniForm--;
					if (iniForm==0) {
						iniForm=body.toLowerCase().indexOf("<form");
						break wh;
					}
				}
			}
		} else {
			iniForm=body.toLowerCase().indexOf("<form");
			if( iniForm < 0 ) iniForm = 0;
		}
		
		String response = body;

		try {
			response = body.substring( iniForm, body.toLowerCase().indexOf("form>",iniForm) );
		} catch (Exception e) {
		}
		
		HashMap<String,String> namepair = new HashMap<String,String>();
		int isec=0; 
		try {
			while( true ) {
				String input = getPat( patInput, response );
				int ips = response.indexOf(input);
				
				String name = getPat( patName, input );
				if (name!=null) {
					name=name.substring(5).trim();
				}
				String value = getPat( patValue, input );
				if (value!=null) {
					value=value.substring(6).trim();
				} else {
					value="";
				}
				
				if (name!=null) {
					if (value.startsWith("'"))
						value=value.substring(1);
					if (value.startsWith("\""))
						value=value.substring(1);
					if (value.endsWith("'"))
						value=value.substring(0,value.length()-1);
					if (value.endsWith("\""))
						value=value.substring(0,value.length()-1);
					if (value.endsWith("\">"))
						value=value.substring(0,value.length()-2);
					if (name.startsWith("'"))
						name=name.substring(1);
					if (name.startsWith("\""))
						name=name.substring(1);
					if (name.endsWith("'"))
						name=name.substring(0,name.length()-1);
					if (name.endsWith("\""))
						name=name.substring(0,name.length()-1);
					
					namepair.put(name, value);
				}
				
				response = response.substring( ips+1, response.length() );
				isec++;
				if (isec>500) {
					break;
				}
			}
		} catch (Exception e) {
		}
		return namepair;
	}
	
	public static String replaceMetaChar(String conteudo) {
		for (int i = 26; i < HTMLCHARS_TO_STRING.length; i+=2) {
			conteudo = conteudo.replaceAll( HTMLCHARS_TO_STRING[i], HTMLCHARS_TO_STRING[i+1] );
		}
		return conteudo;
	}

	private static final String[] HTMLCHARS_TO_STRING = new String[] {
		"&#34;"  , "\"",
		"&#36;"  , "$",
		"&#44;"  , ",",
		"&#38;"  , "&",
		"&#39;"  , "\"",
		"&#60;"  , "<",
		"&#62;"  , ">",
		"&#8220;" , "\"",
		"&#8221;" , "\"",
		"&#145;" , "\"",
		"&#146;" , "\"",
		"&#147;" , "\"",
		"&#148;" , "\"",
		"&#170;" , "�",
		"&#176;" , "�",
		"&#180;" , "�",
		"&#186;" , "�",
		"&#192;" , "�",
		"&#193;" , "�",
		"&#194;" , "�",
		"&#195;" , "�",
		"&#196;" , "�",
		"&#197;" , "�",
		"&#198;" , "�",
		"&#199;" , "�",
		"&#200;" , "�",
		"&#201;" , "�",
		"&#202;" , "�",
		"&#203;" , "�",
		"&#204;" , "�",
		"&#205;" , "�",
		"&#206;" , "�",
		"&#207;" , "�",
		"&#208;" , "�",
		"&#209;" , "�",
		"&#210;" , "�",
		"&#211;" , "�",
		"&#212;" , "�",
		"&#213;" , "�",
		"&#214;" , "�",
		"&#215;" , "�",
		"&#216;" , "�",
		"&#217;" , "�",
		"&#218;" , "�",
		"&#219;" , "�",
		"&#220;" , "�",
		"&#221;" , "�",
		"&#222;" , "�",
		"&#223;" , "�",
		"&#224;" , "�",
		"&#225;" , "�",
		"&#226;" , "�",
		"&#227;" , "�",
		"&#228;" , "�",
		"&#229;" , "�",
		"&#230;" , "�",
		"&#231;" , "�",
		"&#232;" , "�",
		"&#233;" , "�",
		"&#234;" , "�",
		"&#235;" , "�",
		"&#236;" , "�",
		"&#237;" , "�",
		"&#238;" , "�",
		"&#239;" , "�",
		"&#240;" , "�",
		"&#241;" , "�",
		"&#242;" , "�",
		"&#243;" , "�",
		"&#244;" , "�",
		"&#245;" , "�",
		"&#246;" , "�",
		"&#247;" , "�",
		"&#248;" , "�",
		"&#249;" , "�",
		"&#250;" , "�",
		"&#251;" , "�",
		"&#252;" , "�",
		"&#253;" , "�",
		"&#254;" , "�",
		"&#255;" , "�",
		"&#8211;" ,"-",
	};

}
