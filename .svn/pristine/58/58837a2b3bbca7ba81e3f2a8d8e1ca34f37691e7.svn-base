<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.tetrasoft.data.cliente.SuporteEntity"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%>
<%	
	int mensagensNaoLidas = 0;
	Connection conn_home = null;

	try {
		conn_home = new UsuarioEntity().retrieveConnection();
%>

<div id="mainwrapper" class="mainwrapper">
	<%@ include file="topo.jsp" %>

	
	<div class="rightpanel" id="miolo">
		<input type="hidden" name="painelCheck" value="ok" />
		
		<ul class="breadcrumbs">
			<li><a style="color : black;"href="painel.jsp"><i class="iconfa-home"></i></a> <span class="separator"></span></li>
			<li><%=p.get("PAINEL") %></li>
		</ul>
		
		<div class="pageheader">
		  	<div class="pageicon"><span class="iconfa-laptop"></span></div>
			<div class="pagetitle">
				<h5><%=p.get("PAINEL_INICIAL") %></h5>
				<h1><%=p.get("PAINEL") %></h1>

				<table style="font-size: 14px">
				</table>
				
			</div>
		</div><!--pageheader-->

		<div class="maincontent">
			<div class="maincontentinner">

				<!-- CENTRO DO PAINEL -->				
				<div class="row-fluid">

					<div id="centroPainel"></div>
					<script>
						javascript:calendario(1, 0);
					</script>
			

				  	<div id="dashboard-left" class="span4">
				  		<%
				  			MensagemEntity informativo = new MensagemEntity();
							ArrayList<MensagemEntity> msgs = (ArrayList<MensagemEntity>)informativo.getArray("tipo = " + MensagemEntity.TIPO_CORREIO + " AND status = " + MensagemEntity.STATUS_NAO_LIDO + " AND (idDestinatario = " + usuarioLogado.getIdUsuario() + " OR idDestinatario = 0) ORDER BY dataHora DESC", conn_home);
							mensagensNaoLidas = msgs.size();

				  			if( mensagensNaoLidas == 0 ) {
				  		%>
				  				<script>
				  					document.getElementById("centroPainel").style.width = "100%";
				  				</script>
				  					
				  		<% 		
				  			} else { 
								informativo = msgs.get(0); 
								
				  				UsuarioEntity u2 = new UsuarioEntity();
				  				u2.setIdUsuario( informativo.getIdRemetente() );
				  				u2.getThis(conn_home);
				  				String remetente = u2.getNome();
				  				
				  				if( informativo.getMensagem().contains("iframe") ) { // google maps do IP
				  					informativo.setMensagem( informativo.getMensagem().replaceAll("width='600' height='400'", "width='100%' height='355'") );
				  				}
				  		%>
								<div class="row-fluid" style="cursor: pointer;" onclick="javascript:abre('miolo', 'mensagemLer.jsp?idMensagem=<%=informativo.getIdMensagem() %>')">
									<div class="row-fluid">
										<h4 class="widgettitle"><%= remetente %> - <%= informativo.getAssunto() %></h4>
										<h4 class="widgettitle alert">
											<%= defaultTimeSimpleDateFormat.format( informativo.getDataHora() ) %>
									  		<p style="margin: 8px 0">
												<%= informativo.getMensagem() %>							  
									  		</p>
									  	</h4>
									</div>
									<!--span4-->
								</div>			
						<%	} %>		  
						
						<%	
							SuporteEntity os = new SuporteEntity();
							ArrayList mensagens = os.getArray("idUsuario = " + usuarioLogado.getIdUsuario() + " AND status = 1 ORDER BY dataMensagem DESC");										
			
							if( mensagens.size() > 0 ) {									
						%>
								<div class="row-fluid">
									<h4 class="widgettitle"><%=p.get("TICKETS_ABERTOS_PENDENTES") %></h4>
									<h4 class="widgettitle alert" style="padding: 0px">
										<table class="table table-bordered responsive dataTable" style="width: 100%; padding: 0px; margin: 0px">
											<colgroup>
												<col class="con0" style="align: center; width: 4%">
												<col class="con1">
												<col class="con0">
											</colgroup>
											
											<tbody role="alert" aria-live="polite" aria-relevant="all">
												<%
													for( int i = 0; i < mensagens.size(); i++ ) {
														os = (SuporteEntity)mensagens.get(i);
												%>
														<tr class="<%= i%2 == 0 ? "odd" : "even" %>" onclick="javascript:abre('miolo', 'suporteLer.jsp?idOS=<%= os.getIdSuporte() %>')" style="cursor: pointer;">
															<td style="width: 33%" class=" "><h5><strong><%= os.getIdSuporte() %></strong></h5></td>
															<td style="align: center; width: 33%" class=" "><%= p.get( SuporteEntity.assuntos[ new Integer(os.getAssunto()) ] ) %></td>
															<td style="width: 33%" class=" "><%= os.DATE_FORMAT1_BR2.format( os.getDataMensagem() ) %></td>
														</tr>
												<%	} %>
												
											</tbody>
										</table>
									</h4>
								</div>
						<%	} %>									
							
					</div> <!-- menu direito -->
					
			
					<!-- RODAPE -->
					
				  	<div class="footer">
						<div class="footer-left">
							<p>&copy; <%= new SimpleDateFormat("yyyy").format(new Date()) %>. <%=p.get("TODOS_DIREITOS_RESERVADOS") %> &reg; - <a style="color : black;" href="http://www.atallsolutions.com.br">www.atallsolutions.com.br</a> </p>
					  	</div>
					</div><!--footer-->
				
				</div><!--row-fluid-->
				<!-- / CENTRO DO PAINEL -->				
			
			</div><!--maincontentinner-->
	  	</div><!--maincontent-->
	</div><!--rightpanel-->

</div><!--mainwrapper-->

<%
	String redir = request.getParameter("redirect");
	if( redir != null ) {
%>
	  	<a href="/allgenda/<%= redir %>" class="grouped_elements" id="popup_box"></a>
		<script>
		    setTimeout("document.getElementById('popup_box').click();", 500);
		</script>
<%
	}
	
	String redir2 = request.getParameter("redirectNoFancy");
	if( redir2 != null ) {
%>
		<script>
			setTimeout("javascript:abre('miolo', '<%= redir2 %>');", 500);
		</script>
<%
	}
	
	String clean = request.getParameter("clean");
	if( clean != null ) {
%>
		<style>
			#header, .breadcrumbs, .leftpanel { 
				display: none 
			};
			
			.leftpanel {
				width: 0px !important;
			}
			
			.rightpanel {
				margin-left: 0px !important;
			}
			
			.centro {
				padding: 0px !important;
			}
		</style>
<%		
	}

	out.println("<div id='erro'>");
	if(request.getParameter("info") != null){
		out.println("<script>infoAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("info")) == null ? request.getParameter("info") : p.get(request.getParameter("info")))+"'), function() {});</script>");
	}else if(request.getParameter("warning") != null){
		out.println("<script>warningAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("warning")) == null ? request.getParameter("warning") : p.get(request.getParameter("warning")))+"'), function() {});</script>");
	}
	out.println("</div>");
%>

<%@ include file="rodape.jsp" %>

<%
	} catch( Exception e ) {
		e.printStackTrace();
	} finally {
		conn_home.close();
	}
%>

