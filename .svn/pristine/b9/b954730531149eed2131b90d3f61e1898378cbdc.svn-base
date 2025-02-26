<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.cliente.MensagemEntity"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>

<%@ include file="logado.jsp" %>
<%@ include file="idioma.jsp" %>

<%	
String idMensagem = request.getParameter("idMensagem"); 

%>

<ul class="breadcrumbs">
	<li><a href="painel.jsp"><i class="iconfa-home"></i></a> <span class="separator"></span></li>
	<li><%= p.get("HOME") %><span class="separator"></span></li>
	<li><b><%= p.get("MENSAGEM") %></b></li>
</ul>

<div class="pageheader" onclick="javascript:abre('miolo', 'mensagemLista.jsp?caixa=inbox')" style="cursor: pointer;">
	<!--  
	<span class="searchbar">
		<input type="text" name="lojaBusca" placeholder="Busque e pressione ENTER..." onkeypress="return buscaLoja(this.value, false, event, 0)" onblur="javascript:buscaLoja(this.value, true, event, 0);" />
	</span>
	-->

	<div class="pageicon"><span class="iconfa-envelope"></span></div>
	<div class="pagetitle">
		<h5><%=p.get("DETALHES_MENSAGEM") %></h5>
		<h1><%= p.get("MENSAGEM") %></h1>
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
		
		MensagemEntity msg = new MensagemEntity();
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), msg.getTableName(), msg.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), new Long(idMensagem), "Usuario acessou mensagens (ler)", conn );
		
		msg.setIdMensagem( new Long(idMensagem) );
		msg.getThis(conn);
		
		if( msg.getIdDestinatario() != 0l && !msg.getIdDestinatario().toString().equals( usuarioLogado.getIdUsuario().toString() ) && !msg.getIdRemetente().toString().equals( usuarioLogado.getIdUsuario().toString() ) ) {
			out.print("<script type=\"text/javascript\">location.href = \"index.jsp\";</script>");
			return;
		} 
		
		usuario.setIdUsuario( msg.getIdRemetente() );
		if( !usuario.getThis(conn) ) {
			usuario.setNome("Mensagem do Sistema");		
		}
		
		if( usuarioLogado.getIdUsuario().toString().equals( msg.getIdDestinatario().toString() ) ) {
			msg.setStatus(1);
			msg.update(conn);
		}
		
		if( request.getParameter("status") != null ) {
			if( request.getParameter("status").equals("0") ) {
				msg.setStatus(0);
				msg.update(conn);
			}
		}
%>

<div class="centro" style="text-align: left">
	<div class="maincontentinner">
		<table class="table table-bordered table-invoice">
			<tbody><tr>
				<td class="width30"><%=p.get("REMETENTE") %></td>
				<td class="width70">
					<%
						String link = "#";
					
						UsuarioEntity testUsr = new UsuarioEntity();
						
						testUsr.setIdUsuario( msg.getIdRemetente() );
						if( testUsr.getThis(conn) ) {
							link = "adminUsuarioDadosPessoais.jsp?idUsuario=" + msg.getIdRemetente(); 
						}
						
					%>
					<a class='grouped_elements' href='<%= link %>' style='color: #333333'><strong><%= usuario.getNome() %></strong></a>
				</td>
		  	</tr>
		  	<tr>
				<td><%=p.get("ASSUNTO_MENSAGEM") %>:</td>
				<td><%= msg.getAssunto().replaceAll(": " + usuario.getNome(), "") %></td>
		  	</tr>
		  	<tr>
				<td><%=p.get("DATA") %>: </td>
				<td><%= msg.DATE_FORMAT1_BR2.format( msg.getDataHora() ) %></td>
		  	</tr></tbody>
		</table>
			
		<div class="row-fluid">
			<div id="dashboard-left" class="span12">
				<div class="widgetbox box-inverse">
						  
					<div class="headtitle">
						<div class="btn-group">
							<button data-toggle="dropdown" class="btn dropdown-toggle"><%=p.get("OQUE_DESEJA_FAZER")%>? <span class="caret"></span></button>
							<ul class="dropdown-menu">
								<li><a href="javascript:abre('miolo', 'mensagemLista.jsp?caixa=inbox')"><%=p.get("VOLTAR_PARA_CAIXA_ENTRADA") %></a></li>
								<li class="divider"></li>
								<li><a href="javascript:abre('miolo', 'mensagemLer.jsp?idMensagem=<%= idMensagem %>&status=0')"><%=p.get("MARCAR_COMO_NAO_LIDA") %></a></li>
<!-- 								<li class="divider"></li> -->
<%-- 								<li><a href="javascript:abre('miolo', 'mensagemEnviar.jsp?idDestinatario=<%= msg.getIdRemetente() %>')"><%=p.get("RESPONDER") %></a></li> --%>
							</ul>
						</div>
						<h4 class="widgettitle"><%=p.get("CONTEUDO") %></h4>
					</div>
					
					<div class="widgetcontent">
						<%= msg.getMensagem() %>
					</div><!--widgetcontent-->			   
				</div>
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
