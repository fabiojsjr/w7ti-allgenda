// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:30
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ExceptionWarning.java

package com.technique.engine.util;


// Referenced classes of package com.technique.engine.util:
//            ExceptionAbstract

public class ExceptionInjection extends ExceptionAbstract
{

    public ExceptionInjection()
    {
    }

    public ExceptionInjection(String message)
    {
        super(message);
    }

    public ExceptionInjection(String sid, String message)
    {
        super(sid, message);
    }

    public ExceptionInjection(Throwable e)
    {
        super(e);
    }

    public ExceptionInjection(String message, Throwable e)
    {
        super(message, e);
    }

    public ExceptionInjection(String sid, String message, Throwable e)
    {
        super(sid, message, e);
    }

    public int getPriority()
    {
        return 50;
    }
}