package com.tetrasoft.web.finance;

import com.technique.engine.util.ExceptionAbstract;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.web.CommandWrapper;
import com.technique.engine.web.UserSession;
import com.tetrasoft.common.web.SecurityInterface;
import com.tetrasoft.web.basico.CadastroBaseCommand;
import com.tetrasoft.web.basico.CadastroWrapper;


public class RelatorioReuniaoCommand extends CadastroBaseCommand implements SecurityInterface {
	
	private static RelatorioReuniaoCommand instance = new RelatorioReuniaoCommand();	
	private static String CRIAR_RELATORIO_REUNIAO = "criarRelatorioReuniao";	

	public RelatorioReuniaoCommand(){
	}
	
	public static RelatorioReuniaoCommand getInstance(){
		return instance;
	}
	
	@Override
	public long getCommandID() {
		return 0;
	}	

	@Override
	public CommandWrapper execute(UserSession session) throws ExceptionAbstract {	
		
		CadastroWrapper cadWrapper = new CadastroWrapper();
		CommandWrapper  wrapper    = super.execute(session, cadWrapper);
		String action  = getAction(session);	
		
		String dataInicial = session.getAttributeString("dataInicio");
		String dataFinal = session.getAttributeString("dataFinal");
		String relatorioPath = "";
		
		try {
			
			if(action.equals(CRIAR_RELATORIO_REUNIAO)){			
				
				if(dataInicial.equals("") || dataFinal.equals(""))
					throw new ExceptionWarning("A Data inicial e a data Final são obritórias");
				
				RelatorioMes relatorio = new RelatorioMes();
				relatorioPath = relatorio.gerar(dataInicial, dataFinal);								 			
				
			}	
			
		}catch(ExceptionWarning e) {			
			wrapper.setNextPage("calendarioRelatorioEdit.jsp?dataInicio=" + dataInicial + "&dataFinal=" + dataFinal + "&msg="+e.getMessage());
    		return wrapper;			
		}
		
		wrapper.setNextPage("calendarioRelatorioEdit.jsp?&file=" + relatorioPath);
		
		return wrapper;
	}

}
