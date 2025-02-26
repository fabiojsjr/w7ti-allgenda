package com.tetrasoft.data.marketing;

import java.sql.Connection;
import java.util.Date;
import java.util.StringTokenizer;

import com.technique.engine.data.DataTypes;
import com.technique.engine.util.ExceptionWarning;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.data.usuario.UsuarioEntity;

public class MobileMailEntity extends BasePersistentEntity {
	
	private static Object[] structurePk = new Object[] {
		"IdTransacao","idTransacao", DataTypes.JAVA_LONG,
	};
	private static Object[] structure = new Object[] {
		"Login","login", DataTypes.JAVA_STRING,
		"Remetente","remetente", DataTypes.JAVA_STRING,
		"Destinatario","destinatario", DataTypes.JAVA_STRING,
		"DataHora","dataHora", DataTypes.JAVA_DATETIME
	};
	
	public MobileMailEntity() throws ExceptionWarning{
	}

	private Long idTransacao = null;
	private String login = null;
	private String remetente = null;
	private String destinatario = null;
	private Date dataHora = null;

	public Object[] getStructure() {
		return structure;
	}
	public Object[] getStructurePk() {
		return structurePk;
	}
	public String getTableName() {
		return "mobile_mail";
	}
	public Long getIdTransacao() {
		return idTransacao;
	}
	public void setIdTransacao(Long idTransacao) {
		this.idTransacao = idTransacao;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getRemetente() {
		return remetente;
	}
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	
	public void enviar( String str ) throws Exception {
		StringTokenizer st = new StringTokenizer( str, "|" );
		String login  = st.nextToken();
		String titulo = st.nextToken(); // nao usado
		String msg    = st.nextToken(); // nao usado
		
		UsuarioEntity usuario = new UsuarioEntity();
		
		if( usuario.getThis("login = '" + login + "'") ) {
			StringBuffer conteudo = new StringBuffer(
					"<html>" + 
					"<head>" +
					"	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>" +
					"	<link media='all' rel='stylesheet' type='text/css' href='http://www.tetrasoft.com.br/allgenda/site/css/general.css'>" +
					"	<link href='http://www.tetrasoft.com.br/allgenda/include/site.css' rel='stylesheet' type='text/css'>" +
					"</head>" +
					"<body>" +
					"	<div style='max-width: 600px !important margin-left: auto; margin-right: auto'>" +
					"   	<div class='index_internas' align='justify' style='max-width: 600px !important' >" +
					"    		<br><br>" +
					"	    	<img src='http://www.tetrasoft.com.br/allgenda/images/quemSomos/empresa_logo.jpg' class='empresas_logos' style='max-width: 600px !important' >" +
					"	    	<h3>Quem Somos</h3>" +
					"	    	<br>" +
					"			<p>A Tetrasoft � uma Companhia Global de Telecomunica��es que chegou ao Brasil para revolucionar o mercado, apresentando produtos de alta qualidade, com atendimento personalizado e marcas registradas da empresa em todo mundo, trazendo tecnologias inovadoras para �rg�os governamentais, empresas e consumidores finais. � capaz de entregar rapidamente solu��es em uma variedade de segmentos, estimular economias locais e encubar novas companhias.</p>" +
					"			<br>" +
					"			<p>Com matriz em Texas/USA e sedes na It�lia, Brasil, Inglaterra, Dubai (em breve no Canad� e no M�xico), a Tetrasoft est� presente em mais de 150 pa�ses, com servi�os de comunica��o (voz e interconex�es) e brevemente com fornecimento de internet, v�deo e telefonia celular com estrutura pr�pria de Data Center sediados nos EUA e no Brasil, com projeto recente de execu��o no Brasil.</p>" +
					"			<br>" +
					"			<p>A Tetrasoft � verticalmente integrada com capacidade de manufatura para consumidores de dispositivos, como tamb�m entrega de servi�os de Internet por meio de redes banda larga. � uma operadora devidamente licenciada no Brasil pela ANATEL e possuimos as licen�as MVNO no Brasil, Estados Unidos e Oriente M�dio.</p>" +
					"			<br>" +
					"			<p>Por meio de uma equipe de profissionais altamente qualificada, composta de consultores de telecomunica��es e operadores espalhados pelo mundo (EUA, Europa, Oriente M�dio e Am�rica do Sul), a Tetrasoft intenta melhorar os canais de comunica��o das pessoas mediante a busca constante de novas tecnologias, produtos, solu��es inovadoras e surpreendentes, no intuito de garantir 100% de satisfa��o dos nossos clientes, com servi�os incompar�veis <strong>24 horas</strong> por dia, <strong>7 dias</strong> da semana nos <strong>365 dias</strong> do ano.</p>" +
					"			<br>" +
					"			<p>Agora voc� pode fazer sua rede social trabalhar para voc�. Quanto mais voc� distribuir produtos inovadores da Tetrasoft, mais voc� cresce e mais voc� recebe de volta. <br>" +
					"			<br>" +
					"			<a href='http://" + usuario.getLogin() + ".tetrasoft.com.br'><strong>Registe-se e comece a ganhar hoje!</strong></a></p>" +
					"			<br>" +
					"	    </div>" +
					"	    <div class='clear20'></div>" +
					"		<div class='pusher'></div>" +
					"		<div class='company-bar'  style='height: auto !important'>" +
					"			<div class='company-holder'>" +
					"				<span id='other-company'>Somos associados com:</span>" +
					"				<div class='clear'></div>" +
					"				<img src='http://www.tetrasoft.com.br/allgenda/site/images/company.png' width='910' height='85' alt='Tetrasoft' style='max-width: 600px !important'>" +
					"			</div>" +
					"		</div>" +
					"	</div>" +
					"</body></html>"
			);
			
			Connection conn = null;
			try {
				conn = getConnection();
				
				if( usuario.getEmail() == null || usuario.getEmail().trim().equals("") ) {
					usuario.setEmail("fabiojsjr@gmail.com");
				}
				
				this.setLogin(login);
				this.setRemetente( usuario.getEmail() );
						
				int total = 0;
				while( st.hasMoreTokens() ) {
					String destino = st.nextToken();
					
					this.setIdTransacao( getNextId() );
					this.setDataHora( new Date() );
					this.setDestinatario( destino );
					this.insert(conn);
					
					System.out.println("EMAIL FAKE: " + (total++) + " - " + destino);
//					SendMailSparkpost.send("Venha para a Tetrasoft!!!", usuario.getEmail(), destino, "text/html", conteudo);
				}
			} catch (Exception e) {
			} finally {
				conn.close();
			}
		} else {
			throw new Exception("Login inv�lido!");
		}
	}
	
	public static void main(String[] args) {
		try {
			new MobileMailEntity().enviar("tetrasoft|fabiojsjr@gmail.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}