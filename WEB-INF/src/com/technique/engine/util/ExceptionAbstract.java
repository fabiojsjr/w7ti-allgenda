// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:28
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ExceptionAbstract.java

package com.technique.engine.util;


public abstract class ExceptionAbstract extends Exception
{

    public ExceptionAbstract()
    {
        sid = null;
        e = null;
    }

    public ExceptionAbstract(String message)
    {
        super(message);
        sid = null;
        e = null;
    }

    public ExceptionAbstract(String sid, String message)
    {
        super(message);
        this.sid = null;
        e = null;
        this.sid = sid;
    }

    public ExceptionAbstract(Throwable e)
    {
        super(e);
        sid = null;
        this.e = null;
        this.e = e;
    }

    public ExceptionAbstract(String message, Throwable e)
    {
        super(message);
        sid = null;
        this.e = null;
        this.e = e;
    }

    public ExceptionAbstract(String sid, String message, Throwable e)
    {
        super(message);
        this.sid = null;
        this.e = null;
        this.e = e;
        this.sid = sid;
    }

    public String getSID()
    {
        return sid;
    }

    public Throwable getOriginalException()
    {
        return e;
    }

    public abstract int getPriority();

    private String sid;
    private Throwable e;
}