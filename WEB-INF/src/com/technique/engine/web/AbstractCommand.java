// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:18:08
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AbstractCommand.java

package com.technique.engine.web;

import java.util.Date;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.data.AbstractPersistentEntity;
import com.technique.engine.data.DataTypes;
import com.technique.engine.util.ExceptionAbstract;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.util.StringUtil;
import com.technique.engine.util.TechCommonKeys;

// Referenced classes of package com.technique.engine.web:
//            CommandWrapper, UserSession

public abstract class AbstractCommand
{

    public AbstractCommand()
    {
    }

    public abstract CommandWrapper execute(UserSession usersession)
        throws ExceptionAbstract;

    protected void generateSynchronizedToken(CommandWrapper wrapper, UserSession session)
    {
        String s = ((double)(new Date()).getTime() + Math.random() * 999999D) + "";
        wrapper.getXMLData().addClosedTag("syncToken", s);
        session.setAttribute("syncTokenX", s);
    }

    protected void checkSynchronizedToken(CommandWrapper wrapper, UserSession session, String errorMessage)
        throws ExceptionWarning
    {
        String s = session.getAttributeString("syncTokenX");
        String s1 = session.getAttributeString("syncToken");
        session.setAttribute("syncTokenX", "-");
        if(!s.equals(s1))
            throw new ExceptionWarning(errorMessage);
        else
            return;
    }

    protected void populateEntity(AbstractPersistentEntity entity, UserSession session)
        throws ExceptionWarning
    {
        populateEntity(session.getSID(), entity, session);
    }

    protected void populateEntity(String sid, AbstractPersistentEntity entity, UserSession session)
        throws ExceptionWarning
    {
        try
        {
            Object stru[] = entity.getStructurePk();
            for(int i = 0; i < stru.length; i += 3)
            {
                String att = StringUtil.firstToLowerCase((String)stru[i]);
                Integer type = (Integer)stru[i + 2];
                String value = null;
                if(type == DataTypes.JAVA_STRINGBUFFER)
                    value = (String)session.getAttribute(att);
                else
                    value = session.getAttributeString(att);
                try
                {
                    entity.putValueString(stru[i].toString(), value, type);
                }
                catch(ExceptionWarning exceptionwarning) { }
            }

            Object o = entity;
            Class c = getClass();
            do
            {
                stru = ((AbstractPersistentEntity)o).getStructure();
                for(int i = 0; i < stru.length; i += 3)
                {
                    String att = StringUtil.firstToLowerCase((String)stru[i]);
                    Integer type = (Integer)stru[i + 2];
                    String value = null;
                    if(type == DataTypes.JAVA_STRINGBUFFER)
                        value = (String)session.getAttribute(att);
                    else
                        value = session.getAttributeString(att);
                    try
                    {
                        entity.putValueString(stru[i].toString(), value, type);
                    }
                    catch(ExceptionWarning exceptionwarning1) { }
                }

                c = o.getClass().getSuperclass();
                if(AbstractPersistentEntity.isSuperEntity(c))
                    break;
                o = c.newInstance();
            } while(true);
        }
        catch(Exception e)
        {
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "Exception @populateEntity", e);
            throw new ExceptionWarning("Error while populating entiy in method populateEntity [" + entity.getClass().getName() + ",userSession]", e);
        }
    }
}