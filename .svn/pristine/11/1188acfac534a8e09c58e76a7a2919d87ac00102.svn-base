// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:15:11
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ConnectionPoolData.java

package com.technique.engine.data;

import com.technique.engine.util.ExceptionWarning;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

// Referenced classes of package com.technique.engine.data:
//            ConnectionStorage

public class ConnectionPoolData
    implements Serializable
{

    public ConnectionPoolData()
    {
        connections = new ArrayList();
        name = null;
        driver = null;
        host = null;
        instance = null;
        url = null;
        user = null;
        password = null;
        encripted = null;
        dbType = null;
        maxConnection = 0;
        timeOut = 0L;
        autoCommit = true;
        transactional = true;
        enabled = true;
    }

    private void closeConnection(Connection conn)
        throws ExceptionWarning
    {
        try
        {
            if(conn != null)
                conn.close();
        }
        catch(SQLException e)
        {
            throw new ExceptionWarning("SQLException has been raised when closing a connection", e);
        }
    }

    public synchronized void storeConnection(Connection conn)
        throws ExceptionWarning
    {
        if(!enabled || getPoolSize() >= maxConnection)
        {
            closeConnection(conn);
        } else
        {
            ConnectionStorage storage = new ConnectionStorage(conn);
            connections.add(storage);
        }
    }

    public int getPoolSize()
    {
        return connections.size();
    }

    public synchronized Connection retrieveConnection()
        throws ExceptionWarning
    {
        if(getPoolSize() == 0)
            return null;
        ConnectionStorage o = (ConnectionStorage)connections.get(0);
        connections.remove(0);
        if(timeOut > 0L && o.getCreationTime() > getTime() + timeOut)
        {
            closeConnection(o.getConnection());
            return null;
        } else
        {
            return o.getConnection();
        }
    }

    protected synchronized void closeAllConnection()
        throws ExceptionWarning
    {
        enabled = false;
        for(Connection conn = retrieveConnection(); conn != null || getPoolSize() > 0; conn = retrieveConnection())
            closeConnection(conn);

    }

    public boolean isTransactional()
    {
        return transactional;
    }

    public void setTransactional(boolean b)
    {
        transactional = b;
    }

    public String getCompleteUrl()
    {
        if(getInstance() != null)
            return getUrl() + getHost() + "/" + getInstance();
        else
            return getUrl() + getHost();
    }

    public void setName(String value)
    {
        name = value;
    }

    public String getName()
    {
        return name;
    }

    public void setDriver(String value)
    {
        driver = value;
    }

    public String getDriver()
    {
        return driver;
    }

    public void setHost(String value)
    {
        host = value;
    }

    public String getHost()
    {
        return host;
    }

    public void setInstance(String value)
    {
        if(value == null || value.equals(""))
            value = null;
        instance = value;
    }

    public String getInstance()
    {
        return instance;
    }

    public void setUrl(String value)
    {
        url = value;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUser(String value)
    {
        user = value;
    }

    public String getUser()
    {
        return user;
    }

    public void setPassword(String value)
    {
        password = value;
    }

    public String getPassword()
    {
        return password;
    }

    public void setEncrypted(String value)
    {
        encripted = value;
    }

    public String getEncrypted()
    {
        return encripted;
    }

    public void setDbType(String value)
    {
        dbType = value;
    }

    public String getDbType()
    {
        return dbType;
    }

    public void setMaxConnection(String value)
    {
        maxConnection = Integer.parseInt(value);
    }

    public void setMaxConnection(int value)
    {
        maxConnection = value;
    }

    public int getMaxConnection()
    {
        return maxConnection;
    }

    public void setTimeOut(String value)
    {
        timeOut = Long.parseLong(value);
    }

    public void setTimeOut(long value)
    {
        timeOut = value;
    }

    public void setAutoCommit(String value)
    {
        autoCommit = !value.toLowerCase().equals("false");
    }

    public boolean getAutoCommit()
    {
        return autoCommit;
    }

    public long getTimeOut()
    {
        return timeOut;
    }

    private long getTime()
    {
        return (new Date()).getTime();
    }

    private ArrayList connections;
    private String name;
    private String driver;
    private String host;
    private String instance;
    private String url;
    private String user;
    private String password;
    private String encripted;
    private String dbType;
    private int maxConnection;
    private long timeOut;
    private boolean autoCommit;
    private boolean transactional;
    private boolean enabled;
}