// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:16:23
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   LogFactory.java

package com.technique.engine.logging;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionWarning;
import java.util.HashMap;

// Referenced classes of package com.technique.engine.logging:
//            AbstractLogger

public class LogFactory
{

    private LogFactory()
    {
    }

    public static synchronized void clearAll()
    {
        hm = new HashMap();
    }

    @SuppressWarnings("unchecked")
	public static AbstractLogger create(String sid, String logClassName)
        throws ExceptionWarning
    {
        try
        {
            String key = sid + "#" + logClassName;
            AbstractLogger obj = (AbstractLogger)hm.get(key);
            if(obj != null)
                return obj;
            synchronized(com.technique.engine.logging.LogFactory.class)
            {
                String level = SystemParameter.get(sid, "log", "level");
                String defaultLevel = SystemParameter.get(sid, "log", "defaultLevel");
                Class c = Class.forName(logClassName);
                obj = (AbstractLogger)c.newInstance();
                obj.setLevel(Integer.parseInt(level));
                obj.setDefaultLevel(Integer.parseInt(defaultLevel));
                obj.instantiate(sid);
                hm.put(key, obj);
            }
            return obj;
        }
        catch(Exception e)
        {
            throw new ExceptionWarning(sid, "Could not create a valid logger object!", e);
        }
    }

    private static HashMap hm = new HashMap();

}