package com.tetrasoft.common.web;

import com.technique.engine.util.ExceptionAbstract;
import com.technique.engine.util.ExceptionInjection;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.util.TechCommonKeys;
import com.technique.engine.web.AbstractCommand;
import com.technique.engine.web.CommandWrapper;
import com.technique.engine.web.UserSession;
import com.technique.engine.xml.XMLData;
import com.tetrasoft.data.usuario.UsuarioEntity;

public abstract class BaseCommand extends AbstractCommand {

	public static final String ACTION_DEFAULT	= "default";
	public static final String ACTION_FIND		= "find";
	public static final String ACTION_EDIT		= "edit";
	public static final String ACTION_SAVE		= "save";
	public static final String ACTION_INSERT	= "insert";
	public static final String ACTION_DELETE	= "delete";
	public static final String ACTION_UPDATE	= "update";

	public static final String LOGIN_TAG = "loginAllgenda";
	public static final String LOGIN_MASTER = "senhaMaster";
	public static final String ID = "id";
	public static final String NEXT_PAGE_MAIN = "main.xsl";

	public static String PAGE_INTRA_LOGIN = "intranet/login.xsl";
	public static String PAGE_INTRA_MAIN = "intranet/login.xsl";
	public static String PAGE_SITE_LOGIN = "site/login.xsl";
	public static String PAGE_SITE_MAIN = "site/login.xsl";
	public static String PAGE_ALERT_AJAX = "ajax/alert.xsl";
	public static String PAGE_ALERT_POST_AJAX = "ajax/alertPost.xsl";
	public static String PAGE_ALERT_AJAX_CLEAN = "ajax/alertClean.xsl";
	public static String PAGE_ALERT_AJAX_HTML = "ajax/alertHTML.xsl";
	public static String PAGE_ALERT_RETURN_AJAX = "ajax/alertReturn.xsl";
	public static String PAGE_ALERT_RETURN_ACTION_AJAX = "ajax/alertReturnAction.xsl";

	protected static final String USR_NOT_LOGGED_MSG = "Voce precisa estar logado no sistema para poder realizar esta operaééo!";
	protected static final String SECURITY_MSG = "Problemas de Seguranéa do Sistema: Voce não tem direitos de realizar esta operaééo! Procure efetuar login novamente caso o problema persista ou consulte o administrador do sistema para obter maiores informaéées sobre o seu perfil de acesso.";
	protected static final String EXPIRED_SESSION_MSG = "A sua sesséo expirou devido a um grande perédo de inatividade! Por favor, efetue o login novamente para recomeéar.";
	protected static final String USR_NOT_ACTIVE = "O seu usuário foi desativado! Entre em contato com a empresa para maiores esclarecimentos.";
	protected static final String SYNC_TOKEN_MSG = "COMANDO INVALIDO! Este erro ocorre quando a existe a tentativa de realizar a mesma transaééo mais de uma vez. Para evitar este tipo de erro não utilize o comando VOLTAR (BACK) do browser.";

	protected static String getAction(UserSession session) throws ExceptionWarning {
		String str=(String)session.getAttribute(TechCommonKeys.ACTION);
		if (str==null || "".equals(str)) str=ACTION_DEFAULT;
		return str;
	}

	public static boolean isLogged(UserSession session) throws ExceptionWarning {
		Object obj=session.getAttribute(LOGIN_TAG);
		if (obj==null) return false;
		return true;
	}

	public static UsuarioEntity getLoggedUser(UserSession session, CommandWrapper wrapper) throws ExceptionWarning {
		try {
			UsuarioEntity entity = (UsuarioEntity)session.getAttribute(LOGIN_TAG);
			if (entity==null) {
				if (wrapper!=null) {
					wrapper.setNextPage(PAGE_SITE_LOGIN);
				}
				throw new ExceptionWarning(USR_NOT_LOGGED_MSG);
			}
//			if (entity.getAtivo().intValue()==0) {
//				doLogout(session);
//				wrapper.setNextPage(PAGE_SITE_LOGIN);
//				throw new ExceptionWarning(USR_NOT_ACTIVE);
//			}
			return entity;
		} catch (ClassCastException e) {
			throw new ExceptionWarning(USR_NOT_LOGGED_MSG);
		}
	}

	protected static void doLogin(UserSession session, UsuarioEntity usuario) throws ExceptionWarning, ExceptionInjection {
		session.setAttribute(LOGIN_TAG,usuario);
	}

	protected static void doLogout(UserSession session) throws ExceptionWarning {
		session.clearAll();
	}

	protected CommandWrapper redirectLogin(CommandWrapper wrapper) throws ExceptionAbstract {
		wrapper.setNextPage(PAGE_SITE_LOGIN);
		return wrapper;
	}

