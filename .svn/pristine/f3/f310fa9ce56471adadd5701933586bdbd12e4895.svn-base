// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:29
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ExceptionInfo.java

package com.technique.engine.util;


// Referenced classes of package com.technique.engine.util:
//            ExceptionAbstract

public class ExceptionInfo extends ExceptionAbstract
{

    public int getType()
    {
        return _type;
    }

    public void setType(int type)
    {
        _type = type;
    }

    public ExceptionInfo()
    {
        _type = 1;
    }

    public ExceptionInfo(String message)
    {
        super(message);
        _type = 1;
    }

    public ExceptionInfo(String message, int type)
    {
        super(message);
        _type = 1;
        _type = type;
    }

    public ExceptionInfo(String sid, String message)
    {
        super(sid, message);
        _type = 1;
    }

    public ExceptionInfo(Throwable e)
    {
        super(e);
        _type = 1;
    }

    public ExceptionInfo(Throwable e, int type)
    {
        super(e);
        _type = 1;
        _type = type;
    }

    public ExceptionInfo(String message, Throwable e)
    {
        super(message, e);
        _type = 1;
    }

    public ExceptionInfo(String sid, String message, Throwable e)
    {
        super(sid, message, e);
        _type = 1;
    }

    public int getPriority()
    {
        return 0;
    }

    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_INFO = 1;
    public static final int TYPE_DECISION = 2;
    public static final int TYPE_QUESTION = 2;
    public static final int TYPE_WARNING = 4;
    public static final int TYPE_ERROR = 5;
    private int _type;
}