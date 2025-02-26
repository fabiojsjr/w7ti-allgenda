// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:30
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FileUtil.java

package com.technique.engine.util;

import com.technique.engine.app.SystemParameter;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

// Referenced classes of package com.technique.engine.util:
//            ExceptionWarning

public class FileUtil
{

    private FileUtil()
    {
    }

    public static File findFile(String fileName)
        throws ExceptionWarning
    {
        try
        {
            File f = null;
            URL url = instance.getClass().getResource("/" + fileName);
            f = new File(url.getFile());
            if(!f.exists())
                f = new File(fileName);
            return f;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new ExceptionWarning("File " + fileName + " not found or corrupted!", e);
        }
    }

    public static StringBuffer getFileContent(String fileName)
        throws ExceptionWarning
    {
        Reader r = null;
        FileReader fr = null;
        try
        {
            StringBuffer ret = new StringBuffer();
            File f = new File(fileName);
            long size = f.length();
            fr = new FileReader(f);
            r = new BufferedReader(fr);
            for(char content[] = new char[(int)size]; r.read(content, 0, (int)size) != -1; ret.append(content));
            StringBuffer stringbuffer = ret;
            return stringbuffer;
        }
        catch(Exception e)
        {
            throw new ExceptionWarning("Error in getFileContent (" + fileName + ")", e);
        }
        finally
        {
            try
            {
                if(r != null)
                    r.close();
                if(fr != null)
                    fr.close();
            }
            catch(Exception e1) { }
        }
    }

    public static ArrayList findFiles(String beginPath, String fileMask, int subDirectories)
        throws ExceptionWarning
    {
        ArrayList ret = new ArrayList();
        boolean end = false;
        boolean begin = false;
        if(fileMask == null)
            fileMask = "";
        if(fileMask.endsWith("*.*"))
        {
            fileMask = fileMask.substring(0, fileMask.length() - 3);
            end = true;
        }
        if(fileMask.endsWith("*"))
        {
            fileMask = fileMask.substring(0, fileMask.length() - 1);
            end = true;
        }
        if(fileMask.startsWith("*"))
        {
            begin = true;
            fileMask = fileMask.substring(1, fileMask.length());
        }
        fileMask = fileMask.toUpperCase();
        findFiles(beginPath, fileMask, ret, subDirectories, begin, end);
        return ret;
    }

    @SuppressWarnings("unchecked")
	private static void findFiles(String startPath, String fileMask, ArrayList files, int subDirectories, boolean begin, boolean end)
        throws ExceptionWarning
    {
        try
        {
            File file = new File(startPath);
            File lstFiles[] = file.listFiles();
            if(lstFiles == null)
                return;
            for(int i = 0; i < lstFiles.length; i++)
                if(lstFiles[i].isDirectory())
                {
                    if(subDirectories > 0 || subDirectories == -1)
                        findFiles(lstFiles[i].getCanonicalPath(), fileMask, files, subDirectories != -1 ? subDirectories - 1 : -1, begin, end);
                } else
                {
                    boolean ret = false;
                    String fileName = lstFiles[i].getName().toUpperCase();
                    if(begin && end && fileName.indexOf(fileMask) > -1)
                        ret = true;
                    if(begin && !end && fileName.endsWith(fileMask))
                        ret = true;
                    if(!begin && end && fileName.startsWith(fileMask))
                        ret = true;
                    if(ret)
                        files.add(lstFiles[i].getCanonicalPath());
                }

        }
        catch(IOException e)
        {
            throw new ExceptionWarning("IOException in find file", e);
        }
    }

    public static void saveTextToFile(String fileName, StringBuffer content)
        throws ExceptionWarning
    {
        saveTextToFile(fileName, content.toString());
    }

    public static void saveTextToFile(String fileName, String content)
        throws ExceptionWarning
    {
        try
        {
            File f = new File(fileName);
            if(f.exists())
                f.delete();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(content.getBytes());
            fos.close();
        }
        catch(Exception e)
        {
            throw new ExceptionWarning("IOException in saveTextToFile", e);
        }
    }

    public static void saveToOutputPath(String sid, File sourceFile, String fileName)
        throws ExceptionWarning
    {
        try
        {
            String destDir = SystemParameter.get(sid, "fileUpload", "outputPath");
            File destFile = new File(destDir + fileName);
            FileInputStream is = new FileInputStream(sourceFile);
            FileOutputStream os = new FileOutputStream(destFile);
            for(int i = -1; (i = is.read()) > -1;)
                os.write(i);

            os.close();
            is.close();
            is = null;
            os = null;
        }
        catch(Exception e)
        {
            throw new ExceptionWarning("Exception in saveToOutputPath. Reason:" + e.getMessage(), e);
        }
    }

    public static void copyFile(File sourceFile, File destFile)
        throws ExceptionWarning
    {
        try
        {
            destFile.mkdirs();
            if(destFile.exists())
                destFile.delete();
            FileInputStream is = new FileInputStream(sourceFile);
            FileOutputStream os = new FileOutputStream(destFile);
            for(int i = -1; (i = is.read()) > -1;)
                os.write(i);

            os.close();
            is.close();
            is = null;
            os = null;
        }
        catch(Exception e)
        {
            throw new ExceptionWarning("Exception in copyFile. Reason:" + e.getMessage(), e);
        }
    }

    private static FileUtil instance = new FileUtil();

}