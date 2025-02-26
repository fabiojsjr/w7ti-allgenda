// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:18:12
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RendererXSLtoHTML.java

package com.technique.engine.web;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.*;
import com.technique.engine.xml.XMLData;
import java.io.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

// Referenced classes of package com.technique.engine.web:
//            RendererInterface

public class RendererXSLtoHTML
    implements RendererInterface
{

    private RendererXSLtoHTML()
    {
    }

    public static RendererXSLtoHTML getInstance()
    {
        return instance;
    }

    public String transform(String sid, XMLData xmlData, String pageFormat)
        throws ExceptionWarning, ExceptionFatal
    {
        try
        {
            String xmlContent = xmlData.getXML();
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "Transforming XSLtoHTML - page: " + pageFormat + " - content....\n" + lineSeparator + xmlContent + lineSeparator);
            StringReader srReaderXML = new StringReader(xmlContent);
            xmlContent = null;
            StreamSource streamInXML = new StreamSource();
            streamInXML.setReader(srReaderXML);
            StreamSource streamXSL = new StreamSource();
            String page = getPage(sid, pageFormat);
            ByteArrayInputStream xslPlusTemplates = new ByteArrayInputStream(page.getBytes());
            streamXSL.setInputStream(xslPlusTemplates);
            Transformer transformerXSL = TransformerFactory.newInstance().newTransformer(streamXSL);
            StreamResult streamOutHTML = new StreamResult();
            ByteArrayOutputStream byteHTML = new ByteArrayOutputStream();
            streamOutHTML.setOutputStream(byteHTML);
            transformerXSL.transform(streamInXML, streamOutHTML);
            byteHTML.close();
            srReaderXML.close();
            xslPlusTemplates.close();
            return streamOutHTML.getOutputStream().toString();
        }
        catch(ExceptionWarning e)
        {
            throw e;
        }
        catch(ExceptionFatal ef)
        {
            throw ef;
        }
        catch(Exception e2)
        {
            throw new ExceptionFatal("Error while transforming XSL into HTML", e2);
        }
    }

    public String getPage(String sid, String xslFile)
        throws ExceptionFatal, ExceptionWarning
    {
        String rootPath = SystemParameter.get(sid, "systemProperties", "fileRoot");
        if(rootPath == null)
            rootPath = SystemParameter.get(sid, "systemProperties", "rootPath");
        String xslPath = rootPath + SystemParameter.get(sid, "xsl", "filePath");
        String xslTemplPath = rootPath + SystemParameter.get(sid, "xsl", "templatePath");
        String includeTag = SystemParameter.get(sid, "xsl", "includeTemplateTagName") + ">";
        xslFile = xslPath + xslFile;
        String xslContent = FileUtil.getFileContent(xslFile).toString();
        String tag1 = "<" + includeTag;
        int tag1Size = tag1.length();
        String tag2 = "</" + includeTag;
        int tag2Size = tag2.length();
        do
        {
            int i1 = xslContent.indexOf(tag1);
            if(i1 < 0)
                break;
            int i2 = xslContent.indexOf(tag2, i1);
            if(i2 < 0)
                break;
            String templName = xslContent.substring(i1 + tag1Size, i2);
            xslContent = xslContent.substring(0, i1) + FileUtil.getFileContent(xslTemplPath + templName).toString() + xslContent.substring(i2 + tag2Size, xslContent.length());
        } while(true);
        return xslContent;
    }

    private static RendererXSLtoHTML instance = new RendererXSLtoHTML();
    private static String lineSeparator = "\n\t***************************************\n";

}