package com.tetrasoft.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.tetrasoft.util.SMTPAuthenticator;
import com.tetrasoft.util.SendMailSparkpost;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.web.CommandWrapper;
import com.technique.engine.web.RendererXSLtoHTML;
import com.tetrasoft.data.cliente.ConfigEntity;


public class SendMailSparkpost {
	public static String MAILER = "TechEngine";
    public static String ADRESS_EXCEPTION = "AddressException";
    public static String MESSAGING_EXCEPTION = "MessagingException";

    public static boolean changeSender = true;

    public SendMailSparkpost() {
    }
    public SendMailSparkpost( boolean change ) {
        changeSender = change;
    }

    public static StringBuffer enxugarEmail(StringBuffer msg) {
    	return new StringBuffer( msg.toString().replaceAll("(?sim)<script.*?</script>",""));
    }

    public static void send(String subject, String from, String to, String contentType, StringBuffer conteudo) throws ExceptionWarning {
		try {
			if( !to.contains("@") ) {
				return;
			}
			
			// DEIXANDO O CABEÇALHO E RODAPÉ PADRÃO
			StringBuffer cont2 = new StringBuffer() ;
			cont2.append(getEmailTopo());
			cont2.append(conteudo);
			cont2.append(getEmailRodape());
			
			conteudo =  cont2 ;
			
			conteudo = enxugarEmail(conteudo);

			String mailer 	= MAILER;
			String server 	= "smtp.sparkpostmail.com";
			String usr 		= "SMTP_Injection";
			String passwd 	= "0d87b10f34f577e58a4aff68bd23bc6579544a7a";
			
			Properties props = new Properties();
			props.put("mail.smtp.host", server);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.user", usr);
			props.put("mail.smtp.pass", passwd);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.localhost", server);
			props.put("mail.smtp.starttls.enable", "true");
			
			Session session = null;
			if(usr != null) {
//				if(passwd == null) passwd = "";

				props.put("mail.smtp.auth", "true");
				session = Session.getInstance(props, new SMTPAuthenticator(usr, passwd));
//			} else {
//				session = Session.getInstance(props, null);
			}

			MimeMessage mimeMessage = new MimeMessage(session);
			
			InternetAddress[] reply;

			if( from.contains("<") ) {
				String fromMail = from.substring(from.indexOf("<")+1, from.indexOf(">") );
				String fromNome = from.substring(0, from.indexOf("<") );
				
				reply = new InternetAddress[]{ new InternetAddress(fromMail) };
				from = "\"" + fromNome + "\" <" + fromMail + ">";
				
			} else {
				reply = new InternetAddress[]{ new InternetAddress(from) };
				String nome = from;
				try {
					nome = from.substring( 0, from.indexOf("@") );
				} catch (Exception e) {
				}
				
//				TODO: descomentar a linha abaixo SOMENTE se o domínio estiver cadastrado na lista de sender do SparkPost
//				from = "\"" + nome + "\" <" + ConfigEntity.CONFIG.get("email") + ">";
				nome = "Allgenda";
				from = "\"" + subject + "\" <sender@w7ti.com>"; 
			}

			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.setReplyTo(reply);

			javax.mail.Address toAddress = new InternetAddress(to);
			mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, toAddress);

			mimeMessage.setSubject( MimeUtility.encodeText(subject, "ISO-8859-1", "Q") );
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

			System.out.println("ALLGENDA SendMail SparkPost: " + to);
			
			try {
				Transport.send(mimeMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mimeMessage = null;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionWarning("SendMail.send fail - " + ADRESS_EXCEPTION, e);
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
    
	public static String getEmailTopo() {
		String conteudo = "";
				conteudo += "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:v='urn:schemas-microsoft-com:vml'>";
				conteudo += "	<head>";
				conteudo += "		<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1' />";
				conteudo += "		<meta http-equiv='X-UA-Compatible' content='IE=edge' />";
				conteudo += "		<link href='http://" + ConfigEntity.CONFIG.get("url") + "/allgenda/include/site.css' rel='stylesheet' type='text/css'/>";
				conteudo += "	</head>";
				conteudo += "	<body>";
				conteudo += "		<table cellspacing='0' ce1llpadding='0' width='600px' border='0' align='center'>";
				conteudo += "			<tr>";
				conteudo += "				<td height='60' colspan='3' bgcolor='#f0cc45' valign='middle' align='center'>";
//				conteudo += "					<img src='http://timesheet.grupoinpress.com.br/ip/images/logo_ip2.gif'/><br/>";
//				conteudo += "					<img src='http://" + ConfigEntity.CONFIG.get("url") + "/ip/images/logo_ip2.gif'/><br/>";
				conteudo += "					<img src='http://" + ConfigEntity.CONFIG.get("url") + "/allgenda/images/logo/logo.png'/><br/>";
				conteudo += "				</td>";
				conteudo += "			</tr>";
				conteudo += "			<tr>";
				conteudo += "				<td height='10' colspan='3'></td>";
				conteudo += "			</tr>";
		return conteudo;
	}
	
	public static String getEmailRodape() {
		String conteudo = "";

		conteudo += "			<br/>";
		conteudo += "			<br/>";
		conteudo += "			<br/>";
		conteudo += "			<br/>";
		conteudo += "			<tr>";
		conteudo += "				<td height='10' colspan='3'></td>";
		conteudo += "			</tr>";
		conteudo += "			<tr bgcolor='#CCCCCC'>";
		conteudo += "				<td height='30' color='#FFFFFF' colspan='3' align='center'>";
		conteudo += "					Esse é um email automático, por favor, não responda esse email!";
		conteudo += "				</td>";
		conteudo += "			</tr>";
//		conteudo += "			<tr>";
//		conteudo += "				<td height='10' colspan='3'></td>";
//		conteudo += "			</tr>";
//		conteudo += "			<tr>";
//		conteudo += "				<td height='40' colspan='1' bgcolor='#ffffff'  align='left' style='border-top:0px solid #e8e8e8'>";
//		conteudo += "					<br/>";
//		conteudo += "					<img src='http://" + ConfigEntity.CONFIG.get("url") + "/ip/images/logo_ip2.gif'/><br/>";
//		conteudo += "					&#160;&#160;&#160;&#160;&#160;";
//		conteudo += "				</td>";
//		conteudo += "				<td height='40' colspan='2' bgcolor='#ffffff' valign='middle' align='right' style='border-top:0px solid #e8e8e8'>";
//		conteudo += "					<a href='http://w7ti.com'>W7ti.com © Copyright - Direitos reservados</a>";
//		conteudo += "					<br/>";
//		conteudo += "					<img src='http://" + ConfigEntity.CONFIG.get("url") + "/ip/images/w7_logo2_trans_pequeno.png'/><br/>";
//		conteudo += "					&#160;&#160;&#160;&#160;&#160;";
//		conteudo += "				</td>";
//		conteudo += "			</tr>";
		conteudo += "		</table>";
		conteudo += "	</body>";
		conteudo += "</html>";

		return conteudo;
	}
	
	public static void enviarEmailPadrao( String email, String mensagem){
		try {
			String conteudo = "";
//			String conteudo = SendMailSparkpost.getEmailTopo();
			
			conteudo += "<tr>";
			conteudo += "	<td colspan='3' align='center'>" + mensagem + "</td>";
			conteudo += "</tr>";
			
//			conteudo += SendMailSparkpost.getEmailRodape();
			
			SendMailSparkpost.send("Allgenda - Mensagem Automática", ConfigEntity.CONFIG.get("email"), email, "text/html", new StringBuffer(conteudo) );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public static void main(String[] args) {
		try {
			String conteudo = "" ;
			
			conteudo += "<tr>";
			conteudo += "	<td colspan='3' align='left'>" +
					"			<b>Olá TESTE ALLGENDA, </b>" +
					"			<br/><br/>" +
					"			Seja muito bem vindo à  " + ConfigEntity.CONFIG.get("nome") + "." +
					"			<br/><br/>" +
					"			Essa é uma mensagem de teste. Estamos inteiramente à sua disposição." +
					"			<br/><br/>" +
					"			Atenciosamente," +
					"			<br/><br/><br/>" +
					"			Equipe  " + ConfigEntity.CONFIG.get("nome") +		
					"		</td>";
			conteudo += "</tr>";
			
			
			SendMailSparkpost.send("TESTE", "sistemas@w7ti.com", "fabiojsjr@gmail.com", "text/html", new StringBuffer(conteudo) );
		} catch (ExceptionWarning e) {
			e.printStackTrace();
		}
	}
    
	public static String lerHTML(String url) {
		try {
			System.out.println(url);
	        DefaultHttpClient httpclient = new DefaultHttpClient();

			HttpGet httpget1 = new HttpGet(url);
//			httpget1.setHeader("Host","nnex.com");
//			httpget1.setHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.0; pt-BR; rv:1.9.0.12) Gecko/2009070611 Firefox/3.0.12 (.NET CLR 3.5.30729)");
//			httpget1.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");		
//			httpget1.setHeader("Accept-Language","pt-br,pt;q=0.8,en-us;q=0.5,en;q=0.3");
//			httpget1.setHeader("Accept-Encoding","gzip, deflate");
//			httpget1.setHeader("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");
//			httpget1.setHeader("Connection","Keep-Alive");
//			httpget1.setHeader("Keep-Alive","300");
			
			HttpResponse response = httpclient.execute(httpget1);
			HttpEntity entity = response.getEntity();
			
//			if (entity != null) {
//				entity.consumeContent();
//			}
			
			return EntityUtils.toString(entity); 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}