// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:12:41
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SystemCommandData.java

package com.technique.engine.app;

import java.io.Serializable;

public class SystemCommandData
    implements Serializable
{

    public SystemCommandData()
    {
        javaClass = null;
        name = null;
    }

    public String getJavaClass()
    {
        return javaClass;
    }

    public String getName()
    {
        return name;
    }

    public void setJavaClass(String value)
    {
        javaClass = value;
    }

    public void setName(String value)
    {
        name = value;
    }

    private String javaClass;
    private String name;
}