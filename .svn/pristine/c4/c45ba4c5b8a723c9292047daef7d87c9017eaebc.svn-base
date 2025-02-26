<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.cliente.SuporteEntity"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>

<%@ include file="logado.jsp" %>
<%@ include file="idioma.jsp" %>

<%	if (usuarioLogado == null ) { %>
		<script type="text/javascript">location.href = "painel.jsp";</script>
<%	
		return;
	}
	String tipo = request.getParameter("status"); 
%>

<ul class="breadcrumbs">
	<li><a href="painel.jsp"><i class="iconfa-home"></i></a> <span class="separator"></span></li>
	<li><%= p.get("HOME") %><span class="separator"></span></li>
	<li><b><%= p.get("SUPORTE") %></b></li>
</ul>

<div class="pageheader" onclick="javascript:abre('miolo', 'suporteLista.jsp?status=<%= tipo %>')" style="cursor: pointer;">
	<!--  
	<span class="searchbar">
		<input type="text" name="lojaBusca" placeholder="Busque e pressione ENTER..." onkeypress="return buscaLoja(this.value, false, event, 0)" onblur="javascript:buscaLoja(this.value, true, event, 0);" />
	</span>
	-->

	<div class="pageicon"><span class="iconfa-folder-<%= tipo.equals("1") ? "open" : "close" %>"></span></div>
	<div class="pagetitle">
		<h5><%= p.get("LISTA_TODOS_CHAMADOS_TECNICOS") %></h5>
		<h1><%= p.get("SUPORTE") %> - <%= tipo.equals("1") ? p.get("CHAMADOS_EM_ABERTO") : p.get("CHAMADOS_FINALIZADOS") %></h1>
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
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), os.getTableName(), "idSuporte", request.getRemoteAddr(), request.getLocalName(), 0l, "Usuario acessou Suporte: " + tipo, conn );
		
		String query = "status = " + tipo;
		if( !usuarioLogado.isMaster() ) {
			query += " AND idUsuario = " + usuarioLogado.getIdUsuario(); 
		}
		ArrayList<SuporteEntity> mensagens = os.getArray( query + " ORDER BY dataMensagem DESC", conn);
%>

<div class="centro" style="text-align: left">
	<div class="row-fluid">
		<div id="dashboard-left" class="span12">
			<h5 class="subtitle">&nbsp;</h5> 
			<!-- <h4 class="widgettitle"><%=p.get("TICKETS") %></h4> -->
			<div id="dyntable_wrapper" class="dataTables_wrapper" role="grid">
				<!--  
				<div id="dyntable_length" class="dataTables_length">
					<label>Mostrar <select size="1" name="dyntable_length" aria-controls="dyntable"><option value="10" selected="selected">10</option><option value="25">25</option><option value="50">50</option><option value="100">100</option></select> Resultados</label>
				</div>
				<div class="dataTables_filter" id="dyntable_filter"><label>Buscar: <input type="text" aria-controls="dyntable"></label></div>
				-->
				
				<table id="dyntable" class="table table-bordered responsive dataTable" aria-describedby="dyntable_info">
					<colgroup>
						<col class="con0" style="align: center; width: 4%">
						<col class="con1">
						<col class="con0">
						<col class="con1">
						<col class="con0">
						<col class="con1">
					</colgroup>
					<thead>
						<tr role="row">
							<th class="head0 sorting_asc" role="columnheader" tabindex="0" aria-controls="dyntable" rowspan="1" colspan="1" aria-sort="ascending" aria-label=": activate to sort column ascending" style="width: 46px;"></th>
							<th class="head0 sorting" role="columnheader" tabindex="0" aria-controls="dyntable" rowspan="1" colspan="1" aria-label="DE: activate to sort column ascending" style="width: 273px;"><%=p.get("TICKETS") %></th>
							<th class="head1 sorting" role="columnheader" tabindex="0" aria-controls="dyntable" rowspan="1" colspan="1" aria-label="ASSUNTO: activate to sort column ascending" style="width: 782px;"><%=p.get("ASSUNTO") %></th>
							<th class="head0 sorting" role="columnheader" tabindex="0" aria-controls="dyntable" rowspan="1" colspan="1" aria-label="data: activate to sort column ascending" style="width: 265px;"><%=p.get("DATA") %></th>
							<th class="head1 sorting" role="columnheader" tabindex="0" aria-controls="dyntable" rowspan="1" colspan="1" aria-label="&amp;nbsp;: activate to sort column ascending" style="width: 148px;">&nbsp;</th>
						</tr>
					</thead>
					
					<tbody role="alert" aria-live="polite" aria-relevant="all">
						<%
							for( int i = 0; i < mensagens.size(); i++ ) {
								os = (SuporteEntity)mensagens.get(i);
						%>
								<tr class="<%= i%2 == 0 ? "odd" : "even" %>" onclick="javascript:abre('miolo', 'suporteLer.jsp?idSuporte=<%= os.getIdSuporte() %>')" style="cursor: pointer;">
									<td class=" sorting_2">
										<%	if( os.getStatus() == 1 ) { // não  lida %>
											<img src="images/icons/icone_mensagemaberta.png" alt="">
										<%	} else { // lida %>
											<i class=" iconsweets-mail"></i> 
										<%	} %>
									</td>
									<td class=" "><h5><strong><%= os.getIdSuporte() %></strong></h5></td>
									<td style="align: center; width: 50%" class=" "><%= p.get(SuporteEntity.assuntos[ new Integer(os.getAssunto()) ]) %></td>
									<td class=" "><%= SuporteEntity.DATE_FORMAT1_BR2.format( os.getDataMensagem() ) %></td>
									<td class="center "><a href="javascript:abre('miolo', 'suporteLer.jsp?idSuporte=<%= os.getIdSuporte() %>')" class="btn btn-info btn-info"> <i class="iconfa-inbox"></i>&nbsp;&nbsp;<%=p.get("LER") %></a></td>
								</tr>
						<%	} %>
						
					</tbody>
				</table>
				
				<div class="dataTables_info" id="dyntable_info"><%=p.get("TOTAL") %>: <%= mensagens.size() %> <%=p.get("RESULTADOS") %></div>
				
				<!--  
					<div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate"><a tabindex="0" class="first paginate_button paginate_button_disabled" id="dyntable_first">Primeiro</a><a tabindex="0" class="previous paginate_button paginate_button_disabled" id="dyntable_previous">Anterior</a><span><a tabindex="0" class="paginate_active">1</a></span><a tabindex="0" class="next paginate_button paginate_button_disabled" id="dyntable_next">Proximo</a><a tabindex="0" class="last paginate_button paginate_button_disabled" id="dyntable_last">Ultimo</a></div>
				-->
			</div>

			<br/>
					  
		</div><!--span8--><!--span4-->
	</div>	
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
