package com.tetrasoft.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.web.CommandWrapper;
import com.technique.engine.web.RendererXSLtoHTML;

public class SendMailTechEngine {

	public static String MAILER = "TechEngine";
    public static String ADRESS_EXCEPTION = "AddressException";
    public static String MESSAGING_EXCEPTION = "MessagingException";

    public static boolean changeSender = true;

    public SendMailTechEngine() {
    }
    public SendMailTechEngine( boolean change ) {
        changeSender = change;
    }

    public static StringBuffer enxugarEmail(StringBuffer msg) {
    	return new StringBuffer( msg.toString().replaceAll("(?sim)<script.*?</script>",""));
    }
    
    public static void send(String sid, String subject, String from, String to, String contentType, StringBuffer conteudo) throws ExceptionWarning {
		try {
			conteudo = enxugarEmail(conteudo);

			String mailer = MAILER;
			String server = SystemParameter.get(sid, "email", "smtpHost");
			String usr = SystemParameter.get(sid, "email", "smtpUser");
			String passwd = SystemParameter.get(sid, "email", "smtpPasswd");

			Properties props = new Properties();
			props.put("mail.smtp.host", server);

			Session session = null;
			if(usr != null) {
				if(passwd == null) passwd = "";

				props.put("mail.smtp.auth", "true");
				session = Session.getInstance(props, new SMTPAuthenticator(usr, passwd));
			} else {
				session = Session.getInstance(props, null);
			}

			InternetAddress[] reply = new InternetAddress[]{ new InternetAddress(from) };

			MimeMessage mimeMessage = new MimeMessage(session);
//			mimeMessage.setFrom(new InternetAddress(from));
			/*
            if( changeSender && from.toLowerCase().indexOf("@maquina.inf.br") < 0 ) {
                mimeMessage.setFrom(new InternetAddress("maquina@maquina.inf.br"));
                mimeMessage.setReplyTo(reply);
            } else {
                mimeMessage.setFrom(new InternetAddress(from));
            }
			 */

			String nome = from;
			try {
				nome = from.substring( 0, from.indexOf("@") );
			} catch (Exception e) {
			}
			from = "\"" + nome + "\" <catalogo@allgenda.com.br>";

			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.setReplyTo(reply);

			javax.mail.Address toAddress = new InternetAddress(to);
			mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, toAddress);

			mimeMessage.setSubject( MimeUtility.encodeText(subject, "UTF-8", "Q") );
//			mimeMessage.setDataHandler(new DataHandler(new ByteArrayDataSource(message.toString(), contentType)));
			mimeMessage.setHeader("X-Mailer", mailer);
			mimeMessage.setSentDate(new Date());

			MimeBodyPart m1 = new MimeBodyPart();
			MimeBodyPart m2 = new MimeBodyPart();
			m1.setContent( conteudo.toString(), contentType );
			m2.setContent( convertPlain(conteudo.toString()), "text/plain" );

			Multipart mp = new MimeMultipart();
			mp.addBodyPart( m2 );
			mp.addBodyPart( m1 );

			MimeMultipart mm = new MimeMultipart("alternative");
			mm.addBodyPart( m2 );
			mm.addBodyPart( m1 );

			mimeMessage.setContent( mm );

			if(usr != null) {
				Transport.send(mimeMessage);
			} else {
				Transport.send(mimeMessage);
			}
			mimeMessage = null;
		} catch(AddressException e) {
			throw new ExceptionWarning("SendMail.send fail - " + ADRESS_EXCEPTION, e);
		} catch(MessagingException e2) {
			throw new ExceptionWarning("SendMail.send fail - " + MESSAGING_EXCEPTION, e2);
		} catch(Exception e3) {
			throw new ExceptionWarning("SendMail.send fail - ", e3);
		}
	}

	public static void send(String sid, String subject, String from, String[] to, String contentType, StringBuffer conteudo) throws ExceptionWarning {
		try {
			conteudo = enxugarEmail(conteudo);

			String mailer = MAILER;
			String server = SystemParameter.get(sid, "email", "smtpHost");
			String usr = SystemParameter.get(sid, "email", "smtpUser");
			String passwd = SystemParameter.get(sid, "email", "smtpPasswd");

			Properties props = new Properties();
			props.put("mail.smtp.host", server);

			Session session = null;
			if(usr != null) {
				if(passwd == null) passwd = "";

				props.put("mail.smtp.auth", "true");
				session = Session.getInstance(props, new SMTPAuthenticator(usr, passwd));
			} else {
				session = Session.getInstance(props, null);
			}

			InternetAddress[] reply = new InternetAddress[]{ new InternetAddress(from) };

			MimeMessage mimeMessage = new MimeMessage(session);
			String nome = from;
			try {
				nome = from.substring( 0, from.indexOf("@") );
			} catch (Exception e) {
			}
			from = "\"" + nome + "\" <intranet@maquina.inf.br>";

			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.setReplyTo(reply);

			Address[] toAddress = new Address[to.length];
			for( int i = 0; i < to.length; i++ )
				toAddress[i] = new InternetAddress(to[i]);

			mimeMessage.addRecipients(javax.mail.Message.RecipientType.BCC, toAddress);

			mimeMessage.setSubject( MimeUtility.encodeText(subject, "UTF-8", "Q") );
//			mimeMessage.setDataHandler(new DataHandler(new ByteArrayDataSource(message.toString(), contentType)));
			mimeMessage.setHeader("X-Mailer", mailer);
			mimeMessage.setSentDate(new Date());

			MimeBodyPart m1 = new MimeBodyPart();
			MimeBodyPart m2 = new MimeBodyPart();
			m1.setContent( conteudo.toString(), contentType );
			m2.setContent( convertPlain(conteudo.toString()), "text/plain" );

			Multipart mp = new MimeMultipart();
			mp.addBodyPart( m2 );
			mp.addBodyPart( m1 );

			MimeMultipart mm = new MimeMultipart("alternative");
			mm.addBodyPart( m2 );
			mm.addBodyPart( m1 );

			mimeMessage.setContent( mm );

			if(usr != null) {
				Transport.send(mimeMessage);
			} else {
				Transport.send(mimeMessage);
			}
			mimeMessage = null;
		} catch(AddressException e) {
			throw new ExceptionWarning("SendMail.send fail - " + ADRESS_EXCEPTION, e);
		} catch(MessagingException e2) {
			throw new ExceptionWarning("SendMail.send fail - " + MESSAGING_EXCEPTION, e2);
		} catch(Exception e3) {
			throw new ExceptionWarning("SendMail.send fail - ", e3);
		}
	}

    protected static String convertPlain( String s ) {
        try {
          s = s.replaceAll("(?sim)<br><br>", "<br>");

          s = s.replaceAll("\r","");
          s = s.replaceAll("\n","");

          try {
              s = s.substring( s.indexOf("<font face=\"Verdana\" size=\"1\" color=\"#ffffff\">"), s.length() );
          } catch (Exception e2) {
          }

          s = s.replaceAll("(?sim)<script.*?</script>","");
          s = s.replaceAll("(?sim)<(/|)form.*?>","");
          s = s.replaceAll("(?sim)<(/|)input.*?>","");
          s = s.replaceAll("(?sim)<(/|)img.*?>","");
          s = s.replaceAll("(?sim)&nbsp;","");
          s = s.replaceAll("Topo","");

          s = s.replaceAll("(?sim)<br>","\n\r");
          s = s.replaceAll("(?sim)<tr.*?>","\n\r");
          s = s.replaceAll("(?sim)<td.*?>"," ");
          s = s.replaceAll("(?sim)<p.*?>","");
          s = s.replaceAll("(?sim)<.*?>","");

        } catch (Exception e) {
            e.printStackTrace();
            s = "";
        }

        return s;
    }
    
    protected static String getPageHTML(CommandWrapper wrapper) throws ExceptionWarning {
		try {
			return RendererXSLtoHTML.getInstance().transform(wrapper.getSID(), wrapper.getXMLData().getClone(), wrapper.getNextPage());
		} catch (Exception e) {
			throw new ExceptionWarning(e);
		}
	}
}