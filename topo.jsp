<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.File"%>
<%@page import="com.tetrasoft.app.sender.SenderInterface"%>
<%@page import="com.tetrasoft.data.cliente.MensagemEntity"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tetrasoft.data.usuario.UsuarioEntity"%>
<%@page import="com.technique.engine.web.UserSession"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@ include file="header.jsp" %>

<%
	if(usuarioLogado == null ){
		response.sendRedirect("/allgenda/index.jsp");
		return;
	}
	int odd = 0;
	Connection conn = null;
	
	try {
		conn = usuarioLogado.retrieveConnection();
		
		if(usuarioLogado != null && (request.getRequestURI().endsWith("allgenda/") || request.getRequestURI().contains("index.jsp") ) ){
%>
			<script>location.href="/allgenda/painel.jsp?skip=true"</script>
<%
		}
%>

<!-- AQUI COMEéA O MENU DO TOPO -->
<div class="header" id="header">
	<div class="logo">
		<a href="painel.jsp"><img src="images/logo/<%= ConfigEntity.CONFIG.get("logo") %>"  alt="" width="100%" /></a>
	</div>
	
	<div class="headerinner">
		<ul class="headmenu">
			<%
				MensagemEntity msg = new MensagemEntity();				 
			%>
			
			<li>
				<a href="calendarioEdit.jsp">
					<span class="head-icon iconfa-calendar" style="font-size:45px;"></span>
					<span class="count"></span>
					<span class="headmenu-label">Marcar reunião</span>
				</a>			
			</li>
			
			<li style="display:none" class="<%= odd%2 == 0 ? "odd" : "" %>">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#" >
					<%
						ArrayList<MensagemEntity> mensagens = null;
						mensagens = (ArrayList<MensagemEntity>)msg.getArray("tipo = " + MensagemEntity.TIPO_CORREIO + " AND status = " + MensagemEntity.STATUS_NAO_LIDO + " AND (idDestinatario = " + usuarioLogado.getIdUsuario() + " OR idDestinatario = 0) ORDER BY dataHora DESC", conn);
						mensagensNaoLidas = mensagens.size();
					%>
					<span class="count" id="naoLidas"><%= mensagensNaoLidas %></span>
					<span class="head-icon head-message <%= mensagensNaoLidas > 0 ? "blink" : "" %>" id="mensagemEnvelope"></span>
					<span class="headmenu-label"><%=p.get("PAINEL_RECADO") %></span>
				</a>
			
				<ul class="dropdown-menu" id="listaMensagens">
					<li class="nav-header"><%=p.get("MENSAGENS_NOVAS").toString().toUpperCase() %></li>
					
					<%	for( int i = 0; i < mensagensNaoLidas; i++ ) {
							msg = (MensagemEntity)mensagens.get(i);
					%>
							<li>
								<a href="javascript:abre('miolo', 'mensagemLer.jsp?idMensagem=<%=msg.getIdMensagem()%>')"><span class="icon-envelope"></span> 
									<%=p.get("ENVIADO_POR") %>: <strong><%= msg.getRemetente(conn) %></strong> <small class="muted"> <%= defaultTimeMinuteSimpleDateFormat.format( msg.getDataHora() ) %> </small>
									<br/>
									<%= p.get("ASSUNTO") %>: <strong><%= msg.getAssunto().replaceAll(": " + msg.getRemetente(conn), "") %> </strong>
								</a>
							</li>
							<hr/>
					<%	} %>
					
					<li><a href="javascript:abre('miolo', 'mensagemLista.jsp?caixa=inbox')"><span class="icon-envelope"></span>&nbsp;<%=p.get("VER_CAIXA_DE_ENTRADA") %></a></li>
					<li><a href="javascript:abre('miolo', 'mensagemLista.jsp?caixa=outbox')"><span class="iconfa-envelope"></span>&nbsp;<%=p.get("VER_CAIXA_DE_SAIDA") %></a></li>
					<li><a href="javascript:abre('miolo', 'mensagemEnviar.jsp')"><span class="iconfa-mail-forward"></span>&nbsp;<%=p.get("CRIAR_NOVA_MENSAGEM") %></a></li> 
					<li><a href="javascript:abre('miolo', 'mensagemTemplate.jsp')"><span class="iconfa-list"></span>&nbsp;<%=p.get("NOVO") %> Template</a></li> 
				</ul>
				<%odd++; %>
			</li>
			
			<!-- verificador de mensagens novas -->
			<span id="sound"></span>
			<div style="position: absolute; display: none">
				<div id="divPingMensagem"></div> 
				<input type="text" name="mensagensNovas1" value="<%= mensagensNaoLidas %>" style="width: 15px" />
				<script>
					carregaMensagens();
				</script>
			</div>
			<!-- verificador de mensagens novas -->
			
			
			<%	if( !usuarioLogado.semPermissaoAcesso(690) ) { %> 
					<li style="display:none" class="relatorio <%= odd%2 == 0 ? "odd" : "" %>">
						<a class="dropdown-toggle" data-toggle="dropdown" data-target="#" onclick="javascript:abre('miolo', 'calendarioRelatorioEdit.jsp')">
							<span class="count"></span>
							<span class="head-icon head-report"></span>
							<span class="headmenu-label"><%=p.get("RELATORIOS") %> </span>
						</a>
						<%odd++; %>
					</li>
			<%	} %>

			<%
				String linkConfig = "adminUsuarioDadosPessoais.jsp?idUsuario=" + usuarioLogado.getIdUsuario();
			%>

			<li style="display:none" class="menuTopo <%= odd%2 == 0 ? "odd" : "" %>">
				<a class="dropdown-toggle" data-toggle="dropdown" data-target="#">
					<span class="head-icon head-config"></span>
					<span class="count"></span>
					<span class="headmenu-label"><%=p.get("CONFIGURACOES") %></span>
				</a>
				<ul class="dropdown-menu userinfo">
					<li class="nav-header"><a class='grouped_elements' href='<%= linkConfig %>'><%= p.get("DADOS_PESSOAIS") %></a></li>
				</ul>
				<%odd++; %>
			</li>
		   
		  <li class="menuTopo right" style="height: 110px; overflow: hidden; position: absolute">
				<div class="userloggedinfo">
					<a class='grouped_elements' href='<%= linkConfig %>'>
						<%
							String caminho = SystemParameter.get(SenderInterface.SID, "systemProperties", "filePath")+"upload/usuario/"+usuarioLogado.getIdUsuario()+"/foto/";
							File folder = new File(caminho);
							File[] file = folder.listFiles();
							try{
								boolean achou = false;
								if(file != null ) {
									for( File f : file ) {
										if( !f.toString().endsWith(".svn") ) {
											if( f.toString().contains("perfil") ) {
												out.print("<img id='foto' src='upload/usuario/"+usuarioLogado.getIdUsuario()+"/foto/"+f.getName()+"?nc="+new Date().getTime()+"' height='200' border='0'/>");
												achou = true;
											}
										}
									}
								}
								
								if( !achou ) {
									out.print("<img src='images/blankperson.jpg' alt='' height='50' border='0' />");
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						%>					
					</a>
				   
					<div class="userinfo">
						<h5><%= usuarioLogado.getNome() %></h5>
						<ul>
							<li><a class='grouped_elements' href='<%= linkConfig %>'><%=p.get("EDITAR_PERFIL") %></a></li>
							<li><a href="logout.jsp"><%= p.get("SAIR") %></a></li>
							
							<li><a href="#"><%= usuarioLogado.getUltimoAcesso(conn, "PT") %></a></li>
						</ul>
					</div>
				</div>
			</li>
		</ul><!--headmenu-->
 
	</div>
</div>
<%
	} catch( Exception e ) {
		e.printStackTrace();
	} finally {
		conn.close();
	}
%>
