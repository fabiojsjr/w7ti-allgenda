<%@page import="com.tetrasoft.data.cliente.SuporteEntity"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.cliente.MultinivelEntity"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>

<%@ include file="logado.jsp" %>
<%@ include file="idioma.jsp" %>

<script>$('#formX').removeData('validator');</script>

<%	
	String idDestinatario = request.getParameter("idDestinatario"); 
	
	out.println("<div id='erro'>");
	if(request.getParameter("info") != null){
		out.println("<script>infoAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("info")) == null ? request.getParameter("info") : p.get(request.getParameter("info")))+"'), function() {});</script>");
	}else if(request.getParameter("warning") != null){
		out.println("<script>warningAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("warning")) == null ? request.getParameter("warning") : p.get(request.getParameter("warning")))+"'), function() {});</script>");
	}
	out.println("</div>");
%>

<ul class="breadcrumbs">
	<li><a href="painel.jsp"><i class="iconfa-home"></i></a> <span class="separator"></span></li>
	<li><%= p.get("HOME") %><span class="separator"></span></li>
	<li><b><%= p.get("SUPORTE") %></b></li>
</ul>

<div class="pageheader" onclick="javascript:abre('miolo', 'suporteEnviar.jsp?idDestinatario=<%= idDestinatario %>')" style="cursor: pointer;">
	<!--  
	<span class="searchbar">
		<input type="text" name="lojaBusca" placeholder="Busque e pressione ENTER..." onkeypress="return buscaLoja(this.value, false, event, 0)" onblur="javascript:buscaLoja(this.value, true, event, 0);" />
	</span>
	-->

	<div class="pageicon"><span class="iconfa-question"></span></div>
	<div class="pagetitle">
		<h5><%=p.get("ESCREVA_ABAIXO_MENSAGEM_DESEJADA") %></h5>
		<h1><%= p.get("SUPORTE") %></h1>
	</div>
</div>

<%	if (usuarioLogado == null ) { %>
		<script type="text/javascript">location.href = "index.jsp";</script>
<%	
		return;
	}

	Connection conn = null;
	try {
		conn = usuarioLogado.retrieveConnection();
		
		usuarioLogado.getThis("idUsuario="+usuarioLogado.getIdUsuario(), conn);

		UsuarioEntity usuario = new UsuarioEntity();
		MultinivelEntity mn = new MultinivelEntity();
		
		SuporteEntity os = new SuporteEntity();
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), os.getTableName(), "idSuporte", request.getRemoteAddr(), request.getLocalName(), 0l, "Usuario acessou Suporte (enviar)", conn );
%>

<div class="centro" style="text-align: left">
	<div class="maincontentinner">
		
		<div class="row-fluid">
			<div id="dashboard-left" class="span12">
				<h5 class="subtitle">&nbsp;</h5>
				<div class="widgetbox login-information">
					<h4 class="widgettitle"><%=p.get("CRIAR_NOVO_CHAMADO") %></h4>
					
					<div class="widgetcontent">
						<p>
							<label><%=p.get("DE") %>:</label>
							<input type="text" name="DENOME" class="input-xlarge" value="<%= usuarioLogado.getNome() %>" readonly="" /> 
						</p>
						
						<p class="control-group">
							<label><%=p.get("ASSUNTO") %>:</label> 
							<select data-placeholder="Selecione abaixo o assunto" style="width: 350px;" class="chzn-select chzn-done" tabindex="-1" name="ASSUNTO" id="selWTI">
								<%	for( int i = 0; i < SuporteEntity.assuntos.length; i++ ) { %> 
										<option value="<%= i == 0 ? "" : i %>"><%= p.get(SuporteEntity.assuntos[i]) %></option>
								<%	} %> 	
							</select>
						</p>
						
						<div class="maincontent">
							<textarea id="elm1" name="elm1" rows="15" cols="80" style="width: 80%;" class="tinymce" aria-hidden="true"></textarea>
							<input type="hidden" name="MENSAGEM2" />
						</div>

						<br/>&nbsp;<br/>
											   	
					 	<button class="btn btn-primary" id="updateButton"><%= p.get("ENVIAR") %></button>
						<button type="reset"  name="reset"  class="btn"><%=p.get("LIMPAR") %></button>
						
						<p></p>
						
					</div>
						
				</div>

			</div><!--span8--><!--span4-->
		</div> <!-- row fluid -->		
		
	</div><!--maincontentinner-->
</div>

<div class="clear"></div>

<script>
	$("#formX").validate({
		rules: {
			ASSUNTO: "required"
		},
		messages: {
			ASSUNTO: "<%=p.get("ERRO_PADRAO")%>"
		},
		highlight : function(label) {
			$(label).closest('p').removeClass('success');
			$(label).closest('p').addClass('error');
		},
		success : function(label) {
			$(label).addClass('valid').closest('p').addClass('success');
		}
	});
	
	$("#updateButton").click(function(e){
		e.preventDefault();
		formX.MENSAGEM2.value = tinyMCE.activeEditor.getContent({format : 'raw'}).replace(/#/g, "").replace(/%/g, "").replace(/&amp;/g, "").replace(/&nbsp;/g, "+");
		saveOS();
	});
</script>

<%
	} catch( Exception e ) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();
	 	} catch( Exception ee ) {
		}
	}
%>	
