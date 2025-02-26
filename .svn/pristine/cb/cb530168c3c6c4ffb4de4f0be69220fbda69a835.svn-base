package com.tetrasoft.web.finance;

import com.technique.engine.util.ExceptionAbstract;
import com.technique.engine.web.CommandWrapper;
import com.technique.engine.web.UserSession;
import com.tetrasoft.common.web.SecurityInterface;
import com.tetrasoft.data.finance.SalaReuniaoEntity;
import com.tetrasoft.data.usuario.LogEntity;
import com.tetrasoft.web.basico.CadastroBaseCommand;
import com.tetrasoft.web.basico.CadastroWrapper;

public class SalaReuniaoCommand extends CadastroBaseCommand implements SecurityInterface{
	
	private static SalaReuniaoCommand instance = new SalaReuniaoCommand();	
	private static String ACTION_SAVE_SALAREUNIAO = "saveSalaReuniao";	
	
	public SalaReuniaoCommand(){
	}
	
	public static SalaReuniaoCommand getInstance(){
		return instance;
	}
	
	@Override
	public long getCommandID() {
		return 907;
	}	
	
	@Override
	public CommandWrapper execute(UserSession session) throws ExceptionAbstract {
		
		CadastroWrapper cadWrapper = new CadastroWrapper();
		CommandWrapper  wrapper    = super.execute(session, cadWrapper);		
		
		long   usuario = getAuthorizedUser(session, wrapper).getIdUsuario().longValue();		
		
		String action  = getAction(session);					
		
		if(action.equals(ACTION_SAVE_SALAREUNIAO)){		
			
			try {				
				
				SalaReuniaoEntity salaReuniao = new SalaReuniaoEntity();
				populateEntity(salaReuniao, session);			
				
				boolean isExiste = new SalaReuniaoEntity().getThis("idSalaReuniao = '"+salaReuniao.getIdSalaReuniao()+"'");
				
				if( session.getAttribute("inativo") != null){
					salaReuniao.setAtivo(0);
				}			
					
				salaReuniao.save(isExiste);				
				
				if(isExiste){	
					new LogEntity(LogEntity.TIPO_INTRANET, usuario, salaReuniao.getTableName(), salaReuniao.get_IDFieldName(), session.getAttributeString("ip"), session.getAttributeString("ipServidor"), salaReuniao.getIdSalaReuniao(), "sala de reunião alterada com sucesso");
					wrapper.setNextPage("resposta.jsp?info=ALTERA_SUCESSO");	
				}else{
					new LogEntity(LogEntity.TIPO_INTRANET, usuario, salaReuniao.getTableName(), salaReuniao.get_IDFieldName(), session.getAttributeString("ip"), session.getAttributeString("ipServidor"), salaReuniao.getIdSalaReuniao(), "sala de reunião incluída com sucesso");
					wrapper.setNextPage("resposta.jsp?info=INSERIDO_SUCESSO");
				}						
				
			}catch(Exception e) {				
				
				wrapper.setNextPage("resposta.jsp?warning=" + e.getMessage());					
			
			} 
		
		}	
	
		return wrapper;

	}

}
