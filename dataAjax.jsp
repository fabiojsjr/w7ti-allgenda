<%@page import="com.technique.engine.util.ExceptionWarning"%>
<%@page import="com.tetrasoft.data.usuario.PacienteEntity"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%

	boolean isCpf = request.getParameter("cpf") != null;
	boolean isPacienteData = request.getParameter("idPaciente") != null;
	
	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");
	
	if(isPacienteData){
		response.getWriter().write(new PacienteEntity().getPacienteData(request.getParameter("idPaciente")));
	}else if(isCpf){
		response.getWriter().write(new PacienteEntity().checkCpf(request.getParameter("cpf")) == true ? "true" : "false");
	}else{
		response.getWriter().write(" 404 not found! ");
	}
	
	
%>