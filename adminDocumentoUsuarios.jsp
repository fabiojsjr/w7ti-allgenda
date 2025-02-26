<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.text.SimpleDateFormat"%>

<link rel="stylesheet" href="css/responsive-tables.css">
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/responsive-tables.js"></script>

<%@ include file="logadoForce.jsp" %>
<%@ include file="idioma.jsp" %>
<%@ include file="methods.jsp" %>

<ul class="breadcrumbs">
	<li><a href="painel.jsp"><i class="iconfa-user"></i></a> <span class="separator"></span></li>
	<li><%= p.get("ADMINISTRACAO") %><span class="separator"></span></li>
	<li><%= p.get("DOCUMENTO") %><span class="separator"></span></li>
	<li><b><%=p.get("USUARIOS") %></b></li>
</ul>

<div class="pageheader" onclick="javascript:abre('miolo', 'adminProjeto.jsp')" style="cursor: pointer;">
	<div class="pageicon"><span class="iconfa-user"></span></div>
	<div class="pagetitle">
		<h5> &nbsp;</h5>
		<h1><%= p.get("ADMINISTRACAO_USUARIO_DOCUMENTO") %></h1>
		<h5><%=p.get("ADMINISTRACAO_USUARIO_DESC") %></h5>
	</div>
</div>


<%	if (usuarioLogado == null || usuarioLogado.semPermissaoAcesso(DocumentoUsuariosEntity.ID_FUNCIONALIDADE) ) { %> 
		<script type="text/javascript">location.href = "painel.jsp";</script>
<%	
		return;
	}
	String msg = request.getParameter("msg");
	
	try {
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), 0l, "Usu�rio documento");
		
		String filtroNome 	= returnNotNull(request.getParameter("filtroNome")).toString();			
		String filtroUsuario = returnNotNull(request.getParameter("filtroUsuario")).toString();
		String aux			= returnNotNull(request.getParameter("pagina")).toString();
		int pagina = 		(aux == null || aux.equals("") ? 1 : Integer.valueOf(aux));
		
%>
		
<div class="maincontent">
	<div class="maincontentinner">
		<div class="widget">
		<h4 class="widgettitle"><%=p.get("ADMINISTRACAO_USUARIO_DOCUMENTO") %></h4>
			<div class="widgetcontent">
				<div>
					<div class="inline">
						<div><%= p.get("NOME_DOCUMENTO") %></div>
						<input type="text" id="filtroNome" class="filterAdminDocumentoText" name="filtroNome" placeHolder="<%= p.get("NOME_DOCUMENTO") %>" value="<%= filtroNome %>"/>
					</div>
					<div class="inline">
						<div><%= p.get("USUARIO") %></div>
						<input type="text" id="filtroUsuario" class="filterAdminDocumentoText" name="filtroUsuario" placeHolder="<%= p.get("USUARIO") %>" value="<%= filtroUsuario %>"/>
					</div>
					
					<%	if( !usuarioLogado.semPermissaoAcesso(3, "incluir") ) { %>
								<a class="btn btn-primary btn-rounded grouped_elements" href="adminDocumentoUsuariosEdit.jsp?idUsuario=0">
									<i class="iconfa-plus-sign icon-white "></i>
									  <%=p.get("ADICIONAR_USUARIO") %>
								</a>
					<%	} %>
					
					
					<script> 
						$(".filterAdminDocumentoText").keyup(function(){
							table.fnDraw();
						});
					</script>	
				</div>
				
				<div>&nbsp;</div>
				
				<table class="table table-bordered responsive" id="adminDocumentoUsuarios">
			        <colgroup>
			            <col class="con0" style=" width: 40%" />
			            <col class="con1" style=" width: 50%" />
			            <col class="con0" style=" width: 10%" />
			        </colgroup>
					<thead>
						<tr>
							<th class="head0 headerSortDown sortable"><%=p.get("NOME_DOCUMENTO") %></th>
							<th class="head1 headerSortDown sortable"><%=p.get("USUARIO") %></th>
							<th class="head0 nosortable"><%=p.get("ACOES") %></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>			
			</div>
		</div>
	</div>
</div>
<div class="clear20"></div>

<script>
var aoColumns = [];
$("#adminDocumentoUsuarios thead tr th").each(function(){
	if($(this).hasClass("nosortable")){
		aoColumns.push( { "bSortable": false } );
	}else{
		aoColumns.push( null );
	}
});

var table = $('#adminDocumentoUsuarios').dataTable({
				"sPaginationType": "full_numbers",
		    	"sSortAsc": "sorting_asc",
		    	"sSortDesc": "sorting_desc",
		    	"sSortable": "sortable",
		 		"bProcessing": true,
		    	"bServerSide": true,
		    	"sAjaxSource": "table/documentoUsuariosTable.jsp",
		    	"bFilter": false,
			    "aoColumns": aoColumns,
		    	"fnServerParams": function ( aoData ) {
		 	 		aoData.push( { "name": "filtroNome", "value": $("#filtroNome").val() } );
		 	 		aoData.push( { "name": "filtroUsuario", "value": $("#filtroUsuario").val() } );
		     	},
		     	"fnDrawCallback" : function() {
		     		$(".grouped_elements").fancybox({
		     			onClosed: function(){
		     				table.fnDraw();
		     			},
		     			titleShow: false 
		     		});
    	    	},
		     	"oLanguage": {
		    	 	"oPaginate": {
			           "sFirst": "Primeira",
			           "sLast": "Ultima",
			           "sNext": "Pr�ximo",
			           "sPrevious": "Anterior",
		    	 },
		    	 <%@ include file="languageTable.jsp" %>
		});
</script>

<%
	} catch( Exception e ) {
		e.printStackTrace();
	} finally {
	}
%>