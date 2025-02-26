<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.cliente.MailingEntity"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>

<link rel="stylesheet" href="css/responsive-tables.css">
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/responsive-tables.js"></script>
<style>
	body > div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-front.ui-dialog-buttons.ui-draggable > div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix{
		background-color: #71B8EE;
	}
	#admin > tbody > tr:hover{
		background-color: #DDDDFF;
	}
</style>

<%@ include file="logadoForce.jsp" %>
<%@ include file="idioma.jsp" %>
<%@ include file="methods.jsp" %>

<%	
	int idFunc = MailingEntity.ID_FUNCAO;
	if (usuarioLogado == null || usuarioLogado.semPermissaoAcesso(idFunc) ) { %> 
		<script type="text/javascript">location.href = "painel.jsp";</script>
<%	
		return;
	}

	boolean master = false;
	if( !usuarioLogado.semPermissaoAcesso(idFunc, "excluir") ) {
		master = true;
	}

	try {
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), 0l, "Mailing: lista");
		
		String filtroNome 		= returnNotNull(request.getParameter("filtroNome")).toString();			
		String filtroEmail 		= returnNotNull(request.getParameter("filtroEmail")).toString();
		String filtroTelefone 	= returnNotNull(request.getParameter("filtroTelefone")).toString();
		String filtroContato 	= returnNotNull(request.getParameter("filtroContato")).toString();
		String filtroAtivo	 	= returnNotNull(request.getParameter("filtroAtivo")).toString();
		String aux				= returnNotNull(request.getParameter("pagina")).toString();
		int pagina = 			(aux == null || aux.equals("") ? 1 : Integer.valueOf(aux));
		
		
%>

<ul class="breadcrumbs">
	<li><a style="color : black;" href="painel.jsp"><i class="iconfa-user"></i></a> <span class="separator"></span></li>
	<li><%= p.get("ADMINISTRACAO") %><span class="separator"></span></li>
	<li><b>Mailing</b></li>
</ul>

<div class="pageheader" onclick="javascript:abre('miolo', 'mailingList.jsp')" style="cursor: pointer;">
	<div class="pageicon"><span class="iconfa-envelope"></span></div>
	<div class="pagetitle">
		<h5> &nbsp;</h5>
		<h1>Mailing</h1>
	</div>
</div>
		
<div class="contentTable">
	<div class="clear20"></div>

	<div>
		<div class="inline">
			<!-- <div><%= p.get("NOME") %></div> -->
			<input type="text" id="filtroNome" class="filteradminText input-medium" name="filtroNome" placeHolder="<%= p.get("NOME") %>" value="<%= filtroNome %>"/>
		</div>

		<div class="inline">
			<!-- <div><%= p.get("EMAIL") %> </div> -->
			<input type="text" id="filtroEmail" class="filteradminText input-medium" name="filtroEmail" placeHolder="<%= p.get("EMAIL") %>" value="<%= filtroEmail %>"/>
		</div>
	
		<div class="inline">
			<!-- <div><%= p.get("TELEFONE") %> </div> -->
			<input type="text" id="filtroTelefone" class="filteradminText input-medium" name="filtroTelefone" placeHolder="<%= p.get("TELEFONE") %>" value="<%= filtroTelefone %>"/>
		</div>
	
		<div class="inline">
			<!-- <div><%= p.get("ENDERECO") %> </div> -->
			<input type="text" id="filtroContato" class="filteradminText input-medium" name="filtroContato" placeHolder="Mailing" value="<%= filtroContato %>"/>
		</div>
	
		<div class="inline">
			<!-- <div><%= p.get("ATIVO") %> </div> -->
			<select name="filtroAtivo" id="filtroAtivo" style="width: 120px" class="filteradminSelect">
				<option value="">-- <%= p.get("STATUS") %> --</option>
				<option value="1" <%= filtroAtivo.equals("1") ? "selected" : "" %>>Ativo</option>
				<option value="0" <%= filtroAtivo.equals("0") ? "selected" : "" %>>Inativo</option>
			</select>
		</div>
	
		<%	if( !usuarioLogado.semPermissaoAcesso(idFunc, "incluir") ) { %>
			<div class="inline">
				<button class="btn btn-primary btn-circle grouped_elements" href="mailingEdit.jsp" style="width: 200px"><span class="iconfa-plus-sign icon-white"></span> <%= p.get("ADICIONAR") %> Mailing</button>
			</div>
		<%	} %>
	
		<script> 
			$(".filteradminText").keyup(function(){
				adminTable.fnDraw();
			});
			$(".filteradminSelect").change(function(){
				adminTable.fnDraw();
			});
		</script>	
	
	</div>
	
	<div>&nbsp;</div>
	
	<!-- <h4 class="widgettitle">&nbsp;</h4> -->
	<table class="table table-bordered responsive" id="admin">
        <colgroup>
            <col class="con0" style=" width: 25%" />
            <col class="con1" style=" width: 20%" />
            <col class="con0" style=" width: 10%" />
            <col class="con1" style=" width: 25%" />
            <col class="con1" style=" width: 10%" />
            <col class="con0" style=" width: 10%" />
        </colgroup>
		<thead>
			<tr>
				<th class="head0 headerSortDown sortable">NOME</th>
				<th class="head1 headerSortDown sortable">EMAIL</th>
				<th class="head0 headerSortDown sortable">TELEFONE</th>
				<th class="head0 headerSortDown sortable">MAILING</th>
				<th class="head0 headerSortDown sortable">STATUS</th>
				<th class="head1 nosortable">AÇÕES</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>			
</div>

<div class="clear20"></div>

<script>
var adminTable;
$(document).ready(function(){
	var aoColumns = [];
	$("#admin thead tr th").each(function(){
		if($(this).hasClass("nosortable")){
			aoColumns.push( { "bSortable": false } );
		}else{
			aoColumns.push( null );
		}
	});
	
	adminTable = $('#admin').dataTable({
	    "sPaginationType": "full_numbers",
	    "sSortAsc": "sorting_asc",
	    "sSortDesc": "sorting_desc",
	    "sSortable": "sortable",
	 	"bProcessing": true,
	    "bServerSide": true,
	    "sAjaxSource": "table/mailingTable.jsp",
	    "bFilter": false,
	    "aoColumns": aoColumns,
	    "fnCreatedCell": function (nTd, sData, oData, i) {
			if(i == '2'){
				$(nTd).attr("id","1211");
			}
      	},	
	    "fnServerParams": function ( aoData ) {
			aoData.push( { "name": "filtroNome", "value": $("#filtroNome").val() } );
			aoData.push( { "name": "filtroEmail", "value": $("#filtroEmail").val() } );
			aoData.push( { "name": "filtroTelefone", "value": $("#filtroTelefone").val() } );
			aoData.push( { "name": "filtroContato", "value": $("#filtroContato").val() } );
			aoData.push( { "name": "filtroAtivo", "value": $("#filtroAtivo").val() } );
	     },
	       "fnDrawCallback" : function() {
	     		$(".grouped_elements").fancybox({
	     			onClosed: function(){
	     				adminTable.fnDraw();
	     			},
					titleShow: false,
					transitionIn : 'elastic',
					transitionOut : 'elastic',
					autoDimensions: true,
					overlayShow : true,
					autoScale: true,
					easingIn : 'easeOutBack',
					easingOut : 'easeInBack',
					scrolling: 'no'
	     		});
	    	},
	    	<%@ include file="languageTable.jsp" %>
	});
});

</script>

<%
} catch( Exception e ) {
	e.printStackTrace();
} finally {
}
%>