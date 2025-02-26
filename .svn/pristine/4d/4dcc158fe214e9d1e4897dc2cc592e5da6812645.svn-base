// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:15:12
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ConnectionStorage.java

package com.technique.engine.data;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;

public class ConnectionStorage
    implements Serializable
{

    public ConnectionStorage(Connection conn)
    {
        connection = conn;
        creationTime = getTime();
    }

    public long getCreationTime()
    {
        return creationTime;
    }

    public Connection getConnection()
    {
        return connection;
    }

    private long getTime()
    {
        return (new Date()).getTime();
    }

    private Connection connection;
    private long creationTime;
}