package com.intranet.web.usuario;

import java.sql.Connection;

import com.technique.engine.util.ExceptionAbstract;
import com.technique.engine.util.ExceptionInfo;
import com.technique.engine.web.CommandWrapper;
import com.technique.engine.web.UserSession;
import com.technique.engine.xml.XMLData;
import com.tetrasoft.common.web.SecurityInterface;
import com.tetrasoft.data.usuario.LogEntity;
import com.tetrasoft.web.basico.CadastroBaseCommand;
import com.tetrasoft.web.basico.CadastroWrapper;

/**
 * <PRE>
 *
 * </PRE>
 *
 * @author Renato V. Filipov
 * @version 1.0.0a
 * @see <P>
 * <B>Revision History:</B>
 * <PRE>
 * <B>©FUTUWARE MULTIMEDIA - Copyright 2003.  <I>All Rights Reserved.</I></B>
 * Este software pertence à FUTUWARE MULTIMEDIA.
 * A sua utilização é limitada aos termos de uso que acompanha este código.
 * Desenvolvido por Futuware Multimedia - www.futuware.com.br
 *
 * This software is the proprietary information of FUTUWARE MULTIMEDIA.
 * Use is subject to license terms.
 * Developed by Futuware Multimedia - www.futuware.com.br
 * </PRE>
 */

public class LogCommand extends CadastroBaseCommand implements SecurityInterface {
	private static String PAGE_LIST      = "template/intranet/logList.xsl";
    private static String PAGE_LIST_AJAX = "template/intranet/logListAjax.xsl";

    private static LogCommand instance = new LogCommand();

    /** Creates a new instance of LogCommand */
    public LogCommand() {
    }

    public static LogCommand getInstance() {
        return instance;
    }

    /** this method is called by the controller after the object instantiation. This is the place
     * where navigation logic goes
     * @param session UserSession object
     * @throws ExceptionAbstract ExceptionInfo, ExceptionWarning or ExceptionFatal could be raised depending on the situation
     */
    public CommandWrapper execute(UserSession session) throws ExceptionAbstract {
    	CadastroWrapper cadWrapper = new CadastroWrapper();
        CommandWrapper wrapper = super.execute(session, cadWrapper);
        getAuthorizedUser(session, wrapper);
        long usuario = getAuthorizedUser(session, wrapper).getIdUsuario().longValue();
        wrapper.setNextPage(PAGE_LIST_AJAX);
        XMLData data = wrapper.getXMLData();
        String action = getAction(session);

        LogEntity entity = new LogEntity();
        entity.populateRelated( data );
        
        if( action.equals(ACTION_DEFAULT) ) {
        	action="";
        	wrapper.setNextPage(PAGE_LIST);
        }

        if( action.equals(ACTION_FIND) ) {
            cadWrapper.reset();
            action = ACTION_DEFAULT;
        } else if (action.equals(ACTION_NEXT_PAGE)) {
            cadWrapper.moveNextPage();
            action = ACTION_DEFAULT;
        } else if (action.equals(ACTION_PREV_PAGE)) {
            cadWrapper.movePrevPage();
            action = ACTION_DEFAULT;
        } else if (action.equals(ACTION_LAST_PAGE)) {
            cadWrapper.moveLastPage();
            action = ACTION_DEFAULT;
        } else if (action.equals(ACTION_FIRST_PAGE)) {
            cadWrapper.moveFirstPage();
            action = ACTION_DEFAULT;
        }

        if( action.equals(ACTION_DEFAULT) ) {
            String txt1  = session.getAttributeString("data1");
            String txt2  = session.getAttributeString("data2");
            String txt3  = session.getAttributeString("idUsuario");
            String txt4  = session.getAttributeString("tabela");
            String txt5  = session.getAttributeString("idCampo");
            String txt6  = session.getAttributeString("ordem");
            String txt7  = session.getAttributeString("acao");
            String txt8  = "";
            String txt9  = session.getAttributeString("hora1");
            String txt10 = session.getAttributeString("hora2");
            String txt11 = session.getAttributeString("ipOrigem");
            String txt12 = "";
            String txt13 = "";
            String txt14 = "1";

            try {
            	Connection conn = null;
            	
            	entity.getResumo( data, conn, txt1, txt2, txt9, txt10, txt3, txt4, txt5, txt6, txt7, txt8, txt11, txt12, txt13, txt14, cadWrapper.getCurrentRow(), cadWrapper.getNumRecordsPerPage() );
                
                cadWrapper.setNumRows( entity.getTotal().longValue() );

                showList(data);

                new LogEntity( LogEntity.TIPO_SITE,  usuario, "-", "-", session.getAttributeString("ip"), session.getAttributeString("ipServidor"), new Long(0).longValue(), "Análise de log");
            } catch (ExceptionInfo e) {
                wrapper.addError(e);
            }

            data.addClosedTag("data1",txt1);
            data.addClosedTag("data2",txt2);
            data.addClosedTag("idUsuario",txt3);
            data.addClosedTag("tabela",txt4);
            data.addClosedTag("idCampo",txt5);
            data.addClosedTag("ordem",txt6);
            data.addClosedTag("acao",txt7);
            data.addClosedTag("agrupamento",txt8);
            data.addClosedTag("hora1",txt9);
            data.addClosedTag("hora2",txt10);
            data.addClosedTag("ipOrigem",txt11);
            data.addClosedTag("limitador",txt12);
            data.addClosedTag("tipoGrafico",txt13);
            data.addClosedTag("base",txt14);
        }

        fillNavAttributes(data,cadWrapper);
        fillSecurity(session,data,this);
        data=null;
        return wrapper;
    }

    public String getFormTitle() {
      return "Log";
    }

    public long getCommandID() {
        return 40;
    }
}