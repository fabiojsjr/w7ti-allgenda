// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:18:30
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   XMLData.java

package com.technique.engine.xml;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.util.StringUtil;
import java.io.Serializable;
import java.util.ArrayList;

public class XMLData
    implements Serializable
{

    public XMLData(String sid)
        throws ExceptionWarning
    {
        spaces = "";
        tagOpen = false;
        tagStack = new ArrayList();
        this.sid = sid;
        content = new StringBuffer();
        appendSysHeader();
    }

    public XMLData(String sid, boolean empty)
        throws ExceptionWarning
    {
        spaces = "";
        tagOpen = false;
        tagStack = new ArrayList();
        this.sid = sid;
        content = new StringBuffer();
        if(!empty)
            appendSysHeader();
    }

    public void addClosedTag(String tagName, String value)
    {
        closeLocalTag();
        content.append(spaces + OPEN_TAG + tagName + CLOSE_TAG + getValue(value) + OPEN_CLOSE_TAG + tagName + CLOSE_TAG + NL);
    }

    public void addClosedTag(String tagName, String value, boolean cData)
    {
        if(!cData)
        {
            addClosedTag(tagName, value);
        } else
        {
            closeLocalTag();
            content.append(spaces + OPEN_TAG + tagName + CLOSE_TAG + getCDataValue(value) + OPEN_CLOSE_TAG + tagName + CLOSE_TAG + NL);
        }
    }

    public void addParameter(String parName, String value)
    {
        content.append(spaces + parName + EQUALS + "\"" + getValue2(value) + "\"" + NL);
    }

    public void addParameter(String parName, String value, boolean cData)
    {
        if(!cData)
            addParameter(parName, value);
        else
            content.append(spaces + parName + EQUALS + "\"" + getCDataValue(value) + "\"" + NL);
    }

    public void addParameterTag(String tagName)
    {
        closeLocalTag();
        content.append(spaces + OPEN_TAG + tagName + NL);
        tagOpen = true;
        spaces += SPACES;
    }

    private void addProlog()
        throws ExceptionWarning
    {
        content.append(OPEN_TAG + "?xml version='" + SystemParameter.get(sid, "xml", "version") + "' encoding='" + SystemParameter.get(sid, "xml", "encoding") + "'?" + CLOSE_TAG + NL);
    }

    public void appendRaw(String xmlContent)
    {
        closeLocalTag();
        content.append(xmlContent);
    }

    private void appendSysHeader()
        throws ExceptionWarning
    {
        addProlog();
        openGroup("document");
        addParameterTag("systemProperties");
        addParameter("sid", SystemParameter.get(sid, "systemProperties", "sid"));
        addParameter("name", SystemParameter.get(sid, "systemProperties", "name"));
        addParameter("entryPoint", SystemParameter.get(sid, "systemProperties", "entryPoint"));
        addParameter("imageURL", SystemParameter.get(sid, "systemProperties", "imageURL"));
        addParameter("scriptURL", SystemParameter.get(sid, "systemProperties", "scriptURL"));
        addParameter("commandName", SystemParameter.get(sid, "systemProperties", "commandName"));
        addParameter("idLoja", SystemParameter.get(sid, "systemProperties", "idLoja"));
    }

    public void closeGroup()
    {
        closeLocalTag();
        if(tagStack.size() == 0)
        {
            return;
        } else
        {
            reduceSpaces();
            content.append(spaces + OPEN_CLOSE_TAG + tagStack.get(tagStack.size() - 1) + CLOSE_TAG + NL);
            tagStack.remove(tagStack.size() - 1);
            return;
        }
    }

    public void closeLocalTag()
    {
        if(!tagOpen)
        {
            return;
        } else
        {
            reduceSpaces();
            content.append(spaces + CLOSE_OPEN_TAG + NL);
            tagOpen = false;
            return;
        }
    }

    private String getCDataValue(String str)
    {
        if(str == null)
            str = "";
        return "<![CDATA[" + str + "]]>";
    }

    @SuppressWarnings("unchecked")
	public XMLData getClone()
        throws ExceptionWarning
    {
        XMLData obj = new XMLData(getSID());
        obj.content = new StringBuffer(content.toString());
        obj.tagOpen = tagOpen;
        obj.tagStack.clear();
        for(int i = 0; i < tagStack.size(); i++)
            obj.tagStack.add((String)tagStack.get(i) + "");

        return obj;
    }

    public String getSID()
    {
        return sid;
    }

    private String getValue(String str)
    {
        if(str == null)
            str = "";
        return str;
    }

    private String getValue2(String str)
    {
        if(str == null)
            str = "";
        return StringUtil.toXMLString(str);
    }

    public String getXML()
    {
        closeLocalTag();
        for(; tagStack.size() > 0; closeGroup());
        return content.toString();
    }

    @SuppressWarnings("unchecked")
	public void openGroup(String groupName)
    {
        closeLocalTag();
        content.append(spaces + OPEN_TAG + groupName + CLOSE_TAG + NL);
        spaces += SPACES;
        tagStack.add(groupName);
    }

    private void reduceSpaces()
    {
        spaces = spaces.substring(0, spaces.length() - SPACES_LENGHT);
    }

    private static String CLOSE_OPEN_TAG = "/>";
    private static String CLOSE_TAG = ">";
    private static String EQUALS = "=";
    private static String NL = "\n";
    private static String OPEN_CLOSE_TAG = "</";
    private static String OPEN_TAG = "<";
    private static String SPACES;
    private static int SPACES_LENGHT;
    private StringBuffer content;
    private String sid;
    private String spaces;
    private boolean tagOpen;
    private ArrayList tagStack;

    static 
    {
        SPACES = "\t";
        SPACES_LENGHT = SPACES.length();
    }
}