<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.cliente.MensagemEntity"%>

<%@ include file="logado.jsp"%>

<%
	MensagemEntity msg = new MensagemEntity();
	long mensagensNovas2 = 0;

	mensagensNovas2 = msg.getCount("tipo = " + MensagemEntity.TIPO_CORREIO + " AND status = " + MensagemEntity.STATUS_NAO_LIDO + " AND (idDestinatario = " + usuarioLogado.getIdUsuario() + " OR idDestinatario = 0)");
%>

<input type="text" name="mensagensNovas2" value="<%= mensagensNovas2 %>" style="width: 15px" />