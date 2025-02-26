// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:15:11
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ConnectionPool.java

package com.technique.engine.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.CryptRSA;
import com.technique.engine.util.ExceptionFatal;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.util.TechCommonKeys;

// Referenced classes of package com.technique.engine.data:
//            ConnectionPoolData

public class ConnectionPool
    implements Serializable
{

    public static ConnectionPool getInstance()
    {
        return instance;
    }

    @SuppressWarnings("unchecked")
	public synchronized void initialize(String systemId)
        throws ExceptionWarning, ExceptionFatal
    {
        sid = systemId;
        SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "TECH-ENGINE is initializing the connection pool for " + sid + ".");
        Vector vec = SystemParameter.getPoolData(sid, new ConnectionPoolData());
        for(int i = 0; i < vec.size(); i++)
        {
            ConnectionPoolData poolData = (ConnectionPoolData)vec.elementAt(i);
            System.out.println("poolData=" + poolData.getName());
            ConnectionPoolData storedPoolData = getPoolObject(poolData.getName());
            if(storedPoolData == null)
            {
                pool.put(poolData.getName(), poolData);
                registerDriver(poolData);
            }
        }

        SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "TECH-ENGINE has initialized the connection pool for " + sid + ".");
    }

    public synchronized void reset(String sid)
        throws ExceptionWarning, ExceptionFatal
    {
        SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "TECH-ENGINE is reseting the connection pool for " + sid + ".");
        Vector vec = SystemParameter.getPoolData(sid, new ConnectionPoolData());
        for(int i = 0; i < vec.size(); i++)
        {
            ConnectionPoolData poolData = (ConnectionPoolData)vec.elementAt(i);
            ConnectionPoolData storedPoolData = getPoolObject(poolData.getName());
            if(storedPoolData != null)
                storedPoolData.closeAllConnection();
        }

        initialize(sid);
    }

    private void registerDriver(ConnectionPoolData poolData)
        throws ExceptionFatal, ExceptionWarning
    {
        try
        {
            System.out.println("registering driver " + poolData.getDriver());
            Class.forName(poolData.getDriver());
        }
        catch(Exception e)
        {
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "TECH-ENGINE could not initialize the connection pool for " + sid + ".", e);
            throw new ExceptionFatal("Could not initialize database driver " + poolData.getDriver() + "!", e);
        }
    }

    private ConnectionPoolData getPoolObject(String name)
    {
        return (ConnectionPoolData)pool.get(name);
    }

    public Connection getConnection(String poolName)
        throws ExceptionWarning
    {
        ConnectionPoolData pool = getPoolObject(poolName);
        return getConnection(pool, pool.getAutoCommit());
    }

    public Connection getConnection(String poolName, boolean autocommit)
        throws ExceptionWarning
    {
        ConnectionPoolData pool = getPoolObject(poolName);
        return getConnection(pool, autocommit);
    }

    public void close(String poolName, Connection conn)
        throws ExceptionWarning
    {
        ConnectionPoolData pool = getPoolObject(poolName);
        pool.storeConnection(conn);
    }

    public void commit(String poolName, Connection conn)
        throws ExceptionWarning
    {
        ConnectionPoolData poolData = getPoolObject(poolName);
        if(!poolData.isTransactional())
            return;
        try
        {
            conn.commit();
        }
        catch(SQLException e)
        {
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "Exception @ConnectionPool.commit(" + poolName + ",connection).", e);
            throw new ExceptionWarning("Error while commiting a transaction!", e);
        }
    }

    public void rollback(String poolName, Connection conn)
        throws ExceptionWarning
    {
        ConnectionPoolData poolData = getPoolObject(poolName);
        if(!poolData.isTransactional())
            return;
        try
        {
            conn.rollback();
        }
        catch(SQLException e)
        {
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "Exception @ConnectionPool.rollback(" + poolName + ",connection).", e);
            throw new ExceptionWarning("Error while rolling back a transaction!", e);
        }
    }

    private Connection getConnection(ConnectionPoolData poolData, boolean autoCommit)
        throws ExceptionWarning
    {
        Connection conn = poolData.retrieveConnection();
        if(conn == null)
            conn = createConnection(poolData, autoCommit);
        return conn;
    }

    private Connection createConnection(ConnectionPoolData poolData, boolean autoCommit)
        throws ExceptionWarning
    {
        try
        {
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "ConnectionPool is creating a new connection: [" + poolData.getCompleteUrl() + "]");
            String passwd = poolData.getPassword();
            if("true".equals(poolData.getEncrypted()))
            {
                passwd = CryptRSA.decrypt(passwd, 45613);
                poolData.setEncrypted("false");
                poolData.setPassword(passwd);
            }
            Connection conn = DriverManager.getConnection(poolData.getCompleteUrl(), poolData.getUser(), passwd);
            if(autoCommit || !poolData.isTransactional())
                return conn;
            try
            {
                conn.setAutoCommit(autoCommit);
            }
            catch(SQLException e1)
            {
                poolData.setTransactional(false);
            }
            return conn;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new ExceptionWarning("An error occured while attempting to create a new database connection", e);
        }
    }

    private ConnectionPool()
    {
        pool = new HashMap();
    }

    private static ConnectionPool instance = new ConnectionPool();
    private static String sid = "Technique";
    private HashMap pool;

}