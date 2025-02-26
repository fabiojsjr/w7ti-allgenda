// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:16:22
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ConsoleLogger.java

package com.technique.engine.logging;

import com.technique.engine.util.ExceptionWarning;

// Referenced classes of package com.technique.engine.logging:
//            AbstractLogger

public class ConsoleLogger extends AbstractLogger
{

    public ConsoleLogger()
    {
    }

    public ConsoleLogger(int level, int defaultLevel)
    {
        super(level, defaultLevel);
    }

    protected void write(String message)
    {
        System.out.println(message);
    }

    public void instantiate(String s)
        throws ExceptionWarning
    {
    }
}