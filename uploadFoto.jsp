<%@page import="com.tetrasoft.data.cliente.ConfigEntity"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="com.technique.engine.app.SystemParameter"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Properties"%>
<%@page import="java.io.File"%>    
<%@page import="java.util.Date"%>    
<%@page import="com.tetrasoft.app.sender.SenderInterface" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title><%= ConfigEntity.CONFIG.get("nome") %> &reg;</title>
	<% int versao = 2; %>
	
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" ></meta>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" ></meta>
	<link rel="stylesheet" href="css/style.<%= ConfigEntity.CONFIG.get("css") %>.css" type="text/css" ></link>
	<link rel="icon" href="/allgenda/images/favicon/<%= ConfigEntity.CONFIG.get("favicon") %>" type="image/png"></link>
	<link rel="SHORTCUT ICON" href="/allgenda/images/favicon/<%= ConfigEntity.CONFIG.get("favicon") %>"></link>
	
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/jquery-migrate-1.1.1.min.js"></script>
	
	<script type="text/javascript" src="js/jquery-ui-1.10.3.min.js"></script>
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
	
	<script src="js/site.js?versao=<%=versao %>" type="text/javascript"></script>
	<script src="js/ajax.js" type="text/javascript"></script>
	<script src="js/ajaxUpload.js" charset="UTF-8"></script>
	
	<link href="js/site.css?versao=<%=versao %>" rel="stylesheet" type="text/css" />
	<script type='text/javascript' src='js/jquery/jquery.ui.core.js'></script>
</head>

<%@ include file="logado.jsp" %>
<%@ include file="idioma.jsp" %>

<%
	UsuarioEntity toChange = new UsuarioEntity(); 
	if(request.getParameter("idUsuario") != null && !request.getParameter("idUsuario").equals("")){
		if(!toChange.getThis("idUsuario = "+request.getParameter("idUsuario"))){
			return;
		}
	}else{
		toChange = usuarioLogado;
	}

	String renameTo = request.getParameter("rename");
	if( renameTo == null ) renameTo = "perfil";
%>

<script language="JavaScript">
	function clickFoto() {
		$(':file').trigger("click");
	}
</script>

<body style="background: white; width: 100%">

<form action="javascript:return false;" method="post" name="formX" id="formX" enctype="multipart/form-data" class="stdform">
	<input type="hidden" name="sid" value="Allgenda"/>
	<input type="hidden" name="id" value="" />
	<input type="hidden" name="command" value="" />
	<input type="hidden" name="action" value="" />
	<input type="hidden" name="formMode" value=""/>
	<input type="hidden" name="chaveSite" value="<%=System.currentTimeMillis() + "" + System.currentTimeMillis()*2%>"/>
	
	<script type="text/javascript">formX = document.formX;</script>

	<div id="ajaxUploadDiv" style="text-align: center;">
		<iframe name="ifajaxUpload" style="display:none;"></iframe>
		<input type="hidden" name="ajaxUploadPath" value="upload/usuario/<%=toChange.getIdUsuario()%>/foto/" />
		<input type="hidden" name="ajaxUploadRenameTo" value="<%= renameTo %>" />
		<input type="hidden" name="ajaxUploadExcecao" value="" />
		<input type="hidden" name="uploadDocumentos" value="2" />
		<input type="hidden" name="mensagemDeletar" value="<%=p.get("CONFIRMACAO_DELETAR") %>" />
		
		<div id="ajaxResult" class="tcenter">
			<%
				String caminho = SystemParameter.get(SenderInterface.SID, "systemProperties", "filePath")+"upload/usuario/"+toChange.getIdUsuario()+"/foto/";
				File folder = new File(caminho);

				File[] file = folder.listFiles();
				try{
					if(file != null && file.length >= 1 ){
						int index = 0;
						String ff = file[index].getName();
						if( ff.contains("svn") ) ff = file[++index].getName();
						
						if( renameTo.equals("perfil") ) {
							if( ff.contains("assinatura") ) ff = file[++index].getName();
							ff = "perfil" + ff.substring( ff.indexOf(".") );
						} else if( renameTo.equals("assinatura") ) {
							ff = "assinatura" + ff.substring( ff.indexOf(".") );
						}
					%>
						<script>
							function removeFoto() {
								location.href = 'deleteDocument.jsp?fileName=<%=ff%>&profilePicture=1&nc=<%=new Date().getTime()%>'
							}						
						</script>
						
						<% 
							out.print("<a target='_blank' href='upload/usuario/"+toChange.getIdUsuario()+"/foto/"+ff+"'>"
									+ "		<img id='foto' src='upload/usuario/"+toChange.getIdUsuario()+"/foto/"+ff+"?nc="+new Date().getTime()+"' style='max-height: 150px;' border='0'/>"
									+ "</a>"
						);
					}else{
						out.print("<img id='foto' src='/allgenda/images/blankperson.jpg' height='100' />");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			%>
		</div>
		
		<div id="ajaxUpload0">
			<div id="ajaxUploadProg1">
				<input name="arquivo1" type="file" onchange="ajaxUploadThis(1);" style="border: 0px; display: none"  />
			</div>
			<div id="ajaxUpload1" ></div>
		</div>
		
		<div class="clear10"></div>
	</div>


<script>
	window.onload = function() {
		if(parent != null && parent != undefined)
			parent.autoSize(document.getElementById('foto').clientHeight);
	};
</script>
