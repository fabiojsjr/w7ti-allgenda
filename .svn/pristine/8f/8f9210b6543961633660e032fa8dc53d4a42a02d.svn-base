// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:16:22
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FileLogger.java

package com.technique.engine.logging;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionWarning;
import java.io.*;

// Referenced classes of package com.technique.engine.logging:
//            AbstractLogger

public class FileLogger extends AbstractLogger
{

    public FileLogger()
    {
        file = null;
        dos = null;
    }

    public FileLogger(int level, int defaultLevel, String fileName)
    {
        super(level, defaultLevel);
        file = null;
        dos = null;
        try
        {
            dos = new DataOutputStream(new FileOutputStream(fileName));
        }
        catch(Exception exception) { }
    }

    protected synchronized void write(String message)
    {
        try
        {
            dos.write((message + "\r\n").getBytes());
            dos.flush();
        }
        catch(Exception e)
        {
            System.out.println("FileLogger error:" + e.getMessage() + ". Redirecting messages to console");
            System.out.println(message);
        }
    }

    public void instantiate(String sid)
        throws ExceptionWarning
    {
        String fileName = SystemParameter.get(sid, "log", "fileName");
        try
        {
            dos = new DataOutputStream(new FileOutputStream(fileName));
        }
        catch(Exception e)
        {
            throw new ExceptionWarning(sid, "Could not instantiate the FileLogger object!", e);
        }
    }

    File file;
    DataOutputStream dos;
}