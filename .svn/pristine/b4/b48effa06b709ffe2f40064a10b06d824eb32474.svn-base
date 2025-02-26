package com.tetrasoft.app.adaptor;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tetrasoft.app.util.RegularExp;

/**
 *
 * @author  Administrator
 */
public abstract class AbstractAdaptor extends RegularExp {
	public static final Pattern patHtmlTag = Pattern.compile("<.*?>", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    public static final Pattern patOK = Pattern.compile("<!--ok--!>",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    public static final Pattern patTituloPadrao = Pattern.compile("<!--titulo-->.*?<!--/titulo-->", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    public static final Pattern patConteudoPadrao = Pattern.compile("<!--conteudo-->.*?<!--/conteudo-->", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    public static final Pattern patEditoriaPadrao = Pattern.compile("<!--editoria-->.*?<!--/editoria-->", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    public static final Pattern patDataPadrao = Pattern.compile("<!--data-->.*?<!--/data-->", Pattern.CASE_INSENSITIVE + Pattern.DOTALL);

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd");


    /** Creates a new instance of AbstractAdaptor */
    public AbstractAdaptor() {
    }

    public String pegarValorEntre(String html, String inicio, String fim) {
        try {
            int i = html.indexOf(inicio);
            if (i == -1)
                return "";
            int i2 = html.indexOf(fim, i + inicio.length());
            if (i2 == -1)
                return "";
            return html.substring(i + inicio.length(), i2).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    private int _startPosition = 0;
    public String pegarValorEntre(String html, String inicio, String fim, int startPosition) {
        try {
            int i = html.indexOf(inicio, startPosition);
            this._startPosition = i;
            if (i == -1)
                return "";
            int i2 = html.indexOf(fim, i + inicio.length());
            if (i2 == -1)
                return "";
            return html.substring(i + inicio.length(), i2).trim();
        } catch (Exception e) {
            return "";
        }
    }
    public String removeTags(String str) {
        if (str == null)
            return str;
        StringBuffer ret = new StringBuffer(str.length());
        boolean startTag = false;
        for (int i = 0; i < str.length(); i++) {
            String a = str.substring(i, i + 1);
            if (a.equals("<")) {
                startTag = true;
            } else if (a.equals(">")) {
                startTag = false;
            } else {
                if (!startTag)
                    ret.append(a);
            }
        }
        return ret.toString();
    }

    public int getLastStartPosition() {
        return _startPosition;
    }
    public void setLastStartPosition(int i) {
        _startPosition = i;
    }

    public String replaceTags(String html, String pattern, String novo) {
        String retorno = null;
        if (html == null)
            return null;
        // log("ANTES" + html);
        Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
        Matcher m = pat.matcher(html);
        if( m.find() ) retorno = m.replaceAll(novo);
        else           retorno = html;
        // log("DEPOIS" + retorno);
        return retorno;
    }

    public String replaceTags2( String original, String[] remove ) {
        try {
            String t = original;
            for (int j = 0; j < remove.length; j++) {
                t = replaceTags( t, remove[j], "");
            }
            original = t.trim();
        } catch (Exception e) {
        }

        return original;
    }

    public static HashMap<String, String> mes = new HashMap<String, String>();

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
        mes.put("FEVEREIRO", "02");
        mes.put("FEBRUARY", "02");
        mes.put("MARÇO", "03");
        mes.put("MAR&CCEDIL;O", "03");
        mes.put("MARCH", "03");
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
}