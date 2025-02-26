package com.tetrasoft.common;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.Base64;
import com.technique.engine.util.DateUtil;
import com.technique.engine.util.ExceptionAbstract;
import com.technique.engine.util.ExceptionFatal;
import com.technique.engine.util.ExceptionInfo;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.util.StringUtil;
import com.technique.engine.util.TechCommonKeys;
import com.technique.engine.web.AbstractCommand;
import com.technique.engine.web.AbstractController;
import com.technique.engine.web.CommandWrapper;
import com.technique.engine.web.UserSession;
import com.technique.engine.xml.XMLData;
import com.tetrasoft.app.sender.EmailSender;
import com.tetrasoft.app.sender.SenderInterface;
import com.tetrasoft.common.web.BaseCommand;
import com.tetrasoft.common.web.IntranetBaseCommand;
import com.tetrasoft.data.usuario.UsuarioEntity;

public class Controller extends AbstractController {
	public Controller() {
	}

	protected void beforeGetCommand(UserSession usrSession, HttpServletRequest request, HttpServletResponse response) throws ExceptionFatal, ExceptionWarning, ExceptionInfo {
		String strAvgUsrIdHEADER = "x-avantgo-userid";
		String strAvgUsrIdCODED = request.getHeader(strAvgUsrIdHEADER);
		String strAvgUsrId = null;
		if (strAvgUsrIdCODED != null)
		{
			strAvgUsrId = new String(Base64.decode(strAvgUsrIdCODED));
		}
		else
		{
			strAvgUsrId = request.getParameter(strAvgUsrIdHEADER);
		}
		usrSession.setAttribute(strAvgUsrIdHEADER, strAvgUsrId);
		try {
			usrSession.setAttribute("__timeStart",new Date().getTime()+"",true);
		} catch (Exception e) {
		}

		try {
			usrSession.setAttribute("ip", request.getRemoteAddr() , true);
			usrSession.setAttribute("ipServidor", request.getLocalName(), true);
//			usrSession.setAttribute("ip", request.getRemoteAddr() + " " + request.getRemoteHost(), true);
//			System.out.println(request.getRemoteAddr() + " " + request.getRemoteHost() );
		} catch (Exception e) {
		}
		
		try {
			HttpSession session = request.getSession();
			if(session.getAttribute("idioma")!=null){
				usrSession.setAttribute("idioma", (String) session.getAttribute("idioma"));
			}
		} catch (Exception e) {
		}
	}

	private void addError(CommandWrapper wrapper) {
		if (wrapper.hasErrors()) {
			ArrayList a = wrapper.getErrors();
			wrapper.getXMLData().openGroup("errors");
			for (int i=0; i<a.size(); i++) {
				ExceptionAbstract exp = (ExceptionAbstract)a.get(i);
				String str = StringUtil.toXMLString(exp.getMessage());
				if (exp.getClass().equals(ExceptionInfo.class)) {
					wrapper.getXMLData().addClosedTag("type",((ExceptionInfo)exp).getType()+"");
				} else {
					wrapper.getXMLData().addClosedTag("type","1");
				}
				wrapper.getXMLData().addClosedTag("message",str);
			}
			wrapper.getXMLData().closeGroup();
		}
	}
	private void addSystemError(CommandWrapper wrapper, Throwable e, Throwable eSystem) {
		wrapper.getXMLData().openGroup("systemErrors");
		if (eSystem!=null) {
			wrapper.getXMLData().addClosedTag("message",StringUtil.toXMLString(e.getMessage()));
			wrapper.getXMLData().addClosedTag("message",StringUtil.toXMLString(eSystem.getMessage()));
			wrapper.getXMLData().addClosedTag("message","");
			wrapper.getXMLData().addClosedTag("message", EmailSender.getStack(eSystem).replaceAll("\n","<br/>"), true );
		} else {
			wrapper.getXMLData().addClosedTag("message",StringUtil.toXMLString(e.getMessage()));
		}
		wrapper.getXMLData().closeGroup();
	}

