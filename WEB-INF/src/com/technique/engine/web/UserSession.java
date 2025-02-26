// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:18:13
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   UserSession.java

package com.technique.engine.web;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.Cookie;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionWarning;

// Referenced classes of package com.technique.engine.web:
//            UploadedFile

public class UserSession
    implements Serializable
{

    @SuppressWarnings("unchecked")
	public void addCookie(Cookie cookie)
    {
        if(cookies == null)
            cookies = new Hashtable();
        cookies.put(cookie.getName(), cookie);
    }

    public Hashtable getCookies()
    {
        return cookies;
    }

    public Cookie getCookie(String name)
    {
        if(cookies == null)
            return null;
        else
            return (Cookie)cookies.get(name);
    }

    private String getCommandTag()
        throws ExceptionWarning
    {
        if(commandTag == null)
            commandTag = SystemParameter.get(getSID(), "systemProperties", "commandName");
        return commandTag;
    }

    public String getSID()
    {
        return sid;
    }

    public String getSessionID()
    {
        return id;
    }

    public String getCommandName()
        throws ExceptionWarning
    {
        return (String)getAttribute(getCommandTag());
    }

    public UserSession(String sessionId, String sid)
    {
        data = null;
        datatmp = null;
        cookies = null;
        files = null;
        id = null;
        this.sid = null;
        commandTag = null;
        id = sessionId;
        this.sid = sid;
        data = new Hashtable();
    }

    public Hashtable getSession()
    {
        if(data == null)
            data = new Hashtable();
        return data;
    }

    public void setSession(Hashtable data)
    {
        this.data = data;
    }

    public Hashtable getSessionTmp()
    {
        if(datatmp == null)
            datatmp = new Hashtable();
        return datatmp;
    }

    public void setSessionTmp(Hashtable data)
    {
        datatmp = data;
    }

    @SuppressWarnings("unchecked")
	public void setAttribute(String key, Object object)
    {
        if(object == null)
            getSession().remove(key);
        else
            getSession().put(key, object);
    }

    public void removeAttribute(String key)
    {
        getSession().remove(key);
    }

    @SuppressWarnings("unchecked")
	public void setFormAttribute(String key, String value, boolean isTransient)
    {
        getSessionTmp().put(key, value);
    }

    @SuppressWarnings("unchecked")
	public void setFormAttribute(String key, String values[], boolean isTransient)
    {
        if(values.length == 1)
        {
            getSessionTmp().put(key, values[0]);
        } else
        {
            for(int i = 0; i < values.length; i++)
                getSessionTmp().put(key + i, values[i]);

        }
    }

    @SuppressWarnings("unchecked")
	public void setAttribute(String key, Object object, boolean isTransient)
    {
        if(object == null)
            removeAttribute(key);
        else
        if(!isTransient)
            setAttribute(key, object);
        else
            getSessionTmp().put(key, object);
    }

    public String getAttributeDate(String key, SimpleDateFormat sdf)
    {
        try
        {
            Object o = getSession().get(key);
            if(o != null)
            {
                sdf.parse(o.toString());
                return o.toString();
            }
            o = getSessionTmp().get(key);
            if(o == null)
            {
                return null;
            } else
            {
                sdf.parse(o.toString());
                return o.toString();
            }
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public String getAttributeString(String key)
    {
        Object o = getSession().get(key);
        if(o != null)
        {
            return o.toString();
        } else
        {
            o = getSessionTmp().get(key);
            return o != null ? o.toString() : null;
        }
    }

    public String getAttributeString(String key, int position)
    {
        Object o = getSession().get(key + position);
        if(o != null)
            return o.toString();
        o = getSessionTmp().get(key + position);
        if(o != null)
            return o.toString();
        if(position > 0)
            return null;
        o = getSession().get(key);
        if(o != null)
            return o.toString();
        o = getSessionTmp().get(key);
        if(o != null)
            return o.toString();
        else
            return null;
    }

    @SuppressWarnings("unchecked")
	public ArrayList getAttributeArray(String key)
    {
        int i = 0;
        ArrayList ret = new ArrayList();
        do
        {
            String tmp = getAttributeString(key, i);
            if(tmp == null)
            {
                if(i != 0)
                    break;
                i++;
                tmp = getAttributeString(key, i);
                if(tmp == null)
                    break;
            }
            ret.add(tmp);
            i++;
        } while(true);
        return ret;
    }

    public Object getAttribute(String key)
    {
        Object o = getSession().get(key);
        if(o != null)
            return o;
        else
            return getSessionTmp().get(key);
    }

    private void clearFiles()
    {
        if(files == null)
            return;
        for(Enumeration enu = files.elements(); enu.hasMoreElements();)
        {
            UploadedFile f = (UploadedFile)enu.nextElement();
            try
            {
                f.delete();
            }
            catch(Exception e)
            {
                System.out.println("TechEngine Warning: Could not delete file " + f.getName());
            }
        }

        files.clear();
        files = null;
    }

    public void clearTmp()
    {
        datatmp = null;
        cookies = null;
        clearFiles();
    }

    public void clearAll()
    {
        datatmp = null;
        data = null;
        cookies = null;
    }

    @SuppressWarnings("unchecked")
	public void setFile(UploadedFile uploadedFile)
    {
        if(files == null)
            files = new Hashtable();
        files.put(uploadedFile.getName(), uploadedFile);
    }

    public Hashtable getFiles()
    {
        return files;
    }

    public UploadedFile getFile(String name)
    {
        if(files == null)
            return null;
        else
            return (UploadedFile)files.get(name);
    }

    public String toString()
    {
        return "session " + id + " has " + getSession().size() + " objects and " + getSessionTmp().size() + " temporary objects";
    }

    private Hashtable data;
    private Hashtable datatmp;
    private Hashtable cookies;
    private Hashtable files;
    private String id;
    private String sid;
    private String commandTag;
}