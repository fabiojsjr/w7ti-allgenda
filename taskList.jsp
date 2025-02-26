<!DOCTYPE html>
<%@page import="com.tetrasoft.data.cliente.TaskEntity"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.finance.SalaReuniaoEntity"%>
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
	int idFunc = TaskEntity.ID_FUNCAO;
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
		
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), 0l, "Reuni�o: lista");
		
					
		String filtroDescricao		= returnNotNull(request.getParameter("filtroDescricao")).toString();
		String filtroSala		= returnNotNull(request.getParameter("filtroSala")).toString();
		String filtroDataInicial		= returnNotNull(request.getParameter("filtroDataInicial")).toString();
		String filtroDataFinal		= returnNotNull(request.getParameter("filtroDataFinal")).toString();
		
		String aux					= returnNotNull(request.getParameter("pagina")).toString();
		int pagina = 				(aux == null || aux.equals("") ? 1 : Integer.valueOf(aux));
		
%>
		
<div class="contentTable">
<div class="clear20"></div>
	<div>	

		<div class="inline">			
			<input type="text" id="filtroDescricao" class="filteradminText input-medium" name="filtroDescricao" placeHolder="Descri��o" value="<%= filtroDescricao %>"/>
		</div>
		<div class="inline">			
			<input type="text" id="filtroSala" class="filteradminText input-medium" name="filtroSala" placeHolder="Sala" value="<%= filtroSala %>"/>
		</div>
		<%	if( !usuarioLogado.semPermissaoAcesso(idFunc, "incluir") ) { %>
			<div class="inline">
				<button class="btn btn-primary btn-circle grouped_elements" href="calendarioEdit.jsp" style="width: 200px"><span class="iconfa-plus-sign icon-white"></span> <%= p.get("ADICIONAR") %> Reuni�o</button>
			</div>
		<%	} %>
		<div>
			<div class="inline containFiltroData">							
				<input type="text" id="filtroDataInicial" class="filtroadminLogChange input-medium data" name="filtroDataInicial" placeHolder="Data" value="<%=filtroDataInicial.equals("") ? "" : usuarioLogado.DATE_FORMAT1_BR.format(new SimpleDateFormat("dd/MM/yyyy").parse(filtroDataInicial))%>"/>
			</div>			
		</div>	
	</div>
	
	<div>&nbsp;</div>
	
	<!-- <h4 class="widgettitle">&nbsp;</h4> -->
	<!--<table class="table table-bordered responsive" id="admin"> -->
	
		<table class="table table-bordered responsive" id="admin">
	        <colgroup>        	 
	            <col class="con1" style=" width: 30%!Important"/>
	            <col class="con2" style=" width: 15%!Important"/>
	            <col class="con3" style=" width: 15%!Important"/>
	            <col class="con4" style=" width: 15%!Important"/>
	            <col class="con5" style=" width: 15%!Important"/>                        
	        </colgroup>
			<thead>
				<tr>								
					<th class="head1 headerSortDown sortable">DESCRI��O</th>	
					<th class="head2 headerSortDown sortable">SALA</th>				
					<th class="head3 headerSortDown sortable">DATA IN�CIO</th>
					<th class="head4 headerSortDown sortable">DATA FIM</th>				
					<th class="head5 nosortable">A��ES</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>	
	</div>
	<div class="clear20"></div>
<script type="text/javascript" src="js/jquery.mask.min.js"></script>
<script>
var adminTable;

$(document).ready(function(){
	
	//mascara para datas
	$('.data').mask('00/00/0000');
	
	activateDatePickerID($(".data"), 'EN');
	
	$(".filteradminText").keyup(function(){
		adminTable.fnDraw();
	});
	$(".filteradminSelect").change(function(){
		adminTable.fnDraw();
	});
	
	$(".filtroadminLogChange").change(function(e){
		adminTable.fnDraw();
	});
	
	//excluirREuniao
	$(document).on("click",".btn-excluirReuniao",function(e){
		
		var link = this;
		
		e.preventDefault();
		
		confirmBox("Excluir", "Deseja excluir a reuni�o?", function(x){
			
    		if(x){
    			
    			var href = $(link).attr("href");
    			var valores = href.split("=");
    			var id = valores[1];
    			
    			$.get("calendarioEdit.jsp?ExcluirReuniao=true&id=" + id)
    			.done(function(response){    				
    				
    				infoAlert("Mensagem","Reuni�o exclu�da com sucesso",function(){});					
    				setTimeout(function(){
    						$("#popup_ok").trigger("click");
    						$("#fancybox-close").trigger("click");  
    						abre('miolo', 'taskList.jsp');
    				},1000);   				 				
    			})
    			.fail(function(error){
    				
    				warningAlert("Mensagem","Erro ao Excluir a reuni�o",function(){});
    				
    			});    			
    		}
    	});
		
	});		   
	
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
	    "sAjaxSource": "table/taskTable.jsp",
	    "bFilter": false,
	    "aoColumns": aoColumns,
	    "fnCreatedCell": function (nTd, sData, oData, i) {
			if(i == '2'){
				$(nTd).attr("id","1211");
			}
      	},	
	    "fnServerParams": function ( aoData ) {			
			aoData.push(
					
					{ "name": "filtroDescricao", "value": $("#filtroDescricao").val() },
					{ "name": "filtroSala", "value": $("#filtroSala").val() },
					{ "name": "filtroDataInicial", "value": $("#filtroDataInicial").val() }
					//{ "name": "filtroDataFinal", "value": $("#filtroDataFinal").val() }
			
			);
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
} 
%>