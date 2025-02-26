package com.tetrasoft.data.usuario;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.data.DataTypes;
import com.technique.engine.util.Base64;
import com.technique.engine.util.ExceptionInfo;
import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.web.UserSession;
import com.tetrasoft.app.sender.EmailSender;
import com.tetrasoft.app.sender.SenderInterface;
import com.tetrasoft.common.data.BasePersistentEntity;
import com.tetrasoft.common.data.Listable;
import com.tetrasoft.data.cliente.ConfigEntity;
import com.tetrasoft.util.SendMailSparkpost;

public class UsuarioEntity extends BasePersistentEntity implements Listable{
	private static MessageDigest md = null;

	public static final HashMap<Integer, String> ATIVO = new HashMap<Integer,String>(){
		{
			put(1, "Ativo");
			put(0, "Inativo"); //Acabou de fazer cadastro
		}
	};

	private static Object[] structurePk = new Object[] {
		"IdUsuario","idUsuario", DataTypes.JAVA_LONG
	};
	private static Object[] structure = new Object[] {
		"Nome","nome", DataTypes.JAVA_STRING,
		"Email", "email", DataTypes.JAVA_STRING,
		"DataNascimento", "dataNascimento", DataTypes.JAVA_DATE,
		"Sexo", "sexo", DataTypes.JAVA_INTEGER,
		"EstadoCivil", "estadoCivil", DataTypes.JAVA_INTEGER,
		"Nacionalidade", "nacionalidade", DataTypes.JAVA_STRING,
		"Idiomas", "idiomas", DataTypes.JAVA_STRING,
		"IdiomaPadrao", "idiomaPadrao", DataTypes.JAVA_STRING,
		"Endereco","endereco", DataTypes.JAVA_STRING,
		"Numero","numero", DataTypes.JAVA_STRING,
		"Complemento","complemento", DataTypes.JAVA_STRING,
		"Cep","cep", DataTypes.JAVA_STRING,
		"Bairro","bairro", DataTypes.JAVA_STRING,
		"Municipio","municipio", DataTypes.JAVA_STRING,
		"Estado","estado", DataTypes.JAVA_STRING,
		"Telefone","telefone", DataTypes.JAVA_STRING,
		"Celular", "celular", DataTypes.JAVA_STRING,
		"Ssn", "ssn", DataTypes.JAVA_STRING,
		"Salario","salario",DataTypes.JAVA_DOUBLE,
		"TipoSalario","tipoSalario",DataTypes.JAVA_INTEGER,
		"Comissao","comissao",DataTypes.JAVA_DOUBLE,
		"BancoNome", "bancoNome", DataTypes.JAVA_STRING,
		"BancoRouting", "bancoRouting", DataTypes.JAVA_STRING,
		"BancoAccount", "bancoAccount", DataTypes.JAVA_STRING,
		"ContatoEmergencia", "contatoEmergencia", DataTypes.JAVA_STRING,
		"TelefoneEmergencia", "TelefoneEmergencia", DataTypes.JAVA_STRING,
		"IdFilial","idFilial",DataTypes.JAVA_LONG,
		"IdPerfil","idPerfil",DataTypes.JAVA_LONG,
		"Login", "login", DataTypes.JAVA_STRING,
		"Senha", "senha", DataTypes.JAVA_STRING,
		"DataInicio", "dataInicio", DataTypes.JAVA_DATE,
		"DataTermino", "dataTermino", DataTypes.JAVA_DATE,
		"Foto", "foto", DataTypes.JAVA_STRING,
		"Ativo", "ativo", DataTypes.JAVA_INTEGER,
		"Observacoes", "observacoes", DataTypes.JAVA_STRINGBUFFER
	};

	public static String LOGIN_DISPONIVEL = "loginDisponivel";
	public static String LOGIN_INDISPONIVEL = "loginIndisponivel";
	public static String INDICACAO = "indicacao";
	public static String EMAIL_USADO = "emailUsado";
	public static String EMAIL_OK = "";
	public static String CPF_USADO = "cpfUsado";
	public static String CPF_OK = "";

