<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.cliente.RelatorioEntity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@ include file="logadoForce.jsp" %>
<%@ include file="idioma.jsp" %>

<link rel="stylesheet" href="css/responsive-tables.css">
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/responsive-tables.js"></script>

<ul class="breadcrumbs">
	<li><a href="painel.jsp"><i class="iconfa-home"></i></a> <span class="separator"></span></li>
	<li><b><%= p.get("RELATORIOS") %></b></li>
</ul>

<div class="pageheader seta" onclick="javascript:abre('miolo', 'relatorio.jsp')">
	<div class="pageicon"><span class="iconfa-list"></span></div>
	<div class="pagetitle">
		<h5>&nbsp;</h5>
		<h1><%= p.get("RELATORIOS") %></h1>
	</div>
</div>

<%	
	int idFunc = 690; 
	if (usuarioLogado == null || usuarioLogado.semPermissaoAcesso(idFunc) ) { %> 
		<script type="text/javascript">location.href = "painel.jsp";</script>
<%	
		return;
	}

	usuarioLogado.getThis("idUsuario="+usuarioLogado.getIdUsuario());
	String msg = request.getParameter("msg");
	
	String arquivo = request.getParameter("file");
	if(arquivo!=null) msg = "<a href='"+arquivo+"'target='blank'><span style='color: darkblue'><img src='images/excel.png' width='36' align='middle' border='0' /><br/>&nbsp;Clique aqui para visualizar o relatório.</span></a>";
	
	String data1 = request.getParameter("data1");
	String data2 = request.getParameter("data2");
	if(data1==null) data1 = "";
	if(data2==null) data2 = "";
	
	String idRelatorio = request.getParameter("relatorio");
	
	new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), 0l, "Relatórios");
	%>
	<div class="maincontentinner">
		<div class="maincontent">
			<div class="row-fluid">
				<div class="contentTable">
					<div class="maincontent">
						<div class="row-fluid">
							<h4 class="widgettitle"><%=p.get("RELATORIOS") %></h4>
								<div class="widgetcontent tcenter">
								
								<span class="inline" style="display:none ;" >
									<small class="desc" style="margin-left: 0px; display: table;">Data Inicial</small>
										<input placeholder="Data Inicial" type="text" name="data1" id="from" value="<%= data1 %>" class="input-small datepicker" />
								</span> 
									
								<span class="inline" style="display:none ;" >
									<small class="desc" style="margin-left: 0px; display: table;">Data Final</small> 
									<input placeholder="Data Final" type="text" name="data2" id="to" value="<%= data2 %>" class="input-small datepicker" /> 
 								</span> 
								
									<span class="inline">
										<select class="uniformselect input-xlarge" name="relatorio">
											<option value="">-- <%= p.get("SELECIONE") %> --</option>
											<%
												ArrayList<RelatorioEntity> relatorios = new RelatorioEntity().getArray("1=1  order by nome" );
												for(RelatorioEntity relatorio:relatorios){
											%>
													<option value="<%= relatorio.getIdRelatorio() %>" <%= relatorio.getIdRelatorio().toString().equals(idRelatorio) ? "selected='selected'" : "" %>>
														<%= relatorio.getNome() %>
													</option>
											<%	} %>
										</select>
									</span>
									
									<span class="inline tright">
										<button id="escolherRelatorio" class="btn btn-primary">Ok</button>
									</span>
									
									<script>
										$("#escolherRelatorio").click(function(e){
											e.preventDefault();
											
											formX.command.value = "relatorio";
											formX.action.value = "gerar";
											
											abre('miolo', "/allgenda/" + createQueryString(formX));
										});
									</script>
									<%if(msg != null){ %>
										<p class="tcenter">
											<a href="#">
												<%= msg %>
											</a>
										</p>
									<%} %>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
	$(document).ready(function(){
		$("#from").mask("99/99/9999");
		$("#to").mask("99/99/9999");
		
	   $( "#from" ).datepicker({
	    	'dateFormat' : 'dd/mm/yy'
	    });

	    $( "#to" ).datepicker({
	    	'dateFormat' : 'dd/mm/yy'
	    });
		
		
	});
	
	</script>
	