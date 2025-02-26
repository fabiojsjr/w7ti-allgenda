/*
 * AbstractLogger.java
 *
 * Created on 1 de Outubro de 2003, 03:29
 */

package com.tetrasoft.app.loggers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.tetrasoft.app.adaptor.AbstractAdaptorMetaChar;


public abstract class AbstractLogger extends AbstractAdaptorMetaChar {
    protected String cookie="";
    protected boolean connected = false;
    public boolean getConnected() {
        return connected;
    }
    /** Creates a new instance of AbstractLogger */
    public AbstractLogger() {
    }
    public abstract void assertConnection();
    public abstract URLConnection getConnection(URL u) throws IOException;
    public abstract String getLoginUrl();
    public abstract String getHost();
    public abstract String getContentLength();

    public void setConnectionParameters(HttpURLConnection conn2) {
            conn2.setUseCaches(false);
            conn2.setDefaultUseCaches(false);
            conn2.setRequestProperty( "User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; DigExt)");
            //conn2.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
            conn2.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn2.setRequestProperty( "Accept","image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, application/x-shockwave-flash, */*");
            conn2.setRequestProperty( "Referer",getLoginUrl());
            conn2.setRequestProperty( "Accept-Language","pt-br");
            //conn2.setRequestProperty( "Accept-Encoding","gzip, deflate");
            if (getHost()!=null)
            	conn2.setRequestProperty( "Host",getHost());
            if (getContentLength()!=null)
            	conn2.setRequestProperty( "Content-Length",getContentLength());
            conn2.setRequestProperty( "Connection","Keep-Alive");
            conn2.setRequestProperty( "Cache-Control","no-cache");
            if (cookie!=null && !"".equals(cookie)) {
                conn2.setRequestProperty( "Cookie",cookie);
            }
            HttpURLConnection.setFollowRedirects(false);
    }

	public void setConnectionParameters(HttpURLConnection conn2, String cookie) {
			conn2.setUseCaches(false);
			conn2.setDefaultUseCaches(false);
			conn2.setRequestProperty( "User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; DigExt)");
			//conn2.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
			conn2.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn2.setRequestProperty( "Accept","image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, application/x-shockwave-flash, */*");
			conn2.setRequestProperty( "Accept-Language","pt-br");
			//conn2.setRequestProperty( "Accept-Encoding","gzip, deflate");
			if (getContentLength()!=null) {
				conn2.setRequestProperty( "Content-Length",getContentLength());
			} else {
				conn2.setRequestProperty( "Content-Length",cookie.length()+"");
			}
			conn2.setRequestProperty( "Connection","Keep-Alive");
			conn2.setRequestProperty( "Cache-Control","no-cache");
			if (cookie!=null && !"".equals(cookie)) {
				conn2.setRequestProperty( "Cookie",cookie);
			}
			HttpURLConnection.setFollowRedirects(true);
	}

    private static char[] CHAR_REPLACE = new char[] {
        '\r', '\n', '\t'
    };

    protected StringBuffer removeSpaces(StringBuffer buffer) {
        if (buffer == null) return null;
        String s = buffer.toString();
        for (int i=0; i<CHAR_REPLACE.length; i++) {
            s = s.replace(CHAR_REPLACE[i],' ');
        }
        StringBuffer s2 = new StringBuffer(s.length());
        boolean hasSpace=false;
        for (int i=0; i<s.length(); i++) {
            String a = s.substring(i,i+1);
            if (" ".equals(a)) {
                if (!hasSpace) {
                   s2.append(a);
                }
                hasSpace=true;
            } else {
                s2.append(a);
                hasSpace=false;
            }
        }
        return s2;
    }

	protected String getContent( String url ) {
		try {
			URL ulX = new URL( url );
			HttpURLConnection connection = (HttpURLConnection)ulX.openConnection();

			connection.connect();

			InputStream in = connection.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			int i;
			byte[] buf = new byte[8192];
			while(( i = in.read( buf )) > 0 ) {
				out.write( buf, 0, i );
			}
			in.close();
			out.flush();
			connection.disconnect();

			return out.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
