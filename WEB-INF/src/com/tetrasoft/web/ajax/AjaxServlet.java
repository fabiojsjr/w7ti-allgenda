package com.tetrasoft.web.ajax;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.technique.engine.web.CommandWrapper;
import com.technique.engine.xml.XMLData;
import com.tetrasoft.app.sender.SenderInterface;
import com.tetrasoft.util.ajax.AjaxUtil;

public class AjaxServlet extends HttpServlet {
	private static StringBuffer globalMessage;

	public void init(ServletConfig config) throws ServletException {
		globalMessage = new StringBuffer(10000);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 

		String action  = request.getParameter("action");
//		String command = request.getParameter("command");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
		StringBuffer sb = new StringBuffer("");
		
		if( action.equals("clienteSiteView") ) {
			try {
				CommandWrapper wrapper = new CommandWrapper(SenderInterface.SID);
				XMLData data = wrapper.getXMLData();

//				String id = request.getParameter("idCliente");
//				ClienteEntity cliente = new ClienteEntity();
//				cliente.prepareUpdate(data, id, "");
				
				String xml = data.getXML();
				xml = AjaxUtil.formatXML( xml );
				sb.append( xml );

			} catch (Exception e) {
				e.printStackTrace();
			}

			response.setContentType("text/xml");
			
		} if( action.equals("addChat") ) {
			String msg = request.getParameter("message");

			if( (msg != null) && ( !msg.equals("") ) ) {
				globalMessage.insert( 0, sdf.format( new java.util.Date() ) + " : " + msg + "<br>" );

				if( globalMessage.length() > 9000 )
					globalMessage = globalMessage.delete(9000, globalMessage.length());
			}

		} if( action.equals("showChat") ) {
			sb.append("<table width='100%' bgcolor='white'><tr><td>");
			sb.append( globalMessage );
			sb.append("</td></tr></table>");

		} else {
			sb.append("oi, isso é um teste - " + System.currentTimeMillis());
		}
		
		response.setHeader("Cache-Control", "no-cache"); 
		response.getWriter().write( sb.toString() ); 
	}
}
