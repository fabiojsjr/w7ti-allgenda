<%@page import="com.tetrasoft.app.sender.SenderInterface"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.tetrasoft.data.cliente.ConfigEntity"%>
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="com.technique.engine.app.SystemParameter"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Properties"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title><%= ConfigEntity.CONFIG.get("nome") %> &reg;</title>
	<% int versao = 2; %>

	<meta http-equiv="Accept" content="text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" ></meta>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" ></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" ></meta>
	<link rel="stylesheet" href="css/style.<%= ConfigEntity.CONFIG.get("css") %>.css" type="text/css" ></link>
	<link rel="icon" href="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "imageURL") %>favicon/<%= ConfigEntity.CONFIG.get("favicon") %>" type="image/png"></link>
	<link rel="SHORTCUT ICON" href="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "imageURL") %>favicon/<%= ConfigEntity.CONFIG.get("favicon") %>"></link>
	
	<link rel="stylesheet" href="js/jquery/css/jquery.ui.slider.css"></link>
	<link rel="stylesheet" href="css/responsive-tables.css"></link>
	<link rel="stylesheet" href="css/uniform.tp.css"></link>
	<link rel="stylesheet" href="css/jquery.tagsinput.css"></link>
	<style type="text/css">
	body,td,th {
		font-family: RobotoRegular, "Helvetica Neue", Helvetica, sans-serif;
	}
	@-moz-document url-prefix() {
	    .inputwrapper input, .stdform input {
	        height: 32px !important;
   	 	}
   	 	
	}	
	</style>
	
	<!-- <script type="text/javascript" src="js/jquery-2.1.0.min.js"></script> -->
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/jquery-migrate-1.1.1.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.10.3.min.js"></script>
	
	<script type="text/javascript" src="js/fullcalendar.min.js"></script>
	<script type="text/javascript" src="js/modernizr.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/jquery.cookie.js"></script>
	<script type="text/javascript" src="js/jquery.uniform.min.js"></script>
	<script type="text/javascript" src="js/flot/jquery.flot.min.js"></script>
	<script type="text/javascript" src="js/flot/jquery.flot.resize.min.js"></script>
	<script type="text/javascript" src="js/responsive-tables.js"></script>
	<script type="text/javascript" src="js/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="js/custom.js"></script>
	<script type="text/javascript" src="js/jquery.jgrowl.js"></script>
	<script type="text/javascript" src="js/jquery.cookie.js"></script>
	<script type="text/javascript" src="js/chat.js"></script>
	<script type="text/javascript" src="js/jquery.alerts.js"></script>

	<!--  
	<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
	-->
	<script type="text/javascript" src="js/tinymce/jquery.tinymce.js"></script>
	<script  type="text/javascript" src="js/maskedinput.js"></script>
	<script  type="text/javascript" src="js/moment.js"></script>
	<script  type="text/javascript" src="js/textHandler.min.js"></script>
	<script  type="text/javascript" src="js/jquery.maskedinput-1.1.4.pack.js"></script>
	<!-- JS PARA FORMS -->
	<script type="text/javascript" src="js/jquery.tagsinput.min.js"></script>
	<script type="text/javascript" src="js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/charCount.js"></script>
	<script type="text/javascript" src="js/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.autogrow-textarea.js"></script>

	<!-- joyride tutorial -->
	<link rel=" stylesheet" type="text/css" href="/allgenda/css/joyride-2.1.css"></link>
	<script src="/allgenda/js/jquery.joyride-2.1.js"></script>
	
	
	<!-- timepicker -->
	<link href="css/timepicki.css" rel="stylesheet"></link>
	<link rel="stylesheet" href="css/bootstrap-timepicker.min.css" type="text/css" />
	<script type="text/javascript" src="js/bootstrap-timepicker.min.js"></script>
	
	
	<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="js/excanvas.min.js"></script><![endif]-->
	
	<!--  
	<script type='text/javascript' src='/allgenda/include/jquery.js'></script>
	<script type='text/javascript' src='/allgenda/include/jquery.validate.js'></script>
	-->
	
	<!-- ANTIGOS -->	
<!-- <link href="js/jquery/css/jquery.ui.all.css" rel="stylesheet" type="text/css" /> -->
	<script src="js/site.js?versao=<%=versao %>" type="text/javascript"></script>
	<script src="js/ajax.js" type="text/javascript"></script>
	<script src="js/ajaxUpload.js" type="text/javascript"></script>
	
	<link href="js/site.css?versao=<%=versao %>" rel="stylesheet" type="text/css" />
<!-- 	<script type='text/javascript' src='js/jquery/jquery.ui.core.js'></script> -->
	
<!-- 	<script type='text/javascript' src='js/jquery/jquery.ui.widget.js'></script> -->
	<script type='text/javascript' src='js/cycle.js'></script>
	
   	<script type="text/javascript" src="js/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<script type="text/javascript" src="js/fancybox/jquery.easing-1.3.pack.js"></script>
	<!-- inicia api google calendar  -->	
	<!-- <script async defer src="https://apis.google.com/js/api.js"></script>
	<script src="js/google-calendar-api.js"></script> -->	
</script>
<!-- 	Removido pois com ele não da pra rolar dentro do fancybox (Segundo o site opcional: Enable "mouse-wheel" to navigate throught gallery items) -->	
<!-- 	<script type="text/javascript" src="js/fancybox/jquery.mousewheel-3.0.4.pack.js"></script> -->
	<link rel="stylesheet" href="js/fancybox/jquery.fancybox-1.3.4.css" type="text/css" media="screen" />
	<!-- ANTIGOS -->
	
	
		<script type="text/javascript" src="js/jquery.smartWizard.min.js"></script>
	

	
	<% 	if( !request.getRequestURL().toString().contains("localhost") ) { %>
			<!-- Analytics -->
			<script>
				(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
				(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
				m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
				})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
				
				ga('create', 'UA-22767945-2', 'auto');
				ga('send', 'pageview');
			</script>
	<%	} %>
	
	<style>
	
		@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) and (orientation: portrait){
			html{
			background: #FFF;
		}		
	</style>
</head>


<%@ include file="logado.jsp" %>
<%@ include file="idioma.jsp" %>

	
<%
	SimpleDateFormat defaultSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat defaultTimeSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	SimpleDateFormat defaultTimeMinuteSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!--  
<% if(idioma.equals("PT") ){ %>
	<script type='text/javascript' src='js/jquery/jquery.ui.datepicker-pt-BR.js'></script>
<% }else if(idioma.equals("IT")){%>
	<script type='text/javascript' src='js/jquery/jquery.ui.datepicker-IT.js'></script>
<% }else if(idioma.equals("ES")){%>
	<script type='text/javascript' src='js/jquery/jquery.ui.datepicker-ES.js'></script>
<% }else { %>
	<script type='text/javascript' src='js/jquery/jquery.ui.datepicker-en-US.js'></script>
<% }%>
-->	

<script type='text/javascript' src='js/jquery/jquery.ui.datepicker-en-US.js'></script>


<body id="body">

<form action="javascript:return false;" method="post" name="formX" id="formX" enctype="multipart/form-data" class="stdform">
	<input type="hidden" name="sid" value="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "sid") %>"/>
	<input type="hidden" name="id" value="" />
	<input type="hidden" name="command" value="" />
	<input type="hidden" name="action" value="" />
	<input type="hidden" name="formMode" value=""/>
	<input type="hidden" name="chaveSite" value="<%=System.currentTimeMillis() + "" + System.currentTimeMillis()*2%>"/>
	
	<script type="text/javascript">formX = document.formX;</script>
