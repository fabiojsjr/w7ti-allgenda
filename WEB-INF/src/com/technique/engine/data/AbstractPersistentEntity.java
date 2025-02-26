// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:15:10
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   AbstractPersistentEntity.java

package com.technique.engine.data;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.util.StringUtil;
import com.technique.engine.util.TechCommonKeys;
import com.technique.engine.xml.XMLData;

// Referenced classes of package com.technique.engine.data:
//            ConnectionPool, DataTypes

public abstract class AbstractPersistentEntity
    implements Serializable
{

    public static boolean isSuperEntity(Class cls)
    {
        return Modifier.isAbstract(cls.getModifiers());
    }

    public AbstractPersistentEntity()
    {
    }

    protected void close(Connection connection)
        throws ExceptionWarning
    {
        ConnectionPool.getInstance().close(getPoolName(), connection);
    }

    protected void close(Connection connection, PreparedStatement ps)
        throws ExceptionWarning
    {
        close(ps);
        close(connection);
    }

    protected void close(Connection connection, PreparedStatement ps, ResultSet rs)
        throws ExceptionWarning
    {
        close(rs);
        close(connection, ps);
    }

    protected void close(PreparedStatement ps)
        throws ExceptionWarning
    {
        try
        {
            if(ps != null)
                ps.close();
        }
        catch(SQLException e)
        {
            throw new ExceptionWarning("Error while trying to close a PreparedStatement!", e);
        }
    }

    protected void close(PreparedStatement ps, ResultSet rs)
        throws ExceptionWarning
    {
        close(rs);
        close(ps);
    }

    protected void close(ResultSet rs)
        throws ExceptionWarning
    {
        try
        {
            if(rs != null)
                rs.close();
        }
        catch(SQLException e)
        {
            throw new ExceptionWarning("Error while trying to close a ResultSet!", e);
        }
    }

    public boolean delete()
        throws ExceptionWarning
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            boolean flag = delete(conn);
            return flag;
        }
        finally
        {
            close(conn);
        }
    }

    public boolean delete(Connection conn)
        throws ExceptionWarning
    {
        PreparedStatement ps = null;
        boolean ret = true;
        Vector deleteCmd = new Vector();
        Object o2 = this;
        Class c = getClass();
        String sql = null;
        try
        {
            do
            {
                sql = generateSQLDelete(o2, null);
                deleteCmd.addElement(sql);
                c = o2.getClass().getSuperclass();
                if(isSuperEntity(c))
                    break;
                o2 = c.newInstance();
            } while(true);
            for(int i = deleteCmd.size() - 1; i > -1; i--)
            {
                sql = (String)deleteCmd.elementAt(i);
                ps = conn.prepareStatement(sql);
                ps.execute();
                close(ps);
            }

            boolean flag = ret;
            return flag;
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLDelete", e);
            throw new ExceptionWarning("Exception has been throw in delete method", e);
        }
        finally
        {
            close(ps);
        }
    }

    private String generateSQLDelete(Object methodObj, String where)
        throws ExceptionWarning
    {
        String ret = null;
        try
        {
            StringBuffer sql1 = new StringBuffer(CMD_DELETE + FROM);
            sql1.append(((AbstractPersistentEntity)methodObj).getTableName());
            if(where == null || where.length() == 0)
            {
                Object struPk[] = ((AbstractPersistentEntity)methodObj).getStructurePk();
                sql1.append(WHERE);
                for(int i = 0; i < struPk.length; i += 3)
                {
                    sql1.append(struPk[i + 1] + "=");
                    sql1.append(getValue((Integer)struPk[i + 2], (String)struPk[i]) + " AND ");
                }

                sql1.delete(sql1.length() - 4, sql1.length());
            } else
            {
                sql1.append(getFormatedWhere(where));
            }
            ret = sql1.toString();
            SystemParameter.log(getSID(), TechCommonKeys.LOG_INFO, "SQLDelete: " + ret);
            sql1 = null;
            return ret;
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLDelete: " + ret, e);
            throw new ExceptionWarning("Error on generateSQLDelete command!", e);
        }
    }

    private String generateSQLInsert(Object methodObj)
        throws ExceptionWarning
    {
        String ret = null;
        try
        {
            StringBuffer sql1 = new StringBuffer(CMD_INSERT + " INTO ");
            StringBuffer sql2 = new StringBuffer(") VALUES (");
            sql1.append(((AbstractPersistentEntity)methodObj).getTableName() + " (");
            Object struPk[] = ((AbstractPersistentEntity)methodObj).getStructurePk();
            Object stru[] = ((AbstractPersistentEntity)methodObj).getStructure();
            for(int i = 0; i < struPk.length; i += 3)
            {
                sql1.append(struPk[i + 1] + ",");
                sql2.append(getValue((Integer)struPk[i + 2], (String)struPk[i]) + ",");
            }

            for(int i = 0; i < stru.length; i += 3)
            {
                sql1.append(stru[i + 1] + ",");
                sql2.append(getValue((Integer)stru[i + 2], (String)stru[i]) + ",");
            }

            sql1.delete(sql1.length() - 1, sql1.length());
            sql2.delete(sql2.length() - 1, sql2.length());
            ret = sql1.toString() + sql2.toString() + ")";
            SystemParameter.log(getSID(), TechCommonKeys.LOG_INFO, "SQLInsert: " + ret);
            sql1 = null;
            sql2 = null;
            return ret;
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLInsert: " + ret, e);
            throw new ExceptionWarning("Error on generateSQLInsert command!", e);
        }
    }

    protected String generateSQLSelect(String where)
        throws ExceptionWarning, ExceptionInjection
    {
        String ret2 = null;
        try
        {
            StringBuffer ret = new StringBuffer(500);
            Object struPk[] = getStructurePk();
            Object stru[] = (Object[])null;
            Object o = this;
            String table1 = getTableName();
            ret.append(CMD_SELECT);
            if(struPk != null)
            {
                for(int i = 0; i < struPk.length; i += 3)
                    ret.append(table1 + "." + struPk[i + 1].toString() + COMMA);

            }
            StringBuffer tables = new StringBuffer();
            StringBuffer wheretmp = new StringBuffer();
            do
            {
                stru = ((AbstractPersistentEntity)o).getStructure();
                tables.append(((AbstractPersistentEntity)o).getTableName());
                for(int i = 0; i < stru.length; i += 3)
                    ret.append(stru[i + 1].toString() + COMMA);

                Class c = o.getClass().getSuperclass();
                if(isSuperEntity(c))
                    break;
                table1 = ((AbstractPersistentEntity)o).getTableName();
                o = c.newInstance();
                String table2 = ((AbstractPersistentEntity)o).getTableName();
                for(int i = 0; i < struPk.length; i += 3)
                    wheretmp.append(table1 + "." + struPk[i + 1].toString() + " = " + table2 + "." + struPk[i + 1].toString() + " and ");

                tables.append(COMMA);
            } while(true);
            if(wheretmp.length() > 0)
            {
                wheretmp.delete(wheretmp.length() - 4, wheretmp.length());
                if(where == null)
                    where = wheretmp.toString();
                else
                    where = where + " and " + wheretmp.toString();
            }
            ret.deleteCharAt(ret.length() - 1);
            ret.append(FROM + tables.toString());
            if(where != null && where.length() > 0)
                ret.append(getFormatedWhere(where));
            ret2 = ret.toString();
            SystemParameter.log(getSID(), TechCommonKeys.LOG_INFO, "SQLSelect: " + ret2);
            stru = (Object[])null;
            struPk = (Object[])null;
            o = null;
            wheretmp = null;
            tables = null;
            ret = null;            
            return ret2;
        } catch(ExceptionInjection e) {
        	throw e;
        } catch(Exception e) {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLSelect: " + ret2, e);
            throw new ExceptionWarning("Error on generateSQLSelect command!", e);
        }
    }

    protected String generateSQLSelect(String mergeTables[], String where)
        throws ExceptionWarning, ExceptionInjection
    {
        String ret2 = null;
        try
        {
            StringBuffer ret = new StringBuffer(500);
            Object struPk[] = getStructurePk();
            Object stru[] = (Object[])null;
            Object o = this;
            String table1 = getTableName();
            ret.append(CMD_SELECT);
            if(struPk != null)
            {
                for(int i = 0; i < struPk.length; i += 3)
                    ret.append(table1 + "." + struPk[i + 1].toString() + COMMA);

            }
            StringBuffer tables = new StringBuffer();
            StringBuffer wheretmp = new StringBuffer();
            do
            {
                stru = ((AbstractPersistentEntity)o).getStructure();
                tables.append(((AbstractPersistentEntity)o).getTableName());
                for(int i = 0; i < stru.length; i += 3)
                    ret.append(table1 + "." + stru[i + 1].toString() + COMMA);

                Class c = o.getClass().getSuperclass();
                if(isSuperEntity(c))
                    break;
                table1 = ((AbstractPersistentEntity)o).getTableName();
                o = c.newInstance();
                String table2 = ((AbstractPersistentEntity)o).getTableName();
                for(int i = 0; i < struPk.length; i += 3)
                    wheretmp.append(table1 + "." + struPk[i + 1].toString() + " = " + table2 + "." + struPk[i + 1].toString() + " and ");

                tables.append(COMMA);
            } while(true);
            if(wheretmp.length() > 0)
            {
                wheretmp.delete(wheretmp.length() - 4, wheretmp.length());
                if(where == null)
                    where = wheretmp.toString();
                else
                    where = where + " and " + wheretmp.toString();
            }
            ret.deleteCharAt(ret.length() - 1);
            if(mergeTables != null)
            {
                ret.append(FROM + tables.toString());
                for(int i = 0; i < mergeTables.length; i++)
                    ret.append("," + mergeTables[i] + " ");

            } else
            {
                ret.append(FROM + tables.toString());
            }
            if(where != null && where.length() > 0)
                ret.append(getFormatedWhere(where));
            ret2 = ret.toString();            
            SystemParameter.log(getSID(), TechCommonKeys.LOG_INFO, "SQLSelect: " + ret2);
            stru = (Object[])null;
            struPk = (Object[])null;
            o = null;
            wheretmp = null;
            tables = null;
            ret = null;   
            System.out.println("QUERY GERADA>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(ret2);
            System.out.println("QUERY GERADA>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return ret2;
        } catch(ExceptionInjection e) {
        	throw e;
        } catch(Exception e) {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLSelect: " + ret2, e);
            throw new ExceptionWarning("Error on generateSQLSelect command!", e);
        }
    }

    private String generateSQLUpdate(Object methodObj)
        throws ExceptionWarning
    {
        String ret = null;
        try
        {
            StringBuffer sql1 = new StringBuffer(CMD_UPDATE);
            sql1.append(((AbstractPersistentEntity)methodObj).getTableName() + " SET ");
            Object struPk[] = ((AbstractPersistentEntity)methodObj).getStructurePk();
            Object stru[] = ((AbstractPersistentEntity)methodObj).getStructure();
            for(int i = 0; i < stru.length; i += 3)
            {
                sql1.append(stru[i + 1] + "=");
                sql1.append(getValue((Integer)stru[i + 2], (String)stru[i]) + ",");
            }

            sql1.delete(sql1.length() - 1, sql1.length());
            sql1.append(WHERE);
            for(int i = 0; i < struPk.length; i += 3)
            {
                sql1.append(struPk[i + 1] + "=");
                sql1.append(getValue((Integer)struPk[i + 2], (String)struPk[i]) + " AND ");
            }

            sql1.delete(sql1.length() - 4, sql1.length());
            ret = sql1.toString();
            SystemParameter.log(getSID(), TechCommonKeys.LOG_INFO, "SQLUpdate: " + ret);
            sql1 = null;
            return ret;
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLUpdate: " + ret, e);
            throw new ExceptionWarning("Error on generateSQLUpdate command!", e);
        }
    }

    private String generateSQLUpdate(Object methodObj, String fields[])
        throws ExceptionWarning
    {
        String ret = null;
        try
        {
            StringBuffer sql1 = new StringBuffer(CMD_UPDATE);
            sql1.append(((AbstractPersistentEntity)methodObj).getTableName() + " SET ");
            Object struPk[] = ((AbstractPersistentEntity)methodObj).getStructurePk();
            Object stru[] = ((AbstractPersistentEntity)methodObj).getStructure();
            boolean updated = false;
            for(int i = 0; i < stru.length; i += 3)
            {
                for(int j = 0; j < fields.length; j++)
                    if(fields[j].equals((String)stru[i + 1]))
                    {
                        sql1.append(stru[i + 1] + "=");
                        sql1.append(getValue((Integer)stru[i + 2], (String)stru[i]) + ",");
                        updated = true;
                    }

            }

            if(!updated)
                return null;
            sql1.delete(sql1.length() - 1, sql1.length());
            sql1.append(WHERE);
            for(int i = 0; i < struPk.length; i += 3)
            {
                sql1.append(struPk[i + 1] + "=");
                sql1.append(getValue((Integer)struPk[i + 2], (String)struPk[i]) + " AND ");
            }

            sql1.delete(sql1.length() - 4, sql1.length());
            ret = sql1.toString();
            SystemParameter.log(getSID(), TechCommonKeys.LOG_INFO, "SQLUpdate: " + ret);
            sql1 = null;
            return ret;
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLUpdate: " + ret, e);
            throw new ExceptionWarning("Error on generateSQLUpdate command!", e);
        }
    }

    public Vector get(String where)
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            Vector vector = get(where, conn);
            return vector;
        }
        finally
        {
            close(conn);
        }
    }

    public Vector get(String where, Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        return getVector(generateSQLSelect(where), conn);
    }

    public Vector get(String where, long startRow, long quantity)
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            Vector vector = get(where, startRow, quantity, conn);
            return vector;
        }
        finally
        {
            close(conn);
        }
    }

    public Vector get(String where, long startRow, long quantity, Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        if(getDatabaseType() == 4)
        {
            if(where == null)
                where = "1=1";
            where = where + " LIMIT " + (startRow - 1L) + "," + quantity;
            return getVector(generateSQLSelect(where), conn);
        }
        if(getDatabaseType() == 0)
        {
            return getVector(getOracle(where, startRow, quantity), conn);
        } else
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.get(" + where + "," + startRow + "," + quantity + ")");
            throw new ExceptionWarning("get with paging feature is not implemented for this dataBaseType!");
        }
    }

    public Vector getAll()
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            Vector vector = get(null, conn);
            return vector;
        }
        finally
        {
            close(conn);
        }
    }

    public Vector getAll(Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        return get(null, conn);
    }

    public ArrayList getAllArray()
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            ArrayList arraylist = getArray(null, conn);
            return arraylist;
        }
        finally
        {
            close(conn);
        }
    }

    public ArrayList getAllArray(Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        return getArray(null, conn);
    }

    public ArrayList getArray(String where)
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            ArrayList arraylist = getArray(where, conn);
            return arraylist;
        }
        finally
        {
            close(conn);
        }
    }

    public ArrayList getArray(String where, Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        return getVectorArray(generateSQLSelect(where), conn);
    }

    public ArrayList getArray(String where, long startRow, long quantity)
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            ArrayList arraylist = getArray(where, startRow, quantity, conn);
            return arraylist;
        }
        finally
        {
            close(conn);
        }
    }

    public ArrayList getArray(String where, long startRow, long quantity, Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        if(getDatabaseType() == 4)
        {
            if(where == null)
                where = "1=1";
            where = where + " LIMIT " + (startRow - 1L) + "," + quantity;
            return getVectorArray(generateSQLSelect(where), conn);
        }
        if(getDatabaseType() == 0)
        {
            return getVectorArray(getOracle(where, startRow, quantity), conn);
        } else
        {
            getDatabaseType();
            return getVectorArray(generateSQLSelect(where), startRow, quantity, conn);
        }
    }

    public ArrayList getArray(String mergeTables[], String where, long startRow, long quantity)
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            ArrayList arraylist = getArray(mergeTables, where, startRow, quantity, conn);
            return arraylist;
        }
        finally
        {
            close(conn);
        }
    }

    public ArrayList getArray(String mergeTables[], String where, long startRow, long quantity, Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        if(getDatabaseType() == 4)
        {
            if(where == null)
                where = "1=1";
            where = where + " LIMIT " + (startRow - 1L) + "," + quantity;
            return getVectorArray(generateSQLSelect(mergeTables, where), conn);
        }
        if(getDatabaseType() == 0)
        {
            return getVectorArray(getOracle(where, startRow, quantity), conn);
        } else
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.get(" + where + "," + startRow + "," + quantity + ")");
            throw new ExceptionWarning("get with paging feature is not implemented for this dataBaseType!");
        }
    }

    public ArrayList getArraySelect(String selectCommand)
        throws ExceptionWarning
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            ArrayList arraylist = getArraySelect(selectCommand, conn);
            return arraylist;
        }
        finally
        {
            close(conn);
        }
    }

    public ArrayList getArraySelect(String selectCommand, Connection conn)
        throws ExceptionWarning
    {
        return getVectorArray(selectCommand, conn);
    }

    protected Connection getConnection()
        throws ExceptionWarning
    {
        return ConnectionPool.getInstance().getConnection(getPoolName());
    }

    public long getCount()
        throws ExceptionWarning, ExceptionInjection
    {
        return getCount(null);
    }

    public long getCount(String where)
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            long l = getCount(where, conn);
            return l;
        }
        finally
        {
            close(conn);
        }
    }

    public long getCount(String where, Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            conn = getConnection();
            String sql = "select count(*) from " + getTableName() + getFormatedWhere(where);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next())
            {
                long l = rs.getLong(1);
                return l;
            }
            return 0L;
        }
        catch(SQLException e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getCount(" + where + ")", e);
            throw new ExceptionWarning("SQLException in getCount method", e);
        }
        finally
        {
            close(ps, rs);
        }
    }

    public long getCount(String mergeTables[], String where)
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            long l = getCount(mergeTables, where, conn);
            return l;
        }
        finally
        {
            close(conn);
        }
    }

    public long getCount(String mergeTables[], String where, Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            conn = getConnection();
            String sql = "select count(*) from " + getTableName() + " a";
            if(mergeTables != null)
            {
                for(int i = 0; i < mergeTables.length; i++)
                    sql = sql + "," + mergeTables[i] + " ";

            }
            sql = sql + getFormatedWhere(where);
            SystemParameter.log(getSID(), TechCommonKeys.LOG_DEBUG, "SQLCount:" + sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next())
            {
                long l = rs.getLong(1);
                return l;
            }
            return 0L;
        }
        catch(SQLException e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getCount(" + where + ")", e);
            throw new ExceptionWarning("SQLException in getCount method", e);
        }
        finally
        {
            close(ps, rs);
        }
    }

    public abstract int getDatabaseType();

    protected java.util.Date getDate2Java(String value)  throws Exception  {
        if(value == null)
            return null;
        else
            return DATE_FORMAT.parse(value);
    }

    protected java.util.Date getDateTime2Java(String value)
        throws Exception
    {
        if(value == null)
            return null;
        else
            return DATE_TIME_FORMAT.parse(value);
    }

    protected String getFormatedWhere(String where) throws ExceptionWarning, ExceptionInjection
    {
        if(where == null || where.length() == 0)
            return "";
        String tmp = where.toLowerCase();

        if( // tmp.contains("select") || 
        	tmp.contains("&") ||
//        	tmp.contains("%") ||
//        	tmp.contains("'") ||
    		tmp.contains("ascii") ||
    		tmp.contains("database") ) {
        	
        	System.out.println("TENTATIVA DE SQL INJECTION STAL!!!!!!! " + tmp);
//			SendMailSparkpost.send("TENTATIVA DE SQL INJECTION STAL!!!!!", ConfigEntity.CONFIG.get("email"), "fabiojsjr@gmail.com", "text/html", new StringBuffer(tmp) );
        	throw new ExceptionInjection("TENTATIVA DE SQL INJECTION STAL!!!!!!! " + tmp);
        }
        
        if(!tmp.startsWith("where "))
        {
            if(tmp.startsWith("order "))
                return " " + where;
            where = WHERE + where;
        }
        
        return where;
    }

    protected String getJava2Date(java.util.Date date)
    {
        if(date == null)
            return "";
        else
            return DATE_FORMAT.format(date);
    }

    protected String getJava2DateTime(java.util.Date date)
    {
        if(date == null)
            return "";
        else
            return DATE_TIME_FORMAT.format(date);
    }

    protected String getJava2DBDate(java.util.Date date)
    {
        if(date == null)
        {
            return getNull();
        } else
        {
            String formatter = DataTypes.DATES[getDatabaseType()];
            String strDate = DataTypes.DB_DATE_FORMAT.format(date);
            return StringUtil.replace(formatter, "@", strDate);
        }
    }

    protected String getJava2DBDateTime(java.util.Date date)
    {
        if(date == null)
        {
            return getNull();
        } else
        {
            String formatter = DataTypes.DATESTIMES[getDatabaseType()];
            String strDate = DataTypes.DB_DATE_TIME_FORMAT.format(date);
            return StringUtil.replace(formatter, "@", strDate);
        }
    }

    protected String getJava2DBTime(java.util.Date date)
    {
        if(date == null)
        {
            return getNull();
        } else
        {
            String formatter = DataTypes.TIMES[getDatabaseType()];
            String strDate = DataTypes.DB_TIME_FORMAT.format(date);
            return StringUtil.replace(formatter, "@", strDate);
        }
    }

    protected String getJava2Time(java.util.Date date)
    {
        if(date == null)
            return "";
        else
            return TIME_FORMAT.format(date);
    }

    private String getNull()
    {
        return DataTypes.NULLS[getDatabaseType()];
    }

    private String getOracle(String where, long startRow, long quantity)
        throws ExceptionWarning, ExceptionInjection
    {
        StringBuffer ret = new StringBuffer(1000);
        Object defs[] = getStructurePk();
        ret.append("SELECT * FROM ( ");
        ret.append("SELECT ROWNUM CURRLINE");
        if(defs != null)
        {
            for(int i = 0; i < defs.length; i += 3)
                ret.append(", " + defs[i]);

        }
        defs = getStructure();
        if(defs != null)
        {
            for(int i = 0; i < defs.length; i += 3)
                ret.append(", " + defs[i]);

        }
        ret.append(" FROM ( SELECT * FROM " + getTableName());
        if(where == null || where.length() == 0)
            where = " 1=1 ";
        ret.append(getFormatedWhere(where));
        ret.append(") ) WHERE ");
        ret.append(" CURRLINE > " + (startRow - 1L) + " and CURRLINE < " + (startRow + quantity));
        defs = (Object[])null;
        return ret.toString();
    }

    public abstract String getPoolName();

    private String getQuote()
    {
        return DataTypes.QUOTES[getDatabaseType()];
    }

    public abstract String getSID();

    public abstract Object[] getStructure();

    public abstract Object[] getStructurePk();

    public abstract String getTableName();

    public boolean getThis()
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            boolean flag = getThis(conn);
            return flag;
        }
        finally
        {
            close(conn);
        }
    }

    public boolean getThis(Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        StringBuffer where = new StringBuffer();
        Object struPk[] = getStructurePk();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sand = "";
        if(struPk != null)
        {
            for(int i = 0; i < struPk.length; i += 3)
            {
                where.append(sand + struPk[i + 1].toString() + "=" + getValue((Integer)struPk[i + 2], (String)struPk[i]));
                sand = " and ";
            }

        }
        try
        {
            String sql = generateSQLSelect(where.toString());
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next())
            {
                populate(rs, this, false);
                return true;
            }
            return false;
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getThis(connection)", e);
            throw new ExceptionWarning("SQLException in getThis method", e);
        }
        finally
        {
            close(ps, rs);
        }
    }

    public boolean getThis(String where)
        throws ExceptionWarning, ExceptionInjection
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            boolean flag = getThis(where, conn);
            return flag;
        }
        finally
        {
            close(conn);
        }
    }

    public boolean getThis(String where, Connection conn)
        throws ExceptionWarning, ExceptionInjection
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            String sql = generateSQLSelect(where);           
            ps = conn.prepareStatement(sql);            
            rs = ps.executeQuery();
            if(rs.next())
            {
                populate(rs, this, false);
                return true;
            }
            return false;
        }
        catch(SQLException e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getThis(" + where + ",connection)", e);
            throw new ExceptionWarning("SQLException in getThis method", e);
        }
        finally
        {
            close(ps, rs);
        }
    }

    protected java.util.Date getTime2Java(String value)
        throws Exception
    {
        if(value == null)
            return null;
        else
            return TIME_FORMAT.parse(value);
    }

    private String getValue(Integer dataType, String methodName)
        throws ExceptionWarning
    {
        try
        {
            Method m = getClass().getMethod("get" + methodName, new Class[0]);
            Object o = m.invoke(this, null);
            if(o == null)
                return getNull();
            if(dataType == DataTypes.JAVA_STRING)
                return getQuote() + replaceQuotes(o.toString()) + getQuote();
            if(dataType == DataTypes.JAVA_LONG)
                return o.toString();
            if(dataType == DataTypes.JAVA_INTEGER)
                return o.toString();
            if(dataType == DataTypes.JAVA_DOUBLE)
                return o.toString();
            if(dataType == DataTypes.JAVA_FLOAT)
                return o.toString();
            if(dataType == DataTypes.JAVA_DATE)
                return getJava2DBDate((java.util.Date)o);
            if(dataType == DataTypes.JAVA_STRINGBUFFER)
                return getQuote() + replaceQuotes(o.toString()) + getQuote();
            if(dataType == DataTypes.JAVA_TIME)
                return getJava2DBTime((java.util.Date)o);
            if(dataType == DataTypes.JAVA_DATETIME)
                return getJava2DBDateTime((java.util.Date)o);
            if(dataType == DataTypes.JAVA_OBJECT)
                return getQuote() + o.toString() + getQuote();
            else
                return null;
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @getValue[" + dataType + "," + methodName + "]", e);
            throw new ExceptionWarning("Error while retrieving an entity attribute [" + getClass().getName() + ":" + methodName + "()]", e);
        }
    }

    private String getValueString(Integer dataType, String methodName)
        throws ExceptionWarning
    {
        try
        {
            Method m = getClass().getMethod("get" + methodName, new Class[0]);
            Object o = m.invoke(this, null);
            if(dataType == DataTypes.JAVA_STRING)
                if(o == null)
                    return "";
                else
                    return o.toString();
            if(dataType == DataTypes.JAVA_LONG)
                if(o == null)
                    return "";
                else
                    return o.toString();
            if(dataType == DataTypes.JAVA_INTEGER)
                if(o == null)
                    return "";
                else
                    return o.toString();
            if(dataType == DataTypes.JAVA_DOUBLE)
                if(o == null)
                    return "";
                else
                    return o.toString();
            if(dataType == DataTypes.JAVA_FLOAT)
                if(o == null)
                    return "";
                else
                    return o.toString();
            if(dataType == DataTypes.JAVA_DATE)
                if(o == null)
                    return "";
                else
                    return getJava2Date((java.util.Date)o);
            if(dataType == DataTypes.JAVA_STRINGBUFFER)
                if(o == null)
                    return "";
                else
                    return o.toString();
            if(dataType == DataTypes.JAVA_TIME)
                if(o == null)
                    return "";
                else
                    return getJava2Time((java.util.Date)o);
            if(dataType == DataTypes.JAVA_DATETIME)
                if(o == null)
                    return "";
                else
                    return getJava2DateTime((java.util.Date)o);
            if(dataType == DataTypes.JAVA_OBJECT)
            {
                if(o == null)
                    return "";
                else
                    return o.toString();
            } else
            {
                return "";
            }
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @getValueString[" + dataType + "," + methodName + "]", e);
            throw new ExceptionWarning("Error while retrieving an entity attribute [" + getClass().getName() + ":" + methodName + "()]", e);
        }
    }

    private Vector getVector(String sql, Connection conn)
        throws ExceptionWarning
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector ret = new Vector();
        try
        {
            ps = conn.prepareStatement(sql);
            Object o;
            for(rs = ps.executeQuery(); rs.next(); ret.addElement(o))
            {
                o = getClass().newInstance();
                populate(rs, o, false);
            }

            Vector vector = ret;
            return vector;
        }
        catch(InstantiationException e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVector(" + sql + ",connection)", e);
            throw new ExceptionWarning("InstantiationException in class " + getClass().getName() + " method get", e);
        }
        catch(IllegalAccessException e1)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVector(" + sql + ",connection)", e1);
            throw new ExceptionWarning("IllegalAccessException in class " + getClass().getName() + " method get", e1);
        }
        catch(SQLException e2)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVector(" + sql + ",connection)", e2);
            throw new ExceptionWarning("SQLException in class " + getClass().getName(), e2);
        }
        finally
        {
            close(ps, rs);
        }
    }

    protected ArrayList getVectorArray(String sql, Connection conn)
        throws ExceptionWarning
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList ret = new ArrayList();
        try
        {
            ps = conn.prepareStatement(sql);
            Object o;
            for(rs = ps.executeQuery(); rs.next(); ret.add(o))
            {
                o = getClass().newInstance();
                populate(rs, o, false);
            }

            ArrayList arraylist = ret;
            return arraylist;
        }
        catch(InstantiationException e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVectorArray(" + sql + ",connection)", e);
            throw new ExceptionWarning("InstantiationException in class " + getClass().getName() + " method get", e);
        }
        catch(IllegalAccessException e1)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVectorArray(" + sql + ",connection)", e1);
            throw new ExceptionWarning("IllegalAccessException in class " + getClass().getName() + " method get", e1);
        }
        catch(SQLException e2)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVectorArray(" + sql + ",connection)", e2);
            e2.printStackTrace();
            throw new ExceptionWarning("SQLException in class " + getClass().getName(), e2);
        }
        finally
        {
            close(ps, rs);
        }
    }

    protected ArrayList getVectorArray(String sql, long startRow, long numRecords, Connection conn)
        throws ExceptionWarning
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList ret = new ArrayList();
        try
        {
            System.out.println("{*}sql=" + sql + " s.row=" + startRow + " nr=" + numRecords);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                startRow--;
                if(startRow >= 1L)
                    continue;
                Object o = getClass().newInstance();
                populate(rs, o, false);
                ret.add(o);
                numRecords--;
                if(numRecords <= 0L)
                    break;
            }
            ArrayList arraylist = ret;
            return arraylist;
        }
        catch(InstantiationException e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVectorArray(" + sql + ",connection)", e);
            throw new ExceptionWarning("InstantiationException in class " + getClass().getName() + " method get", e);
        }
        catch(IllegalAccessException e1)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVectorArray(" + sql + ",connection)", e1);
            throw new ExceptionWarning("IllegalAccessException in class " + getClass().getName() + " method get", e1);
        }
        catch(SQLException e2)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVectorArray(" + sql + ",connection)", e2);
            throw new ExceptionWarning("SQLException in class " + getClass().getName(), e2);
        }
        finally
        {
            close(ps, rs);
        }
    }

    protected ArrayList getVectorArrayPartial(String sql, Connection conn)
        throws ExceptionWarning
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList ret = new ArrayList();
        try
        {
            ps = conn.prepareStatement(sql);
            Object o;
            for(rs = ps.executeQuery(); rs.next(); ret.add(o))
            {
                o = getClass().newInstance();
                populate(rs, o, true);
            }

            ArrayList arraylist = ret;
            return arraylist;
        }
        catch(InstantiationException e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVectorArray(" + sql + ",connection)", e);
            throw new ExceptionWarning("InstantiationException in class " + getClass().getName() + " method get", e);
        }
        catch(IllegalAccessException e1)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVectorArray(" + sql + ",connection)", e1);
            throw new ExceptionWarning("IllegalAccessException in class " + getClass().getName() + " method get", e1);
        }
        catch(SQLException e2)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @AbstractEntity.getVectorArray(" + sql + ",connection)", e2);
            throw new ExceptionWarning("SQLException in class " + getClass().getName(), e2);
        }
        finally
        {
            close(ps, rs);
        }
    }

    public boolean insert()
        throws ExceptionWarning
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            boolean flag = insert(conn);
            return flag;
        }
        finally
        {
            close(conn);
        }
    }

    public boolean insert(Connection conn)
        throws ExceptionWarning
    {
        PreparedStatement ps = null;
        boolean ret = true;
        Vector insertCmd = new Vector();
        Object o2 = this;
        Class c = getClass();
        String sql = null;
        try
        {
            do
            {
                sql = generateSQLInsert(o2);
                insertCmd.addElement(sql);
                c = o2.getClass().getSuperclass();
                if(isSuperEntity(c))
                    break;
                o2 = c.newInstance();
            } while(true);
            for(int i = insertCmd.size() - 1; i > -1; i--)            {
                sql = (String)insertCmd.elementAt(i); 
                System.out.println("QUERY GERADA");
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.execute();
                close(ps);
            }

            boolean flag = ret;
            return flag;
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLInsert", e);
            throw new ExceptionWarning("Exception has been throw in insert method", e);
        }
        finally
        {
            close(ps);
        }
    }

    public Integer nullableInt(Integer value)
    {
        if(value != null && value.intValue() == 0)
            value = null;
        return value;
    }

    private void populate(ResultSet rs, Object o, boolean ignoreNotFound)
        throws ExceptionWarning
    {
        Object struPk[] = ((AbstractPersistentEntity)o).getStructurePk();
        Object stru[] = (Object[])null;
        String tableName = null;
        if(getDatabaseType() == 1)
            tableName = "";
        else
            tableName = ((AbstractPersistentEntity)o).getTableName() + ".";
        try
        {
            if(struPk != null)
            {
                for(int i = 0; i < struPk.length; i += 3)
                    populate(struPk[i].toString(), tableName + struPk[i + 1].toString(), struPk[i + 2], rs, o);

            }
            Object o2 = o;
            do
            {
                stru = ((AbstractPersistentEntity)o2).getStructure();
                for(int i = 0; i < stru.length; i += 3)
                    try
                    {
                        populate(stru[i].toString(), stru[i + 1].toString(), stru[i + 2], rs, o);
                    }
                    catch(Exception enf)
                    {
                        if(!ignoreNotFound)
                            throw enf;
                    }

                Class c = o2.getClass().getSuperclass();
                if(isSuperEntity(c))
                    break;
                o2 = c.newInstance();
            } while(true);
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLPopulate", e);
            throw new ExceptionWarning("Error while populating a persistent object of type " + o.getClass().getName(), e);
        }
    }

    private void populate(String methodName, String tableFieldName, Object javaType, ResultSet rs, Object o)
        throws Exception
    {
        methodName = "set" + methodName;
        if(javaType == DataTypes.JAVA_STRING)
        {
            Method m = o.getClass().getMethod(methodName, new Class[] {
                java.lang.String.class
            });
            m.invoke(o, new Object[] {
                rs.getString(tableFieldName)
            });
        } else
        if(javaType == DataTypes.JAVA_LONG)
        {
            Method m = o.getClass().getMethod(methodName, new Class[] {
                java.lang.Long.class
            });
            m.invoke(o, new Object[] {
                new Long(rs.getLong(tableFieldName))
            });
        } else
        if(javaType == DataTypes.JAVA_DOUBLE)
        {
            Method m = o.getClass().getMethod(methodName, new Class[] {
                java.lang.Double.class
            });
            m.invoke(o, new Object[] {
                new Double(rs.getDouble(tableFieldName))
            });
        } else
        if(javaType == DataTypes.JAVA_INTEGER)
        {
            Method m = o.getClass().getMethod(methodName, new Class[] {
                java.lang.Integer.class
            });
            m.invoke(o, new Object[] {
                new Integer(rs.getInt(tableFieldName))
            });
        } else
        if(javaType == DataTypes.JAVA_DATE)
        {
            Method m = o.getClass().getMethod(methodName, new Class[] {
                java.util.Date.class
            });
            m.invoke(o, new Object[] {
                rs.getDate(tableFieldName)
            });
        } else
        if(javaType == DataTypes.JAVA_DATETIME)
        {
            Method m = o.getClass().getMethod(methodName, new Class[] {
                java.util.Date.class
            });
            m.invoke(o, new Object[] {
                rs.getTimestamp(tableFieldName)
            });
        } else
        if (javaType == DataTypes.JAVA_FLOAT) {
            Method m = o.getClass().getMethod(methodName, Float.class);
            m.invoke(o, Float.valueOf(rs.getFloat(tableFieldName)));
        } else {
            // outras condições...
        }
        
        if(javaType == DataTypes.JAVA_TIME)
        {
            Method m = o.getClass().getMethod(methodName, new Class[] {
                java.sql.Time.class
            });
            m.invoke(o, new Object[] {
                rs.getTime(tableFieldName)
            });
        } else
        if(javaType == DataTypes.JAVA_STRINGBUFFER)
        {
            Method m = o.getClass().getMethod(methodName, new Class[] {
                java.lang.StringBuffer.class
            });
            String s = rs.getString(tableFieldName);
            if(s != null)
                m.invoke(o, new Object[] {
                    new StringBuffer(s)
                });
            else
                m.invoke(o, new Object[1]);
        } else
        if(javaType == DataTypes.JAVA_OBJECT)
        {
            Method m = o.getClass().getMethod(methodName, new Class[] {
                java.lang.Object.class
            });
            m.invoke(o, new Object[] {
                rs.getObject(tableFieldName)
            });
        }
    }

    public void populateXML(XMLData data, String tagGroupName)
        throws ExceptionWarning
    {
        populateXML(data, tagGroupName, true);
    }

    public void populateXML(XMLData data, String tagGroupName, boolean useCData)
        throws ExceptionWarning
    {
        data.addParameterTag(tagGroupName);
        Object stru[] = getStructurePk();
        for(int i = 0; i < stru.length; i += 3)
            data.addParameter(StringUtil.firstToLowerCase(stru[i].toString()), getValueString((Integer)stru[i + 2], stru[i].toString()));

        Object o = this;
        Class c = getClass();
        Vector stringBufferData = new Vector();
        try
        {
            do
            {
                stru = ((AbstractPersistentEntity)o).getStructure();
                for(int i = 0; i < stru.length; i += 3)
                    if((Integer)stru[i + 2] == DataTypes.JAVA_STRINGBUFFER && useCData)
                    {
                        stringBufferData.addElement(tagGroupName + "_" + StringUtil.firstToLowerCase(stru[i].toString()));
                        stringBufferData.addElement(getValueString((Integer)stru[i + 2], stru[i].toString()));
                    } else
                    {
                        data.addParameter(StringUtil.firstToLowerCase(stru[i].toString()), getValueString((Integer)stru[i + 2], stru[i].toString()));
                    }

                c = o.getClass().getSuperclass();
                if(isSuperEntity(c))
                    break;
                o = c.newInstance();
            } while(true);
            for(int i = 0; i < stringBufferData.size(); i += 2)
                data.addClosedTag((String)stringBufferData.elementAt(i), (String)stringBufferData.elementAt(i + 1), true);

            stringBufferData = null;
        }
        catch(ExceptionWarning ew)
        {
            throw ew;
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @populateXML", e);
            throw new ExceptionWarning("Exception has been throw in populateXML method", e);
        }
    }

    public final void putValueString(String mutatorName, String value, Object javaType)
        throws ExceptionWarning
    {
        if(value == null)
            return;
        try
        {
            String methodName = "set" + mutatorName;
            Object o = this;
            if(javaType == DataTypes.JAVA_STRING)
            {
                Method m = o.getClass().getMethod(methodName, new Class[] {
                    java.lang.String.class
                });
                m.invoke(o, new Object[] {
                    value
                });
            } else
            if(javaType == DataTypes.JAVA_LONG)
            {
                Method m = o.getClass().getMethod(methodName, new Class[] {
                    java.lang.Long.class
                });
                m.invoke(o, new Object[] {
                    new Long(value)
                });
            } else
            if(javaType == DataTypes.JAVA_DOUBLE)
            {
                Method m = o.getClass().getMethod(methodName, new Class[] {
                    java.lang.Double.class
                });
                m.invoke(o, new Object[] {
                    new Double(value)
                });
            } else
            if(javaType == DataTypes.JAVA_INTEGER)
            {
                Method m = o.getClass().getMethod(methodName, new Class[] {
                    java.lang.Integer.class
                });
                m.invoke(o, new Object[] {
                    new Integer(value)
                });
            } else
            if(javaType == DataTypes.JAVA_DATE)
            {
                Method m = o.getClass().getMethod(methodName, new Class[] {
                    java.util.Date.class
                });
                m.invoke(o, new Object[] {
                    getDate2Java(value)
                });
            } else
            if(javaType == DataTypes.JAVA_DATETIME)
            {
                Method m = o.getClass().getMethod(methodName, new Class[] {
                    java.util.Date.class
                });
                m.invoke(o, new Object[] {
                    getDateTime2Java(value)
                });
            } else
            if(javaType == DataTypes.JAVA_FLOAT)
            {
                Method m = o.getClass().getMethod(methodName, new Class[] {
                    java.lang.Float.class
                });
                m.invoke(o, new Object[] {
                    new Float(value)
                });
            } else
            if(javaType == DataTypes.JAVA_TIME)
            {
                Method m = o.getClass().getMethod(methodName, new Class[] {
                    java.sql.Time.class
                });
                m.invoke(o, new Object[] {
                    getTime2Java(value)
                });
            } else
            if(javaType == DataTypes.JAVA_STRINGBUFFER)
            {
                Method m = o.getClass().getMethod(methodName, new Class[] {
                    java.lang.StringBuffer.class
                });
                m.invoke(o, new Object[] {
                    new StringBuffer(value)
                });
            } else
            if(javaType == DataTypes.JAVA_OBJECT)
            {
                Method m = o.getClass().getMethod(methodName, new Class[] {
                    java.lang.Object.class
                });
                m.invoke(o, new Object[] {
                    value
                });
            }
        }
        catch(Exception e)
        {
            throw new ExceptionWarning("Error in putValue", e);
        }
    }

    protected String replaceQuotes(String value)
    {
        return StringUtil.replace(value, "'", "''");
    }

    public boolean update()
        throws ExceptionWarning
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            boolean flag = update(conn);
            return flag;
        }
        finally
        {
            close(conn);
        }
    }

    public boolean update(Connection conn)
        throws ExceptionWarning
    {
        PreparedStatement ps = null;
        boolean ret = true;
        Vector updateCmd = new Vector();
        Object o2 = this;
        Class c = getClass();
        String sql = null;
        try
        {
            do
            {
                sql = generateSQLUpdate(o2);
                updateCmd.addElement(sql);
                c = o2.getClass().getSuperclass();
                if(isSuperEntity(c))
                    break;
                o2 = c.newInstance();
            } while(true);
            for(int i = updateCmd.size() - 1; i > -1; i--)
            {
                sql = (String)updateCmd.elementAt(i);
                ps = conn.prepareStatement(sql);
                ps.execute();
                close(ps);
            }

            boolean flag = ret;
            return flag;
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLUpdate", e);
            throw new ExceptionWarning("Exception has been throw in update method", e);
        }
        finally
        {
            close(ps);
        }
    }

    public boolean update(String fields[])
        throws ExceptionWarning
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            boolean flag = update(fields, conn);
            return flag;
        }
        finally
        {
            close(conn);
        }
    }

    public boolean update(String fields[], Connection conn)
        throws ExceptionWarning
    {
        PreparedStatement ps = null;
        boolean ret = true;
        Vector updateCmd = new Vector();
        Object o2 = this;
        Class c = getClass();
        String sql = null;
        try
        {
            do
            {
                sql = generateSQLUpdate(o2, fields);
                if(sql != null)
                    updateCmd.addElement(sql);
                c = o2.getClass().getSuperclass();
                if(isSuperEntity(c))
                    break;
                o2 = c.newInstance();
            } while(true);
            for(int i = updateCmd.size() - 1; i > -1; i--)
            {
                sql = (String)updateCmd.elementAt(i);
                ps = conn.prepareStatement(sql);
                ps.execute();
                close(ps);
            }

            boolean flag = ret;
            return flag;
        }
        catch(Exception e)
        {
            SystemParameter.log(getSID(), TechCommonKeys.LOG_WARNING, "Exception @SQLUpdate", e);
            throw new ExceptionWarning("Exception has been throw in update method", e);
        }
        finally
        {
            close(ps);
        }
    }

    private static String CMD_DELETE = "DELETE ";
    private static String CMD_INSERT = "INSERT ";
    private static String CMD_SELECT = "SELECT ";
    private static String CMD_UPDATE = "UPDATE ";
    private static String COMMA = ",";
    protected static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    protected static SimpleDateFormat DATE_FORMAT_USA = new SimpleDateFormat("dd/MM/yyyy");
    protected static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static String FROM = " FROM ";
    protected static int LOG_CRASH;
    protected static int LOG_DEBUG;
    protected static int LOG_ERROR;
    protected static int LOG_INFO;
    protected static int LOG_WARNING;
    protected static final String NULL = "NULL";
    protected static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static String WHERE = " WHERE ";

    static
    {
        LOG_CRASH = TechCommonKeys.LOG_CRASH;
        LOG_DEBUG = TechCommonKeys.LOG_DEBUG;
        LOG_ERROR = TechCommonKeys.LOG_ERROR;
        LOG_INFO = TechCommonKeys.LOG_INFO;
        LOG_WARNING = TechCommonKeys.LOG_WARNING;
    }
}