// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:27
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ByteArrayDataSource.java

package com.technique.engine.util;

import java.io.*;
import javax.activation.DataSource;

public class ByteArrayDataSource
    implements DataSource
{

    public ByteArrayDataSource(InputStream is, String type)
    {
        this.type = type;
        try
        {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int ch;
            while((ch = is.read()) != -1) 
                os.write(ch);
            data = os.toByteArray();
        }
        catch(IOException ioexception) { }
    }

    public ByteArrayDataSource(byte data[], String type)
    {
        this.data = data;
        this.type = type;
    }

    public ByteArrayDataSource(String data, String type)
    {
        try
        {
            this.data = data.getBytes("iso-8859-1");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        this.type = type;
    }

    public InputStream getInputStream()
        throws IOException
    {
        if(data == null)
            throw new IOException("no data");
        else
            return new ByteArrayInputStream(data);
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        throw new IOException("cannot do this");
    }

    public String getContentType()
    {
        return type;
    }

    public String getName()
    {
        return "ByteArrayDataSource";
    }

    private byte data[];
    private String type;
}