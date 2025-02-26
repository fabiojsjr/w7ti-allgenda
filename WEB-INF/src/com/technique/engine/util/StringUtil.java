// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:33
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   StringUtil.java

package com.technique.engine.util;


public class StringUtil
{

    private StringUtil()
    {
    }

    public static String firstToUpperCase(String str)
    {
    	return str.substring(0, 1).toUpperCase() + str.substring(1, str.length()).toLowerCase();
    }
    
    public static String firstToLowerCase(String str)
    {
        return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
    }

    public static String toDBString(String aString)
    {
        if(aString == null)
            return "null";
        int posQuote = aString.indexOf("'");
        if(posQuote < 0)
            return "'" + aString + "'";
        StringBuffer ret = new StringBuffer(aString.length() + 10);
        int lastPosQuote = 0;
        for(; posQuote != -1; posQuote = aString.indexOf("'", lastPosQuote))
        {
            ret.append(aString.substring(lastPosQuote, posQuote) + "'" + "'");
            lastPosQuote = posQuote + 1;
        }

        ret.append(aString.substring(lastPosQuote, aString.length()));
        return "'" + ret.toString() + "'";
    }

    public static String toXMLString(String aString)
    {
        if(aString == null)
            return null;
        int numChars = badXMLCharacters.length;
        boolean found = false;
        for(int i = 0; i < numChars; i++)
        {
            if(aString.indexOf(badXMLCharacters[i]) < 0)
                continue;
            found = true;
            break;
        }

        if(!found)
            return aString;
        int valueLength = aString.length();
        char valueChars[] = new char[valueLength];
        aString.getChars(0, valueLength, valueChars, 0);
        StringBuffer ret = new StringBuffer(valueLength + 50);
        for(int i = 0; i < valueLength; i++)
            switch(valueChars[i])
            {
            case 34: // '"'
                ret.append("&quot;");
                break;

            case 39: // '\''
                ret.append("&apos;");
                break;

            case 60: // '<'
                ret.append("&lt;");
                break;

            case 62: // '>'
                ret.append("&gt;");
                break;

            case 38: // '&'
                ret.append("&amp;");
                break;

            default:
                ret.append(valueChars[i]);
                break;
            }

        return ret.toString();
    }
    
    public static String toXMLStringSimple(String aString)
    {
        if(aString == null)
            return null;
        int numChars = badXMLCharacters.length;
        boolean found = false;
        for(int i = 0; i < numChars; i++)
        {
            if(aString.indexOf(badXMLCharacters[i]) < 0)
                continue;
            found = true;
            break;
        }

        if(!found)
            return aString;
        int valueLength = aString.length();
        char valueChars[] = new char[valueLength];
        aString.getChars(0, valueLength, valueChars, 0);
        StringBuffer ret = new StringBuffer(valueLength + 50);
        for(int i = 0; i < valueLength; i++)
            switch(valueChars[i])
            {
            case 34: // '"'
                ret.append("&quot;");
                break;

            case 39: // '\''
                ret.append("&apos;");
                break;

            case 60: // '<'
                ret.append("&lt;");
                break;

            case 62: // '>'
                ret.append("&gt;");
                break;

            default:
                ret.append(valueChars[i]);
                break;
            }

        return ret.toString();
    }
    

    public static String replace(String value, String originalString, String newString)
    {
        if(value == null)
            return null;
        if(originalString == null)
            return value;
        if(newString == null)
            return value;
        StringBuffer ret = new StringBuffer(value.length() + 50);
        do
        {
            int i = value.indexOf(originalString);
            if(i >= 0)
            {
                if(i > 0)
                    ret.append(value.substring(0, i) + newString);
                else
                    ret.append(newString);
                value = value.substring(i + originalString.length(), value.length());
            } else
            {
                ret.append(value);
                return ret.toString();
            }
        } while(true);
    }

    public static String replaceIgnoreCase(String value, String originalString, String newString)
    {
        if(value == null)
            return null;
        if(originalString == null)
            return value;
        if(newString == null)
            return value;
        StringBuffer ret = new StringBuffer(value.length() + 50);
        String valuetmp = value.toUpperCase();
        String valueorigtmp = originalString.toUpperCase();
        do
        {
            int i = valuetmp.indexOf(valueorigtmp);
            if(i >= 0)
            {
                if(i > 0)
                    ret.append(value.substring(0, i) + newString);
                else
                    ret.append(newString);
                valuetmp = valuetmp.substring(i + originalString.length(), valuetmp.length());
                value = value.substring(i + originalString.length(), value.length());
            } else
            {
                ret.append(value);
                return ret.toString();
            }
        } while(true);
    }

