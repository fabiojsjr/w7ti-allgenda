package com.tetrasoft.app.adaptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;

import com.technique.engine.util.StringUtil;


public abstract class AbstractAdaptorMetaChar extends AbstractAdaptor {

	public String removeDoubleSpace(String str) {
		while (str.indexOf("  ") > -1) {
			str = StringUtil.replace(str, "  ", " ");
		}
		return str;
	}

	public String getMes(String mes) {
		if (mes == null) return "00";
		for (int i = 0; i < MESES.length; i++) {
			if (mes.equalsIgnoreCase(MESES[i])) {
				if (i < 9) {
					return "0" + (i + 1);
				}
				return "" + (i + 1);
			}
		}
		return "0";
	}

	public static final String[] MESES = new String[] {
		"Janeiro",
		"Fevereiro",
		"Mar�o",
		"Abril",
		"Maio",
		"Junho",
		"Julho",
		"Agosto",
		"Setembro",
		"Outubro",
		"Novembro",
		"Dezembro"
	};

	public static final String[] HTMLCHARS_TO_STRING = new String[] {
			"(?sim)<(/|)form.*?>","",
			"&quot;","'",
			"&ldquo;","'",
			"&rdquo;","'",
			"&nbsp;"," ",
			"�","�",
			"&ccedil;","�",
			"&Ccedil;","�",
			"&atilde;","�",
			"&otilde;","�",
			"&Atilde;","�",
			"&Otilde;","�",
			"&ecirc;","�",
			"&ocirc;","�",
			"&Ocirc;","�",
			"&euml;","e",
			"&reg;","(R)",
			"�","&#174;",
			"&agrave;","�",
			"&acirc;","�",
			"&Acirc;","�",
			"&Ecirc;","�",
			"&aacute;","�",
			"&eacute;","�",
			"&iacute;","�",
			"&oacute;","�",
			"&uacute;","�",
			"&Aacute;","�",
			"&Eacute;","�",
			"&Iacute;","�",
			"&Oacute;","�",
			"&Uacute;","�",
			"&copy;","(c)",
			"&amp;","&#38;",
			"&sup2;","2",
			"&gt;","&#62;",
			"&lt;","&#60;",
			"&ordf;","�",
			"&ordm;","�",
			"&euro;","$Euro",
			"&Agrave;","�",
			"&#034;", "'",
			"&#039;", "'",
			"&#039", "'",
 	};

    public static final String[] HTMLCHARS_TO_STRING_2 = new String[] {
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
    };

	public static final String[] COMPLEX_TO_SIMPLE = new String[] {
			" ","",
/*			"@","",
  			"#","",
  			"$","",
			"%","",
			"�","",
			"&","",
  			"*","",
			"+","",
			"=","",
  	        "(","",
  	        ")","",
			"�","",
*/	        "�","",
			"�","",
			"�","",
			"'","",
			"\"","",
			"�","",
			"`","",
			"�","c",
			"�","C",
			"�","o",
			"�","o",
			"�","o",
			"�","o",
			"�","o",
			"�","O",
			"�","O",
			"�","O",
			"�","O",
			"�","O",
			"�","a",
			"�","a",
			"�","a",
			"�","a",
			"�","a",
			"�","A",
			"�","A",
			"�","A",
			"�","A",
			"�","A",
			"�","e",
			"�","e",
			"�","e",
			"�","e",
			"�","E",
			"�","E",
			"�","E",
			"�","E",
			"�","i",
			"�","i",
			"�","i",
			"�","i",
			"�","I",
			"�","I",
			"�","I",
			"�","I",
			"�","u",
			"�","u",
			"�","u",
			"�","u",
			"�","U",
			"�","U",
			"�","U",
			"�","U",
			"�","2",
			"�","3",
	};

    protected static HashMap<String, String> mes = new HashMap<String, String>();

    static {
        mes.put("JAN", "01");
        mes.put("FEV", "02");
        mes.put("FEB", "02");
        mes.put("MAR", "03");
        mes.put("ABR", "04");
        mes.put("APR", "04");
        mes.put("MAI", "05");
        mes.put("MAY", "05");
        mes.put("JUN", "06");
        mes.put("JUL", "07");
        mes.put("AGO", "08");
        mes.put("AUG", "08");
        mes.put("SET", "09");
        mes.put("SEP", "09");
        mes.put("OUT", "10");
        mes.put("OCT", "10");
        mes.put("NOV", "11");
        mes.put("DEZ", "12");
        mes.put("DEC", "12");
        mes.put("JANEIRO", "01");
        mes.put("JANUARY", "01");
        mes.put("FEVEREIRO", "02");
        mes.put("FEBRUARY", "02");
        mes.put("MAR�O", "03");
        mes.put("MARCH", "03");
        mes.put("MAR&CCEDIL;O", "03");
        mes.put("MARS", "03");
        mes.put("ABRIL", "04");
        mes.put("APRIL", "04");
        mes.put("MAIO", "05");
        mes.put("MAY", "05");
        mes.put("JUNHO", "06");
        mes.put("JUNE", "06");
        mes.put("JULHO", "07");
        mes.put("JULY", "07");
        mes.put("AGOSTO", "08");
        mes.put("AUGUST", "08");
        mes.put("SETEMBRO", "09");
        mes.put("SEPTEMBER", "09");
        mes.put("OUTUBRO", "10");
        mes.put("OCTOBER", "10");
        mes.put("NOVEMBRO", "11");
        mes.put("NOVEMBER", "11");
        mes.put("DEZEMBRO", "12");
        mes.put("DECEMBER", "12");
    }