	protected void afterExecuteCommand(AbstractCommand command, UserSession usrSession, HttpServletRequest request, HttpServletResponse response, CommandWrapper wrapper) throws ExceptionFatal, ExceptionWarning, ExceptionInfo {
		String tmp = usrSession.getAttributeString("NAV");
		if (tmp==null) tmp="1";
		XMLData data = wrapper.getXMLData();
		
		String pagina = wrapper.getNextPage();
		if( pagina.contains(".jsp") ) {
			try {
//				pagina = "http://localhost/permuta/" + pagina;
//				System.out.println(pagina);
//				response.sendRedirect(pagina);
				request.getRequestDispatcher(pagina).forward(request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String x = usrSession.getAttributeString("__timeStart");
		Long idUsuario = null;
		String nome = null;
		String primeiroNome = null;
		/*
		if(pagina.contains("xsl") || pagina.contains("intranet")){
			com.intranet.data.usuario.UsuarioEntity usuario = IntranetBaseCommand.getLoggedUser(usrSession,wrapper);
			if(usuario!=null){
				idUsuario = usuario.getIdUsuario();
				nome = usuario.getNome();
				primeiroNome = usuario.getPrimeiroNome();
			}
		}else{
		*/
			UsuarioEntity usuario = BaseCommand.getLoggedUser(usrSession, wrapper);		
			if(usuario!=null){
				idUsuario = usuario.getIdUsuario();
				nome = usuario.getNome();
				primeiroNome = usuario.getNome();
			}
//		}
		
		if (x!=null) {
			Date d = new Date();
			data.addClosedTag("elapsedTime",(d.getTime()-Long.parseLong(x))+"");
			System.out.println(d + " - Execucao: " + (d.getTime()-Long.parseLong(x)) + " - User: " + idUsuario  + " - IP: " + request.getRemoteAddr() + " - Next: " + wrapper.getNextPage() );
		}
		
		String empresa = usrSession.getAttributeString("empresa");
		if( empresa == null ) empresa = "1";
		
		usrSession.setAttribute("resolucao", usrSession.getAttributeString("resolucao"));
		data.addClosedTag("empresaSelecionada", empresa );
		data.addClosedTag("resolucao",usrSession.getAttributeString("resolucao"));
        data.addClosedTag("localname",request.getLocalName());
		data.addClosedTag("dataHoje",DateUtil.sdf3.format(new Date()));
		data.addClosedTag("NAV",tmp);
		addError(wrapper);
		data.addClosedTag("HREF",SystemParameter.get(wrapper.getXMLData().getSID(),TechCommonKeys.TAG_SYSTEM_PROPERTIES,TechCommonKeys.SYSTEM_ENTRY_POINT)+"?sid="+wrapper.getXMLData().getSID()+"&#38;command=");
		//data.addParameterTag("usuario");

		if (!BaseCommand.isLogged(usrSession)) {
			data.addParameter("isLogged","false");

		} else {
			if(IntranetBaseCommand.isLogged(usrSession)){
				data.addParameter("isLogged","true");
				data.addParameter("nome",nome);
				data.addParameter("primeiroNome",primeiroNome);
				data.addParameter("idUsuario",idUsuario.toString());
				data.addClosedTag("idLoggedUser", idUsuario.toString());
			}else{
				data.addParameter("isLogged","false");
			}
		}
	}
				


	protected void onInfoErrors(ExceptionInfo e, PrintWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception {
		e.printStackTrace();
//		boolean ajax = request.getParameter("AjaxRequestUniqueId") == null ? false : true;

		CommandWrapper wrapper = new CommandWrapper(SenderInterface.SID);
		wrapper.addError(e);
		addError(wrapper);
		addSystemError(wrapper,e,e.getOriginalException());
		wrapper.getXMLData().addClosedTag("HREF",SystemParameter.get(wrapper.getXMLData().getSID(),TechCommonKeys.TAG_SYSTEM_PROPERTIES,TechCommonKeys.SYSTEM_ENTRY_POINT)+"?sid="+wrapper.getXMLData().getSID()+"&#38;command=");
//		wrapper.setNextPage( ajax ? "error/errorAjax.xsl" : "error/error.xsl");
		dispatch(wrapper, response);
	}

	protected void onFatalErrors(ExceptionFatal e, PrintWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		boolean ajax = request.getParameter("AjaxRequestUniqueId") == null ? false : true;

		CommandWrapper wrapper = new CommandWrapper(SenderInterface.SID);
		wrapper.addError(e);
		addError(wrapper);
		addSystemError(wrapper,e,e.getOriginalException());
		wrapper.getXMLData().addClosedTag("HREF",SystemParameter.get(wrapper.getXMLData().getSID(),TechCommonKeys.TAG_SYSTEM_PROPERTIES,TechCommonKeys.SYSTEM_ENTRY_POINT)+"?sid="+wrapper.getXMLData().getSID()+"&#38;command=");
//		wrapper.setNextPage( ajax ? "error/errorAjax.xsl" : "error/error.xsl");
		dispatch(wrapper, response);
	}

	protected void onWarningErrors(ExceptionWarning e, PrintWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception {
		e.printStackTrace();
//		boolean ajax = request.getParameter("AjaxRequestUniqueId") == null ? false : true;

		CommandWrapper wrapper = new CommandWrapper(SenderInterface.SID);
		wrapper.addError(e);
		addError(wrapper);
		addSystemError(wrapper,e,e.getOriginalException());
		wrapper.getXMLData().addClosedTag("HREF",SystemParameter.get(wrapper.getXMLData().getSID(),TechCommonKeys.TAG_SYSTEM_PROPERTIES,TechCommonKeys.SYSTEM_ENTRY_POINT)+"?sid="+wrapper.getXMLData().getSID()+"&#38;command=");
//		wrapper.setNextPage( ajax ? "error/errorAjax.xsl" : "error/error.xsl");
		dispatch(wrapper, response);
	}

	protected void beforeExecuteCommand(AbstractCommand command, UserSession usrSession, HttpServletRequest request, HttpServletResponse response) throws ExceptionFatal, ExceptionWarning, ExceptionInfo {
	}

	public String getServletName() {
		return "";
//		return "by Renato V. Filipov - Futuware Multimedia (www.futuware.com.br)";
	}

}