<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.cliente.MensagemTemplateEntity"%>
<%@page import="com.tetrasoft.data.cliente.EmailEntity"%>
<%@page import="com.tetrasoft.data.cliente.MensagemEntity"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>

<%@ include file="logado.jsp" %>
<%@ include file="idioma.jsp" %>

<script>$('#formX').removeData('validator');</script>

<%	
	boolean auto     = false;
	boolean schedule = false;

	String idDestinatario = request.getParameter("idDestinatario"); 
	if( idDestinatario == null ) {
		idDestinatario = usuarioLogado.getIdUsuario().toString();
	} else { 
		if( idDestinatario.contains("schedule") ) {
			schedule = true;
			idDestinatario = idDestinatario.substring( 0, idDestinatario.indexOf("-") );
		}
		
		auto = true;
	}
	
	String filter   = request.getParameter("filter");
	String template = request.getParameter("template");
	if( filter   == null ) filter   = "1";
	if( template == null ) template = "0";
	
//	if( filter.equals("1") || filter.equals("2") ) template = "1";
	
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
	<li><b><%= p.get("MENSAGEM") %></b></li>
</ul>

<div class="pageheader" onclick="javascript:abre('miolo', 'mensagemEnviar.jsp')" style="cursor: pointer;">
	<!--  
	<span class="searchbar">
		<input type="text" name="lojaBusca" placeholder="Busque e pressione ENTER..." onkeypress="return buscaLoja(this.value, false, event, 0)" onblur="javascript:buscaLoja(this.value, true, event, 0);" />
	</span>
	-->

	<div class="pageicon"><span class="iconfa-envelope"></span></div>
	<div class="pagetitle">
		<h5><%=p.get("ESCREVA_ABAIXO_MENSAGEM_DESEJADA") %></h5>
		<h1><%=p.get("ENVIAR_MENSAGEM") %></h1>
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
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), msg.getTableName(), msg.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), new Long(idDestinatario), "Usuario acessou mensagens (enviar)", conn );
		
		boolean responder = false;
		try {
			usuario.setIdUsuario( new Long(idDestinatario) );
			if( usuario.getThis(conn) ) {
				responder = true;
			}
		} catch(Exception e ) {
			e.printStackTrace();
		}
		
		String assunto  = "";
		String mensagem = "";

		if( template.equals("2") ) {
			assunto  = "Welcome to Allgenda!";
			mensagem = new EmailEntity().getBoasVindasContentEN(null, conn);
			
		} else if( template.equals("3") ) {
			assunto = "Pre Inventory Survey";
			mensagem = new EmailEntity().getInventarioContentEN(null, conn);
			
		} else if( template.equals("4") ) {
			assunto = "Would you leave us your feedback?";
			mensagem = new EmailEntity().getFeedbackContentEN(null, conn);
		}
		
		if( !template.equals("0") ) {
			MensagemTemplateEntity tmp = new MensagemTemplateEntity();
			tmp.setIdTemplate( new Long(template) );
			if( tmp.getThis(conn) ) {
				assunto  = tmp.getNome();
				mensagem = tmp.getTemplate();
			}
		}
		
		ArrayList<UsuarioEntity> usuarios = null;
		if( filter.equals("1") ) {
			usuarios = usuario.getArray("ativo = " + UsuarioEntity.STATUS_ATIVO + " ORDER BY nome", conn);			
			
		} else if( filter.equals("2") ) {
			usuarios = usuario.getArray("ativo = " + UsuarioEntity.STATUS_ATIVO + " ORDER BY nome", conn);
		}
		
		if( auto ) {
			usuarios = usuario.getArray("idUsuario = " + idDestinatario, conn);
		}
%>

