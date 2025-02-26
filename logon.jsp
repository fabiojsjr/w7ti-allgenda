<%@page import="com.tetrasoft.data.cliente.ConfigEntity"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.app.sender.SenderInterface"%>
<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
<!DOCTYPE html>
<html>
<head>
<% int versao = 1; %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Back Office - <%= ConfigEntity.CONFIG.get("nome") %> �</title>

<style>
	@-moz-document url-prefix() {
	    .inputwrapper input {
	        height: 33px !important;
   	 	}
	}
</style>

<link rel="stylesheet" href="css/style.<%= ConfigEntity.CONFIG.get("css") %>.css" type="text/css" ></link>
<link rel="icon" href="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "imageURL") %>favicon/<%= ConfigEntity.CONFIG.get("favicon") %>" type="image/png"></link>
<link rel="SHORTCUT ICON" href="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "imageURL") %>favicon/<%= ConfigEntity.CONFIG.get("favicon") %>"></link>

<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.1.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.min.js"></script>
<script type="text/javascript" src="js/modernizr.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/custom.js"></script>
<script src="js/site.js?versao=<%=versao %>" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.alerts.js"></script>
<script type="text/javascript">
    jQuery(document).ready(function(){
        jQuery('#login').submit(function(){
            var u = jQuery('#username').val();
            var p = jQuery('#password').val();
            if(u == '' && p == '') {
                jQuery('.login-alert').fadeIn();
                return false;
            }
        });
    });
</script>
</head>
<%@ include file="idioma.jsp" %>

<body class="loginpage" style="text-align:center !important;">

<%
	out.println("<div id='erro'>");
	if(request.getParameter("info") != null){
		out.println("<script>infoAlertTop('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("info")) == null ? request.getParameter("info") : p.get(request.getParameter("info")))+"'), function() {});</script>");
	}else if(request.getParameter("warning") != null){
		out.println("<script>warningAlertTop('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("warning")) == null ? request.getParameter("warning") : p.get(request.getParameter("warning")))+"'), function() {});</script>");
	}
	out.println("</div>");
%>

	<div style="margin: auto;">
	    <div class="" style="margin-top:10% !important; margin:auto;border: 0px solid red;width: 270px;">
	        <div class="logo" style="width: 270px;margin: auto; padding-bottom: 10px"><img src="images/logo/<%= ConfigEntity.CONFIG.get("logo") %>" alt="" /></div>
	        <form id="login" action="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "entryPoint") %>" method="post">
	            <input type="hidden" name="sid" value="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "sid") %>" />
	            <input type="hidden" name="command" value="usuario" />
	            <input type="hidden" name="action" value="login" />
	            <input type="hidden" name="no_cache" value="<%= System.currentTimeMillis() %>" />
	            
	            <div class="inputwrapper">
	                <input type="text" name="emailLogin" id="login" placeholder="User" required value="<%=request.getParameter("login") == null ? "" : request.getParameter("login")%>"/>
	            </div>
	            <div class="inputwrapper">
	                <input type="password" name="senhaLogin" id="senha" placeholder="Password" required value=""/>
	            </div>
	            <div class="inputwrapper">
	                <button name="submit">Log In</button>
	            </div>
	            <div class="inputwrapper " style="padding-top: 5px">
	                <div class="pull-right">
	                	<a href="recuperarSenha.jsp"><%= p.get("DontKnow") %></a>
	                </div>
	            </div>
	        </form>
	        
	    </div><!--loginpanelinner-->
	</div><!--loginpanel-->
	
	<div class="loginfooter">
	    <p>&copy; 2019. <%= ConfigEntity.CONFIG.get("nome") %> �</p>
	</div>

</body>
</html>
