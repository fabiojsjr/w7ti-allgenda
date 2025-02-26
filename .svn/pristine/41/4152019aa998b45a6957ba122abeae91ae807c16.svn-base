<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.text.SimpleDateFormat"%>

<link rel="stylesheet" href="css/responsive-tables.css">
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/responsive-tables.js"></script>

<%@ include file="logadoForce.jsp" %>
<%@ include file="idioma.jsp" %>
<%@ include file="methods.jsp" %>

<ul class="breadcrumbs">
	<li><a style="color : black;" href="painel.jsp"><i class="iconfa-user"></i></a> <span class="separator"></span></li>
	<li><%= p.get("ADMINISTRACAO") %><span class="separator"></span></li>
	<li><b>Previsão do Tempo</b></li>
</ul>

<div class="pageheader" onclick="javascript:abre('miolo', 'previsaoTempo.jsp')" style="cursor: pointer;">
	<div class="pageicon"><span class="iconfa-bolt"></span></div>
	<div class="pagetitle">
		<h5> &nbsp;</h5>
		<h1>Previsão do Tempo</h1>
	</div>
</div>


<div class="contentTable">
	<div class="clear20"></div>

	<div>
		<iframe id="forecast_embed" type="text/html" frameborder="0" height="245" width="100%" src="http://forecast.io/embed/#lat=-23.030672&lon=-46.983761&name=Vinhedo,%20SP&color=#BF7318&font=Arial&units=uk"> </iframe>
	</div>
</div>

