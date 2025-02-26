// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:12:41
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SystemParameter.java

package com.technique.engine.app;

import com.technique.engine.logging.AbstractLogger;
import com.technique.engine.logging.LogFactory;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.util.TechCommonKeys;
import com.technique.engine.web.AbstractCommand;
import com.technique.engine.xml.XMLReader;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;

// Referenced classes of package com.technique.engine.app:
//            SystemCommandData

public class SystemParameter
    implements Serializable
{

    public static synchronized void clearAll()
    {
        logEnabled = false;
        parameters = new HashMap();
        LogFactory.clearAll();
    }

    public static String get(String sid, String tag, String attribute)
        throws ExceptionWarning
    {
        return (String)getParameters(sid).get(tag + "#" + attribute);
    }

    @SuppressWarnings("unchecked")
	public static AbstractCommand getCommand(String sid, String commandName)
        throws ExceptionWarning
    {
        try
        {
            String cmdClass = (String)getParameters(sid).get("commandMapping#name#" + commandName);
            Class c = Class.forName(cmdClass);
            Method m = c.getMethod("getInstance", new Class[0]);
            return (AbstractCommand)m.invoke(null, null);
        }
        catch(Exception e)
        {
            throw new ExceptionWarning("Error while trying to getCommad(" + sid + "," + commandName + ")", e);
        }
    }

    private static AbstractLogger getLogObject(String sid)
        throws ExceptionWarning
    {
        String className = get(sid, "log", "javaClass");
        if(className == null)
            return null;
        else
            return LogFactory.create(sid, className);
    }

    private static HashMap getParameters(String sid)
        throws ExceptionWarning
    {
        HashMap hm = (HashMap)parameters.get(sid);
        if(hm == null)
            hm = initialize(sid);
        return hm;
    }

    public static synchronized Vector getPoolData(String sid, Object connectionPoolData)
        throws ExceptionWarning
    {
        return XMLReader.getNodesCollection("connectionPools", getSystemFileName(sid), connectionPoolData);
    }

    @SuppressWarnings("unchecked")
	private static void getSystemCommands(String sid, HashMap hm)
        throws ExceptionWarning
    {
        log(sid, TechCommonKeys.LOG_DEBUG, "retrieving system commands");
        Vector vec = XMLReader.getNodesCollection("commandMapping", getSystemFileName(sid), new SystemCommandData());
        if(vec == null)
        {
            log(sid, TechCommonKeys.LOG_DEBUG, "no system commands found!");
            return;
        }
        for(int i = 0; i < vec.size(); i++)
        {
            SystemCommandData obj = (SystemCommandData)vec.elementAt(i);
            hm.put("commandMapping#name#" + obj.getName(), obj.getJavaClass());
        }

        log(sid, TechCommonKeys.LOG_DEBUG, "system commands found =" + vec.size());
        vec = null;
    }

    private static String getSystemFileName(String sid)
    {
        return "config" + sid + ".xml";
    }

    @SuppressWarnings("unchecked")
	private static synchronized HashMap initialize(String sid)
        throws ExceptionWarning
    {
        HashMap hm = XMLReader.getAllNodes(sid, "systemParameters", getSystemFileName(sid));
        parameters.put(sid, hm);
        log(sid, TechCommonKeys.LOG_DEBUG, "TECH-ENGINE is initializing....");
        getSystemCommands(sid, hm);
        log(sid, TechCommonKeys.LOG_DEBUG, "TECH-ENGINE is ready.");
        String isLogEnabled = get(sid, "systemProperties", "logEnabled");
        if("true".equals(isLogEnabled))
            logEnabled = true;
        return hm;
    }

    public static void log(String sid, int level, String message)
        throws ExceptionWarning
    {
        if(!logEnabled)
            return;
        AbstractLogger obj = getLogObject(sid);
        if(obj == null)
        {
            return;
        } else
        {
            obj.log(level, sid + "  ****\t" + message);
            return;
        }
    }

    public static void log(String sid, int level, String message, Throwable e)
        throws ExceptionWarning
    {
        if(!logEnabled)
            return;
        AbstractLogger obj = getLogObject(sid);
        if(obj == null)
        {
            return;
        } else
        {
            obj.log(level, sid + "  ****\t" + message, e);
            return;
        }
    }

    public static void log(String sid, String message, Throwable e)
        throws ExceptionWarning
    {
        if(!logEnabled)
            return;
        AbstractLogger obj = getLogObject(sid);
        if(obj == null)
        {
            return;
        } else
        {
            obj.log(sid + "  ****\t" + message, e);
            return;
        }
    }

    private SystemParameter()
    {
    }

    private static boolean logEnabled = false;
    private static HashMap parameters = new HashMap();

}