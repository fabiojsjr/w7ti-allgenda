<%@page import="com.tetrasoft.data.cliente.SuporteEntity"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Connection"%>

<%@ include file="logado.jsp" %>
<%@ include file="idioma.jsp" %>

<%	String idSuporte = request.getParameter("idSuporte"); %>

<ul class="breadcrumbs">
	<li><a href="painel.jsp"><i class="iconfa-home"></i></a> <span class="separator"></span></li>
	<li><%= p.get("HOME") %><span class="separator"></span></li>
	<li><b><%= p.get("SUPORTE") %></b></li>
</ul>

<div class="pageheader" onclick="javascript:abre('miolo', 'suporteLista.jsp?status=1')" style="cursor: pointer;">
	<!--  
	<span class="searchbar">
		<input type="text" name="lojaBusca" placeholder="Busque e pressione ENTER..." onkeypress="return buscaLoja(this.value, false, event, 0)" onblur="javascript:buscaLoja(this.value, true, event, 0);" />
	</span>
	-->

	<div class="pageicon"><span class="iconfa-question"></span></div>
	<div class="pagetitle">
		<h5>Detalhes do chamado</h5>
		<h1><%= p.get("SUPORTE") %></h1>
	</div>
</div>

<div class="clear20"></div>

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
		
		SuporteEntity os = new SuporteEntity();
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), os.getTableName(), "idSuporte", request.getRemoteAddr(), request.getLocalName(), new Long(idSuporte), "Usuario acessou Suporte (ler)", conn );
		
		os.setIdSuporte( new Long(idSuporte) );
		os.getThis(conn);
		
		if( !os.getIdUsuario().toString().equals( usuarioLogado.getIdUsuario().toString() ) ) {
			out.print("<script type=\"text/javascript\">location.href = \"index.jsp\";</script>");
			return;
		} 
		
		if( request.getParameter("novoStatus") != null ) {
			os.setStatus( new Long(request.getParameter("novoStatus")) );
			os.update(conn);

			new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), os.getTableName(), "idSuporte", request.getRemoteAddr(), request.getLocalName(), os.getIdSuporte(), "Usuario mudou status de suporte: " + os.getStatus(), conn );
		}
%>

<div class="centro" style="text-align: left">
	<div class="maincontentinner">
		<table class="table table-bordered table-invoice">
			<tbody><tr>
				<td class="width30">Ticket</td>
				<td class="width70"><strong><%= os.getIdSuporte() %></strong></td>
		  	</tr>
		  	<tr>
				<td>Status:</td>
				<td><%= os.getStatus() == 1 ? "Em aberto" : "Finalizado" %></td>
		  	</tr>
		  	<tr>
				<td>Assunto:</td>
				<td><%= SuporteEntity.assuntos[ new Integer( os.getAssunto() ) ] %></td>
		  	</tr>
		  	<tr>
				<td>Data de Abertura: </td>
				<td><%= os.DATE_FORMAT1_BR2.format( os.getDataMensagem() ) %></td>
		  	</tr>
		  	<tr>
				<td>Data de Resposta: </td>
				<td><%= os.getDataResposta() == null ? "-" : os.DATE_FORMAT1_BR2.format( os.getDataResposta() ) %></td>
		  	</tr>
		  	</tbody>
		</table>
			
		<div class="row-fluid">
			<div id="dashboard-left" class="span12">
				<div class="widgetbox box-inverse">
					<div class="headtitle">
						<div class="btn-group">
							<button data-toggle="dropdown" class="btn dropdown-toggle">O que deseja fazer? <span class="caret"></span></button>
							<ul class="dropdown-menu">
								<li><a href="javascript:abre('miolo', 'suporteLista.jsp?status=1')">Voltar para Tickets Abertos</a></li>
								<li class="divider"></li>
								<li><a href="javascript:abre('miolo', 'suporteLista.jsp?status=0')">Voltar para Tickets Fechados</a></li>
								<li class="divider"></li>
								<%	if( os.getStatus() == 0 ) { %>
									<li><a href="javascript:abre('miolo', 'suporteLer.jsp?idSuporte=<%= idSuporte %>&novoStatus=1')">Marcar como não  resolvida</a></li>
								<%	} else if( os.getStatus() == 1 ) { %>
									<li><a href="javascript:abre('miolo', 'suporteLer.jsp?idSuporte=<%= idSuporte %>&novoStatus=0')">Marcar como resolvida</a></li>
								<%	} %>
							</ul>
						</div>
						<h4 class="widgettitle">Mensagem</h4>
					</div>
					
					<div class="widgetcontent">
						<%= os.getMensagem() %>
					</div><!--widgetcontent-->			   
				</div>
		
				<%	if( os.getResposta() != null ) { %>
							<div class="widgetbox box-inverse">
								<div class="headtitle">
									<h4 class="widgettitle">Resposta</h4>
								</div>
								
								<div class="widgetcontent">
									<%= os.getResposta() %>
								</div><!--widgetcontent-->			   
							</div>
				<%	} %>
			</div>			   
		
		</div><!--row-fluid-->
	</div><!--maincontentinner-->
</div>

<div class="clear"></div>

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
