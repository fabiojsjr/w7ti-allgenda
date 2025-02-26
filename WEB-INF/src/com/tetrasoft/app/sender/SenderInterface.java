/*
 * file SenderInterface.java
 *
 *
 */
package com.tetrasoft.app.sender;

public interface SenderInterface {

   String SID = "Allgenda";
   String TAG_ENVIO = "envioMsg";

   public void enviarMensagem(String subject, String mensagem, String from, String[] dest) throws SenderException;

}