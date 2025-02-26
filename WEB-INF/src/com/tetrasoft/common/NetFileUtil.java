package com.tetrasoft.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * <PRE>
 *
 * </PRE>
 *
 * @author Renato V. Filipov
 * @version 1.0.0a
 * @see <P>
 * <B>Revision History:</B>
 * <PRE>
 * ==================================================================================================================
 * date       By                      Version    Comments
 * ---------- ----------------------- --------   --------------------------------------------------------------------
 * 2003       Renato V. Filipov        1.0.0a     initial release
 *            Renato V. Filipov        1.0.0b     walkthrough
 * ==================================================================================================================
 * </PRE><P>
 * <PRE>
 * <B>©FUTUWARE MULTIMEDIA - Copyright 2003.  <I>All Rights Reserved.</I></B>
 * Este software pertence à FUTUWARE MULTIMEDIA.
 * A sua utilização é limitada aos termos de uso que acompanha este código.
 * Desenvolvido por FUTUWARE MULTIMEDIA - www.futuware.com.br
 *
 * This software is the proprietary information of FUTUWARE MULTIMEDIA.
 * Use is subject to license terms.
 * Developed by FUTUWARE MULTIMEDIA - www.futuware.com.br
 * </PRE>
 */

public class NetFileUtil {
    public void copyFile( String original, String destino ) throws Exception {
        try {
            URL url = new URL(original);

            System.out.println("Opening connection to " + original + "...");

            URLConnection urlC = url.openConnection();
            // Copy resource to local file, use remote file

            InputStream is = url.openStream();
            // Print info about resource

            try {
                File dir = new File( destino.substring( 0, destino.lastIndexOf("/") ) );
                if( !dir.exists() ) dir.mkdirs();
            } catch (Exception e) {
                throw new Exception( e.getMessage() );
            }

            System.out.println("destino = " + destino );

            System.out.print("Copying resource (type: " + urlC.getContentType() + ") ");

            System.out.flush();

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File(destino));
            } catch (Exception e1) {
                throw new Exception(e1.getMessage());
            }

            int oneChar, count=0;
            while ((oneChar=is.read()) != -1) {
               fos.write(oneChar);
               count++;
            }

            is.close();
            fos.close();

            System.out.println(count + " byte(s) copied");
        } catch( Exception e) {
            e.printStackTrace();
            throw new Exception( e.getMessage() );
        }
    }

/*
    public static void main(String args[]) {
        if (args.length < 1) {
            System.err.println("usage: java copyURL URL [LocalFile]");
            System.exit(1);
        } else {
            copyFile( args[0], args[1] );
        }
    }
*/

}
