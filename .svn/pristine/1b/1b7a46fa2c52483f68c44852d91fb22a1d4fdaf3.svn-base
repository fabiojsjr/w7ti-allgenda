// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:31
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   NumberUtil.java

package com.technique.engine.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtil
{

    private NumberUtil()
    {
    }

    public static String formatCurrencyBrasil(double value)
    {
        return "R$" + formatoReal.format(value);
    }

    public static String formatCurrencyBrasil(double value, boolean showSimbol)
    {
        if(showSimbol)
            return "R$" + formatoReal.format(value);
        else
            return formatoReal.format(value);
    }

    public static String formatCurrency(double value)
    {
        return NumberFormat.getCurrencyInstance().format(value).toString();
    }

    public static String formatCurrency(double value, Locale locale)
    {
        return NumberFormat.getCurrencyInstance(locale).format(value).toString();
    }

    public static String formatPercent(double value)
    {
        return NumberFormat.getPercentInstance().format(value).toString();
    }

    public static String formatPercent(double value, Locale locale)
    {
        return NumberFormat.getPercentInstance(locale).format(value).toString();
    }

    private static DecimalFormat formatoReal = new DecimalFormat("#,##0.00");

}