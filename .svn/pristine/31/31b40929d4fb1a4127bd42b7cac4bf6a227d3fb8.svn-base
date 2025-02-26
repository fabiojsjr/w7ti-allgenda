package com.tetrasoft.web.usuario;

import com.technique.engine.util.ExceptionAbstract;
import com.technique.engine.web.CommandWrapper;
import com.technique.engine.web.UserSession;
import com.technique.engine.xml.XMLData;
import com.tetrasoft.common.web.BaseCommand;
import com.tetrasoft.common.web.SecurityInterface;
import com.tetrasoft.data.usuario.LogEntity;
import com.tetrasoft.data.usuario.PerfilAcessoEntity;

public class PerfilCommand extends BaseCommand implements SecurityInterface {

	private static PerfilCommand instance = new PerfilCommand();
	public static PerfilCommand getInstance() {
		return instance;
	}

	public PerfilCommand() {
	}

	public CommandWrapper execute(UserSession session) throws ExceptionAbstract {
		CommandWrapper wrapper = new CommandWrapper(session.getSID());
		getAuthorizedUser(session, wrapper);
		long usuario = getAuthorizedUser(session, wrapper).getIdUsuario().longValue();
		
		XMLData data = wrapper.getXMLData();
		String action = getAction(session);
		PerfilAcessoEntity entity = new PerfilAcessoEntity();
		if (action.equals(ACTION_DEFAULT)) {

		} else if (action.equals(ACTION_SAVE)) {
			try {
                entity.save(session);
                new LogEntity( LogEntity.TIPO_INTRANET,  usuario, entity.getTableName(), entity.get_IDFieldName(), session.getAttributeString("ip"), session.getAttributeString("ipServidor"),  entity.getIdPerfilAcesso().longValue(), "Perfil de acesso alterado" );
                wrapper.setNextPage("resposta.jsp?info=Perfil de acesso alterado com sucesso!");
			} catch (Exception e) {
				wrapper.setNextPage("resposta.jsp?warning="+ e.getMessage() );				
			}
		}
		
		fillSecurity(session,data,this);
		entity=null;
		data=null;
		return wrapper;
	}

	public long getCommandID() {
		return 410;
	}

	public String getFormTitle() {
		return "Perfil de Acesso";
	}
}