	public static String replaceMetaChar_2(String conteudo) {
		for (int i = 0; i < HTMLCHARS_TO_STRING_2.length; i+=2) {
			conteudo = StringUtil.replace(conteudo, HTMLCHARS_TO_STRING_2[i],HTMLCHARS_TO_STRING_2[i+1]);
		}
		return conteudo;
	}

	public static String replaceMetaChar(String conteudo) {
        for (int i = 0; i < HTMLCHARS_TO_STRING.length; i+=2) {
			conteudo = StringUtil.replace(conteudo, HTMLCHARS_TO_STRING[i], HTMLCHARS_TO_STRING[i+1]);
		}
		return conteudo;
	}

	public static String replaceMetaChar_3(String conteudo) {
		for (int i = 20; i < HTMLCHARS_TO_STRING_2.length; i+=2) {
			conteudo = conteudo.replaceAll( HTMLCHARS_TO_STRING_2[i+1], HTMLCHARS_TO_STRING_2[i] );
		}
		return conteudo;
	}
	
	public static String replaceMetaChar_4(String conteudo) {
        for (int i = 26; i < HTMLCHARS_TO_STRING_2.length; i+=2) {
			conteudo = conteudo.replaceAll( HTMLCHARS_TO_STRING_2[i+1], HTMLCHARS_TO_STRING_2[i] );
		}
		return conteudo;
	}

	public static String replaceMetaChar_Simple(String conteudo) {
        for (int i = 2; i < COMPLEX_TO_SIMPLE.length; i+=2) {
			conteudo = StringUtil.replace(conteudo, COMPLEX_TO_SIMPLE[i], COMPLEX_TO_SIMPLE[i+1]);
		}
		return conteudo;
	}

    public String formatData(String date) {
        date = date.toLowerCase();
        String retorno = null;
        CharSequence day, month, year;
        day = date.subSequence(0, 2);
        month = date.subSequence(date.indexOf("de") + 3, date.lastIndexOf("de") - 1);
        month = (CharSequence)mes.get(month.toString().toUpperCase());
        year = date.substring(date.lastIndexOf("de") + 3);
        retorno = day + "/" + month + "/" + year;
        return retorno;
    }
    
	public static String convertCharset(String origem, String charsetFrom, String charsetTo) throws IOException {
		InputStream in = new ByteArrayInputStream( origem.getBytes() );
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		int i;
		byte[] buf = new byte[8192];
		while(( i = in.read( buf )) > 0 ) {
			out.write( buf, 0, i );
		}
		in.close();
		out.flush();
		
		ByteArrayInputStream in2 = new ByteArrayInputStream(out.toString(charsetFrom).getBytes());
		ByteArrayOutputStream out2 = new ByteArrayOutputStream();
		buf = new byte[8192];
		while(( i = in2.read( buf )) > 0 ) {
			out2.write( buf, 0, i );
		}
		in2.close();
		out2.flush();
		
		return out2.toString( charsetTo );
	}

	public static String convertCharset(HttpURLConnection conn, boolean utf) throws IOException, UnsupportedEncodingException {
		conn.connect();
//		if (conn.getContentType().toLowerCase().indexOf("utf")!=-1) {
		if (utf) {
			return convertDO(conn, "UTF-8", "UTF-8");
		}
		return convertDownload(conn);
	}
	
	private static String convertDownload(HttpURLConnection conn) throws IOException, UnsupportedEncodingException {
		InputStream in = conn.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		int i;
		byte[] buf = new byte[8192];
		while(( i = in.read( buf )) > 0 ) {
			out.write( buf, 0, i );
		}
		in.close();
		out.flush();
		conn.disconnect();
		return out.toString();
	}
	
	public static String convertCharset(HttpURLConnection conn, String charsetFrom, String charsetTo) throws IOException, UnsupportedEncodingException {
		conn.connect();
		return convertDO(conn, charsetFrom, charsetTo);
	}

	private static String convertDO(HttpURLConnection conn, String charsetFrom, String charsetTo) throws IOException, UnsupportedEncodingException {
		InputStream in = conn.getInputStream();
//		if (conn.getContentEncoding().toLowerCase().indexOf("gzip")!=-1) {
//			urlsGzip++;
//			in = new GZIPInputStream(in);
//		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		int i;
		byte[] buf = new byte[8192];
		while(( i = in.read( buf )) > 0 ) {
			out.write( buf, 0, i );
		}
		in.close();
		out.flush();
		conn.disconnect();
		
		ByteArrayInputStream in2 = new ByteArrayInputStream(out.toString(charsetFrom).getBytes());
		ByteArrayOutputStream out2 = new ByteArrayOutputStream();
		buf = new byte[8192];
		while(( i = in2.read( buf )) > 0 ) {
			out2.write( buf, 0, i );
		}
		in2.close();
		out2.flush();
		
		return out2.toString(charsetTo);
	}

	public static byte[] convert(byte[] data, String srcEncoding, String targetEncoding) {
        byte[] result = null;
        try {
            // First, decode the data using the source encoding.
            // The String constructor does this (Javadoc).
            String str = new String(data, srcEncoding);

            // Next, encode the data using the target encoding.
            // The String.getBytes() method does this.
            result = str.getBytes(targetEncoding);
        } catch (Exception uee) {
            uee.printStackTrace();
        } finally {
        }
        return result;
    }
}