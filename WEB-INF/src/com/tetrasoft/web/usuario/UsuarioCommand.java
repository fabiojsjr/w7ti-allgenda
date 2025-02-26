package com.tetrasoft.web.usuario;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.Base64;
import com.technique.engine.util.ExceptionAbstract;
import com.technique.engine.util.ExceptionInfo;
import com.technique.engine.web.CommandWrapper;
import com.technique.engine.web.UserSession;
import com.technique.engine.xml.XMLData;
import com.tetrasoft.common.web.SecurityInterface;
import com.tetrasoft.data.cliente.MensagemEntity;
import com.tetrasoft.data.cliente.MensagemTemplateEntity;
import com.tetrasoft.data.cliente.SuporteEntity;
import com.tetrasoft.data.usuario.LogEntity;
import com.tetrasoft.data.usuario.UsuarioEntity;
import com.tetrasoft.web.basico.CadastroBaseCommand;
import com.tetrasoft.web.basico.CadastroWrapper;

public class UsuarioCommand extends CadastroBaseCommand implements SecurityInterface {
	private static String LOGIN = "login";
	private static String LOGOUT = "logout";
	private static String ESQUECI = "esqueci";
	private static String ALTERAR_SENHA = "alterarSenha";
	private static String OS = "os";
	private static String OS_RESPOSTA = "osResposta";
	private static String OS_RESPOSTA_SUBCATEGORIA = "osRespostaSubcategoria";
	private static String DUVIDA = "duvida";
	private static String EMAIL_LOGIN = "emailLogin";

	private static String PAGE_INDEX = "index.jsp";

	private static String SAVE_DADOS_PESSOAIS_ADMIN = "salvarConfigDadosPessoaisAdmin";
	private static String INATIVAR_USUARIO = "inativarUsuario";
	
	private static String MENSAGEM_ENVIAR   			= "mensagemEnviar";
	private static String MENSAGEM_TEMPLATE 			= "mensagemTemplate";

	private static UsuarioCommand instance = new UsuarioCommand();

	public UsuarioCommand() {
	}

	public static UsuarioCommand getInstance() {
		return instance;
	}

