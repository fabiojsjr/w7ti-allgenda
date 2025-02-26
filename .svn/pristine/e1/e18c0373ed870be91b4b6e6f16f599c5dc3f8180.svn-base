<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>

<%	
	String id    = request.getParameter("id");
	String path  = request.getParameter("path");
	String dimen = request.getParameter("dimen");
	
	if( dimen == null ) dimen = "";
%>

<div class="widgetbox profile-photo">
	<div class="headtitle">
		<div class="btn-group">
			<span data-toggle="dropdown" class="btn dropdown-toggle">Ações <span class="caret"></span></span>
			<ul class="dropdown-menu">
<!-- 				Enviar Imagem ou ZIP -->
<%-- 				<li><a href="#"  <%= request.getParameter("zip") == null ? "accept='image/*'" : "accept='.zip'" %>id="changeProfilePhoto"><%= request.getParameter("zip") == null ? "Enviar Imagem" : "Enviar ZIP" %></a></li> --%>
				<li><a href="#" id="changeProfilePhoto"><%= request.getParameter("zip") == null ? "Enviar Imagem" : "Enviar ZIP" %></a></li>
<!-- 				Botão Remover só irá aparecer quando adiciona ou altera -->
				<%= request.getParameter("zip") == null ? "<li><a href='#' id='removePhoto'>Remover Imagem</a></li>" : ""%>
				
				<script>
					$("#changeProfilePhoto").click(function(){
						document.getElementById('uploadPhotoIframe').contentWindow.clickFoto();
					});
					$("#removePhoto").click(function(){
				 		document.getElementById('uploadPhotoIframe').contentWindow.removeFoto();
				 		if(confirm("Deseja realmente apagar?")){
				 			form.command.value = "cliente";
							form.action.value = "deleteMidia";
							
							$.ajax({
								type: 'POST',
								url: createQueryString(form),
								dataType: 'html',
						        contentType: "application/x-www-form-urlencoded;charset=ISO-8859-1",
						        processData: false,
								success: function(data){
									$('#erro').html(data);
								}
							});
				 		}
					});
			  	</script>
			</ul>
		</div>
		<h4 class="widgettitle">Imagem</h4>
	</div>
	<div class="widgetcontent">
		<div class="profilethumb">
			<iframe src="uploadFile.jsp?path=<%= path %>&id=<%=id %>&nc=<%=new Date().getTime() %>" style="border: 0 none; width: 249px; max-width:249px;" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="uploadPhotoIframe"></iframe>
			<br/>
			<b><%= dimen %></b>
		</div>
	</div>
</div>
