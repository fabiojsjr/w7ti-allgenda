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
	
	.stdform label.error{
		margin-left: 0px!important;
	}	
	
	.fancy-close-aux{
		top: 1px!important;
		right: -2px!important;
	}
	
	@media only screen 	
	and (max-device-width : 380px)  {
	
		.statusAtivo, .filterStatusContainer, table tbody tr td:nth-child(4){
			display:none;
		}
	
	}
	
	
</style>

<%@ include file="logadoForce.jsp" %>
<%@ include file="idioma.jsp" %>
<%@ include file="methods.jsp" %>

<%	if (usuarioLogado == null || usuarioLogado.semPermissaoAcesso(400) ) { %> 
		<script type="text/javascript">location.href = "painel.jsp";</script>
<%	
		return;
	}
	String msg = request.getParameter("msg");
	
	try {
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), 0l, "Usuário");
		
		String filtroNome 	= returnNotNull(request.getParameter("filtroNome")).toString();			
		String filtroLogin 	= returnNotNull(request.getParameter("filtroLogin")).toString();			
		String filtroEmail 	= returnNotNull(request.getParameter("filtroEmail")).toString();
		String filtroAtivo	= returnNotNull(request.getParameter("filtroAtivo")).toString();
		String aux			= returnNotNull(request.getParameter("pagina")).toString();
		int pagina = 		(aux == null || aux.equals("") ? 1 : Integer.valueOf(aux));
		
%>

<div id="erro"/>
		
<div class="contentTable">
	<div class="clear20"></div>
	<div>
		<div class="inline">
			<div><%= p.get("NOME") %></div>
			<input type="text" id="filtroNome" class="filterAdminUsuarioText input-medium" name="filtroNome" placeHolder="<%= p.get("NOME") %>" value="<%= filtroNome %>"/>
		</div>
		<div class="inline">
			<div><%= p.get("FORM_LOGIN") %></div>
			<input type="text" id="filtroLogin" class="filterAdminUsuarioText input-medium" name="filtroLogin" placeHolder="<%= p.get("FORM_LOGIN") %>" value="<%= filtroLogin %>"/>
		</div>
		
		<div style="display:none" class="inline">
			<div><%= p.get("EMAIL") %></div>
			<input type="text" id="filtroEmail" class="filterAdminUsuarioText input-medium" name="filtroEmail" placeHolder="<%= p.get("EMAIL") %>" value="<%= filtroEmail %>" />
		</div>
		
		<div class="filterStatusContainer inline">
			<div><%= p.get("ATIVO") %></div>
			<select name="filtroAtivo" class="filterAdminUsuarioSelect input-medium" id="filtroAtivo" style="width: 144px; height: 32px;">
				<option value="blank" > <%="-- " + p.get("SELECIONE") + " --"%> </option>
				<%
					for(Entry<Integer,String> entry : UsuarioEntity.ATIVO.entrySet()){
				%>
					<option value="<%=entry.getKey()%>" <%=filtroAtivo.equals(entry.getKey().toString()) ? "selected" : "" %>><%=entry.getValue() %></option>
				<%
					}
				%>
			</select>
		</div>

		<%	if( !usuarioLogado.semPermissaoAcesso(400, "incluir") ) { %>
					<button class="btn btn-primary btn-circle grouped_elements" href="adminUsuarioDadosPessoais.jsp?idUsuario=0" style="width: 200px"><span class="iconfa-plus-sign icon-white"></span> <%= p.get("ADICIONAR") %> <%= p.get("USUARIO") %></button>
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
	<table class="table table-bordered responsive" id="adminUsuario">
        <colgroup>
            <col class="con0" style=" width: 35%" />
            <col class="con1" style=" width: 35%" />
            <col class="con1" style=" width: 10%" />
            <col class="con0" style=" width: 5%" />
            <col class="con1" style=" width: 15%" />
        </colgroup>
		<thead>
			<tr>
				<th class="head0 headerSortDown sortable"><%=p.get("FORM_LOGIN") %></th>
				<th class="head1 headerSortDown sortable"><%=p.get("NOME") %></th>
				<th class="head1 headerSortDown sortable"><%=p.get("PERFIL") %></th>
				<th class="head0 statusAtivo headerSortDown sortable"><%=p.get("STATUS") %></th>
				<th class="head1 nosortable"><%=p.get("ACOES") %></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>			
</div>

<div class="clear20"></div>

<script>

/*$(document).ready(function(){
	
	function ajustesTelaAdminUsuario(){
		
		$(document).on('click',".grouped_elements",function(){			
			
			setTimeout(function(){
				
				var tamanhoTela = $(".rightpanel").width();	
				
				if(tamanhoTela <= 600){					
					
					//em telas pequenas não vai usar esse elemento
					//$("#fancybox-overlay").removeAttr("style");
					//$("#fancybox-overlay").attr("style","background-color: rgb(119, 119, 119);opacity: 0.7; cursor: pointer; height: auto; display: block;");						
					
					$("#fancybox-wrap").removeAttr("style");
					$("#fancybox-wrap").attr("style","height: auto; width: 100vw; padding:0px; display: block; top: 0px; left: 0px;");
					
					$("#fancybox-content").removeAttr("style");
					$("#fancybox-content").attr("style","border-width: 10px; width: 95%; height: auto;");
					
					$("#fancybox-close").addClass("fancy-close-aux");
					$("#fancybox-bg-n").remove();
					$("#fancybox-bg-ne").remove();
					$("#fancybox-bg-e").remove();
					$("#fancybox-bg-se").remove();
					$("#fancybox-bg-s").remove();
					
					var btnSalvar = $(".containerBtnSend").clone();
					$(".containerBtnSend").remove();
					$("#formUpdateCadastroPersonalInfo").append(btnSalvar);
				}				
				
			},500);			
			
		});	
	}		
	
	ajustesTelaAdminUsuario();
	
});	*/



function inativarUsuario2(id, form, table){
		
	form.command.value = "usuario";
	form.action.value = "inativarUsuario";
	
	$.ajax({
		type: 'POST',
		url: createQueryString(form)+"&inativo="+id,
		dataType: 'html',
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		processData: false,
		success: function(data){
			$('#erro').html(data);
			table.fnDraw();
			$("#erro").remove();
			infoAlert("Mensagem", "Usu&aacute;rio alterado com sucesso", function(){});	
			
			setTimeout(function(){
				
				$("#popup_ok").trigger('click');
			},1000);
			
		}
	});
		

}



// $("input[name=filtroCpf]").mask("999.999.999-99");

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
		    	"sAjaxSource": "table/usuarioTable.jsp",
		    	"bFilter": false,
			    "aoColumns": aoColumns,
		    	"fnServerParams": function ( aoData ) {
		 	 		aoData.push( { "name": "filtroNome", "value": $("#filtroNome").val() } );
		 	 		aoData.push( { "name": "filtroLogin", "value": $("#filtroLogin").val() } );
		 	 		//aoData.push( { "name": "filtroEmail", "value": $("#filtroEmail").val() } );
		 	 		aoData.push( { "name": "filtroAtivo", "value": $("#filtroAtivo").val() } );
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