	public CommandWrapper execute(UserSession session) throws ExceptionAbstract {
		CadastroWrapper cadWrapper = new CadastroWrapper();
		CommandWrapper wrapper = super.execute(session, cadWrapper);

		XMLData data = wrapper.getXMLData();
		String action = getAction(session);
		UsuarioEntity entity = new UsuarioEntity();

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
		data.addClosedTag("dataExtenso", sdf.format(new Date()));

		long usuario = 0l;
		try {
			usuario = getAuthorizedUser(session, wrapper).getIdUsuario().longValue();
		} catch (Exception e) {
		}

		if (action.equals(LOGOUT)) {
			doLogout(session);
			wrapper.setNextPage(PAGE_INDEX);

		} else if(action.equals(LOGIN)){
			String email 	= session.getAttributeString("emailLogin");
			String senha 	= session.getAttributeString("senhaLogin");
			Properties p = new Properties();
			try {
				
				if( email.trim().equals("") || senha.trim().equals("") ) throw new ExceptionInfo("USUARIO_OU_SENHA_EM_BRANCO");
				
				entity = entity.doLogin(email,senha);
				session.setAttribute(LOGIN_TAG,entity);
				
				String redirect = "";
				try {
					if( session.getAttributeString("idioma") == null ) {
						String idioma = entity.getIdiomaPadrao();
						if( idioma == null || idioma.equals("") ) idioma = "PT";
						
						if( !idioma.equals("PT") ) { // mudar idioma na entrada
							//redirect = "&redirect=changeIdiomaToEN";
							idioma = "PT";
						}
						
						session.setAttribute("idioma", idioma);
					}
					
					String pathFisico = SystemParameter.get("Allgenda", "systemProperties", "filePath");
					FileInputStream fis = new FileInputStream( pathFisico + "lang/" + session.getAttributeString("idioma") + "/geral.properties");
					p.load( fis );
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				new LogEntity( LogEntity.TIPO_SITE, entity.getIdUsuario(), entity.getTableName(), entity.get_IDFieldName(), session.getAttributeString("ip"), session.getAttributeString("ipServidor"),  entity.getIdUsuario(), "Usuario efetuou logon" );
				wrapper.setNextPage("resposta.jsp?action=logado" + redirect);
				
			} catch (ExceptionInfo e) {
				String msg = e.getMessage();
				wrapper.setNextPage("logon.jsp?warning=" +msg);
			}
		
			
		} else if (action.equals(ESQUECI)) {
			String email = session.getAttributeString("emailLogin");
			try {
				entity.recuperarSenha(email, 1);
				wrapper.setNextPage("resposta.jsp?msgClean=ESQUECI_SENHA_EMAIL");					

			} catch (Exception e) {
				String msg = e.getMessage();
				wrapper.setNextPage("resposta.jsp?msgClean=" + msg);
			}

		} else if (action.equals(ALTERAR_SENHA) ){
			String login		= session.getAttributeString("login");
			String novaSenha	= session.getAttributeString("password");
			String confirmacao	= session.getAttributeString("confirm");

			try {
				if( novaSenha.trim().equals("") || confirmacao.trim().equals("") ) 
					throw new ExceptionInfo("INFORME_NOVA_SENHA_CONFIRMACAO");

				if( !novaSenha.trim().equals(confirmacao.trim()) ) 
					throw new ExceptionInfo("CONFIRMACAO_IGUAL_SENHA");

				String idUsuario	= session.getAttributeString("idUsuario");
				if(idUsuario==null) idUsuario = "0";

				String email	= session.getAttributeString("email");
				if(email==null) email = "";

				idUsuario = new String( Base64.decode(idUsuario) );
				email = new String( Base64.decode(email) );

				entity.setIdUsuario(new Long(idUsuario));
				entity.setEmail(email);
				entity.getThis();

				novaSenha = entity.criptografar(novaSenha);

				entity.setLogin(login);
				entity.setSenha(novaSenha);
				try {
					entity.update();
					new LogEntity( LogEntity.TIPO_SITE, usuario, entity.getTableName(), entity.get_IDFieldName(), session.getAttributeString("ip"), session.getAttributeString("ipServidor"),  entity.getIdUsuario(), "Senha alterada com sucesso!" );
					wrapper.setNextPage("resposta.jsp?msgClean=SENHA_ALTERADA_SUCESSO");
				} catch (Exception e) {
					wrapper.setNextPage("resposta.jsp?msgClean=ERROR: login already in use, try another one.");
				}

			} catch (ExceptionInfo e) {
				String msg = e.getMessage();
				wrapper.setNextPage("resposta.jsp?msgClean=" + msg);
			}

		} else if (action.equals(OS_RESPOSTA)) {

			String idUsuarioResposta = session.getAttributeString("idUsuarioResposta");
			String id = session.getAttributeString("idSuporte");
			String res = session.getAttributeString("resposta");
			String assunto = session.getAttributeString("assunto");
			String anexo = session.getAttributeString("urlResposta");

			String tipoResposta = session.getAttributeString("tipo");
			String respostaAntiga = session.getAttributeString("respostaAntiga");

			SimpleDateFormat sdfTime = new SimpleDateFormat("dd/MM/yyyy '�s' HH:mm:ss");
			if(!respostaAntiga.equals(""))res = respostaAntiga + "<br/><br/><hr><br/> "+ sdfTime.format(new Date())+" - "+ res;

			try {
				SuporteEntity os = new SuporteEntity();
				os.responder(id, res, assunto, idUsuarioResposta,anexo,tipoResposta,respostaAntiga);

				new LogEntity(LogEntity.TIPO_SITE, usuario, "os", "idSuporte",
						session.getAttributeString("ip"),
						session.getAttributeString("ipServidor"), new Long(id),
						"OS Respondida");

				wrapper.setNextPage("resposta.jsp?msgClean=RESPOSTA_ENVIADA_SUCESSO");
			} catch (Exception e) {
				wrapper.setNextPage("resposta.jsp?msgClean=" + e.getMessage());
			}
		} else if (action.equals(OS_RESPOSTA_SUBCATEGORIA)) {

			String id = session.getAttributeString("idSuporte");
			String subcategoria = session.getAttributeString("subcategoria");

			try {
				SuporteEntity os = new SuporteEntity();
				os.saveSubcategoria(id, subcategoria);

				new LogEntity(LogEntity.TIPO_SITE, usuario, "os", "idSuporte",
						session.getAttributeString("ip"),
						session.getAttributeString("ipServidor"), new Long(id),
						"Subcategoria Cadastrada");

				wrapper.setNextPage("resposta.jsp?msgClean=Categoria Cadastrada");
			} catch (Exception e) {
				wrapper.setNextPage("resposta.jsp?msgClean=" + e.getMessage());
			}
		} else if (action.equals(OS)) {
			String login = session.getAttributeString("osLogin");
			String tel = session.getAttributeString("osTelefone");
			String msg = session.getAttributeString("osMensagem");
			String nome = session.getAttributeString("osNome");

			try {
				SuporteEntity os = new SuporteEntity();
				os.save(login, tel, msg, nome);

				new LogEntity(LogEntity.TIPO_SITE, usuario, "os", "idSuporte",
						session.getAttributeString("ip"),
						session.getAttributeString("ipServidor"),
						os.getIdUsuario(), "Usuario enviou OS");

				wrapper.setNextPage("resposta.jsp?msgClean=DUVIDA_ENVIADA_SUCESSO");
			} catch (Exception e) {
				wrapper.setNextPage("resposta.jsp?msgClean=" + e.getMessage());
			}

		} else if(action.equals(DUVIDA)){
			String msg = session.getAttributeString("MENSAGEM2");
			String assunto = session.getAttributeString("ASSUNTO");
			try {
				SuporteEntity os = new SuporteEntity();
				os.save(usuario, msg, assunto);

				new LogEntity(LogEntity.TIPO_SITE, usuario, "os", "idSuporte",
						session.getAttributeString("ip"),
						session.getAttributeString("ipServidor"),
						os.getIdUsuario(), "Usuario enviou OS");

				wrapper.setNextPage("resposta.jsp?info=DUVIDA_ENVIADA_SUCESSO&protocolo=" + os.getIdSuporte());
			} catch (Exception e) {
				e.printStackTrace();
				msg = e.getMessage();
				wrapper.setNextPage("resposta.jsp?warning=" + e.getMessage());
			}


		} else if(action.equals(INATIVAR_USUARIO)){
			String idUsuario = session.getAttributeString("inativo");
			if(entity.getThis("idUsuario = "+idUsuario)){
				if( entity.getAtivo() == 0 ) {
					entity.setAtivo(1);
				} else {
					entity.setAtivo(0);
				}

//				String motivo = "inativar_usuario: ";
//				motivo += session.getAttributeString("motivo");
//				entity.setMotivo(new StringBuffer(entity.getMotivo()+"|"+new StringBuffer(motivo)));
				if(entity.update()){
					new LogEntity(LogEntity.TIPO_SITE, getLoggedUser(session, wrapper).getIdUsuario(),
							entity.getTableName(), entity.get_IDFieldName(),
							session.getAttributeString("ip"),
							session.getAttributeString("ipServidor"), Long.valueOf(idUsuario),
							"admin_inativar: Usuario inativado com sucesso, usuarioLogado = "+getLoggedUser(session, wrapper).getIdUsuario()+" usuarioInativado = "+idUsuario);
				}
			}
			wrapper.setNextPage("resposta.jsp?msgClean=USUARIO_INATIVADO_COM_SUCESSO");

		}else if (action.equals(SAVE_DADOS_PESSOAIS_ADMIN)) {
			
			populateEntity(entity, session);
			String msg = "";
			
			try {		
				
				entity.setIdUsuario(new Long(session.getAttributeString("idUsuarioAdmin")));
				entity.setAtivo( session.getAttributeString("inativo") != null ? 0 : 1 );				
				
				if(session.getAttributeString("senha") != null && !session.getAttributeString("senha").equals("")){
					entity.setSenha(session.getAttributeString("senha"));
				}			
				
				
				boolean ok = entity.updateAdmin(session, entity.getIdUsuario());
				
				if (ok) {
					
					new LogEntity(LogEntity.TIPO_SITE, usuario, entity.getTableName(), entity.get_IDFieldName(), session.getAttributeString("ip"), session.getAttributeString("ipServidor"), entity.getIdUsuario(),	"Usu�rio: alterado dados pessoais");
					msg = "ALTERA_DADOS_PESSOAIS_SUCESSO";
				}
				
				wrapper.setNextPage("resposta.jsp?info=" + msg);
				
			} catch (Exception e) {				
				e.printStackTrace();
				msg = e.getMessage();
				wrapper.setNextPage("resposta.jsp?warning="+msg);
			}
			
		}else if (action.equals(MENSAGEM_ENVIAR)) {
			String msg = "";
			try {
				MensagemEntity mensagem = new MensagemEntity();
				
				String value = session.getAttributeString("value");
				boolean ok = mensagem.enviar(usuario, MensagemEntity.TIPO_CORREIO, session, value);
				
				if (ok) {
					msg = "MENSAGEM_ENVIADA";
					new LogEntity(LogEntity.TIPO_SITE, usuario, mensagem.getTableName(), mensagem.get_IDFieldName(),session.getAttributeString("ip"), session.getAttributeString("ipServidor"),	mensagem.getIdMensagem(), "Mensagem enviada");
					wrapper.setNextPage("resposta.jsp?info=" + msg);
				} else {
					throw new Exception("ERROR SENDING MESSAGE");
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg = e.getMessage();
				wrapper.setNextPage("resposta.jsp?warning=" + msg);
			}
			
		}else if (action.equals(MENSAGEM_TEMPLATE)) {
			String msg = "";
			try {
				MensagemTemplateEntity template = new MensagemTemplateEntity();
				
				String value = session.getAttributeString("value");
				boolean ok = template.salvar(usuario, session, value);

				if (ok) {
					msg = "MENSAGEM_ENVIADA";
					new LogEntity(LogEntity.TIPO_SITE, usuario,	template.getTableName(), "idTemplate", session.getAttributeString("ip"), session.getAttributeString("ipServidor"), template.getIdTemplate(), "Template salvo");
					wrapper.setNextPage("resposta.jsp?info=" + msg);
				} else {
					throw new Exception("ERROR SENDING MESSAGE");
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg = e.getMessage();
				wrapper.setNextPage("resposta.jsp?warning=" + msg);
			}

			/*
		} else if (action.equals(ATUALIZA_USUARIO)) {
			try {
				entity.update(session);
				new LogEntity(LogEntity.TIPO_SITE, usuario,
						entity.getTableName(), entity.get_IDFieldName(),
						session.getAttributeString("ip"),
						session.getAttributeString("ipServidor"),
						entity.getIdUsuario(), "Usuario atualizado com sucesso");
				session.removeAttribute("LOGIN_TAG");
				session.setAttribute(LOGIN_TAG, entity);
				wrapper.setNextPage("mensagem.jsp?info=Sucesso");
			} catch (Exception e) {
				wrapper.setNextPage("mensagem.jsp?warning=" + e.getMessage());
			}
			*/

		} else if(action.equals(EMAIL_LOGIN)){
			String login = session.getAttributeString("txtUsuario");
			entity.getThis("login = '"+new String(Base64.decode(login))+"'");
			doLogin(session, entity);
			wrapper.setNextPage("resposta.jsp?action=logado");
		}
		data = null;
		return wrapper;
	}

	public String getFormTitle() {
		return "Usu�rios";
	}

	public long getCommandID() {
		return 20;
	}
}