package com.tetrasoft.data.cliente;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import com.technique.engine.data.DataTypes;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.data.usuario.UsuarioEntity;
import com.tetrasoft.util.SendMailSparkpost;

public class SuporteEntity extends BasePersistentEntity {
	private static Object[] structurePk = new Object[] {
    	"IdSuporte","idSuporte", DataTypes.JAVA_LONG,
    };
    private static Object[] structure = new Object[] {
    	"IdUsuario","idUsuario", DataTypes.JAVA_LONG,
    	"IdUsuarioResposta","idUsuarioResposta", DataTypes.JAVA_LONG,
    	"Email","email", DataTypes.JAVA_STRING,
    	"Nome", "nome", DataTypes.JAVA_STRING,
    	"Telefone","telefone", DataTypes.JAVA_STRING,
    	"Subcategoria", "subcategoria", DataTypes.JAVA_STRING,
    	"Assunto", "assunto", DataTypes.JAVA_STRING,
    	"Mensagem","mensagem", DataTypes.JAVA_STRING,
    	"Resposta","resposta", DataTypes.JAVA_STRING,
    	"DataMensagem","dataMensagem", DataTypes.JAVA_DATETIME,
    	"DataResposta","dataResposta", DataTypes.JAVA_DATETIME,
    	"UrlAnexo","urlAnexo", DataTypes.JAVA_STRING,
    	"UrlResposta","urlResposta", DataTypes.JAVA_STRING,
    	"Status","status", DataTypes.JAVA_LONG
    };
    public static final String EMAIL_SUPPORT = "ak.io@hotmail.com";
    public static final String TELEFONE_SUPPORT = "1100000000";

    public static String [] assuntos = new String []{
//    	"-- selecione --",
    	"SELECIONE",
//    	"Como Comprar",
    	"CATEGORIA_0",
//    	"Como ingressar na rede",
    	"CATEGORIA_1",
//    	"Sugest�es e elogios",
    	"CATEGORIA_2",
//    	"Pagamentos",
    	"CATEGORIA_3",
//    	"Entregas",
    	"CATEGORIA_4",
//    	"Propostas comerciais",
    	"CATEGORIA_5",
//    	"Rede Bin�ria/Posicionamento",
    	"CATEGORIA_6",
//    	"Cancelamento",
    	"CATEGORIA_7",
//    	"Altera��es cadastrais",
    	"CATEGORIA_8",
//    	"Reset de senha",
    	"CATEGORIA_9",
//    	"Agendamento de visitas",
    	"CATEGORIA_10",
//    	"Telefonia",
    	"CATEGORIA_11",
//    	"Suporte T�cnico",
    	"CATEGORIA_12"
    };
    public static String [] filtros = new String []{
    	"Exibir Todos",
    	"Exibir somente os chamados abertos",
    	"Exibir somente os chamados fechados",
    	"P39"
    };
    
    private Long idSuporte;
    private Long idUsuario;
    private Long idUsuarioResposta;
    private String email;
    private String nome;
    private String telefone;
    private String assunto;
    private String subcategoria;
    private String mensagem;
    private String resposta;
    private Date dataMensagem;
    private Date dataResposta;
    private String urlAnexo;
    private String urlResposta;
    private Long status;
    
