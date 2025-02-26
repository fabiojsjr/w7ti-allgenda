// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:18:12
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   UploadedFile.java

package com.technique.engine.web;

import com.technique.engine.util.ExceptionWarning;
import java.io.File;

public class UploadedFile
{

    public UploadedFile(String attributeName, String fileName, File file, String contentType)
    {
        this.file = file;
        this.fileName = fileName;
        name = attributeName;
        this.contentType = contentType;
    }

    public File getFile()
    {
        return file;
    }

    public String getContentType()
    {
        return contentType;
    }

    public String getName()
    {
        if(name == null)
            return getFileName();
        else
            return name;
    }

    public String getFileName()
    {
        if(fileName == null)
            return "file1";
        else
            return fileName;
    }

    public boolean delete()
        throws ExceptionWarning
    {
        return file.delete();
    }

    private File file;
    private String fileName;
    private String name;
    private String contentType;
}