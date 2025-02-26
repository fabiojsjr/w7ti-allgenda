package com.tetrasoft.data.cliente;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import com.technique.engine.data.DataTypes;
import com.technique.engine.web.UserSession;
import com.tetrasoft.app.sender.EmailSender;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;
import com.tetrasoft.data.usuario.UsuarioEntity;
import com.tetrasoft.util.IPLocator;
import com.tetrasoft.util.SendMailSparkpost;

public class MensagemEntity extends BasePersistentEntity implements Listable {
	public static int TIPO_CORREIO  = 1;
	public static int TIPO_LEAD 	= 2;
	
	private static Object[] structurePk = new Object[] {
		"IdMensagem","idMensagem", DataTypes.JAVA_LONG,
	};
	private static Object[] structure = new Object[] {
		"IdRemetente","idRemetente", DataTypes.JAVA_LONG,
		"IdDestinatario","idDestinatario", DataTypes.JAVA_LONG,
		"Tipo","tipo", DataTypes.JAVA_INTEGER,
		"DataHora","dataHora", DataTypes.JAVA_DATETIME,
		"Assunto","assunto", DataTypes.JAVA_STRING,
		"Mensagem","mensagem", DataTypes.JAVA_STRING,
		"Status","status", DataTypes.JAVA_INTEGER
	};

	private Long idMensagem;
	private Long idRemetente;
	private Long idDestinatario;
	private Date dataHora = new Date();
	private String assunto;
	private String mensagem;
	private Integer tipo = TIPO_CORREIO;
	private Integer status;

	public Object[] getStructurePk() {
		return structurePk;
	}

	public Object[] getStructure() {
		return structure;
	}

	public String getTableName() {
		return "mensagem";
	}

	public String get_IDFieldName() {
		return "idMensagem";
	}

	public String get_NomeFieldName() {
		return "assunto";
	}

	public String get_SelecioneName() {
		return LABEL_SELECIONE;
	}

	public Long getIdMensagem() {
		return idMensagem;
	}

	public void setIdMensagem(Long idMensagem) {
		this.idMensagem = idMensagem;
	}

	public Long getIdRemetente() {
		return idRemetente;
	}

	public void setIdRemetente(Long idRemetente) {
		this.idRemetente = idRemetente;
	}

