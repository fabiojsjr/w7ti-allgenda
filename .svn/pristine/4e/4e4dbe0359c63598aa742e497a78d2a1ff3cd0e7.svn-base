<%@page import="com.tetrasoft.data.cliente.MensagemEntity"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.text.SimpleDateFormat"%>

<link rel="stylesheet" href="css/responsive-tables.css">
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/responsive-tables.js"></script>

<style>
	body > div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-front.ui-dialog-buttons.ui-draggable > div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix{
		background-color: #71B8EE;
	}
	#adminMsg > tbody > tr:hover{
		background-color: #DDDDFF;
	}
</style>


<%@ include file="logado.jsp" %>
<%@ include file="idioma.jsp" %>
<%@ include file="methods.jsp" %>

<%
	String tipo = request.getParameter("caixa"); 
%>

<ul class="breadcrumbs">
	<li><a style="color : black;" href="painel.jsp"><i class="iconfa-user"></i></a> <span class="separator"></span></li>
	<li><%= p.get("ADMINISTRACAO") %><span class="separator"></span></li>
	<li><b><%=p.get("MENSAGEM") %></b></li>
</ul>

<div class="pageheader" onclick="javascript:abre('miolo', 'mensagemLista.jsp?caixa=<%= tipo %>')" style="cursor: pointer;">
	<div class="pageicon"><span class="iconfa-sign<%= tipo.equals("inbox") ? "in" : "out" %>"></span></div>
	<div class="pagetitle">
		<h5><%=p.get("LISTA_TODAS_MENSAGENS_RECEBIDAS_ENVIADAS") %></h5>
		<h1><%= tipo.equals("inbox") ? p.get("CAIXA_DE_ENTRADA") : p.get("ITENS_ENVIADOS") %></h1>
	</div>
</div>

<%
	if (usuarioLogado == null ) { %> 
		<script type="text/javascript">location.href = "painel.jsp";</script>
<%	
		return;
	}
	
	try {
		UsuarioEntity usuario = new UsuarioEntity();		
		MensagemEntity msg = new MensagemEntity();

		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), msg.getTableName(), msg.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), 0l, "Usuário acessou mensagens: " + tipo );

		String filtroNome			= returnNotNull(request.getParameter("filtroNome")).toString();		
		String filtroDataInicial 	= returnNotNull(request.getParameter("filtroDataInicio")).toString();
		String filtroDataFinal	 	= returnNotNull(request.getParameter("filtroDataFinal")).toString();
		String aux					= returnNotNull(request.getParameter("pagina")).toString();
		int pagina = 				(aux == null || aux.equals("") ? 1 : Integer.valueOf(aux));
%>
		
