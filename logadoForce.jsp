<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ include file="logado.jsp" %>

<%	if (usuarioLogado == null ) { %>
		<script type="text/javascript">location.href = "index.jsp";</script>
<%	
		return;
	}
%>