<div class="centro" style="text-align: left">
	<div class="maincontentinner">
		
		<div class="row-fluid">
			<div id="dashboard-left" class="span12">
				<h5 class="subtitle">&nbsp;</h5>
				<div class="widgetbox login-information">
					<h4 class="widgettitle"><%=p.get("NOVA_MENSAGEM") %></h4>
					
					<div class="widgetcontent">
						<p style="display: <%= schedule ? "none" : "block" %>">
							<label><%=p.get("DE") %>:</label>
							<input type="text" name="DENOME" class="input-xlarge" value="<%= usuarioLogado.getNome() %>" readonly="" /> 
						</p>

						<p style="display: <%= clienteLogado ? "none" : "block" %>">
							<label><%=p.get("TEMPLATE") %>:</label>
							
							<select style="width: 350px;" class="chzn-select chzn-done" tabindex="-1" name="TEMPLATE" id="TEMPLATE" onchange="javascript:mensagemFilter(formX.TIPO.value, this.value )">
								<option value="1" <%= template.equals("1") ? "selected" : "" %>><%= ((String)p.get("BLANK")).toUpperCase() %></option>
								<option value="2" <%= template.equals("2") ? "selected" : "" %>><%= ((String)p.get("TEMPLATE1")).toUpperCase() %></option>
								<option value="3" <%= template.equals("3") ? "selected" : "" %>><%= ((String)p.get("TEMPLATE2")).toUpperCase() %></option>
								<option value="4" <%= template.equals("4") ? "selected" : "" %>><%= ((String)p.get("TEMPLATE3")).toUpperCase() %></option>

								<%
									MensagemTemplateEntity temp2 = new MensagemTemplateEntity();
									ArrayList t = temp2.getArray("1=1 ORDER BY nome", conn);
									for( int i = 0; i < t.size(); i++ ) {
										temp2 = (MensagemTemplateEntity)t.get(i);
								%>
										<option value="<%= temp2.getIdTemplate() %>" <%= template.equals( temp2.getIdTemplate().toString() ) ? "selected" : "" %>><%= temp2.getNome().toUpperCase() %></option>
								<%	} %>

								<!--  
								<option value="5" <%= template.equals("5") ? "selected" : "" %>>Estimate - Local - Open Hour</option>
								-->
							</select>

							<%	if( !schedule ) { %>
								<%	if( template.equals("0") ) { %>
										<button class="btn btn-primary" id="addTemplate" onclick="javascript:abre('miolo', 'mensagemTemplate.jsp')"><%= p.get("ADICIONAR") %> <%= p.get("NOVO") %> Template</button>
								<%	} else { %>
										<button class="btn btn-primary" id="addTemplate" onclick="javascript:abre('miolo', 'mensagemTemplate.jsp?template=<%= template %>')"><%= p.get("EDITAR") %> Template</button>
								<%	}  %>
							<%	} %>
						</p>						
						
						<p style="display: <%= clienteLogado || auto ? "none" : "block" %>">
							<label><%=p.get("FILTRO") %>:</label> 
							<select style="width: 350px;" class="chzn-select chzn-done" tabindex="-1" name="TIPO" id="TIPO" onchange="javascript:mensagemFilter(this.value, formX.TEMPLATE.value )">
								<option value="1"  <%= filter.equals("1")  ? "selected" : "" %>><%= ((String)p.get("PROSPECT")).toUpperCase() %>S + <%= ((String)p.get("FUNCIONARIO")).toUpperCase() %>S</option>
								<option value="2"  <%= filter.equals("2")  ? "selected" : "" %>><%= ((String)p.get("TODOS")).toUpperCase() %> <%= ((String)p.get("FUNCIONARIO")).toUpperCase() %>S</option>
								<option value="3"  <%= filter.equals("3")  ? "selected" : "" %>><%= ((String)p.get("TODOS")).toUpperCase() %> <%= ((String)p.get("PROSPECT")).toUpperCase() %>S</option>
								<option value="6"  <%= filter.equals("6")  ? "selected" : "" %>><%= ((String)p.get("TODOS")).toUpperCase() %> <%= ((String)p.get("AGENTES")).toUpperCase() %></option>
								<option value="7"  <%= filter.equals("7")  ? "selected" : "" %>><%= ((String)p.get("TODOS")).toUpperCase() %> <%= ((String)p.get("CORRETORS")).toUpperCase() %></option>
								<option value="8"  <%= filter.equals("8")  ? "selected" : "" %>><%= ((String)p.get("TODOS")).toUpperCase() %> MAILING</option>
								<option value="4"  <%= filter.equals("4")  ? "selected" : "" %>><%= ((String)p.get("SOMENTE")).toUpperCase() %> <%= ((String)p.get("PROSPECT")).toUpperCase() %>S <%= ((String)p.get("SEM_INVENTARIO")).toUpperCase() %></option>
								<option value="5"  <%= filter.equals("5")  ? "selected" : "" %>><%= ((String)p.get("SOMENTE")).toUpperCase() %> <%= ((String)p.get("MEUS_PROSPECTS")).toUpperCase() %></option>
								<option value="-1" <%= filter.equals("-1") ? "selected" : "" %>><%= ((String)p.get("MAILING_EXTERNO")).toUpperCase() %></option>
							</select>
						</p>						
						
						<div style="display: <%= filter.equals("-1") ? "block" : "none" %>" id="mailing1">
							<p>
								<label><%=p.get("EMAILS_PONTO_VIRGULA") %></label>
								<textarea rows="5" cols="80" style="width: 500px; height: 100px" name="emails"></textarea>
							</p>
							<p>
								<label><%=((String)(p.get("EMAILS_PONTO_VIRGULA"))).replace("E-mail", (String)p.get("NOME") ) %></label>
								<textarea rows="5" cols="80" style="width: 500px; height: 100px" name="names"></textarea>
							</p> 
						</div>						

						<p style="display: <%= filter.equals("-1") || schedule ? "none" : "block" %>" id="mailing2">
							<label><%=p.get("PARA") %>:</label> 
							<select style="width: 350px;" class="chzn-select chzn-done" tabindex="-1" name="PARA" id="PARA">
								<%	if( !auto ) { %>
										<option value="-1">-- <%= ((String)p.get("TODOS")).toUpperCase() %> --</option>
								<% } %>
								
								<%	if( usuarios != null ) {
										for( int i = 0; i < usuarios.size(); i++ ){	%> 
											<option value="<%= usuarios.get(i).getIdUsuario() %>" <%= usuarios.get(i).getIdUsuario().toString().equals( idDestinatario ) ? "selected" : "" %>><%= usuarios.get(i).getNome() %></option>
								<%		}
									}
								%> 	
							</select>
						</p>
						
						<p class="control-group">
							<label class="control-label"><%=p.get("ASSUNTO") %>:</label>
							<input type="text" name="ASSUNTO" class="input-xxlarge" id="ASSUNTO" value="<%= assunto %>" /> 
						</p>

						<!--  
						<p class="control-group">
							<label class="control-label">TAGS:</label>
							TAG_NAME &nbsp;&nbsp;&nbsp; TAG_LINK1
						</p>
						-->

						<div class="maincontent">
							<textarea id="elm1" name="elm1" rows="15" cols="80" style="width: 80%; height: 500px" class="tinymce" aria-hidden="true"><%= mensagem %></textarea>
							<input type="hidden" name="MENSAGEM2" />
						</div>

						<br/>&nbsp;<br/>
						
						<%	if( !schedule ) { %>
							 	<button class="btn btn-primary" id="updateButton"><%= p.get("ENVIAR") %></button>
								<button type="reset"  name="reset"  class="btn"><%=p.get("LIMPAR") %></button>
						<%	} else { %>
								<style>
									.maincontentinner {
										padding: 0px !important;
									} 
									h5 {
										display: none;
									}
								</style>
						
						<%	} %>
						
						<p></p>
						
					</div>
						
				</div>

			</div><!--span8--><!--span4-->
		</div> <!-- row fluid -->		
		
	</div><!--maincontentinner-->
