<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.technique.engine.web.UserSession"%>
<%@page import="com.tetrasoft.data.cliente.ClienteEntity"%>
<%@page import="com.tetrasoft.data.usuario.UsuarioEntity"%>
<%@page import="com.tetrasoft.data.cliente.Calendario"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>

<%
	Calendario calendario = new Calendario();

	ClienteEntity cliTODO = new ClienteEntity();
	String idClienteSelecionado = null;
	if (session.getAttribute("UserSession_Allgenda") != null){
		UserSession u = ((UserSession) session.getAttribute("UserSession_Allgenda"));
		if( u.getAttributeString("idClienteSelecionado") != null ) {
			idClienteSelecionado = u.getAttributeString("idClienteSelecionado");
		}
	}
	
	ArrayList<String> list = null;
	if( idClienteSelecionado != null ) {
		cliTODO.setIdCliente( new Long(idClienteSelecionado) );
		cliTODO.getThis();
		
		list = calendario.getTODO( cliTODO.getIdCliente(), false );
	} else {
		list = calendario.getTODO( usuarioLogado.getIdUsuario(), idCalendario, false );		
	}
	
	for( String s : list ) {
		StringTokenizer st = new StringTokenizer( s, "|");
		
		String data       = st.nextToken().substring(0,10);
		String id2        = st.nextToken();
		String tipoAlerta = st.nextToken();
		String tipo2      = st.nextToken();
		String evento     = st.nextToken();
		String cliente    = st.nextToken();
		
		if( idClienteSelecionado != null ) {
			if( !cliTODO.getNome().equals( cliente ) ) {
				continue;
			}
		}
		
		String color = "";
		Date date = usuarioLogado.DATE_FORMAT7.parse( data + " 12:00:00" );
		if( date.before( new Date() ) ) color = "color: red;";
		if( data.replaceAll("-", "").equals( usuarioLogado.DATE_FORMAT2.format( new Date() ) ) )  color = "color: orange;";
		
		if( !cliente.equals(".") ) cliente = " - " + cliente;
		
		data = data.substring(5,7) + "/" + data.substring(8,10); 
%>
		<div style="margin-bottom: 8px; <%= color %>">
			<nobr>
				<input type="checkbox" onclick="javascript:taskDone2('<%= id2 %>', '<%= tipo2 %>', '1', <%= idCalendario %>)" style="margin-bottom: 5px; margin-right: 5px;">
				<span style="<%= tipoAlerta.equals("2") ? "color: darkgreen; font-weight: bold" : "" %>">
					<%= data %> - <%= evento %>
					<%	if( tipoAlerta.equals("2") ) { %>
							<a title='<%= p.get("ENVIAR_TESTE") %>' style='width: 16px; margin-bottom: 0px;margin-left:15px;' class='btn btn-primary btn-circle' href="javascript:enviarEmailTeste('<%= id2 %>')"><i class='iconfa-envelope'></i></a>
					<%	} %>
				</span>
			</nobr>
		</div>
		
<%	} %>	

<hr/>
<center><b><%= p.get("finalizadas_recentemente") %></b></center>

<%
	if( idClienteSelecionado != null ) {
		cliTODO.setIdCliente( new Long(idClienteSelecionado) );
		cliTODO.getThis();
		
		list = calendario.getTODO( cliTODO.getIdCliente(), true );
	} else {
		list = calendario.getTODO( usuarioLogado.getIdUsuario(), idCalendario, true );		
	}
	
	for( String s : list ) {
		StringTokenizer st = new StringTokenizer( s, "|");
		
		String data       = st.nextToken().substring(0,10);
		String id2        = st.nextToken();
		String tipoAlerta = st.nextToken();
		String tipo2      = st.nextToken();
		String evento     = st.nextToken();
		String cliente    = st.nextToken();
		
		if( idClienteSelecionado != null ) {
			if( !cliTODO.getNome().equals( cliente ) ) {
				continue;
			}
		}

		if( !cliente.equals(".") ) cliente = " - " + cliente;
		data = data.substring(5,7) + "/" + data.substring(8,10); 
%>
		<div style="margin-bottom: 8px; color: darkgray"><input type="checkbox" checked="checked" onclick="javascript:taskDone2('<%= id2 %>', '<%= tipo2 %>', '0', <%= idCalendario %>)" style="margin-bottom: 5px; margin-right: 5px"><%= data %> - <%= evento %></div>
		
<%	} %>	

<div id="taskDoneAjax" style="display: none"></div>
