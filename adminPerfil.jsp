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

<style>
	body > div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-front.ui-dialog-buttons.ui-draggable > div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix{
		background-color: #71B8EE;
	}
	#adminUsuario > tbody > tr:hover{
		background-color: #DDDDFF;
	}
	
	.fancy-close-aux{
		top: 1px!important;
		right: 1px!important;
	}
</style>

<%@ include file="logadoForce.jsp" %>
<%@ include file="idioma.jsp" %>
<%@ include file="methods.jsp" %>

<%	if (usuarioLogado == null || usuarioLogado.semPermissaoAcesso(410) ) { %> 
		<script type="text/javascript">location.href = "painel.jsp";</script>
<%	
		return;
	}
	String msg = request.getParameter("msg");
	
	try {
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), 0l, "Perfil de acesso");
		
		String filtroNome 	= returnNotNull(request.getParameter("filtroNome")).toString();			
		String aux			= returnNotNull(request.getParameter("pagina")).toString();
		int pagina = 		(aux == null || aux.equals("") ? 1 : Integer.valueOf(aux));
%>
		
<div class="contentTable">
	<div class="clear20"></div>
	<div>
		<div class="inline">
			<div><%= p.get("NOME") %></div>
			<input type="text" id="filtroNome" class="filterAdminUsuarioText" name="filtroNome" placeHolder="<%= p.get("NOME") %>" value="<%= filtroNome %>"/>
		</div>
		
		<%	if( !usuarioLogado.semPermissaoAcesso(410, "incluir") ) { %>
				<div class="inline" >
					<button class="btn btn-primary btn-circle grouped_elements" href="adminPerfilEdit.jsp?idPerfil=0" style="width: 200px"><span class="iconfa-plus-sign icon-white"></span> <%= p.get("PERFIL_NOVO") %></button>
				</div>
		<%	} %>
		
		<script> 
			$(".filterAdminUsuarioText").keyup(function(){
				adminUsuarioTable.fnDraw();
			});
			$(".filterAdminUsuarioSelect").change(function(){
				adminUsuarioTable.fnDraw();
			});
		</script>	
	</div>
	<div>&nbsp;</div>
	<!-- <h4 class="widgettitle">&nbsp;</h4> -->
	<table class="table table-bordered" id="adminUsuario">
        <colgroup>
            <col class="con0" style=" width: 75%" />
            <col class="con1" style=" width: 25%" />
        </colgroup>
		<thead>
			<tr>
				<th class="head1 headerSortDown sortable"><%=p.get("NOME") %></th>
				<th class="head1 nosortable"><%=p.get("ACOES") %></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>		
</div>
<div class="clear20"></div>	
<div class="clear20"></div>	
<script>

$(document).ready(function(){
	
	function ajustesTelaAdminPerfil(){
		
		$(document).on('click',".grouped_elements",function(){			
			
			setTimeout(function(){
				
				var tamanhoTela = $(".rightpanel").width();	
				
				if(tamanhoTela <= 600){		
					var containerFuncionalidades = $(".widgetbox.personal-information").clone();					
					var containerLogin = $(".login-information");
					
					$("#fancybox-wrap").removeAttr("style");
					$("#fancybox-wrap").attr("style","height: auto; width: 100vw; padding:0px; display: block; top: 0px; left: 0px");
					
					$("#fancybox-content").removeAttr("style");
					$("#fancybox-content").attr("style","border-width: 10px; width: 95%; height: auto;");
					
					$("#fancybox-close").addClass("fancy-close-aux");
					$(".widgetbox.personal-information").remove();
					$("#fancybox-bg-n").remove();
					$("#fancybox-bg-ne").remove();
					$("#fancybox-bg-e").remove();
					$("#fancybox-bg-se").remove();
					$("#fancybox-bg-s").remove();	
					
					$(containerLogin).after(containerFuncionalidades);					
					
				}				
				
			},700);			
			
		});	
	}		
	
	ajustesTelaAdminPerfil();
	
});	



var aoColumns = [];
$("#adminUsuario thead tr th").each(function(){
	if($(this).hasClass("nosortable")){
		aoColumns.push( { "bSortable": false } );
	}else{
		aoColumns.push( null );
	}
});

var adminUsuarioTable = $('#adminUsuario').dataTable({
				"sPaginationType": "full_numbers",
		    	"sSortAsc": "sorting_asc",
		    	"sSortDesc": "sorting_desc",
		    	"sSortable": "sortable",
		 		"bProcessing": true,
		    	"bServerSide": true,
		    	"sAjaxSource": "table/perfilTable.jsp",
		    	"bFilter": false,
			    "aoColumns": aoColumns,
		    	"fnServerParams": function ( aoData ) {
		 	 		aoData.push( { "name": "filtroNome", "value": $("#filtroNome").val() } );
		     	},
		     	"fnDrawCallback" : function() {
		     		$(".grouped_elements").fancybox({
		     			onClosed: function(){
		     				adminUsuarioTable.fnDraw();
		     			},
		     			titleShow: false 
		     		});
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