<div class="contentTable">
	<div class="clear20"></div>
	
	<input type="hidden" name="filtroTipoCaixa" id="filtroTipoCaixa" value="<%= tipo %>" /> 
	
	<div>
		<div class="inline">
			<div><%= p.get("FILTRO") %></div>
			<input type="text" class="filtroMensagemText" id="filtroNome" name="filtroNome" placeHolder="<%= p.get("FILTRO") %>" value="<%= filtroNome %>" />
		</div>

		<div class="inline">
			<div><%= p.get("DATA_INICIAL") %></div>	
			<input name="filtroDataInicio" id="from" type="text"  value="<%=filtroDataInicial.equals("") ? "" : usuarioLogado.DATE_FORMAT1_BR.format(new SimpleDateFormat("dd/MM/yyyy").parse(filtroDataInicial))%>" class="datePickerInput filtroMensagemChange" />
		</div>
		
		<div class="inline">
			<div><%= p.get("DATA_FINAL") %></div>	
			<input name="filtroDataFinal" id="to" type="text" value="<%= filtroDataFinal.equals("") ? "" : usuarioLogado.DATE_FORMAT1_BR.format(new SimpleDateFormat("dd/MM/yyyy").parse(filtroDataFinal)) %>" class="datePickerInput filtroMensagemChange" />
		</div>

		<div class="inline">
			<div><%= p.get("STATUS") %></div>
			<select class="filtroMensagemChange" id="filtroStatus" name="filtroStatus">
				<option value="<%= MensagemEntity.STATUS_NAO_LIDO %>"><%= p.get("NAO_LIDAS") %></option>
				<option value="<%= MensagemEntity.STATUS_LIDO%>"><%= p.get("LIDAS") %></option>
			</select>
		</div>
		
		<div class="inline">
			<div><%= p.get("TIPO") %></div>
			<select class="filtroMensagemChange" id="filtroTipo" name="filtroTipo">
				<option value="<%= MensagemEntity.TIPO_CORREIO %>"><%= p.get("CORREIO") %></option>
				<option value="<%= MensagemEntity.TIPO_LEAD%>"><%= p.get("LEAD") %></option>
			</select>
		</div>
		
		<script> 
			$(".filtroMensagemText").keyup(function(e){
 				adminMensagemTable.fnDraw();
			});
			
			$(".filtroMensagemChange").change(function(e){
				adminMensagemTable.fnDraw();
			});
		</script>
	</div>
		
	<div>&nbsp;</div>
	<!-- <h4 class="widgettitle">&nbsp;</h4> -->
	<table class="table table-bordered responsive" id="adminMsg">
        <colgroup>
            <col class="con0" style="width: 20%;" />
            <col class="con1" style="width: 20%;" />
            <col class="con0" style="width: 50%;" />
            <col class="con1" style="width: 10%;" />
        </colgroup>
		<thead>
			<tr>
				<th class="head0 headerSortDown sortable"><%=p.get("DATA") %></th>
				<th class="head1 headerSortDown nosortable"><%= tipo.equals("inbox") ? p.get("REMETENTE") : p.get("DESTINATARIO") %></th>
				<th class="head0 headerSortDown sortable"><%=p.get("ASSUNTO") %></th>
				<th class="head1 headerSortDown nosortable"><%=p.get("ACOES") %></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>			
</div>

<div class="clear20"></div>

<script>
var adminMensagemTable;
$(document).ready(function(){
	activateDatePickerID($("#to"), 'EN');
	activateDatePickerID($("#from"), 'EN');
	
	$('input[name=filtroDataInicio]').mask("99/99/9999");
	$('input[name=filtroDataFinal]').mask("99/99/9999");
	
	var aoColumns = [];
	$("#adminMsg thead tr th").each(function(){
		if($(this).hasClass("nosortable")){
			aoColumns.push( { "bSortable": false } );
		}else{
			aoColumns.push( null );
		}
	});
	
	adminMensagemTable = $('#adminMsg').dataTable({
	    "sPaginationType": "full_numbers",
	    "sSortAsc": "sorting_asc",
	    "sSortDesc": "sorting_desc",
	    "sSortable": "sortable",
	 	"bProcessing": true,
	    "bServerSide": true,
	    "sAjaxSource": "table/mensagemTable.jsp",
	    "bFilter": false,
	    "aoColumns": aoColumns,
	    "fnCreatedCell": function (nTd, sData, oData, i) {
			if(i == '2'){
				$(nTd).attr("id","1211");
			}
      	},	
	    "fnServerParams": function ( aoData ) {
	 	 		aoData.push( { "name": "filtroDataInicio", "value": $("input[name=filtroDataInicio]").val() } );
	 	 		aoData.push( { "name": "filtroDataFinal", "value": $("input[name=filtroDataFinal]").val() } );
	 	 		aoData.push( { "name": "filtroNome", "value": $("#filtroNome").val() } );
	 	 		aoData.push( { "name": "filtroStatus", "value": $("#filtroStatus").val() } );
	 	 		aoData.push( { "name": "filtroTipo", "value": $("#filtroTipo").val() } );
	 	 		aoData.push( { "name": "filtroTipoCaixa", "value": $("#filtroTipoCaixa").val() } );
	     },
	       "fnDrawCallback" : function() {
	     		$(".grouped_elements").fancybox({
	     			onClosed: function(){
	     				adminMensagemTable.fnDraw();
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
<div id="erro"></div>
<%
	} catch( Exception e ) {
		e.printStackTrace();
	} finally {
	}
%>