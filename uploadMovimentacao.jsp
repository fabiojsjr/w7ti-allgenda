<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head><title>Enviar Arquivos</title></head>
<link rel="stylesheet" href="css/responsive-tables.css">

<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.min.js"></script>
<script type="text/javascript" src="js/ajaxUpload.js"charset="UTF-8"></script>
<%@ include file="logadoForce.jsp"%>
<%@ include file="idioma.jsp"%>
<%@ include file="methods.jsp"%>

<div class="maincontent">
<form action="javascript:return false;" method="post" name="formUpload" id="formUpload" enctype="multipart/form-data" style="display: none; visibility: none; position: absolute; width: 0px; height: 0px;">
	<input type="hidden" name="sid" value="allgenda"/>
	<input type="hidden" name="id" value="" />
	<input type="hidden" name="idUsuario" value="<%= usuarioLogado.getIdUsuario() %>" />
	<input type="hidden" name="command" value="" />
	<input type="hidden" name="action" value="" />
	<input type="hidden" name="formMode" value=""/>
	<input type="hidden" name="chaveSite" value="<%=System.currentTimeMillis() + "" + System.currentTimeMillis()*2%>"/>
	<input type="hidden" name="historical" value=""/>
	
	<script type="text/javascript">formX = document.formUpload;</script>
	<div id="ajaxUploadDiv" style="text-align: center;">
		<iframe name="ifajaxUpload" style="display:none;"></iframe>
		<input type="hidden" name="ajaxUploadPath" value="<%= request.getParameter("importacao") == null ? "upload/"+usuarioLogado.getIdUsuario()+"/preUpload/" : "upload/importacao/"+usuarioLogado.getIdUsuario()+"/" %>" />
		<input type="hidden" name="ajaxUploadRenameTo" value=""/>
		<input type="hidden" name="ajaxUploadExcecao" value="<%= request.getParameter("importacao") == null ? "1"  : "3"%>"/>
		<input type="hidden" id="ajaxUploadBk" name="ajaxUploadBk" value=""/>
		<div id="ajaxResult">
		</div>
		<div id="ajaxUpload0">
			<div id="ajaxUploadProg1">
				<input id="arquivo1" name="arquivo1"  type="file" onchange="beforeUpload();" style="border: 0px; display: none"  />
			</div>
			<div id="ajaxUpload1" ></div>
		</div>
	</div>
</form>
</div>
<div id="erro"></div>
<div style="display: none; visibility: none; position: absolute; width: 0px; height: 0px;" id="errorUp"></div>
<ul class="list-unordered list-checked downloadList">
</ul>
<script>
$( "#tabs" ).tabs({selected : 1});
function beforeUpload(){
	var arquivo = $("input[name=arquivo1]");
	var logado = <%= usuarioLogado.getIdUsuario() %>
	var nome = arquivo[0].files[0].name;
	var extension = arquivo.val().substring(arquivo.val().length-3,arquivo.val().length);

	$.get('documentoDataAjax.jsp',{user:logado,file:nome},function(res,data,status){
	var data = $.trim(res);
	var response = false;
		if(data == "true"){
			response = true;
		}else{
			response = false;
		}
							
		if(arquivo.val().indexOf(".") < 0){
			window.parent.warningAlert("Mensagem", "Erro, Arquivo inv�lido" , function(){});
		}else if(!validateExtension(extension)){
			window.parent.warningAlert("Mensagem", "Erro, Arquivos do tipo: "+getNomeExtension(extension)+" n�o s�o permitidos.", function(){});
		}else if(response){
			window.parent.warningAlert("Mensagem", "Erro, Este arquivo j� foi enviado." , function(){
				window.parent.reloadFiles();
			});
			return false;
		}else{
			var file;
			try{
				if(!arquivo[0].files[0]){
					window.parent.warningAlert("Mensagem", "Erro, Arquivo inv�lido" , function(){});
					return;
				}else{
				file = arquivo[0].files[0];
					if(file.size == 0 ){
						window.parent.warningAlert("Mensagem", "Erro, tamanho do arquivo inv�lido" , function(){});
						return;						
					}
				}
			}catch(err){
				return;
			}
			
			ajaxUploadThis(1, "&idUploader="+<%= usuarioLogado.getIdUsuario() %>+"&arquivo1="+$("input[name=arquivo1]").val()+"&tipo="+<%= request.getParameter("tipo") %>);
			 
		}
	});
}

function triggerButton(){
	$("#arquivo1").trigger("click");
}

$(".removeDocument").click(function() {
	$.get('deleteDocument.jsp?id='+$(this).attr("data-id")+'&fileName=&nc=<%=new Date().getTime()%>&idRelatorioTecnico=1&tipo=relatorioTecnicoDoc', function(data){
		window.parent.infoAlert("Mensagem", $.trim(data) , function(){
			window.parent.refreshPage();
		});
	});   
});

function validateExtension(data){
	if(data == 'exe' || data == 'jsp' || data== 'bat' || data == '.js'|| data == 'php'|| data == 'class'|| data == 'java'|| data == 'do'){
		return false;
	}
	return true;
}
function getNomeExtension(extension){
	if(extension == '.js'){
		return "JavaScript";
	}else if(extension == 'exe'){
		return "Executavel";
	}else if(extension == 'jsp'){
		return "Java Server Page";
	}else if(extension == 'bat'){
		return "Executavel DOS";
	}else{
		return "Selecionado";
	}
}
</script>