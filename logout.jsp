<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.technique.engine.web.UserSession"%>

<%
	UserSession userSession = null;
	userSession = (UserSession) session.getAttribute("UserSession_Allgenda");

	if(userSession!=null){
		userSession.removeAttribute("loginAllgenda");
		userSession.removeAttribute("senhaMaster");
	}
	
	session.removeAttribute("UserSession_Allgenda");
	session.invalidate();
	
	out.println("<script>top.document.location.href='index.jsp'</script>");   
%>
