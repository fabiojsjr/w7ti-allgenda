<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.technique.engine.util.Base64"%>

<%
	try {
		if( request.getParameter("bypassLog") != null ) {
			String token = request.getParameter("bypassLog");
			token = new String( Base64.decode(token) );
			
			usuarioLogado = new UsuarioEntity();
			if( usuarioLogado.getThis("login = '" + token + "'") ) {
				if( usuarioLogado.getAtivo() == 1 ) {
					logado = true;
				} else {
					usuarioLogado = null;
				}
			} else {
				usuarioLogado = null;
			}
		}
	} catch( Exception e ){
		usuarioLogado = null;
	}
%>