<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.util.SendMailSparkpost"%>
<%@page import="com.tetrasoft.data.cliente.MensagemTemplateEntity"%>
<%@page import="com.tetrasoft.data.cliente.MensagemEntity"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>

<%@ include file="logado.jsp" %>
<%@ include file="idioma.jsp" %>

<script>$('#formX').removeData('validator');</script>

<%	
	String template = request.getParameter("template");
	if( template == null ) template = System.currentTimeMillis() + "";
	
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
	<li><b>Template</b></li>
</ul>

<div class="pageheader" onclick="javascript:abre('miolo', 'mensagemTemplate.jsp')" style="cursor: pointer;">
	<div class="pageicon"><span class="iconfa-list"></span></div>
	<div class="pagetitle">
		<h5>&nbsp;</h5>
		<h1>Template</h1>
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
		
		MensagemEntity msg = new MensagemEntity();
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), msg.getTableName(), msg.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), new Long(0l), "Usuario acessou mensagens (template)", conn );
		
		String assunto  = "";
		String mensagem = "";
		
		MensagemTemplateEntity tmp = new MensagemTemplateEntity();
		tmp.setIdTemplate( new Long(template) );
		
		if( tmp.getThis(conn) ) {
			assunto  = tmp.getNome();
			mensagem = tmp.getTemplate();
		} else {
			
//			mensagem += SendMailSparkpost.getEmailTopo("PT");
			mensagem += 
				"<center>" +
				"	<div align=\"center\">" +
				"		<table style=\"width: 740px;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" +
				"			<tbody>" +
				"				<tr>" +
				"					<td align='center'><br/><br/>DIGITE SUA MENSAGEM AQUI<br/><br/><br/></td>" +
				"				</tr>" +
				"			</tbody>" +
				"		</table>" +
				"	</div>" +
				"</center>"
			;		
					
//			mensagem += SendMailSparkpost.getEmailRodape("PT");
		}
%>

<div class="centro" style="text-align: left">
	<div class="maincontentinner">
		
		<div class="row-fluid">
			<div id="dashboard-left" class="span12">
				<h5 class="subtitle">&nbsp;</h5>
				<div class="widgetbox login-information">
					<h4 class="widgettitle">Template</h4>
					
					<div class="widgetcontent">
						<p class="control-group">
							<label class="control-label"><%=p.get("ASSUNTO") %>:</label>
							<input type="text" name="ASSUNTO" class="input-xxlarge" value="<%= assunto %>" /> 
						</p>

						<div class="maincontent">
							<textarea id="elm1" name="elm1" rows="15" cols="80" style="width: 80%; height: 500px" class="tinymce" aria-hidden="true"><%= mensagem %></textarea>
							<input type="hidden" name="MENSAGEM2" />
							<input type="hidden" name="TEMPLATE" value="<%= template %>" />
						</div>

						<br/>&nbsp;<br/>

					 	<button class="btn btn-primary" id="updateButton"><%= p.get("SALVAR") %></button>
						
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
		
		var ok = false;
		if( formX.ASSUNTO.value == "" ) {
			warningAlert('<%= p.get("ATENCAO") %>', '<%= p.get("ERRO_PADRAO2") %>!', function(response) {});
			return;
		}

		formX.MENSAGEM2.value = tinyMCE.activeEditor.getContent({format : 'raw'}).replace(/#/g, "").replace(/%/g, "").replace(/&amp;/g, "").replace(/&nbsp;/g, "+");
		
		jConfirm('<%= p.get("CERTEZA2") %>', '<%= p.get("ATENCAO") %>!', function(response) {
			if(response){
				saveTemplate();
			}
		});
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
