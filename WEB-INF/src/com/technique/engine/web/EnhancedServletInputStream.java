// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:18:10
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   EnhancedServletInputStream.java

package com.technique.engine.web;

import java.io.*;

public class EnhancedServletInputStream extends InputStream
{

    private void fillBuffer()
        throws IOException
    {
        if(streamBufferLength > -1 && streamBufferPos > -1)
        {
            long l = (streamSize - (long)totalLength) + 1L;
            int length = streamBufferSize;
            if((long)length > l)
                length = (int)l;
            streamReaded = length == 0;
            if(length > 0 && maxSize > -1L)
            {
                l = maxSize - (long)totalLength;
                if((long)length > l)
                    length = (int)l;
                if(length == 0)
                    streamBufferFilled = true;
            }
            int numBytes = -1;
            if(length > 0)
                numBytes = servletStream.read(streamBuffer, 0, length);
            if(numBytes == -1)
            {
                streamBuffer = null;
                streamBufferPos = -1;
                streamBufferLength = -1;
            } else
            {
                streamBufferLength = numBytes;
                streamBufferPos = 0;
                totalLength += numBytes;
            }
        }
    }

    public EnhancedServletInputStream(long maxSize, int streamBufferSize, long streamSize, InputStream servletStream)
        throws IOException
    {
        if(maxSize < streamSize && maxSize != -1L)
        {
            throw new IOException("maxSize [" + maxSize + "] should be greather or equals streamSize [" + streamSize + "]");
        } else
        {
            this.maxSize = maxSize;
            this.streamSize = streamSize;
            this.servletStream = servletStream;
            this.streamBufferSize = streamBufferSize;
            streamBuffer = new byte[streamBufferSize];
            fillBuffer();
            return;
        }
    }

    public boolean markSupported()
    {
        return servletStream.markSupported();
    }

    public void mark(int pos)
    {
        servletStream.mark(pos);
    }

    public void close()
        throws IOException
    {
        servletStream.close();
        servletStream = null;
    }

    public int available()
    {
        return streamBufferLength - streamBufferPos;
    }

    @SuppressWarnings("unused")
	private boolean streamReaded()
    {
        return streamReaded;
    }

    public void reset()
        throws IOException
    {
        servletStream.reset();
    }

    public boolean streamBufferFilled()
    {
        return streamBufferFilled;
    }

    public int read()
        throws IOException
    {
        if(streamBuffer == null)
            return -1;
        if(streamBufferFilled)
            throw new IOException("The stream buffer has exceeded the maxSize [" + maxSize + "]");
        if(streamReaded)
            throw new IOException("No more data from stream!");
        if(streamBufferPos < streamBufferLength)
        {
            return (char)streamBuffer[streamBufferPos++];
        } else
        {
            fillBuffer();
            return read();
        }
    }

    public int read(byte b[], int offset, int length)
        throws IOException
    {
        int i = read();
        if(i == -1)
            return -1;
        int qtd;
        for(qtd = 1; i != -1 && qtd < length; qtd++)
        {
            b[offset] = (byte)i;
            i = read();
            offset++;
        }

        return qtd;
    }

    public int read(byte b[])
        throws IOException
    {
        return read(b, 0, b.length);
    }

    public int readLine(byte b[], int offset, int length)
        throws IOException
    {
        int count = 0;
        int i = read();
        if(i == -1)
            return -1;
        for(; i != -1 && count < length; i = read())
        {
            if(i == BARRA_N)
                break;
            b[offset] = (byte)i;
            count++;
            offset++;
        }

        return count;
    }

    public byte[] readLine()
        throws IOException
    {
        int i = read();
        if(i == -1)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            for(; i != -1 && i != BARRA_N; i = read())
                os.write(i);

            byte abyte0[] = os.toByteArray();
            return abyte0;
        }
        finally
        {
            os.close();
            os = null;
        }
    }

    private static int BARRA_N = 10;
    private boolean streamBufferFilled;
    private boolean streamReaded;
    private long maxSize;
    private long streamSize;
    private int streamBufferPos;
    private int streamBufferSize;
    private int streamBufferLength;
    private int totalLength;
    private byte streamBuffer[];
    private InputStream servletStream;

}