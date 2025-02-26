// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:28
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DateUtil.java

package com.technique.engine.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{

    private DateUtil()
    {
    }

    public static String formatDateTime(Date date)
    {
        return sdf3.format(date);
    }

    public static String formatDate(Date date)
    {
        return sdf1.format(date);
    }

    public static String formatTime(Date date)
    {
        return sdf2.format(date);
    }

    public static Date addDays(Date originalDate, int days)
    {
        return new Date(originalDate.getTime() + 0x5265c00L * (long)days);
    }

    public static String getDataHojeExtenso()
    {
        GregorianCalendar g = new GregorianCalendar();
        int diaSemana = g.get(7) - 1;
        int diaMes = g.get(5);
        int mes = g.get(2);
        int ano = g.get(1);
        return diasSemana[diaSemana] + ", " + diaMes + " de " + meses[mes] + " de " + ano;
    }

    public static String getDataAbreviada(Date d, boolean showYear)
    {
        GregorianCalendar g = new GregorianCalendar();
        g.setTime(d);
        int diaMes = g.get(5);
        int mes = g.get(2);
        if(!showYear)
            return diaMes + "/" + meses[mes].substring(0, 3);
        else
            return diaMes + "/" + meses[mes].substring(0, 3) + "/" + g.get(1);
    }

    public static String diasSemana[] = {
        "Domingo", "Segunda", "Ter\347a", "Quarta", "Quinta", "Sexta", "S\341bado"
    };
    public static String meses[] = {
        "janeiro", "fevereiro", "mar\347o", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", 
        "novembro", "dezembro"
    };
    public static SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

}