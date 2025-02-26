package com.tetrasoft.data.cliente;

import java.sql.Connection;

import com.tetrasoft.app.sender.EmailSender;
import com.tetrasoft.data.usuario.UsuarioEntity;
import com.tetrasoft.util.SendMailSparkpost;

public class EmailEntity {
	public void emailBoasVindas(UsuarioEntity entity) {
		Connection conn = null;
		try {
			conn = new UsuarioEntity().retrieveConnection();
			
			emailBoasVindas(entity, conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
			}
		}		
	}
	public void emailBoasVindas(UsuarioEntity cliente, Connection conn) {
		emailBoasVindasPT(cliente, conn);
	}
	public void emailBoasVindasPT(UsuarioEntity cliente, Connection conn) {
		try {
			String conteudo = SendMailSparkpost.getEmailTopo();
			
			conteudo += "<tr>" + 
					"	" +
					"	<td align='left'>" +
					"	<center>" +
					"		<table width='640'>" +
					"			<tr><td align='left' style='font-family: Arial'><br/>" +
					"					Ol�, <b>" + cliente.getNome() + "</b>, ";
			
			String sexo = "";
			if( cliente.getSexo().equals("F") )	sexo = "a";
			else 								sexo = "o";
			
//			conteudo += sexo;
			
			conteudo +=  
				"<br/><br/>obrigado por escolher o <b>Allgenda</b>."+  
				"<br/><br/>"+ 
				"Nossa equipe est� revisando a sua mensagem e responderemos muito em breve. "+  
				"<br/><br/>" +
				"Qualquer d�vida ou esclarecimento, n�o deixe de nos procurar, estamos � total disposi��o!" +
				"<br/><br/>" +
				"Atenciosamente," +
				"<br/><br/><br/>" +
				"<i>Allgenda</i><br/><br/>";		
			
			conteudo +=	"		</td></tr></table></center>";
			conteudo +=	"		</td>"+
						"</tr>";
			
			conteudo += SendMailSparkpost.getEmailRodape();
			
			EmailSender.enviarMensagemThread("Seja bem vind" + sexo + " ao " + ConfigEntity.CONFIG.get("nome"), conteudo, cliente.getEmail());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}