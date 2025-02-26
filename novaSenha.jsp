<!DOCTYPE html>
<%@page import="com.technique.engine.util.Base64"%>
<%@page import="com.tetrasoft.app.sender.SenderInterface"%>
<%@page import="com.technique.engine.app.SystemParameter"%>
<%@page import="com.tetrasoft.data.cliente.ConfigEntity"%>
<html>
<head>
<% int versao = 1; %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Back Office - <%= ConfigEntity.CONFIG.get("nome") %> ®</title>
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
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script src="js/site.js?versao=<%=versao %>" type="text/javascript"></script>
<script src="js/ajax.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.alerts.js"></script>
<link rel="stylesheet" href="js/site.css" type="text/css" ></link>
<script type="text/javascript" src="js/jquery.alerts.js"></script>

</head>

<body class="cadastro">
<%@ include file="idioma.jsp" %> 
	<%
		String tipo  = request.getParameter("t");
		String token = request.getParameter("token");
		String idUsuario = "";
		String email = "";
		String email2 = "";
		
		if(token!=null){
			idUsuario = token.substring(0, token.indexOf("|"));
			email = token.substring(token.indexOf("|")+1, token.length());
			email2 = new String( Base64.decode(email) );
		}
	%>
	<div class="regpanel">
        <div class="pageheader">
	        <div class="logo animate0 bounceIn" style="margin-left: 0px; text-align: center;">
				<img src="images/logo/<%= ConfigEntity.CONFIG.get("logo") %>" alt="" />
			</div>
        </div>
		<div class="box-inverse" id="passwordRecoveryBox">
	    	<h4 class="widgettitle"><%= tipo.equals("1") ? "Reset Password" : "Alterar Senha Master"%></h4>
	       	<div class="widgetcontent wc1">
	           	<form name="formAlterarSenha" id="formAlterarSenha" class="stdform" action="javascript:return false;" method="post"  enctype="multipart/form-data">
	           		<script type="text/javascript"> var form = document.formAlterarSenha;</script>
	  	            <input type="hidden" name="sid" value="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "sid") %>" />
		            <input type="hidden" name="command" value="usuario" />
		            <input type="hidden" name="action" value="alterarSenha" />
		            <input type="hidden" name="no_cache" value="<%= System.currentTimeMillis() %>" />
	            	<input type="hidden" name="tipo" value="<%= tipo %>">
					<input type="hidden" name="idUsuario" value="<%= idUsuario %>">
					<input type="hidden" name="email" value="<%= email %>">
					
					<div class="inputwrapper animate1 bounceIn">
		                <h4>Please change your password below</h4>
	          		</div>
	          		<hr>
	                <div class="control-group">
	                   	Username 
	                    <div class="controls">
	                    	<input type="text" name="login" id="login" value="<%= email2 %>" class="input-xlarge" required />
	          			</div>
	                   	<%= tipo.equals("1") ? "New Password" : "New Master Password"%>
	                    <div class="controls">
	                    	<input type="password" name="password" id="password" class="input-xlarge" required />
	          			</div>
                 		Confirm Password
	                    <div class="controls">
	                    	<input type="password" name="confirm" id="confirm" class="input-xlarge" required />
   	                		<span id="respostaNovaSenha" class="customError" style="display: block !important;"> &nbsp; </span>
	          			</div>
	               	</div>
	               	<hr>
	                <p class="tcenter">
	                	<button class="btn btn-primary btn-large">Change Password</button>
   	              		<button id="backChangePassword" class="btn btn-primary btn-large">Back</button>
	                </p>
	        	</form>  
	       	</div>
		</div>
	</div>
	
	<div class="regfooter">
    	<p>&copy; 2016. <%= ConfigEntity.CONFIG.get("nome") %> ®</p>
    </div>
    
    <script>
    $("#backChangePassword").click(function(e){
    	e.preventDefault();
    	window.location = "index.jsp";
    });
    $("#formAlterarSenha").submit(function(e){
    	e.preventDefault();
    	alterarSenha(form);
    });
    
	$.validator.addMethod("checkPassword", function (value, element) {
		var password = $("input[name=password]").val();
		var confirmacao = $("input[name=confirm]").val();
		if(password == null || confirmacao == null) return false;
		if(password == '' || confirmacao == '') return false;
        return password == confirmacao;
	});
    
	$("#formAlterarSenha").validate({
		groups: {
			password: "password confirm",
		},
		rules: {
			password: {
				checkPassword : true,
				required: true
			},
			confirm: {
				checkPassword : true,
				required: true
			}
		},
		messages: {
			password: {
				checkPassword: "Password don't match",
				required: "Please inform your password"
			},			
			confirm: {
				checkPassword: "Password don't match",
				required: "Please confirm your password"
			}
		},
		errorElement: "span",
	   	onclick: false,
	   	onfocusout: false,
	   	onkeyup: false,
		errorPlacement: function(error, element){
			$("#respostaNovaSenha").html(($.trim(error.html())));
		}
           
	});

    </script>
</body>
</html>
