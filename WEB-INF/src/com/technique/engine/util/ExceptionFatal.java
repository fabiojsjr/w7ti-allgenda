// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:29
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ExceptionFatal.java

package com.technique.engine.util;


// Referenced classes of package com.technique.engine.util:
//            ExceptionAbstract

public class ExceptionFatal extends ExceptionAbstract
{

    public ExceptionFatal()
    {
    }

    public ExceptionFatal(String message)
    {
        super(message);
    }

    public ExceptionFatal(String sid, String message)
    {
        super(sid, message);
    }

    public ExceptionFatal(Throwable e)
    {
        super(e);
    }

    public ExceptionFatal(String message, Throwable e)
    {
        super(message, e);
    }

    public ExceptionFatal(String sid, String message, Throwable e)
    {
        super(sid, message, e);
    }

    public int getPriority()
    {
        return 99;
    }
}