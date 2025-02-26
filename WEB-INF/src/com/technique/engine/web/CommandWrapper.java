// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:18:09
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CommandWrapper.java

package com.technique.engine.web;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionAbstract;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.xml.XMLData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class CommandWrapper
    implements Serializable
{

    public CommandWrapper(String sid)
        throws ExceptionWarning
    {
        _errorStack = null;
        attachmentContent = null;
        attachmentFileName = null;
        contentType = "text/html";
        errorStack = null;
        lastPage = "";
        nextPage = "";
        this.sid = null;
        xmlData = null;
        this.sid = sid;
        xmlData = new XMLData(sid);
        xmlData.addParameterTag("pageProperties");
        xmlData.addParameter("href", SystemParameter.get(sid, "systemProperties", "entryPoint") + "?sid=" + sid + "&#38;" + SystemParameter.get(sid, "systemProperties", "commandName") + "=");
    }

    @SuppressWarnings("unchecked")
	public void addError(ExceptionAbstract e)
    {
        if(errorStack == null)
        {
            errorStack = new Vector();
            _errorStack = new ArrayList();
        }
        errorStack.addElement(e.getMessage());
        _errorStack.add(e);
        
    }

    public byte[] getAttachmentContent()
    {
        return attachmentContent;
    }

    public String getAttachmentFileName()
    {
        return attachmentFileName;
    }

    public String getContentType()
    {
        return contentType;
    }

    public ArrayList getErrors()
    {
        return _errorStack;
    }

    /**
     * @deprecated Method getErrorStack is deprecated
     */

    public Vector getErrorStack()
    {
        return errorStack;
    }

    public String getNextPage()
    {
        return nextPage;
    }

    public String getSID()
    {
        return sid;
    }

    public XMLData getXMLData()
    {
        return xmlData;
    }

    public boolean hasAttachment()
    {
        return attachmentFileName != null;
    }

    public boolean hasErrors()
    {
        return errorStack != null;
    }

    public void setAttachment(String contentType, String fileName, byte content[])
    {
        this.contentType = contentType;
        attachmentFileName = fileName;
        attachmentContent = content;
    }

    public void setContentType(String content)
    {
        contentType = content;
    }

    public void setLastPage()
    {
        nextPage = lastPage;
    }

    public void setLastPage(String optionalPage)
    {
        if("".equals(lastPage))
            lastPage = optionalPage;
        nextPage = lastPage;
    }

    public void setNextPage(String value)
    {
        lastPage = nextPage;
        nextPage = value;
    }

    private ArrayList _errorStack;
    private byte attachmentContent[];
    private String attachmentFileName;
    private String contentType;
    private Vector errorStack;
    private String lastPage;
    private String nextPage;
    private String sid;
    private XMLData xmlData;
}