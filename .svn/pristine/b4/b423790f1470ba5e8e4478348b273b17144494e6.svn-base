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
		"&#170;" , "ª",
		"&#176;" , "°",
		"&#180;" , "´",
		"&#186;" , "º",
		"&#192;" , "À",
		"&#193;" , "Á",
		"&#194;" , "Â",
		"&#195;" , "Ã",
		"&#196;" , "Ä",
		"&#197;" , "Å",
		"&#198;" , "Æ",
		"&#199;" , "Ç",
		"&#200;" , "È",
		"&#201;" , "É",
		"&#202;" , "Ê",
		"&#203;" , "Ë",
		"&#204;" , "Ì",
		"&#205;" , "Í",
		"&#206;" , "Î",
		"&#207;" , "Ï",
		"&#208;" , "Ð",
		"&#209;" , "Ñ",
		"&#210;" , "Ò",
		"&#211;" , "Ó",
		"&#212;" , "Ô",
		"&#213;" , "Õ",
		"&#214;" , "Ö",
		"&#215;" , "×",
		"&#216;" , "Ø",
		"&#217;" , "Ù",
		"&#218;" , "Ú",
		"&#219;" , "Û",
		"&#220;" , "Ü",
		"&#221;" , "Ý",
		"&#222;" , "Þ",
		"&#223;" , "ß",
		"&#224;" , "à",
		"&#225;" , "á",
		"&#226;" , "â",
		"&#227;" , "ã",
		"&#228;" , "ä",
		"&#229;" , "å",
		"&#230;" , "æ",
		"&#231;" , "ç",
		"&#232;" , "è",
		"&#233;" , "é",
		"&#234;" , "ê",
		"&#235;" , "ë",
		"&#236;" , "ì",
		"&#237;" , "í",
		"&#238;" , "î",
		"&#239;" , "ï",
		"&#240;" , "ð",
		"&#241;" , "ñ",
		"&#242;" , "ò",
		"&#243;" , "ó",
		"&#244;" , "ô",
		"&#245;" , "õ",
		"&#246;" , "ö",
		"&#247;" , "÷",
		"&#248;" , "ø",
		"&#249;" , "ù",
		"&#250;" , "ú",
		"&#251;" , "û",
		"&#252;" , "ü",
		"&#253;" , "ý",
		"&#254;" , "þ",
		"&#255;" , "ÿ",
		"&#8211;" ,"-",
	};

}
