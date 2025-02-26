// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:17:26
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Base64.java

package com.technique.engine.util;


public class Base64
{

    private Base64()
    {
    }

    public static String encode(byte b[])
    {
        int outputLength = ((b.length + 2) / 3) * 4;
        if(lineLength != 0)
        {
            int lines = ((outputLength + lineLength) - 1) / lineLength - 1;
            if(lines > 0)
                outputLength += lines * lineSeparator.length();
        }
        StringBuffer sb = new StringBuffer(outputLength);
        int linePos = 0;
        int len = (b.length / 3) * 3;
        int leftover = b.length - len;
        for(int i = 0; i < len; i += 3)
        {
            if((linePos += 4) > lineLength)
            {
                if(lineLength != 0)
                    sb.append(lineSeparator);
                linePos = 4;
            }
            int combined = b[i + 0] & 0xff;
            combined <<= 8;
            combined |= b[i + 1] & 0xff;
            combined <<= 8;
            combined |= b[i + 2] & 0xff;
            int c3 = combined & 0x3f;
            combined >>>= 6;
            int c2 = combined & 0x3f;
            combined >>>= 6;
            int c1 = combined & 0x3f;
            combined >>>= 6;
            int c0 = combined & 0x3f;
            sb.append(valueToChar[c0]);
            sb.append(valueToChar[c1]);
            sb.append(valueToChar[c2]);
            sb.append(valueToChar[c3]);
        }

        byte abyte0[];
        byte abyte1[];
        switch(leftover)
        {
        case 1: // '\001'
            if((linePos += 4) > lineLength)
            {
                if(lineLength != 0)
                    sb.append(lineSeparator);
                linePos = 4;
            }
            abyte0 = new byte[3];
            abyte0[0] = b[len];
            sb.append(encode(abyte0).substring(0, 2));
            sb.append("==");
            break;

        case 2: // '\002'
            if((linePos += 4) > lineLength)
            {
                if(lineLength != 0)
                    sb.append(lineSeparator);
                linePos = 4;
            }
            abyte1 = new byte[3];
            abyte1[0] = b[len];
            abyte1[1] = b[len + 1];
            sb.append(encode(abyte1).substring(0, 3));
            sb.append("=");
            break;
        }
        if(outputLength != sb.length())
        {
            System.out.println("Base64 Error: output length mis-estimated");
            System.out.println("estimate:" + outputLength);
            System.out.println("actual:" + sb.length());
        }
        return sb.toString();
    }

    public static byte[] decode(String s)
    {
        byte b[] = new byte[(s.length() / 4) * 3];
        int cycle = 0;
        int combined = 0;
        int j = 0;
        int len = s.length();
        int dummies = 0;
        for(int i = 0; i < len;)
        {
            int c = s.charAt(i);
            int value = c > 255 ? -1 : charToValue[c];
            switch(value)
            {
            case -2: 
                value = 0;
                dummies++;
                // fall through

            default:
                switch(cycle)
                {
                case 0: // '\0'
                    combined = value;
                    cycle = 1;
                    break;

                case 1: // '\001'
                    combined <<= 6;
                    combined |= value;
                    cycle = 2;
                    break;

                case 2: // '\002'
                    combined <<= 6;
                    combined |= value;
                    cycle = 3;
                    break;

                case 3: // '\003'
                    combined <<= 6;
                    combined |= value;
                    b[j + 2] = (byte)combined;
                    combined >>>= 8;
                    b[j + 1] = (byte)combined;
                    combined >>>= 8;
                    b[j] = (byte)combined;
                    j += 3;
                    cycle = 0;
                    break;
                }
                // fall through

            case -1: 
                i++;
                break;
            }
        }

        if(cycle != 0)
            throw new ArrayIndexOutOfBoundsException("Input to decode not an even multiple of 4 characters; pad with =.");
        j -= dummies;
        if(b.length != j)
        {
            byte b2[] = new byte[j];
            System.arraycopy(b, 0, b2, 0, j);
            b = b2;
        }
        return b;
    }

    private static String lineSeparator = System.getProperty("line.separator");
    private static int lineLength = 144;
    static final char valueToChar[];
    static final int charToValue[];
    static final int IGNORE = -1;
    static final int PAD = -2;

    static 
    {
        valueToChar = new char[64];
        charToValue = new int[256];
        for(int i = 0; i <= 25; i++)
            valueToChar[i] = (char)(65 + i);

        for(int i = 0; i <= 25; i++)
            valueToChar[i + 26] = (char)(97 + i);

        for(int i = 0; i <= 9; i++)
            valueToChar[i + 52] = (char)(48 + i);

        valueToChar[62] = '+';
        valueToChar[63] = '/';
        for(int i = 0; i < 256; i++)
            charToValue[i] = -1;

        for(int i = 0; i < 64; i++)
            charToValue[valueToChar[i]] = i;

        charToValue[61] = -2;
    }
}