	protected UsuarioEntity getAuthorizedUser(UserSession session, CommandWrapper wrapper) throws ExceptionWarning {
		UsuarioEntity usuario = getLoggedUser(session, null);
		if (usuario==null) {
			if (wrapper!=null) wrapper.setNextPage(PAGE_SITE_LOGIN);
			throw new ExceptionWarning(USR_NOT_LOGGED_MSG);
		}
		return usuario;
	}

	protected void showList(XMLData data) {
		data.addClosedTag("showList","1");
	}

	protected void setEditMode(XMLData data) {
		data.addClosedTag("formMode","Alterar");
		data.addClosedTag("formModeA","Alterar");
	}

	protected void setInsertMode(XMLData data) {
		data.addClosedTag("formMode","Novo");
		data.addClosedTag("formModeA","Nova");
	}

	protected void setFormMode(XMLData data, String s) {
		String s2="Alterar";
		if (s==null) {
			s="Novo";
			s2="Nova";
		} else if (s.equals("Novo")) {
			s2="Nova";
		}
		data.addClosedTag("formMode",s);
		data.addClosedTag("formModeA",s2);
	}

	protected void fillSecurity(UserSession session, XMLData data, SecurityInterface command) {
		/*
		try {
			UsuarioEntity usuario = getLoggedUser(session, null);
			PerfilFuncionalidadeEntity acesso = usuario.getAcessoFuncionalidade(command.getCommandID());
			if( acesso == null ) {
				acesso = new PerfilFuncionalidadeEntity();
				acesso.setIdFuncionalidade( command.getCommandID() );
			}

			UsuarioFuncionalidadeEntity usuarioFunc = new UsuarioFuncionalidadeEntity();
			usuarioFunc.setIdUsuario( usuario.getIdUsuario() );
			usuarioFunc.setIdFuncionalidade( acesso.getIdFuncionalidade() );
			if( usuarioFunc.getThis() ) {
				if( usuarioFunc.getIncluir().intValue() == 1 ) 	acesso.setIncluir(1);
				if( usuarioFunc.getAlterar().intValue() == 1 ) 	acesso.setAlterar(1);
				if( usuarioFunc.getExcluir().intValue() == 1 ) 	acesso.setExcluir(1);
				if( usuarioFunc.getConsultar().intValue() == 1 ) 	acesso.setConsultar(1);
			}

			if (acesso.getAlterar().intValue()==1)   data.addClosedTag("canEdit","1");
			if (acesso.getAlterar().intValue()==0)   data.addClosedTag("canEdit","0");
			if (acesso.getIncluir().intValue()==1)   data.addClosedTag("canInsert","1");
			if (acesso.getIncluir().intValue()==0)   data.addClosedTag("canInsert","0");
			if (acesso.getConsultar().intValue()==1) data.addClosedTag("canList","1");
			if (acesso.getConsultar().intValue()==0) data.addClosedTag("canList","0");
			if (acesso.getExcluir().intValue()==1)   data.addClosedTag("canDelete","1");
			if (acesso.getExcluir().intValue()==0)   data.addClosedTag("canDelete","0");
		} catch (Exception e) {
		}
		 */
	}

	protected void securityCanEdit(UserSession session, SecurityInterface command) throws ExceptionWarning {
		/*
		UsuarioEntity usuario = getLoggedUser(session, null);
		if (usuario.getAcessoFuncionalidade(command.getCommandID()).getAlterar().intValue()==0) {
			throw new ExceptionWarning(SECURITY_MSG);
		}
		*/
	}

	protected void securityCanInsert(UserSession session, SecurityInterface command) throws ExceptionWarning {
		/*
		UsuarioEntity usuario = getLoggedUser(session, null);
		if (usuario.getAcessoFuncionalidade(command.getCommandID()).getIncluir().intValue()==0) {
			throw new ExceptionWarning(SECURITY_MSG);
		}
		*/
	}

	protected void securityCanDelete(UserSession session, SecurityInterface command) throws ExceptionWarning {
		/*
		UsuarioEntity usuario = getLoggedUser(session, null);
		if (usuario.getAcessoFuncionalidade(command.getCommandID()).getExcluir().intValue()==0) {
			throw new ExceptionWarning(SECURITY_MSG);
		}
		*/
	}

	protected void securityCanList(UserSession session, SecurityInterface command) throws ExceptionWarning {
		/*
		UsuarioEntity usuario = getLoggedUser(session, null);
		if (usuario.getAcessoFuncionalidade(command.getCommandID()).getConsultar().intValue()==0) {
			throw new ExceptionWarning(SECURITY_MSG);
		}
		*/
	}

}