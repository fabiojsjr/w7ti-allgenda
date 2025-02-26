<%@page import="java.sql.Connection"%>
<%@page import="java.util.List"%>
<%@page import="com.tetrasoft.data.usuario.UsuarioEntity"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<select id="idUsuario" name="idUsuario" >
	<%
		UsuarioEntity entx = new UsuarioEntity(); 
	try{
		%>
	<% List<UsuarioEntity> lista = entx.getArray(" idUsuario <> 1"); %>
	<%for(UsuarioEntity us : lista){ %>
		<option value="<%= us.getIdUsuario() %>" ><%= us.getNome() %></option>
	<%} }catch(Exception ex){%>
		<option value="0" >-- Falha ao recuperar --</option>
	<%}%> 
</select>

<script>
	jQuery('#idUsuario').chosen();
</script>