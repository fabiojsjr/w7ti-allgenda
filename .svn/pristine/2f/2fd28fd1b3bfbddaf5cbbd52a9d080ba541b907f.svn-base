// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:18:30
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   XMLReader.java

package com.technique.engine.xml;

import com.sun.xml.tree.TreeWalker;
import com.sun.xml.tree.XmlDocument;
import com.technique.engine.util.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLReader
{

    @SuppressWarnings("unchecked")
	public static HashMap getAllNodes(String sid, String mainTag, String fileName)
        throws ExceptionWarning
    {
        HashMap ret = new HashMap();
        XmlDocument xmlDocument = getXMLDocumentFromFile(fileName);
        TreeWalker walker = new TreeWalker(xmlDocument.getDocumentElement());
        boolean found = false;
        Node mainNode = null;
        do
        {
            mainNode = walker.getNext();
            if(mainNode == null)
                break;
            if(!mainNode.getNodeName().equals(mainTag))
                continue;
            found = true;
            break;
        } while(true);
        if(!found)
            throw new ExceptionWarning("Could not find tag group " + mainTag + " in the system parameter file " + fileName + "!");
        NodeList nodes = mainNode.getChildNodes();
        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node groupNode = nodes.item(i);
            NodeList nodesAttr = groupNode.getChildNodes();
            for(int j = 0; j < nodesAttr.getLength(); j++)
            {
                Node attrNode = nodesAttr.item(j);
                if(attrNode.getNodeType() == 1 && attrNode.getFirstChild() != null)
                    ret.put(groupNode.getNodeName() + "#" + attrNode.getNodeName(), attrNode.getFirstChild().getNodeValue() + "");
            }

        }

        return ret;
    }

    @SuppressWarnings("unchecked")
	public static Vector getNodesCollection(String mainTag, String fileName, Object valueObject)
        throws ExceptionWarning
    {
        Vector ret = new Vector();
        XmlDocument xmlDocument = getXMLDocumentFromFile(fileName);
        TreeWalker walker = new TreeWalker(xmlDocument.getDocumentElement());
        boolean found = false;
        Node mainNode = null;
        do
        {
            mainNode = walker.getNext();
            if(mainNode == null)
                break;
            if(!mainNode.getNodeName().equals(mainTag))
                continue;
            found = true;
            break;
        } while(true);
        if(!found)
            throw new ExceptionWarning("Could not find tag group " + mainTag + " in the system parameter file " + fileName + "!");
        Class c = valueObject.getClass();
        NodeList nodes = mainNode.getChildNodes();
        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node groupNode = nodes.item(i);
            if(groupNode.getNodeType() == 1)
            {
                NamedNodeMap nodeMap = groupNode.getAttributes();
                Object o = null;
                try
                {
                    o = c.newInstance();
                }
                catch(Exception e)
                {
                    throw new ExceptionWarning("Could not create an instance of " + c.getName() + "!", e);
                }
                for(int j = 0; j < nodeMap.getLength(); j++)
                {
                    Node nodeAttr = nodeMap.item(j);
                    String name = nodeAttr.getNodeName();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
                    String value = nodeAttr.getNodeValue();
                    try
                    {
                        Method m = c.getDeclaredMethod("set" + name, new Class[] {
                            java.lang.String.class
                        });
                        m.invoke(o, new Object[] {
                            value
                        });
                    }
                    catch(Exception exception) { }
                }

                ret.add(o);
            }
        }

        return ret;
    }

    public static String getNodeValueFromFile(File file, String group, String attribute)
        throws ExceptionWarning, ExceptionInfo
    {
        try
        {
            FileInputStream fis = new FileInputStream(file);
            return getNodeValueFromStream(fis, group, attribute);
        }
        catch(FileNotFoundException e)
        {
            throw new ExceptionWarning("File not found!", e);
        }
    }

    public static String getNodeValueFromFile(String fileName, String group, String attribute)
        throws ExceptionWarning, ExceptionInfo
    {
        File f = FileUtil.findFile(fileName);
        return getNodeValueFromFile(f, group, attribute);
    }

    public static String getNodeValueFromStream(FileInputStream stream, String group, String attribute)
        throws ExceptionWarning, ExceptionInfo
    {
        try
        {
            XmlDocument xmldoc = XmlDocument.createXmlDocument(stream, false);
            return getNodeValueFromXMLDocument(xmldoc, group, attribute);
        }
        catch(IOException e)
        {
            throw new ExceptionWarning("IO Exception", e);
        }
        catch(SAXException e2)
        {
            throw new ExceptionWarning("XML parsing Exception", e2);
        }
    }

    public static String getNodeValueFromXMLDocument(XmlDocument xmlDocument, String group, String attribute)
        throws ExceptionWarning, ExceptionInfo
    {
        String ret = null;
        TreeWalker walker = new TreeWalker(xmlDocument.getDocumentElement());
        Node parentNode = walker.getNextElement(group);
        NodeList nodeList = parentNode.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++)
        {
            Node childNode = nodeList.item(i);
            if(childNode.getNodeType() != 1 || !attribute.equals(childNode.getNodeName()))
                continue;
            ret = childNode.getFirstChild() != null ? childNode.getFirstChild().getNodeValue() : "";
            break;
        }

        return ret;
    }

    public static XmlDocument getXMLDocumentFromFile(File file)
        throws ExceptionWarning
    {
        try
        {
            FileInputStream fis = new FileInputStream(file);
            return getXMLDocumentFromStream(fis);
        }
        catch(FileNotFoundException e)
        {
            throw new ExceptionWarning("File not found!", e);
        }
    }

    public static XmlDocument getXMLDocumentFromFile(String fileName)
        throws ExceptionWarning
    {
        File f = FileUtil.findFile(fileName);
        return getXMLDocumentFromFile(f);
    }

    public static XmlDocument getXMLDocumentFromStream(InputStream stream)
        throws ExceptionWarning
    {
        try
        {
            return XmlDocument.createXmlDocument(stream, false);
        }
        catch(IOException e)
        {
            throw new ExceptionWarning("IO Exception", e);
        }
        catch(SAXException e2)
        {
            e2.printStackTrace();
            throw new ExceptionWarning("XML parsing Exception", e2);
        }
    }

    public static XmlDocument getXMLDocumentFromString(String xmlString)
        throws ExceptionWarning
    {
        try
        {
            return XmlDocument.createXmlDocument(xmlString, false);
        }
        catch(IOException e)
        {
            throw new ExceptionWarning("IO Exception", e);
        }
        catch(SAXException e2)
        {
            throw new ExceptionWarning("XML parsing Exception", e2);
        }
    }

    public static XmlDocument getXMLDocumentFromString(StringBuffer xmlString)
        throws ExceptionWarning
    {
        try
        {
            return XmlDocument.createXmlDocument(xmlString.toString(), false);
        }
        catch(IOException e)
        {
            throw new ExceptionWarning("IO Exception", e);
        }
        catch(SAXException e2)
        {
            throw new ExceptionWarning("XML parsing Exception", e2);
        }
    }

    private XMLReader()
    {
    }
}