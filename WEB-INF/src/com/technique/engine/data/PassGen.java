// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:15:13
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   PassGen.java

package com.technique.engine.data;

import com.technique.engine.util.CryptRSA;

public class PassGen
{

    public PassGen()
    {
    }

    public static void main(String args[])
    {
        try
        {
            System.out.println("encrypted password=" + CryptRSA.encrypt(args[0], 45613));
        }
        catch(Exception e)
        {
            System.out.println("usage: com.technique.engine.data.PassGen <password>");
        }
    }
}