    public static String replicate(String value, int count)
    {
        if(value == null)
            return null;
        StringBuffer ret = new StringBuffer(value.length() * count);
        for(int i = 0; i < count; i++)
            ret.append(value);

        return ret.toString();
    }

    public static String fillWithSpaces(String value, int size)
    {
        if(value == null)
            return null;
        if(value.length() > size)
            return value.substring(0, size);
        else
            return value + replicate(" ", size - value.length());
    }

    private static char badXMLCharacters[] = {
        '"', '\'', '<', '>', '&'
    };
    
    
    
    public static String converteValorPorExtenso(double vlr) {
        if (vlr == 0)
           return("zero");

        long inteiro = (long)Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15)
           return("Erro: valor superior a 999 trilhões.");

        String s = "", saux, vlrP;
        String centavos = String.valueOf((int)Math.round(resto * 100));

        String[] unidade = {"", "um", "dois", "três", "quatro", "cinco",
                 "seis", "sete", "oito", "nove", "dez", "onze",
                 "doze", "treze", "quatorze", "quinze", "dezesseis",
                 "dezessete", "dezoito", "dezenove"};
        String[] centena = {"", "cento", "duzentos", "trezentos",
                 "quatrocentos", "quinhentos", "seiscentos",
                 "setecentos", "oitocentos", "novecentos"};
        String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
                 "sessenta", "setenta", "oitenta", "noventa"};
        String[] qualificaS = {"", "mil", "milhão", "bilhão", "trilhão"};
        String[] qualificaP = {"", "mil", "milhões", "bilhões", "trilhões"};

    // definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
          tam = vlrS.length();
    // retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
    // 1a. parte = 789 (centena)
    // 2a. parte = 456 (mil)
    // 3a. parte = 123 (milhões)
          if (tam > 3) {
             vlrP = vlrS.substring(tam-3, tam);
             vlrS = vlrS.substring(0, tam-3);
          }
          else { // última parte do valor
            vlrP = vlrS;
            vlrS = "0";
          }
          if (!vlrP.equals("000")) {
             saux = "";
             if (vlrP.equals("100"))
                saux = "cem";
             else {
               n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
               cent = n / 100;                  // cent = 3 (centena trezentos)
               dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
               unid = (n % 100) % 10;           // unid = 1 (unidade um)
               if (cent != 0)
                  saux = centena[cent];
               if ((n % 100) <= 19) {
                  if (saux.length() != 0)
                     saux = saux + " e " + unidade[n % 100];
                  else saux = unidade[n % 100];
               }
               else {
                  if (saux.length() != 0)
                     saux = saux + " e " + dezena[dez];
                  else saux = dezena[dez];
                  if (unid != 0) {
                     if (saux.length() != 0)
                        saux = saux + " e " + unidade[unid];
                     else saux = unidade[unid];
                  }
               }
             }
             if (vlrP.equals("1") || vlrP.equals("001")) {
                if (i == 0) // 1a. parte do valor (um real)
                   umReal = true;
                else saux = saux + " " + qualificaS[i];
             }
             else if (i != 0)
                     saux = saux + " " + qualificaP[i];
             if (s.length() != 0)
                s = saux + ", " + s;
             else s = saux;
          }
          if (((i == 0) || (i == 1)) && s.length() != 0)
             tem = true; // tem centena ou mil no valor
          i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ...
        }

        if (s.length() != 0) {
           if (umReal)
              s = s + " real";
           else if (tem)
                   s = s + " reais";
                else s = s + " de reais";
        }

    // definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
           if (s.length() != 0) // se não é valor somente com centavos
              s = s + " e ";
           if (centavos.equals("1"))
              s = s + "um centavo";
           else {
             n = Integer.parseInt(centavos, 10);
             if (n <= 19)
                s = s + unidade[n];
             else {             // para n = 37, tem-se:
               unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
               dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
               s = s + dezena[dez];
               if (unid != 0)
                  s = s + " e " + unidade[unid];
             }
             s = s + " centavos";
           }
        }
        return(s);
      }
}