	public Long getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(Long idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemetente( Connection conn ) {
		UsuarioEntity user = new UsuarioEntity();
		user.setIdUsuario( this.getIdRemetente() );
		try {
			if( user.getThis(conn) ) {
				return user.getNome();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Sistema";
	}

	// mensagem direta para alguém especéfico
	public void enviar( long idRemetente, long idDestinatario, String assunto, String mensagem, int tipo, Connection conn ) {
		UsuarioEntity usuarioDestino = new UsuarioEntity();
		
		try {
			usuarioDestino.getThis("idUsuario="+ idDestinatario, conn);
			
			this.setDataHora( new Date() );
			this.setIdRemetente( idRemetente );
			this.setAssunto( assunto );
			this.setMensagem( mensagem );
			this.setStatus(0);
			this.setTipo(tipo);
			this.setIdMensagem( getNextId() );
			this.setIdDestinatario( idDestinatario );
			this.insert(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// mensagem geral, pelo painel de "mensagens" do topo do portal
	@SuppressWarnings("unchecked")
	public boolean enviar( long remetente, int tipo, UserSession session, String value ) {
		value = value.replaceAll("%20", " ");
		
		String idDestinatario = value.substring( value.indexOf("PARA=")+5, value.indexOf("&", value.indexOf("PARA=") ) );
		String assunto		  = value.substring( value.indexOf("ASSUNTO=")+8, value.indexOf("&", value.indexOf("ASSUNTO=") ) );
		String filtro		  = value.substring( value.indexOf("TIPO=")+5, value.indexOf("&", value.indexOf("TIPO=") ) );
		String emailsExternos = value.substring( value.indexOf("emails=")+7, value.indexOf("&", value.indexOf("emails=") ) );
		String nomesExternos  = value.substring( value.indexOf("names=")+6, value.indexOf("&", value.indexOf("names=") ) );
		String mensagem		  = value.substring( value.indexOf("elm1=")+5, value.indexOf("&", value.indexOf("elm1=") ) );
//		String mensagem		  = value.substring( value.indexOf("MENSAGEM2=")+10, value.indexOf("&", value.indexOf("MENSAGEM2=") ) );
//		String template		  = value.substring( value.indexOf("TEMPLATE=")+9, value.indexOf("&", value.indexOf("TEMPLATE=") ) );


//		assunto  = assunto.replaceAll("%20", " ");
//		mensagem = mensagem.replaceAll("%20", " ");
		
		UsuarioEntity usuarioOrigem  = new UsuarioEntity();
		UsuarioEntity usuarioDestino = new UsuarioEntity();
		
		Connection conn = null;
		try {
			conn = getConnection();

			if( !usuarioOrigem.getThis("idUsuario="+ remetente, conn) )
				return false;
			
			if( !mensagem.contains("width='640'") && !mensagem.contains("640px") ) {
				mensagem = 
					"	<center>" +
					"		<table width='640'>" +
					"			<tr><td align='left' style='font-family: Arial'><br/>" +
									mensagem +
					"			</td></tr>" + 
					"		</table>" + 
					"	</center>"
				;
			}
			

			String html = SendMailSparkpost.getEmailTopo();
//			html += "<tr>";
//			html += "	<td align='center'>";
			html += 		mensagem;
//			html += "	</td>";
//			html += "</tr>";
			html += SendMailSparkpost.getEmailRodape();

			this.setDataHora( new Date() );
			this.setIdRemetente( remetente );
			this.setAssunto( assunto );
			this.setMensagem( mensagem );
			this.setStatus(0);
			this.setTipo(tipo);
			this.setIdMensagem( getNextId() );
			
			if( idDestinatario.equals("-1") ) { // TODOS
				UsuarioEntity usuario = new UsuarioEntity();
				ArrayList<UsuarioEntity> usuarios = new ArrayList<UsuarioEntity>();
				if( filtro.equals("1") ) {
					usuarios = usuario.getArray("ativo = " + UsuarioEntity.STATUS_ATIVO + " ORDER BY nome", conn);			
					
				} else if( filtro.equals("2") ) {
					usuarios = usuario.getArray("ativo = " + UsuarioEntity.STATUS_ATIVO + " ORDER BY nome", conn);
					
				} else if( filtro.equals("8") ) { // mailing
					emailsExternos = new MailingEntity().getEmailsExternos(conn);
					nomesExternos  = new MailingEntity().getNomesExternos(conn);
					filtro = "-1";

				} else if( filtro.equals("-1") ) {
					// emails em massa
				}
				
				if( emailsExternos != null && !emailsExternos.equals("") ) {
//					UsuarioEntity user  = new UsuarioEntity();
					StringTokenizer st  = new StringTokenizer( emailsExternos, ";" );
					StringTokenizer stN = new StringTokenizer( nomesExternos, ";" );
					while( st.hasMoreTokens() ) {
						try {
//							String email = st.nextToken();
							String nome  = "";
							try {
								nome = stN.nextToken();
								nome = nome.trim().replaceAll("", "");
							} catch (Exception e) {
								nome = "";
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				for( int i = 0; i < usuarios.size(); i++ ){
					usuarioDestino = usuarios.get(i);
					
					this.setIdMensagem( getNextId() );
					this.setIdDestinatario( usuarioDestino.getIdUsuario() );
					this.insert(conn);
				}
				
			} else { // envio individual
				usuarioDestino.setIdUsuario( new Long(idDestinatario) );
				if( !usuarioDestino.getThis(conn) ) {
					return false;
				}
				
				this.setIdDestinatario( new Long(idDestinatario) );
				this.insert(conn);
				
				EmailSender.enviarMensagemThread(assunto, html, usuarioDestino.getEmail());
			}

			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
			}
		}
	}
	
	public void handleForm( String tipo, String name, String email, String subject, String message, String from, String to, String date, String phone, String idioma, String ip ) {
		Connection conn = null;
		
		try {
			conn = getConnection();
			
			if( message == null ) message = "";
			message = message.replaceAll("\n", "<br/>");
			
			// envio de mensagem por email para Allgenda
			String html = SendMailSparkpost.getEmailTopo();
			html += "<tr>";
			html += "	<td height='10' colspan='3'>";
			html += "		<div style='margin-left: 50px; padding: 15px;'>Olé, hé uma nova mensagem recebida pelo site: <br/> ";
//			html +=		    "<br/><br/>Remetente: <b>" + cliente.getNome() + "</b>";
//			html +=		    "<br/><br/>E-Mail: <b>" + cliente.getEmail() + "</b>";
//			html += "<br/><br/>Mensagem / dévida: <b>" + contato.getMensagem() + "</b><br><hr><br>";
			
			String mapa = new IPLocator().getIPMap(ip);
			if( mapa.contains("<img") ) {
				html += "<br/><br/>IP do contato: <b>" + ip + "</b>";
				html += "<br/><br/>Origem do contato: <b>" + mapa + "</b><br><br>";
			}
			
			
			html += "	</td>";
			html += "</tr>";
			html += SendMailSparkpost.getEmailRodape();  

			String[] emails = new String[]{"fabiojsjr@gmail.com"};
			
			EmailSender.enviarMensagemThread("Contato via site", html, emails);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
			}
		}
	}
	
	public static void main(String[] args) {
		new MensagemEntity().handleForm("quote", "Renato Filipov", "fabiojsjr@gmail.com", "TESTE", "TESTE", "Séo Paulo", "Miami", "01/01/2018", "123-456-7890", "PT", "108.240.7.71");
	}
} 