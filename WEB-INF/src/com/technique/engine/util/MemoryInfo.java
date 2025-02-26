// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:31
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MemoryInfo.java

package com.technique.engine.util;


public class MemoryInfo
{

    private MemoryInfo()
    {
    }

    public static long getAvailable()
    {
        return Runtime.getRuntime().freeMemory();
    }

    public static long getTotal()
    {
        return Runtime.getRuntime().totalMemory();
    }

    public static long getInUse()
    {
        return getTotal() - getAvailable();
    }

    public static synchronized void gc()
    {
        System.runFinalization();
        System.gc();
    }
}