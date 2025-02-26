<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@ include file="logadoForce.jsp" %>
<%@ include file="idioma.jsp" %>
<%@ include file="methods.jsp" %>

<input type="hidden" name="ajaxUploadRenameTo" value="" />
<script type="text/javascript" src="js/colorpicker.js"></script>
<input type="hidden" name="ajaxUploadExcecao" value="4" />
<input type="hidden" name="uploadDocumentos" value="4" />
<input type="hidden" name="ajaxUploadPath" value="upload/documento/<%=usuarioLogado.getIdUsuario()%>/" />
<input type="hidden" name="idUploader" value="<%= usuarioLogado.getIdUsuario() %>"/>
<input type="hidden" name="fileOver" value=""/>
<div id="controlUpload"></div>
<div  style="border:0px solid red;background-color: #E3E0DC;">
<div class="pageHr"></div>
<%
	String msg = request.getParameter("msg");
	boolean isNovo = false;

	try {
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), 0l, "BANNER");
%>	
		<div class="clear20"></div>
<div class="contentTable">
	<div>&nbsp;</div>
			<p>		
			    <label class="control-label" ><%= p.get("ARQUIVO") %></label>
					<span class="btn btn-file" id="previewUpload" style="display : none;" ><a id="showThis" target="_blank" >Preview</a></span>
	                            	<p>
                             	<input disabled id="fileName" style="width : 90px;" name="fileName" class="fileupload-preview input-medium"  type="text" id="appendedDropdownButton" class="span2" value="<%= p.get("SELECIONE") %>" />
	                            		<a  id="uploadButton" href="#" class="btn btn-circle"><i class="icon-upload"></i></a><img id="load" src="images/load.GIF" style="display : none;"/>
										<a id="imgMax"	style="display : none;" class="btn btn-circle grouped_elements"><i class="icon-resize-full grouped_elements" ></i></a>
										<a id="imgCancel"   style="display : none;" class="btn btn-circle"><i class=" icon-remove" ></i></a>
									</p>
			</p>
			    </div>
	
<div class="clear20"></div>
	<li style="display : none;" class="right newfilebtn" style="border:0px solid red;">
		<div id="uploadStatus" style="display : none;">
			<div class="progress progress-striped active inline">
				<div class="bar" style=""></div>
				<iframe id="uploader" src="adminUploadBanner.jsp" style="width: 100%;"></iframe>
				<script type="text/javascript">
					var uploader = document.getElementById("uploader");
				</script>
			</div>
			<a href="javasript:void(0)" id="stopUpload"><i class="iconfa-remove"></i></a>
		</div>
	</li>
</div>
<script>

var janela;

function updateProgressBar(data) {
		var fileName = data.fileName === undefined ? "" : data.fileName;
		var percent = data.percent === undefined ? 0 : data.percent;

		if (fileName != "") {
			jQuery("#uploadedFileName").val(fileName);
		}

		var finish = false;
		if (percent >= 100) {
			finish = true;
			percent = 100;
		} else if (percent < 0) {
			finish = true;
			percent = 0;
		}
		
		jQuery("#uploadStatus div.bar").css("width", percent + "%");
		
		if (finish) {
			setTimeout(function(){
				jQuery("#uploadStatus").hide();
			}, 1000);
		}
}

<%if(isNovo){%>	
	function complete(data,fileName){
		jQuery("#load").hide();
		jQuery(".rightpanel").css('cursor','auto');
		jQuery(".btn").prop('disabled',false);
		jQuery(".btn").css('cursor','pointer');
		jQuery("#previewData").attr('src',data);
		jQuery("#previewData").toggle();
		jQuery("#fileName").val(fileName);
		jQuery("input[name=fileOver]").val(fileName);
		jQuery("#controlUpload").append("<input type='hidden' id='sended' name='changeBanner' value='true'/>");
		jQuery("#imgMax").toggle();
		jQuery("#imgMax").attr('href','imagePreview.jsp?imageData='+data);
		jQuery("#imgCancel").toggle();
		jQuery("#uploadButton").toggle();
// 		janela = moldaDialog({title : 'Image Preview',content : "<img src='"+data+"' alt=''><a id='imgCancel'   style='display : none;' class='btn btn-circle'><i class=' icon-remove' ></i></a>"});
// 		janela.create();
// 		janela.show();
	}
<%}else{%>
 function complete(data,fileName){
	jQuery("#load").hide();
	jQuery(".rightpanel").css('cursor','auto');
	jQuery(".btn").prop('disabled',false);
	jQuery(".btn").css('cursor','pointer');
	jQuery("#previewData").attr('src',data);
	jQuery("#fileName").val(fileName);
	jQuery("input[name=fileOver]").val(fileName);
	jQuery("#controlUpload").append("<input type='hidden' id='sended' name='changeBanner' value='true'/>");
	jQuery("#imgMax").attr('href','imagePreview.jsp?imageData='+data);

	
}
<%}%>

	jQuery("#imgCancel").click(function(event){
		jConfirm('<%= p.get("CANCELAR_ENVIO") %>','<%= p.get("ATENCAO") %>',function(response){
			if(response){
				abre('mioloNovo','adminBannerEdit.jsp');	
			}
		});
	});
	
	function startProgressBar(data) {

		jQuery("#load").show();
		jQuery(".btn").prop('disabled',true);
		jQuery(".btn").css('cursor','wait');
		jQuery(".rightpanel").css('cursor','wait');
		jQuery("#fileName").val('<%= p.get("CARREGANDO") %>');
		
		var fileName = data.fileName === undefined ? "" : data.fileName;
		var percent = 0;

		if (fileName != "") {
			jQuery("#uploadedFileName").val(fileName);
		}
		
		jQuery("#uploadStatus div.bar").css("width", percent + "%")
		jQuery("#uploadStatus").show();
		
	}

	jQuery("#stopUpload").click(function() {
		stopUpload();
	});

	jQuery("#uploadButton").click(function(e) {
		e.preventDefault();
		uploader.contentWindow.triggerButton();
		jQuery('#sended').remove();
	});
	
	jQuery('#btnSend').click(function(event){
		event.preventDefault();
		var dataFile = jQuery.trim(jQuery('#fileName').val());
		var tipo = jQuery('select[name=tipo]').val() > 0;
		
		if(jQuery('input[name=titulo]').val() != '' && dataFile != 'Select' && dataFile != 'Selecione'  && dataFile != 'Selecionar' && tipo){
			validateHtml();
			inserirBanner(formX);
		}else{
			warningAlert('Mensagem:','<%= p.get("PREENCHER") %>',function(){});
		}
	});
	
	function validateHtml(){
		var inputs = [jQuery('input[name=titulo]'),jQuery('input[name=sub]'),jQuery('input[name=corTitulo]'),jQuery('input[name=corFundo]'),jQuery('select[name=tipo]'),jQuery('input[name=link]')];
		for(var a = 0; a < inputs.length; a++){
			inputs[a].val(new textHandler(inputs[a].val()).removeHtmltags());
		}
	}
</script>
<%
	} catch( Exception e ) {
		e.printStackTrace();
	} finally {
	}
%>
</div>