</div>

<div class="clear"></div>

<script>
	function mensagemFilter(f, t) {
		<%	if( auto ) { 
				if( schedule ) idDestinatario += "-schedule";
		%>
				abre('miolo', 'mensagemEnviar.jsp?filter=' + f + '&template=' + t + '&idDestinatario=<%= idDestinatario %>');
		<%	} else { %>
				abre('miolo', 'mensagemEnviar.jsp?filter=' + f + '&template=' + t);
		<%	} %>
	}

//  document.getElementById("#elm1_code > span.mceIcon.mce_code").disabled = true;
	$("#formX").validate({
		rules: {
			ASSUNTO: "required",
			PARA: "required"
		},
		messages: {
			PARA: "<%=p.get("ERRO_PADRAO")%>",
			ASSUNTO: "<%=p.get("ERRO_PADRAO")%>",
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

		if( formX.TIPO.value == "-1" ) { // mailing externo
			if( formX.emails.value == "" ) {
				warningAlert('<%= p.get("ATENCAO") %>', '<%= p.get("ERRO_PADRAO2") %>!', function(response) {});
				return;
			} else {
				formX.emails.value = replaceAll( formX.emails.value, "\n", ";");
				formX.names.value  = replaceAll( formX.names.value, "\n", ";");
			}
		
			formX.PARA.value = "-1";
		}

		formX.MENSAGEM2.value = tinyMCE.activeEditor.getContent({format : 'raw'}).replace(/#/g, "").replace(/%/g, "").replace(/&amp;/g, "").replace(/&nbsp;/g, "+");
		
		if( formX.PARA.value == "-1" ) {
			jConfirm('<%= p.get("CERTEZA4") %>', '<%= p.get("ATENCAO") %>!', function(response) {
				if(response){
					saveMensagem();
				}
			});
		} else {
			saveMensagem();
		}
		
	});
	
	activateWYSIWYG();
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
