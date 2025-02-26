/*
 * file EmailSender.java
 *
 *
 */
package com.tetrasoft.app.sender;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.technique.engine.app.SystemParameter;
import com.tetrasoft.util.SendMailSparkpost;

public class EmailSender implements SenderInterface {
	public static final String[]	EMAILS_REPLICACAO = {""};

	public void enviarMensagem( String subject, String mensagemHtml, String from, String[] dest ) throws SenderException {
        enviarMensagem( subject, mensagemHtml, from, dest, 0, 0 );
    }

    @SuppressWarnings("static-access")
    public void enviarPost( String subject, String mensagemHtml, String from, String[] dest ) throws SenderException {
    	try {
    		SendMailSparkpost sendMail = new SendMailSparkpost();
    		for (int i = 0; i < dest.length; i++) {
    			sendMail.send( subject, from, dest[i], "text/html", new StringBuffer(mensagemHtml) );
    		}
    	} catch (Exception e) {
    	}
    }
	public boolean enviarMensagem( String subject, String mensagemHtml, String from, String[] dest, long idProduto, long idContato ) throws SenderException {
		boolean retorno = false;
        try {
            String sender = from;
            if( sender.equals("") )
                sender = SystemParameter.get(SID, TAG_ENVIO, "email_sender");

//            @SuppressWarnings("unused")
//			SendMail sendMail = new SendMail( idProduto, idContato );
//            for (int i = 0; i < dest.length; i++) {
//            	retorno = SendMail.send( SID, subject, sender, dest[i], "text/html", new StringBuffer(mensagemHtml) );
//            }
        } catch (Exception e) {
        }

        return retorno;
    }
    /**
     * Envia email com Thread
     */
	public static void enviarMensagemThread(String assunto, String msg, String emails) {
		enviarMensagemThread(assunto, msg, new String[]{emails} );
	}
    public static void enviarMensagemThread(String assunto, String msg, String[] emails) {
    	new Thread("email") {
    		@Override
    		public void run() {
    			for (String email: emails) {
    				try {
    					SendMailSparkpost.send(assunto, "allgenda@allgenda.org.br", email, "text/html", new StringBuffer(msg));
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		}

    		private String		assunto;
    		private String		msg;
    		private String[]	emails;

    		public void email(String assunto, String msg, String[] emails) {
    			this.assunto = assunto;
    			this.msg = msg;
    			this.emails=emails;
    			this.start();
    		}
    	}.email(assunto, msg, emails);
    }

    /**
     * Envia email com Thread
     */
    public static void enviarMensagem(String assunto, Exception e, String[] emails) {
		enviarMensagemThread(assunto, getStack(e).replaceAll("\n", "<br>"), emails);
	}

	public static String getStack(Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	public static String getStack(Throwable ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}