<%@page import="com.tetrasoft.data.usuario.UsuarioEntity"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tetrasoft.data.cliente.Calendario"%>

<%
	Calendario calendario = new Calendario();
	
	ArrayList<String> list = calendario.getTODO( usuarioLogado.getIdUsuario(), idCalendario, false );
	
	for( String s : list ) {
		StringTokenizer st = new StringTokenizer( s, "|");
		
		String data       = st.nextToken().substring(0,10);
		String id         = st.nextToken();
		String tipoAlerta = st.nextToken();
		String tipo2      = st.nextToken();
		String evento     = st.nextToken();
		String cliente2   = st.nextToken();
		
		String color = "";
		Date date = usuarioLogado.DATE_FORMAT7.parse( data + " 12:00:00" );
		if( date.before( new Date() ) ) color = "color: red;";
		if( data.replaceAll("-", "").equals( usuarioLogado.DATE_FORMAT2.format( new Date() ) ) )  color = "color: orange;";
		
		if( !cliente2.equals(".") ) cliente2 = " - " + cliente2;
		
		data = data.substring(5,7) + "/" + data.substring(8,10); 
%>
		<div style="margin-bottom: 8px; <%= color %>"><input type="checkbox" onclick="javascript:taskDone('<%= id %>', '<%= tipo2 %>', '1', <%= idCalendario %>)" style="margin-bottom: 5px; margin-right: 5px"><%= data %> - <%= evento %> <i><%= cliente2 %></i></div>
		
<%	} %>	

<hr/>
<center><b><%= p.get("finalizadas_recentemente") %></b></center>

<%
	list = calendario.getTODO( usuarioLogado.getIdUsuario(), idCalendario, true );
	
	for( String s : list ) {
		StringTokenizer st = new StringTokenizer( s, "|");
		
		String data       = st.nextToken().substring(0,10);
		String id         = st.nextToken();
		String tipoAlerta = st.nextToken();
		String tipo2      = st.nextToken();
		String evento     = st.nextToken();
		String cliente2   = st.nextToken();
		
		if( !cliente2.equals(".") ) cliente2 = " - " + cliente2;
		data = data.substring(5,7) + "/" + data.substring(8,10); 
%>
		<div style="margin-bottom: 8px; color: darkgray"><input type="checkbox" checked="checked" onclick="javascript:taskDone('<%= id %>', '<%= tipo2 %>', '0', <%= idCalendario %>)" style="margin-bottom: 5px; margin-right: 5px"><%= data %> - <%= evento %> <i><%= cliente2 %></i></div>
		
<%	} %>	
