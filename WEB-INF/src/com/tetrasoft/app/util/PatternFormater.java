package com.tetrasoft.app.util;

import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.technique.engine.util.StringUtil;

/**
 *
 * @author  Administrator
 */
public class PatternFormater {
	private static final PatternFormater instance = new PatternFormater();

	public static PatternFormater getInstance() {
		return instance;

	}

	/** Creates a new instance of PatternFormater */
	private PatternFormater() {
	}

	public String getExpressao(StringBuffer expressao) {
		if (expressao == null) return null;
		String str = expressao.toString().replace('\'',' ').trim();

		if( str.trim().equalsIgnoreCase("ig") ) str = "[^\\.]" + str;

		str = StringUtil.replace(str,"  "," ");
		//str = StringUtil.replace(str," e "," ");
		//if (str.endsWith("]")) {
		//    str = "(\\b"+str.substring(0,str.length()-1)+"\\b{0})";
		//} else {
		str = "(\\b"+str+"\\b)+";
		//}
		str = StringUtil.replaceIgnoreCase(str," ou ","\\b)+|(\\b");
		//str = StringUtil.replaceIgnoreCase(str.trim(),"[","(\\b");
		str = StringUtil.replaceIgnoreCase(str.trim()," e ", "\\b)+.*?(\\b");
		str = StringUtil.replaceIgnoreCase(str.trim(),"\\b(","(");
		return str;
	}
	public String getExpressao2(StringBuffer expressao) {
		if (expressao == null) return null;
		String str = expressao.toString().replace('\'',' ').trim();

		if( str.trim().equalsIgnoreCase("ig") ) str = "[^\\.]" + str;

		str = StringUtil.replace(str,"  "," ").trim();
		str = "(\\b"+str+"\\b)+";
		return str;
	}

	public String getExpressaoSublinhada(StringBuffer expressao, String conteudo,String idClienteLogado) {
		try {
			if (expressao == null) return conteudo;
			if (conteudo  == null) return conteudo;

			String cont = conteudo;
			Pattern p = formatExpressao(expressao);
			Matcher m = p.matcher(cont);

			boolean ok = true;
			if(ok){
				if( m.find() ) {
					if(idClienteLogado.equals("1226512393031")){
						return m.replaceAll("<b><font color=navy>$0</font></b>");//Exceção:OI
					}else{
						return m.replaceAll("<b><font color=CC0000>$0</font></b>");
					}
				}
			}
			return cont;
		} catch (Exception e) {
			return conteudo;
		}
	}

	/******************************** ORIGINAL *************************************
   public String getExpressaoSublinhada2(StringBuffer expressao, String conteudo) {
      try {
         if (expressao == null) return conteudo;
         if (conteudo == null) return conteudo;
         String cont = conteudo;
         Pattern p = formatExpressao(expressao);
         Matcher m = p.matcher(cont);
         if (!m.find()) {
            return cont;
         } else if (cont.indexOf(" e ") == -1) {
            return m.replaceAll("<b><font color=CC0000>$0</font></b>");
         }
         String exp = StringUtil.replaceIgnoreCase(expressao.toString()," e ","-@-");
         StringTokenizer st = new StringTokenizer(exp,"-@-");
         while (st.hasMoreElements()) {
            String exp2 = st.nextToken();
            p = formatExpressao2(new StringBuffer(exp2.trim()));
            m = p.matcher(cont);
            if (m.find()) {
               cont = m.replaceAll("<b><font color=CC0000>$0</font></b>");
            }
         }
         return cont;
      } catch (Exception e) {
         return conteudo;
      }
   }
	 */

	/************************************* RENATO *********************************/
	public String getExpressaoSublinhada2(StringBuffer expressao, String conteudo) {
		try {
			if (expressao == null) return conteudo;
			if (conteudo  == null) return conteudo;

			/*
			String s = expressao.toString();
			s = s.replaceAll("\\(", "");
			s = s.replaceAll("\\)", "");
		    expressao = new StringBuffer(s);
		    System.out.println(expressao.toString());
			 */

			String cont = conteudo;
			Pattern p   = formatExpressao(expressao);
			Matcher m   = p.matcher(cont);

			if (!m.find()) {
				return cont;
			} else if (cont.indexOf(" e ") == -1) {
				return m.replaceAll("<b><font color=CC0000>$0</font></b>");
			}

			String exp1  = expressao.toString();
			/*
            System.out.println("==================================");
            System.out.println("TESTE 1 = " + expressao.toString() );

            String chave = "";
            if( exp1.indexOf(" e ") < 0 ) {
                int i = exp1.indexOf("(");
                if( i > 0 ) {
                    chave = exp1.substring(0,i);
                } else {
                    int j = exp1.lastIndexOf(")");
                    chave = exp1.substring(j+1,exp1.length());
                }
            }
            System.out.println("TESTE (chave) = " + chave );
			 */
			String exp = StringUtil.replaceIgnoreCase(exp1," e ","§");
			exp        = StringUtil.replaceIgnoreCase(exp,"|","§");
//			exp        = StringUtil.replaceIgnoreCase(exp,"(","§");
//			exp        = StringUtil.replaceIgnoreCase(exp,")","§");

//			System.out.println("TESTE 2 = " + exp );

			StringTokenizer st = new StringTokenizer(exp,"§");

//			System.out.println("TESTE 3 (total) = " + st.countTokens() );

			while( st.hasMoreTokens() ) {
				String exp2 = st.nextToken();
				exp2 = exp2.replaceAll("\\(","");
				exp2 = exp2.replaceAll("\\)","");
				exp2 = exp2.trim();

				/*              if( exp2.indexOf("(") < 0 ||
                    exp2.indexOf(")") < 0 ) { // se houver um '(' sem ')'...

                    exp2 = exp2.replace('(',' ');
                    exp2 = exp2.replace(')',' ');
                    exp2 = exp2.trim();
                }
  		        System.out.println("exp2 = " + exp2);
				 */
				if( exp2.trim().length() > 1 ) {
					p = formatExpressao2(new StringBuffer(exp2.trim()));
					m = p.matcher(cont);

					if( m.find() ) {
						cont = m.replaceAll("<b><font color=CC0000>$0</font></b>");
					}
				}
			}

			return cont;
		} catch (Exception e) {
//			e.printStackTrace();
			return conteudo;
		}
	}

	public Pattern formatExpressao(String expressao) {
		Pattern p = null;
		try {
			p = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		} catch (Exception e) {

		}
		return p;
	}
	public Pattern formatExpressao(StringBuffer expressao) {
		if (expressao == null) return null;
		return formatExpressao(getExpressao(expressao));
	}
	public Pattern formatExpressao2(StringBuffer expressao) {
		if (expressao == null) return null;
		return formatExpressao(getExpressao2(expressao));
	}

	public boolean contemExpressao(StringBuffer expr, String conteudo) {
		String s = PatternFormater.getInstance().getExpressao(expr);

		if (s == null)
			return false;

		Hashtable<String, Pattern> patterns = new Hashtable<String, Pattern>();
		Pattern p = (Pattern) patterns.get(s);
		if (p == null) {
			p = PatternFormater.getInstance().formatExpressao(s);
			if (p == null) {
				System.out.print("<wrong pattern> " + s + " </wrong pattern>");
				return false;
			}
			patterns.put(s, p);
		}

		if( !find(p, conteudo) ) {
			return false;
		}

		return true;
	}

	protected boolean find(Pattern p, String content) {
		Matcher m = p.matcher(content);
		return m.find();
	}
}