    public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getSubcategoria() {
		return subcategoria;
	}
	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}
	public String getUrlResposta() {
		return urlResposta;
	}
	public void setUrlResposta(String urlResposta) {
		this.urlResposta = urlResposta;
	}
	public Long getIdUsuarioResposta() {
		return idUsuarioResposta;
	}
	public void setIdUsuarioResposta(Long idUsuarioResposta) {
		this.idUsuarioResposta = idUsuarioResposta;
	}
	public String getUrlAnexo() {
		return urlAnexo;
	}
	public void setUrlAnexo(String urlAnexo) {
		this.urlAnexo = urlAnexo;
	}
	public Long getIdSuporte() {
		return idSuporte;
	}
	public void setIdSuporte(Long idSuporte) {
		this.idSuporte = idSuporte;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public Date getDataMensagem() {
		return dataMensagem;
	}
	public void setDataMensagem(Date dataMensagem) {
		this.dataMensagem = dataMensagem;
	}
	public Date getDataResposta() {
		return dataResposta;
	}
	public void setDataResposta(Date dataResposta) {
		this.dataResposta = dataResposta;
	}

	public Object[] getStructure() {
		return structure;
	}
	public Object[] getStructurePk() {
		return structurePk;
	}
	public String getTableName() {
		return "suporte";
	}
	
	public void save(long usuario, String msg, String assunto) throws Exception {
		UsuarioEntity user = new UsuarioEntity();
		user.setIdUsuario(usuario);
		user.getThis();
		
		save( user.getEmail(), user.getTelefone().trim(), msg, assunto, user.getNome(), "undefined"); 
	}
	
	public void save(String email, String tel, String msg, String assunto, String nome, String anexo) throws Exception {		
		try {
			UsuarioEntity user = new UsuarioEntity();
			String query = null;
			if(anexo.equals("undefined"))anexo=null;
			if(!isEmpty(email)) query ="email = '"+email+"'";
			if(!user.getThis(query)) {
				user.setIdUsuario(0l);
				user.setNome(nome);
				user.setEmail(email);
				
			}
			SuporteEntity checkOS = new SuporteEntity();
			if(checkOS.getThis(query + " and assunto = " + assunto+" order by dataMensagem desc")&& checkOS.getAssunto().equals(assunto) && checkOS.getResposta() == null){
				throw new Exception("Aguarde a Resposta de seu �ltimo chamado.! ");
			}else{
				this.setIdSuporte( getNextId() );
				this.setIdUsuario( user.getIdUsuario() );
				this.setIdUsuarioResposta(0l);
				this.setEmail(user.getEmail());
				this.setTelefone(tel);
				this.setAssunto(assunto);
				this.setMensagem(msg);
				this.setDataMensagem( new Date() );
				this.setNome(user.getNome());
				if(anexo!=null)this.setUrlAnexo(anexo.substring(0,anexo.lastIndexOf("/")));
				this.setUrlResposta("");
				this.setSubcategoria(null);
				this.setStatus(1l);
				this.insert();
			}
			
			String conteudo = SendMailSparkpost.getEmailTopo();
			conteudo += "<tr>";
			conteudo += "	<td colspan='3' align='center'>";
			conteudo += "		Voc� abriu um chamado no suporte:<br/><br/>";
			conteudo += "		Protocolo: " + this.getIdSuporte() + "<br/><br/>";
			conteudo += "		Usu�rio: " + user.getNome() + "<br/><br/>";
			conteudo += "		E-mail: " + user.getEmail() + "<br/><br/>";
			conteudo += "		Telefone: " + tel + "<br/><br/>";
			conteudo += "		Assunto: " + assuntos[Integer.parseInt(assunto)] + "<br/><br/>";
			conteudo += "		Mensagem: " + msg + "<br/><br/>";
			conteudo += "	</td>";
			conteudo += "</tr>";
			conteudo += SendMailSparkpost.getEmailRodape();
			
			SendMailSparkpost.send("Suporte T�cnico", ConfigEntity.CONFIG.get("email"), email , "text/html", new StringBuffer(conteudo) );
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao enviar mensagem: " + e.getMessage() );
		}
	}
	
	public void saveSubcategoria(String id, String subcategoria) throws Exception {
		try {
			
			this.setIdSuporte(new Long(id));
			this.getThis();
			this.setSubcategoria(subcategoria);
			this.update();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao gravar subcategoria: " + e.getMessage() );
		}
	}
	
	public void save(String login, String tel, String msg, String nome) throws Exception {
		try {
			UsuarioEntity user = new UsuarioEntity();
			if( !user.getThis("email = '" + login + "'") ) {
				user.setIdUsuario(0l);
				user.setLogin("");
				user.setEmail(login);
				user.setNome(nome);
			}
			
			this.setIdSuporte( getNextId() );
			this.setIdUsuario( user.getIdUsuario() );
			this.setEmail(user.getEmail());
			this.setTelefone(tel);
			this.setMensagem(msg);
			this.setDataMensagem( new Date() );
			if(user.getIdUsuario().equals(0L)) this.setNome(user.getNome());
			this.insert();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao enviar mensagem: " + e.getMessage() );
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SuporteEntity> populateList(String data1, String data2, String filter, String assunto, String protocolo, int max, int min, Connection conn){
		ArrayList<SuporteEntity> retorno = null;
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(new Date());
			
			if(isEmpty(data2)) data2 = DATE_FORMAT.format(cal.getTime());
			if(isEmpty(data1)){
				cal.add(GregorianCalendar.DATE, -30);
				data1 = DATE_FORMAT.format(cal.getTime());
			}
			
			data1 += " 00:00:00";
			data2 += " 23:59:59";
			
			String query = " 1=1 ";
			if( filter.equals("1") ) query += " AND status = 1 ";
			else if( filter.equals("2") ) query += " AND status = 0 ";
			else if( filter.equals("3")) query += " AND subcategoria <> 'NULL' ";
			if( !isEmpty(assunto)) query += " AND assunto = " + assunto;
			if( !isEmpty(protocolo)) query += " AND idSuporte = " + protocolo;
			
			
			query += "   AND dataMensagem >= " + getJava2DBDateTime(DATE_TIME_FORMAT.parse(data1));
			query += "   AND dataMensagem <= " + getJava2DBDateTime(DATE_TIME_FORMAT.parse(data2));
			query += " ORDER BY dataMensagem DESC ";
			query += " limit "+ min + "," + max;
			
			retorno = getArray(query, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SuporteEntity> populateList(String data1, String data2, String filter, String assunto, String protocolo, Connection conn){
		ArrayList<SuporteEntity> retorno = null;
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(new Date());
			
			if(isEmpty(data2)) data2 = DATE_FORMAT.format(cal.getTime());
			if(isEmpty(data1)){
				cal.add(GregorianCalendar.DATE, -30);
				data1 = DATE_FORMAT.format(cal.getTime());
			}
			
			data1 += " 00:00:00";
			data2 += " 23:59:59";
			
			String query = " 1=1 ";
			if( filter.equals("1") ) query += " AND status = 1 ";
			else if( filter.equals("2") ) query += " AND status = 0 ";
			else if( filter.equals("3")) query += " AND subcategoria <> 'NULL' ";
			if( !isEmpty(assunto)) query += " AND assunto = " + assunto;
			if( !isEmpty(protocolo)) query += " AND idSuporte = " + protocolo;
			
			
			query += "   AND dataMensagem >= " + getJava2DBDateTime(DATE_TIME_FORMAT.parse(data1));
			query += "   AND dataMensagem <= " + getJava2DBDateTime(DATE_TIME_FORMAT.parse(data2));
			query += " ORDER BY dataMensagem DESC ";
			
			retorno = getArray(query, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	public void responder(String id, String resposta, String assunto, String idUsuarioResposta, String anexo, String tipoResposta, String respostaAntiga) throws Exception {
		try {
			if(anexo.equals("undefined"))anexo=null;
			
			SuporteEntity os = new SuporteEntity();
			os.setIdSuporte( new Long(id) );
			os.getThis();
			
			UsuarioEntity user = new UsuarioEntity();
			user.setIdUsuario( os.getIdUsuario() );
			user.getThis();
			
			resposta = resposta.replaceAll("\n", "<br>");
			resposta = resposta.replaceAll("\r", "<br>");
			
			os.setIdUsuarioResposta(new Long(idUsuarioResposta));
			if(anexo!=null)os.setUrlResposta(anexo.substring(0,anexo.lastIndexOf("/")));
			if(tipoResposta.equals("responder")){
				if(respostaAntiga.equals("")){
					os.setDataResposta( new Date() );
				}
				os.setResposta(resposta);
				os.setStatus(1l);
				os.update();
			}else{
				if(respostaAntiga.equals("")){
					os.setDataResposta( new Date() );
				}
				os.setResposta(resposta);
				os.setStatus(0l);
				os.update();
			}
			
			String conteudo = SendMailSparkpost.getEmailTopo();
			conteudo += "<tr>";
			conteudo += "	<td colspan='3' align='left'>";
			conteudo += "		Ol�, " + ((user.getNome() != null && !user.getEmail().equals("")) ? user.getNome() : os.getNome()) + ", voc� enviou a seguinte d�vida abaixo para nossa equipe de atendimento: " + os.getMensagem() + " <br/><hr/>";
			conteudo += "		E abaixo segue nossa resposta: <br/><br/><b> " + resposta.replaceAll("\n", "<br/>") + "</b><br/><hr/>";
			if(anexo!=null){
				conteudo += "		ANEXO: <br/><br/><b> <a href='http://" + ConfigEntity.CONFIG.get("url") + ""+ os.getUrlResposta() +"' target='_blank'>clique para abrir o anexo</a></b><br/><hr/>";
			}
			conteudo += "		Qualquer d�vida, estamos � disposi��o.";
			conteudo += "		<br/>";
			conteudo += "		Atenciosamente,";
			conteudo += "		<br/>";
			conteudo += "		<br/>";
			conteudo += "		Equipe " + ConfigEntity.CONFIG.get("nome");
			conteudo += "	</td>";
			conteudo += "</tr>";
			conteudo += SendMailSparkpost.getEmailRodape();
			
			if(tipoResposta.equals("responder")){
				SendMailSparkpost.send("Suporte T�cnico", ConfigEntity.CONFIG.get("email"), (user.getEmail()!=null && !user.getEmail().equals("")) ? user.getEmail() : os.getEmail(), "text/html", new StringBuffer(conteudo) );
			}else{
				SendMailSparkpost.send("Fechamento do Chamado - Suporte T�cnico", ConfigEntity.CONFIG.get("email"), (user.getEmail()!=null && !user.getEmail().equals("")) ? user.getEmail() : os.getEmail(), "text/html", new StringBuffer(conteudo) );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao enviar mensagem: " + e.getMessage() );
		}
	}

}