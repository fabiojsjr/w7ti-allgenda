/*
 * file SenderException.java
 *
 *
 */
package com.tetrasoft.app.sender;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class SenderException extends Exception {

   private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
   private String mensagem;

   private String sid;

    /**
     *
     */
    public SenderException() {
        super();
    }

    /**
     * @param message
     */
    public SenderException(String message) {
        super(sdf.format(new Date()) + " - SENDER EXCEPTON: " + message);
      mensagem = message;
    }
   public SenderException(String message, String sid) {
      super(message);
      mensagem = message;
      this.sid = sid;
   }

    /**
     * @param message
     * @param cause
     */
    public SenderException(String message, Throwable cause) {
        super(message, cause);
      mensagem = message;
    }
   public SenderException(String[] message) {
      super(message[0]);
      StringBuffer sb = new StringBuffer();
      String header = sdf.format(new Date()) + " - SENDER EXCEPTON: ";
      for (int i = 0; i < message.length; i++) {
         sb.append(header + message[i] + "\r\n");
      }
      mensagem = sb.toString();
   }


    /**
     * @param cause
     */
    public SenderException(Throwable cause) {
        super(cause);
    }

   public String getMessage() {
      return mensagem;
   }

    /**
     * @return
     */
    public String getSid() {
        return sid;
    }

}
