// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:18:11
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RendererInterface.java

package com.technique.engine.web;

import com.technique.engine.util.ExceptionFatal;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.xml.XMLData;

public interface RendererInterface
{

    public abstract String transform(String s, XMLData xmldata, String s1)
        throws ExceptionWarning, ExceptionFatal;
}