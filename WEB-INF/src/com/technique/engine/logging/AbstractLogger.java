// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:16:21
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AbstractLogger.java

package com.technique.engine.logging;

import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.util.TechCommonKeys;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

// Referenced classes of package com.technique.engine.logging:
//            LoggerInterface

public abstract class AbstractLogger
    implements LoggerInterface
{

    public AbstractLogger()
    {
        _defaultLevel = 1;
        _level = 1;
    }

    public void setDefaultLevel(int level)
    {
        _defaultLevel = level;
    }

    public void setLevel(int level)
    {
        _level = level;
    }

    public AbstractLogger(int level, int defaultLevel)
    {
        _defaultLevel = 1;
        _level = 1;
        _defaultLevel = defaultLevel;
        _level = level;
    }

    protected String getStackTrace(Throwable error)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(bos);
        error.printStackTrace(pw);
        return bos.toString();
    }

    protected boolean shouldLog()
    {
        return _defaultLevel >= _level;
    }

    protected boolean shouldLog(int priority)
    {
        return priority >= _level;
    }

    public int getDefaultLevel()
    {
        return _defaultLevel;
    }

    protected abstract void write(String s);

    public void log(String message)
    {
        log(getDefaultLevel(), message);
    }

    public void log(int level, String message)
    {
        if(shouldLog(level))
            write(LEVELS[level - 1] + message);
    }

    public void log(String message, Throwable e)
    {
        log(getDefaultLevel(), message, e);
    }

    public void log(int level, String message, Throwable e)
    {
        if(shouldLog(level))
            write(LEVELS[level - 1] + message + "\n" + getStackTrace(e));
    }

    public abstract void instantiate(String s)
        throws ExceptionWarning;

    private static final String LEVELS[] = {
        "DEBUG:", "*INFO:", "** WARNING:", "*** ERROR:", "************* CRASH:", ":"
    };
    public static int DEBUG;
    public static int INFO;
    public static int WARNING;
    public static int ERROR;
    public static int CRASH;
    public static int USER;
    private int _defaultLevel;
    private int _level;

    static 
    {
        DEBUG = TechCommonKeys.LOG_DEBUG;
        INFO = TechCommonKeys.LOG_INFO;
        WARNING = TechCommonKeys.LOG_WARNING;
        ERROR = TechCommonKeys.LOG_ERROR;
        CRASH = TechCommonKeys.LOG_CRASH;
        USER = TechCommonKeys.LOG_USER;
    }
}