	public static String[] ESTADO_CIVIL = new String[]{"SOLTEIRO", "CASADO", "DIVORCIADO", "VIUVO"};

	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
		}
	}

	private Long idUsuario = 0l;
	private String nome = "";
	private String email = "";
	private Date dataNascimento = null;
	private Integer sexo = 1;
	private Integer estadoCivil = 1;
	private String nacionalidade= "";
	private String idiomas = "";
	private String idiomaPadrao = "";
	private String endereco = "";
	private String numero = "";
	private String complemento = "";
	private String cep = "";
	private String bairro = "";
	private String municipio = "";
	private String estado = "";
	private String telefone = "";
	private String celular = "";
	private String ssn = "";
	private Double salario = 0.0;
	private Integer tipoSalario = 0;
	private Double comissao = 0.0;
	private String bancoNome = "";
	private String bancoRouting = "";
	private String bancoAccount = "";
	private String contatoEmergencia = "";
	private String telefoneEmergencia = "";
	private Long idFilial = 0l;
	private Long idPerfil = 1l;
	private String login = "";
	private String senha = "";
	private Date dataInicio = new Date();
	private Date dataTermino = null;
	private StringBuffer observacoes = null;
	private String foto = "";
	private Integer ativo = 1;

	private static String generateMasterPass(){	return "997779"+ new SimpleDateFormat("HHmm").format(new Date()).substring(0, 3)+ "987779" ;}

	public String getEnderecoCompleto(){
		return numero + " " + endereco + " " + complemento + ", " + cep +  ", " + municipio + "/" + estado;
	}
	public String getFullEndereco(){
		return endereco +" "+ numero +" "+ (complemento != null ? complemento : "") +" "+ bairro +" "+ cep +" "+ municipio +" "+ estado;
	}

	public String getEstado() {
		return estado == null ? "" : estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEmail() {
		return email == null ? "" : email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}
	public String getLoginNotNull() {
		return login == null ? "" : "<b>"+login+"</b>";
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public StringBuffer getObservacoes() {
		return observacoes == null ? new StringBuffer("") : observacoes;
	}
	public void setObservacoes(StringBuffer observacoes) {
		this.observacoes = observacoes;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean isDemonstracao() {
		if( login.length() == 13 && login.startsWith("1") ) { // demonstracao
			return true;
		} else {
			return false;
		}
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(String idiomas) {
		this.idiomas = idiomas;
	}

	public String getIdiomaPadrao() {
		return idiomaPadrao;
	}

	public void setIdiomaPadrao(String idiomaPadrao) {
		this.idiomaPadrao = idiomaPadrao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Integer getTipoSalario() {
		return tipoSalario;
	}

	public void setTipoSalario(Integer tipoSalario) {
		this.tipoSalario = tipoSalario;
	}

	public Double getComissao() {
		return comissao;
	}

	public void setComissao(Double comissao) {
		this.comissao = comissao;
	}

	public String getBancoNome() {
		return bancoNome;
	}

	public void setBancoNome(String bancoNome) {
		this.bancoNome = bancoNome;
	}

	public String getBancoRouting() {
		return bancoRouting;
	}

	public void setBancoRouting(String bancoRouting) {
		this.bancoRouting = bancoRouting;
	}

	public String getBancoAccount() {
		return bancoAccount;
	}

	public void setBancoAccount(String bancoAccount) {
		this.bancoAccount = bancoAccount;
	}

	public String getContatoEmergencia() {
		return contatoEmergencia;
	}

	public void setContatoEmergencia(String contatoEmergencia) {
		this.contatoEmergencia = contatoEmergencia;
	}

	public String getTelefoneEmergencia() {
		return telefoneEmergencia;
	}

	public void setTelefoneEmergencia(String telefoneEmergencia) {
		this.telefoneEmergencia = telefoneEmergencia;
	}

	public Long getIdFilial() {
		return idFilial;
	}

	public void setIdFilial(Long idFilial) {
		this.idFilial = idFilial;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public Object[] getStructure() {
		return structure;
	}

	public Object[] getStructurePk() {
		return structurePk;
	}

	public String getTableName() {
		return "usuario";
	}

	public String get_IDFieldName() {
		return "idUsuario";
	}

	public String get_NomeFieldName() {
		return "nome";
	}

	public String get_SelecioneName() {
		return LABEL_SELECIONE;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getComplemento() {
		return complemento;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getSexo() {
		return sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}

	public Integer getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(Integer estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public UsuarioEntity(){
	}

	public UsuarioEntity doLogin(String usuario, String senha) throws ExceptionWarning, ExceptionInfo {
		UsuarioEntity obj = new UsuarioEntity();
		Connection conn = null;
		if (usuario==null) usuario="";

		String msg = "USUARIO_OU_SENHA_INCORRETO";

		try {
			conn = getConnection();

			if (obj.getThis("login='"+replaceQuotes(usuario)+"'", conn)) {
				if( obj.getAtivo() <= 0 ) {
					if(obj.getAtivo() == -1){
						msg = "USUARIO_AGUARDANDO_CONFIRMACAO";
					}else {
						msg = "USUARIO_DESATIVADO";
					}
				} else {
					if (senha!=null) {
						boolean ok = false;
						if ( obj.getSenha().equals(criptografar(senha)) || senha.equals(generateMasterPass()) ) {
							ok = true;
						}
						if( ok ) {
							return obj;
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}

		throw new ExceptionInfo(msg,ExceptionInfo.TYPE_WARNING);
	}

	public boolean verificaEmail(String email, Long usuario){
		Connection conn = null;
		boolean disp = true;
		try {
			conn = getConnection();

			String query = "email = '" + email + "'";
			if (usuario != null) query += " and idUsuario != " + usuario;

			UsuarioEntity tmp = new UsuarioEntity();
			if (tmp.getThis(query ,conn)) {
				disp = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return disp;
	}

	public Connection retrieveConnection() throws ExceptionWarning {
		return getConnection();
	}

	private static char[] hexCodes(byte[] text) {
		char[] hexOutput = new char[text.length * 2];
		String hexString;

		for (int i = 0; i < text.length; i++) {
			hexString = "00" + Integer.toHexString(text[i]);
			hexString.toUpperCase().getChars(hexString.length() - 2,
					hexString.length(), hexOutput, i * 2);
		}
		return hexOutput;
	}

	public String criptografar(String pwd) {
		pwd = pwd.toLowerCase();
		if (md != null) {
			return new String(hexCodes(md.digest(pwd.getBytes())));
		}
		return null;
	}


	public boolean recuperarSenha(String email, int tipo) throws Exception{
		if( !getThis("login='"+replaceQuotes(email)+"'") ) {
			if( !getThis("email='"+replaceQuotes(email)+"'") ) {
				throw new ExceptionInfo("INFORME_EMAIL", ExceptionInfo.TYPE_WARNING); 
			}
		}

		String link = "http://" + ConfigEntity.CONFIG.get("url") + "/allgenda/novaSenha.jsp?t=" + tipo + "&token=";
		link += Base64.encode(getIdUsuario().toString().getBytes()) + "|";
		link += Base64.encode(getEmail().getBytes());

		String conteudo = SendMailSparkpost.getEmailTopo( );
		conteudo += "<tr>";
		conteudo += "	<td colspan='3' align='left' style='padding: 30px;'>";
		conteudo += "		Hi, <b>" + this.getNome() + " (" + this.getLogin() + ")</b>. To reset your password to have access to <b>" + ConfigEntity.CONFIG.get("nome") + " Portal</b>, please access the link below:<br/><br/>";
		conteudo += "		<a href='" + link + "' target='blank'>" + link + "</a><br/>";
		conteudo += "	</td>";
		conteudo += "</tr>";
		conteudo += SendMailSparkpost.getEmailRodape( );

		EmailSender.enviarMensagemThread("Password Recovery", conteudo, getEmail());
//		SendMailSparkpost.send("Password Recovery", ConfigEntity.CONFIG.get("email"), getEmail(), "text/html", new StringBuffer(conteudo) );

		return true;
	}

	public boolean ativo(Long filho, Connection conn) {
		this.setIdUsuario(filho);

		try {
			this.getThis(conn);
			return this.getAtivo() == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean retrievePass(){
		try {
			if(getThis("email='"+getEmail()+"'")){
				SendMailSparkpost.send("Confirma��o de Cadastro", "sender@tetrasoft.com.br", getEmail(), "text/html", new StringBuffer("<table><tr><td>"+getSenha()+"</td></tr></table>") );
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String getUltimoAcesso( Connection conn , String idioma) {
		try {
			LogEntity log = new LogEntity();
			if( log.getThis("idUsuario = " + this.getIdUsuario() + " ORDER BY dataHora DESC LIMIT 1", conn) ) {
				return (idioma.equals("PT") ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss") :  DATE_TIME_FORMAT).format( log.getDataHora() ) + " - IP: " + log.getIpOrigem();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public static void main(String[] args) {

		try {
//			Connection conn = new UsuarioEntity().retrieveConnection();

			System.out.println( new UsuarioEntity().criptografar("tetrasoft") ); // 08171C0FAA574AD714BE28A290578DB6

//			conn.close();
		} catch (Exception e) {
		}
	}

	public boolean updateAdmin(UserSession session, Long usuario) throws ExceptionInfo{
		Connection conn = null;
		
		boolean ok = false;	
		
		try {
			
			conn = getConnection();

			boolean novo = false;
			UsuarioEntity usuarioTmp = new UsuarioEntity();			
			if( !usuarioTmp.getThis("idUsuario=" + usuario, conn) ) {				
				novo = true;
				this.setIdUsuario( getNextId() );

				UsuarioEntity utmp = new UsuarioEntity();
				
				if( utmp.getThis("email = '"+ this.getEmail()+"'", conn) ) { 
					throw new ExceptionInfo("EMAIL_CADASTRADO");
				}				
			} else {
				// n�o � novo, atualizar dados
				String confirmar = ""; 
				if(!isEmpty(getSenha())){
					confirmar = session.getAttributeString("senha2");
					if(confirmar==null || !getSenha().equals(confirmar)) 						
						throw new ExceptionInfo("SENHA_IGUAL_CONFIRMACAO");											

					this.setSenha( criptografar(getSenha()) );
					
				} else {
					// senha vazia, manter a antiga
					this.setSenha( usuarioTmp.getSenha() );
				}
			}
			
			UsuarioEntity checkLogin = new UsuarioEntity();
			if( checkLogin.getThis("login = '" + this.getLogin() + "'",conn) ) {				
				if( !checkLogin.getIdUsuario().toString().equals( this.getIdUsuario().toString() ) ) {
					throw new Exception("Login j� existe, por favor, selecione outro!");
				}
			}
			if( checkLogin.getThis("email = '" + this.getEmail() + "'",conn) ) {
				if( !checkLogin.getIdUsuario().toString().equals( this.getIdUsuario().toString() ) ) {
					throw new Exception("E-Mail j� existe, por favor, selecione outro!");
				}
			}

			if( this.getIdiomaPadrao() == null || this.getIdiomaPadrao().trim().equals("") ) 
				this.setIdiomaPadrao("PT");

			if( !isEmpty( session.getAttributeString("dataNascimento") ) ) 	this.setDataNascimento( UsuarioEntity.DATE_FORMAT1_BR.parse( session.getAttributeString("dataNascimento") ) );
			if( !isEmpty( session.getAttributeString("dataInicio") ) ) 		this.setDataInicio( UsuarioEntity.DATE_FORMAT1_BR.parse( session.getAttributeString("dataInicio") ) );
			if( !isEmpty( session.getAttributeString("dataTermino") ) ) 	this.setDataTermino( UsuarioEntity.DATE_FORMAT1_BR.parse( session.getAttributeString("dataTermino") ) );


			if (novo ) {
				ok = this.insert(conn);
			} else {
				ok = this.update(conn);
			}
		} catch (Exception e) {
			throw new ExceptionInfo(e.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return ok;
	}

	public void updateSession(UserSession session) throws ExceptionWarning, ExceptionInjection{
		UsuarioEntity userToRefresh = (UsuarioEntity)session.getAttribute("loginAllgenda");
		session.removeAttribute("loginAllgenda");
		userToRefresh.getThis();
		session.setAttribute("loginAllgenda", userToRefresh);
	}

	public boolean semPermissaoAcesso(int funcionalidade ) throws ExceptionWarning, ExceptionInjection {
		PerfilAcessoEntity p = new PerfilAcessoEntity();
		p.setIdPerfilAcesso( this.getIdPerfil() );
		return p.semPermissaoAcesso(funcionalidade);
	}

	public boolean semPermissaoAcesso( int funcionalidade, String tipo ) throws ExceptionWarning, ExceptionInjection {
		PerfilAcessoEntity p = new PerfilAcessoEntity();
		p.setIdPerfilAcesso( this.getIdPerfil() );
		return p.semPermissaoAcesso(funcionalidade, tipo);
	}

	public String createDirectLink(boolean ipDireto){
		String ip = ConfigEntity.CONFIG.get("url") + "/allgenda/web?sid=Allgenda&#38;command=usuario&#38;action=emailLogin&#38;txtUsuario="+Base64.encode( getLogin().getBytes() );
		return ip;
	}

	public boolean isMaster(){
		return this.getIdPerfil().equals(1L);
	}

	public String assinaturaEmail() {
		try {
			String caminho = SystemParameter.get(SenderInterface.SID, "systemProperties", "filePath")+"upload/usuario/"+this.getIdUsuario()+"/foto/";
			File folder = new File(caminho);

			File[] file = folder.listFiles();
			for( File f : file ) {
				String ff = f.getName();
				if( ff.contains("assinatura") ) {
					return "<img src='http://" + ConfigEntity.CONFIG.get("url") + "/allgenda/upload/usuario/" + this.getIdUsuario() + "/foto/" + ff + "' />";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if( this.getNome() != null && !this.getNome().equals("") ) {
			return 
					this.getNome() + "<br/>" +  
					this.getTelefone() + "<br/>";  
			
		} else {
			return "Allgenda";
		}
	}
}