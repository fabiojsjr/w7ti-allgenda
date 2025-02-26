// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:15:12
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DataTypes.java

package com.technique.engine.data;

import java.text.SimpleDateFormat;

public class DataTypes
{

    private DataTypes()
    {
    }

    public static SimpleDateFormat DB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat DB_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat DB_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String QUOTES[] = {
        "'", "'", "'", "'", "'"
    };
    public static final String NULLS[] = {
        "NULL", "NULL", "NULL", "NULL", "NULL"
    };
    public static final String DATES[] = {
        "TO_DATE('@','yyyy-mm-dd')", "'@'", "'@'", "TO_DATE('@','yyyy-mm-dd')", "'@'"
    };
    public static final String DATESTIMES[] = {
        "TO_DATE('@','yyyy-mm-dd hh24:mi:ss')", "'@'", "'@'", "TO_DATE('@','yyyy-mm-dd hh:mm:ss')", "'@'"
    };
    public static final String TIMES[] = {
        "TO_DATE('@','hh24:mi:ss')", "'@'", "'@'", "TO_DATE('@','hh:mm:ss')", "'@'"
    };
    public static final int DB_ORACLE = 0;
    public static final int DB_SQLSERVER = 1;
    public static final int DB_SYBASE = 2;
    public static final int DB_DB2 = 3;
    public static final int DB_MYSQL = 4;
    public static final Boolean TRUE = new Boolean(true);
    public static final Boolean FALSE = new Boolean(false);
    public static final String JAVA_TYPES[] = {
        "java.lang.String", "java.lang.Long", "java.lang.Integer", "java.lang.Double", "java.lang.StringBuffer", "java.util.Date", "java.util.Date", "java.util.Date", "java.lang.Float", "java.lang.Object", 
        "com.technique.engine.data.AbstractPersistentEntity"
    };
    public static final Integer JAVA_STRING = new Integer(0);
    public static final Integer JAVA_LONG = new Integer(1);
    public static final Integer JAVA_INTEGER = new Integer(2);
    public static final Integer JAVA_DOUBLE = new Integer(3);
    public static final Integer JAVA_STRINGBUFFER = new Integer(4);
    public static final Integer JAVA_DATE = new Integer(5);
    public static final Integer JAVA_DATETIME = new Integer(6);
    public static final Integer JAVA_TIME = new Integer(7);
    public static final Integer JAVA_FLOAT = new Integer(8);
    public static final Integer JAVA_OBJECT = new Integer(9);
    public static final Integer JAVA_OBJECTENTITY = new Integer(10);

}