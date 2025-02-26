<!DOCTYPE html>
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

	<div class="regpanel">
        <div class="pageheader">
	        <div class="logo animate0 bounceIn" style="margin-left: 0px; text-align: center;">
				<img src="images/logo/<%= ConfigEntity.CONFIG.get("logo") %>" alt="" />
			</div>
        </div>
		<div class="box-inverse" id="passwordRecoveryBox">
	    	<h4 class="widgettitle">Password recovery</h4>
	       	<div class="widgetcontent wc1">
	           	<form name="formRecuperarSenha" id="formRecuperarSenha" class="stdform" action="javascript:return false;" method="post"  enctype="multipart/form-data">
	           		<script type="text/javascript"> var form = document.formRecuperarSenha;</script>
	  	            <input type="hidden" name="sid" value="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "sid") %>" />
		            <input type="hidden" name="command" value="usuario" />
		            <input type="hidden" name="action" value="esqueci" />
		            <input type="hidden" name="no_cache" value="<%= System.currentTimeMillis() %>" />
					<div class="inputwrapper animate1 bounceIn">
		                <h4>Please inform your e-mail below</h4>
		                <br/>
		          		You will receive a message in your e-mail account with directions how to set-up a new password.
	          		</div>
	          		<hr>
	                <div class="control-group">
	                    <div class="controls">
	                    	<input type="text" name="emailLogin" id="email" class="input-xlarge" required />
   	                		<span id="respostaRecuperarSenha" class="customError" style="display: block !important;"> &nbsp; </span>
	          			</div>
	               	</div>
	               	<hr>
	                <p class="tcenter">
	                	<button class="btn btn-primary btn-large">Recover Password</button>
   	              		<button id="backPasswordRecovery" class="btn btn-primary btn-large">Back</button>
	                </p>
	        	</form>  
	       	</div>
		</div>
	</div>
	
	<div class="regfooter">
    	<p>&copy; 2016. <%= ConfigEntity.CONFIG.get("nome") %> ®</p>
    </div>
    
    <script>
    $("#backPasswordRecovery").click(function(e){
    	e.preventDefault();
    	window.location = "index.jsp";
    });
    $("#formRecuperarSenha").submit(function(e){
    	e.preventDefault();
    	esqueci(form);
    });
	$("#formRecuperarSenha").validate({
		rules: {
			emailLogin : "required"
		},
		messages: {
			emailLogin :  "<%=p.get("DIGITE_EMAIL_VALIDO")%>"
		},
		errorElement: "span",
	   	onclick: false,
	   	onfocusout: false,
		errorPlacement: function(error, element){
			if(element.attr("name") == "emailLogin"){
				$("#respostaRecuperarSenha").html(($.trim(error.html())));
			}
		}
	});

    </script>
</body>
</html>
