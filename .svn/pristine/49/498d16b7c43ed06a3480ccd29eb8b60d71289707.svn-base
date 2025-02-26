package com.tetrasoft.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author  Administrator
 */
public class RegularExp {

    static Pattern patHtmlJavascript = Pattern.compile("<script.*?language\\s*=[\"]javascript.*?[\"]\\s*>.*?</.*?script.*?>",Pattern.CASE_INSENSITIVE + Pattern.DOTALL);

    //Regexp reg = new Regexp("a*");
    /** Creates a new instance of RegularExp */
    public RegularExp() {

    }

    public String replaceAll(Pattern p, String content, String replaceWith) {
        Matcher m = p.matcher(content);
        if (!m.find()) return content;
        return m.replaceAll(replaceWith);
    }
    public static boolean find(Pattern p, String content) {
        Matcher m = p.matcher(content);
        return m.find();
    }

    public static String get(Pattern p, String content) {
        try {
            Matcher m = p.matcher(content);
            if (m.find())
                return m.group();
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    protected String getFirst(Pattern p, String content) {
        try {
            Matcher m = p.matcher(content);
            if (m.find())
                return m.group(0);
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}