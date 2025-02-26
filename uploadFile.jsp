<%@page import="com.tetrasoft.web.cliente.ClienteCommand"%>
<%@page import="com.technique.engine.web.CommandWrapper"%>
<%@page import="com.tetrasoft.web.basico.CadastroWrapper"%>
<%@page import="com.tetrasoft.util.Zip"%>
<%@page import="com.tetrasoft.web.ajax.AjaxUploadServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.cliente.ConfigEntity"%>
<%@page import="com.technique.engine.app.SystemParameter"%>
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
	String id   = request.getParameter("id");
	String path = request.getParameter("path");
	
	if( !path.endsWith("/") ) path += "/";
	
	boolean gallery = (request.getParameter("gallery") == null ? false : true);
%>

<script language="JavaScript">
	function clickFoto() {
		$(':file').trigger("click");
	}
	
	function changeUploader() {
		var idNovo = new Date().getTime();
		parent.document.getElementById("idUploader").value = idNovo;		
		document.getElementById("renameTO").value = idNovo;
		document.getElementById("uploadDOC").value = "";
	}
	
	function hideButton() {
		document.getElementById("botaoGallery").style.display = "none";
	}
</script>

<body style="background: white; width: 100%">

<form action="javascript:return false;" method="post" name="formX" id="formX" enctype="multipart/form-data" class="stdform">
	
	<%	if( gallery ) { %>
			<button class="btn btn-primary" id="botaoGallery" onclick="javascript:clickFoto();changeUploader();">Selecione o arquivo que deseja enviar</button>
	<%	} %>

	<input type="hidden" name="sid" value="Allgenda"/>
	<input type="hidden" name="id" value="" />
	<input type="hidden" name="command" value="" />
	<input type="hidden" name="action" value="" />
	<input type="hidden" name="formMode" value=""/>
	<input type="hidden" name="chaveSite" value="<%=System.currentTimeMillis() + "" + System.currentTimeMillis()*2%>"/>
	
	<script type="text/javascript">formX = document.formX;</script>

	<div id="ajaxUploadDiv" style="text-align: center;">
		<iframe name="ifajaxUpload" style="display:none;"></iframe>

		<input type="hidden" name="ajaxUploadPath" 		value="<%= path %>" />
		<input type="hidden" name="ajaxUploadRenameTo" 	id="renameTO" value="<%= id %>" />
		<input type="hidden" name="ajaxUploadExcecao" 	value="" />
		<input type="hidden" name="uploadDocumentos" 	id="uploadDOC" value="2" /> <!-- 2 = imagem -->
		<input type="hidden" name="mensagemDeletar" 	value="<%=p.get("CONFIRMACAO_DELETAR") %>" />

		<div id="ajaxResult" class="tcenter">
			<%
				String caminho = SystemParameter.get(SenderInterface.SID, "systemProperties", "filePath") + path;

				String  ff = "";
				boolean fileFound = false;
				boolean isZip = false;
				
				String[] extensions = AjaxUploadServlet.EXTENSION;
				for( String ext : extensions ) {
					ff = caminho + id + "." + ext;
					
					if( new File(ff).exists() ) {
						if( ext.equals("zip") ) isZip = true;
						fileFound = true;
						break;
					}
				}
				
				if( fileFound ) {
					ff   = ff.substring( ff.lastIndexOf("/") + 1 );	
					if(isZip){
						%>
						<script type="text/javascript">
							alert("Imagens cadastradas com sucesso!");
						</script>
						<%
						// Ao contr�rio de mostrar a img de um avatar, mostrar uma img galleryZip, para demonstrar que possui mais de uma imagem
						path = "";
						ff   = "/allgenda/images/galleryZip.png";
// 						Code abaixo apaga o zip da pasta m�dia
						File apagar = new File(caminho+"/"+id+".zip");
						apagar.delete();
					}					
				} else {
					path = "";
					ff   = "/allgenda/images/blankperson.jpg";
				}
			
				try { %>
					<script>
						function removeFoto() {
							location.href = 'deleteDocument.jsp?fileName=<%=ff%>&path=<%= path %>&nc=<%=new Date().getTime()%>';
						}						
					</script>
						
				<%
				
					if( !gallery ) {
						out.print("<a target='_blank' href='" + path + ff + "'>" +
								  "		<img id='foto' src='" + path + ff + "?nc="+new Date().getTime()+"' style='max-height: 150px;' border='0'/>" +
								  "</a>"
						);
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
</form>

</body>
</html>
